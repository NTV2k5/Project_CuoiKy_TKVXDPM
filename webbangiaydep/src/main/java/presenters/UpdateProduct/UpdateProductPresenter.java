// presenters/UpdateProduct/UpdateProductPresenter.java
package presenters.UpdateProduct;

import business.UpdateProduct.UpdateProductOutputBoundary;
import business.UpdateProduct.UpdateProductOutputData;

public class UpdateProductPresenter implements UpdateProductOutputBoundary {
    private UpdateProductViewModel viewModel;

    @Override
    public void present(UpdateProductOutputData outputData) {
        this.viewModel = new UpdateProductViewModel();
        viewModel.success = outputData.isSuccess();
        viewModel.message = outputData.getMessage();
    }

    public UpdateProductViewModel getViewModel() {
        return viewModel;
    }
}