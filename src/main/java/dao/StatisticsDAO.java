package dao;

import java.sql.*;
import java.util.*;

public class StatisticsDAO {
    private Connection conn;

    public StatisticsDAO(Connection conn) {
        this.conn = conn;
    }

    // 설문 제목 가져오기
    public String getSurveyTitle(int 설문ID) throws SQLException {
        String sql = "SELECT 제목 FROM 설문 WHERE 설문ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, 설문ID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("제목");
            }
        }
        return "";
    }

    // 객관식 질문별 보기 응답 수 통계
    public Map<String, Map<String, Integer>> getStatistics(int 설문ID) throws SQLException {
        Map<String, Map<String, Integer>> result = new LinkedHashMap<>();

        String sql = """
            SELECT q.질문내용, b.보기내용, COUNT(*) AS 응답수
            FROM 응답상세 d
            JOIN 질문 q ON d.질문ID = q.질문ID
            JOIN 보기 b ON d.보기ID = b.보기ID
            WHERE q.설문ID = ?
            GROUP BY q.질문내용, b.보기내용
            ORDER BY q.질문내용, b.보기내용
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, 설문ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String 질문 = rs.getString("질문내용");
                String 보기 = rs.getString("보기내용");
                int count = rs.getInt("응답수");

                result.computeIfAbsent(질문, k -> new LinkedHashMap<>())
                      .put(보기, count);
            }
        }

        return result;
    }

    // 주관식 질문별 모든 응답 가져오기
    public Map<String, List<String>> getSubjectiveAnswers(int 설문ID) throws SQLException {
        Map<String, List<String>> result = new LinkedHashMap<>();

        String sql = """
            SELECT q.질문내용, d.주관식답변
            FROM 응답상세 d
            JOIN 질문 q ON d.질문ID = q.질문ID
            WHERE q.설문ID = ?
            ORDER BY q.질문내용
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, 설문ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String 질문 = rs.getString("질문내용");
                String 답변 = rs.getString("주관식답변");

                result.computeIfAbsent(질문, k -> new ArrayList<>())
                      .add(답변);
            }
        }

        return result;
    }
}
