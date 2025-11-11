// persistence/Category/CategoryDAO.java
package persistence.Category;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements CategoryGateway {
    private final Connection conn;

    public CategoryDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/shoesdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "12345678";
        this.conn = DriverManager.getConnection(url, username, password);
    }

    @Override
    public long createCategory(String code, String name, String description, Integer parentId) {
        String sql = "INSERT INTO category (code, name, description, parent_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, code);
            ps.setString(2, name);
            ps.setString(3, description);
            if (parentId != null) {
                ps.setInt(4, parentId);
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi tạo category", e);
        }
    }

    @Override
    public void updateCategory(long id, String code, String name, String description, Integer parentId) {
        String sql = "UPDATE category SET code = ?, name = ?, description = ?, parent_id = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            ps.setString(2, name);
            ps.setString(3, description);
            if (parentId != null) {
                ps.setInt(4, parentId);
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            ps.setLong(5, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi cập nhật category", e);
        }
    }

    @Override
    public void deleteCategory(long id) {
        String sql = "DELETE FROM category WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi xóa category", e);
        }
    }

    @Override
    public List<CategoryDTO> searchCategories(String keyword, Integer parentId, int limit) {
        StringBuilder sql = new StringBuilder("SELECT id, code, name, description, parent_id FROM category WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (name LIKE ? OR code LIKE ?)");
            String likeKeyword = "%" + keyword.trim() + "%";
            params.add(likeKeyword);
            params.add(likeKeyword);
        }
        if (parentId != null) {
            sql.append(" AND parent_id = ?");
            params.add(parentId);
        }
        sql.append(" ORDER BY name ASC LIMIT ?");
        params.add(limit);

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            List<CategoryDTO> categories = new ArrayList<>();
            while (rs.next()) {
                Integer pid = rs.getObject("parent_id") != null ? rs.getInt("parent_id") : null;
                categories.add(new CategoryDTO(rs.getInt("id"), rs.getString("code"), rs.getString("name"), rs.getString("description"), pid));
            }
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi tìm kiếm category", e);
        }
    }

    @Override
    public CategoryDTO getCategoryById(long id) {
        String sql = "SELECT id, code, name, description, parent_id FROM category WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer pid = rs.getObject("parent_id") != null ? rs.getInt("parent_id") : null;
                return new CategoryDTO(rs.getInt("id"), rs.getString("code"), rs.getString("name"), rs.getString("description"), pid);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy category", e);
        }
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return searchCategories(null, null, Integer.MAX_VALUE);
    }

    @Override
    public boolean codeExists(String code) {
        String sql = "SELECT 1 FROM category WHERE code = ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi kiểm tra code", e);
        }
    }

    @Override
    public boolean isCodeOwnedByCategory(String code, long categoryId) {
        String sql = "SELECT 1 FROM category WHERE code = ? AND id = ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            ps.setLong(2, categoryId);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi kiểm tra code owner", e);
        }
    }

    @Override
    public int getTotalCategoryCount(String keyword, Integer parentId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM category");
        List<Object> params = new ArrayList<>();
        boolean hasWhere = false;

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" WHERE (name LIKE ? OR code LIKE ?)");
            String likeKeyword = "%" + keyword.trim() + "%";
            params.add(likeKeyword);
            params.add(likeKeyword);
            hasWhere = true;
        }
        if (parentId != null) {
            if (hasWhere) {
                sql.append(" AND parent_id = ?");
            } else {
                sql.append(" WHERE parent_id = ?");
            }
            params.add(parentId);
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
            throw new RuntimeException("Lỗi đếm category", e);
        }
    }
}