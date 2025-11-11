package business.entity;

import java.util.ArrayList;
import java.util.List;

public class Shoe {
    private int id;                 // Mã sản phẩm
    private String name;            // Tên sản phẩm (vd: Giày Sneaker XYZ)
    private String description;     // Mô tả chi tiết sản phẩm
    private double price;           // Giá bán
    private int stockQuantity;      // Số lượng tồn kho
    private String imageUrl;        // Đường dẫn hình ảnh
    private String brand;           // Thương hiệu
    private String category;        // Loại sản phẩm (vd: giày thể thao, sandal,...)
    private boolean isActive;       // Còn kinh doanh hay đã ngừng bán
    private List<ShoeVariant> variants = new ArrayList<>();

    public Shoe()
    {
        
    }
    public Shoe(int id, String name, String description, double price, int stockQuantity,
                   String imageUrl, String brand, String category, boolean isActive) 
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.brand = brand;
        this.category = category;
        this.isActive = isActive;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getStockQuantity() {
        return stockQuantity;
    }
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
    public void addVariant(ShoeVariant variant) {
        variants.add(variant);
    }

    public List<ShoeVariant> getVariants() {
        return variants;
    }

    // Nghiệp vụ: tính tổng tồn kho
    public int calculateTotalStock() 
    {
        return variants.stream().mapToInt(ShoeVariant::getStock).sum();
    }
}
