package business.OrderShoe;

import java.util.List;

import business.OrderShoe.OrderShoeInputData.RequestItemData;

public class OrderShoeInputData {
    private Long userId;
    private String customerName;
    private String email;
    private String phone;
    private String address;
    private List<RequestItemData> items;
    private String paymentMethod;

    // Thêm 7 setter này – BẮT BUỘC
    public void setUserId(Long userId) { this.userId = userId; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setItems(List<RequestItemData> items) { this.items = items; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    // Giữ nguyên getter
    public Long getUserId() { return userId; }
    public String getCustomerName() { return customerName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public List<RequestItemData> getItems() { return items; }
    public String getPaymentMethod() { return paymentMethod; }

    public static class RequestItemData {
        private Long productId;
        private Long variantId;  
        private Integer quantity;

        // Thêm setter cho RequestItemData
        public void setProductId(Long productId) { this.productId = productId; }
        public void setVariantId(Long variantId) { this.variantId = variantId; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }

        // Getter
        public Long getProductId() { return productId; }
        public Long getVariantId() { return variantId; }
        public Integer getQuantity() { return quantity; }
    }
}