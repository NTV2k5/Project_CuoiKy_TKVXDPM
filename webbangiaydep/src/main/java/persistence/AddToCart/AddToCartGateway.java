package persistence.AddToCart;

public interface AddToCartGateway 
{
    AddToCartDTO addItemToCart(int userId, int productId, int VariantId, int quantity);

    AddToCartDTO getCartByUserId(int userId);
}