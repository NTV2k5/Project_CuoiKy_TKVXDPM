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
                MIN(pv.price) AS price, 
                p.imageUrl, 
                p.brand, 
                c.name AS category_name,
                p.is_active
            FROM product p
            JOIN category c ON p.category_id = c.id
            LEFT JOIN product_variant pv ON p.id = pv.product_id
            WHERE p.is_active = 1
            GROUP BY p.id, p.name, p.imageUrl, p.brand, c.name, p.is_active
            ORDER BY p.id
            """;

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ViewShoeListDTO dto = new ViewShoeListDTO();
                dto.id = rs.getLong("id");
                dto.name = rs.getString("name");
                
                // Lấy giá từ cột alias 'price' (kết quả của hàm MIN)
                // Nếu sản phẩm chưa có variant nào thì giá có thể là 0 hoặc null, ta xử lý mặc định là 0
                dto.price = rs.getDouble("price"); 
                
                dto.imageUrl = rs.getString("imageUrl");
                dto.brand = rs.getString("brand");
                dto.category = rs.getString("category_name");
                dto.isActive = rs.getInt("is_active");
                listDTO.add(dto);
            }
        }
        return listDTO;
    }
}