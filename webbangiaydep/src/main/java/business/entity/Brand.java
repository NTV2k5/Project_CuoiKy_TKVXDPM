package business.entity;

public class Brand 
{
    private String id;          // Mã thương hiệu 
    private String name;        // Tên thương hiệu 
    private String country;     // Quốc gia 
    private String description; // Mô tả thêm 
    public Brand()
    {

    }
    public Brand(String id, String name, String country, String description) 
    {
        this.id = id;
        this.name = name;
        this.country = country;
        this.description = description;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
