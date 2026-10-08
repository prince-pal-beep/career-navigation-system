package com.careernav.dao;

import com.careernav.config.DBConnection;
import com.careernav.model.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillDAO {

    public List<Skill> listAll() throws SQLException {
        List<Skill> list = new ArrayList<>();
        try (Connection c = DBConnection.getInstance().getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM skills ORDER BY skill_type, skill_name")) {
            while (rs.next()) {
                Skill s = new Skill();
                s.setSkillId(rs.getInt("skill_id"));
                s.setSkillName(rs.getString("skill_name"));
                s.setSkillType(rs.getString("skill_type"));
                list.add(s);
            }
        }
        return list;
    }

    public void add(String name, String type) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement("INSERT INTO skills(skill_name, skill_type) VALUES(?,?)")) {
            ps.setString(1, name);
            ps.setString(2, type);
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM skills WHERE skill_id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
