package presenters.ViewShoeCart;

import java.util.ArrayList;
import java.util.List;
import business.ViewShoeCart.ViewShoeCartOutputBoundary;
import persistence.ViewShoeCart.ViewShoeCartDTO;

public class ViewShoeCartPresenter implements ViewShoeCartOutputBoundary {

    private final ViewShoeCartViewModel viewModel;

    public ViewShoeCartPresenter(ViewShoeCartViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(List<ViewShoeCartDTO> cartItem) {
        List<ViewShoeCartItem> items = new ArrayList<>();

        for (ViewShoeCartDTO dto : cartItem) 
        {
            ViewShoeCartItem item = new ViewShoeCartItem();
            item.productId = dto.productId;
            item.productName = dto.productName;
            item.imageUrl = dto.imageUrl;
            item.size = dto.size;
            item.color = dto.color;
            item.quantity = dto.quantity;
            item.price = dto.price;
            item.totalPrice = dto.price * dto.quantity; 
            items.add(item);

        }

        viewModel.setItems(items);
    }

    public ViewShoeCartViewModel getViewModel() {
        return viewModel;
    }
}
