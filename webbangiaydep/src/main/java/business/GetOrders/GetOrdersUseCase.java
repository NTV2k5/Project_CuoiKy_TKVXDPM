package business.GetOrders;

import persistence.Order.OrderGateway;

public class GetOrdersUseCase implements GetOrdersInputBoundary {
    private final OrderGateway gateway;

    public GetOrdersUseCase(OrderGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(GetOrdersInputData input, GetOrdersOutputBoundary presenter) {
        try {
            var orders = gateway.searchOrders(input.getKeyword(), input.getStatus(), 100);
            int total = gateway.getTotalOrderCount(input.getKeyword(), input.getStatus());
            presenter.present(GetOrdersOutputData.success(orders, total));
        } catch (Exception e) {
            presenter.present(GetOrdersOutputData.failure("Lỗi tải danh sách đơn hàng"));
        }
    }
}