package presenters.ViewShoeCart;

import java.util.ArrayList;
import java.util.List;

import business.ViewShoeCart.ViewShoeCartOutputBoundary;
import business.ViewShoeCart.ViewShoeCartOutputData;
import persistence.ViewShoeCart.ViewShoeCartDTO;

public class ViewShoeCartPresenter implements ViewShoeCartOutputBoundary {

    private final ViewShoeCartViewModel viewModel;

    public ViewShoeCartPresenter(ViewShoeCartViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(ViewShoeCartOutputData output) {
        List<ViewShoeCartItem> items = new ArrayList<>();

        if (output.cartItems != null) {
            for (ViewShoeCartDTO dto : output.cartItems) {
                ViewShoeCartItem item = new ViewShoeCartItem();
                item.productId = dto.productId;
                item.productName = dto.productName;
                item.imageUrl = dto.imageUrl;
                item.size = dto.size;
                item.color = dto.color;
                item.quantity = dto.quantity;
                item.price = dto.price;
                item.totalPrice = dto.price; 
                items.add(item);
            }
        }

        viewModel.setItems(items);
        viewModel.setMessage(output.message); // gán thông báo lỗi nếu có
    }

    public ViewShoeCartViewModel getViewModel() {
        return viewModel;
    }
}
