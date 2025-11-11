// business/UpdateCategory/UpdateCategoryUseCase.java
package business.UpdateCategory;

import persistence.Category.CategoryGateway;

public class UpdateCategoryUseCase implements UpdateCategoryInputBoundary {
    private final CategoryGateway categoryGateway;

    public UpdateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public void execute(UpdateCategoryInputData input, UpdateCategoryOutputBoundary presenter) {
        if (input.getId() <= 0) {
            presenter.present(new UpdateCategoryOutputData(false, "ID không hợp lệ"));
            return;
        }
        if (input.getName() == null || input.getName().trim().isEmpty()) {
            presenter.present(new UpdateCategoryOutputData(false, "Tên không được để trống"));
            return;
        }

        if (categoryGateway.codeExists(input.getCode()) && !categoryGateway.isCodeOwnedByCategory(input.getCode(), input.getId())) {
            presenter.present(new UpdateCategoryOutputData(false, "Code đã tồn tại"));
            return;
        }

        categoryGateway.updateCategory(input.getId(), input.getCode().trim(), input.getName().trim(), input.getDescription() != null ? input.getDescription().trim() : "", input.getParentId());

        presenter.present(new UpdateCategoryOutputData(true, "Cập nhật thành công"));
    }
}