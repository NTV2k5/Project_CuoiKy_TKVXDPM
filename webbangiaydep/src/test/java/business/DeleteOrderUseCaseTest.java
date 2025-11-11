package business;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import business.DeleteOrder.DeleteOrderInputData;
import business.DeleteOrder.DeleteOrderOutputBoundary;
import business.DeleteOrder.DeleteOrderUseCase;
import persistence.Order.OrderDTO;
import persistence.Order.OrderGateway;

class DeleteOrderUseCaseTest {
    private OrderGateway gateway;
    private DeleteOrderUseCase useCase;
    private DeleteOrderOutputBoundary presenter;

    @BeforeEach
    void setUp() {
        gateway = mock(OrderGateway.class);
        useCase = new DeleteOrderUseCase(gateway);
        presenter = mock(DeleteOrderOutputBoundary.class);
    }

    @Test
    void execute_notFound() {
        when(gateway.getOrderById(1L)).thenReturn(null);
        useCase.execute(new DeleteOrderInputData(1L), presenter);
        verify(presenter).present(argThat(out ->
            !out.isSuccess() && out.getMessage().contains("không tồn tại")
        ));
    }

    @Test
    void execute_notPending() {
        OrderDTO order = new OrderDTO(
            1L, 1L, "user@example.com", "123 Main St", "COD", "SHIPPED",
            LocalDateTime.now(), 1000000.0, "NOTE", "SHIPPER", 1
        );

        when(gateway.getOrderById(1L)).thenReturn(order);
        useCase.execute(new DeleteOrderInputData(1L), presenter);
        verify(presenter).present(argThat(out ->
            !out.isSuccess() && out.getMessage().contains("Chờ xử lý")
        ));
    }
}