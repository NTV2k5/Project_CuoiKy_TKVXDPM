package business.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private Long id;
    private String sku;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Boolean isActive;
    private Long categoryId;

    // === NGHIỆP VỤ (GIỐNG Cart) ===
    public void reduceStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        }
        if (this.stock < quantity) {
            throw new IllegalStateException("Không đủ hàng trong kho");
        }
        this.stock -= quantity;
    }

    public void increaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        }
        this.stock += quantity;
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        if (this.stock > 0) {
            throw new IllegalStateException("Không thể ẩn sản phẩm còn hàng");
        }
        this.isActive = false;
    }

    public void updatePrice(BigDecimal newPrice) {
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Giá phải lớn hơn 0");
        }
        this.price = newPrice;
    }

    // === GETTER & SETTER (GIỐNG Cart) ===
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
