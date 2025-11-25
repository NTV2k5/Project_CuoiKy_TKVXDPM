package TestViewShoeDetail;

import business.ViewShoeDetail.*;
import persistence.ViewShoeDetail.ViewShoeDetailDTO;
import presenters.ViewShoeDetail.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TestViewShoeDetailUsecase {

    // Helper: Tạo DTO giả với dữ liệu đầy đủ
    private ViewShoeDetailRepository fakeRepositoryWithFullProduct() {
        return new ViewShoeDetailRepository() {
            @Override
            public ViewShoeDetailDTO getShoeById(int id) {
                ViewShoeDetailDTO dto = new ViewShoeDetailDTO();
                dto.id = 100;
                dto.name = "Nike Air Max 270";
                dto.brand = "Nike";
                dto.category = "Sneaker";
                dto.description = "Giày chạy bộ đỉnh cao với công nghệ Air Max";
                dto.price = 3500000;
                dto.imageUrl = "https://example.com/nike-air-max.jpg";
                dto.isActive = 1;

                dto.variants = new ArrayList<>();

                // Variant 1: Hết hàng
                ViewShoeDetailDTO.Variant v1 = new ViewShoeDetailDTO.Variant();
                v1.variantId = 501;
                v1.size = "40";
                v1.color = "Black";
                v1.hexCode = "#000000";
                v1.stock = 2;
                v1.price = 3500000;
                dto.variants.add(v1);

                // Variant 2: Còn hàng
                ViewShoeDetailDTO.Variant v2 = new ViewShoeDetailDTO.Variant();
                v2.variantId = 502;
                v2.size = "42";
                v2.color = "White/Red";
                v2.hexCode = "#FFFFFF";
                v2.stock = 12;
                v2.price = 3500000;
                dto.variants.add(v2);

                // Variant 2: Còn hàng
                ViewShoeDetailDTO.Variant v3 = new ViewShoeDetailDTO.Variant();
                v3.variantId = 503;
                v3.size = "43";
                v3.color = "Grey";
                v3.hexCode = "#808080";
                v3.stock = 3;
                v3.price = 3700000;
                dto.variants.add(v3);

                return dto;
            }
        };
    }

    @Test
    void testViewDetailProduct_WithVariants() throws Exception {

        // === Arrange: Setup ViewModel + Presenter + Fake DAO ===
        ViewShoeDetailViewModel viewModel = new ViewShoeDetailViewModel();
        ViewShoeDetailPresenter presenter = new ViewShoeDetailPresenter(viewModel);
        ViewShoeDetailRepository fakeDao = fakeRepositoryWithFullProduct();

        ViewShoeDetailUseCase useCase = new ViewShoeDetailUseCase(presenter, fakeDao);

        // === Act: Gọi UseCase ===
        useCase.execute(100);

        // === Assert: Kiểm tra kết quả trong ViewModel ===
        ViewShoeDetailItem result = viewModel.getShoeDetail();
        // Danh sách variant
        assertEquals(3, result.variants.size());

        var v2 = result.variants.get(1);
        assertEquals("42", v2.size);
        assertEquals("White/Red", v2.color);
        assertEquals(12, v2.stock);
        assertEquals("Còn hàng", v2.status);

        var v3 = result.variants.get(2);
        assertEquals("43", v3.size);
        assertEquals(3, v3.stock);
        assertEquals("Còn hàng", v3.status);
    }

        @Test
    void testViewDetailProduct_WithVariantOutStock() throws Exception {

        // === Arrange: Setup ViewModel + Presenter + Fake DAO ===
        ViewShoeDetailViewModel viewModel = new ViewShoeDetailViewModel();
        ViewShoeDetailPresenter presenter = new ViewShoeDetailPresenter(viewModel);
        ViewShoeDetailRepository fakeDao = fakeRepositoryWithFullProduct();

        ViewShoeDetailUseCase useCase = new ViewShoeDetailUseCase(presenter, fakeDao);

        // === Act: Gọi UseCase ===
        useCase.execute(100);

        // === Assert: Kiểm tra kết quả trong ViewModel ===
        ViewShoeDetailItem result = viewModel.getShoeDetail();
        // Danh sách variant
        assertEquals(3, result.variants.size());

        var v1 = result.variants.get(0);
        assertEquals("40", v1.size);
        assertEquals("Black", v1.color);
        assertEquals(0, v1.stock);
        assertEquals("hết hàng", v1.status);

        var v2 = result.variants.get(1);
        assertEquals("42", v2.size);
        assertEquals("White/Red", v2.color);
        assertEquals(12, v2.stock);
        assertEquals("Còn hàng", v2.status);

    }
}