// persistence/AddToCart/AddToCartGateway.java
package persistence.AddToCart;

public interface AddToCartGateway {
    // Tạo hoặc lấy cart_id theo user/session
    public int getOrCreateCartId(Integer userId, String sessionId);

    // Kiểm tra biến thể hợp lệ
    public boolean isValidVariant(int productId, String size, String color);

    // Lấy variant_id (nếu cần lưu)
    public Integer getVariantId(int productId, String size, String color);

    // Thêm hoặc cập nhật item trong giỏ
    public void addOrUpdateCartItem(int cartId, int productId, Integer variantId, int quantity, double price);

    public int getTotalItemsInCart(int cartId);
}