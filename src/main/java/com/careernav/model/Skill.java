package com.careernav.model;

import java.util.*;

/** Plain Java Bean for the Skill entity. */
public class Skill implements java.io.Serializable {
    private int skillId;
    private String skillName;
    private String skillType;
    private int level;

    public int getSkillId() { return skillId; }
    public void setSkillId(int skillId) { this.skillId = skillId; }
    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
    public String getSkillType() { return skillType; }
    public void setSkillType(String skillType) { this.skillType = skillType; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
}
