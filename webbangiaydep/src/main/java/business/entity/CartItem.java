package business.entity;

public class CartItem {
    private int productId;
    private String color;
    private String size;
    private int quantity;
    private double price;

    public CartItem(int productId, String color, String size, int quantity, double price) {
        this.productId = productId;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    //nghiệp vụ
    public double getTotalPrice() 
    {
        return quantity * price;
    }
}
