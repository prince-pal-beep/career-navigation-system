package com.careernav.controller;

import com.careernav.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/** Shared helpers: unified exception handling, view forwarding, flash messages, parameter parsing. */
public abstract class BaseServlet extends HttpServlet {

    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception { resp.sendError(405); }
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception { resp.sendError(405); }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try { get(req, resp); } catch (ServletException | IOException e) { throw e; } catch (Exception e) { throw new ServletException(e); }
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try { post(req, resp); } catch (ServletException | IOException e) { throw e; } catch (Exception e) { throw new ServletException(e); }
    }

    protected void forward(HttpServletRequest req, HttpServletResponse resp, String view) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/" + view + ".jsp").forward(req, resp);
    }

    protected void redirect(HttpServletRequest req, HttpServletResponse resp, String path) throws IOException {
        resp.sendRedirect(req.getContextPath() + path);
    }

    protected User user(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return s == null ? null : (User) s.getAttribute("user");
    }

    protected void flash(HttpServletRequest req, String msg) { req.getSession().setAttribute("flash", msg); }

    protected String param(HttpServletRequest req, String name) {
        String v = req.getParameter(name);
        return v == null ? "" : v.trim();
    }

    protected int intParam(HttpServletRequest req, String name, int def) {
        try { return Integer.parseInt(param(req, name)); } catch (NumberFormatException e) { return def; }
    }
}
