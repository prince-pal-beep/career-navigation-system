package com.careernav.controller;

import com.careernav.dao.MessageDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/contact")
public class ContactServlet extends BaseServlet {
    @Override protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String name = param(req, "name"), email = param(req, "email"), msg = param(req, "message");
        if (!name.isEmpty() && !msg.isEmpty()) {
            new MessageDAO().saveContact(name, email, msg);
            flash(req, "Thank you! Your message has been sent to the admin.");
        } else {
            flash(req, "Name and message are required.");
        }
        redirect(req, resp, "/contact.jsp");
    }
    @Override protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception { redirect(req, resp, "/contact.jsp"); }
}
