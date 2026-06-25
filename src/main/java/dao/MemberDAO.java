package dao;

import java.sql.*;
import model.Member;

public class MemberDAO {
    private Connection conn;

    public MemberDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean register(Member m) throws SQLException {
        String sql = "INSERT INTO 회원 (이메일, 비밀번호, 닉네임) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.get이메일());
            ps.setString(2, m.get비밀번호());
            ps.setString(3, m.get닉네임());
            return ps.executeUpdate() == 1;
        }
    }

    public Member login(String 이메일, String 비밀번호) throws SQLException {
        String sql = "SELECT * FROM 회원 WHERE 이메일 = ? AND 비밀번호 = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, 이메일);
            ps.setString(2, 비밀번호);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Member m = new Member();
                m.set회원ID(rs.getInt("회원ID"));
                m.set이메일(rs.getString("이메일"));
                m.set닉네임(rs.getString("닉네임"));
                m.set관리자여부(rs.getBoolean("관리자여부"));
                m.set포인트(rs.getInt("포인트"));
                return m;
            }
        }
        return null;
    }
    
    public void addPoint(int 회원ID, int 포인트, String 내용) {
        String sql1 = "UPDATE 회원 SET 포인트 = 포인트 + ? WHERE 회원ID = ?";
        String sql2 = "INSERT INTO 포인트이력 (회원ID, 변동포인트, 내용) VALUES (?, ?, ?)";

        try (
            PreparedStatement pstmt1 = conn.prepareStatement(sql1);
            PreparedStatement pstmt2 = conn.prepareStatement(sql2)
        ) {
            conn.setAutoCommit(false);  // 트랜잭션 시작

            pstmt1.setInt(1, 포인트);
            pstmt1.setInt(2, 회원ID);
            pstmt1.executeUpdate();

            pstmt2.setInt(1, 회원ID);
            pstmt2.setInt(2, 포인트);
            pstmt2.setString(3, 내용);
            pstmt2.executeUpdate();

            conn.commit();  // 커밋
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();  // 롤백
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
