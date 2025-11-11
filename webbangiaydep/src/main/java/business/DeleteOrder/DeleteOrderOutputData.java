package business.DeleteOrder;

public class DeleteOrderOutputData {
    private final boolean success;
    private final String message;

    private DeleteOrderOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static DeleteOrderOutputData success() {
        return new DeleteOrderOutputData(true, "Xóa đơn hàng thành công");
    }

    public static DeleteOrderOutputData failure(String message) {
        return new DeleteOrderOutputData(false, message);
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}