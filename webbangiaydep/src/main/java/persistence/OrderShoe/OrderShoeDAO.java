package persistence.OrderShoe;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderShoeDAO implements OrderShoeDAOInterface
{

    private final Connection conn;

    public OrderShoeDAO() {
        try {
            String url = "jdbc:mysql://localhost:3306/shoesdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String username = "root";
            String password = "123456789";
            this.conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Không thể kết nối đến cơ sở dữ liệu!", e);
        }
    }

    /**
     * Lưu đơn hàng + chi tiết đơn hàng
     */
    @Override
    public OrderDTO save(OrderDTO dto) {
        // Gộp thông tin giao hàng vào note
        String shippingInfo = String.format(
            "=== THÔNG TIN GIAO HÀNG ===%n" +
            "Họ tên: %s%n" +
            "Số điện thoại: %s%n" +
            "Email: %s%n" +
            "Địa chỉ: %s%n" +
            "Ghi chú: %s",
            dto.getCustomerName() != null ? dto.getCustomerName() : "Chưa nhập tên",
            dto.getPhone() != null ? dto.getPhone() : "Chưa nhập SĐT",
            dto.getEmail() != null ? dto.getEmail() : "Không có",
            dto.getAddress() != null ? dto.getAddress() : "Chưa nhập địa chỉ",
            dto.getNote() != null ? dto.getNote() : "Không có ghi chú"
        );

        String sqlInsertOrder = """
            INSERT INTO orders 
            (user_id, total, status, payment_method, note, created_at, updated_at) 
            VALUES (?, ?, ?, ?, ?, NOW(), NOW())
            """;

        String sqlInsertItem = """
            INSERT INTO order_item 
            (order_id, product_id, variant_id, quantity, price) 
            VALUES (?, ?, ?, ?, ?)
            """;

        try {
            conn.setAutoCommit(false);

            // 1. Lưu đơn hàng (chỉ dùng các cột có thật trong DB)
            try (PreparedStatement ps = conn.prepareStatement(sqlInsertOrder, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, dto.getUserId());
                ps.setBigDecimal(2, dto.getTotal());
                ps.setString(3, "PENDING"); // hoặc dto.getStatus()
                ps.setString(4, dto.getPaymentMethod());
                ps.setString(5, shippingInfo); // ← Toàn bộ thông tin nằm đây, rất rõ ràng!

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        dto.setId(rs.getLong(1));
                    } else {
                        throw new SQLException("Không lấy được ID đơn hàng");
                    }
                }
            }

            // 2. Lưu chi tiết đơn hàng
            try (PreparedStatement psItem = conn.prepareStatement(sqlInsertItem)) {
                for (OrderItemDTO item : dto.getItems()) {
                    psItem.setLong(1, dto.getId());
                    psItem.setLong(2, item.getProductId());
                    psItem.setLong(3, item.getVariantId());
                    psItem.setInt(4, item.getQuantity());
                    psItem.setBigDecimal(5, item.getUnitPrice());
                    psItem.addBatch();
                }
                psItem.executeBatch();
            }

            conn.commit();
            return dto;

        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            throw new RuntimeException("Lỗi khi lưu đơn hàng: " + e.getMessage(), e);
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    /**
     * Tìm đơn hàng theo ID (có kèm items)
     */
    public OrderDTO findById(Long orderId) {
        String sqlOrder = "SELECT * FROM orders WHERE id = ?";
        String sqlItems = "SELECT oi.*, pv.product_name FROM order_item oi " +
                         "LEFT JOIN product_variant pv ON oi.variant_id = pv.id " +
                         "WHERE oi.order_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sqlOrder)) {
            ps.setLong(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                OrderDTO dto = new OrderDTO();
                dto.setId(rs.getLong("id"));
                dto.setUserId(rs.getLong("user_id"));
                dto.setCustomerName(rs.getString("customer_name"));
                dto.setEmail(rs.getString("email"));
                dto.setPhone(rs.getString("phone"));
                dto.setAddress(rs.getString("address"));
                dto.setTotal(rs.getBigDecimal("total"));
                dto.setStatus(rs.getString("status"));
                dto.setPaymentMethod(rs.getString("payment_method"));
                dto.setNote(rs.getString("note"));
                dto.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                dto.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

                // Lấy items
                List<OrderItemDTO> items = new ArrayList<>();
                try (PreparedStatement psItem = conn.prepareStatement(sqlItems)) {
                    psItem.setLong(1, orderId);
                    try (ResultSet rsItem = psItem.executeQuery()) {
                        while (rsItem.next()) {
                            OrderItemDTO item = new OrderItemDTO();
                            item.setProductId(rsItem.getLong("product_id"));
                            item.setVariantId(rsItem.getLong("variant_id"));
                            item.setProductName(rsItem.getString("product_name"));
                            item.setQuantity(rsItem.getInt("quantity"));
                            item.setUnitPrice(rsItem.getBigDecimal("price"));
                            items.add(item);
                        }
                    }
                }
                dto.setItems(items);
                return dto;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm đơn hàng ID = " + orderId, e);
        }
    }

    // Đóng kết nối khi cần (nếu không dùng Spring)
    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}