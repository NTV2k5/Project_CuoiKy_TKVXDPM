package business.DeleteOrder;

import persistence.Order.OrderDTO;
import persistence.Order.OrderGateway;

public class DeleteOrderUseCase implements DeleteOrderInputBoundary {
    private final OrderGateway gateway;

    public DeleteOrderUseCase(OrderGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(DeleteOrderInputData input, DeleteOrderOutputBoundary presenter) {
        try {
            OrderDTO order = gateway.getOrderById(input.getOrderId());
            if (order == null) {
                presenter.present(DeleteOrderOutputData.failure("Đơn hàng không tồn tại"));
                return;
            }
            if (!order.getStatus().equals("PENDING")) {
                presenter.present(DeleteOrderOutputData.failure("Chỉ xóa được đơn hàng ở trạng thái Chờ xử lý"));
                return;
            }

            gateway.deleteOrder(input.getOrderId());
            presenter.present(DeleteOrderOutputData.success());
        } catch (Exception e) {
            presenter.present(DeleteOrderOutputData.failure("Lỗi xóa đơn hàng"));
        }
    }
}