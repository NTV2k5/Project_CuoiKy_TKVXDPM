package presenters.Reposity;

import business.AddToCart.AddToCartRepository;
import persistence.AddToCart.AddToCartDAOInterFace;
import persistence.AddToCart.AddToCartDTO;

public class AddToCartRepositoryImpl implements AddToCartRepository 
{

    private AddToCartDAOInterFace DAOInterface;

    public AddToCartRepositoryImpl(AddToCartDAOInterFace DAOInterface) {
        this.DAOInterface = DAOInterface;
    }

    @Override
    public AddToCartDTO findByUserId(int userId) 
	{
        return DAOInterface.findByUserId(userId);
    }

    @Override
    public void save(AddToCartDTO dto) 
    {
        DAOInterface.save(dto);
    }

    @Override
    public AddToCartDTO.CartItemDTO getVariantById(int variantId) {
        if (variantId <= 0) {
            throw new IllegalArgumentException("Variant ID không hợp lệ");
        }
        AddToCartDTO.CartItemDTO dto = DAOInterface.getProductVariantById(variantId);
        if (dto == null) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại");
        }
        return dto;
    }
}