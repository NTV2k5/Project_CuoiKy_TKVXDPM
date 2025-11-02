package business.ViewShoeCart;

import java.util.List;
import persistence.ViewShoeCart.ViewShoeCartDTO;

public interface ViewShoeCartOutputBoundary {
    void present(List<ViewShoeCartDTO> cartItems);
}
