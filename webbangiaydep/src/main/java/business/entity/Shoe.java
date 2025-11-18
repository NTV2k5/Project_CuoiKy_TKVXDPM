package business.entity;

import java.util.ArrayList;
import java.util.List;

public class Shoe 
{
    private  int id;
    private  String name;
    private  String description;
    private  String imageUrl;
    private  String brand;
    private  String category;
    private  int isActive;
    private  List<ShoeVariant> variants = new ArrayList<>();

    public Shoe()
    {
        
    }

    public Shoe(int id, String name, String description, String imageUrl,
                String brand, String category, int isActive, List<ShoeVariant> variants) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.brand = brand;
        this.category = category;
        this.isActive = isActive;
        this.variants = variants != null ? new ArrayList<>(variants) : new ArrayList<>();
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getBrand() { return brand; }
    public String getCategory() { return category; }
    public int isActive() { return isActive; }
    public List<ShoeVariant> getVariants() { return new ArrayList<>(variants); }
    

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setActive(int isActive) {
        this.isActive = isActive;
    }

    public void setVariants(List<ShoeVariant> variants) {
        this.variants = variants;
    }

    public void addVariant(ShoeVariant variant) {
        variants.add(variant);
    }

    public int calculateTotalStock() {
        return variants.stream().mapToInt(ShoeVariant::getStock).sum();
    }
    
    public boolean isAvailableForSale() 
    {
        return this.isActive == 1;
    }
}