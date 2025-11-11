package business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import business.AddProduct.AddProductInputData;
import business.AddProduct.AddProductOutputBoundary;
import business.AddProduct.AddProductUseCase;
import persistence.Product.ProductGateway;

class AddProductUseCaseTest {
    private ProductGateway gateway;
    private AddProductUseCase useCase;
    private AddProductOutputBoundary presenter;

    @BeforeEach
    void setUp() {
        gateway = mock(ProductGateway.class);
        useCase = new AddProductUseCase(gateway);
        presenter = mock(AddProductOutputBoundary.class);
    }

    @Test
    void execute_success() {
        AddProductInputData input = new AddProductInputData("SKU001", "Giày Nike", "Mô tả ngắn", "Mô tả dài", "img.jpg", "Nike", 1, true);
        when(gateway.skuExists("SKU001")).thenReturn(false);
        when(gateway.createProduct(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyInt(), anyBoolean())).thenReturn(1L);

        useCase.execute(input, presenter);

        verify(gateway).createProduct("SKU001", "Giày Nike", "Mô tả ngắn", "Mô tả dài", "img.jpg", "Nike", 1, true);
        verify(presenter).present(argThat(out -> out.isSuccess() && out.getProductId() == 1));
    }

    @Test
    void execute_skuEmpty() {
        AddProductInputData input = new AddProductInputData("", "Giày", "", "", "", "", 1, true);
        useCase.execute(input, presenter);
        verify(presenter).present(argThat(out -> out.getMessage().contains("SKU")));
    }

    @Test
    void execute_nameEmpty() {
        AddProductInputData input = new AddProductInputData("SKU001", "", "", "", "", "", 1, true);
        useCase.execute(input, presenter);
        verify(presenter).present(argThat(out -> out.getMessage().contains("Tên")));
    }

    @Test
    void execute_invalidCategory() {
        AddProductInputData input = new AddProductInputData("SKU001", "Giày", "", "", "", "", 0, true);
        useCase.execute(input, presenter);
        verify(presenter).present(argThat(out -> out.getMessage().contains("Danh mục")));
    }
}