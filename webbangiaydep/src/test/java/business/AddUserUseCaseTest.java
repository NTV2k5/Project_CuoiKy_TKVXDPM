package business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import business.AddUser.AddUserInputData;
import business.AddUser.AddUserOutputBoundary;
import business.AddUser.AddUserUseCase;
import persistence.User.UserGateway;

class AddUserUseCaseTest {
    private UserGateway gateway;
    private AddUserUseCase useCase;
    private AddUserOutputBoundary presenter;

    @BeforeEach
    void setUp() {
        gateway = mock(UserGateway.class);
        useCase = new AddUserUseCase(gateway);
        presenter = mock(AddUserOutputBoundary.class);
    }

    @Test
    void execute_success() {
        AddUserInputData input = new AddUserInputData("user@example.com", "123", "Nguyen Van A", "0123456789", 2);
        when(gateway.emailExists("user@example.com")).thenReturn(false);
        when(gateway.createUser(anyString(), anyString(), anyInt(), anyString(), anyString())).thenReturn(1L);

        useCase.execute(input, presenter);

        verify(gateway).createUser("user@example.com", "123", 2, "Nguyen Van A", "0123456789");
        verify(presenter).presentSuccess();
    }

    @Test
    void execute_emailEmpty() {
        AddUserInputData input = new AddUserInputData("", "123", "", "A", 7);
        useCase.execute(input, presenter);
        verify(presenter).presentError("Email và mật khẩu là bắt buộc");
    }

    @Test
    void execute_emailExists() {
        AddUserInputData input = new AddUserInputData("user@example.com", "123", "user", "A", 4);
        when(gateway.emailExists("user@example.com")).thenReturn(true);
        useCase.execute(input, presenter);
        verify(presenter).presentError("Email đã tồn tại");
    }
}