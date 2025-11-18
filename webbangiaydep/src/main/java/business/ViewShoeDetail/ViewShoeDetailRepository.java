package business.ViewShoeDetail;

import java.sql.SQLException;

import persistence.ViewShoeDetail.ViewShoeDetailDTO;

public interface ViewShoeDetailRepository 
{
   public ViewShoeDetailDTO getShoeById(int shoeId) throws SQLException;
}
