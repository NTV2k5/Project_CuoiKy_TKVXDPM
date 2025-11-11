// business/DeleteCategory/DeleteCategoryOutputData.java
package business.DeleteCategory;

public class DeleteCategoryOutputData {
    private final boolean success;
    private final String message;

    public DeleteCategoryOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}