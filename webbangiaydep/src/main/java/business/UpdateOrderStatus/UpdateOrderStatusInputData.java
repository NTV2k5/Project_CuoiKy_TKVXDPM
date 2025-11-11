// src/main/java/business/Order/UpdateOrderStatusInputData.java
package business.UpdateOrderStatus;

public class UpdateOrderStatusInputData {
    private final long orderId;
    private final String status;
    private final Long changedBy;

    public UpdateOrderStatusInputData(long orderId, String status, Long changedBy) {
        this.orderId = orderId;
        this.status = status;
        this.changedBy = changedBy;
    }

    public long getOrderId() { return orderId; }
    public String getStatus() { return status; }
    public Long getChangedBy() { return changedBy; }
}