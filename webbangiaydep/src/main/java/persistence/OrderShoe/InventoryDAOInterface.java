package persistence.OrderShoe;

import java.math.BigDecimal;

public interface InventoryDAOInterface 
{
    boolean checkStock(Long productId, Long variantId, int quantity);
    
    int reduceStock(Long productId, Long variantId, int quantity);
    
    ProductInfo getProductInfo(Long productId, Long variantId);

    record ProductInfo(String name, BigDecimal price) {}
}
