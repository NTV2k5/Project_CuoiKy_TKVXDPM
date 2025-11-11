package persistence.AddToCart;
import java.time.LocalDateTime;
import java.util.List;

public class AddToCartDTO 
{
    public Integer cartId;
    public Integer userId;
    public List<CartItem> items;
    public int totalItemCount;

    public static class CartItem {
        public int CartId;
        public int productId;
        public int variantId;
        public String name;
        public String imageUrl;
        public String color;
        public String size;
        public int quantity;
        public double price;
        public LocalDateTime addedAt;
    }
}