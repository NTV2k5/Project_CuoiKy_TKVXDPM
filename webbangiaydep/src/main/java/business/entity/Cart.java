    // business/entity/Cart.java
    package business.entity;

    import java.util.ArrayList;
    import java.util.List;

    public class Cart {
        private final int id;
        private final Integer userId;
        private final String sessionId;
        private final List<CartItem> items;

        public Cart(int id, Integer userId, String sessionId, List<CartItem> items) 
        {
            this.id = id;
            this.userId = userId;
            this.sessionId = sessionId;
            this.items = items != null ? items : new ArrayList<>();
        }

        public int getId() { return id; }
        public Integer getUserId() { return userId; }
        public String getSessionId() { return sessionId; }
        public List<CartItem> getItems() { return items; }
        
        //nghiệp vụ 
        public int getTotalItems() {
            return items.stream().mapToInt(CartItem::getQuantity).sum();
        }
    }