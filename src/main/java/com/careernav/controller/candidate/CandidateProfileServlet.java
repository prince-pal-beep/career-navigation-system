package com.careernav.controller.candidate;

import com.careernav.controller.BaseServlet;
import com.careernav.dao.SkillDAO;
import com.careernav.dao.UserDAO;
import com.careernav.model.Skill;
import com.careernav.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/candidate/profile")
public class CandidateProfileServlet extends BaseServlet {

    @Override protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User u = new UserDAO().findById(user(req).getUserId());
        req.setAttribute("profile", u);
        req.setAttribute("skills", new SkillDAO().listAll());
        req.setAttribute("userLevels", new UserDAO().getSkillLevels(u.getUserId()));
        forward(req, resp, "candidate/profile");
    }

    @Override protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User sessionUser = user(req);
        UserDAO dao = new UserDAO();
        User u = new User();
        u.setUserId(sessionUser.getUserId());
        u.setName(param(req, "name").isEmpty() ? sessionUser.getName() : param(req, "name"));
        u.setQualification(param(req, "qualification"));
        u.setExperience(Math.max(0, intParam(req, "experience", 0)));
        u.setInterests(param(req, "interests"));
        dao.updateProfile(u);

        Map<Integer, Integer> levels = new HashMap<>();
        for (Skill s : new SkillDAO().listAll()) {
            int lvl = intParam(req, "skill_" + s.getSkillId(), 0);
            if (lvl >= 1 && lvl <= 5) levels.put(s.getSkillId(), lvl);
        }
        dao.saveSkillLevels(u.getUserId(), levels);

        sessionUser.setName(u.getName());
        sessionUser.setInterests(u.getInterests());
        flash(req, "Profile updated.");
        redirect(req, resp, "/candidate/profile");
    }
}
