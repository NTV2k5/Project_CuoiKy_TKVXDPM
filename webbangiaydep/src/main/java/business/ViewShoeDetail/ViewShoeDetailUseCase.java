package business.ViewShoeDetail;

import persistence.ViewShoeDetail.ViewShoeDetailDTO;

import java.sql.SQLException;

import business.entity.Shoe;

public class ViewShoeDetailUseCase implements ViewShoeDetailInputBoundary {

    private ViewShoeDetailOutputBoundary outputBoundary;
    private ViewShoeDetailRepository dao;

    public ViewShoeDetailUseCase(ViewShoeDetailOutputBoundary outputBoundary, ViewShoeDetailRepository dao) {
        this.outputBoundary = outputBoundary;
        this.dao = dao;
    }

    @Override
    public void execute(int shoeId) throws ClassNotFoundException, SQLException
    {
        
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
        dto.imageUrl = shoe.getImageUrl();
        dto.brand = shoe.getBrand();
        dto.price = shoe.getPrice();
        dto.category = shoe.getCategory();
        dto.isActive = shoe.isActive();

        if (shoe.getVariants() != null) {
            dto.variants = new java.util.ArrayList<>();
            for (var variant : shoe.getVariants()) {
                ViewShoeDetailDTO.Variant vDto = new ViewShoeDetailDTO.Variant();
                vDto.variantId = variant.getId();
                vDto.size = variant.getSize();
                vDto.color = variant.getColor();
                vDto.hexCode = variant.getHexCode();
                vDto.stock = variant.getStock();
                vDto.status = variant.isOutOfStock();
                dto.variants.add(vDto);
            }
        }

        return dto;
    }
}
