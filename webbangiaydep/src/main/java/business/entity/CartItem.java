// business/entity/CartItem.java
package business.entity;

public class CartItem 
{
    private int productId;
    private int variantId;
    private int quantity;
    private double price;

    public CartItem(int productId, int variantId, int quantity, double price) {
        this.productId = productId;
        this.variantId = variantId;
        this.quantity = quantity;
        this.price = price;
    }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getProductId() { return productId; }
    public int getVariantId() { return variantId; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
}