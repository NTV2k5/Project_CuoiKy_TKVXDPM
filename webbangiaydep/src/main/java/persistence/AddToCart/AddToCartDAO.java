package persistence.AddToCart;

import java.sql.*;

public class AddToCartDAO implements AddToCartGateway {

    private final Connection conn;

    public AddToCartDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/shoesdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "123456789";
        this.conn = DriverManager.getConnection(url, username, password);
    }

    @Override
    public int getOrCreateCartId(Integer userId, String sessionId) 
    {
        String selectSql = "SELECT id FROM cart WHERE (user_id = ? AND user_id IS NOT NULL) OR (session_id = ? AND user_id IS NULL) LIMIT 1";
        String insertSql = "INSERT INTO cart (user_id, session_id) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
            ps.setObject(1, userId);
            ps.setString(2, sessionId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

            // Tạo mới
            try (PreparedStatement psInsert = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                psInsert.setObject(1, userId);
                psInsert.setString(2, sessionId);
                psInsert.executeUpdate();

                ResultSet genKeys = psInsert.getGeneratedKeys();
                if (genKeys.next()) {
                    return genKeys.getInt(1);
                }
                throw new SQLException("Không tạo được giỏ hàng");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy/tạo giỏ hàng", e);
        }
    }

    @Override
    public boolean isValidVariant(int productId, String sizeValue, String colorName) 
    {
        String sql = """
            SELECT 1 
            FROM product_variant pv
            JOIN size s ON pv.size_id = s.id
            JOIN color c ON pv.color_id = c.id
            WHERE pv.product_id = ? AND s.value = ? AND c.name = ?
            LIMIT 1
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setString(2, sizeValue);
            ps.setString(3, colorName);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Integer getVariantId(int productId, String sizeValue, String colorName) 
    {
        String sql = """
            SELECT pv.id 
            FROM product_variant pv
            JOIN size s ON pv.size_id = s.id
            JOIN color c ON pv.color_id = c.id
            WHERE pv.product_id = ? AND s.value = ? AND c.name = ?
            """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setString(2, sizeValue);
            ps.setString(3, colorName);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void addOrUpdateCartItem(int cartId, int productId, Integer variantId, int quantityToAdd, double price) 
    {
        String upsertSql = """
            INSERT INTO cart_item (cart_id, product_id, variant_id, quantity, added_at)
            VALUES (?, ?, ?, ?, NOW())
            ON DUPLICATE KEY UPDATE
                quantity = quantity + VALUES(quantity)
            """;

        try (PreparedStatement ps = conn.prepareStatement(upsertSql)) {
            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            ps.setObject(3, variantId);
            ps.setInt(4, quantityToAdd);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi thêm sản phẩm vào giỏ", e);
        }
    }
    @Override
    public int getTotalItemsInCart(int cartId) 
    {
        String sql = "SELECT COALESCE(SUM(quantity), 0) FROM cart_item WHERE cart_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}