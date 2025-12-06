// business/entity/CartItem.java
package business.entity;

public class CartItem 
{
    private Long productId;
    private Long variantId;
    private int quantity;
    private double price;

    public CartItem(Long productId, Long variantId, int quantity, double price) {
        this.productId = productId;
        this.variantId = variantId;
        this.quantity = quantity;
        this.price = price;
    }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Long getProductId() { return productId; }
    public Long getVariantId() { return variantId; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    public double getTotalPrice() {
        return quantity * price;
    }
}