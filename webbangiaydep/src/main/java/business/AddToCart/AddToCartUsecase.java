    // business/AddToCart/AddToCartUsecase.java
    package business.AddToCart;

    import persistence.AddToCart.AddToCartGateway;

    public class AddToCartUsecase implements AddToCartInputBoundary 
    {
        private AddToCartGateway cartGateway;

        public AddToCartUsecase(AddToCartGateway cartGateway) {
            this.cartGateway = cartGateway;
        }

        @Override
        public void execute(AddToCartInputData input, AddToCartOutputBoundary presenter) {
            // 1. Validate đầu vào
            if (input.getQuantity() <= 0) {
                presenter.present(new AddToCartOutputData(false, "Số lượng phải > 0", 0));
                return;
            }
            if (input.getPrice() <= 0) {
                presenter.present(new AddToCartOutputData(false, "Giá không hợp lệ", 0));
                return;
            }

            // 2. Kiểm tra biến thể
            if (!cartGateway.isValidVariant(input.getProductId(), input.getSize(), input.getColor())) {
                presenter.present(new AddToCartOutputData(false, "Sản phẩm với kích thước/màu này không tồn tại", 0));
                return;
            }

            // 3. Lấy hoặc tạo cart_id
            int cartId = cartGateway.getOrCreateCartId(input.getUserId(), input.getSessionId());

            // 4. Lấy variant_id
            Integer variantId = cartGateway.getVariantId(input.getProductId(), input.getSize(), input.getColor());
            if (variantId == null) {
                presenter.present(new AddToCartOutputData(false, "Không tìm thấy variant", 0));
                return;
            }

            // 5. Thêm/cộng dồn vào giỏ
            cartGateway.addOrUpdateCartItem(cartId, input.getProductId(), variantId, input.getQuantity(), input.getPrice());

            // 6. ĐẾM TỔNG SỐ MÓN TRONG GIỎ → DÙNG cartId, KHÔNG PHẢI variantId
            int totalItems = cartGateway.getTotalItemsInCart(cartId);

            // 7. TRẢ VỀ THÀNH CÔNG + SỐ LƯỢNG
            presenter.present(new AddToCartOutputData(true, "Đã thêm vào giỏ hàng", totalItems));
        }
    }