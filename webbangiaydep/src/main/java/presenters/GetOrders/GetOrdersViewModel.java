// src/main/java/presenters/Order/GetOrdersViewModel.java
package presenters.GetOrders;

import java.util.List;

import persistence.Order.OrderDTO;

public class GetOrdersViewModel {
    private final List<OrderDTO> orders;
    private final int totalCount;
    private final String error;

    public GetOrdersViewModel(List<OrderDTO> orders, int totalCount, String error) {
        this.orders = orders;
        this.totalCount = totalCount;
        this.error = error;
    }

    public List<OrderDTO> getOrders() { return orders; }
    public int getTotalCount() { return totalCount; }
    public String getError() { return error; }
    public boolean hasError() { return error != null; }
}