package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Survey;

public class SurveyDAO {
    private Connection conn;

    public SurveyDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Survey> getAllSurveys() throws SQLException {
        List<Survey> list = new ArrayList<>();
        String sql = "SELECT * FROM 설문 ORDER BY 설문ID DESC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Survey s = new Survey();
                s.set설문ID(rs.getInt("설문ID"));
                s.set제목(rs.getString("제목"));
                s.set설명(rs.getString("설명"));
                list.add(s);
            }
        }
        return list;
    }

    public Survey getSurveyById(int 설문ID) throws SQLException {
        String sql = "SELECT * FROM 설문 WHERE 설문ID = ?";
        Survey survey = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 설문ID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                survey = new Survey();
                survey.set설문ID(rs.getInt("설문ID"));
                survey.set제목(rs.getString("제목"));
                survey.set설명(rs.getString("설명"));
            }
        }
        return survey;
    }

    public void insertSurvey(Survey survey) throws SQLException {
        String sql = "INSERT INTO 설문 (제목, 설명) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, survey.get제목());
            pstmt.setString(2, survey.get설명());
            pstmt.executeUpdate();
        }
    }

    public void updateSurvey(Survey survey) throws SQLException {
        String sql = "UPDATE 설문 SET 제목 = ?, 설명 = ? WHERE 설문ID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, survey.get제목());
            pstmt.setString(2, survey.get설명());
            pstmt.setInt(3, survey.get설문ID());
            pstmt.executeUpdate();
        }
    }

    public void deleteSurvey(int 설문ID) throws SQLException {
        String sql = "DELETE FROM 설문 WHERE 설문ID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 설문ID);
            pstmt.executeUpdate();
        }
    }
}
