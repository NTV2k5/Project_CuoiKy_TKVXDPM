package business.ViewShoeList;

import java.sql.SQLException;
import java.util.List;

import persistence.ViewShoeList.ViewShoeListDTO;

public interface ViewShoeListRepository 
{
    public List<ViewShoeListDTO> getAllShoes() throws SQLException;
}
