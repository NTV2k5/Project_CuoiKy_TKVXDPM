package Web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.SQLException;

import presenters.ViewShoeList.ViewShoeListPresenter;
import presenters.ViewShoeList.ViewShoeListViewModel;
import business.ViewShoeList.ViewShoeListRepository;
import business.ViewShoeList.ViewShoeListUsecase;
import presenters.Reposity.ViewShoeListRepositoryImpl;
import persistence.ViewShoeList.viewShoeListDAO;

@WebServlet("/shoes")
public class ViewShoeListControllerWeb extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. Tạo presenter
            ViewShoeListPresenter presenter = new ViewShoeListPresenter();

            // 2. Tạo DAO và Repository
            viewShoeListDAO daoImpl = new viewShoeListDAO(); // class thực thi DAOInterface
            ViewShoeListRepository dao = new ViewShoeListRepositoryImpl(daoImpl);

            // 3. Tạo Usecase và thực thi
            ViewShoeListUsecase usecase = new ViewShoeListUsecase(presenter, dao);
            usecase.execute();

            // 4. Lấy ViewModel và gửi sang JSP
            ViewShoeListViewModel viewModel = presenter.getViewModel();
            request.setAttribute("shoesList", viewModel.ShoeList);

            // 5. Forward sang trang view
            RequestDispatcher dispatcher = request.getRequestDispatcher("viewShoeList.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi lấy danh sách giày");
        }
    }
}
