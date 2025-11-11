// src/main/java/presenters/Order/CreateOrderViewModel.java
package presenters.CreateOrder;

public class CreateOrderViewModel {
    private final boolean success;
    private final long orderId;
    private final String message;

    public CreateOrderViewModel(boolean success, long orderId, String message) {
        this.success = success;
        this.orderId = orderId;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public long getOrderId() { return orderId; }
    public String getMessage() { return message; }
}