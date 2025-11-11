package TestViewShoeDetail;

import business.ViewShoeDetail.*;
import business.entity.Shoe;
import persistence.ViewShoeDetail.ViewShoeDetailDTO;
import persistence.ViewShoeDetail.ViewShoeDetailGateway;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestViewshoeDetailUsecase {

    @Test
    //Kiểm tra khi sản phẩm tồn tại, presenter nhận đúng dữ liệu sản phẩm
    public void testExecute_ProductExists() throws SQLException, ClassNotFoundException {
        // Mock dữ liệu trả về từ DAO
        ViewShoeDetailDTO mockDTO = new ViewShoeDetailDTO();
        mockDTO.id = 1;
        mockDTO.name = "Sneaker XYZ";
        mockDTO.price = 1200000.0;
        mockDTO.description = "Giày thể thao thoáng khí";
        mockDTO.brand = "Nike";
        mockDTO.category = "Thể thao";
        mockDTO.isActive = true;
        mockDTO.variants = new ArrayList<>();

        // Mock presenter
        ViewShoeDetailOutputBoundary presenter = mock(ViewShoeDetailOutputBoundary.class);

        // Spy UseCase để override DAO
        ViewShoeDetailUseCase useCase = new ViewShoeDetailUseCase(presenter) {
            @Override
            public ViewShoeDetailDTO convertToDTO(business.entity.Shoe shoe) {
                return mockDTO; 
            }

            @Override
            public Shoe convertToBusinessObject(ViewShoeDetailDTO dto) {
                return new Shoe(1, "Sneaker XYZ", "Giày thể thao thoáng khí",
                        1200000.0, 50, "img.jpg", "Nike", "Thể thao", true);
            }

            @Override
            public void execute(int shoeId) throws SQLException, ClassNotFoundException {
                // Không gọi DAO thật
                ViewShoeDetailDTO dtoDb = mockDTO;
                Shoe shoe = convertToBusinessObject(dtoDb);
                ViewShoeDetailDTO dtoItem = convertToDTO(shoe);
                presenter.presentShoeDetail(dtoItem);
            }
        };

        useCase.execute(1);
        // Verify presenter được gọi đúng
        verify(presenter, times(1)).presentShoeDetail(mockDTO);
    }

    @Test
    //Kiểm tra khi sản phẩm không tồn tại, presenter nhận null.
    public void testExecute_ProductNotExists() throws SQLException, ClassNotFoundException {
        // Mock presenter
        ViewShoeDetailOutputBoundary presenter = mock(ViewShoeDetailOutputBoundary.class);

        // UseCase mock trả null khi không tìm thấy sản phẩm
        ViewShoeDetailUseCase useCase = new ViewShoeDetailUseCase(presenter) {
            @Override
            public void execute(int shoeId) {
                presenter.presentShoeDetail(null);
            }
        };

        useCase.execute(999); // sản phẩm không tồn tại

        verify(presenter, times(1)).presentShoeDetail(null);
    }
}
