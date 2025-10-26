package business.ViewShoeList;

import java.sql.SQLException;
import java.util.List;
import persistence.ViewShoeList.ViewShoeListDTO;
import persistence.ViewShoeList.ViewShoeListGateway;
import persistence.ViewShoeList.viewShoeListDAO;

public class ViewShoeListUsecase implements ViewShoeListInputBoundary 
{
    private final ViewShoeListOutputBoundary output;

    public ViewShoeListUsecase(ViewShoeListOutputBoundary output) {
        this.output = output;
    }

    @Override
    public List<ViewShoeListDTO> getAllShoes() throws SQLException, ClassNotFoundException 
    {
        // Gọi DAO để lấy dữ liệu từ database
        ViewShoeListGateway dao = new viewShoeListDAO();
        List<ViewShoeListDTO> shoes = dao.getAllShoes();

        // Gửi kết quả sang lớp OutputBoundary (trình bày ở View)
        output.presentShoeList(shoes);
		return shoes;
    }
}
