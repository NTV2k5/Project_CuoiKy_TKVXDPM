package business.ProcessPayment;

import persistence.Order.OrderGateway;
import persistence.Order.OrderDTO;

public class ProcessPaymentUseCase implements ProcessPaymentInputBoundary {
    
    private final OrderGateway orderGateway;

    public ProcessPaymentUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    @Override
    public void execute(ProcessPaymentInputData input, ProcessPaymentOutputBoundary presenter) {
        try {
            // 1. Kiểm tra mã phản hồi từ VNPAY
            if (!"00".equals(input.getResponseCode())) {
                // Mã khác 00 nghĩa là lỗi hoặc hủy
                presenter.presentFailure(new ProcessPaymentOutputData(
                    false, 
                    "Giao dịch thất bại hoặc bị hủy bởi người dùng (Mã lỗi: " + input.getResponseCode() + ")", 
                    input.getOrderId()
                ));
                return;
            }

            // 2. Kiểm tra đơn hàng có tồn tại không
            OrderDTO order = orderGateway.getOrderById(input.getOrderId());
            if (order == null) {
                presenter.presentFailure(new ProcessPaymentOutputData(
                    false, 
                    "Không tìm thấy đơn hàng #" + input.getOrderId(), 
                    input.getOrderId()
                ));
                return;
            }

            // 3. Kiểm tra trạng thái hiện tại (Tránh cập nhật lại đơn đã xong)
            if ("PAID".equals(order.getStatus()) || "COMPLETED".equals(order.getStatus())) {
                // Đơn đã xử lý rồi -> Coi như thành công luôn
                presenter.presentSuccess(new ProcessPaymentOutputData(
                    true, 
                    "Đơn hàng đã được thanh toán trước đó.", 
                    input.getOrderId()
                ));
                return;
            }

            // 4. Cập nhật trạng thái đơn hàng sang PAID
            // (changedBy = 0L đại diện cho Hệ thống tự động cập nhật)
            orderGateway.updateOrderStatus(input.getOrderId(), "PAID", 0L);
            
            // 5. Báo thành công
            presenter.presentSuccess(new ProcessPaymentOutputData(
                true, 
                "Thanh toán thành công! Đơn hàng đã được xác nhận.", 
                input.getOrderId()
            ));

        } catch (Exception e) {
            e.printStackTrace();
            presenter.presentFailure(new ProcessPaymentOutputData(
                false, 
                "Lỗi hệ thống khi xử lý thanh toán: " + e.getMessage(), 
                input.getOrderId()
            ));
        }
    }
}