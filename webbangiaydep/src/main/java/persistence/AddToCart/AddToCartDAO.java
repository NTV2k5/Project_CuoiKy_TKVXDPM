// persistence/AddToCart/AddToCartDAO.java
package persistence.AddToCart;

import java.sql.*;

public class AddToCartDAO implements AddToCartGateway 
{

    private final Connection conn;

    public AddToCartDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/shoesdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "123456789";
        this.conn = DriverManager.getConnection(url, username, password);
    }

    @Override
    public AddToCartDTO addItemToCart(int userId, int productId, int variantId, int quantity) {
        if (userId <= 0 || quantity <= 0 || productId <= 0) {
            return null;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        int cartId = 0;

        try {
            conn.setAutoCommit(false);

            // 1. Lấy hoặc tạo giỏ
            ps = conn.prepareStatement("SELECT id FROM cart WHERE user_id = ? FOR UPDATE");
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                cartId = rs.getInt("id");
            } else {
                ps = conn.prepareStatement("INSERT INTO cart (user_id) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                rs.next();
                cartId = rs.getInt(1);
            }
            close(rs, ps);

            // 2. Kiểm tra stock
            if (variantId > 0) {
                ps = conn.prepareStatement("SELECT stock FROM product_variant WHERE id = ? AND product_id = ?");
                ps.setInt(1, variantId);
                ps.setInt(2, productId);
                rs = ps.executeQuery();
                if (!rs.next() || rs.getInt("stock") < quantity) {
                    conn.rollback();
                    return null;
                }
            } else {
                ps = conn.prepareStatement("SELECT 1 FROM product WHERE id = ? AND is_active = 1");
                ps.setInt(1, productId);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    conn.rollback();
                    return null;
                }
            }
            close(rs, ps);

            // 3. Upsert cart_item
            String upsert = """
                INSERT INTO cart_item (cart_id, product_id, variant_id, quantity)
                VALUES (?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)
                """;
            ps = conn.prepareStatement(upsert);
            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            if (variantId > 0) {
                ps.setInt(3, variantId);
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setInt(4, quantity);
            ps.executeUpdate();

            conn.commit();
            return getCartByUserId(userId); // Trả DTO

        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ignored) {}
            e.printStackTrace();
            return null;
        } finally {
            try { conn.setAutoCommit(true); } catch (Exception ignored) {}
            close(rs, ps);
        }
    }

    @Override
    public AddToCartDTO getCartByUserId(int userId) {
        if (userId <= 0) return null;

        String sqlCart = "SELECT id, user_id FROM cart WHERE user_id = ?";
        String sqlItems = """
            SELECT 
                ci.cart_id, ci.product_id, COALESCE(ci.variant_id, 0) AS variant_id,
                p.name, p.imageUrl,
                COALESCE(c.name, '') AS color, COALESCE(s.value, '') AS size,
                ci.quantity, p.price, ci.added_at
            FROM cart_item ci
            JOIN product p ON ci.product_id = p.id
            LEFT JOIN product_variant pv ON ci.variant_id = pv.id
            LEFT JOIN color c ON pv.color_id = c.id
            LEFT JOIN size s ON pv.size_id = s.id
            WHERE ci.cart_id = ?
            """;

        AddToCartDTO dto = new AddToCartDTO();

        try (PreparedStatement ps = conn.prepareStatement(sqlCart)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                dto.cartId = rs.getInt("id");
                dto.userId = rs.getInt("user_id");

                try (PreparedStatement ps2 = conn.prepareStatement(sqlItems)) {
                    ps2.setInt(1, dto.cartId);
                    try (ResultSet rs2 = ps2.executeQuery()) {
                        while (rs2.next()) {
                            AddToCartDTO.CartItem item = new AddToCartDTO.CartItem();
                            item.productId = rs2.getInt("product_id");
                            item.variantId = rs2.getInt("variant_id");
                            item.name = rs2.getString("name");
                            item.imageUrl = rs2.getString("imageUrl");
                            item.color = rs2.getString("color");
                            item.size = rs2.getString("size");
                            item.quantity = rs2.getInt("quantity");
                            item.price = rs2.getDouble("price");
                            item.addedAt = rs2.getTimestamp("added_at").toLocalDateTime();

                            dto.items.add(item);
                            dto.totalItemCount += item.quantity;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return dto;
    }

    private void close(AutoCloseable... resources) {
        for (var r : resources) {
            if (r != null) {
                try { r.close(); } catch (Exception ignored) {}
            }
        }
    }

    public void close() {
        if (conn != null) {
            try { conn.close(); } catch (Exception ignored) {}
        }
    }
}