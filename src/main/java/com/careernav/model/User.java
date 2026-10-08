package com.careernav.model;

import java.util.*;

/** Plain Java Bean for the User entity. */
public class User implements java.io.Serializable {
    private int userId;
    private String name;
    private String email;
    private String password;
    private String role;
    private String qualification;
    private int experience;
    private String interests;
    private String status;

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }
    public String getInterests() { return interests; }
    public void setInterests(String interests) { this.interests = interests; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
