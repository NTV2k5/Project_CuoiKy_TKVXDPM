package persistence.ViewShoeList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class viewShoeListDAO implements ViewShoeListGateway
{
    private Connection conn;

    public viewShoeListDAO() throws ClassNotFoundException, SQLException 
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String username = "root";
        String password = "123456789";
        String url = "jdbc:mysql://localhost:3306/webbangiaydep?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        conn = DriverManager.getConnection(url, username, password);
    }

	@Override
	public List<ViewShoeListDTO> getAllShoes() throws SQLException 
    {
		 List<ViewShoeListDTO> listDTO = new ArrayList<>();
        String sql = "SELECT id, name, price, imageUrl, brand, category FROM shoes WHERE isActive = TRUE";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ViewShoeListDTO dto = new ViewShoeListDTO();
                dto.id = rs.getInt("id");
                dto.name = rs.getString("name");
                dto.price = rs.getDouble("price");
                dto.imageUrl = rs.getString("imageUrl");
                dto.brand = rs.getString("brand");
                dto.category = rs.getString("category");
                listDTO.add(dto);
            }
        }
        return listDTO;
	}

}
