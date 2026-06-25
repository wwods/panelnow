package controller;

import dao.MemberDAO;
import dao.ResponseDAO;
import model.Member;
import util.DBUtil; // DB 연결을 위한 유틸리티 (필요 시 수정)

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@WebServlet("/submitResponse")
public class SubmitResponseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Member user = (Member) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int 회원ID = user.get회원ID();
        int 설문ID = Integer.parseInt(request.getParameter("설문ID"));

        // 질문ID → 답변내용 저장용 Map
        Map<Integer, String> answers = new HashMap<>();

        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if (name.startsWith("question_")) {
                String str질문ID = name.substring("question_".length());
                try {
                    int 질문ID = Integer.parseInt(str질문ID);
                    String 답변 = request.getParameter(name);
                    answers.put(질문ID, 답변);
                } catch (NumberFormatException ignored) {}
            }
        }

        // 응답 저장
        ResponseDAO dao = new ResponseDAO();
        dao.saveResponse(설문ID, 회원ID, answers);

        // ✅ 포인트 지급 처리
        try (Connection conn = DBUtil.getConnection()) {
            MemberDAO memberDAO = new MemberDAO(conn);
            memberDAO.addPoint(회원ID, 10, "설문 응답 보상");
            
            try (PreparedStatement ps = conn.prepareStatement("SELECT 포인트 FROM 회원 WHERE 회원ID = ?")) {
                ps.setInt(1, 회원ID);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int 최신포인트 = rs.getInt("포인트");
                    user.set포인트(최신포인트);  // 기존 세션 사용자에 최신 포인트 설정
                    session.setAttribute("user", user);  // 세션 갱신
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        


        // 완료 후 리다이렉트
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}
