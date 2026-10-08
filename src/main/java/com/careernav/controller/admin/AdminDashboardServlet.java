package com.careernav.controller.admin;

import com.careernav.controller.BaseServlet;
import com.careernav.dao.JobDAO;
import com.careernav.dao.UserDAO;
import com.careernav.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.Arrays;
import java.util.List;

/** Dashboard (counts, job moderation, user management). ?view=reports shows the reports page. */
@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends BaseServlet {

    @Override protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserDAO udao = new UserDAO();
        req.setAttribute("userCount", udao.count("users"));
        req.setAttribute("careerCount", udao.count("careers"));
        req.setAttribute("skillCount", udao.count("skills"));
        req.setAttribute("jobCount", udao.count("jobs"));
        req.setAttribute("applicationCount", udao.count("applications"));

        if ("reports".equals(param(req, "view"))) {
            req.setAttribute("usersByRole", udao.countGrouped("users", "role"));
            req.setAttribute("jobsByStatus", udao.countGrouped("jobs", "status"));
            req.setAttribute("applicationsByStatus", udao.countGrouped("applications", "status"));
            forward(req, resp, "admin/reports");
            return;
        }
        req.setAttribute("jobs", new JobDAO().listAll());
        req.setAttribute("users", udao.listAll());
        forward(req, resp, "admin/dashboard");
    }

    @Override protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String action = param(req, "action");
        List<String> jobStatuses = Arrays.asList("PENDING", "APPROVED", "REJECTED");
        switch (action) {
            case "jobStatus":
                if (jobStatuses.contains(param(req, "status")))
                    new JobDAO().setStatus(intParam(req, "jobId", 0), param(req, "status"));
                flash(req, "Job updated.");
                break;
            case "deleteJob":
                new JobDAO().delete(intParam(req, "jobId", 0));
                flash(req, "Job deleted.");
                break;
            case "userStatus":
                String st = "BLOCKED".equals(param(req, "status")) ? "BLOCKED" : "ACTIVE";
                new UserDAO().setStatus(intParam(req, "userId", 0), st);
                flash(req, "User status updated.");
                break;
            case "deleteUser":
                User me = user(req);
                if (intParam(req, "userId", 0) != me.getUserId()) new UserDAO().delete(intParam(req, "userId", 0));
                flash(req, "User deleted.");
                break;
            default:
                break;
        }
        redirect(req, resp, "/admin/dashboard");
    }
}
