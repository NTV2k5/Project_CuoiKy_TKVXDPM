// package Web;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.annotation.WebServlet;
// import jakarta.servlet.http.*;
// import persistence.ViewShoeList.ViewShoeListGateway;
// import persistence.ViewShoeList.viewShoeListDAO;
// import jakarta.servlet.RequestDispatcher;
// import java.io.IOException;
// import java.sql.SQLException;

// import presenters.ViewShoeList.ViewShoeListPresenter;
// import presenters.ViewShoeList.ViewShoeListViewModel;
// import business.ViewShoeList.ViewShoeListUsecase;

// @WebServlet("/shoes")
// public class ViewShoeListControllerWeb extends HttpServlet {
//     @Override
//     protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
//         try {
//             // Gọi Usecase xử lý nghiệp vụ
//             ViewShoeListPresenter presenter = new ViewShoeListPresenter();
//             ViewShoeListGateway dao = new viewShoeListDAO();
//             ViewShoeListUsecase usecase = new ViewShoeListUsecase(presenter, dao);
//             usecase.execute();

//             // Lấy ViewModel từ presenter
//             ViewShoeListViewModel viewModel = presenter.getViewModel();

//             // Gửi dữ liệu ra JSP
//             request.setAttribute("shoesList", viewModel.ShoeList);

//             // Chuyển sang trang view
//             RequestDispatcher dispatcher = request.getRequestDispatcher("viewShoeList.jsp");
//             dispatcher.forward(request, response);

//         } catch (SQLException | ClassNotFoundException e) {
//             e.printStackTrace();
//             response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi lấy danh sách giày");
//         }
//     }
// }
