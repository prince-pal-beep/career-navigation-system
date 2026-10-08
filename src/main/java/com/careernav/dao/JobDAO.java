package com.careernav.dao;

import com.careernav.config.DBConnection;
import com.careernav.model.Job;
import com.careernav.model.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDAO {

    private Job map(ResultSet rs) throws SQLException {
        Job j = new Job();
        j.setJobId(rs.getInt("job_id"));
        j.setEmployerId(rs.getInt("employer_id"));
        j.setTitle(rs.getString("job_title"));
        j.setDescription(rs.getString("description"));
        j.setLocation(rs.getString("location"));
        j.setRequiredExperience(rs.getInt("required_experience"));
        j.setSalaryRange(rs.getString("salary_range"));
        j.setStatus(rs.getString("status"));
        j.setPostedOn(rs.getTimestamp("posted_on"));
        j.setCompanyName(rs.getString("company_name"));
        return j;
    }

    private static final String BASE =
        "SELECT j.*, e.company_name FROM jobs j JOIN employers e ON e.employer_id = j.employer_id ";

    public int post(Job j, int[] skillIds) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection()) {
            c.setAutoCommit(false);
            try {
                int id;
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO jobs(employer_id,job_title,description,location,required_experience,salary_range) VALUES(?,?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, j.getEmployerId());
                    ps.setString(2, j.getTitle());
                    ps.setString(3, j.getDescription());
                    ps.setString(4, j.getLocation());
                    ps.setInt(5, j.getRequiredExperience());
                    ps.setString(6, j.getSalaryRange());
                    ps.executeUpdate();
                    try (ResultSet k = ps.getGeneratedKeys()) { k.next(); id = k.getInt(1); }
                }
                try (PreparedStatement ps = c.prepareStatement("INSERT INTO job_skills(job_id, skill_id) VALUES(?,?)")) {
                    for (int s : skillIds) { ps.setInt(1, id); ps.setInt(2, s); ps.addBatch(); }
                    ps.executeBatch();
                }
                c.commit();
                return id;
            } catch (SQLException ex) {
                c.rollback();
                throw ex;
            }
        }
    }

    public Job findById(int jobId) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(BASE + "WHERE j.job_id=?")) {
            ps.setInt(1, jobId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }

    public List<Job> listApproved(String keyword, String location) throws SQLException {
        List<Job> list = new ArrayList<>();
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(BASE +
                 "WHERE j.status='APPROVED' AND (j.job_title LIKE ? OR j.description LIKE ?) AND IFNULL(j.location,'') LIKE ? " +
                 "ORDER BY j.posted_on DESC")) {
            String k = "%" + keyword + "%";
            ps.setString(1, k);
            ps.setString(2, k);
            ps.setString(3, "%" + location + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
            loadSkills(c, list);
        }
        return list;
    }

    public List<Job> listByEmployer(int employerId) throws SQLException {
        List<Job> list = new ArrayList<>();
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(
                 "SELECT j.*, e.company_name, (SELECT COUNT(*) FROM applications a WHERE a.job_id=j.job_id) AS applicants " +
                 "FROM jobs j JOIN employers e ON e.employer_id=j.employer_id WHERE j.employer_id=? ORDER BY j.posted_on DESC")) {
            ps.setInt(1, employerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) { Job j = map(rs); j.setApplicantCount(rs.getInt("applicants")); list.add(j); }
            }
        }
        return list;
    }

    public List<Job> listAll() throws SQLException {
        List<Job> list = new ArrayList<>();
        try (Connection c = DBConnection.getInstance().getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(BASE + "ORDER BY FIELD(j.status,'PENDING','APPROVED','REJECTED'), j.posted_on DESC")) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public void setStatus(int jobId, String status) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement("UPDATE jobs SET status=? WHERE job_id=?")) {
            ps.setString(1, status);
            ps.setInt(2, jobId);
            ps.executeUpdate();
        }
    }

    public void delete(int jobId) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM jobs WHERE job_id=?")) {
            ps.setInt(1, jobId);
            ps.executeUpdate();
        }
    }

    private void loadSkills(Connection c, List<Job> jobs) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(
                "SELECT s.skill_id, s.skill_name, s.skill_type FROM job_skills js JOIN skills s ON s.skill_id=js.skill_id WHERE js.job_id=?")) {
            for (Job j : jobs) {
                ps.setInt(1, j.getJobId());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Skill s = new Skill();
                        s.setSkillId(rs.getInt(1));
                        s.setSkillName(rs.getString(2));
                        s.setSkillType(rs.getString(3));
                        j.getSkills().add(s);
                    }
                }
            }
        }
    }
}
