package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Question;

public class QuestionDAO {
    private Connection conn;

    public QuestionDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Question> getQuestionsBySurveyId(int 설문ID) throws SQLException {
        List<Question> list = new ArrayList<>();
        String sql = "SELECT * FROM 질문 WHERE 설문ID = ? ORDER BY 질문ID";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 설문ID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Question q = new Question();
                q.set질문ID(rs.getInt("질문ID"));
                q.set설문ID(rs.getInt("설문ID"));
                q.set질문내용(rs.getString("질문내용"));
                list.add(q);
            }
        }

        return list;
    }
}
