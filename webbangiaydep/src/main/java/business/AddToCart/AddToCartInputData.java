// business/AddToCart/AddToCartInputData.java
package business.AddToCart;

public class AddToCartInputData {
    
    public AddToCartInputData(Long userId, Long productId, Long variantId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.variantId = variantId;
        this.quantity = quantity;
    }
    public Long userId;
    public Long productId;
    public Long variantId;
    public int quantity;
}