// presenters/AddCategory/AddCategoryPresenter.java
package presenters.AddCategory;

import business.AddCategory.AddCategoryOutputBoundary;
import business.AddCategory.AddCategoryOutputData;

public class AddCategoryPresenter implements AddCategoryOutputBoundary {
    private AddCategoryViewModel viewModel;

    @Override
    public void present(AddCategoryOutputData outputData) {
        this.viewModel = new AddCategoryViewModel();
        viewModel.success = outputData.isSuccess();
        viewModel.message = outputData.getMessage();
        viewModel.categoryId = outputData.getCategoryId();
    }

    public AddCategoryViewModel getViewModel() {
        return viewModel;
    }
}