package com.careernav.controller.auth;

import com.careernav.controller.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/logout")
public class LogoutServlet extends BaseServlet {
    @Override protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession s = req.getSession(false);
        if (s != null) s.invalidate();
        redirect(req, resp, "/index.jsp");
    }
    @Override protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception { get(req, resp); }
}
