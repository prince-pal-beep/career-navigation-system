package com.careernav.controller.auth;

import com.careernav.config.AppConfig;
import com.careernav.controller.BaseServlet;
import com.careernav.dao.UserDAO;
import com.careernav.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/** Change password for any logged-in role (candidate, employer, admin). */
@WebServlet("/change-password")
public class ChangePasswordServlet extends BaseServlet {

    @Override protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        forward(req, resp, "auth/change-password");
    }

    @Override protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserDAO dao = new UserDAO();
        User u = dao.findById(user(req).getUserId());
        String cur = req.getParameter("current"), neu = req.getParameter("newPassword");
        if (cur == null || !AppConfig.verify(cur, u.getPassword())) req.setAttribute("error", "Current password is incorrect.");
        else if (neu == null || neu.length() < 6) req.setAttribute("error", "New password must be at least 6 characters.");
        else if (!neu.equals(req.getParameter("confirm"))) req.setAttribute("error", "Passwords do not match.");
        else {
            dao.updatePassword(u.getUserId(), AppConfig.hash(neu));
            flash(req, "Password changed successfully.");
            redirect(req, resp, "/change-password");
            return;
        }
        forward(req, resp, "auth/change-password");
    }
}
