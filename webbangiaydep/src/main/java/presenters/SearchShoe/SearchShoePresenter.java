package presenters.SearchShoe;

import java.util.ArrayList;
import java.util.List;
import business.SearchShoe.SearchShoeOutputBoundary;
import persistence.SearchShoe.SearchShoeDTO;

public class SearchShoePresenter implements SearchShoeOutputBoundary {

    
    public SearchShoeViewModel viewModel = new SearchShoeViewModel();

    @Override
    public void present(List<SearchShoeDTO> shoes) {
        
        List<SearchShoeItem> items = new ArrayList<>();

        if (shoes != null && !shoes.isEmpty()) {
            for (SearchShoeDTO dto : shoes) {
                SearchShoeItem item = new SearchShoeItem();
                item.id = dto.id;
                item.name = dto.name;
                item.price = String.format("%,.0f", dto.price) + "₫";
                item.imageUrl = dto.imageUrl;

                items.add(item);
            }
        }

        viewModel.items = items;
    }

    public SearchShoeViewModel getViewModel() 
    {
        return viewModel;
    }
}
