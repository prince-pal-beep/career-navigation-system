package com.careernav.dao;

import com.careernav.config.DBConnection;
import com.careernav.model.Career;
import com.careernav.model.LearningResource;
import com.careernav.model.Skill;

import java.sql.*;
import java.util.*;

public class CareerDAO {

    /** All careers, each with its required skills (Skill.level = importance 1..5). */
    public List<Career> listAll() throws SQLException {
        Map<Integer, Career> m = new LinkedHashMap<>();
        try (Connection c = DBConnection.getInstance().getConnection();
             Statement st = c.createStatement()) {
            try (ResultSet rs = st.executeQuery("SELECT * FROM careers ORDER BY career_name")) {
                while (rs.next()) {
                    Career cr = new Career();
                    cr.setCareerId(rs.getInt("career_id"));
                    cr.setCareerName(rs.getString("career_name"));
                    cr.setDescription(rs.getString("description"));
                    cr.setCategory(rs.getString("category"));
                    m.put(cr.getCareerId(), cr);
                }
            }
            try (ResultSet rs = st.executeQuery(
                    "SELECT cs.career_id, s.skill_id, s.skill_name, s.skill_type, cs.importance_level " +
                    "FROM career_skills cs JOIN skills s ON s.skill_id = cs.skill_id " +
                    "ORDER BY cs.importance_level DESC, s.skill_name")) {
                while (rs.next()) {
                    Career cr = m.get(rs.getInt(1));
                    if (cr == null) continue;
                    Skill s = new Skill();
                    s.setSkillId(rs.getInt(2));
                    s.setSkillName(rs.getString(3));
                    s.setSkillType(rs.getString(4));
                    s.setLevel(rs.getInt(5));
                    cr.getSkills().add(s);
                }
            }
        }
        return new ArrayList<>(m.values());
    }

    public Career findById(int id) throws SQLException {
        for (Career c : listAll()) if (c.getCareerId() == id) return c;   // tiny table - fine
        return null;
    }

    public void add(Career career, Map<Integer, Integer> skillImportance) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection()) {
            c.setAutoCommit(false);
            try {
                int id;
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO careers(career_name, description, category) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, career.getCareerName());
                    ps.setString(2, career.getDescription());
                    ps.setString(3, career.getCategory());
                    ps.executeUpdate();
                    try (ResultSet k = ps.getGeneratedKeys()) { k.next(); id = k.getInt(1); }
                }
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO career_skills(career_id, skill_id, importance_level) VALUES(?,?,?)")) {
                    for (Map.Entry<Integer, Integer> e : skillImportance.entrySet()) {
                        ps.setInt(1, id);
                        ps.setInt(2, e.getKey());
                        ps.setInt(3, e.getValue());
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
                c.commit();
            } catch (SQLException ex) {
                c.rollback();
                throw ex;
            }
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM careers WHERE career_id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /** Resources attached directly to a skill (or to the career itself). */
    public List<LearningResource> getResources(int careerId) throws SQLException {
        List<LearningResource> list = new ArrayList<>();
        String sql = "SELECT r.*, s.skill_name FROM learning_resources r LEFT JOIN skills s ON s.skill_id = r.skill_id " +
                     "WHERE r.career_id = ? OR r.skill_id IN (SELECT skill_id FROM career_skills WHERE career_id = ?) " +
                     "ORDER BY r.resource_id";
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, careerId);
            ps.setInt(2, careerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LearningResource r = new LearningResource();
                    r.setResourceId(rs.getInt("resource_id"));
                    r.setTitle(rs.getString("title"));
                    r.setResourceType(rs.getString("resource_type"));
                    r.setLink(rs.getString("link"));
                    r.setCareerId(rs.getInt("career_id"));
                    r.setSkillId(rs.getInt("skill_id"));
                    r.setSkillName(rs.getString("skill_name"));
                    list.add(r);
                }
            }
        }
        return list;
    }
}
