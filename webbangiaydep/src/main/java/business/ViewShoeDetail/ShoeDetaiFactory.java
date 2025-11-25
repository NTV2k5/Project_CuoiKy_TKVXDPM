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
        shoe.setImageUrl(dto.imageUrl);
        shoe.setBrand(dto.brand);
        shoe.setPrice(dto.price);
        shoe.setCategory(dto.category);
        shoe.setActive(dto.isActive);
        if (dto.variants != null) {
            for (ViewShoeDetailDTO.Variant vDto : dto.variants) 
            {
                ShoeVariant variant = new ShoeVariant
                (
                    vDto.variantId,
                    shoe.getId(),
                    vDto.size,
                    vDto.color,
                    vDto.hexCode,
                    dto.price,
                    vDto.stock
                );
                shoe.addVariant(variant);
            }
        }

        return shoe;
    }
}
