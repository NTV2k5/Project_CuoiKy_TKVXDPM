package persistence.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements OrderGateway {
    private final Connection conn;

    public OrderDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/shoesdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        this.conn = DriverManager.getConnection(url, "root", "12345678");
    }

    @Override
    public List<OrderDTO> searchOrders(String keyword, String status, int limit) {
        StringBuilder sql = new StringBuilder(
            "SELECT o.id, o.user_id, u.email, COALESCE(a.full_name, 'Khách lẻ') AS full_name, " +
            "COALESCE(a.phone, '') AS phone, " +
            "CONCAT(COALESCE(a.street, ''), ', ', COALESCE(a.ward, ''), ', ', COALESCE(a.district, ''), ', ', COALESCE(a.city, '')) AS address, " +
            "o.order_date, o.total, o.status, o.payment_method, COUNT(oi.id) AS item_count " +
            "FROM orders o " +
            "JOIN users u ON o.user_id = u.id " +
            "LEFT JOIN address a ON o.address_id = a.id " +
            "LEFT JOIN order_item oi ON o.id = oi.order_id WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            String like = "%" + keyword.trim() + "%";
            sql.append(" AND (CAST(o.id AS CHAR) LIKE ? OR u.email LIKE ? OR a.full_name LIKE ?)");
            params.add(like); params.add(like); params.add(like);
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND o.status = ?");
            params.add(status);
        }
        sql.append(" GROUP BY o.id ORDER BY o.order_date DESC LIMIT ?");
        params.add(limit);

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            List<OrderDTO> orders = new ArrayList<>();
            while (rs.next()) {
                orders.add(new OrderDTO(
                    rs.getLong("id"),
                    rs.getLong("user_id"),
                    rs.getString("email"),
                    rs.getString("full_name"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getTimestamp("order_date").toLocalDateTime(),
                    rs.getDouble("total"),
                    rs.getString("status"),
                    rs.getString("payment_method"),
                    rs.getInt("item_count")
                ));
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi tìm đơn hàng", e);
        }
    }

    @Override
    public int getTotalOrderCount(String keyword, String status) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM orders o WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            String like = "%" + keyword.trim() + "%";
            sql.append(" AND (CAST(o.id AS CHAR) LIKE ? OR EXISTS(SELECT 1 FROM users u WHERE u.id = o.user_id AND u.email LIKE ?))");
            params.add(like); params.add(like);
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND o.status = ?");
            params.add(status);
        }
        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi đếm đơn hàng", e);
        }
    }

    @Override
    public OrderDTO getOrderById(long id) {
        String sql = "SELECT o.*, u.email, COALESCE(a.full_name, 'Khách lẻ') AS full_name, a.phone, " +
                     "CONCAT(COALESCE(a.street, ''), ', ', COALESCE(a.ward, ''), ', ', COALESCE(a.district, ''), ', ', COALESCE(a.city, '')) AS address, " +
                     "COUNT(oi.id) AS item_count " +
                     "FROM orders o JOIN users u ON o.user_id = u.id " +
                     "LEFT JOIN address a ON o.address_id = a.id " +
                     "LEFT JOIN order_item oi ON o.id = oi.order_id WHERE o.id = ? GROUP BY o.id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new OrderDTO(
                    rs.getLong("id"), rs.getLong("user_id"), rs.getString("email"),
                    rs.getString("full_name"), rs.getString("phone"), rs.getString("address"),
                    rs.getTimestamp("order_date").toLocalDateTime(),
                    rs.getDouble("total"), rs.getString("status"),
                    rs.getString("payment_method"), rs.getInt("item_count")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy đơn hàng", e);
        }
    }

    @Override
    public void updateOrderStatus(long id, String status, Long changedBy) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setLong(2, id);
            ps.executeUpdate();

            // Ghi lịch sử
            String histSql = "INSERT INTO order_status_history (order_id, status, changed_by) VALUES (?, ?, ?)";
            try (PreparedStatement ps2 = conn.prepareStatement(histSql)) {
                ps2.setLong(1, id);
                ps2.setString(2, status);
                ps2.setObject(3, changedBy);
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi cập nhật trạng thái", e);
        }
    }

    @Override
    public List<OrderItemDTO> getOrderItems(long orderId) {
        String sql = "SELECT p.id AS product_id, p.name, " +
                     "COALESCE(CONCAT(s.value, ' - ', c.name), 'Không có') AS variant_info, " +
                     "oi.quantity, oi.price " +
                     "FROM order_item oi " +
                     "JOIN product p ON oi.product_id = p.id " +
                     "LEFT JOIN product_variant pv ON oi.variant_id = pv.id " +
                     "LEFT JOIN size s ON pv.size_id = s.id " +
                     "LEFT JOIN color c ON pv.color_id = c.id " +
                     "WHERE oi.order_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, orderId);
            ResultSet rs = ps.executeQuery();
            List<OrderItemDTO> items = new ArrayList<>();
            while (rs.next()) {
                items.add(new OrderItemDTO(
                    rs.getLong("product_id"),
                    rs.getString("name"),
                    rs.getString("variant_info"),
                    rs.getInt("quantity"),
                    rs.getDouble("price")
                ));
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy chi tiết đơn", e);
        }
    }

    // src/main/java/persistence/Order/OrderDAO.java
@Override
public long createOrder(long userId, long addressId, String paymentMethod, double total, List<OrderItemDTO> items) {
    String sql = "INSERT INTO orders (user_id, address_id, payment_method, total, status, order_date) " +
                 "VALUES (?, ?, ?, ?, 'PENDING', NOW())";
    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setLong(1, userId);
        ps.setLong(2, addressId);
        ps.setString(3, paymentMethod);
        ps.setDouble(4, total);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            long orderId = rs.getLong(1);
            insertOrderItems(orderId, items);
            return orderId;
        }
        return -1;
    } catch (SQLException e) {
        throw new RuntimeException("Lỗi tạo đơn hàng", e);
    }
}

private void insertOrderItems(long orderId, List<OrderItemDTO> items) throws SQLException {
    String sql = "INSERT INTO order_item (order_id, product_id, variant_id, quantity, price) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        for (OrderItemDTO item : items) {
            ps.setLong(1, orderId);
            ps.setLong(2, item.getProductId());
            ps.setObject(3, null); // variant_id (có thể null)
            ps.setInt(4, item.getQuantity());
            ps.setDouble(5, item.getPrice());
            ps.addBatch();
        }
        ps.executeBatch();
    }
}

@Override
public void deleteOrder(long id) {
    String sql1 = "DELETE FROM order_item WHERE order_id = ?";
    String sql2 = "DELETE FROM orders WHERE id = ?";
    try (PreparedStatement ps1 = conn.prepareStatement(sql1);
         PreparedStatement ps2 = conn.prepareStatement(sql2)) {
        ps1.setLong(1, id);
        ps1.executeUpdate();
        ps2.setLong(1, id);
        ps2.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException("Lỗi xóa đơn hàng", e);
    }
}
}