package business.CreateOrder;

import java.util.List;

public class CreateOrderInputData {
    private final long userId;
    private final long addressId;
    private final String paymentMethod;
    private final double total;
    private final List<OrderItemInput> items;

    public CreateOrderInputData(long userId, long addressId, String paymentMethod, double total, List<OrderItemInput> items) {
        this.userId = userId;
        this.addressId = addressId;
        this.paymentMethod = paymentMethod;
        this.total = total;
        this.items = items;
    }

    public long getUserId() { return userId; }
    public long getAddressId() { return addressId; }
    public String getPaymentMethod() { return paymentMethod; }
    public double getTotal() { return total; }
    public List<OrderItemInput> getItems() { return items; }

    public static class OrderItemInput {
        private final long productId;
        private final int quantity;
        private final double price;

        public OrderItemInput(long productId, int quantity, double price) {
            this.productId = productId;
            this.quantity = quantity;
            this.price = price;
        }

        public long getProductId() { return productId; }
        public int getQuantity() { return quantity; }
        public double getPrice() { return price; }
    }
}