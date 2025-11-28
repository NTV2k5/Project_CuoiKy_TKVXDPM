package persistence.ViewShoeDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViewShoeDetailDAO implements ViewShoeDetailInterface 
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
                p.id, p.name, p.price, p.description, p.imageUrl, p.brand,
                c.name AS category_name,
                s.value AS size_value,
                co.name AS color_name,
                co.hex_code,
                pv.id AS variant_id,
                pv.stock
            FROM product p
            LEFT JOIN category c ON p.category_id = c.id
            LEFT JOIN product_variant pv ON p.id = pv.product_id
            LEFT JOIN size s ON pv.size_id = s.id
            LEFT JOIN color co ON pv.color_id = co.id
            WHERE p.id = ? AND p.is_active = 1
            ORDER BY s.sort_order, co.name
            """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, shoeId);
            try (ResultSet rs = stmt.executeQuery()) {

                ViewShoeDetailDTO dto = null;
                List<ViewShoeDetailDTO.Variant> variants = new ArrayList<>();

                while (rs.next()) {
                    if (dto == null) {
                        dto = new ViewShoeDetailDTO();
                        dto.id = rs.getInt("id");
                        dto.name = rs.getString("name");
                        dto.price = rs.getDouble("price");
                        dto.description = rs.getString("description");
                        dto.imageUrl = rs.getString("imageUrl");
                        dto.brand = rs.getString("brand");
                        dto.category = rs.getString("category_name");
                    }
                    // Chỉ thêm nếu có variant
                    if (rs.getObject("size_value") != null) {
                        ViewShoeDetailDTO.Variant v = new ViewShoeDetailDTO.Variant();
                        v.variantId = rs.getInt("variant_id");
                        v.size = rs.getString("size_value");
                        v.color = rs.getString("color_name");
                        v.hexCode = rs.getString("hex_code");
                        v.stock = rs.getInt("stock");
                        variants.add(v);
                    }
                }

                if (dto != null) 
                {
                    dto.variants = variants;
                }
                return dto;
            }
        }
    }
}