package presenters.ViewShoeCart;

import java.util.List;

public class ViewShoeCartViewModel {
    private List<ViewShoeCartItem> items;
    private String message;

    public List<ViewShoeCartItem> getItems() { 
        return items; 
    }

    public void setItems(List<ViewShoeCartItem> items) { 
        this.items = items; 
    }

    public String getMessage() { 
        return message; 
    }

    public void setMessage(String message) { 
        this.message = message; 
    }
}
