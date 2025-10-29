package presenters.ViewShoeDetail;

import business.ViewShoeDetail.ViewShoeDetailOutputBoundary;
import persistence.ViewShoeDetail.ViewShoeDetailDTO;

public class ViewShoeDetailPresenter implements ViewShoeDetailOutputBoundary 
{
    private ViewShoeDetailViewModel viewModel;

    public ViewShoeDetailPresenter(ViewShoeDetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentShoeDetail(ViewShoeDetailDTO shoeDetail) 
    {
        // chuyển dữ liệu từ DTO sang Item cho ViewModel    
        ViewShoeDetailItem item = new ViewShoeDetailItem();
        item.id = shoeDetail.id;
        item.name = shoeDetail.name;
        item.price = shoeDetail.price;
        item.imageUrl = shoeDetail.imageUrl;
        item.brand = shoeDetail.brand;
        item.category = shoeDetail.category;
        item.description = shoeDetail.description;
        item.size = shoeDetail.size;
        item.color = shoeDetail.color;

        // cập nhật vào ViewModel để View có thể hiển thị
        viewModel.setShoeDetail(item);
    }
    public ViewShoeDetailViewModel getViewModel() 
    {
        return viewModel;
    }
}
