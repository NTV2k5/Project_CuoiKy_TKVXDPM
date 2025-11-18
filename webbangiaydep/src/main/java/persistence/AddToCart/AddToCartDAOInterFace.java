package persistence.AddToCart;

public interface AddToCartDAOInterFace 
{
    public AddToCartDTO findByUserId(int userId);
    public void save(AddToCartDTO cartDTO);
    public AddToCartDTO.CartItemDTO getProductVariantById(int variantId);
}
