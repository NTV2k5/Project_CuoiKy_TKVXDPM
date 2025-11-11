package business.ViewShoeList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.entity.Shoe;
import persistence.ViewShoeList.ViewShoeListDTO;
import persistence.ViewShoeList.ViewShoeListGateway;
import persistence.ViewShoeList.viewShoeListDAO;

public class ViewShoeListUsecase implements ViewShoeListInputBoundary 
{
    private ViewShoeListOutputBoundary output;
    private ViewShoeListGateway dao;

    public ViewShoeListUsecase(ViewShoeListOutputBoundary output, ViewShoeListGateway dao) {
        this.output = output;
        this.dao = dao;
    }

    @Override
    public List<ViewShoeListDTO> execute() throws SQLException, ClassNotFoundException {
        List<ViewShoeListDTO> listDTO = dao.getAllShoes();
        List<Shoe> shoes = convertToBusinessObject(listDTO);
        List<ViewShoeListDTO> dtoListUI = convertToDTO(shoes);
        output.presentShoeList(dtoListUI);
        return dtoListUI;
    }

    private List<Shoe> convertToBusinessObject(List<ViewShoeListDTO> listDTO)
    {
        List<Shoe> shoes = new ArrayList<>();
        for(ViewShoeListDTO dto : listDTO)
        {
            Shoe shoe = ShoeFactory.createShoe(dto);
            shoes.add(shoe);
        }
        return shoes;
    }

    private List<ViewShoeListDTO> convertToDTO(List<Shoe> shoes)
    {
        List<ViewShoeListDTO> itemList = new ArrayList<ViewShoeListDTO>();
        for(Shoe shoe : shoes)
        {
            ViewShoeListDTO item = new ViewShoeListDTO();

            item.id = shoe.getId();
            item.name = shoe.getName();
            item.price = shoe.getPrice();
            item.imageUrl = shoe.getImageUrl();
            item.brand = shoe.getBrand();
            itemList.add(item);
        }
        return itemList;
    }
}
