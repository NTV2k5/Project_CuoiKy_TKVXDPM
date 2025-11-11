// business/UpdateProduct/UpdateProductOutputData.java
package business.UpdateProduct;

public class UpdateProductOutputData {
    private final boolean success;
    private final String message;

    public UpdateProductOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}