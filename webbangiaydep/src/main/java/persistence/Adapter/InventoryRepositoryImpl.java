package persistence.Adapter;

import business.OrderShoe.ProductInventoryRepository;
import persistence.OrderShoe.InventoryDAO;
import persistence.OrderShoe.InventoryDAOInterface;

public class InventoryRepositoryImpl implements ProductInventoryRepository
{
    private InventoryDAOInterface dao;
    public InventoryRepositoryImpl(InventoryDAOInterface dao) {
        this.dao = dao;
    }
    public InventoryRepositoryImpl() 
    {
        this.dao = new InventoryDAO();
    }
    @Override
    public boolean checkStock(Long productId, Long variantId, int quantity) {
        return dao.checkStock(productId, variantId, quantity);
    }
    @Override
    public void reduceStock(Long productId, Long variantId, int quantity) {
        int updated = dao.reduceStock(productId, variantId, quantity);
        if (updated == 0) {
            throw new IllegalStateException("Hết hàng hoặc không đủ số lượng trong kho!");
        }
    }
    @Override
    public ProductInfo getProductInfo(Long productId, Long variantId) {
        InventoryDAOInterface.ProductInfo info = dao.getProductInfo(productId, variantId);
        return new ProductInfo(info.name(), info.price());
    }
    
}
