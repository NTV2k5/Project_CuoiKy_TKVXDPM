package business.entity;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private  int id;
    private  int userId;
    private  List<CartItem> items;

    public Cart(int id, int userId) 
    {
        this.id = id;
        this.userId = userId;
        this.items = new ArrayList<>();
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public List<CartItem> getItems() {return items;}

    public String addOrUpdateItem(int productId, int variantId, int quantity, double price) 
    {
        CartItem existing = findItem(productId, variantId);
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
        } else {
            items.add(new CartItem(productId, variantId, quantity, price));
        }
        return null;
    }

    public CartItem findItem(int productId, int variantId) {
        for (CartItem item : items) {
            if (item.getProductId() == productId && item.getVariantId() == variantId) 
            {
                return item;
            }
        }
        return null;
    }
    // NGHIỆP VỤ: Tính tổng số lượng
    public int getTotalQuantity() 
    {
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }

    // === TÍNH TỔNG TIỀN ===
    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
    }
}