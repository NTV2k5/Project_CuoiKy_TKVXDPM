package business;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import business.GetOrders.GetOrdersInputData;
import business.GetOrders.GetOrdersOutputBoundary;
import business.GetOrders.GetOrdersUseCase;
import persistence.Order.OrderGateway;

class GetOrdersUseCaseTest {
    private OrderGateway gateway;
    private GetOrdersUseCase useCase;
    private GetOrdersOutputBoundary presenter;

    @BeforeEach
    void setUp() {
        gateway = mock(OrderGateway.class);
        useCase = new GetOrdersUseCase(gateway);
        presenter = mock(GetOrdersOutputBoundary.class);
    }

    @Test
    void execute_success() {
        when(gateway.searchOrders("nike", "PENDING", 100)).thenReturn(Collections.emptyList());
        when(gateway.getTotalOrderCount("nike", "PENDING")).thenReturn(5);

        useCase.execute(new GetOrdersInputData("nike", "PENDING"), presenter);

        verify(presenter).present(argThat(out -> out.isSuccess() && out.getTotalCount() == 5));
    }

    @Test
    void execute_exception() {
        when(gateway.searchOrders(any(), any(), anyInt())).thenThrow(RuntimeException.class);
        useCase.execute(new GetOrdersInputData("", ""), presenter);
        verify(presenter).present(argThat(out -> out.getError().contains("Lỗi")));
    }
}