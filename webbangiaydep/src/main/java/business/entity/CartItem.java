package business.entity;

public class CartItem {
    private int productId;
    private String name;
    private String imageUrl;
    private String color;
    private String size;
    private int quantity;
    private double price;

    public CartItem(int productId, String name, String imageUrl, String color, String size, int quantity,
            double price) 
    {
        this.productId = productId;
        this.name = name;
        this.imageUrl = imageUrl;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
