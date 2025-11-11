package persistence.Product;

// ProductDTO as separate class (move from inner)
public class ProductDTO {
    private long id;
    private String sku;
    private String name;
    private String shortDescription;
    private String description;
    private String imageUrl;
    private String brand;
    private String categoryName;
    private boolean isActive;
    private int categoryId;

    public ProductDTO(long id, String sku, String name, String shortDescription, String description, String imageUrl, String brand, String categoryName, boolean isActive) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.imageUrl = imageUrl;
        this.brand = brand;
        this.categoryName = categoryName;
        this.isActive = isActive;
    }

    // Getters
    public long getId() { return id; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public String getShortDescription() { return shortDescription; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getBrand() { return brand; }
    public String getCategoryName() { return categoryName; }
    public boolean isActive() { return isActive; }
    public int getCategoryId() { return categoryId; } // Add if needed
}
