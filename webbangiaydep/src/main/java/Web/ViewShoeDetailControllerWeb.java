package Web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import persistence.ViewShoeDetail.ViewShoeDetailDAO;
import persistence.ViewShoeDetail.ViewShoeDetailInterface;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.SQLException;

import business.ViewShoeDetail.ViewShoeDetailRepository;
import business.ViewShoeDetail.ViewShoeDetailUseCase;
import presenters.Repository.ViewShoeDetailRepositoryImpl;
import presenters.ViewShoeDetail.ViewShoeDetailPresenter;
import presenters.ViewShoeDetail.ViewShoeDetailViewModel;

@WebServlet("/shoe-detail")
public class ViewShoeDetailControllerWeb extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException
    {

        String shoeIdParam = request.getParameter("id");
        if (shoeIdParam == null || shoeIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu mã sản phẩm");
            return;
        }

        int shoeId = Integer.parseInt(shoeIdParam);

        try {
            // 1. Tạo DAO (lớp thực tế truy vấn DB)
            ViewShoeDetailInterface dao = new ViewShoeDetailDAO();

            // 2. Tạo Repository (lớp adapter nằm ở business layer)
            ViewShoeDetailRepository repository = new ViewShoeDetailRepositoryImpl(dao);

            // 3. Tạo ViewModel + Presenter
            ViewShoeDetailViewModel viewModel = new ViewShoeDetailViewModel();
            ViewShoeDetailPresenter presenter = new ViewShoeDetailPresenter(viewModel);

            // 4. Tạo UseCase – truyền presenter và repository
            ViewShoeDetailUseCase useCase = new ViewShoeDetailUseCase(presenter, repository);

            // 5. Thực thi
            useCase.execute(shoeId);

            // 6. Lấy dữ liệu từ ViewModel để gửi sang JSP
            request.setAttribute("shoeDetail", viewModel.getShoeDetail());

            // 7. Forward
            request.getRequestDispatcher("/detailShoe.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Không thể lấy chi tiết sản phẩm", e);
        }
    }
}
