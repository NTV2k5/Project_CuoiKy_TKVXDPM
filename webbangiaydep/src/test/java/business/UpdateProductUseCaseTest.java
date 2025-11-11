package business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import business.UpdateProduct.UpdateProductInputData;
import business.UpdateProduct.UpdateProductOutputBoundary;
import business.UpdateProduct.UpdateProductUseCase;
import persistence.Product.ProductGateway;

class UpdateProductUseCaseTest {
    private ProductGateway gateway;
    private UpdateProductUseCase useCase;
    private UpdateProductOutputBoundary presenter;

    @BeforeEach
    void setUp() {
        gateway = mock(ProductGateway.class);
        useCase = new UpdateProductUseCase(gateway);
        presenter = mock(UpdateProductOutputBoundary.class);
    }

    @Test
    void execute_success() {
        when(gateway.skuExists("SKU001")).thenReturn(true);
        when(gateway.isSkuOwnedByProduct("SKU001", 1)).thenReturn(true);

        useCase.execute(new UpdateProductInputData(1, "SKU001", "Giày mới", "", "", "", "", 1, true), presenter);

        verify(gateway).updateProduct(1, "SKU001", "Giày mới", "", "", "", "", 1, true);
        verify(presenter).present(argThat(out -> out.isSuccess()));
    }
}