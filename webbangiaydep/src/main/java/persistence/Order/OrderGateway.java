// src/main/java/persistence/Order/OrderGateway.java
package persistence.Order;

import java.util.List;

public interface OrderGateway {
    List<OrderDTO> searchOrders(String keyword, String status, int limit);
    int getTotalOrderCount(String keyword, String status);
    OrderDTO getOrderById(long id);
    void updateOrderStatus(long id, String status, Long changedBy);
    List<OrderItemDTO> getOrderItems(long orderId);
    long createOrder(long userId, long addressId, String paymentMethod, double total, List<OrderItemDTO> items);
    void deleteOrder(long id);

}