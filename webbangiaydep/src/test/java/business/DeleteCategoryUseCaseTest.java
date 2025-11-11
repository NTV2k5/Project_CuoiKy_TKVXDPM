package business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import business.DeleteCategory.DeleteCategoryInputData;
import business.DeleteCategory.DeleteCategoryOutputBoundary;
import business.DeleteCategory.DeleteCategoryUseCase;
import persistence.Category.CategoryGateway;

class DeleteCategoryUseCaseTest {
    private CategoryGateway gateway;
    private DeleteCategoryUseCase useCase;
    private DeleteCategoryOutputBoundary presenter;

    @BeforeEach
    void setUp() {
        gateway = mock(CategoryGateway.class);
        useCase = new DeleteCategoryUseCase(gateway);
        presenter = mock(DeleteCategoryOutputBoundary.class);
    }

    @Test
    void execute_success() {
        DeleteCategoryInputData input = new DeleteCategoryInputData(1);
        useCase.execute(input, presenter);
        verify(gateway).deleteCategory(1);
        verify(presenter).present(argThat(out -> out.isSuccess()));
    }

    @Test
    void execute_invalidId() {
        DeleteCategoryInputData input = new DeleteCategoryInputData(0);
        useCase.execute(input, presenter);
        verify(presenter).present(argThat(out -> out.getMessage().contains("ID")));
    }
}