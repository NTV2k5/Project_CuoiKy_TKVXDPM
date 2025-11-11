package business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import business.UpdateCategory.UpdateCategoryInputData;
import business.UpdateCategory.UpdateCategoryOutputBoundary;
import business.UpdateCategory.UpdateCategoryUseCase;
import persistence.Category.CategoryGateway;

class UpdateCategoryUseCaseTest {
    private CategoryGateway gateway;
    private UpdateCategoryUseCase useCase;
    private UpdateCategoryOutputBoundary presenter;

    @BeforeEach
    void setUp() {
        gateway = mock(CategoryGateway.class);
        useCase = new UpdateCategoryUseCase(gateway);
        presenter = mock(UpdateCategoryOutputBoundary.class);
    }

    @Test
    void execute_success() {
        when(gateway.codeExists("SHOES")).thenReturn(true);
        when(gateway.isCodeOwnedByCategory("SHOES", 1)).thenReturn(true);

        useCase.execute(new UpdateCategoryInputData(1, "SHOES", "Giày mới", "", null), presenter);

        verify(gateway).updateCategory(1, "SHOES", "Giày mới", "", null);
        verify(presenter).present(argThat(out -> out.isSuccess()));
    }

    @Test
    void execute_codeConflict() {
        when(gateway.codeExists("NEW")).thenReturn(true);
        when(gateway.isCodeOwnedByCategory("NEW", 1)).thenReturn(false);

        useCase.execute(new UpdateCategoryInputData(1, "NEW", "Giày", "", null), presenter);

        verify(presenter).present(argThat(out -> out.getMessage().contains("Code đã tồn tại")));
    }
}