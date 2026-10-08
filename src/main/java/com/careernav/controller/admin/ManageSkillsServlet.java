package com.careernav.controller.admin;

import com.careernav.controller.BaseServlet;
import com.careernav.dao.SkillDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet("/admin/skills")
public class ManageSkillsServlet extends BaseServlet {

    @Override protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setAttribute("skills", new SkillDAO().listAll());
        forward(req, resp, "admin/manage-skills");
    }

    @Override protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        SkillDAO dao = new SkillDAO();
        if ("delete".equals(param(req, "action"))) {
            dao.delete(intParam(req, "skillId", 0));
            flash(req, "Skill deleted.");
        } else if (!param(req, "skillName").isEmpty()) {
            try {
                dao.add(param(req, "skillName"), "Soft".equals(param(req, "skillType")) ? "Soft" : "Technical");
                flash(req, "Skill added.");
            } catch (SQLIntegrityConstraintViolationException dup) {
                flash(req, "That skill already exists.");
            }
        }
        redirect(req, resp, "/admin/skills");
    }
}
