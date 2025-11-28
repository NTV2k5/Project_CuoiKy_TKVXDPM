package business.ViewShoeCart;

import java.util.List;

import persistence.ViewShoeCart.ViewShoeCartDTO;

public class ViewShoeCartOutputData 
{
    public List<ViewShoeCartDTO> cartItems;
    public double totalPriceCart;
    public String message;
}
