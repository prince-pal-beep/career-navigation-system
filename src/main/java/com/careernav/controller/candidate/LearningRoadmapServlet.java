package com.careernav.controller.candidate;

import com.careernav.controller.BaseServlet;
import com.careernav.dao.CareerDAO;
import com.careernav.dao.UserDAO;
import com.careernav.model.Career;
import com.careernav.service.SkillGapService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/candidate/roadmap")
public class LearningRoadmapServlet extends BaseServlet {
    @Override protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        CareerDAO cdao = new CareerDAO();
        req.setAttribute("careers", cdao.listAll());
        int careerId = intParam(req, "careerId", 0);
        if (careerId > 0) {
            Career c = cdao.findById(careerId);
            if (c != null) {
                SkillGapService svc = new SkillGapService();
                SkillGapService.Result r = svc.analyze(c, new UserDAO().getSkillLevels(user(req).getUserId()));
                req.setAttribute("selectedCareerId", careerId);
                req.setAttribute("result", r);
                req.setAttribute("steps", svc.buildRoadmap(r, cdao.getResources(careerId)));
            }
        }
        forward(req, resp, "candidate/roadmap");
    }
}
