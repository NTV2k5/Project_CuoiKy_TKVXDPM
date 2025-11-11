package business.ViewShoeDetail;

import persistence.ViewShoeDetail.ViewShoeDetailDAO;
import persistence.ViewShoeDetail.ViewShoeDetailDTO;
import persistence.ViewShoeDetail.ViewShoeDetailGateway;

import java.sql.SQLException;

import business.entity.Shoe;

public class ViewShoeDetailUseCase implements ViewShoeDetailInputBoundary {

    private ViewShoeDetailOutputBoundary outputBoundary;
    private ViewShoeDetailGateway dao;

    public ViewShoeDetailUseCase(ViewShoeDetailOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(int shoeId) throws ClassNotFoundException, SQLException
    {
        dao = new ViewShoeDetailDAO();
        ViewShoeDetailDTO dtoDb = dao.getShoeById(shoeId);
        Shoe shoe = convertToBusinessObject(dtoDb);
        ViewShoeDetailDTO dtoItem = convertToDTO(shoe);

        outputBoundary.presentShoeDetail(dtoItem);
    }

    public Shoe convertToBusinessObject(ViewShoeDetailDTO dto)
    {
        Shoe shoe = ShoeDetaiFactory.CreateShoeDetail(dto);

        return shoe;
    }

    public ViewShoeDetailDTO convertToDTO(Shoe shoe) 
    {
        if (shoe == null) return null;

        ViewShoeDetailDTO dto = new ViewShoeDetailDTO();
        dto.id = shoe.getId();
        dto.name = shoe.getName();
        dto.description = shoe.getDescription();
        dto.price = shoe.getPrice();
        dto.imageUrl = shoe.getImageUrl();
        dto.brand = shoe.getBrand();
        dto.category = shoe.getCategory();
        dto.isActive = shoe.isActive();

        if (shoe.getVariants() != null) {
            dto.variants = new java.util.ArrayList<>();
            for (var variant : shoe.getVariants()) {
                ViewShoeDetailDTO.Variant vDto = new ViewShoeDetailDTO.Variant();
                vDto.size = variant.getSize();
                vDto.color = variant.getColor();
                vDto.hexCode = variant.getHexCode();
                vDto.stock = variant.getStock();
                dto.variants.add(vDto);
            }
        }

        return dto;
    }
}
