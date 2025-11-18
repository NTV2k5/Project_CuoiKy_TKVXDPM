package presenters.Reposity;

import java.sql.SQLException;
import java.util.List;

import business.ViewShoeList.ViewShoeListRepository;
import persistence.ViewShoeList.ViewShoeListDAOInterface;
import persistence.ViewShoeList.ViewShoeListDTO;

public class ViewShoeListRepositoryImpl implements ViewShoeListRepository
{
    private ViewShoeListDAOInterface daoInterface; 

    
    public ViewShoeListRepositoryImpl(ViewShoeListDAOInterface daoInterface) {
        this.daoInterface = daoInterface;
    }

    @Override
    public List<ViewShoeListDTO> getAllShoes() throws SQLException 
    {
        return daoInterface.getAllShoes();
    }
    
}
