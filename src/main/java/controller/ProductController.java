package controller;

import dao.ProductDAO;
import model.Member;
import model.Product;
import util.DBUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/product")
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("📥 [doGet] 호출됨");

        try (Connection conn = DBUtil.getConnection()) {
            ProductDAO dao = new ProductDAO(conn);
            List<Product> list = dao.getAll();

            req.setAttribute("products", list);
            req.getRequestDispatcher("/product/list.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("상품 목록 조회 중 오류 발생", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("✅ [doPost] 호출됨");
        req.setCharacterEncoding("UTF-8");

        Member user = (Member) req.getSession().getAttribute("user");
        if (user == null) {
            res.sendRedirect(req.getContextPath() + "/auth/login.jsp");
            return;
        }

        String 상품IDStr = req.getParameter("상품ID");
        System.out.println("🆔 상품ID 파라미터: " + 상품IDStr);

        if (상품IDStr == null || 상품IDStr.isEmpty()) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "상품ID가 전달되지 않았습니다.");
            return;
        }

        int 상품ID;
        try {
            상품ID = Integer.parseInt(상품IDStr);
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "상품ID가 올바른 숫자가 아닙니다.");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            ProductDAO dao = new ProductDAO(conn);
            Product 상품 = dao.getById(상품ID);

            if (상품 == null) {
                res.sendError(HttpServletResponse.SC_NOT_FOUND, "해당 상품이 존재하지 않습니다.");
                return;
            }

            boolean success = dao.purchase(user.get회원ID(), 상품);
            System.out.println("💸 구매 결과: " + (success ? "성공" : "실패"));

            if (success) {
                // ✅ 갱신된 포인트를 세션에 반영
                try (PreparedStatement ps = conn.prepareStatement("SELECT 포인트 FROM 회원 WHERE 회원ID = ?")) {
                    ps.setInt(1, user.get회원ID());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        int 최신포인트 = rs.getInt("포인트");
                        user.set포인트(최신포인트);
                        req.getSession().setAttribute("user", user);
                    }
                }
            }

            res.sendRedirect(req.getContextPath() + "/index.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("상품 구매 처리 중 오류 발생", e);
        }
    }
}
