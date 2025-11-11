package TestViewShoeDetail;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import presenters.ViewShoeDetail.*;

import persistence.ViewShoeDetail.ViewShoeDetailDTO;

public class TestViewShoeDetailPresenter {

    @Test
    public void testPresentShoeDetailMapping() {
        // Kịch bản test: Chuyển đổi DTO sang ViewShoeDetailItem thành công
        ViewShoeDetailViewModel viewModel = new ViewShoeDetailViewModel();

        // Tạo Presenter với ViewModel
        ViewShoeDetailPresenter presenter = new ViewShoeDetailPresenter(viewModel);

        // Tạo DTO mẫu
        ViewShoeDetailDTO dto = new ViewShoeDetailDTO();
        dto.id = 1;
        dto.name = "Sneaker ABC";
        dto.price = 1200000.0;
        dto.brand = "Nike";
        dto.category = "Thể thao";
        dto.description = "Giày thể thao cao cấp";
        dto.imageUrl = "sneaker.jpg";

        // Thêm biến thể
        dto.variants = new ArrayList<>();
        ViewShoeDetailDTO.Variant variant = new ViewShoeDetailDTO.Variant();
        variant.size = "42";
        variant.color = "Đỏ";
        variant.hexCode = "#FF0000";
        variant.stock = 10;
        dto.variants.add(variant);

        // 4. Gọi Presenter
        presenter.presentShoeDetail(dto);

        // 5. Lấy ViewModel từ Presenter
        ViewShoeDetailItem item = viewModel.getShoeDetail();

        // 6. Kiểm tra mapping
        assertNotNull(item);
        assertEquals(dto.id, item.id);
        assertEquals(dto.name, item.name);
        assertEquals(dto.price, item.price);
        assertEquals(dto.brand, item.brand);
        assertEquals(dto.category, item.category);
        assertEquals(dto.description, item.description);
        assertEquals(dto.imageUrl, item.imageUrl);

        // Kiểm tra biến thể
        assertNotNull(item.variants);
        assertEquals(1, item.variants.size());
        assertEquals(variant.size, item.variants.get(0).size);
        assertEquals(variant.color, item.variants.get(0).color);
        assertEquals(variant.hexCode, item.variants.get(0).hexCode);
        assertEquals(variant.stock, item.variants.get(0).stock);
    }

    @Test
    public void testPresentShoeDetailWithNullVariants() {
        // Kịch bản test: DTO không có biến thể (variants = null)
        ViewShoeDetailViewModel viewModel = new ViewShoeDetailViewModel();
        ViewShoeDetailPresenter presenter = new ViewShoeDetailPresenter(viewModel);

        ViewShoeDetailDTO dto = new ViewShoeDetailDTO();
        dto.id = 2;
        dto.name = "Sandal XYZ";
        dto.price = 500000.0;
        dto.brand = "Bitis";
        dto.category = "Sandal";
        dto.description = "Giày sandal nữ";
        dto.imageUrl = "sandal.jpg";
        dto.variants = null;

        presenter.presentShoeDetail(dto);

        ViewShoeDetailItem item = viewModel.getShoeDetail();
        assertNotNull(item);
        assertEquals(0, item.variants == null ? 0 : item.variants.size()); // đảm bảo không có biến thể
    }
}
