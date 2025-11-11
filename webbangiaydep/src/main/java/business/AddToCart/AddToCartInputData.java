// business/AddToCart/AddToCartInputData.java
package business.AddToCart;

public class AddToCartInputData {
    public int userId;
    public int productId;
    public int variantId;
    public int quantity;
    public double price;
    public AddToCartInputData(int userId, int productId, int variantId, int quantity, double price) {
        this.userId = userId;
        this.productId = productId;
        this.variantId = variantId;
        this.quantity = quantity;
        this.price = price;
    }
    public int getUserId() {
        return userId;
    }
    public int getProductId() {
        return productId;
    }
    public int getVariantId() {
        return variantId;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getPrice() {
        return price;
    }
    
    
}