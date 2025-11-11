// persistence/Shoe/ShoeDAO.java
package persistence.Shoe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShoeDAO implements ShoeGateway {
    private final Connection conn;

    public ShoeDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/shoesdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8";
        String username = "root";
        String password = "12345678"; // Adjust if needed
        this.conn = DriverManager.getConnection(url, username, password);
    }

    @Override
    public List<ShoeDTO> searchByKeyword(String keyword) {
        List<ShoeDTO> shoes = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE name LIKE ? OR sku LIKE ? OR brand LIKE ? AND is_active = 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String likeKeyword = "%" + keyword + "%";
            ps.setString(1, likeKeyword);
            ps.setString(2, likeKeyword);
            ps.setString(3, likeKeyword);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ShoeDTO shoe = mapRsToDto(rs);
                shoes.add(shoe);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi tìm kiếm giày", e);
        }
        return shoes;
    }

    @Override
    public boolean insertShoe(ShoeDTO shoe) {
        String sql = "INSERT INTO product (sku, name, short_description, description, price, stock, imageUrl, brand, size, color, category_id, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, shoe.sku);
            ps.setString(2, shoe.name);
            ps.setString(3, shoe.shortDescription);
            ps.setString(4, shoe.description);
            ps.setDouble(5, shoe.price);
            ps.setInt(6, shoe.stock);
            ps.setString(7, shoe.imageUrl);
            ps.setString(8, shoe.brand);
            ps.setString(9, shoe.size);
            ps.setString(10, shoe.color);
            ps.setInt(11, shoe.categoryId);
            ps.setBoolean(12, shoe.isActive);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi thêm giày", e);
        }
    }

    @Override
    public boolean updateShoe(ShoeDTO shoe) {
        String sql = "UPDATE product SET sku = ?, name = ?, short_description = ?, description = ?, price = ?, stock = ?, imageUrl = ?, brand = ?, size = ?, color = ?, category_id = ?, is_active = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, shoe.sku);
            ps.setString(2, shoe.name);
            ps.setString(3, shoe.shortDescription);
            ps.setString(4, shoe.description);
            ps.setDouble(5, shoe.price);
            ps.setInt(6, shoe.stock);
            ps.setString(7, shoe.imageUrl);
            ps.setString(8, shoe.brand);
            ps.setString(9, shoe.size);
            ps.setString(10, shoe.color);
            ps.setInt(11, shoe.categoryId);
            ps.setBoolean(12, shoe.isActive);
            ps.setLong(13, shoe.id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi cập nhật giày", e);
        }
    }

    @Override
    public boolean deleteShoe(long id) {
        String sql = "UPDATE product SET is_active = 0 WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0; // Soft delete: set is_active=0
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi xóa giày", e);
        }
    }

    @Override
    public ShoeDTO findById(long id) {
        String sql = "SELECT * FROM product WHERE id = ? AND is_active = 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRsToDto(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi tìm giày by ID", e);
        }
        return null;
    }

    @Override
    public List<ShoeDTO> findAll() {
        List<ShoeDTO> shoes = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE is_active = 1";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                shoes.add(mapRsToDto(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy danh sách giày", e);
        }
        return shoes;
    }

    private ShoeDTO mapRsToDto(ResultSet rs) throws SQLException {
        ShoeDTO dto = new ShoeDTO();
        dto.id = rs.getLong("id");
        dto.sku = rs.getString("sku");
        dto.name = rs.getString("name");
        dto.shortDescription = rs.getString("short_description");
        dto.description = rs.getString("description");
        dto.price = rs.getDouble("price");
        dto.stock = rs.getInt("stock");
        dto.imageUrl = rs.getString("imageUrl");
        dto.brand = rs.getString("brand");
        dto.size = rs.getString("size");
        dto.color = rs.getString("color");
        dto.categoryId = rs.getInt("category_id");
        dto.isActive = rs.getBoolean("is_active");
        return dto;
    }
}