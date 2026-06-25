package dao;

import java.sql.*;
import java.util.*;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    // 설문 응답 목록
    public List<Map<String, Object>> getUserResponses(int 회원ID) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = """
            SELECT s.제목 AS 설문제목, q.질문내용, 
                   COALESCE(b.보기내용, d.주관식답변) AS 응답
            FROM 응답 r
            JOIN 응답상세 d ON r.응답ID = d.응답ID
            JOIN 질문 q ON d.질문ID = q.질문ID
            JOIN 설문 s ON r.설문ID = s.설문ID
            LEFT JOIN 보기 b ON d.보기ID = b.보기ID
            WHERE r.회원ID = ?
            ORDER BY r.제출시간 DESC
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, 회원ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("설문제목", rs.getString("설문제목"));
                row.put("질문내용", rs.getString("질문내용"));
                row.put("응답", rs.getString("응답"));
                list.add(row);
            }
        }
        return list;
    }

    // ✅ 포인트 이력 조회
    public List<Map<String, Object>> getPointHistory(int 회원ID) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT 변동포인트, 내용, 변동일시 FROM 포인트이력 WHERE 회원ID = ? ORDER BY 변동일시 DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, 회원ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("포인트", rs.getInt("변동포인트"));
                row.put("내용", rs.getString("내용"));
                row.put("날짜", rs.getString("변동일시"));
                list.add(row);
            }
        }
        return list;
    }

    // ✅ 구매 내역 조회
    public List<Map<String, Object>> getPurchaseHistory(int 회원ID) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = """
            SELECT p.상품명, p.가격, g.구매일
            FROM 구매 g
            JOIN 상품 p ON g.상품ID = p.상품ID
            WHERE g.회원ID = ?
            ORDER BY g.구매일 DESC
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, 회원ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("상품명", rs.getString("상품명"));
                row.put("가격", rs.getInt("가격"));
                row.put("구매일", rs.getString("구매일"));
                list.add(row);
            }
        }
        return list;
    }
}
