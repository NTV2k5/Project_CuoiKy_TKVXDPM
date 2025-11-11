// presenters/UpdateCategory/UpdateCategoryPresenter.java
package presenters.UpdateCategory;

import business.UpdateCategory.UpdateCategoryOutputBoundary;
import business.UpdateCategory.UpdateCategoryOutputData;

public class UpdateCategoryPresenter implements UpdateCategoryOutputBoundary {
    private UpdateCategoryViewModel viewModel;

    @Override
    public void present(UpdateCategoryOutputData outputData) {
        this.viewModel = new UpdateCategoryViewModel();
        viewModel.success = outputData.isSuccess();
        viewModel.message = outputData.getMessage();
    }

    public UpdateCategoryViewModel getViewModel() {
        return viewModel;
    }
}