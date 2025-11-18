package persistence.AddToCart;

import java.util.List;

public class AddToCartDTO 
{
    public int cartId;
    public int userId;
    public List<CartItemDTO> items;

    public static class CartItemDTO 
    {
        public int productId;
        public Integer variantId;
        public int quantity;
        public double unitPrice;

        public String productName;
        public String productImageUrl;
        public String brand;
        public String category;

        public String size;
        public String color;
        public String hexCode;
        public int stock;
    }
}