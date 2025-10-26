package persistence.ViewShoeList;
import java.sql.SQLException;
import java.util.List;

public interface ViewShoeListGateway 
{
    public List<ViewShoeListDTO> getAllShoes() throws SQLException;
}
