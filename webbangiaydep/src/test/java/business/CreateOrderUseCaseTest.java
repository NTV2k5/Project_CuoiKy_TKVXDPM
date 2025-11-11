package business;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import business.CreateOrder.CreateOrderInputData;
import business.CreateOrder.CreateOrderOutputBoundary;
import business.CreateOrder.CreateOrderUseCase;
import persistence.Order.OrderGateway;

class CreateOrderUseCaseTest {
    private OrderGateway gateway;
    private CreateOrderUseCase useCase;
    private CreateOrderOutputBoundary presenter;

    @BeforeEach
    void setUp() {
        gateway = mock(OrderGateway.class);
        useCase = new CreateOrderUseCase(gateway);
        presenter = mock(CreateOrderOutputBoundary.class);
    }

    @Test
    void execute_success() {
        List<CreateOrderInputData.OrderItemInput> items = List.of(
            new CreateOrderInputData.OrderItemInput(1, 2, 500000)
        );
        CreateOrderInputData input = new CreateOrderInputData(1, 1, "COD", 1000000, items);

        // DÙNG TOÀN BỘ MATCHER → ĐÚNG QUY TẮC
        when(gateway.createOrder(anyLong(), anyLong(), anyString(), anyDouble(), anyList())).thenReturn(10L);

        useCase.execute(input, presenter);

        // DÙNG TOÀN BỘ MATCHER TRONG VERIFY
        verify(gateway).createOrder(
            eq(1L), eq(1L), eq("COD"), eq(1000000.0), anyList()
        );
        verify(presenter).present(argThat(out -> out.isSuccess() && out.getOrderId() == 10));
    }

    @Test
    void execute_emptyItems() {
        CreateOrderInputData input = new CreateOrderInputData(1, 1, "COD", 1000000, List.of());
        useCase.execute(input, presenter);
        verify(presenter).present(argThat(out -> out.getMessage().contains("trống")));
    }
}