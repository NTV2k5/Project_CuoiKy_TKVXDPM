package TestAddToCart;

import business.AddToCart.*;
import business.entity.*;
import persistence.AddToCart.AddToCartDTO;
import persistence.AddToCart.AddToCartDTO.CartItemDTO;
import presenters.AddToCart.AddToCartPresenter;
import presenters.AddToCart.AddToCartViewModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class TestAddToCartUseCase {

    // === MOCK REPOSITORY ===
    private static class MockRepository implements AddToCartRepository {
        private AddToCartDTO cartInDB;
        private final CartItemDTO variantInDB;

        public MockRepository(CartItemDTO variantInDB, AddToCartDTO cartInDB) {
            this.variantInDB = variantInDB;
            this.cartInDB = cartInDB;
        }

        @Override
        public AddToCartDTO findByUserId(int userId) {
            return cartInDB;
        }

        @Override
        public void save(AddToCartDTO dto) {
            this.cartInDB = dto;
        }

        @Override
        public CartItemDTO getVariantById(int variantId) {
            if (variantId <= 0) {
                throw new IllegalArgumentException("Variant ID không hợp lệ");
            }
            if (variantInDB == null || variantInDB.variantId != variantId) {
                throw new IllegalArgumentException("Sản phẩm không tồn tại");
            }

            return variantInDB;
        }
    }

    // === HELPER: TẠO VARIANT ===
    private CartItemDTO createVariant(int id, int stock, double price) {
        CartItemDTO dto = new CartItemDTO();
        dto.variantId = id;
        dto.productId = 100;
        dto.stock = stock;
        dto.unitPrice = price;
        dto.size = "40";
        dto.color = "Red";
        dto.hexCode = "#FF0000";
        return dto;
    }

    // === HELPER: TẠO GIỎ ===
    private AddToCartDTO createCart(int userId, ArrayList<CartItemDTO> items) {
        AddToCartDTO dto = new AddToCartDTO();
        dto.cartId = 1;
        dto.userId = userId;
        dto.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
        return dto;
    }

    // ==================================================================
    // 1. TEST: INPUT SAI 
    // ==================================================================
    @Test
    public void testInvalidInput_UserId() {
        AddToCartViewModel viewModel = new AddToCartViewModel();
        AddToCartPresenter presenter = new AddToCartPresenter(viewModel);
        AddToCartRepository repo = new MockRepository(null, null);

        AddToCartUseCase useCase = new AddToCartUseCase(repo);
        AddToCartInputData input = new AddToCartInputData();
        input.userId = 0;
        input.productId = 100;
        input.variantId = 1;
        input.quantity = 1;

        useCase.execute(input, presenter);

        assertFalse(viewModel.success);
        assertEquals("Yêu Cấu Đăng nhập trước khi thêm vào giỏ hàng", viewModel.message);
    }

    @Test
    public void testInvalidInput_VariantId() {
        AddToCartViewModel vm = new AddToCartViewModel();
        AddToCartPresenter p = new AddToCartPresenter(vm);
        AddToCartRepository repo = new MockRepository(null, null);
        AddToCartUseCase useCase = new AddToCartUseCase(repo);

        AddToCartInputData input = new AddToCartInputData();
        input.userId = 1; 
        input.productId = 100; 
        input.variantId = 0; 
        input.quantity = 1;

        useCase.execute(input, p);

        assertFalse(vm.success);
        assertEquals("Variant ID không hợp lệ", vm.message);
    }

    @Test
    public void testInvalidInput_Quantity() {
        AddToCartViewModel viewModel = new AddToCartViewModel();
        AddToCartPresenter presenter = new AddToCartPresenter(viewModel);
        AddToCartRepository repo = new MockRepository(null, null);

        AddToCartUseCase useCase = new AddToCartUseCase(repo);
        AddToCartInputData input = new AddToCartInputData();
        input.userId = 1;
        input.productId = 100;
        input.variantId = 1;
        input.quantity = 0;         // SAI

        useCase.execute(input, presenter);

        assertFalse(viewModel.success);
        assertEquals("Số lượng phải lớn hơn 0", viewModel.message);
    }

    // ==================================================================
    // 2. TEST: VARIANT KHÔNG TỒN TẠI
    // ==================================================================
    @Test
    public void testVariantNotFound() {
        AddToCartViewModel viewModel = new AddToCartViewModel();
        AddToCartPresenter presenter = new AddToCartPresenter(viewModel);
        // CartItemDTO variant4 = createVariant(2, 5, 1500000);
        AddToCartRepository repo = new MockRepository(null, null); // Không có variant

        AddToCartUseCase useCase = new AddToCartUseCase(repo);
        AddToCartInputData input = new AddToCartInputData();
        input.userId = 1;
        input.productId = 100;
        input.variantId = 2;
        input.quantity = 1;

        useCase.execute(input, presenter);

        assertFalse(viewModel.success);
        assertEquals("Sản phẩm không tồn tại", viewModel.message);
        // assertEquals("Variant ID không hợp lệ", viewModel.message);
    }

    // ==================================================================
    // 3. TEST: KHÔNG ĐỦ HÀNG
    // ==================================================================
    @Test
    public void testNotEnoughStock() {
        AddToCartViewModel viewModel = new AddToCartViewModel();
        AddToCartPresenter presenter = new AddToCartPresenter(viewModel);

        CartItemDTO variant = createVariant(1, 2, 1000000); // Chỉ còn 2
        AddToCartRepository repo = new MockRepository(variant, null);

        AddToCartUseCase useCase = new AddToCartUseCase(repo);
        AddToCartInputData input = new AddToCartInputData();
        input.userId = 1;
        input.productId = 100;
        input.variantId = 1;
        input.quantity = 5; 

        useCase.execute(input, presenter);

        assertFalse(viewModel.success);
        assertEquals("Chỉ còn 2 sản phẩm trong kho", viewModel.message);
    }

    // ==================================================================
    // 4. TEST: THÊM MỚI GIỎ (CHƯA CÓ GIỎ)
    // ==================================================================
    @Test
    public void testAddToNewCart() {
        AddToCartViewModel viewModel = new AddToCartViewModel();
        AddToCartPresenter presenter = new AddToCartPresenter(viewModel);

        CartItemDTO variant = createVariant(1, 10, 1000000);
        MockRepository repo = new MockRepository(variant, null); // Chưa có giỏ

        AddToCartUseCase useCase = new AddToCartUseCase(repo);
        AddToCartInputData input = new AddToCartInputData();
        input.userId = 1;
        input.productId = 100;
        input.variantId = 1;
        input.quantity = 2;

        useCase.execute(input, presenter);

        assertTrue(viewModel.success);
        assertEquals("Thêm vào giỏ thành công!", viewModel.message);
        assertEquals(2000000, viewModel.totalPrice, 0.01);

        // Kiểm tra DB đã lưu
        AddToCartDTO savedCart = repo.findByUserId(1);
        assertNotNull(savedCart);
        assertEquals(1, savedCart.items.size());
        assertEquals(2, savedCart.items.get(0).quantity);
    }

    // ==================================================================
    // 5. TEST: CẬP NHẬT GIỎ (ĐÃ CÓ ITEM)
    // ==================================================================
    @Test
    public void testUpdateExistingItem() {
        AddToCartViewModel viewModel = new AddToCartViewModel();
        AddToCartPresenter presenter = new AddToCartPresenter(viewModel);

        CartItemDTO variant = createVariant(1, 10, 1000000);

        ArrayList<CartItemDTO> items = new ArrayList<>();
        CartItemDTO existing = new CartItemDTO();
        existing.productId = 100;
        existing.variantId = 1;
        existing.quantity = 3;
        existing.unitPrice = 1000000;
        items.add(existing);

        AddToCartDTO existingCart = createCart(1, items);
        MockRepository repo = new MockRepository(variant, existingCart);

        AddToCartUseCase useCase = new AddToCartUseCase(repo);
        AddToCartInputData input = new AddToCartInputData();
        input.userId = 1;
        input.productId = 100;
        input.variantId = 1;
        input.quantity = 2;

        useCase.execute(input, presenter);

        assertTrue(viewModel.success);
        assertEquals("Thêm vào giỏ thành công!", viewModel.message);
        assertEquals(5000000, viewModel.totalPrice, 0.01);

        AddToCartDTO savedCart = repo.findByUserId(1);
        assertNotNull(savedCart.items);
        assertEquals(1, savedCart.items.size());
        assertEquals(5, savedCart.items.get(0).quantity);
    }

    // ==================================================================
    // 6. TEST: THÊM SẢN PHẨM KHÁC VÀO GIỎ
    // ==================================================================
    @Test
    public void testAddDifferentProduct() {
        AddToCartViewModel viewModel = new AddToCartViewModel();
        AddToCartPresenter presenter = new AddToCartPresenter(viewModel);

        CartItemDTO variant1 = createVariant(1, 10, 1000000);
        CartItemDTO variant2 = createVariant(2, 5, 1500000);

        // Giỏ đã có variant 1
        ArrayList<CartItemDTO> items = new ArrayList<>();
        CartItemDTO existing = new CartItemDTO();
        existing.productId = 100;
        existing.variantId = 1;
        existing.quantity = 1;
        existing.unitPrice = 1000000;
        items.add(existing);

        AddToCartDTO cart = createCart(1, items);
        MockRepository repo = new MockRepository(variant2, cart);

        AddToCartUseCase useCase = new AddToCartUseCase(repo);
        AddToCartInputData input = new AddToCartInputData();
        input.userId = 1;
        input.productId = 101;
        input.variantId = 2;
        input.quantity = 1;

        useCase.execute(input, presenter);

        assertTrue(viewModel.success);
        assertEquals("Thêm vào giỏ thành công!", viewModel.message);
        assertEquals(2500000, viewModel.totalPrice, 0.01);

        AddToCartDTO saved = repo.findByUserId(1);
        assertEquals(2, saved.items.size()); // Có 2 sản phẩm
    }
}