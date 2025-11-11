// business/AddProduct/AddProductUseCase.java (fixed: use correct method names)
package business.AddProduct;

import persistence.Product.ProductGateway;

public class AddProductUseCase implements AddProductInputBoundary {
    private final ProductGateway productGateway;

    public AddProductUseCase(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public void execute(AddProductInputData input, AddProductOutputBoundary presenter) {
        // Validate input
        if (input.getSku() == null || input.getSku().trim().isEmpty()) {
            presenter.present(new AddProductOutputData(false, "SKU không được để trống", 0));
            return;
        }
        if (input.getName() == null || input.getName().trim().isEmpty()) {
            presenter.present(new AddProductOutputData(false, "Tên sản phẩm không được để trống", 0));
            return;
        }
        if (input.getCategoryId() <= 0) {
            presenter.present(new AddProductOutputData(false, "Danh mục không hợp lệ", 0));
            return;
        }

        // Check SKU exists
        if (productGateway.skuExists(input.getSku())) {
            presenter.present(new AddProductOutputData(false, "SKU đã tồn tại", 0));
            return;
        }

        // Create product
        long productId = productGateway.createProduct(input.getSku().trim(), input.getName().trim(), input.getShortDescription() != null ? input.getShortDescription().trim() : "", input.getDescription() != null ? input.getDescription().trim() : "", input.getImageUrl() != null ? input.getImageUrl().trim() : "", input.getBrand() != null ? input.getBrand().trim() : "", input.getCategoryId(), input.isActive());

        if (productId > 0) {
            presenter.present(new AddProductOutputData(true, "Thêm sản phẩm thành công", productId));
        } else {
            presenter.present(new AddProductOutputData(false, "Lỗi khi thêm sản phẩm", 0));
        }
    }
}