package business.ViewShoeCart;

import java.util.List;
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
    public void execute(ViewShoeCartInputData inputData) {
        // Gọi gateway với cả userId và sessionId
        List<ViewShoeCartDTO> cartItems = gateway.getCartItems(inputData.userId, inputData.sessionId);

        // Gửi dữ liệu sang presenter để hiển thị
        outputBoundary.present(cartItems);
    }
}
