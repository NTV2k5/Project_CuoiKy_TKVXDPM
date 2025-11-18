package persistence.ViewShoeDetail;

import java.sql.SQLException;

public interface ViewShoeDetailInterface 
{
    ViewShoeDetailDTO getShoeById(int shoeId) throws SQLException;
}
