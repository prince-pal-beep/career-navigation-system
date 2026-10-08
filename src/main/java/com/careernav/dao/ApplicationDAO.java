package com.careernav.dao;

import com.careernav.config.DBConnection;
import com.careernav.model.Application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {

    /** @return false if the candidate already applied to this job. */
    public boolean apply(int userId, int jobId) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(
                 "INSERT INTO applications(user_id, job_id) SELECT ?, job_id FROM jobs WHERE job_id=? AND status='APPROVED'")) {
            ps.setInt(1, userId);
            ps.setInt(2, jobId);
            return ps.executeUpdate() == 1;
        } catch (SQLIntegrityConstraintViolationException dup) {
            return false;
        }
    }

    public List<Application> listByUser(int userId) throws SQLException {
        List<Application> list = new ArrayList<>();
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(
                 "SELECT a.*, j.job_title, e.company_name FROM applications a " +
                 "JOIN jobs j ON j.job_id=a.job_id JOIN employers e ON e.employer_id=j.employer_id " +
                 "WHERE a.user_id=? ORDER BY a.application_date DESC")) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Application a = base(rs);
                    a.setJobTitle(rs.getString("job_title"));
                    a.setCompanyName(rs.getString("company_name"));
                    list.add(a);
                }
            }
        }
        return list;
    }

    /** Applicants for a job - only if the job belongs to the given employer. */
    public List<Application> listByJob(int jobId, int employerId) throws SQLException {
        List<Application> list = new ArrayList<>();
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(
                 "SELECT a.*, u.name, u.email, u.qualification, u.experience FROM applications a " +
                 "JOIN jobs j ON j.job_id=a.job_id JOIN users u ON u.user_id=a.user_id " +
                 "WHERE a.job_id=? AND j.employer_id=? ORDER BY a.application_date")) {
            ps.setInt(1, jobId);
            ps.setInt(2, employerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Application a = base(rs);
                    a.setApplicantName(rs.getString("name"));
                    a.setApplicantEmail(rs.getString("email"));
                    a.setApplicantQualification(rs.getString("qualification"));
                    a.setApplicantExperience(rs.getInt("experience"));
                    list.add(a);
                }
            }
        }
        return list;
    }

    public void updateStatus(int applicationId, int employerId, String status) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(
                 "UPDATE applications a JOIN jobs j ON j.job_id=a.job_id SET a.status=? " +
                 "WHERE a.application_id=? AND j.employer_id=?")) {
            ps.setString(1, status);
            ps.setInt(2, applicationId);
            ps.setInt(3, employerId);
            ps.executeUpdate();
        }
    }

    private Application base(ResultSet rs) throws SQLException {
        Application a = new Application();
        a.setApplicationId(rs.getInt("application_id"));
        a.setUserId(rs.getInt("user_id"));
        a.setJobId(rs.getInt("job_id"));
        a.setApplicationDate(rs.getTimestamp("application_date"));
        a.setStatus(rs.getString("status"));
        return a;
    }
}
