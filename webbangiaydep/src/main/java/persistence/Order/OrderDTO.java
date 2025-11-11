// src/main/java/persistence/Order/OrderDTO.java
package persistence.Order;

import java.time.LocalDateTime;

public class OrderDTO {
    private final long id;
    private final long userId;
    private final String userEmail;
    private final String fullName;
    private final String phone;
    private final String address;
    private final LocalDateTime orderDate;
    private final double total;
    private final String status;
    private final String paymentMethod;
    private final int itemCount;

    public OrderDTO(long id, long userId, String userEmail, String fullName, String phone, String address,
                    LocalDateTime orderDate, double total, String status, String paymentMethod, int itemCount) {
        this.id = id;
        this.userId = userId;
        this.userEmail = userEmail;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.orderDate = orderDate;
        this.total = total;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.itemCount = itemCount;
    }

    // Getters
    public long getId() { return id; }
    public long getUserId() { return userId; }
    public String getUserEmail() { return userEmail; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public double getTotal() { return total; }
    public String getStatus() { return status; }
    public String getPaymentMethod() { return paymentMethod; }
    public int getItemCount() { return itemCount; }
}