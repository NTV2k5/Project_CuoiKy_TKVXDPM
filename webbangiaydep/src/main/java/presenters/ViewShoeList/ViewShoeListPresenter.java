package presenters.ViewShoeList;

import java.util.ArrayList;
import java.util.List;

import business.ViewShoeList.ViewShoeListOutputBoundary;
import persistence.ViewShoeList.ViewShoeListDTO;

public class ViewShoeListPresenter implements ViewShoeListOutputBoundary {

    // ViewModel để lưu dữ liệu sau khi trình bày
    public ViewShoeListViewModel viewModel = new ViewShoeListViewModel();

    @Override
    public void presentShoeList(List<ViewShoeListDTO> shoes) 
    {
        // Tạo danh sách item để hiển thị
        List<ViewShoeListItem> items = new ArrayList<>();

        for (ViewShoeListDTO dto : shoes) {
            ViewShoeListItem item = new ViewShoeListItem();
            item.id = dto.id;
            item.name = dto.name;
            item.price = String.format("%,.0f", dto.price) + "₫"; // format giá đẹp hơn
            item.imageUrl = dto.imageUrl;
            items.add(item);
        }

        // Gán vào ViewModel
        viewModel.ShoeList = items;
    }

    // Hàm cho Controller truy cập ViewModel
    public ViewShoeListViewModel getViewModel() {
        return viewModel;
    }
}
