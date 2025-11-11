package business.UpdateOrderStatus;

public class UpdateOrderStatusOutputData {
    private final boolean success;
    private final String message;

    private UpdateOrderStatusOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static UpdateOrderStatusOutputData success() {
        return new UpdateOrderStatusOutputData(true, "Cập nhật trạng thái thành công");
    }

    public static UpdateOrderStatusOutputData failure(String message) {
        return new UpdateOrderStatusOutputData(false, message);
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}
