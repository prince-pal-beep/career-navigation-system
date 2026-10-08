package com.careernav.controller.auth;

import com.careernav.config.AppConfig;
import com.careernav.controller.BaseServlet;
import com.careernav.dao.UserDAO;
import com.careernav.model.Employer;
import com.careernav.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/register")
public class RegisterServlet extends BaseServlet {

    @Override protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        forward(req, resp, "auth/register");
    }

    @Override protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String role = "EMPLOYER".equals(param(req, "role")) ? "EMPLOYER" : "CANDIDATE";   // ADMIN can never self-register
        String name = param(req, "name"), email = param(req, "email").toLowerCase(), pass = req.getParameter("password");
        String error = null;
        UserDAO dao = new UserDAO();

        if (name.isEmpty() || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) error = "Enter a valid name and email.";
        else if (pass == null || pass.length() < 6) error = "Password must be at least 6 characters.";
        else if (!pass.equals(req.getParameter("confirm"))) error = "Passwords do not match.";
        else if ("EMPLOYER".equals(role) && param(req, "companyName").isEmpty()) error = "Company name is required for employers.";
        else if (dao.emailExists(email)) error = "This email is already registered.";

        if (error != null) {
            req.setAttribute("error", error);
            forward(req, resp, "auth/register");
            return;
        }

        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPassword(AppConfig.hash(pass));
        u.setRole(role);
        u.setQualification(param(req, "qualification"));
        u.setExperience(Math.max(0, intParam(req, "experience", 0)));
        Employer e = null;
        if ("EMPLOYER".equals(role)) {
            e = new Employer();
            e.setCompanyName(param(req, "companyName"));
            e.setContactNo(param(req, "contactNo"));
        }
        dao.register(u, e);
        flash(req, "Registration successful! Please log in.");
        redirect(req, resp, "/login");
    }
}
