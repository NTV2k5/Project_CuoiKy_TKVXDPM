package business;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import business.SearchCategory.SearchCategoryInputData;
import business.SearchCategory.SearchCategoryOutputBoundary;
import business.SearchCategory.SearchCategoryUseCase;
import persistence.Category.CategoryGateway;

class SearchCategoryUseCaseTest {
    private CategoryGateway gateway;
    private SearchCategoryUseCase useCase;
    private SearchCategoryOutputBoundary presenter;

    @BeforeEach
    void setUp() {
        gateway = mock(CategoryGateway.class);
        useCase = new SearchCategoryUseCase(gateway);
        presenter = mock(SearchCategoryOutputBoundary.class);
    }

    @Test
    void execute_success() {
        when(gateway.searchCategories("giay", null, 50)).thenReturn(Collections.emptyList());
        when(gateway.getTotalCategoryCount("giay", null)).thenReturn(3);

        useCase.execute(new SearchCategoryInputData("giay", null, 50), presenter);

        verify(presenter).present(argThat(out -> out.getTotalCount() == 3));
    }
}