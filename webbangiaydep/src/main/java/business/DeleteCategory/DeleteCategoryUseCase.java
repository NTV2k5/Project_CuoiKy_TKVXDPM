// business/DeleteCategory/DeleteCategoryUseCase.java
package business.DeleteCategory;

import persistence.Category.CategoryGateway;

public class DeleteCategoryUseCase implements DeleteCategoryInputBoundary {
    private final CategoryGateway categoryGateway;

    public DeleteCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public void execute(DeleteCategoryInputData input, DeleteCategoryOutputBoundary presenter) {
        if (input.getId() <= 0) {
            presenter.present(new DeleteCategoryOutputData(false, "ID không hợp lệ"));
            return;
        }

        categoryGateway.deleteCategory(input.getId());

        presenter.present(new DeleteCategoryOutputData(true, "Xóa thành công"));
    }
}