package TestViewShoeList;

import business.ViewShoeList.ViewShoeListUsecase;
import business.ViewShoeList.ViewShoeListOutputBoundary;
import persistence.ViewShoeList.ViewShoeListDTO;
import persistence.ViewShoeList.ViewShoeListGateway;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestViewShoeListUseCase {
    class FakeDAO implements ViewShoeListGateway {
        @Override
        public List<ViewShoeListDTO> getAllShoes() {
            List<ViewShoeListDTO> list = new ArrayList<>();

            ViewShoeListDTO s1 = new ViewShoeListDTO();
            s1.id = 1;
            s1.name = "Nike Air Zoom";
            s1.price = 2500000;
            s1.imageUrl = "nike.jpg";
            s1.brand = "Nike";

            ViewShoeListDTO s2 = new ViewShoeListDTO();
            s2.id = 2;
            s2.name = "Adidas Ultraboost";
            s2.price = 3000000;
            s2.imageUrl = "adidas.jpg";
            s2.brand = "Adidas";

            list.add(s1);
            list.add(s2);
            return list;
        }
    }
    class FakeOutput implements ViewShoeListOutputBoundary {
        public boolean called = false;
        public List<ViewShoeListDTO> receivedList;

        @Override
        public void presentShoeList(List<ViewShoeListDTO> shoeList) 
        {
            called = true;
            receivedList = shoeList;
        }
    }

    // Test case 1: UseCase hoạt động bình thường
    @Test
    public void testExecute_ReturnsConvertedDTOList() throws SQLException, ClassNotFoundException {
        ViewShoeListGateway dao = new FakeDAO();
        FakeOutput output = new FakeOutput();
        ViewShoeListUsecase useCase = new ViewShoeListUsecase(output, dao);

        List<ViewShoeListDTO> result = useCase.execute();

        //  Kiểm tra danh sách trả về
        assertNotNull(result);
        assertEquals(2, result.size(), "Phải có 2 sản phẩm được trả về");
    }

    // Test case 2: DAO trả về danh sách rỗng
    @Test
    public void testExecute_EmptyList() throws SQLException, ClassNotFoundException {
        // DAO giả trả về danh sách rỗng
        ViewShoeListGateway emptyDAO = new ViewShoeListGateway() {
            @Override
            public List<ViewShoeListDTO> getAllShoes() {
                return new ArrayList<>();
            }
        };

        FakeOutput output = new FakeOutput();
        ViewShoeListUsecase useCase = new ViewShoeListUsecase(output, emptyDAO);

        List<ViewShoeListDTO> result = useCase.execute();

        assertTrue(output.called, "Output boundary phải được gọi");
        assertNotNull(result);
        assertEquals(0, result.size(), "Danh sách trả về phải rỗng");
    }

}
