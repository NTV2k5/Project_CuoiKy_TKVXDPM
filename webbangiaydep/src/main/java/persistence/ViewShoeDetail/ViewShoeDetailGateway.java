package persistence.ViewShoeDetail;

import java.sql.SQLException;

public interface ViewShoeDetailGateway 
{
    ViewShoeDetailDTO getShoeById(int shoeId) throws SQLException;
}
