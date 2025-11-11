package business.ViewShoeCart;

import java.util.ArrayList;
import java.util.List;

import business.entity.CartItem;
import persistence.ViewShoeCart.ViewShoeCartDTO;
import persistence.ViewShoeCart.ViewShoeCartGateway;

public class ViewShoeCartUsecase implements ViewShoeCartInputBoundary {

    private final ViewShoeCartGateway gateway;
    private final ViewShoeCartOutputBoundary outputBoundary;

    public ViewShoeCartUsecase(ViewShoeCartGateway gateway, ViewShoeCartOutputBoundary outputBoundary) {
        this.gateway = gateway;
        this.outputBoundary = outputBoundary;
    }

    @Override

    public void execute(ViewShoeCartInputData inputData) 
    {

        ViewShoeCartOutputData res = new ViewShoeCartOutputData();

        try{
        // Gọi gateway với cả userId và sessionId
        List<ViewShoeCartDTO> cartItems = gateway.getCartItems(inputData.userId);

        List<CartItem> entityCart = convertToEntity(cartItems);

        for(CartItem item : entityCart)
        {
            item.validate();
            item.setPrice(item.getTotalPrice());
        }

        List<ViewShoeCartDTO> outputDTO = new ArrayList<>();
            for (int i = 0; i < cartItems.size(); i++) 
            {
                ViewShoeCartDTO dtoOld = cartItems.get(i);
                CartItem item = entityCart.get(i);

                ViewShoeCartDTO dto = new ViewShoeCartDTO();
                dto.productId = item.getProductId();
                dto.productName = dtoOld.productName; // giữ tên cũ
                dto.imageUrl = dtoOld.imageUrl;
                dto.size = dtoOld.size;
                dto.color = dtoOld.color;
                dto.quantity = item.getQuantity();
                dto.price = item.getPrice(); // giá đã tính tổng
                outputDTO.add(dto);
            }
            res.cartItems = outputDTO;
            res.message = null;
        }catch(IllegalArgumentException e)
        {
            res.message = e.getMessage();
        }catch(Exception e)
        {
            res.message = "Lỗi không kết nối Db";
            e.printStackTrace();
        }

        // Gửi dữ liệu sang presenter để hiển thị
        outputBoundary.present(res);
    }

    public List<CartItem> convertToEntity(List<ViewShoeCartDTO> dtos)
    {
        List<CartItem> entityCart = new ArrayList<>();
        for (ViewShoeCartDTO dto : dtos) {
                CartItem item = new CartItem(
                        dto.cartId,
                        dto.productId,
                        dto.variantId,             
                        dto.quantity,
                        dto.price
                );
                entityCart.add(item);
            }
            return entityCart;
    }
}
