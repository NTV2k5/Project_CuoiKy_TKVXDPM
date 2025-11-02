// presenters/ViewShoeCart/ViewShoeCartItem.java
package presenters.ViewShoeCart;

public class ViewShoeCartItem {
    public int productId;
    public String productName;
    public String imageUrl;
    public String size;
    public String color;
    public int quantity;
    public double price;
    public double totalPrice;

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getImageUrl() { return imageUrl; }
    public String getSize() { return size; }
    public String getColor() { return color; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public double getTotalPrice() { return totalPrice; }
}