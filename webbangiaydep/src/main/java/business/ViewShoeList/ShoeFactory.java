package business.ViewShoeList;

import business.entity.Shoe;
import persistence.ViewShoeList.ViewShoeListDTO;

public class ShoeFactory 
{
    public static Shoe createShoe(ViewShoeListDTO dto)
    {
        Shoe shoe = new Shoe();
        shoe.setId(dto.id);
        shoe.setName(dto.name);
        shoe.setDescription(dto.description);
        shoe.setImageUrl(dto.imageUrl);
        shoe.setBrand(dto.brand);
        shoe.setCategory(dto.category);
        shoe.setActive(dto.isActive);
        return shoe;
    }
}
