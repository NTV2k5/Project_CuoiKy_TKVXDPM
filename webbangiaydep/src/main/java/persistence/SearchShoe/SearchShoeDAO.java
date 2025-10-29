package persistence.SearchShoe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchShoeDAO implements SearchShoeGateway {

    private Connection conn;

    public SearchShoeDAO() throws ClassNotFoundException, SQLException 
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/webbangiaydep?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "123456789";
        conn = DriverManager.getConnection(url, username, password);
    }

    @Override
    public List<SearchShoeDTO> searchShoes(String keyword) throws SQLException {
        List<SearchShoeDTO> result = new ArrayList<>();

        String sql = "SELECT * FROM shoes WHERE name LIKE ? OR brand LIKE ? OR description LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            String search = "%" + keyword + "%";
            stmt.setString(1, search);
            stmt.setString(2, search);
            stmt.setString(3, search);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SearchShoeDTO dto = new SearchShoeDTO();
                    dto.id = rs.getInt("id");
                    dto.name = rs.getString("name");
                    dto.description = rs.getString("description");
                    dto.price = rs.getDouble("price");
                    dto.imageUrl = rs.getString("imageUrl");
                    dto.brand = rs.getString("brand");
                    dto.category = rs.getString("category");
                    dto.size = rs.getString("size");
                    dto.color = rs.getString("color");

                    result.add(dto);
                }
            }
        }
        return result;
    }
}
