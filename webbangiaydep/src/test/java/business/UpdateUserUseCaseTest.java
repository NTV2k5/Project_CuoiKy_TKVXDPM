package business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import business.UpdateUser.UpdateUserInputData;
import business.UpdateUser.UpdateUserOutputBoundary;
import business.UpdateUser.UpdateUserUseCase;
import persistence.User.UserGateway;

class UpdateUserUseCaseTest {
    private UserGateway gateway;
    private UpdateUserUseCase useCase;
    private UpdateUserOutputBoundary presenter;

    @BeforeEach
    void setUp() {
        gateway = mock(UserGateway.class);
        useCase = new UpdateUserUseCase(gateway);
        presenter = mock(UpdateUserOutputBoundary.class);
    }

    @Test
    void execute_success() {
        doNothing().when(gateway).updateUser(1, "Nguyen Van A", "0123", 2, true);
        useCase.execute(new UpdateUserInputData(1, "Nguyen Van A", "0123", 2, true), presenter);
        verify(presenter).presentSuccess();
    }

    @Test
    void execute_nameEmpty() {
        useCase.execute(new UpdateUserInputData(1, "", "", 2, true), presenter);
        verify(presenter).presentError("Họ tên là bắt buộc");
    }
}