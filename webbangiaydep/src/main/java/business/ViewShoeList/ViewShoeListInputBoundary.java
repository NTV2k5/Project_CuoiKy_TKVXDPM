package business.ViewShoeList;

import java.sql.SQLException;
import java.util.List;

import persistence.ViewShoeList.ViewShoeListDTO;

public interface ViewShoeListInputBoundary 
{
    List<ViewShoeListDTO> getAllShoes() throws SQLException, ClassNotFoundException;
}
