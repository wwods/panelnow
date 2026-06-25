package controller;

import dao.ProductDAO;
import model.Member;
import model.Product;
import util.DBUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/userAction")
public class UserActionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "mypage";

        HttpSession session = req.getSession();
        Member user = (Member) session.getAttribute("user");

        if (user == null) {
            res.sendRedirect(req.getContextPath() + "/auth/login.jsp");
            return;
        }

        if ("mypage".equals(action)) {
            try (Connection conn = DBUtil.getConnection()) {
                ProductDAO dao = new ProductDAO(conn);
                List<Product> purchased = dao.getPurchasedByMember(user.get회원ID());
                req.setAttribute("purchased", purchased);
            } catch (Exception e) {
                throw new ServletException("구매 내역 조회 중 오류 발생", e);
            }

            req.getRequestDispatcher("/user/mypage.jsp").forward(req, res);
        } else {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "지원하지 않는 작업입니다.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res); // 대부분의 작업은 GET으로 처리
    }
}
