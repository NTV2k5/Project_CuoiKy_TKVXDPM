package persistence.AddToCart;

public interface AddToCartDAOInterFace 
{
    public AddToCartDTO findByUserId(Long userId);
    public void save(AddToCartDTO cartDTO);
    public AddToCartDTO.CartItemDTO getProductVariantById(int variantId);
}
