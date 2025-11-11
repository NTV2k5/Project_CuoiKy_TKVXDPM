// business/AddCategory/AddCategoryUseCase.java
package business.AddCategory;

import persistence.Category.CategoryGateway;

public class AddCategoryUseCase implements AddCategoryInputBoundary {
    private final CategoryGateway categoryGateway;

    public AddCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public void execute(AddCategoryInputData input, AddCategoryOutputBoundary presenter) {
        // Validate
        if (input.getCode() == null || input.getCode().trim().isEmpty()) {
            presenter.present(new AddCategoryOutputData(false, "Code không được để trống", 0));
            return;
        }
        if (input.getName() == null || input.getName().trim().isEmpty()) {
            presenter.present(new AddCategoryOutputData(false, "Tên danh mục không được để trống", 0));
            return;
        }

        // Check code exists
        if (categoryGateway.codeExists(input.getCode())) {
            presenter.present(new AddCategoryOutputData(false, "Code đã tồn tại", 0));
            return;
        }

        // Create
        long categoryId = categoryGateway.createCategory(input.getCode().trim(), input.getName().trim(), input.getDescription() != null ? input.getDescription().trim() : "", input.getParentId());

        if (categoryId > 0) {
            presenter.present(new AddCategoryOutputData(true, "Thêm danh mục thành công", categoryId));
        } else {
            presenter.present(new AddCategoryOutputData(false, "Lỗi khi thêm danh mục", 0));
        }
    }
}