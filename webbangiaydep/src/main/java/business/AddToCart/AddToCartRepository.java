package business.AddToCart;

import persistence.AddToCart.AddToCartDTO;

public interface AddToCartRepository 
{
    public AddToCartDTO findByUserId(int userId);
    public void save(AddToCartDTO dto);
    public AddToCartDTO.CartItemDTO getVariantById(int variantId);
}
