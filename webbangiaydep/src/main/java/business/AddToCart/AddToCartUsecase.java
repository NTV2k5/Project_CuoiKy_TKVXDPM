package business.AddToCart;

import java.util.ArrayList;

import business.entity.Cart;
import business.entity.CartItem;
import business.entity.ShoeVariant;
import persistence.AddToCart.AddToCartDTO;
import persistence.AddToCart.AddToCartDTO.CartItemDTO;

public class AddToCartUseCase implements AddToCartInputBoundary 
{

    private AddToCartRepository repository;
    

    public AddToCartUseCase(AddToCartRepository repository) {
		this.repository = repository;
	}


	@Override
    public void execute(AddToCartInputData input, AddToCartOutputBoundary presenter)
    {
        AddToCartOutputData res = new AddToCartOutputData();
        Cart cart = null;
        try {
            AddToCart.checkInputQuantity(input.quantity);
            AddToCart.checkInputUserId(input.userId);
            AddToCart.checkInputVariant(input.variantId);
            CartItemDTO variantDTO = repository.getVariantById(input.variantId);
            ShoeVariant variant = convertToVariantEntity(variantDTO);
            String stockError = variant.validateStock(input.quantity);
            if (stockError != null) 
            {
                throw new IllegalArgumentException(stockError);
            }
            // 4. Lấy giỏ hàng hiện tại
            AddToCartDTO cartDTO = repository.findByUserId(input.userId);
            if (cartDTO == null) {
                cartDTO = new AddToCartDTO();
                cartDTO.userId = input.userId;
                cartDTO.items = new ArrayList<>();
            }
            cart = convertToCartEntity(cartDTO);
            // 5. Tạo CartItem mới
            String addError = cart.addOrUpdateItem(input.productId,input.variantId,input.quantity,variant.getPrice());
                if (addError != null) 
                {
                    throw new IllegalArgumentException(addError);
                }
    
            // 7. Lưu giỏ hàng
            // 7. Chuyển Entity → DTO để lưu
            AddToCartDTO updatedCartDTO = convertToCartDTO(cart);
            repository.save(updatedCartDTO);
        } catch (IllegalArgumentException  e) {
            res.success = false;
            res.message = e.getMessage();
            res.totalPrice = 0;
            presenter.present(res);
            return;                  
        }
        // 8. Phản hồi thành công
        res.success = true;
        res.totalPrice = cart.getTotalPrice();
        presenter.present(res);
    }

    // ==================== CONVERTERS ====================

    private ShoeVariant convertToVariantEntity(CartItemDTO dto) {
        return new ShoeVariant(
            dto.variantId,
            dto.productId,
            dto.size,
            dto.color,
            dto.hexCode,
            dto.unitPrice,
            dto.stock
        );
    }

    private Cart convertToCartEntity(AddToCartDTO dto) 
    {
        Cart cart = new Cart(dto.cartId, dto.userId);
        if (dto.items != null) {
            for (CartItemDTO itemDTO : dto.items) {
                CartItem item = new CartItem(
                    itemDTO.productId,
                    itemDTO.variantId,
                    itemDTO.quantity,
                    itemDTO.unitPrice
                );
                cart.getItems().add(item);
            }
        }
        return cart;
    }

    private AddToCartDTO convertToCartDTO(Cart cart) {
        AddToCartDTO dto = new AddToCartDTO();
        dto.cartId = cart.getId();
        dto.userId = cart.getUserId();
        dto.items = new ArrayList<>();

        for (CartItem item : cart.getItems()) {
            CartItemDTO itemDTO = new CartItemDTO();
            itemDTO.productId = item.getProductId();
            itemDTO.variantId = item.getVariantId();
            itemDTO.quantity = item.getQuantity();
            itemDTO.unitPrice = item.getPrice();
            dto.items.add(itemDTO);
        }
        return dto;
    }
}