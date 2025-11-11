// src/test/java/business/UpdateOrderStatus/UpdateOrderStatusUseCaseTest.java
package business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import business.UpdateOrderStatus.UpdateOrderStatusInputData;
import business.UpdateOrderStatus.UpdateOrderStatusOutputBoundary;
import business.UpdateOrderStatus.UpdateOrderStatusUseCase;
import persistence.Order.OrderGateway;

class UpdateOrderStatusUseCaseTest {
    private OrderGateway gateway;
    private UpdateOrderStatusUseCase useCase;
    private UpdateOrderStatusOutputBoundary presenter;

    @BeforeEach
    void setUp() {
        gateway = mock(OrderGateway.class);
        useCase = new UpdateOrderStatusUseCase(gateway);
        presenter = mock(UpdateOrderStatusOutputBoundary.class);
    }

    @Test
    void execute_success() {
        doNothing().when(gateway).updateOrderStatus(1, "SHIPPED", 2L);
        useCase.execute(new UpdateOrderStatusInputData(1, "SHIPPED", 2L), presenter);
        verify(presenter).present(argThat(out -> out.isSuccess()));
    }

    @Test
    void execute_exception() {
        doThrow(RuntimeException.class).when(gateway).updateOrderStatus(anyLong(), anyString(), any());
        useCase.execute(new UpdateOrderStatusInputData(1, "SHIPPED", 2L), presenter);
        verify(presenter).present(argThat(out -> out.getMessage().contains("thất bại")));
    }
}