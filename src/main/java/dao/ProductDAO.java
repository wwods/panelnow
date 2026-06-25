package dao;

import model.Product;
import java.sql.*;
import java.util.*;

public class ProductDAO {
    private Connection conn;

    public ProductDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Product> getAll() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM 상품";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product p = new Product();
                p.set상품ID(rs.getInt("상품ID"));
                p.set상품명(rs.getString("상품명"));
                p.set설명(rs.getString("설명"));
                p.set가격(rs.getInt("가격"));
                list.add(p);
            }
        }
        return list;
    }

    public Product getById(int id) throws SQLException {
        String sql = "SELECT * FROM 상품 WHERE 상품ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Product p = new Product();
                p.set상품ID(rs.getInt("상품ID"));
                p.set상품명(rs.getString("상품명"));
                p.set설명(rs.getString("설명"));
                p.set가격(rs.getInt("가격"));
                return p;
            }
        }
        return null;
    }

    public boolean purchase(int 회원ID, Product 상품) throws SQLException {
        String checkPoint = "SELECT 포인트 FROM 회원 WHERE 회원ID = ?";
        String updatePoint = "UPDATE 회원 SET 포인트 = 포인트 - ? WHERE 회원ID = ?";
        String insertPurchase = "INSERT INTO 구매 (회원ID, 상품ID) VALUES (?, ?)";
        String insertPointLog = "INSERT INTO 포인트이력 (회원ID, 변동포인트, 내용) VALUES (?, ?, ?)";

        conn.setAutoCommit(false);
        try (
            PreparedStatement ps1 = conn.prepareStatement(checkPoint);
            PreparedStatement ps2 = conn.prepareStatement(updatePoint);
            PreparedStatement ps3 = conn.prepareStatement(insertPurchase);
            PreparedStatement ps4 = conn.prepareStatement(insertPointLog)
        ) {
            ps1.setInt(1, 회원ID);
            ResultSet rs = ps1.executeQuery();
            if (rs.next()) {
                int point = rs.getInt(1);
                if (point < 상품.get가격()) {
                    conn.rollback();
                    return false; // 포인트 부족
                }
            }

            ps2.setInt(1, 상품.get가격());
            ps2.setInt(2, 회원ID);
            ps2.executeUpdate();

            ps3.setInt(1, 회원ID);
            ps3.setInt(2, 상품.get상품ID());
            ps3.executeUpdate();

            ps4.setInt(1, 회원ID);
            ps4.setInt(2, -상품.get가격());
            ps4.setString(3, 상품.get상품명() + " 구매");
            ps4.executeUpdate();

            conn.commit();
            return true;
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
    
    public List<Product> getPurchasedByMember(int 회원ID) throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.* FROM 상품 p JOIN 구매 g ON p.상품ID = g.상품ID WHERE g.회원ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, 회원ID);
            ResultSet rs = ps.executeQuery();
            System.out.println("🧾 구매 내역 개수: " + list.size());
            while (rs.next()) {
                Product p = new Product();
                p.set상품ID(rs.getInt("상품ID"));
                p.set상품명(rs.getString("상품명"));
                p.set설명(rs.getString("설명"));
                p.set가격(rs.getInt("가격"));
                list.add(p);
            }
        }
        return list;
    }
}
