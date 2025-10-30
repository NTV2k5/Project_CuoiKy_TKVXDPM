package persistence.SearchShoe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchShoeDAO implements SearchShoeGateway 
{

    private Connection conn;

    public SearchShoeDAO() throws ClassNotFoundException, SQLException 
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/shoesdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "123456789";
        conn = DriverManager.getConnection(url, username, password);
    }

    @Override
    public List<SearchShoeDTO> searchShoes(String keyword) throws SQLException 
    {
        List<SearchShoeDTO> result = new ArrayList<>();
        String sql = """
            SELECT 
                p.id,
                p.name,
                p.description,
                p.price,
                p.imageUrl,
                p.brand,
                c.name AS category_name
            FROM product p
            LEFT JOIN category c ON p.category_id = c.id
            WHERE p.is_active = 1
              AND (p.name LIKE ? 
                OR p.brand LIKE ? 
                OR p.description LIKE ? 
                OR p.short_description LIKE ?
                OR c.name LIKE ?)
            ORDER BY p.name
            """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String search = "%" + keyword + "%";
            stmt.setString(1, search);
            stmt.setString(2, search);
            stmt.setString(3, search);
            stmt.setString(4, search);
            stmt.setString(5, search);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SearchShoeDTO dto = new SearchShoeDTO();
                    dto.id = rs.getInt("id");                    // BIGINT → Long
                    dto.name = rs.getString("name");
                    dto.description = rs.getString("description");
                    dto.price = rs.getDouble("price");
                    dto.imageUrl = rs.getString("imageUrl");
                    dto.brand = rs.getString("brand");
                    dto.category = rs.getString("category_name"); // Tên danh mục
                    result.add(dto);
                }
            }
        }
        return result;
    }
}