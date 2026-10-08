package com.careernav.service;

import com.careernav.model.Career;
import com.careernav.model.Skill;
import com.careernav.model.User;

import java.util.*;

/**
 * Rule-based recommendation engine.
 * score (0-100) = 80 * (skill coverage of the career's required levels) + 20 if the candidate's interests match the career.
 */
public class CareerRecommendationEngine {

    public static class Recommendation {
        private final Career career; private final int score;
        private final List<String> matched = new ArrayList<>(), missing = new ArrayList<>();
        Recommendation(Career career, int score) { this.career = career; this.score = score; }
        public Career getCareer() { return career; }
        public int getScore() { return score; }
        public List<String> getMatchedSkills() { return matched; }
        public List<String> getMissingSkills() { return missing; }
    }

    public List<Recommendation> recommend(User user, Map<Integer, Integer> userLevels, List<Career> careers) {
        Set<String> interests = new HashSet<>();
        if (user.getInterests() != null) {
            for (String t : user.getInterests().toLowerCase().split("[,;\\s]+")) if (t.length() > 2) interests.add(t);
        }
        List<Recommendation> out = new ArrayList<>();
        for (Career c : careers) {
            int total = 0, credit = 0;
            List<String> matched = new ArrayList<>(), missing = new ArrayList<>();
            for (Skill s : c.getSkills()) {
                int cur = userLevels.getOrDefault(s.getSkillId(), 0);
                total += s.getLevel();
                credit += Math.min(cur, s.getLevel());
                (cur >= s.getLevel() ? matched : missing).add(s.getSkillName());
            }
            double skillScore = total == 0 ? 0 : 80.0 * credit / total;
            String text = (c.getCareerName() + " " + c.getCategory() + " " + c.getDescription()).toLowerCase();
            boolean interestHit = false;
            for (String t : interests) if (text.contains(t)) { interestHit = true; break; }
            Recommendation r = new Recommendation(c, (int) Math.round(skillScore + (interestHit ? 20 : 0)));
            r.matched.addAll(matched);
            r.missing.addAll(missing);
            out.add(r);
        }
        out.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
        return out;
    }
}
