// presenters/AddProduct/AddProductPresenter.java
package presenters.AddProduct;

import business.AddProduct.AddProductOutputBoundary;
import business.AddProduct.AddProductOutputData;

public class AddProductPresenter implements AddProductOutputBoundary {
    private AddProductViewModel viewModel;

    @Override
    public void present(AddProductOutputData outputData) {
        this.viewModel = new AddProductViewModel();
        viewModel.success = outputData.isSuccess();
        viewModel.message = outputData.getMessage();
        viewModel.productId = outputData.getProductId();
    }

    public AddProductViewModel getViewModel() {
        return viewModel;
    }
}