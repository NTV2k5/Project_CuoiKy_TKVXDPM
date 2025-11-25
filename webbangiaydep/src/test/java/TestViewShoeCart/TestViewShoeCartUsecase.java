package TestViewShoeCart;

import business.ViewShoeCart.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.ViewShoeCart.ViewShoeCartDTO;
import persistence.ViewShoeCart.ViewShoeCartInterface;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class TestViewShoeCartUseCase {

    private ViewShoeCartInterface mockGateway;
    private ViewShoeCartOutputBoundary mockOutput;
    private ViewShoeCartUsecase usecase;

    @BeforeEach
    void setup() {
        mockGateway = mock(ViewShoeCartInterface.class);
        mockOutput = mock(ViewShoeCartOutputBoundary.class);
        usecase = new ViewShoeCartUsecase(mockGateway, mockOutput);
    }

    @Test
    void testViewCartSuccess() throws Exception {

        ViewShoeCartDTO dto1 = new ViewShoeCartDTO();
        dto1.productId = 1;
        dto1.variantId = 10;
        dto1.quantity = 2;
        dto1.price = 100.0;
        dto1.productName = "Nike Air";
        dto1.imageUrl = "img1.jpg";
        dto1.color = "Red";
        dto1.size = 42;

        ViewShoeCartDTO dto2 = new ViewShoeCartDTO();
        dto2.productId = 2;
        dto2.variantId = 20;
        dto2.quantity = 1;
        dto2.price = 200.0;
        dto2.productName = "Adidas Run";
        dto2.imageUrl = "img2.jpg";
        dto2.color = "Black";
        dto2.size = 41;
        

        List<ViewShoeCartDTO> mockData = Arrays.asList(dto1, dto2);

        when(mockGateway.getCartItems(1)).thenReturn(mockData);

        ViewShoeCartInputData input = new ViewShoeCartInputData(1);

        usecase.execute(input);

        verify(mockOutput, times(1)).present(argThat(res ->
                res.cartItems.size() == 2 &&
                res.totalPriceCart == 400.0 &&
                res.message == null
        ));
    }
}
