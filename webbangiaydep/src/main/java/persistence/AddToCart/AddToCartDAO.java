package persistence.AddToCart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import persistence.AddToCart.AddToCartDTO.CartItemDTO;

public class AddToCartDAO implements AddToCartDAOInterFace {

    private final Connection conn;

    public AddToCartDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/shoesdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "123456789";
        this.conn = DriverManager.getConnection(url, username, password);
    }

    // =================================================
    // LẤY GIỎ HÀNG
    // =================================================
    @Override
    public AddToCartDTO findByUserId(Long userId) {
        // SỬA: Không lấy ci.unit_price nữa, mà tính từ pv.price
        String sql = """
            SELECT 
                c.id AS cart_id,
                ci.product_id,
                ci.variant_id,
                ci.quantity,
                (pv.price + COALESCE(pv.extra_price, 0)) AS unit_price, -- Lấy giá từ variant
                p.name AS product_name,
                COALESCE(pi.url, p.imageUrl) AS product_image_url,
                p.brand,
                cat.name AS category_name,
                s.value AS size_name,
                col.name AS color_name,
                col.hex_code,
                pv.stock
            FROM cart c
            JOIN cart_item ci ON ci.cart_id = c.id
            JOIN product p ON ci.product_id = p.id
            LEFT JOIN product_variant pv ON ci.variant_id = pv.id
            LEFT JOIN size s ON pv.size_id = s.id
            LEFT JOIN color col ON pv.color_id = col.id
            LEFT JOIN product_image pi ON p.id = pi.product_id AND pi.is_primary = 1
            LEFT JOIN category cat ON p.category_id = cat.id
            WHERE c.user_id = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            Long cartId = rs.getLong("cart_id");
            List<CartItemDTO> items = new ArrayList<>();

            do {
                CartItemDTO item = new CartItemDTO();
                item.productId = rs.getLong("product_id");
                item.variantId = rs.getLong("variant_id");
                item.quantity = rs.getInt("quantity");
                item.unitPrice = rs.getDouble("unit_price"); // Giá lấy từ tính toán ở trên

                item.productName = rs.getString("product_name");
                item.productImageUrl = rs.getString("product_image_url");
                item.brand = rs.getString("brand");
                item.category = rs.getString("category_name");

                if (rs.getString("size_name") != null) {
                    item.size = rs.getString("size_name");
                    item.color = rs.getString("color_name");
                    item.hexCode = rs.getString("hex_code");
                    item.stock = rs.getInt("stock");
                }

                items.add(item);
            } while (rs.next());

            AddToCartDTO dto = new AddToCartDTO();
            dto.cartId = cartId;
            dto.userId = userId;
            dto.items = items;

            return dto;

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy giỏ hàng", e);
        }
    }

    // =================================================
    // LƯU GIỎ HÀNG
    // =================================================
    @Override
    public void save(AddToCartDTO dto) {

        if (dto.userId == null || dto.userId <= 0)
            throw new IllegalArgumentException("userId không hợp lệ");

        try {
            conn.setAutoCommit(false);

            // 1. Lấy hoặc tạo cart_id
            long cartId = getOrCreateCartId(dto.userId);

            // 2. XÓA TẤT CẢ cart_item cũ
            String deleteSql = "DELETE FROM cart_item WHERE cart_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                ps.setLong(1, cartId);
                ps.executeUpdate();
            }

            // 3. Insert lại từng item (SỬA: Bỏ cột unit_price)
            String insertSql = """
                INSERT INTO cart_item 
                    (cart_id, product_id, variant_id, quantity)
                VALUES (?, ?, ?, ?)
                """;

            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                for (CartItemDTO item : dto.items) {
                    if (item.quantity <= 0) continue;

                    ps.setLong(1, cartId);
                    ps.setLong(2, item.productId);
                    ps.setLong(3, item.variantId);
                    ps.setInt(4, item.quantity);
                    // Không lưu unit_price vào bảng cart_item nữa

                    ps.addBatch(); 
                }
                ps.executeBatch(); 
            }

            conn.commit();
        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ignore) {}
            throw new RuntimeException("Lỗi khi lưu giỏ hàng", e);
        } finally {
            try { conn.setAutoCommit(true); } catch (Exception ignore) {}
        }
    }

    private long getOrCreateCartId(Long userId) throws SQLException {
        String checkSql = "SELECT id FROM cart WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }
        }

        String insertSql = "INSERT INTO cart (user_id) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, userId);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        }
    }

    // =================================================
    // LẤY THÔNG TIN VARIANT
    // =================================================
    @Override
    public AddToCartDTO.CartItemDTO getProductVariantById(Long variantId) {
        String sql = """
            SELECT 
                pv.id AS variant_id,
                pv.product_id,
                s.value AS size_value,
                c.name AS color_name,
                c.hex_code,
                (pv.price + COALESCE(pv.extra_price, 0)) AS final_price,
                pv.stock,
                p.name AS product_name,
                COALESCE(pi.url, p.imageUrl) AS product_image_url,
                p.brand,
                cat.name AS category_name
            FROM product_variant pv
            JOIN product p ON pv.product_id = p.id
            JOIN size s ON pv.size_id = s.id
            JOIN color c ON pv.color_id = c.id
            LEFT JOIN product_image pi ON p.id = pi.product_id AND pi.is_primary = 1
            JOIN category cat ON p.category_id = cat.id
            WHERE pv.id = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, variantId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                CartItemDTO dto = new CartItemDTO();
                dto.productId = rs.getLong("product_id");
                dto.variantId = rs.getLong("variant_id");
                dto.unitPrice = rs.getDouble("final_price");
                dto.quantity = 1; 

                dto.productName = rs.getString("product_name");
                dto.productImageUrl = rs.getString("product_image_url");
                dto.brand = rs.getString("brand");
                dto.category = rs.getString("category_name");

                dto.size = rs.getString("size_value");
                dto.color = rs.getString("color_name");
                dto.hexCode = rs.getString("hex_code");
                dto.stock = rs.getInt("stock");

                return dto;
            } else {
                throw new IllegalArgumentException("Sản phẩm không tồn tại!");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy variant: " + variantId, e);
        }
    }

    @Override
    public void removeCartItem(Long userId, Long productId, Long variantId) {
        String getCartSql = "SELECT id FROM cart WHERE user_id = ?";
        Long cartId = null;

        try (PreparedStatement ps = conn.prepareStatement(getCartSql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) cartId = rs.getLong("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy giỏ hàng", e);
        }

        if (cartId == null) return; 

        String deleteSql = "DELETE FROM cart_item WHERE cart_id = ? AND product_id = ? AND variant_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
            ps.setLong(1, cartId);
            ps.setLong(2, productId);
            ps.setLong(3, variantId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi xóa sản phẩm khỏi giỏ", e);
        }
    }
}