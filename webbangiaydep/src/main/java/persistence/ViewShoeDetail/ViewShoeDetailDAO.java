    package persistence.ViewShoeDetail;

    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;

    public class ViewShoeDetailDAO implements ViewShoeDetailGateway {

        private Connection conn;

        public ViewShoeDetailDAO() throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String username = "root";
            String password = "123456789";
            String url = "jdbc:mysql://localhost:3306/webbangiaydep?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            conn = DriverManager.getConnection(url, username, password);
        }

        @Override
        public ViewShoeDetailDTO getShoeById(int shoeId) throws SQLException {
            String sql = "SELECT id, name, price, description, imageUrl, brand, category, size, color " +
            "FROM shoes WHERE id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, shoeId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        ViewShoeDetailDTO dto = new ViewShoeDetailDTO();
                        dto.id = rs.getInt("id");
                        dto.name = rs.getString("name");
                        dto.price = rs.getDouble("price");
                        dto.description = rs.getString("description");
                        dto.imageUrl = rs.getString("imageUrl");
                        dto.brand = rs.getString("brand");
                        dto.category = rs.getString("category");
                        dto.size = rs.getString("size");
                        dto.color = rs.getString("color");
                        return dto;
                    }
                }
            }
            return null; // nếu không tìm thấy giày
        }
    }
