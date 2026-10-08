package com.careernav.controller.auth;

import com.careernav.config.AppConfig;
import com.careernav.controller.BaseServlet;
import com.careernav.dao.UserDAO;
import com.careernav.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends BaseServlet {

    @Override protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User u = user(req);
        if (u != null) { redirect(req, resp, home(u)); return; }
        forward(req, resp, "auth/login");
    }

    @Override protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String email = param(req, "email").toLowerCase(), pass = req.getParameter("password");
        UserDAO dao = new UserDAO();
        User u = email.isEmpty() || pass == null ? null : dao.findByEmail(email);
        if (u == null || !AppConfig.verify(pass, u.getPassword())) {
            req.setAttribute("error", "Invalid email or password.");
            forward(req, resp, "auth/login");
            return;
        }
        if (!"ACTIVE".equals(u.getStatus())) {
            req.setAttribute("error", "Your account is blocked. Please contact the administrator.");
            forward(req, resp, "auth/login");
            return;
        }
        req.getSession().invalidate();                    // prevent session fixation
        HttpSession session = req.getSession(true);
        u.setPassword(null);                              // never keep the hash in the session
        session.setAttribute("user", u);
        if ("EMPLOYER".equals(u.getRole())) session.setAttribute("employer", dao.findEmployerByUserId(u.getUserId()));
        redirect(req, resp, home(u));
    }

    static String home(User u) {
        switch (u.getRole()) {
            case "ADMIN":    return "/admin/dashboard";
            case "EMPLOYER": return "/employer/dashboard";
            default:         return "/candidate/profile";
        }
    }
}
