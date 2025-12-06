package business.ProcessPayment;

public class ProcessPaymentOutputData 
{
    private final boolean success;
    private final String message;
    private final long orderId;

    public ProcessPaymentOutputData(boolean success, String message, long orderId) {
        this.success = success;
        this.message = message;
        this.orderId = orderId;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public long getOrderId() { return orderId; }
}