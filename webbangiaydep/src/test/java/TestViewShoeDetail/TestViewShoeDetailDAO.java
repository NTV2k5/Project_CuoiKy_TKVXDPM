package TestViewShoeDetail;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import persistence.ViewShoeDetail.*;

public class TestViewShoeDetailDAO {

    @Test
    public void testGetShoeByIdExists() throws Exception {
        // Kịch bản test: Lấy giày tồn tại với variants
        ViewShoeDetailDAO dao = new ViewShoeDetailDAO();

        // Giả sử trong DB có giày id = 1 với ít nhất 1 variant
        int shoeId = 1;
        ViewShoeDetailDTO dto = dao.getShoeById(shoeId);

        assertNotNull(dto, "DTO không được null");  // Giày phải tồn tại
        assertEquals(shoeId, dto.id);
        assertNotNull(dto.variants, "Variants không được null");
        assertTrue(dto.variants.size() > 0, "Phải có ít nhất 1 variant");

        // Kiểm tra một số thông tin cơ bản
        assertNotNull(dto.name);
        assertNotNull(dto.category);
        assertNotNull(dto.brand);
    }

    @Test
    public void testGetShoeByIdNotExists() throws Exception {
        // Kịch bản test: Lấy giày không tồn tại
        ViewShoeDetailDAO dao = new ViewShoeDetailDAO();

        int shoeId = 999999; // Giả sử ID này không có trong DB
        ViewShoeDetailDTO dto = dao.getShoeById(shoeId);

        assertNull(dto, "DTO phải null khi giày không tồn tại");
    }
}
