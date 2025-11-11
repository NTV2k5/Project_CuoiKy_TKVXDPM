package business.entity;

public class CartItem 
{
    private  int cartId;
    private  int productId;
    private  int variantId;
    private  int quantity;
    private  double price;
    public CartItem()
    {

    }
    public CartItem(int cartId, int productId, int variantId, int quantity, double price) {
        this.cartId = cartId;
        this.productId = productId;
        this.variantId = variantId;
        this.quantity = quantity;
        this.price = price;
    }


    public int getCartId() {
        return cartId;
    }
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public int getVariantId() {
        return variantId;
    }
    public void setVariantId(int variantId) {
        this.variantId = variantId;
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
    // NGHIỆP VỤ: VALIDATE 
    public void validate() {
        if (cartId <= 0) {
            throw new IllegalArgumentException("Cart ID không hợp lệ.");
        }
        if (productId <= 0) {
            throw new IllegalArgumentException("Product ID không hợp lệ.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0.");
        }
        if (variantId <= 0) {
            throw new IllegalArgumentException("Variant ID không hợp lệ.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Giá không hợp lệ.");
        }
    }

    // NGHIỆP VỤ: TÍNH TIỀN
    public double getTotalPrice() {
        return quantity * price;
    }

}