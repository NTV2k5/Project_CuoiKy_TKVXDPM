package persistence.OrderShoe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryDAO implements InventoryDAOInterface
{
    private final Connection conn;

    public InventoryDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/shoesdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String user = "root";
            String pass = "123456789";
            this.conn = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Không kết nối được CSDL!", e);
        }
    }

    @Override
    public boolean checkStock(Long productId, Long variantId, int quantity) {
        String sql = """
            SELECT stock >= ? 
            FROM product_variant 
            WHERE product_id = ? AND id = ?
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setLong(2, productId);
            ps.setLong(3, variantId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi kiểm tra tồn kho", e);
        }
    }

    @Override
    public int reduceStock(Long productId, Long variantId, int quantity) {
        String sql = """
            UPDATE product_variant 
            SET stock = stock - ? 
            WHERE product_id = ? AND id = ? AND stock >= ?
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setLong(2, productId);
            ps.setLong(3, variantId);
            ps.setInt(4, quantity);
            return ps.executeUpdate(); // trả về số dòng bị ảnh hưởng
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi giảm tồn kho", e);
        }
    }

    @Override
    public ProductInfo getProductInfo(Long productId, Long variantId) {
        // === SỬA LẠI CÂU SQL Ở ĐÂY ===
        // 1. p.name AS product_name (thay vì pv.product_name)
        // 2. pv.price (thay vì p.price)
        String sql = """
            SELECT 
                p.name AS product_name, 
                (pv.price + COALESCE(pv.extra_price, 0)) as final_price
            FROM product_variant pv
            JOIN product p ON pv.product_id = p.id
            WHERE pv.product_id = ? AND pv.id = ?
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, productId);
            ps.setLong(2, variantId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ProductInfo(
                        rs.getString("product_name"),
                        rs.getBigDecimal("final_price")
                    );
                }
                throw new RuntimeException("Không tìm thấy sản phẩm ID=" + productId + " variant=" + variantId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy thông tin sản phẩm", e);
        }
    }

    public void close() {
        try { if (conn != null) conn.close(); } catch (Exception e) { /* ignore */ }
    }
}