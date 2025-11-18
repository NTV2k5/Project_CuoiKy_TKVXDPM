package presenters.AddToCart;

import business.AddToCart.AddToCartOutputBoundary;
import business.AddToCart.AddToCartOutputData;

public class AddToCartPresenter implements AddToCartOutputBoundary {

    private final AddToCartViewModel viewModel;

    public AddToCartPresenter(AddToCartViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(AddToCartOutputData outputData) {
        viewModel.success = outputData.success;

        if (outputData.success) {
            viewModel.message = "Thêm vào giỏ thành công!";
            viewModel.totalPrice = outputData.totalPrice;
        } else {
            viewModel.message = outputData.message;
        }
    }

    public AddToCartViewModel getViewModel() {
        return viewModel;
    }
}