// business/AddToCart/AddToCartUseCase.java
package business.AddToCart;

import persistence.AddToCart.AddToCartGateway;

import java.time.LocalDateTime;

import business.entity.CartItem;
import business.entity.ShoeVariant;
import persistence.AddToCart.AddToCartDTO;

public class AddToCartUseCase implements AddToCartInputBoundary {

    private final AddToCartGateway dao;

    public AddToCartUseCase(AddToCartGateway dao) {
        this.dao = dao;
    }

    @Override
    public void execute(AddToCartInputData input, AddToCartOutputBoundary presenter) {
        AddToCartOutputData res = new AddToCartOutputData();
        try {
            
            CartItem cartItem = new CartItem(input.userId, input.productId, input.variantId, input.quantity, input.price);

            cartItem.validate();
            AddToCartDTO resultDTO = dao.addItemToCart(
                    input.getUserId(),
                    cartItem.getProductId(),
                    cartItem.getVariantId(),
                    cartItem.getQuantity()
            );
            res.cartItemCount = resultDTO.totalItemCount;
            res.message = null;
            
        } catch (IllegalArgumentException e) {
        // Lỗi nghiệp vụ do validate
            res.message = e.getMessage();
        } catch (Exception e) {
        // Lỗi hệ thống
            res.message = "Lỗi hệ thống. Vui lòng thử lại sau.";
            e.printStackTrace(); // ghi log cho dev
        }


        // === 4. TRẢ KẾT QUẢ QUA PRESENTER ===
        presenter.present(res);
    }

}