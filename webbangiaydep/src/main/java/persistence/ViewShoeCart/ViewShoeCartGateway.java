// persistence/ViewShoeCart/ViewShoeCartGateway.java
package persistence.ViewShoeCart;

import java.util.List;

public interface ViewShoeCartGateway {
    List<ViewShoeCartDTO> getCartItems(int userId);
}