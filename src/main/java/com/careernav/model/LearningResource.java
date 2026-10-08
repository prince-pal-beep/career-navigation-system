package com.careernav.model;

import java.util.*;

/** Plain Java Bean for the LearningResource entity. */
public class LearningResource implements java.io.Serializable {
    private int resourceId;
    private int careerId;
    private int skillId;
    private String title;
    private String resourceType;
    private String link;
    private String skillName;

    public int getResourceId() { return resourceId; }
    public void setResourceId(int resourceId) { this.resourceId = resourceId; }
    public int getCareerId() { return careerId; }
    public void setCareerId(int careerId) { this.careerId = careerId; }
    public int getSkillId() { return skillId; }
    public void setSkillId(int skillId) { this.skillId = skillId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getResourceType() { return resourceType; }
    public void setResourceType(String resourceType) { this.resourceType = resourceType; }
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
}
