package business.CreateOrder;

import java.util.List;
import java.util.stream.Collectors;

import persistence.Order.OrderGateway;
import persistence.Order.OrderItemDTO;

public class CreateOrderUseCase implements CreateOrderInputBoundary {
    private final OrderGateway gateway;

    public CreateOrderUseCase(OrderGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(CreateOrderInputData input, CreateOrderOutputBoundary presenter) {
        if (input.getItems() == null || input.getItems().isEmpty()) {
            presenter.present(CreateOrderOutputData.failure("Danh sách sản phẩm trống"));
            return;
        }

        List<OrderItemDTO> items = input.getItems().stream()
            .map(i -> new OrderItemDTO(i.getProductId(), "", "", i.getQuantity(), i.getPrice()))
            .collect(Collectors.toList());

        try {
            long orderId = gateway.createOrder(
                input.getUserId(),
                input.getAddressId(),
                input.getPaymentMethod(),
                input.getTotal(),
                items
            );
            if (orderId > 0) {
                presenter.present(CreateOrderOutputData.success(orderId));
            } else {
                presenter.present(CreateOrderOutputData.failure("Lỗi tạo đơn hàng"));
            }
        } catch (Exception e) {
            presenter.present(CreateOrderOutputData.failure("Lỗi hệ thống: " + e.getMessage()));
        }
    }
}