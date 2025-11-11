// presenters/DeleteCategory/DeleteCategoryPresenter.java
package presenters.DeleteCategory;

import business.DeleteCategory.DeleteCategoryOutputBoundary;
import business.DeleteCategory.DeleteCategoryOutputData;

public class DeleteCategoryPresenter implements DeleteCategoryOutputBoundary {
    private DeleteCategoryViewModel viewModel;

    @Override
    public void present(DeleteCategoryOutputData outputData) {
        this.viewModel = new DeleteCategoryViewModel();
        viewModel.success = outputData.isSuccess();
        viewModel.message = outputData.getMessage();
    }

    public DeleteCategoryViewModel getViewModel() {
        return viewModel;
    }
}