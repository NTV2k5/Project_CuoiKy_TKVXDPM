package business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import business.Login.LoginInputData;
import business.Login.LoginOutputBoundary;
import business.Login.LoginUseCase;
import persistence.User.UserGateway;

class LoginUseCaseTest {
    private UserGateway gateway;
    private LoginUseCase useCase;
    private LoginOutputBoundary presenter;

    @BeforeEach
    void setUp() {
        gateway = mock(UserGateway.class);
        useCase = new LoginUseCase(gateway);
        presenter = mock(LoginOutputBoundary.class);
    }

    @Test
    void execute_success() {
        when(gateway.authenticate("a@b.c", "123")).thenReturn(1L);
        when(gateway.getRoleCodeByUserId(1L)).thenReturn("ADMIN");

        useCase.execute(new LoginInputData("a@b.c", "123"), presenter);

        verify(presenter).present(argThat(out -> out.isSuccess() && "ADMIN".equals(out.getRoleCode())));
    }

    @Test
    void execute_wrongCredentials() {
        when(gateway.authenticate(anyString(), anyString())).thenReturn(null);
        useCase.execute(new LoginInputData("a@b.c", "123"), presenter);
        verify(presenter).present(argThat(out -> out.getMessage().contains("không đúng")));
    }
}