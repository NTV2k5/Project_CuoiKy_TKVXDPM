package business.entity;

public  class ShoeVariant {
    private  Long id;
    private  Long productId;
    private  String size; 
    private  String color;     
    private  String hexCode;
    private  double price;
    private  int stock;

    public ShoeVariant(Long id, Long productId, String size, String color, String hexCode, double price, int stock) {
        this.id = id;
        this.productId = productId;
        this.size = size;
        this.color = color;
        this.hexCode = hexCode;
        this.price = price;
        this.stock = stock;
    }

    // === Getters ===
    
    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
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

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }
    // === NGHIỆP VỤ ===
    //nghiệp vụ kiểm tra có đủ số lượng với yêu cầu khách hàng không
    public String validateStock(int requestedQuantity) 
    {
        if (requestedQuantity > stock) {
            return "Chỉ còn " + stock + " sản phẩm trong kho";
        }
        return null;
    }
    // nghiệp vụ kiểm tra đã hết hàng chưa
    public boolean isOutOfStock() {
        if(stock <= 0)
        {
            return true;
        }
        return false;
    }
}