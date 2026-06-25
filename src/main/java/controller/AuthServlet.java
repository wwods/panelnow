package controller;

import dao.MemberDAO;
import model.Member;
import util.DBUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            // ✅ H2 드라이버 명시적으로 로드
        	Connection conn = DBUtil.getConnection();

            MemberDAO dao = new MemberDAO(conn);
            Member user = dao.login(email, password);

            if (user != null) {
                req.getSession().setAttribute("user", user);
                res.sendRedirect("../index.jsp");
            } else {
                res.sendRedirect("login.jsp?error=1");
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // ✅ 로그아웃 처리 (GET 방식)
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.getSession().invalidate();
        res.sendRedirect(req.getContextPath() + "/auth/login.jsp");
    }
}
