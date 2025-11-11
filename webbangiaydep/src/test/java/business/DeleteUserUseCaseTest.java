package business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import business.DeleteUser.DeleteUserInputData;
import business.DeleteUser.DeleteUserOutputBoundary;
import business.DeleteUser.DeleteUserUseCase;
import persistence.User.UserGateway;

class DeleteUserUseCaseTest {
    private UserGateway gateway;
    private DeleteUserUseCase useCase;
    private DeleteUserOutputBoundary presenter;

    @BeforeEach
    void setUp() {
        gateway = mock(UserGateway.class);
        useCase = new DeleteUserUseCase(gateway);
        presenter = mock(DeleteUserOutputBoundary.class);
    }

    @Test
    void execute_success() {
        doNothing().when(gateway).deleteUser(1);
        useCase.execute(new DeleteUserInputData(1), presenter);
        verify(gateway).deleteUser(1);
        verify(presenter).presentSuccess();
    }

    @Test
    void execute_exception() {
        doThrow(RuntimeException.class).when(gateway).deleteUser(1);
        useCase.execute(new DeleteUserInputData(1), presenter);
        verify(presenter).presentError("Xóa người dùng thất bại");
    }
}