// presenters/DeleteProduct/DeleteProductPresenter.java
package presenters.DeleteProduct;

import business.DeleteProduct.DeleteProductOutputBoundary;
import business.DeleteProduct.DeleteProductOutputData;

public class DeleteProductPresenter implements DeleteProductOutputBoundary {
    private DeleteProductViewModel viewModel;

    @Override
    public void present(DeleteProductOutputData outputData) {
        this.viewModel = new DeleteProductViewModel();
        viewModel.success = outputData.isSuccess();
        viewModel.message = outputData.getMessage();
    }

    public DeleteProductViewModel getViewModel() {
        return viewModel;
    }
}