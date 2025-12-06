package business.ProcessPayment;

import business.entity.Order;

public interface PaymentGatewayRepository {
    String createPaymentUrl(Order order, String ipAddress);
}