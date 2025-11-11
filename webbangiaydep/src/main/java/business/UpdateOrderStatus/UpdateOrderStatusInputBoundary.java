// src/main/java/business/Order/UpdateOrderStatusInputBoundary.java
package business.UpdateOrderStatus;

public interface UpdateOrderStatusInputBoundary {
    void execute(UpdateOrderStatusInputData input, UpdateOrderStatusOutputBoundary presenter);
}