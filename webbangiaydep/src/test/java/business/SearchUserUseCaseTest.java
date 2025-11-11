package business;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import business.SearchUser.SearchUserInputData;
import business.SearchUser.SearchUserOutputBoundary;
import business.SearchUser.SearchUserUseCase;
import persistence.User.UserGateway;

class SearchUserUseCaseTest {
    private UserGateway gateway;
    private SearchUserUseCase useCase;
    private SearchUserOutputBoundary presenter;

    @BeforeEach
    void setUp() {
        gateway = mock(UserGateway.class);
        useCase = new SearchUserUseCase(gateway);
        presenter = mock(SearchUserOutputBoundary.class);
    }

    @Test
    void execute_success() {
        when(gateway.searchUsers("admin", "ADMIN", 50)).thenReturn(Collections.emptyList());
        when(gateway.getTotalUserCount("admin", "ADMIN")).thenReturn(2);

        useCase.execute(new SearchUserInputData("admin", "ADMIN", 50), presenter);

        verify(presenter).present(anyList(), eq(2));
    }
}