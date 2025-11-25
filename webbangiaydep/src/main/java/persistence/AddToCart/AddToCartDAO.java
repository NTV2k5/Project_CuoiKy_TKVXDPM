// persistence/AddToCart/AddToCartDAO.java
package persistence.AddToCart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import persistence.AddToCart.AddToCartDTO.CartItemDTO;

public class AddToCartDAO implements AddToCartDAOInterFace
{
    private final Connection conn;

    public AddToCartDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/shoesdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "123456789";
        this.conn = DriverManager.getConnection(url, username, password);
    }

    // ==================== LẤY GIỎ HÀNG ===================
    @Override
        public AddToCartDTO findByUserId(Long userId) 
        {
        String sql = """
            SELECT 
                c.id AS cart_id,
                ci.product_id, 
                ci.variant_id, 
                ci.quantity,
                ci.unit_price,
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
            List<AddToCartDTO.CartItemDTO> items = new ArrayList<>();

            do {
                AddToCartDTO.CartItemDTO item = new AddToCartDTO.CartItemDTO();
                item.productId = rs.getInt("product_id");
                item.variantId = rs.getInt("variant_id");
                item.quantity = rs.getInt("quantity");
                item.unitPrice = rs.getDouble("unit_price");

                item.productName = rs.getString("product_name");
                item.productImageUrl = rs.getString("product_image_url");
                item.brand = rs.getString("brand");
                item.category = rs.getString("category_name");
                String sizeName = rs.getString("size_name");
                if (sizeName != null) {
                    item.size = sizeName;
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

    // ==================== LƯU GIỎ HÀNG ====================
    
   @Override
    public void save(AddToCartDTO dto) {
        if (dto.userId == null || dto.userId <= 0) {
            throw new IllegalArgumentException("userId không hợp lệ");
        }

        String upsertCartSql = """
            INSERT INTO cart (user_id) VALUES (?)
            ON DUPLICATE KEY UPDATE id = LAST_INSERT_ID(id)
            """;

        String upsertItemSql = """
            INSERT INTO cart_item (cart_id, product_id, variant_id, quantity, unit_price)
            VALUES (?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                quantity = quantity + VALUES(quantity),
                unit_price = VALUES(unit_price)
            """;

        try {
            conn.setAutoCommit(false);

            // 1. Lấy hoặc tạo cart_id
            long cartId;
            try (PreparedStatement ps = conn.prepareStatement(upsertCartSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, dto.userId);
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                cartId = rs.getLong(1);
            }

            // 2. UPSERT từng item (an toàn 100% với concurrent)
            try (PreparedStatement ps = conn.prepareStatement(upsertItemSql)) {
                for (CartItemDTO item : dto.items) {
                    if (item.quantity <= 0) continue;

                    ps.setLong(1, cartId);
                    ps.setInt(2, item.productId);
                    ps.setObject(3, item.variantId > 0 ? item.variantId : null);
                    ps.setInt(4, item.quantity);      // số lượng THÊM vào
                    ps.setDouble(5, item.unitPrice);

                    ps.addBatch();
                }
                ps.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            try { conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            throw new RuntimeException("Lỗi khi lưu giỏ hàng", e);
        } finally {
            try { conn.setAutoCommit(true); } catch (Exception ex) { ex.printStackTrace(); }
        }
    }
    // ==================== LẤY VARIANT THEO ID → DÙNG CartItemDTO ====================
    @Override
    public AddToCartDTO.CartItemDTO getProductVariantById(int variantId) {
        String sql = """
            SELECT 
                pv.id AS variant_id,
                pv.product_id,
                s.value AS size_value,
                c.name AS color_name,
                c.hex_code,
                (p.price + COALESCE(pv.extra_price, 0)) AS final_price,
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
            stmt.setInt(1, variantId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    AddToCartDTO.CartItemDTO dto = new AddToCartDTO.CartItemDTO();
                    dto.productId = rs.getInt("product_id");
                    dto.variantId = rs.getInt("variant_id");
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
                }
                else
                {
                    throw new IllegalArgumentException("Sản phẩm không tồn tại");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy variant ID: " + variantId, e);
        }
    }
}