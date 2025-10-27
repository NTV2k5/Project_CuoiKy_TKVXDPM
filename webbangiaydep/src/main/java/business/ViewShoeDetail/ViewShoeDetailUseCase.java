package business.ViewShoeDetail;

import persistence.ViewShoeDetail.ViewShoeDetailDAO;
import persistence.ViewShoeDetail.ViewShoeDetailDTO;
import java.sql.SQLException;

public class ViewShoeDetailUseCase implements ViewShoeDetailInputBoundary {

    private ViewShoeDetailOutputBoundary outputBoundary;
    private ViewShoeDetailDAO dao;

    public ViewShoeDetailUseCase(ViewShoeDetailOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
        try {
            this.dao = new ViewShoeDetailDAO();
        } catch (Exception e) {
            throw new RuntimeException("Không thể kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }

    @Override
    public void getShoeDetail(int shoeId) {
        try {
            ViewShoeDetailDTO shoe = dao.getShoeById(shoeId);

            if (shoe != null) 
            {
                outputBoundary.presentShoeDetail(shoe);
            } 
            else 
            {
                System.out.println("Sản phẩm không tồn tại hoặc đã bị xóa. ID = " + shoeId);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi truy xuất dữ liệu: " + e.getMessage());
        }
    }
}
