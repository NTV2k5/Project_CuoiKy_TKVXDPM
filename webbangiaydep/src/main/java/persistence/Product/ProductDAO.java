// persistence/Product/ProductDAO.java (added missing methods, fixed imports)
package persistence.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import persistence.Category.CategoryDTO;

public class ProductDAO implements ProductGateway {
    private final Connection conn;

    public ProductDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/shoesdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "12345678";
        this.conn = DriverManager.getConnection(url, username, password);
    }

    @Override
    public long createProduct(String sku, String name, String shortDescription, String description, String imageUrl, String brand, int categoryId, boolean isActive) {
        String sql = "INSERT INTO product (sku, name, short_description, description, imageUrl, brand, category_id, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, sku);
            ps.setString(2, name);
            ps.setString(3, shortDescription);
            ps.setString(4, description);
            ps.setString(5, imageUrl);
            ps.setString(6, brand);
            ps.setInt(7, categoryId);
            ps.setBoolean(8, isActive);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi tạo product", e);
        }
    }

    @Override
    public void updateProduct(long id, String sku, String name, String shortDescription, String description, String imageUrl, String brand, int categoryId, boolean isActive) {
        String sql = "UPDATE product SET sku = ?, name = ?, short_description = ?, description = ?, imageUrl = ?, brand = ?, category_id = ?, is_active = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sku);
            ps.setString(2, name);
            ps.setString(3, shortDescription);
            ps.setString(4, description);
            ps.setString(5, imageUrl);
            ps.setString(6, brand);
            ps.setInt(7, categoryId);
            ps.setBoolean(8, isActive);
            ps.setLong(9, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi cập nhật product", e);
        }
    }

    @Override
    public void deleteProduct(long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi xóa product", e);
        }
    }

    @Override
    public List<ProductDTO> searchProducts(String keyword, Integer categoryId, int limit) {
        StringBuilder sql = new StringBuilder("SELECT p.id, p.sku, p.name, p.short_description, p.description, p.imageUrl, p.brand, c.name as category_name, p.is_active FROM product p JOIN category c ON p.category_id = c.id WHERE p.is_active = 1");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (p.name LIKE ? OR p.sku LIKE ? OR p.brand LIKE ?)");
            String likeKeyword = "%" + keyword.trim() + "%";
            params.add(likeKeyword);
            params.add(likeKeyword);
            params.add(likeKeyword);
        }
        if (categoryId != null) {
            sql.append(" AND p.category_id = ?");
            params.add(categoryId);
        }
        sql.append(" ORDER BY p.created_at DESC LIMIT ?");
        params.add(limit);

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            List<ProductDTO> products = new ArrayList<>();
            while (rs.next()) {
                products.add(new ProductDTO(rs.getLong("id"), rs.getString("sku"), rs.getString("name"), rs.getString("short_description"), rs.getString("description"), rs.getString("imageUrl"), rs.getString("brand"), rs.getString("category_name"), rs.getBoolean("is_active")));
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi tìm kiếm product", e);
        }
    }

    @Override
    public ProductDTO getProductById(long id) {
        String sql = "SELECT p.id, p.sku, p.name, p.short_description, p.description, p.imageUrl, p.brand, c.name as category_name, p.is_active FROM product p JOIN category c ON p.category_id = c.id WHERE p.id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ProductDTO(rs.getLong("id"), rs.getString("sku"), rs.getString("name"), rs.getString("short_description"), rs.getString("description"), rs.getString("imageUrl"), rs.getString("brand"), rs.getString("category_name"), rs.getBoolean("is_active"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy product", e);
        }
    }

    @Override
    public boolean skuExists(String sku) {
        String sql = "SELECT 1 FROM product WHERE sku = ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sku);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi kiểm tra SKU", e);
        }
    }

    @Override
    public boolean isSkuOwnedByProduct(String sku, long productId) {
        String sql = "SELECT 1 FROM product WHERE sku = ? AND id = ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sku);
            ps.setLong(2, productId);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi kiểm tra SKU owner", e);
        }
    }

    @Override
    public int getTotalProductCount(String keyword, Integer categoryId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM product p WHERE p.is_active = 1");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (p.name LIKE ? OR p.sku LIKE ? OR p.brand LIKE ?)");
            String likeKeyword = "%" + keyword.trim() + "%";
            params.add(likeKeyword);
            params.add(likeKeyword);
            params.add(likeKeyword);
        }
        if (categoryId != null) {
            sql.append(" AND p.category_id = ?");
            params.add(categoryId);
        }

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi đếm product", e);
        }
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        String sql = "SELECT id, code, name, description, parent_id FROM category ORDER BY name";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<CategoryDTO> categories = new ArrayList<>();
            while (rs.next()) {
                categories.add(new CategoryDTO(rs.getInt("id"), rs.getString("code"), rs.getString("name"), rs.getString("description"), rs.getObject("parent_id") != null ? rs.getInt("parent_id") : null));
            }
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy danh mục", e);
        }
    }
    
}

