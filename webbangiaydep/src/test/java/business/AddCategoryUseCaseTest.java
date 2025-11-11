package business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import business.AddCategory.AddCategoryInputData;
import business.AddCategory.AddCategoryOutputBoundary;
import business.AddCategory.AddCategoryUseCase;
import persistence.Category.CategoryGateway;

class AddCategoryUseCaseTest {
    private CategoryGateway gateway;
    private AddCategoryUseCase useCase;
    private AddCategoryOutputBoundary presenter;

    @BeforeEach
    void setUp() {
        gateway = mock(CategoryGateway.class);
        useCase = new AddCategoryUseCase(gateway);
        presenter = mock(AddCategoryOutputBoundary.class);
    }

    @Test
    void execute_success() {
        AddCategoryInputData input = new AddCategoryInputData("SHOES", "Giày", "Giày thể thao", null);
        when(gateway.codeExists("SHOES")).thenReturn(false);
        when(gateway.createCategory("SHOES", "Giày", "Giày thể thao", null)).thenReturn(1L);

        useCase.execute(input, presenter);

        verify(gateway).createCategory("SHOES", "Giày", "Giày thể thao", null);
        verify(presenter).present(argThat(out -> out.isSuccess() && out.getCategoryId() == 1));
    }

    @Test
    void execute_codeEmpty() {
        AddCategoryInputData input = new AddCategoryInputData("", "Giày", "", null);
        useCase.execute(input, presenter);
        verify(presenter).present(argThat(out -> !out.isSuccess() && out.getMessage().contains("Code")));
    }

    @Test
    void execute_nameEmpty() {
        AddCategoryInputData input = new AddCategoryInputData("SHOES", "", "", null);
        useCase.execute(input, presenter);
        verify(presenter).present(argThat(out -> !out.isSuccess() && out.getMessage().contains("Tên")));
    }

    @Test
    void execute_codeExists() {
        AddCategoryInputData input = new AddCategoryInputData("SHOES", "Giày", "", null);
        when(gateway.codeExists("SHOES")).thenReturn(true);
        useCase.execute(input, presenter);
        verify(presenter).present(argThat(out -> !out.isSuccess() && out.getMessage().contains("Code đã tồn tại")));
    }
}