package business.ProcessPayment;

import java.math.BigDecimal;

public class ProcessPaymentInputData {
    private final long orderId;
    private final String transactionId; // Mã giao dịch phía VNPAY (vnp_TransactionNo)
    private final String responseCode;  // Mã phản hồi (00 là thành công)
    private final BigDecimal amount;    // Số tiền đã thanh toán

    public ProcessPaymentInputData(long orderId, String transactionId, String responseCode, BigDecimal amount) {
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.responseCode = responseCode;
        this.amount = amount;
    }

    public long getOrderId() { return orderId; }
    public String getTransactionId() { return transactionId; }
    public String getResponseCode() { return responseCode; }
    public BigDecimal getAmount() { return amount; }
}