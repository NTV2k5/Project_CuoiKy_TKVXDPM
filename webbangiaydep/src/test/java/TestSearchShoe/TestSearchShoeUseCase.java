package TestSearchShoe;

import business.SearchShoe.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import persistence.SearchShoe.SearchShoeDTO;
import persistence.SearchShoe.SearchShoeGateway;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestSearchShoeUseCase {

    private SearchShoeGateway mockDao;
    private SearchShoeOutputBoundary mockOutput;
    private SearchShoeUsecase usecase;

    @BeforeEach
    public void setUp() {
        mockDao = mock(SearchShoeGateway.class);
        mockOutput = mock(SearchShoeOutputBoundary.class);
        usecase = new SearchShoeUsecase(mockDao, mockOutput);
    }

    // Kịch bản 1: Tìm thấy kết quả khi keyword hợp lệ
    @Test
    public void execute_givenValidKeyword_shouldReturnMatchingShoes() throws Exception {
        // Arrange
        String keyword = "sneaker";

        SearchShoeDTO shoe1 = new SearchShoeDTO();
        shoe1.id = 1;
        shoe1.name = "Sneaker X";

        SearchShoeDTO shoe2 = new SearchShoeDTO();
        shoe2.id = 2;
        shoe2.name = "Sneaker Y";

        List<SearchShoeDTO> mockResult = Arrays.asList(shoe1, shoe2);
        when(mockDao.searchShoes(keyword)).thenReturn(mockResult);

        // Act
        usecase.execute(keyword);

        // Assert
        ArgumentCaptor<List<SearchShoeDTO>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockOutput, times(1)).present(captor.capture());

        List<SearchShoeDTO> output = captor.getValue();
        assertEquals(2, output.size());
        assertEquals("Sneaker X", output.get(0).name);
        assertEquals("Sneaker Y", output.get(1).name);

        verify(mockDao, times(1)).searchShoes(keyword);
    }

    // Kịch bản 2: Không tìm thấy kết quả (trả về list rỗng)
    @Test
    public void execute_givenKeywordWithNoMatch_shouldReturnEmptyList() throws Exception {
        // Arrange
        String keyword = "nonexistent";
        when(mockDao.searchShoes(keyword)).thenReturn(Arrays.asList());

        // Act
        usecase.execute(keyword);

        // Assert
        ArgumentCaptor<List<SearchShoeDTO>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockOutput, times(1)).present(captor.capture());

        List<SearchShoeDTO> output = captor.getValue();
        assertTrue(output.isEmpty());

        verify(mockDao, times(1)).searchShoes(keyword);
    }
}
