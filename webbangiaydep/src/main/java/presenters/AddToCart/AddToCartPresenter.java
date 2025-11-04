// presenters/AddToCart/AddToCartPresenter.java
package presenters.AddToCart;

import business.AddToCart.AddToCartOutputBoundary;
import business.AddToCart.AddToCartOutputData;

public class AddToCartPresenter implements AddToCartOutputBoundary 
{
    private AddToCartViewModel viewModel;

    @Override
    public void present(AddToCartOutputData outputData) {
        this.viewModel = new AddToCartViewModel();
        viewModel.success = outputData.isSuccess();
        viewModel.message = outputData.getMessage();
        viewModel.totalItems = 0;
    }

    public AddToCartViewModel getViewModel() {
        return viewModel;
    }
}