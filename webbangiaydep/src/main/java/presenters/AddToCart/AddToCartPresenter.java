// presenters/AddToCart/AddToCartPresenter.java
package presenters.AddToCart;

import business.AddToCart.AddToCartOutputBoundary;
import business.AddToCart.AddToCartOutputData;

public class AddToCartPresenter implements AddToCartOutputBoundary {

    private AddToCartViewModel viewModel;

    @Override
    public void present(AddToCartOutputData outputData) {
        this.viewModel = new AddToCartViewModel();
        viewModel.message = outputData.message;
        viewModel.totalItems = outputData.cartItemCount;
    }

    public AddToCartViewModel getViewModel() {
        return viewModel;
    }
}
