// business/UpdateCategory/UpdateCategoryOutputData.java
package business.UpdateCategory;

public class UpdateCategoryOutputData {
    private final boolean success;
    private final String message;

    public UpdateCategoryOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}