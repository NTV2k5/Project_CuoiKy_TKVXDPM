// persistence/ViewShoeCart/ViewShoeCartGateway.java
package persistence.ViewShoeCart;

import java.util.List;

public interface ViewShoeCartInterface {
    List<ViewShoeCartDTO> getCartItems(int userId);
}