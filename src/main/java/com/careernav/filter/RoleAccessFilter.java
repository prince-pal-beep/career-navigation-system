package com.careernav.filter;

import com.careernav.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

/** /candidate/* -> CANDIDATE, /employer/* -> EMPLOYER, /admin/* -> ADMIN. */
@WebFilter(urlPatterns = {"/candidate/*", "/employer/*", "/admin/*"})
public class RoleAccessFilter implements Filter {
    @Override
    public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) rq;
        HttpServletResponse resp = (HttpServletResponse) rs;
        HttpSession session = req.getSession(false);
        User u = session == null ? null : (User) session.getAttribute("user");
        if (u == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }

        String path = req.getServletPath();
        String need = path.startsWith("/admin") ? "ADMIN" : path.startsWith("/employer") ? "EMPLOYER" : "CANDIDATE";
        if (!need.equals(u.getRole())) { resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied for your role"); return; }
        chain.doFilter(rq, rs);
    }
}
