package business.ViewShoeList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.entity.Shoe;
import persistence.ViewShoeList.ViewShoeListDTO;

public class ViewShoeListUsecase implements ViewShoeListInputBoundary 
{
    private ViewShoeListOutputBoundary output;
    private ViewShoeListRepository dao;

    public ViewShoeListUsecase(ViewShoeListOutputBoundary output, ViewShoeListRepository dao) {
        this.output = output;
        this.dao = dao;
    }

    @Override
    public List<ViewShoeListDTO> execute() throws SQLException, ClassNotFoundException {
        List<ViewShoeListDTO> listDTO = dao.getAllShoes();
        List<Shoe> shoes = convertToBusinessObject(listDTO);
        shoes = shoes.stream()
                 .filter(Shoe::isAvailableForSale)
                 .toList();
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
