package com.careernav.controller.candidate;

import com.careernav.controller.BaseServlet;
import com.careernav.dao.ApplicationDAO;
import com.careernav.dao.JobDAO;
import com.careernav.dao.UserDAO;
import com.careernav.model.Job;
import com.careernav.model.Skill;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.List;
import java.util.Map;

/** GET: search/filter jobs with a profile-match score + my applications. POST: apply for a job. */
@WebServlet("/candidate/jobs")
public class JobApplyServlet extends BaseServlet {

    @Override protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int uid = user(req).getUserId();
        String q = param(req, "q"), loc = param(req, "loc");
        Map<Integer, Integer> levels = new UserDAO().getSkillLevels(uid);

        List<Job> jobs = new JobDAO().listApproved(q, loc);
        for (Job j : jobs) {
            int have = 0;
            for (Skill s : j.getSkills()) if (levels.containsKey(s.getSkillId())) have++;
            j.setMatchScore(j.getSkills().isEmpty() ? 0 : Math.round(100f * have / j.getSkills().size()));
        }
        jobs.sort((a, b) -> Integer.compare(b.getMatchScore(), a.getMatchScore()));

        req.setAttribute("jobs", jobs);
        req.setAttribute("q", q);
        req.setAttribute("loc", loc);
        req.setAttribute("applications", new ApplicationDAO().listByUser(uid));
        forward(req, resp, "candidate/job-search");
    }

    @Override protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int jobId = intParam(req, "jobId", 0);
        boolean ok = new ApplicationDAO().apply(user(req).getUserId(), jobId);
        flash(req, ok ? "Application submitted." : "You have already applied for this job (or it is no longer open).");
        redirect(req, resp, "/candidate/jobs");
    }
}
