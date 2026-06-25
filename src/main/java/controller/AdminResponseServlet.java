package controller;

import dao.AdminDAO;
import util.DBUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/adminResponse")
public class AdminResponseServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int 설문ID = Integer.parseInt(req.getParameter("id"));

        try (Connection conn = DBUtil.getConnection();) {
            AdminDAO dao = new AdminDAO(conn);

            // 전체 응답 내역 가져오기
            List<Map<String, String>> 응답목록 = dao.getSurveyResponses(설문ID);

            req.setAttribute("설문ID", 설문ID);
            req.setAttribute("응답목록", 응답목록);
            req.getRequestDispatcher("/survey/responses.jsp").forward(req, res);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
