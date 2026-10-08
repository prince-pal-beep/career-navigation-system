package com.careernav.controller.employer;

import com.careernav.controller.BaseServlet;
import com.careernav.dao.JobDAO;
import com.careernav.model.Employer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/employer/dashboard")
public class EmployerDashboardServlet extends BaseServlet {
    @Override protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Employer e = (Employer) req.getSession().getAttribute("employer");
        req.setAttribute("jobs", new JobDAO().listByEmployer(e.getEmployerId()));
        forward(req, resp, "employer/dashboard");
    }
}
