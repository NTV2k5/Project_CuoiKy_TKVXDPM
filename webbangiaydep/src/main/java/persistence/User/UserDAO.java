// src/main/java/persistence/User/UserDAO.java
package persistence.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements UserGateway {
    private final Connection conn;

    public UserDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/shoesdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        this.conn = DriverManager.getConnection(url, "root", "123456789");
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return searchUsers(null, null, Integer.MAX_VALUE);
    }

    @Override
    public List<UserDTO> searchUsers(String keyword, String role, int limit) {
        StringBuilder sql = new StringBuilder(
            "SELECT u.id, u.email, u.full_name, u.phone, u.role_id, r.code AS role_code, u.is_active " +
            "FROM users u JOIN role r ON u.role_id = r.id WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (u.email LIKE ? OR u.full_name LIKE ? OR u.phone LIKE ?)");
            String like = "%" + keyword.trim() + "%";
            params.add(like); params.add(like); params.add(like);
        }
        if (role != null && !role.isEmpty()) {
            sql.append(" AND r.code = ?");
            params.add(role);
        }
        sql.append(" ORDER BY u.id DESC LIMIT ?");
        params.add(limit);

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            List<UserDTO> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new UserDTO(
    rs.getLong("id"),
    rs.getString("email"),
    rs.getString("full_name"),
    rs.getString("phone"),
    rs.getInt("role_id"),
    rs.getString("role_code"),
    rs.getBoolean("is_active") // ĐÚNG
));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi tìm kiếm người dùng", e);
        }
    }

    @Override
    public int getTotalUserCount(String keyword, String role) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM users u JOIN role r ON u.role_id = r.id WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (u.email LIKE ? OR u.full_name LIKE ? OR u.phone LIKE ?)");
            String like = "%" + keyword.trim() + "%";
            params.add(like); params.add(like); params.add(like);
        }
        if (role != null && !role.isEmpty()) {
            sql.append(" AND r.code = ?");
            params.add(role);
        }
        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi đếm người dùng", e);
        }
    }

    @Override
    public UserDTO getUserById(long id) {
        String sql = "SELECT u.id, u.email, u.full_name, u.phone, u.role_id, r.code AS role_code, u.is_active " +
                     "FROM users u JOIN role r ON u.role_id = r.id WHERE u.id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UserDTO(
    rs.getLong("id"),
    rs.getString("email"),
    rs.getString("full_name"),
    rs.getString("phone"),
    rs.getInt("role_id"),
    rs.getString("role_code"),
    rs.getBoolean("is_active"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy người dùng", e);
        }
    }

    @Override
    public long createUser(String email, String password, int roleId, String fullName, String phone) {
        String sql = "INSERT INTO users (email, password, role_id, full_name, phone, is_active) VALUES (?, ?, ?, ?, ?, 1)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setInt(3, roleId);
            ps.setString(4, fullName);
            ps.setString(5, phone);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            return rs.next() ? rs.getLong(1) : -1;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi tạo người dùng", e);
        }
    }

    @Override
    public void updateUser(long id, String fullName, String phone, int roleId, boolean isActive) {
        String sql = "UPDATE users SET full_name = ?, phone = ?, role_id = ?, is_active = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fullName);
            ps.setString(2, phone);
            ps.setInt(3, roleId);
            ps.setBoolean(4, isActive);
            ps.setLong(5, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi cập nhật người dùng", e);
        }
    }

    @Override
    public void deleteUser(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi xóa người dùng", e);
        }
    }

    @Override
    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM users WHERE email = ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi kiểm tra email", e);
        }
    }

    @Override
    public boolean emailExistsExcept(long userId, String email) {
        String sql = "SELECT 1 FROM users WHERE email = ? AND id != ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setLong(2, userId);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi kiểm tra email", e);
        }
    }

    @Override
    public Long authenticate(String email, String password) {
        String sql = "SELECT u.id FROM users u JOIN role r ON u.role_id = r.id WHERE u.email = ? AND u.password = ? AND u.is_active = 1 LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getLong("id") : null;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi xác thực: " + e.getMessage(), e);
        }
    }

    @Override
    public String getRoleCodeByUserId(Long userId) {
        String sql = "SELECT r.code FROM users u JOIN role r ON u.role_id = r.id WHERE u.id = ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getString("code") : null;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy vai trò", e);
        }
    }
}