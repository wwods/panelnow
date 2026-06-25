package controller;

import dao.MemberDAO;
import model.Member;
import util.DBUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String nickname = req.getParameter("nickname");

        try (Connection conn = DBUtil.getConnection();) {
            MemberDAO dao = new MemberDAO(conn);
            Member m = new Member();
            m.set이메일(email);
            m.set비밀번호(password);
            m.set닉네임(nickname);

            boolean success = dao.register(m);
            res.sendRedirect(success ? "/auth/login.jsp" : "register.jsp?error=1");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
