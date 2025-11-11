package business.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Cart 
{
    private long id;
    private Long userId; 
    private String sessionId;
    private List<CartItem> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Cart(long id, Long userId, String sessionId, List<CartItem> items, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.sessionId = sessionId;
        this.items = items;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public static void validateAddItem(CartItem newItem) 
    {
        if (newItem == null) {
            throw new IllegalArgumentException("Item không được null.");
        }
        newItem.validate();
    }

    public int getTotalItemCount() {
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }
}

