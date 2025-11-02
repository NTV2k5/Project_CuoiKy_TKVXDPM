// package: business.AddToCart
package business.AddToCart;

public class AddToCartInputData 
{
    private final int productId; //final: Giá trị này chỉ được gán 1 lần duy nhất – không bao giờ thay đổi(IMMUTABLE)
    private final String size;
    private final String color;
    private final int quantity;
    private final double price;
    private final Integer userId;
    private final String sessionId;

    public AddToCartInputData(int productId, String size, String color, int quantity,
                              double price, Integer userId, String sessionId) 
    {
        this.productId = productId;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this.price = price;
        this.userId = userId;
        this.sessionId = sessionId;
    }
    public int getProductId() { return productId; }
    public String getSize() { return size; }
    public String getColor() { return color; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public Integer getUserId() { return userId; }
    public String getSessionId() { return sessionId; }
}