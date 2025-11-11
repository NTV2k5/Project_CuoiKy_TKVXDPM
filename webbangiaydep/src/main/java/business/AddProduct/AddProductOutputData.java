// business/AddProduct/AddProductOutputData.java
package business.AddProduct;

public class AddProductOutputData {
    private final boolean success;
    private final String message;
    private final long productId;

    public AddProductOutputData(boolean success, String message, long productId) {
        this.success = success;
        this.message = message;
        this.productId = productId;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public long getProductId() { return productId; }
}