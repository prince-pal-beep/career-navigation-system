package com.careernav.dao;

import com.careernav.config.DBConnection;

import java.sql.*;

/** Contact-us messages and feedback (simple inserts). */
public class MessageDAO {

    public void saveContact(String name, String email, String message) throws SQLException {
        try (Connection c = DBConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement("INSERT INTO contact_messages(name,email,message) VALUES(?,?,?)")) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, message);
            ps.executeUpdate();
        }
    }
}
