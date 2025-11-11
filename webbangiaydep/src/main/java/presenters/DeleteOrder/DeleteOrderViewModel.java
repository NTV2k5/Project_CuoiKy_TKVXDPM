// src/main/java/presenters/Order/DeleteOrderViewModel.java
package presenters.DeleteOrder;

public class DeleteOrderViewModel {
    private final boolean success;
    private final String message;

    public DeleteOrderViewModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}