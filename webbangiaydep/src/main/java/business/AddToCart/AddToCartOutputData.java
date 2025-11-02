// business/AddToCart/AddToCartOutputData.java
package business.AddToCart;

public class AddToCartOutputData {
    private final boolean success;
    private final String message;
    private final int cartItemCount;

    public AddToCartOutputData(boolean success, String message, int cartItemCount) {
        this.success = success;
        this.message = message;
        this.cartItemCount = cartItemCount;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public int getCartItemCount() { return cartItemCount; }
}