package business.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private Long id;
    private Long userId;
    private String customerName;
    private String email;
    private String phone;
    private String address;
    private String paymentMethod;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String note;
    private List<OrderItem> items = new ArrayList<>();
    private BigDecimal total;

    // === NGHIỆP VỤ (GIỐNG Cart) ===
    public void confirmPayment() {
        if (!"PENDING".equals(status)) {
            throw new IllegalStateException("Chỉ xác nhận khi đang Chờ xử lý");
        }
        this.status = "PAID";
        this.updatedAt = LocalDateTime.now();
    }

    public void ship() {
        if (!"PAID".equals(status) && !"PROCESSING".equals(status)) {
            throw new IllegalStateException("Chỉ giao khi đã thanh toán");
        }
        this.status = "SHIPPED";
        this.updatedAt = LocalDateTime.now();
    }

    public void complete() {
        if (!"SHIPPED".equals(status)) {
            throw new IllegalStateException("Chỉ hoàn thành khi đã giao");
        }
        this.status = "COMPLETED";
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        if ("SHIPPED".equals(status) || "COMPLETED".equals(status)) {
            throw new IllegalStateException("Không thể hủy đơn đã giao");
        }
        this.status = "CANCELLED";
        this.updatedAt = LocalDateTime.now();
    }

    public boolean canCancel() {
        return "PENDING".equals(status) || "PAID".equals(status);
    }

    public void calculateTotal() {
        this.total = items.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // === GETTER & SETTER ===
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}