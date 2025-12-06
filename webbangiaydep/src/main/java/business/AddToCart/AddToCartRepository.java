package business.AddToCart;

import persistence.AddToCart.AddToCartDTO;

public interface AddToCartRepository 
{
    public AddToCartDTO findByUserId(Long userId);
    public void save(AddToCartDTO dto);
    public AddToCartDTO.CartItemDTO getVariantById(Long variantId);
    public void removeCartItem(Long userId, Long productId, Long variantId);
}
