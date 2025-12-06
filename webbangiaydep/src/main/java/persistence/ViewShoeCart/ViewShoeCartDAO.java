package persistence.ViewShoeCart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViewShoeCartDAO implements ViewShoeCartInterface {

    private Connection conn;

    public ViewShoeCartDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/shoesdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "123456789";
        conn = DriverManager.getConnection(url, username, password);
    }

    @Override
    public List<ViewShoeCartDTO> getCartItems(Long userId) {
        List<ViewShoeCartDTO> items = new ArrayList<>();

        // SỬA SQL: Thay p.price bằng pv.price
        String sql = """
            SELECT 
                ci.product_id AS productId,
                ci.variant_id AS variantId,
                p.name AS productName,
                p.imageUrl,
                s.value AS size,
                co.name AS color,
                ci.quantity,
                (COALESCE(pv.price, 0) + IFNULL(pv.extra_price, 0)) AS price
            FROM cart c
            JOIN cart_item ci ON c.id = ci.cart_id
            JOIN product p ON ci.product_id = p.id
            LEFT JOIN product_variant pv ON ci.variant_id = pv.id
            LEFT JOIN size s ON pv.size_id = s.id
            LEFT JOIN color co ON pv.color_id = co.id
            WHERE c.user_id = ?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ViewShoeCartDTO dto = new ViewShoeCartDTO();
                    dto.productId   = rs.getLong("productId");
                    Long variantIdTemp = rs.getObject("variantId", Long.class);
                    dto.variantId = variantIdTemp;
                    dto.productName = rs.getString("productName");
                    dto.imageUrl    = rs.getString("imageUrl");
                    dto.size        = rs.getInt("size");
                    dto.color       = rs.getString("color");
                    dto.quantity    = rs.getInt("quantity");
                    dto.price       = rs.getDouble("price");

                    items.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi kết nối hoặc truy vấn DB", e);
        }

        return items;
    }
}