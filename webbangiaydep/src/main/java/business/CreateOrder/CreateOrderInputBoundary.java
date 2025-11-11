package business.CreateOrder;

public interface CreateOrderInputBoundary {
    void execute(CreateOrderInputData input, CreateOrderOutputBoundary presenter);
}