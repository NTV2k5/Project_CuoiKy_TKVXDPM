// business/AddToCart/AddToCartInputData.java
package business.AddToCart;

public class AddToCartInputData {
    
    public AddToCartInputData(Long userId, int productId, int variantId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.variantId = variantId;
        this.quantity = quantity;
    }
    public Long userId;
    public int productId;
    public int variantId;
    public int quantity;
}