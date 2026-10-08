package com.careernav.controller.candidate;

import com.careernav.controller.BaseServlet;
import com.careernav.dao.CareerDAO;
import com.careernav.dao.UserDAO;
import com.careernav.model.Career;
import com.careernav.service.SkillGapService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/candidate/skill-gap")
public class SkillGapServlet extends BaseServlet {
    @Override protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        CareerDAO cdao = new CareerDAO();
        req.setAttribute("careers", cdao.listAll());
        int careerId = intParam(req, "careerId", 0);
        if (careerId > 0) {
            Career c = cdao.findById(careerId);
            if (c != null) {
                req.setAttribute("selectedCareerId", careerId);
                req.setAttribute("result", new SkillGapService()
                        .analyze(c, new UserDAO().getSkillLevels(user(req).getUserId())));
            }
        }
        forward(req, resp, "candidate/skill-gap");
    }
}
