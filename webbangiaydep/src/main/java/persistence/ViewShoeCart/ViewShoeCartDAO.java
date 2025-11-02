// persistence/ViewShoeCart/ViewShoeCartDAO.java
package persistence.ViewShoeCart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViewShoeCartDAO implements ViewShoeCartGateway {

    private Connection conn;
    public ViewShoeCartDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/shoesdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "123456789";
        conn = DriverManager.getConnection(url, username, password);
    }

    @Override
    public List<ViewShoeCartDTO> getCartItems(String userId, String sessionId) {
        List<ViewShoeCartDTO> items = new ArrayList<>();

        String sql = """
            SELECT 
                ci.product_id,
                p.name,
                p.imageUrl,
                s.value AS size,
                co.name AS color,
                ci.quantity,
                p.price
            FROM cart c
            JOIN cart_item ci ON c.id = ci.cart_id
            JOIN product p ON ci.product_id = p.id
            LEFT JOIN product_variant pv ON ci.variant_id = pv.id
            LEFT JOIN size s ON pv.size_id = s.id
            LEFT JOIN color co ON pv.color_id = co.id
            WHERE (c.user_id = ? AND c.user_id IS NOT NULL)
               OR (c.session_id = ? AND c.user_id IS NULL)
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setString(2, sessionId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ViewShoeCartDTO dto = new ViewShoeCartDTO();
                    dto.productId   = rs.getInt("product_id");
                    dto.productName = rs.getString("name");
                    dto.imageUrl    = rs.getString("imageUrl");
                    dto.size        = rs.getString("size");
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