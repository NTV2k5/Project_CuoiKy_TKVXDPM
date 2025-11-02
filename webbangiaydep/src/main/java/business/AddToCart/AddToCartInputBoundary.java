package business.AddToCart;

public interface AddToCartInputBoundary {
    void execute(AddToCartInputData input, AddToCartOutputBoundary presenter);
}