package business.ViewShoeCart;

import java.util.ArrayList;
import java.util.List;

import business.entity.CartItem;
import persistence.ViewShoeCart.ViewShoeCartDTO;
import persistence.ViewShoeCart.ViewShoeCartInterface;

public class ViewShoeCartUsecase implements ViewShoeCartInputBoundary {

    private final ViewShoeCartInterface gateway;
    private final ViewShoeCartOutputBoundary outputBoundary;

    public ViewShoeCartUsecase(ViewShoeCartInterface gateway, ViewShoeCartOutputBoundary outputBoundary) {
        this.gateway = gateway;
        this.outputBoundary = outputBoundary;
    }

    @Override

    public void execute(ViewShoeCartInputData inputData) 
    {

        ViewShoeCartOutputData res = new ViewShoeCartOutputData();

        try{
        List<ViewShoeCartDTO> cartItems = gateway.getCartItems(inputData.userId);

        List<CartItem> entityCart = convertToEntity(cartItems);


        List<ViewShoeCartDTO> outputDTO = new ArrayList<>();
            for (int i = 0; i < cartItems.size(); i++) 
            {
                ViewShoeCartDTO dtoOld = cartItems.get(i);
                CartItem item = entityCart.get(i);

                ViewShoeCartDTO dto = new ViewShoeCartDTO();
                dto.productId = item.getProductId();
                dto.variantId = dtoOld.variantId;
                dto.productName = dtoOld.productName;
                dto.imageUrl = dtoOld.imageUrl;
                dto.size = dtoOld.size;
                dto.color = dtoOld.color;
                dto.quantity = item.getQuantity();
                dto.price = dtoOld.price;
                outputDTO.add(dto);
            }
            double totalPrice = 0;
            for(CartItem item : entityCart)
            {
                totalPrice += item.getTotalPrice();
            }
            res.cartItems = outputDTO;
            res.totalPriceCart = totalPrice;
            res.message = null;
        }catch(IllegalArgumentException e)
        {
            res.message = e.getMessage();
        }catch(Exception e)
        {
            res.message = "Lỗi không kết nối Db";
            e.printStackTrace();
        }
        outputBoundary.present(res);
    }

    public List<CartItem> convertToEntity(List<ViewShoeCartDTO> dtos)
    {
        List<CartItem> entityCart = new ArrayList<>();
        for (ViewShoeCartDTO dto : dtos) {
                CartItem item = new CartItem(
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
