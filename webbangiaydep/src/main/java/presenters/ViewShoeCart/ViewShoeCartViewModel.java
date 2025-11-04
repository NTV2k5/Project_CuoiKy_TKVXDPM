package presenters.ViewShoeCart;

import java.util.List;

public class ViewShoeCartViewModel {
    public List<ViewShoeCartItem> items;
    public double totalAmount;

    public void setItems(List<ViewShoeCartItem> items) 
    {
        this.items = items;
        this.totalAmount = items.stream().mapToDouble(i -> i.totalPrice).sum();
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    
}
