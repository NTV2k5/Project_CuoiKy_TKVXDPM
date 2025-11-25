// presenters/ViewShoeCart/ViewShoeCartItem.java
package presenters.ViewShoeCart;

public class ViewShoeCartItem {
    public long productId;
    public String productName;
    public String imageUrl;
    public int size;
    public String color;
    public int quantity;
    public double price;
    public double totalPrice;

    public long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getImageUrl() { return imageUrl; }
    public int getSize() { return size; }
    public String getColor() { return color; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public double getTotalPrice() { return totalPrice; }
}