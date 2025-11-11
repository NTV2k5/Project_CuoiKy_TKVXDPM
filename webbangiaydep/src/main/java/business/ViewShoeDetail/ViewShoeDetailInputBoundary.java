package business.ViewShoeDetail;

import java.sql.SQLException;

public interface ViewShoeDetailInputBoundary 
{
    void execute(int shoeId) throws ClassNotFoundException, SQLException;
}
