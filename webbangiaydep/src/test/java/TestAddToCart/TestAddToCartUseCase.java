package TestAddToCart;

import business.AddToCart.*;
import persistence.AddToCart.AddToCartDTO;
import persistence.AddToCart.AddToCartDTO.CartItemDTO;
import presenters.AddToCart.AddToCartPresenter;
import presenters.AddToCart.AddToCartViewModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class TestAddToCartUseCase {

    private static class MockRepository implements AddToCartRepository {
        private AddToCartDTO cartInDB;
        private final CartItemDTO variantInDB;

        public MockRepository(CartItemDTO variantInDB, AddToCartDTO cartInDB) {
            this.variantInDB = variantInDB;
            this.cartInDB = cartInDB;
        }

        @Override
        public AddToCartDTO findByUserId(Long userId) {
            return cartInDB;
        }

        @Override
        public void save(AddToCartDTO dto) {
            this.cartInDB = dto;
        }

        @Override
        public CartItemDTO getVariantById(Long variantId) {
            if (variantId == null || variantId <= 0) throw new IllegalArgumentException("Variant ID không hợp lệ");
            
            // SỬA: SỬ DỤNG equals() ĐỂ SO SÁNH GIÁ TRỊ CỦA LONG OBJECTS
            if (variantInDB == null || !variantInDB.variantId.equals(variantId))
                throw new IllegalArgumentException("Sản phẩm không tồn tại");
                
            return variantInDB;
        }

        @Override
        public void removeCartItem(Long userId, Long productId, Long variantId) {
            throw new UnsupportedOperationException("Unimplemented method 'removeCartItem'");
        }
    }

    private CartItemDTO createVariant(Long id, int stock, double price) {
        CartItemDTO dto = new CartItemDTO();
        dto.variantId = id;
        dto.productId = 100L;
        dto.stock = stock;
        dto.unitPrice = price;
        return dto;
    }

    private AddToCartDTO createCart(Long userId, ArrayList<CartItemDTO> items) {
        AddToCartDTO dto = new AddToCartDTO();
        dto.cartId = 1L;
        dto.userId = userId;
        dto.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
        return dto;
    }

    // ==================================================================
    // 1. TEST: USER CHƯA ĐĂNG NHẬP 
    // ==================================================================
    @Test
    public void testInvalidInput_UserIdNull() {
        AddToCartViewModel viewModel = new AddToCartViewModel();
        AddToCartPresenter presenter = new AddToCartPresenter(viewModel);
        AddToCartRepository repo = new MockRepository(null, null);

        AddToCartUseCase useCase = new AddToCartUseCase(repo);
        AddToCartInputData input = new AddToCartInputData(null, 100L, 1L, 1);

        useCase.execute(input, presenter);

        assertFalse(viewModel.success);
        assertEquals("Yêu Cầu Đăng nhập trước khi thêm vào giỏ hàng", viewModel.message);
    }

    // ==================================================================
    // 2. TEST: SỐ LƯỢNG = 0
    // ==================================================================
    @Test
    public void testInvalidInput_QuantityZero() {
        AddToCartViewModel viewModel = new AddToCartViewModel();
        AddToCartPresenter presenter = new AddToCartPresenter(viewModel);
        AddToCartRepository repo = new MockRepository(null, null);

        AddToCartUseCase useCase = new AddToCartUseCase(repo);
        AddToCartInputData input = new AddToCartInputData(1L, 100L, 1L, 0); // SỬA: 1L

        useCase.execute(input, presenter);

        assertFalse(viewModel.success);
        assertEquals("Số lượng phải lớn hơn 0", viewModel.message);
    }

    // ==================================================================
    // 3. TEST: KHÔNG ĐỦ HÀNG
    // ==================================================================
    @Test
    public void testNotEnoughStock() {
        AddToCartViewModel viewModel = new AddToCartViewModel();
        AddToCartPresenter presenter = new AddToCartPresenter(viewModel);

        CartItemDTO variant = createVariant(1L, 2, 1000000);
        AddToCartRepository repo = new MockRepository(variant, null);

        AddToCartUseCase useCase = new AddToCartUseCase(repo);
        AddToCartInputData input = new AddToCartInputData(1L, 100L, 1L, 5); // SỬA: 1L

        useCase.execute(input, presenter);

        assertFalse(viewModel.success);
        assertEquals("Chỉ còn 2 sản phẩm trong kho", viewModel.message);
    }

    // ==================================================================
    // 4. TEST: THÊM MỚI GIỎ
    // ==================================================================
    @Test
    public void testAddToNewCart() {
        AddToCartViewModel viewModel = new AddToCartViewModel();
        AddToCartPresenter presenter = new AddToCartPresenter(viewModel);

        CartItemDTO variant = createVariant(1L, 10, 1000000);
        MockRepository repo = new MockRepository(variant, null);

        AddToCartUseCase useCase = new AddToCartUseCase(repo);
        AddToCartInputData input = new AddToCartInputData(1L, 100L, 1L, 2); // SỬA: 1L

        useCase.execute(input, presenter);

        assertTrue(viewModel.success);
        assertEquals("Thêm vào giỏ thành công!", viewModel.message);
        assertEquals(2000000, viewModel.totalPrice, 0.01);

        AddToCartDTO savedCart = repo.findByUserId(1L);
        assertNotNull(savedCart);
        assertEquals(1, savedCart.items.size());
        assertEquals(2, savedCart.items.get(0).quantity);
    }

    // ==================================================================
    // 5. TEST: CẬP NHẬT GIỎ
    // ==================================================================
    @Test
    public void testUpdateExistingItem() {
        AddToCartViewModel viewModel = new AddToCartViewModel();
        AddToCartPresenter presenter = new AddToCartPresenter(viewModel);

        CartItemDTO variant = createVariant(1L, 10, 1000000);

        ArrayList<CartItemDTO> items = new ArrayList<>();
        CartItemDTO existing = new CartItemDTO();
        existing.productId = 100L;
        existing.variantId = 1L;
        existing.quantity = 3;
        existing.unitPrice = 1000000;
        items.add(existing);

        AddToCartDTO existingCart = createCart(1L, items); // SỬA: 1L
        MockRepository repo = new MockRepository(variant, existingCart);

        AddToCartUseCase useCase = new AddToCartUseCase(repo);
        AddToCartInputData input = new AddToCartInputData(1L, 100L, 1L, 2); // SỬA: 1L

        useCase.execute(input, presenter);

        assertTrue(viewModel.success);
        assertEquals("Thêm vào giỏ thành công!", viewModel.message);
        assertEquals(5000000, viewModel.totalPrice, 0.01);

        AddToCartDTO savedCart = repo.findByUserId(1L);
        assertEquals(5, savedCart.items.get(0).quantity);
    }

    // ==================================================================
    // 6. TEST: THÊM SẢN PHẨM KHÁC
    // ==================================================================
    @Test
    public void testAddDifferentProduct() {
        AddToCartViewModel viewModel = new AddToCartViewModel();
        AddToCartPresenter presenter = new AddToCartPresenter(viewModel);

        CartItemDTO variant2 = createVariant(2L, 5, 1500000);

        ArrayList<CartItemDTO> items = new ArrayList<>();
        CartItemDTO existing = new CartItemDTO();
        existing.productId = 100L;
        existing.variantId = 1L;
        existing.quantity = 1;
        existing.unitPrice = 1000000;
        items.add(existing);

        AddToCartDTO cart = createCart(1L, items);
        MockRepository repo = new MockRepository(variant2, cart);

        AddToCartUseCase useCase = new AddToCartUseCase(repo);
        AddToCartInputData input = new AddToCartInputData(1L, 101L, 2L, 1);

        useCase.execute(input, presenter);

        assertTrue(viewModel.success);
        assertEquals("Thêm vào giỏ thành công!", viewModel.message);
        assertEquals(2500000, viewModel.totalPrice, 0.01);

        AddToCartDTO saved = repo.findByUserId(1L);
        assertEquals(2, saved.items.size());
    }
}