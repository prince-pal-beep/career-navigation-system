package com.careernav.controller.candidate;

import com.careernav.controller.BaseServlet;
import com.careernav.dao.CareerDAO;
import com.careernav.dao.UserDAO;
import com.careernav.model.User;
import com.careernav.service.CareerRecommendationEngine;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.Map;

@WebServlet("/candidate/recommendations")
public class CareerRecommendationServlet extends BaseServlet {
    @Override protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserDAO udao = new UserDAO();
        User u = udao.findById(user(req).getUserId());
        Map<Integer, Integer> levels = udao.getSkillLevels(u.getUserId());
        req.setAttribute("hasSkills", !levels.isEmpty());
        req.setAttribute("recommendations",
                new CareerRecommendationEngine().recommend(u, levels, new CareerDAO().listAll()));
        forward(req, resp, "candidate/recommendations");
    }
}
