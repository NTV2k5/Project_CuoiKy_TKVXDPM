package business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import business.DeleteProduct.DeleteProductInputData;
import business.DeleteProduct.DeleteProductOutputBoundary;
import business.DeleteProduct.DeleteProductUseCase;
import persistence.Product.ProductGateway;

class DeleteProductUseCaseTest {
    private ProductGateway gateway;
    private DeleteProductUseCase useCase;
    private DeleteProductOutputBoundary presenter;

    @BeforeEach
    void setUp() {
        gateway = mock(ProductGateway.class);
        useCase = new DeleteProductUseCase(gateway);
        presenter = mock(DeleteProductOutputBoundary.class);
    }

    @Test
    void execute_success() {
        useCase.execute(new DeleteProductInputData(1), presenter);
        verify(gateway).deleteProduct(1);
        verify(presenter).present(argThat(out -> out.isSuccess()));
    }

    @Test
    void execute_invalidId() {
        useCase.execute(new DeleteProductInputData(0), presenter);
        verify(presenter).present(argThat(out -> out.getMessage().contains("ID")));
    }
}