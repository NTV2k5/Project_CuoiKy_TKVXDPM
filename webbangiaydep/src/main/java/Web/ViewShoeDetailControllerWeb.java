package Web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;

import business.ViewShoeDetail.ViewShoeDetailUseCase;
import presenters.ViewShoeDetail.ViewShoeDetailPresenter;
import presenters.ViewShoeDetail.ViewShoeDetailViewModel;

@WebServlet("/shoe-detail")
public class ViewShoeDetailControllerWeb extends HttpServlet {

    private ViewShoeDetailUseCase usecase;
    private ViewShoeDetailPresenter presenter;

    @Override
    public void init() throws ServletException {
        // Khởi tạo ViewModel, Presenter và UseCase
        ViewShoeDetailViewModel viewModel = new ViewShoeDetailViewModel();
        presenter = new ViewShoeDetailPresenter(viewModel);
        usecase = new ViewShoeDetailUseCase(presenter);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String shoeIdParam = request.getParameter("id");
        if (shoeIdParam == null) {
            throw new ServletException("Thiếu mã sản phẩm (id).");
        }

        int shoeId = Integer.parseInt(shoeIdParam);

        // Gọi UseCase để lấy chi tiết sản phẩm
        usecase.getShoeDetail(shoeId);

        // Lấy ViewModel từ Presenter
        ViewShoeDetailViewModel viewModel = presenter.getViewModel();

        // Gửi dữ liệu sang JSP
        request.setAttribute("shoeDetail", viewModel.getShoeDetail());

        // Chuyển tiếp đến trang hiển thị
        RequestDispatcher dispatcher = request.getRequestDispatcher("detailShoe.jsp");
        dispatcher.forward(request, response);
    }
}
