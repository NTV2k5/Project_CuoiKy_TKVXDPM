package business;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import business.SearchProduct.SearchProductInputData;
import business.SearchProduct.SearchProductOutputBoundary;
import business.SearchProduct.SearchProductUseCase;
import persistence.Product.ProductGateway;

class SearchProductUseCaseTest {
    private ProductGateway gateway;
    private SearchProductUseCase useCase;
    private SearchProductOutputBoundary presenter;

    @BeforeEach
    void setUp() {
        gateway = mock(ProductGateway.class);
        useCase = new SearchProductUseCase(gateway);
        presenter = mock(SearchProductOutputBoundary.class);
    }

    @Test
    void execute_success() {
        when(gateway.searchProducts("nike", 1, 50)).thenReturn(Collections.emptyList());
        when(gateway.getTotalProductCount("nike", 1)).thenReturn(10);

        useCase.execute(new SearchProductInputData("nike", 1, 50), presenter);

        verify(presenter).present(argThat(out -> out.getTotalCount() == 10));
    }
}