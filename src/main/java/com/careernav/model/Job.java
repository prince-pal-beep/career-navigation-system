package com.careernav.model;

import java.util.*;

/** Plain Java Bean for the Job entity. */
public class Job implements java.io.Serializable {
    private int jobId;
    private int employerId;
    private String title;
    private String description;
    private String location;
    private int requiredExperience;
    private String salaryRange;
    private String status;
    private String companyName;
    private int applicantCount;
    private int matchScore;
    private java.sql.Timestamp postedOn;
    private List<Skill> skills = new ArrayList<>();

    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }
    public int getEmployerId() { return employerId; }
    public void setEmployerId(int employerId) { this.employerId = employerId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public int getRequiredExperience() { return requiredExperience; }
    public void setRequiredExperience(int requiredExperience) { this.requiredExperience = requiredExperience; }
    public String getSalaryRange() { return salaryRange; }
    public void setSalaryRange(String salaryRange) { this.salaryRange = salaryRange; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public int getApplicantCount() { return applicantCount; }
    public void setApplicantCount(int applicantCount) { this.applicantCount = applicantCount; }
    public int getMatchScore() { return matchScore; }
    public void setMatchScore(int matchScore) { this.matchScore = matchScore; }
    public java.sql.Timestamp getPostedOn() { return postedOn; }
    public void setPostedOn(java.sql.Timestamp postedOn) { this.postedOn = postedOn; }
    public List<Skill> getSkills() { return skills; }
    public void setSkills(List<Skill> skills) { this.skills = skills; }
}
