package com.careernav.model;

import java.util.*;

/** Plain Java Bean for the Application entity. */
public class Application implements java.io.Serializable {
    private int applicationId;
    private int userId;
    private int jobId;
    private java.sql.Timestamp applicationDate;
    private String status;
    private String jobTitle;
    private String companyName;
    private String applicantName;
    private String applicantEmail;
    private String applicantQualification;
    private int applicantExperience;

    public int getApplicationId() { return applicationId; }
    public void setApplicationId(int applicationId) { this.applicationId = applicationId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }
    public java.sql.Timestamp getApplicationDate() { return applicationDate; }
    public void setApplicationDate(java.sql.Timestamp applicationDate) { this.applicationDate = applicationDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }
    public String getApplicantEmail() { return applicantEmail; }
    public void setApplicantEmail(String applicantEmail) { this.applicantEmail = applicantEmail; }
    public String getApplicantQualification() { return applicantQualification; }
    public void setApplicantQualification(String applicantQualification) { this.applicantQualification = applicantQualification; }
    public int getApplicantExperience() { return applicantExperience; }
    public void setApplicantExperience(int applicantExperience) { this.applicantExperience = applicantExperience; }
}
