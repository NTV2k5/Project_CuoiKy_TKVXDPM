package business.OrderShoe;

import java.math.BigDecimal;

public interface ProductInventoryRepository 
{
    boolean checkStock(Long productId, Long variantId, int quantity);

    void reduceStock(Long productId, Long variantId, int quantity);

    ProductInfo getProductInfo(Long productId, Long variantId);

    class ProductInfo {
        private final String name;
        private final BigDecimal price;

        public ProductInfo(String name, BigDecimal price) {
            this.name = name;
            this.price = price;
        }
        public String getName() { return name; }
        public BigDecimal getPrice() { return price; }
    }
}
