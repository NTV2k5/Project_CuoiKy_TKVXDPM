package business.OrderShoe;

public interface OrderShoeOutputBoundary 
{
    void presentSuccess(OrderShoeOutputData outputData);
    void presentFailure(OrderShoeOutputData outputData);
}
