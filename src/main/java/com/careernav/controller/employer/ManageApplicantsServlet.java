package com.careernav.controller.employer;

import com.careernav.controller.BaseServlet;
import com.careernav.dao.ApplicationDAO;
import com.careernav.dao.JobDAO;
import com.careernav.model.Employer;
import com.careernav.model.Job;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.Arrays;
import java.util.List;

@WebServlet("/employer/applicants")
public class ManageApplicantsServlet extends BaseServlet {

    private static final List<String> STATUSES = Arrays.asList("APPLIED", "SHORTLISTED", "SELECTED", "REJECTED");

    @Override protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Employer e = (Employer) req.getSession().getAttribute("employer");
        int jobId = intParam(req, "jobId", 0);
        Job job = new JobDAO().findById(jobId);
        if (job == null || job.getEmployerId() != e.getEmployerId()) { resp.sendError(404); return; }
        req.setAttribute("job", job);
        req.setAttribute("applicants", new ApplicationDAO().listByJob(jobId, e.getEmployerId()));
        req.setAttribute("statuses", STATUSES);
        forward(req, resp, "employer/applicants");
    }

    @Override protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Employer e = (Employer) req.getSession().getAttribute("employer");
        String status = param(req, "status");
        if (STATUSES.contains(status)) {
            new ApplicationDAO().updateStatus(intParam(req, "applicationId", 0), e.getEmployerId(), status);
            flash(req, "Applicant status updated.");
        }
        redirect(req, resp, "/employer/applicants?jobId=" + intParam(req, "jobId", 0));
    }
}
