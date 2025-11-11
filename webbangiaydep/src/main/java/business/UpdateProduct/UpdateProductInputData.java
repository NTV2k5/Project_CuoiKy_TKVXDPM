// business/UpdateProduct/UpdateProductInputData.java
package business.UpdateProduct;

public class UpdateProductInputData {
    private final long id;
    private final String sku;
    private final String name;
    private final String shortDescription;
    private final String description;
    private final String imageUrl;
    private final String brand;
    private final int categoryId;
    private final boolean isActive;

    public UpdateProductInputData(long id, String sku, String name, String shortDescription, String description, String imageUrl, String brand, int categoryId, boolean isActive) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.imageUrl = imageUrl;
        this.brand = brand;
        this.categoryId = categoryId;
        this.isActive = isActive;
    }

    public long getId() { return id; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public String getShortDescription() { return shortDescription; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getBrand() { return brand; }
    public int getCategoryId() { return categoryId; }
    public boolean isActive() { return isActive; }
}