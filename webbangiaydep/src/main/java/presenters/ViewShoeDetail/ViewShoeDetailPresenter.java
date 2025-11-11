package presenters.ViewShoeDetail;

import java.util.ArrayList;
import java.util.List;

import business.ViewShoeDetail.ViewShoeDetailOutputBoundary;
import persistence.ViewShoeDetail.ViewShoeDetailDTO;

public class ViewShoeDetailPresenter implements ViewShoeDetailOutputBoundary 
{
    private ViewShoeDetailViewModel viewModel;

    public ViewShoeDetailPresenter()
    {

    }
    public ViewShoeDetailPresenter(ViewShoeDetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentShoeDetail(ViewShoeDetailDTO shoeDetail) {
        ViewShoeDetailItem item = new ViewShoeDetailItem();
        item.id = shoeDetail.id;
        item.name = shoeDetail.name;
        item.price = shoeDetail.price;
        item.imageUrl = shoeDetail.imageUrl;
        item.brand = shoeDetail.brand;
        item.category = shoeDetail.category;
        item.description = shoeDetail.description;

        // CHUYỂN VARIANT
        if (shoeDetail.variants != null) {
            List<ViewShoeDetailItem.Variant> itemVariants = new ArrayList<>();
            for (ViewShoeDetailDTO.Variant v : shoeDetail.variants) {
                ViewShoeDetailItem.Variant iv = new ViewShoeDetailItem.Variant();
                iv.size = v.size;
                iv.color = v.color;
                iv.hexCode = v.hexCode;
                iv.stock = v.stock;
                itemVariants.add(iv);
            }
            item.variants = itemVariants;
        }

        viewModel.setShoeDetail(item);
    }
    public ViewShoeDetailViewModel getViewModel() 
    {
        return viewModel;
    }
}
