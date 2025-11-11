// business/AddCategory/AddCategoryOutputData.java
package business.AddCategory;

public class AddCategoryOutputData {
    private final boolean success;
    private final String message;
    private final long categoryId;

    public AddCategoryOutputData(boolean success, String message, long categoryId) {
        this.success = success;
        this.message = message;
        this.categoryId = categoryId;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public long getCategoryId() { return categoryId; }
}