package dao;

import util.DBUtil;

import java.sql.*;
import java.util.Map;

public class ResponseDAO {

    public void saveResponse(int 설문ID, int 회원ID, Map<Integer, String> answers) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();

            // 1. 응답 테이블 저장
            String insertResponse = "INSERT INTO 응답 (설문ID, 회원ID) VALUES (?, ?)";
            pstmt = conn.prepareStatement(insertResponse, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, 설문ID);
            pstmt.setInt(2, 회원ID);
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            int 응답ID = 0;
            if (rs.next()) {
                응답ID = rs.getInt(1);
            }
            pstmt.close();

            // 2. 응답상세 저장 (객관식 or 주관식 자동 분기)
            for (Map.Entry<Integer, String> entry : answers.entrySet()) {
                int 질문ID = entry.getKey();
                String value = entry.getValue();

                if (value.matches("\\d+")) {
                    // 보기ID가 숫자인 경우 (객관식)
                    pstmt = conn.prepareStatement("INSERT INTO 응답상세 (응답ID, 질문ID, 보기ID) VALUES (?, ?, ?)");
                    pstmt.setInt(1, 응답ID);
                    pstmt.setInt(2, 질문ID);
                    pstmt.setInt(3, Integer.parseInt(value));
                } else {
                    // 주관식 텍스트 응답
                    pstmt = conn.prepareStatement("INSERT INTO 응답상세 (응답ID, 질문ID, 주관식답변) VALUES (?, ?, ?)");
                    pstmt.setInt(1, 응답ID);
                    pstmt.setInt(2, 질문ID);
                    pstmt.setString(3, value);
                }

                pstmt.executeUpdate();
                pstmt.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
    }
}
