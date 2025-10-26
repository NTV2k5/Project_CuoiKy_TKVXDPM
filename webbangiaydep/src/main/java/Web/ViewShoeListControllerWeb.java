package Web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.SQLException;

import presenters.ViewShoeList.ViewShoeListPresenter;
import presenters.ViewShoeList.ViewShoeListViewModel;
import business.ViewShoeList.ViewShoeListUsecase;

@WebServlet("/shoes")
public class ViewShoeListControllerWeb extends HttpServlet {

    private ViewShoeListUsecase usecase;
    private ViewShoeListPresenter presenter;

    @Override
    public void init() throws ServletException {
        presenter = new ViewShoeListPresenter();
        usecase = new ViewShoeListUsecase(presenter);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Gọi Usecase xử lý nghiệp vụ
            usecase.getAllShoes();

            // Lấy ViewModel từ presenter
            ViewShoeListViewModel viewModel = presenter.getViewModel();

            // Gửi dữ liệu ra JSP
            request.setAttribute("shoesList", viewModel.ShoeList);

            // Chuyển sang trang view
            RequestDispatcher dispatcher = request.getRequestDispatcher("viewShoeList.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi lấy danh sách giày");
        }
    }
}
