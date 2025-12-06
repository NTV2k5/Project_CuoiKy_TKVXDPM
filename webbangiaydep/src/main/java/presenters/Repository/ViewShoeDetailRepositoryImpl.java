package presenters.Repository;

import java.sql.SQLException;

import business.ViewShoeDetail.ViewShoeDetailRepository;
import persistence.ViewShoeDetail.ViewShoeDetailDTO;
import persistence.ViewShoeDetail.ViewShoeDetailInterface;

public class ViewShoeDetailRepositoryImpl implements ViewShoeDetailRepository
{
    private ViewShoeDetailInterface daoInterface;

    
    public ViewShoeDetailRepositoryImpl(ViewShoeDetailInterface daoInterface) {
        this.daoInterface = daoInterface;
    }
    @Override
    public ViewShoeDetailDTO getShoeById(int shoeId) throws SQLException 
    {
        return daoInterface.getShoeById(shoeId);
    }
    
}
