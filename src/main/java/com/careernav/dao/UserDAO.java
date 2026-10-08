package com.careernav.dao;

import com.careernav.config.DBConnection;
import com.careernav.model.Employer;
import com.careernav.model.User;

import java.sql.*;
import java.util.*;

public class UserDAO {

    private User map(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setName(rs.getString("name"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setRole(rs.getString("role"));
        u.setQualification(rs.getString("qualification"));
        u.setExperience(rs.getInt("experience"));
        u.setInterests(rs.getString("interests"));
        u.setStatus(rs.getString("status"));
        return u;
    }

    public boolean emailExists(String email) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT 1 FROM users WHERE email=?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    /** Inserts a user (and an employer row when {@code e} is not null) in one transaction. */
    public int register(User u, Employer e) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection()) {
            c.setAutoCommit(false);
            try {
                int id;
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO users(name,email,password,role,qualification,experience,interests) VALUES(?,?,?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, u.getName());
                    ps.setString(2, u.getEmail());
                    ps.setString(3, u.getPassword());
                    ps.setString(4, u.getRole());
                    ps.setString(5, u.getQualification());
                    ps.setInt(6, u.getExperience());
                    ps.setString(7, u.getInterests());
                    ps.executeUpdate();
                    try (ResultSet k = ps.getGeneratedKeys()) { k.next(); id = k.getInt(1); }
                }
                if (e != null) {
                    try (PreparedStatement ps = c.prepareStatement(
                            "INSERT INTO employers(user_id,company_name,email,contact_no) VALUES(?,?,?,?)")) {
                        ps.setInt(1, id);
                        ps.setString(2, e.getCompanyName());
                        ps.setString(3, u.getEmail());
                        ps.setString(4, e.getContactNo());
                        ps.executeUpdate();
                    }
                }
                c.commit();
                return id;
            } catch (SQLException ex) {
                c.rollback();
                throw ex;
            }
        }
    }

    public User findByEmail(String email) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE email=?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }

    public User findById(int id) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE user_id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }

    public Employer findEmployerByUserId(int userId) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM employers WHERE user_id=?")) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                Employer e = new Employer();
                e.setEmployerId(rs.getInt("employer_id"));
                e.setUserId(rs.getInt("user_id"));
                e.setCompanyName(rs.getString("company_name"));
                e.setEmail(rs.getString("email"));
                e.setContactNo(rs.getString("contact_no"));
                return e;
            }
        }
    }

    public void updateProfile(User u) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "UPDATE users SET name=?, qualification=?, experience=?, interests=? WHERE user_id=?")) {
            ps.setString(1, u.getName());
            ps.setString(2, u.getQualification());
            ps.setInt(3, u.getExperience());
            ps.setString(4, u.getInterests());
            ps.setInt(5, u.getUserId());
            ps.executeUpdate();
        }
    }

    public void updatePassword(int userId, String hash) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement("UPDATE users SET password=? WHERE user_id=?")) {
            ps.setString(1, hash);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    /** skillId -> proficiency (1..5) for one user. */
    public Map<Integer, Integer> getSkillLevels(int userId) throws SQLException {
        Map<Integer, Integer> m = new HashMap<>();
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT skill_id, proficiency_level FROM user_skills WHERE user_id=?")) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) m.put(rs.getInt(1), rs.getInt(2));
            }
        }
        return m;
    }

    public void saveSkillLevels(int userId, Map<Integer, Integer> levels) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement del = c.prepareStatement("DELETE FROM user_skills WHERE user_id=?")) {
                    del.setInt(1, userId);
                    del.executeUpdate();
                }
                try (PreparedStatement ins = c.prepareStatement(
                        "INSERT INTO user_skills(user_id,skill_id,proficiency_level) VALUES(?,?,?)")) {
                    for (Map.Entry<Integer, Integer> e : levels.entrySet()) {
                        ins.setInt(1, userId);
                        ins.setInt(2, e.getKey());
                        ins.setInt(3, e.getValue());
                        ins.addBatch();
                    }
                    ins.executeBatch();
                }
                c.commit();
            } catch (SQLException ex) {
                c.rollback();
                throw ex;
            }
        }
    }

    public List<User> listAll() throws SQLException {
        List<User> list = new ArrayList<>();
        try (Connection c = DBConnection.getInstance().getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM users ORDER BY role, name")) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public void setStatus(int userId, String status) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement("UPDATE users SET status=? WHERE user_id=? AND role<>'ADMIN'")) {
            ps.setString(1, status);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    public void delete(int userId) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM users WHERE user_id=? AND role<>'ADMIN'")) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    private static final Set<String> TABLES = new HashSet<>(Arrays.asList("users", "careers", "skills", "jobs", "applications"));
    private static final Set<String> COLUMNS = new HashSet<>(Arrays.asList("role", "status"));

    public int count(String table) throws SQLException {
        if (!TABLES.contains(table)) throw new IllegalArgumentException(table);
        try (Connection c = DBConnection.getInstance().getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM " + table)) {
            rs.next();
            return rs.getInt(1);
        }
    }

    /** e.g. countGrouped("jobs","status") -> {APPROVED=3, PENDING=1}. Table/column are whitelisted. */
    public Map<String, Integer> countGrouped(String table, String column) throws SQLException {
        if (!TABLES.contains(table) || !COLUMNS.contains(column)) throw new IllegalArgumentException();
        Map<String, Integer> m = new LinkedHashMap<>();
        try (Connection c = DBConnection.getInstance().getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT " + column + ", COUNT(*) FROM " + table + " GROUP BY " + column)) {
            while (rs.next()) m.put(rs.getString(1), rs.getInt(2));
        }
        return m;
    }
}
