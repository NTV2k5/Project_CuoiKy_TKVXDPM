package TestAddToCart;

import business.AddToCart.*;
import persistence.AddToCart.AddToCartGateway;
import persistence.AddToCart.AddToCartDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestAddToCartUseCase 
{

    private AddToCartGateway mockDao;
    private AddToCartUseCase useCase;
    private TestPresenter presenter;

    // Presenter giả lập, chỉ capture output data
    static class TestPresenter implements AddToCartOutputBoundary {
        AddToCartOutputData res;
        @Override
        public void present(AddToCartOutputData outputData) {
            this.res = outputData;
        }
    }

    @BeforeEach
    void setup() {
        mockDao = mock(AddToCartGateway.class);
        useCase = new AddToCartUseCase(mockDao);
        presenter = new TestPresenter();
    }

    @Test
    void testAddToCartSuccess() throws Exception {
        AddToCartInputData input = new AddToCartInputData(1, 10, 5, 2, 100.0);

        AddToCartDTO mockDTO = new AddToCartDTO();
        mockDTO.cartId = 1;
        mockDTO.userId = 1;
        mockDTO.totalItemCount = 2;

        when(mockDao.addItemToCart(1, 10, 5, 2)).thenReturn(mockDTO);

        useCase.execute(input, presenter);

        // Kiểm tra số lượng sản phẩm trong giỏ hàng
        assertEquals(2, presenter.res.cartItemCount);

        // Message không set khi thành công → presenter hoặc UI sẽ handle
        assertNull(presenter.res.message);

        verify(mockDao).addItemToCart(1, 10, 5, 2);
    }

    @Test
    void testAddToCartInvalidQuantity() {
        AddToCartInputData input = new AddToCartInputData(1, 10, 5, 0, 100.0);

        useCase.execute(input, presenter);

        assertEquals(0, presenter.res.cartItemCount);
        assertEquals("Số lượng phải lớn hơn 0.", presenter.res.message);

        verifyNoInteractions(mockDao);
    }

    @Test
    void testAddToCartInvalidProductId() {
        AddToCartInputData input = new AddToCartInputData(1, 0, 5, 2, 100.0);

        useCase.execute(input, presenter);

        assertEquals(0, presenter.res.cartItemCount);
        assertEquals("Product ID không hợp lệ.", presenter.res.message);

        verifyNoInteractions(mockDao);
    }

    @Test
    void testAddToCartDaoThrowsException() throws Exception {
        AddToCartInputData input = new AddToCartInputData(1, 10, 5, 2, 100.0);

        when(mockDao.addItemToCart(1, 10, 5, 2)).thenThrow(new RuntimeException("DB lỗi"));

        useCase.execute(input, presenter);

        assertEquals(0, presenter.res.cartItemCount);
        assertEquals("Lỗi hệ thống. Vui lòng thử lại sau.", presenter.res.message);

        verify(mockDao).addItemToCart(1, 10, 5, 2);
    }
}
