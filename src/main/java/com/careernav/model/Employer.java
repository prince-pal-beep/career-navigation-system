package com.careernav.model;

import java.util.*;

/** Plain Java Bean for the Employer entity. */
public class Employer implements java.io.Serializable {
    private int employerId;
    private int userId;
    private String companyName;
    private String email;
    private String contactNo;

    public int getEmployerId() { return employerId; }
    public void setEmployerId(int employerId) { this.employerId = employerId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getContactNo() { return contactNo; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }
}
