package persistence.ViewShoeList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class viewShoeListDAO implements ViewShoeListDAOInterface {

    private Connection conn;

    public viewShoeListDAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/shoesdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "123456789";
        conn = DriverManager.getConnection(url, username, password);
    }
    @Override
    public List<ViewShoeListDTO> getAllShoes() throws SQLException {
        List<ViewShoeListDTO> listDTO = new ArrayList<>();
        
        String sql = """
            SELECT 
                p.id, 
                p.name, 
                p.price, 
                p.imageUrl, 
                p.brand, 
                c.name AS category_name
            FROM product p
            JOIN category c ON p.category_id = c.id
            WHERE p.is_active = 1
            ORDER BY p.id
            """;

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ViewShoeListDTO dto = new ViewShoeListDTO();
                dto.id = rs.getInt("id");
                dto.name = rs.getString("name");
                dto.price = rs.getDouble("price");
                dto.imageUrl = rs.getString("imageUrl");
                dto.brand = rs.getString("brand");
                dto.category = rs.getString("category_name");
                listDTO.add(dto);
            }
        }
        return listDTO;
    }
}