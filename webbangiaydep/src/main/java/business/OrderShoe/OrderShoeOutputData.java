package business.OrderShoe;

import java.math.BigDecimal;

public class OrderShoeOutputData 
{
    private Long orderId;       // ID của đơn hàng vừa tạo (nếu thành công)
    private String status;      // Trạng thái cuối cùng: "SUCCESS", "FAILED", "PENDING_PAYMENT"
    private String message;     // Tin nhắn chi tiết cho người dùng (Ví dụ: "Đặt hàng thành công", "Thanh toán thất bại")
    private BigDecimal totalAmount; // Tổng tiền đã thanh toán/cần thanh toán
    private String paymentUrl;
    public OrderShoeOutputData(Long orderId, String status, String message, BigDecimal totalAmount) {
        this.orderId = orderId;
        this.status = status;
        this.message = message;
        this.totalAmount = totalAmount;
    }
    public Long getOrderId() {
        return orderId;
    }
    public String getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public String getPaymentUrl() { return paymentUrl; }
    public void setPaymentUrl(String paymentUrl) { this.paymentUrl = paymentUrl; }
}
