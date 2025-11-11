package TestViewShoeList;

import org.junit.jupiter.api.Test;
import presenters.ViewShoeList.*;
import persistence.ViewShoeList.ViewShoeListDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 🧪 TEST CASE CHO LỚP ViewShoeListPresenter
 * ------------------------------------------
 * Mục tiêu: Kiểm tra việc chuyển đổi dữ liệu từ tầng UseCase (DTO)
 * sang tầng View (ViewModel) hoạt động đúng và định dạng giá tiền chuẩn.
 */
public class TestViewShoeListPresenter {

    /**
     * ✅ Kịch bản 1: Chuyển đổi DTO sang ViewModel đúng dữ liệu
     * ---------------------------------------------------------
     * Mục tiêu: Đảm bảo các thuộc tính id, name, imageUrl được giữ nguyên
     * sau khi gọi presentShoeList().
     */
    @Test
    public void testPresentShoeList_ConvertsDTOToViewModel() {
        ViewShoeListPresenter presenter = new ViewShoeListPresenter();

        // Tạo danh sách DTO giả
        List<ViewShoeListDTO> dtoList = new ArrayList<>();
        ViewShoeListDTO dto = new ViewShoeListDTO();
        dto.id = 1;
        dto.name = "Giày Nike Zoom";
        dto.price = 1200000.0;
        dto.imageUrl = "nike.jpg";
        dtoList.add(dto);

        presenter.presentShoeList(dtoList);

        // Kiểm tra ViewModel không null
        assertNotNull(presenter.getViewModel().ShoeList);

        // Kiểm tra có 1 phần tử
        assertEquals(1, presenter.getViewModel().ShoeList.size());

        // Kiểm tra dữ liệu đúng
        ViewShoeListItem item = presenter.getViewModel().ShoeList.get(0);
        assertEquals(dto.id, item.id);
        assertEquals(dto.name, item.name);
        assertEquals(dto.imageUrl, item.imageUrl);
    }

    /**
     * ✅ Kịch bản 2: Định dạng giá tiền đúng theo chuẩn Việt Nam
     * -----------------------------------------------------------
     * Mục tiêu: Kiểm tra số tiền được format có dấu "." phân cách hàng nghìn.
     */
    @Test
    public void testPresentShoeList_FormatsPriceCorrectly() {
        ViewShoeListPresenter presenter = new ViewShoeListPresenter();

        ViewShoeListDTO dto = new ViewShoeListDTO();
        dto.id = 2;
        dto.name = "Adidas UltraBoost";
        dto.price = 2500000.0;
        dto.imageUrl = "adidas.jpg";

        List<ViewShoeListDTO> list = List.of(dto);
        presenter.presentShoeList(list);

        String formattedPrice = presenter.getViewModel().ShoeList.get(0).price;

        // Giá phải chứa dấu chấm phân tách hàng nghìn (VD: "2.500.000")
        assertTrue(formattedPrice.contains("."), "Giá tiền phải có dấu '.' để phân tách");
    }

    /**
     * ✅ Kịch bản 3: Xử lý danh sách trống
     * ------------------------------------
     * Mục tiêu: Khi danh sách đầu vào rỗng, ViewModel vẫn khởi tạo hợp lệ
     * nhưng danh sách hiển thị trống.
     */
    @Test
    public void testPresentShoeList_HandlesEmptyList() {
        ViewShoeListPresenter presenter = new ViewShoeListPresenter();

        List<ViewShoeListDTO> emptyList = new ArrayList<>();
        presenter.presentShoeList(emptyList);

        assertNotNull(presenter.getViewModel().ShoeList);
        assertEquals(0, presenter.getViewModel().ShoeList.size());
    }

    /**
     * ✅ Kịch bản 4: getViewModel() trả về đúng dữ liệu đã xử lý
     * ----------------------------------------------------------
     * Mục tiêu: Đảm bảo dữ liệu trong ViewModel đúng với dữ liệu convert trước đó.
     */
    @Test
    public void testGetViewModel_ReturnsSameData() {
        ViewShoeListPresenter presenter = new ViewShoeListPresenter();

        ViewShoeListDTO dto = new ViewShoeListDTO();
        dto.id = 3;
        dto.name = "Converse Classic";
        dto.price = 800000.0;
        dto.imageUrl = "converse.jpg";

        presenter.presentShoeList(List.of(dto));

        ViewShoeListViewModel vm = presenter.getViewModel();
        assertNotNull(vm);
        assertEquals("Converse Classic", vm.ShoeList.get(0).name);
    }
}
