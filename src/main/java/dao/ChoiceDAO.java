package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Choice;

public class ChoiceDAO {
    private Connection conn;

    public ChoiceDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Choice> getChoicesByQuestionId(int 질문ID) throws SQLException {
        List<Choice> list = new ArrayList<>();
        String sql = "SELECT * FROM 보기 WHERE 질문ID = ? ORDER BY 보기ID";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 질문ID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Choice c = new Choice();
                c.set보기ID(rs.getInt("보기ID"));
                c.set질문ID(rs.getInt("질문ID"));
                c.set내용(rs.getString("보기내용")); // ✅ 보기내용
                list.add(c);
            }
        }

        return list;
    }
}
