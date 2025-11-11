package business.ViewShoeDetail;

import business.entity.Shoe;
import business.entity.ShoeVariant;
import persistence.ViewShoeDetail.ViewShoeDetailDTO;

public class ShoeDetaiFactory {
    public static Shoe CreateShoeDetail(ViewShoeDetailDTO dto) 
    {
        Shoe shoe = new Shoe();
        shoe.setId(dto.id);
        shoe.setName(dto.name);
        shoe.setDescription(dto.description);
        shoe.setPrice(dto.price);
        shoe.setImageUrl(dto.imageUrl);
        shoe.setBrand(dto.brand);
        shoe.setCategory(dto.category);
        shoe.setActive(dto.isActive);
        if (dto.variants != null) {
            for (ViewShoeDetailDTO.Variant vDto : dto.variants) 
            {
                ShoeVariant variant = new ShoeVariant
                (
                        vDto.size,
                        vDto.color,
                        vDto.hexCode,
                        vDto.stock
                );
                shoe.addVariant(variant);
            }
        }

        return shoe;
    }
}
