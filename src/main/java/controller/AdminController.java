package controller;

import util.DBUtil;
import model.Member;
import model.Product;
import model.Survey;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin")
public class AdminController extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // 로그인한 사용자 정보 확인
        Member user = (Member) req.getSession().getAttribute("user");
        if (user == null || !user.is관리자여부()) {
            res.sendRedirect("auth/login.jsp");
            return;
        }

        String action = req.getParameter("action");

        try (Connection conn = DBUtil.getConnection()) {

            // 설문 삭제 처리
            if ("deleteSurvey".equals(action)) {
                int 설문ID = Integer.parseInt(req.getParameter("설문ID"));
                PreparedStatement ps = conn.prepareStatement("DELETE FROM 설문 WHERE 설문ID = ?");
                ps.setInt(1, 설문ID);
                ps.executeUpdate();
            }

            // 상품 삭제 처리
            if ("deleteProduct".equals(action)) {
                int 상품ID = Integer.parseInt(req.getParameter("상품ID"));
                PreparedStatement ps = conn.prepareStatement("DELETE FROM 상품 WHERE 상품ID = ?");
                ps.setInt(1, 상품ID);
                ps.executeUpdate();
            }

            // 설문 목록 조회
            List<Survey> surveys = new ArrayList<>();
            PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM 설문 ORDER BY 설문ID DESC");
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                Survey s = new Survey();
                s.set설문ID(rs1.getInt("설문ID"));
                s.set제목(rs1.getString("제목"));
                s.set설명(rs1.getString("설명"));
                surveys.add(s);
            }

            // 상품 목록 조회
            List<Product> products = new ArrayList<>();
            PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM 상품 ORDER BY 상품ID DESC");
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                Product p = new Product();
                p.set상품ID(rs2.getInt("상품ID"));
                p.set상품명(rs2.getString("상품명"));
                p.set설명(rs2.getString("설명"));
                p.set가격(rs2.getInt("가격"));
                products.add(p);
            }

            // JSP에 전달
            req.setAttribute("surveys", surveys);
            req.setAttribute("products", products);

            // forward
            RequestDispatcher dispatcher = req.getRequestDispatcher("/survey/manage.jsp");
            dispatcher.forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            res.sendError(500, "관리자 페이지 오류 발생");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        Member user = (Member) req.getSession().getAttribute("user");
        if (user == null || !user.is관리자여부()) {
            res.sendRedirect(req.getContextPath() + "/auth/login.jsp");
            return;
        }

        String action = req.getParameter("action");

        try (Connection conn = DBUtil.getConnection()) {

            if ("addProduct".equals(action)) {
                String 상품명 = req.getParameter("상품명");
                String 설명 = req.getParameter("설명");
                int 가격 = Integer.parseInt(req.getParameter("가격"));

                PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO 상품 (상품명, 설명, 가격) VALUES (?, ?, ?)");
                ps.setString(1, 상품명);
                ps.setString(2, 설명);
                ps.setInt(3, 가격);
                ps.executeUpdate();

                res.sendRedirect(req.getContextPath() + "/admin");
                return;
            }

            if ("addSurvey".equals(action)) {
                String 제목 = req.getParameter("제목");
                String 설명 = req.getParameter("설명");
                String 질문내용 = req.getParameter("질문내용");
                String 응답유형 = req.getParameter("응답유형");
                String[] 보기내용들 = req.getParameterValues("보기내용");

                // 설문 INSERT
                PreparedStatement ps1 = conn.prepareStatement(
                    "INSERT INTO 설문 (제목, 설명, 작성자ID) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
                ps1.setString(1, 제목);
                ps1.setString(2, 설명);
                ps1.setInt(3, user.get회원ID());
                ps1.executeUpdate();

                ResultSet rs1 = ps1.getGeneratedKeys();
                int 설문ID = 0;
                if (rs1.next()) {
                    설문ID = rs1.getInt(1);
                }

                // 질문 INSERT
                PreparedStatement ps2 = conn.prepareStatement(
                    "INSERT INTO 질문 (설문ID, 질문내용) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
                ps2.setInt(1, 설문ID);
                ps2.setString(2, 질문내용);
                ps2.executeUpdate();

                ResultSet rs2 = ps2.getGeneratedKeys();
                int 질문ID = 0;
                if (rs2.next()) {
                    질문ID = rs2.getInt(1);
                }

                // 보기 INSERT (객관식인 경우만)
                if (보기내용들 != null) {
                    PreparedStatement ps3 = conn.prepareStatement(
                        "INSERT INTO 보기 (질문ID, 보기내용) VALUES (?, ?)");
                    for (String 보기 : 보기내용들) {
                        ps3.setInt(1, 질문ID);
                        ps3.setString(2, 보기);
                        ps3.addBatch();
                    }
                    ps3.executeBatch();
                }

                res.sendRedirect(req.getContextPath() + "/admin");
                return;
            }

            // 기본은 doGet 처리
            doGet(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            res.sendError(500, "등록 처리 중 오류 발생");
        }
    }
}
