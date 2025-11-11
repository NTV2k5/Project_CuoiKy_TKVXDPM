// src/main/java/business/Order/DeleteOrder/DeleteOrderInputData.java
package business.DeleteOrder;

public class DeleteOrderInputData {
    private final long orderId;

    public DeleteOrderInputData(long orderId) {
        this.orderId = orderId;
    }

    public long getOrderId() { return orderId; }
}