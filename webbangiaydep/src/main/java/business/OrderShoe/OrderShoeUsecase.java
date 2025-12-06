// File: business/OrderShoe/OrderShoeUsecase.java
package business.OrderShoe;

import business.AddToCart.AddToCartRepository;
import business.ProcessPayment.PaymentGatewayRepository;
import business.entity.Order;
import business.entity.OrderItem;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderShoeUsecase implements OrderShoeInputBoundary {

    private  OrderShoeRepository orderRepository;
    private  ProductInventoryRepository productInventoryRepository;
    private  OrderShoeOutputBoundary outputBoundary;
    private AddToCartRepository cartRepository;
    private  PaymentGatewayRepository paymentGateway;


    public OrderShoeUsecase(
            OrderShoeRepository orderRepository,
            ProductInventoryRepository productInventoryRepository,
            OrderShoeOutputBoundary outputBoundary,
            AddToCartRepository cartRepository,
            PaymentGatewayRepository paymentGateway) {
        this.orderRepository = orderRepository;
        this.productInventoryRepository = productInventoryRepository;
        this.outputBoundary = outputBoundary;
        this.cartRepository = cartRepository;
        this.paymentGateway = paymentGateway;
    }

    @Override
    public void execute(OrderShoeInputData inputData) {
        try {
            // 1. Validate cơ bản
            if (inputData.getItems() == null || inputData.getItems().isEmpty()) {
                outputBoundary.presentFailure(new OrderShoeOutputData(
                    null, "FAILED", "Giỏ hàng trống", BigDecimal.ZERO));
                return;
            }

            if (inputData.getCustomerName() == null || inputData.getCustomerName().trim().isEmpty() ||
                inputData.getPhone() == null || inputData.getPhone().trim().isEmpty() ||
                inputData.getAddress() == null || inputData.getAddress().trim().isEmpty()) {
                outputBoundary.presentFailure(new OrderShoeOutputData(
                    null, "FAILED", "Vui lòng nhập đầy đủ thông tin nhận hàng", BigDecimal.ZERO));
                return;
            }

            String paymentMethod = inputData.getPaymentMethod();
            if (!"COD".equalsIgnoreCase(paymentMethod) && !"ONLINE".equalsIgnoreCase(paymentMethod)) {
                outputBoundary.presentFailure(new OrderShoeOutputData(
                    null, "FAILED", "Phương thức thanh toán không hợp lệ", BigDecimal.ZERO));
                return;
            }

            // 2. Kiểm tra tồn kho
            for (OrderShoeInputData.RequestItemData item : inputData.getItems()) {
                boolean inStock = productInventoryRepository.checkStock(
                    item.getProductId(), item.getVariantId(), item.getQuantity());

                if (!inStock) {
                    outputBoundary.presentFailure(new OrderShoeOutputData(
                        null, "FAILED",
                        "Sản phẩm ID " + item.getProductId() + " (size " + item.getVariantId() + ") không đủ số lượng",
                        BigDecimal.ZERO));
                    return;
                }
            }

            // 3. Tạo đơn hàng
            Order order = new Order();
            order.setUserId(inputData.getUserId());
            order.setCustomerName(inputData.getCustomerName());
            order.setEmail(inputData.getEmail());
            order.setPhone(inputData.getPhone());
            order.setAddress(inputData.getAddress());
            order.setPaymentMethod(paymentMethod.toUpperCase());
            order.setCreatedAt(LocalDateTime.now());
            order.setUpdatedAt(LocalDateTime.now());

            boolean isCOD = "COD".equalsIgnoreCase(paymentMethod);
            order.setStatus(isCOD ? "PENDING" : "PENDING_PAYMENT");

            // 4. Thêm items + lấy giá hiện tại
            for (OrderShoeInputData.RequestItemData item : inputData.getItems()) {
                ProductInventoryRepository.ProductInfo productInfo = productInventoryRepository.getProductInfo(
                    item.getProductId(), item.getVariantId());

                OrderItem orderItem = new OrderItem();
                orderItem.setProductId(item.getProductId());
                orderItem.setVariantId(item.getVariantId());
                orderItem.setProductName(productInfo.getName());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setUnitPrice(productInfo.getPrice());
                order.getItems().add(orderItem);
            }

            // 5. Tính tổng tiền
            order.calculateTotal();

            // 6. Giảm tồn kho
            for (OrderShoeInputData.RequestItemData item : inputData.getItems()) {
                productInventoryRepository.reduceStock(
                    item.getProductId(), item.getVariantId(), item.getQuantity());
            }

            // 7. Lưu đơn hàng
            Order savedOrder = orderRepository.save(order);

            // 7.1 
            if (inputData.getItems() != null) 
                {
                for (OrderShoeInputData.RequestItemData item : inputData.getItems())
                     {
                    // Gọi Repository để xóa đúng item đó
                        cartRepository.removeCartItem(
                        inputData.getUserId(), 
                        item.getProductId(), 
                        item.getVariantId()
                    );
                }
            }
            OrderShoeOutputData outputData;
            if ("ONLINE".equalsIgnoreCase(inputData.getPaymentMethod())) {
                // Tạo link VNPAY
                String url = paymentGateway.createPaymentUrl(savedOrder, "127.0.0.1");
                
                outputData = new OrderShoeOutputData(
                    savedOrder.getId(), "PENDING_PAYMENT", "Chuyển hướng VNPAY...", order.getTotal()
                );
                outputData.setPaymentUrl(url); // Cần thêm method này vào OutputData
            } else {
                // COD
                outputData = new OrderShoeOutputData(
                    savedOrder.getId(), "SUCCESS", "Đặt hàng thành công!", order.getTotal()
                );
            }
            outputBoundary.presentSuccess(outputData);
        } catch (Exception e) {
            e.printStackTrace();
            outputBoundary.presentFailure(new OrderShoeOutputData(
                null,
                "FAILED",
                "Hệ thống đang bận, vui lòng thử lại sau vài phút.",
                BigDecimal.ZERO
            ));
        }
    }
}