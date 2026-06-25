package dao;

import java.sql.*;
import java.util.*;

public class AdminDAO {
    private Connection conn;

    public AdminDAO(Connection conn) {
        this.conn = conn;
    }

    // 설문 등록 기능
    public void insertSurvey(int 작성자ID, String 제목, String 설명, String 질문내용, String 응답유형, String[] 보기내용들) throws SQLException {
        String insertSurvey = "INSERT INTO 설문 (제목, 설명, 작성자ID) VALUES (?, ?, ?)";
        String insertQuestion = "INSERT INTO 질문 (설문ID, 질문내용, 유형) VALUES (?, ?, ?)";
        String insertChoice = "INSERT INTO 보기 (질문ID, 보기내용) VALUES (?, ?)";

        conn.setAutoCommit(false);
        try (
            PreparedStatement ps1 = conn.prepareStatement(insertSurvey, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement ps2 = conn.prepareStatement(insertQuestion, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps1.setString(1, 제목);
            ps1.setString(2, 설명);
            ps1.setInt(3, 작성자ID);
            ps1.executeUpdate();
            ResultSet rs1 = ps1.getGeneratedKeys();
            rs1.next();
            int 설문ID = rs1.getInt(1);

            ps2.setInt(1, 설문ID);
            ps2.setString(2, 질문내용);
            ps2.setString(3, 응답유형);
            ps2.executeUpdate();
            ResultSet rs2 = ps2.getGeneratedKeys();
            rs2.next();
            int 질문ID = rs2.getInt(1);

            if ("객관식".equals(응답유형) && 보기내용들 != null) {
                try (PreparedStatement ps3 = conn.prepareStatement(insertChoice)) {
                    for (String 보기 : 보기내용들) {
                        if (보기 != null && !보기.trim().isEmpty()) {
                            ps3.setInt(1, 질문ID);
                            ps3.setString(2, 보기);
                            ps3.addBatch();
                        }
                    }
                    ps3.executeBatch();
                }
            }

            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // ✅ 설문 응답 조회 (LEFT JOIN 으로 수정)
    public List<Map<String, String>> getSurveyResponses(int 설문ID) throws SQLException {
        List<Map<String, String>> list = new ArrayList<>();

        String sql = """
            SELECT m.닉네임, r.제출시간, q.질문내용, 
                   COALESCE(b.보기내용, d.주관식답변) AS 응답내용
            FROM 응답 r
            JOIN 응답상세 d ON r.응답ID = d.응답ID
            LEFT JOIN 질문 q ON d.질문ID = q.질문ID
            LEFT JOIN 보기 b ON d.보기ID = b.보기ID
            LEFT JOIN 회원 m ON r.회원ID = m.회원ID
            WHERE r.설문ID = ?
            ORDER BY r.제출시간, q.질문내용
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, 설문ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                row.put("닉네임", rs.getString("닉네임"));
                row.put("제출시간", rs.getString("제출시간"));
                row.put("질문", rs.getString("질문내용"));
                row.put("응답", rs.getString("응답내용"));
                list.add(row);
            }
        }

        return list;
    }
}
