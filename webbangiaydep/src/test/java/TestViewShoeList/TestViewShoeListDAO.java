package TestViewShoeList;

import org.junit.jupiter.api.*;
import persistence.ViewShoeList.ViewShoeListDTO;
import persistence.ViewShoeList.viewShoeListDAO;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 🧪 TEST CASE CHO LỚP viewShoeListDAO
 * -------------------------------------
 * Mục tiêu: Kiểm tra các chức năng lấy dữ liệu giày từ cơ sở dữ liệu MySQL.
 */
public class TestViewShoeListDAO {

    private viewShoeListDAO dao;
    @BeforeEach
    public void setup() throws SQLException, ClassNotFoundException {
        dao = new viewShoeListDAO();
    }

    /**
     * 🧩 Kịch bản 1: getAllShoes() trả về danh sách sản phẩm hợp lệ
     * ------------------------------------------------------------
     * Mục tiêu: Kiểm tra phương thức trả về danh sách không null,
     * có ít nhất 1 sản phẩm, và các trường chính có dữ liệu hợp lệ.
     */
    @Test
    public void testGetAllShoes_ReturnsList() throws SQLException {
        List<ViewShoeListDTO> result = dao.getAllShoes();

        // Danh sách không được null
        assertNotNull(result, "Danh sách sản phẩm không được null");

        // Danh sách phải có ít nhất 1 bản ghi
        assertTrue(result.size() > 0, "Danh sách phải có ít nhất 1 sản phẩm");

        // Kiểm tra sản phẩm đầu tiên
        ViewShoeListDTO first = result.get(0);
        assertNotNull(first.name, "Tên sản phẩm không được null");
        assertTrue(first.price > 0, "Giá sản phẩm phải lớn hơn 0");
        assertNotNull(first.brand, "Thương hiệu không được null");
    }

    /**
     * 🧩 Kịch bản 2: Kiểm tra dữ liệu mapping chính xác từ SQL sang DTO
     * ----------------------------------------------------------------
     * Mục tiêu: Đảm bảo các cột trong bảng (id, name, brand, category...) 
     * được gán đúng vào thuộc tính của ViewShoeListDTO.
     */
    @Test
    public void testGetAllShoes_FieldMapping() throws SQLException {
        List<ViewShoeListDTO> result = dao.getAllShoes();

        // Duyệt toàn bộ danh sách và kiểm tra dữ liệu hợp lệ
        for (ViewShoeListDTO dto : result) {
            assertTrue(dto.id > 0, "ID sản phẩm phải > 0");
            assertNotNull(dto.name, "Tên sản phẩm không được null");
            assertNotNull(dto.brand, "Thương hiệu không được null");
            assertNotNull(dto.category, "Danh mục không được null");
        }
    }

    /**
     * 🧩 Kịch bản 3: Kết nối database thất bại -> Ném ngoại lệ
     * --------------------------------------------------------
     * Mục tiêu: Mô phỏng tình huống kết nối DB bị sai mật khẩu hoặc URL,
     * kiểm tra xem hệ thống có ném SQLException đúng cách hay không.
     */
    @Test
    public void testConnectionFail_ThrowsException() {
        assertThrows(SQLException.class, () -> {
            // Giả lập kết nối sai thông tin
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/shoesdb?useSSL=false",
                "root",
                "sai_mat_khau"
            );
        }, "Phải ném SQLException khi kết nối thất bại");
    }
}
