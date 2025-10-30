package persistence.ViewShoeDetail;

import java.sql.*;

public class ViewShoeDetailDAO implements ViewShoeDetailGateway 
{

    private Connection conn;

    public ViewShoeDetailDAO() throws ClassNotFoundException, SQLException 
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/shoesdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "123456789";
        conn = DriverManager.getConnection(url, username, password);
    }

    @Override
    public ViewShoeDetailDTO getShoeById(int shoeId) throws SQLException {
        String sql = """
            SELECT 
                p.id,
                p.name,
                p.price,
                p.description,
                p.imageUrl,
                p.brand,
                p.size,
                p.color,
                c.name AS category_name
            FROM product p
            LEFT JOIN category c ON p.category_id = c.id
            WHERE p.id = ? AND p.is_active = 1
            """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, shoeId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ViewShoeDetailDTO dto = new ViewShoeDetailDTO();
                    dto.id = rs.getInt("id");
                    dto.name = rs.getString("name");
                    dto.price = rs.getDouble("price");
                    dto.description = rs.getString("description");
                    dto.imageUrl = rs.getString("imageUrl");
                    dto.brand = rs.getString("brand");
                    dto.category = rs.getString("category_name"); 
                    dto.size = rs.getString("size");
                    dto.color = rs.getString("color");
                    return dto;
                }
            }
        }
        return null; 
    }
}