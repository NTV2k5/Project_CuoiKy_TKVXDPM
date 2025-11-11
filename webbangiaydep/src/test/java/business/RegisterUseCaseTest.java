package business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import business.Register.RegisterInputData;
import business.Register.RegisterOutputBoundary;
import business.Register.RegisterUseCase;
import persistence.User.UserGateway;

class RegisterUseCaseTest {
    private UserGateway gateway;
    private RegisterUseCase useCase;
    private RegisterOutputBoundary presenter;

    @BeforeEach
    void setUp() {
        gateway = mock(UserGateway.class);
        useCase = new RegisterUseCase(gateway);
        presenter = mock(RegisterOutputBoundary.class);
    }

    @Test
    void execute_success() {
        when(gateway.emailExists("a@b.c")).thenReturn(false);
        when(gateway.createUser(anyString(), anyString(), eq(2), anyString(), anyString())).thenReturn(1L);

        useCase.execute(new RegisterInputData("a@b.c", "123", "Nguyen A", "0123", 2), presenter);

        verify(presenter).present(argThat(out -> out.isSuccess()));
    }

    @Test
    void execute_emailExists() {
        when(gateway.emailExists("a@b.c")).thenReturn(true);
        useCase.execute(new RegisterInputData("a@b.c", "123", "A", "", 2), presenter);
        verify(presenter).present(argThat(out -> out.getMessage().contains("Email đã tồn tại")));
    }
}