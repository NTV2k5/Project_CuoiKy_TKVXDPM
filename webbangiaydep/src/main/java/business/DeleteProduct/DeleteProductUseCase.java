// business/DeleteProduct/DeleteProductUseCase.java
package business.DeleteProduct;

import persistence.Product.ProductGateway;

public class DeleteProductUseCase implements DeleteProductInputBoundary {
    private final ProductGateway productGateway;

    public DeleteProductUseCase(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public void execute(DeleteProductInputData input, DeleteProductOutputBoundary presenter) {
        if (input.getId() <= 0) {
            presenter.present(new DeleteProductOutputData(false, "ID không hợp lệ"));
            return;
        }

        productGateway.deleteProduct(input.getId());

        presenter.present(new DeleteProductOutputData(true, "Xóa thành công"));
    }
}