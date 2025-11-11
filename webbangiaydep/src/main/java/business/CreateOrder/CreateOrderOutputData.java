package business.CreateOrder;

public class CreateOrderOutputData {
    private final boolean success;
    private final long orderId;
    private final String message;

    private CreateOrderOutputData(boolean success, long orderId, String message) {
        this.success = success;
        this.orderId = orderId;
        this.message = message;
    }

    public static CreateOrderOutputData success(long orderId) {
        return new CreateOrderOutputData(true, orderId, "Tạo đơn hàng thành công");
    }

    public static CreateOrderOutputData failure(String message) {
        return new CreateOrderOutputData(false, -1, message);
    }

    public boolean isSuccess() { return success; }
    public long getOrderId() { return orderId; }
    public String getMessage() { return message; }
}