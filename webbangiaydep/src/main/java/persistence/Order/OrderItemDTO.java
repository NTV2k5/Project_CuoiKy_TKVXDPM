// src/main/java/persistence/Order/OrderItemDTO.java
package persistence.Order;

public class OrderItemDTO {
    private final long productId;
    private final String productName;
    private final String variantInfo;
    private final int quantity;
    private final double price;

    public OrderItemDTO(long productId, String productName, String variantInfo, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.variantInfo = variantInfo;
        this.quantity = quantity;
        this.price = price;
    }

    public long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getVariantInfo() { return variantInfo; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public double getSubtotal() { return quantity * price; }
}