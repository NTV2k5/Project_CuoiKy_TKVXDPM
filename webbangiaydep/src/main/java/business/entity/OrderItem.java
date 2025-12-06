package business.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {
    private Long productId;
    private Long VariantId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;

    // GETTER & SETTER
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public Long getVariantId() {return VariantId;}
    public void setVariantId(Long variantId) {VariantId = variantId;}
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem item = (OrderItem) o;
        return Objects.equals(productId, item.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

}
