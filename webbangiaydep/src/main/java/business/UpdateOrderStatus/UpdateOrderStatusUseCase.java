// src/main/java/business/Order/UpdateOrderStatusUseCase.java
package business.UpdateOrderStatus;

import persistence.Order.OrderGateway;

public class UpdateOrderStatusUseCase implements UpdateOrderStatusInputBoundary {
    private final OrderGateway gateway;

    public UpdateOrderStatusUseCase(OrderGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(UpdateOrderStatusInputData input, UpdateOrderStatusOutputBoundary presenter) {
        try {
            gateway.updateOrderStatus(input.getOrderId(), input.getStatus(), input.getChangedBy());
            presenter.present(UpdateOrderStatusOutputData.success());
        } catch (Exception e) {
            presenter.present(UpdateOrderStatusOutputData.failure("Cập nhật thất bại"));
        }
    }
}