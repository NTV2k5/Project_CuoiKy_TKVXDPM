// src/main/java/presenters/Order/UpdateOrderStatusViewModel.java
package presenters.UpdateOrderStatus;

public class UpdateOrderStatusViewModel {
    private final boolean success;
    private final String message;

    public UpdateOrderStatusViewModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}