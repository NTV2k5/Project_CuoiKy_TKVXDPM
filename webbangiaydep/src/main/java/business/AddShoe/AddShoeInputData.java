// business/AddShoe/AddShoeInputData.java (immutable)
package business.AddShoe;

public class AddShoeInputData {
    private final String sku;
    private final String name;
    private final String shortDescription;
    private final String description;
    private final double price;
    private final int stock;
    private final String imageUrl;
    private final String brand;
    private final String size;
    private final String color;
    private final int categoryId;
    private final boolean isActive;

    public AddShoeInputData(String sku, String name, String shortDescription, String description, double price, int stock,
                            String imageUrl, String brand, String size, String color, int categoryId, boolean isActive) {
        this.sku = sku;
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.brand = brand;
        this.size = size;
        this.color = color;
        this.categoryId = categoryId;
        this.isActive = isActive;
    }

    // Getters
    public String getSku() { return sku; }
    public String getName() { return name; }
    public String getShortDescription() { return shortDescription; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public String getImageUrl() { return imageUrl; }
    public String getBrand() { return brand; }
    public String getSize() { return size; }
    public String getColor() { return color; }
    public int getCategoryId() { return categoryId; }
    public boolean isActive() { return isActive; }
}