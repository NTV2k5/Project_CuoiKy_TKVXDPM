package business.GetOrders;

import java.util.List;

import persistence.Order.OrderDTO;

public class GetOrdersOutputData {
    private final List<OrderDTO> orders;
    private final int totalCount;
    private final String error;

    private GetOrdersOutputData(List<OrderDTO> orders, int totalCount, String error) {
        this.orders = orders;
        this.totalCount = totalCount;
        this.error = error;
    }

    public static GetOrdersOutputData success(List<OrderDTO> orders, int totalCount) {
        return new GetOrdersOutputData(orders, totalCount, null);
    }

    public static GetOrdersOutputData failure(String error) {
        return new GetOrdersOutputData(null, 0, error);
    }

    public List<OrderDTO> getOrders() { return orders; }
    public int getTotalCount() { return totalCount; }
    public String getError() { return error; }
    public boolean isSuccess() { return error == null; }
}