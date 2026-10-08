package com.careernav.controller.employer;

import com.careernav.controller.BaseServlet;
import com.careernav.dao.JobDAO;
import com.careernav.dao.SkillDAO;
import com.careernav.model.Employer;
import com.careernav.model.Job;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.Arrays;

@WebServlet("/employer/post-job")
public class PostJobServlet extends BaseServlet {

    @Override protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setAttribute("skills", new SkillDAO().listAll());
        forward(req, resp, "employer/post-job");
    }

    @Override protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Employer e = (Employer) req.getSession().getAttribute("employer");
        String title = param(req, "title");
        if (title.isEmpty()) {
            req.setAttribute("error", "Job title is required.");
            get(req, resp);
            return;
        }
        Job j = new Job();
        j.setEmployerId(e.getEmployerId());
        j.setTitle(title);
        j.setDescription(param(req, "description"));
        j.setLocation(param(req, "location"));
        j.setRequiredExperience(Math.max(0, intParam(req, "requiredExperience", 0)));
        j.setSalaryRange(param(req, "salaryRange"));

        String[] raw = req.getParameterValues("skillIds");
        int[] ids = raw == null ? new int[0] : Arrays.stream(raw).mapToInt(s -> {
            try { return Integer.parseInt(s); } catch (NumberFormatException ex) { return -1; }
        }).filter(i -> i > 0).toArray();

        new JobDAO().post(j, ids);
        flash(req, "Job submitted. It will be visible to candidates after admin approval.");
        redirect(req, resp, "/employer/dashboard");
    }
}
