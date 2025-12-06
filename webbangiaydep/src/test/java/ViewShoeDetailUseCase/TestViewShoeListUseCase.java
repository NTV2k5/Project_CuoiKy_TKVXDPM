package ViewShoeDetailUseCase;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.ViewShoeList.*;
import persistence.ViewShoeList.ViewShoeListDTO;

public class TestViewShoeListUseCase {

    private ViewShoeListRepository dao;
    private ViewShoeListOutputBoundary output;
    private ViewShoeListUsecase usecase;

    @BeforeEach
    public void setup() 
    {
        dao = mock(ViewShoeListRepository.class); 
        output = mock(ViewShoeListOutputBoundary.class);
        usecase = new ViewShoeListUsecase(output, dao);
    }

    @Test
    public void testFilterAvailableShoes() throws SQLException, ClassNotFoundException {
        // Chuẩn bị dữ liệu DTO
        ViewShoeListDTO shoe1 = new ViewShoeListDTO();
        shoe1.id = 1L; shoe1.name = "Shoe1"; shoe1.brand = "Nike"; shoe1.imageUrl = "img1.jpg";
        shoe1.isActive = 1;

        ViewShoeListDTO shoe2 = new ViewShoeListDTO();
        shoe2.id = 2L; shoe2.name = "Shoe2"; shoe2.brand = "Adidas"; shoe2.imageUrl = "img2.jpg";
        shoe2.isActive = 1; 

        when(dao.getAllShoes()).thenReturn(Arrays.asList(shoe1, shoe2));

        List<ViewShoeListDTO> result = usecase.execute();

        // Kiểm tra kết quả trả về chỉ chứa những đôi bán được
        assertEquals(2, result.size());
        assertEquals("Shoe1", result.get(0).name);
        assertEquals("Shoe2", result.get(1).name);

        // Kiểm tra output được gọi với danh sách đã lọc
        verify(output).presentShoeList(result);
    }
    @Test
    public void testNoShoesAvailable() throws SQLException, ClassNotFoundException 
    {

        ViewShoeListDTO shoe1 = new ViewShoeListDTO();
        shoe1.id = 1L; shoe1.name = "Old Shoe"; shoe1.isActive = 0;

        ViewShoeListDTO shoe2 = new ViewShoeListDTO();
        shoe2.id = 2L; shoe2.name = "Discontinued"; shoe2.isActive = 0;

        when(dao.getAllShoes()).thenReturn(Arrays.asList(shoe1, shoe2));

        List<ViewShoeListDTO> result = usecase.execute();

        assertTrue(result.isEmpty()); 
    }
}
