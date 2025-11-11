package business.GetOrders;

public interface GetOrdersInputBoundary {
    void execute(GetOrdersInputData input, GetOrdersOutputBoundary presenter);
}