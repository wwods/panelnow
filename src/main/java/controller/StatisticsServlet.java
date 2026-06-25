package controller;

import dao.StatisticsDAO;
import util.DBUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/statistics")
public class StatisticsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String 설문IDParam = req.getParameter("설문ID"); // 수정: 파라미터 이름 일치시킴
        System.out.println("DEBUG: 설문ID 파라미터 값 = " + 설문IDParam);

        if (설문IDParam == null || 설문IDParam.isEmpty()) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "설문ID 파라미터가 없습니다.");
            return;
        }

        int 설문ID = Integer.parseInt(설문IDParam);

        try (Connection conn = DBUtil.getConnection()) {
            StatisticsDAO dao = new StatisticsDAO(conn);

            // 설문 제목
            String 제목 = dao.getSurveyTitle(설문ID);
            req.setAttribute("제목", 제목);

            // 객관식 통계
            Map<String, Map<String, Integer>> 통계 = dao.getStatistics(설문ID);
            req.setAttribute("통계", 통계);

            // 주관식 응답
            Map<String, List<String>> 주관식 = dao.getSubjectiveAnswers(설문ID);
            req.setAttribute("주관식", 주관식);

            req.getRequestDispatcher("/survey/stats.jsp").forward(req, res);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
