package business.entity;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private  Long id;
    private  Long userId;
    private  List<CartItem> items;

    public Cart(Long id, Long userId) 
    {
        this.id = id;
        this.userId = userId;
        this.items = new ArrayList<>();
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public List<CartItem> getItems() {return items;}

    //Thêm vào giỏ hàng (gộp nếu đã tồn tại)
    public String addOrUpdateItem(Long productId, Long variantId, int quantity, double price) 
    {
        CartItem existing = findItem(productId, variantId);
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
        } else {
            items.add(new CartItem(productId, variantId, quantity, price));
        }
        return null;
    }
    //Tìm sản phẩm trong giỏ theo variant
    public CartItem findItem(Long productId, Long variantId) {
        for (CartItem item : items) {
            if (item.getProductId().equals(productId) && item.getVariantId().equals(variantId)) 
            {
                return item;
            }
        }
        return null;
    }

    // === TÍNH TỔNG TIỀN ===
    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
    }

    public int getTotalItems() 
    {
        return items.size();
    }
    public int getTotalQuantity() 
    {
        return items.stream()
                    .mapToInt(CartItem::getQuantity)
                    .sum();
    }
}