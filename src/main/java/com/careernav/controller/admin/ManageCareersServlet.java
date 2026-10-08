package com.careernav.controller.admin;

import com.careernav.controller.BaseServlet;
import com.careernav.dao.CareerDAO;
import com.careernav.dao.SkillDAO;
import com.careernav.model.Career;
import com.careernav.model.Skill;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/admin/careers")
public class ManageCareersServlet extends BaseServlet {

    @Override protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setAttribute("careers", new CareerDAO().listAll());
        req.setAttribute("skills", new SkillDAO().listAll());
        forward(req, resp, "admin/manage-careers");
    }

    @Override protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        CareerDAO dao = new CareerDAO();
        if ("delete".equals(param(req, "action"))) {
            dao.delete(intParam(req, "careerId", 0));
            flash(req, "Career deleted.");
        } else if (!param(req, "careerName").isEmpty()) {
            Career c = new Career();
            c.setCareerName(param(req, "careerName"));
            c.setDescription(param(req, "description"));
            c.setCategory(param(req, "category"));
            // importance_<skillId> = 0 (not required) .. 5
            Map<Integer, Integer> imp = new LinkedHashMap<>();
            for (Skill s : new SkillDAO().listAll()) {
                int v = intParam(req, "imp_" + s.getSkillId(), 0);
                if (v >= 1 && v <= 5) imp.put(s.getSkillId(), v);
            }
            dao.add(c, imp);
            flash(req, "Career added.");
        }
        redirect(req, resp, "/admin/careers");
    }
}
