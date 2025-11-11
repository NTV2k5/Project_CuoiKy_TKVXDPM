package business.entity;

public class ShoeVariant 
{
    private String size;
    private String color;
    private String hexCode;
    private int stock;
    public ShoeVariant(String size, String color, String hexCode, int stock) 
    {
        this.size = size;
        this.color = color;
        this.hexCode = hexCode;
        this.stock = stock;
    }
    
    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getHexCode() {
        return hexCode;
    }

    public int getStock() {
        return stock;
    }

    // Nghiệp vụ: kiểm tra tồn kho
    public boolean isInStock(int requestedQuantity) 
    {
        return stock >= requestedQuantity;
    }
    
}
