// business/UpdateProduct/UpdateProductUseCase.java
package business.UpdateProduct;

import persistence.Product.ProductGateway;

public class UpdateProductUseCase implements UpdateProductInputBoundary {
    private final ProductGateway productGateway;

    public UpdateProductUseCase(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public void execute(UpdateProductInputData input, UpdateProductOutputBoundary presenter) {
        // Validate
        if (input.getId() <= 0) {
            presenter.present(new UpdateProductOutputData(false, "ID sản phẩm không hợp lệ"));
            return;
        }
        if (input.getName() == null || input.getName().trim().isEmpty()) {
            presenter.present(new UpdateProductOutputData(false, "Tên sản phẩm không được để trống"));
            return;
        }
        if (input.getCategoryId() <= 0) {
            presenter.present(new UpdateProductOutputData(false, "Danh mục không hợp lệ"));
            return;
        }

        // Check SKU unique if changed
        if (productGateway.skuExists(input.getSku()) && !productGateway.isSkuOwnedByProduct(input.getSku(), input.getId())) {
            presenter.present(new UpdateProductOutputData(false, "SKU đã tồn tại"));
            return;
        }

        // Update
        productGateway.updateProduct(input.getId(), input.getSku().trim(), input.getName().trim(), input.getShortDescription() != null ? input.getShortDescription().trim() : "", input.getDescription() != null ? input.getDescription().trim() : "", input.getImageUrl() != null ? input.getImageUrl().trim() : "", input.getBrand() != null ? input.getBrand().trim() : "", input.getCategoryId(), input.isActive());

        presenter.present(new UpdateProductOutputData(true, "Cập nhật thành công"));
    }
}