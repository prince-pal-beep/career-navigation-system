package com.careernav.model;

import java.util.*;

/** Plain Java Bean for the Career entity. */
public class Career implements java.io.Serializable {
    private int careerId;
    private String careerName;
    private String description;
    private String category;
    private List<Skill> skills = new ArrayList<>();

    public int getCareerId() { return careerId; }
    public void setCareerId(int careerId) { this.careerId = careerId; }
    public String getCareerName() { return careerName; }
    public void setCareerName(String careerName) { this.careerName = careerName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public List<Skill> getSkills() { return skills; }
    public void setSkills(List<Skill> skills) { this.skills = skills; }
}
