package com.careernav.service;

import com.careernav.model.Career;
import com.careernav.model.LearningResource;
import com.careernav.model.Skill;

import java.util.*;

/** Compares a candidate's skill levels with a career's required levels. */
public class SkillGapService {

    /** One row of the gap report. */
    public static class Row {
        private final Skill skill; private final int required; private final int current;
        Row(Skill skill, int required, int current) { this.skill = skill; this.required = required; this.current = current; }
        public Skill getSkill() { return skill; }
        public String getSkillName() { return skill.getSkillName(); }
        public int getRequired() { return required; }
        public int getCurrent() { return current; }
        public int getGap() { return Math.max(0, required - current); }
        public boolean isMissing() { return current < required; }
    }

    public static class Result {
        private final Career career; private final List<Row> rows; private final int readiness;
        Result(Career career, List<Row> rows, int readiness) { this.career = career; this.rows = rows; this.readiness = readiness; }
        public Career getCareer() { return career; }
        public List<Row> getRows() { return rows; }
        public int getReadiness() { return readiness; }
        public List<Row> getMissing() {
            List<Row> m = new ArrayList<>();
            for (Row r : rows) if (r.isMissing()) m.add(r);
            return m;
        }
    }

    /** One step of a learning roadmap: a skill to improve + resources to use. */
    public static class Step {
        private final Row row; private final List<LearningResource> resources;
        Step(Row row, List<LearningResource> resources) { this.row = row; this.resources = resources; }
        public Row getRow() { return row; }
        public List<LearningResource> getResources() { return resources; }
    }

    public Result analyze(Career career, Map<Integer, Integer> userLevels) {
        List<Row> rows = new ArrayList<>();
        int total = 0, credit = 0;
        for (Skill s : career.getSkills()) {
            int cur = userLevels.getOrDefault(s.getSkillId(), 0);
            rows.add(new Row(s, s.getLevel(), cur));
            total += s.getLevel();
            credit += Math.min(cur, s.getLevel());
        }
        int readiness = total == 0 ? 0 : Math.round(100f * credit / total);
        return new Result(career, rows, readiness);
    }

    /** Roadmap = the missing skills, biggest gap first, each with its learning resources. */
    public List<Step> buildRoadmap(Result result, List<LearningResource> resources) {
        List<Row> missing = result.getMissing();
        missing.sort((a, b) -> Integer.compare(b.getGap(), a.getGap()));
        List<Step> steps = new ArrayList<>();
        for (Row r : missing) {
            List<LearningResource> rs = new ArrayList<>();
            for (LearningResource lr : resources) {
                if (lr.getSkillId() == r.getSkill().getSkillId()) rs.add(lr);
            }
            steps.add(new Step(r, rs));
        }
        return steps;
    }
}
