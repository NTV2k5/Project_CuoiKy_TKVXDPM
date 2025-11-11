// package Web;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.annotation.WebServlet;
// import jakarta.servlet.http.*;
// import jakarta.servlet.RequestDispatcher;
// import java.io.IOException;

// import presenters.SearchShoe.SearchShoePresenter;
// import presenters.SearchShoe.SearchShoeViewModel;
// import business.SearchShoe.SearchShoeUsecase;
// import business.SearchShoe.SearchShoeInputBoundary;
// import persistence.SearchShoe.SearchShoeDAO;
// import persistence.SearchShoe.SearchShoeGateway;

// @WebServlet("/searchShoeList")
// public class SearchShoeListControllerWeb extends HttpServlet {

//     private SearchShoePresenter presenter;
//     private SearchShoeInputBoundary usecase;

//     @Override
//     public void init() throws ServletException {
//         try {
//             presenter = new SearchShoePresenter();
//             SearchShoeGateway gateway = new SearchShoeDAO(); 
//             usecase = new SearchShoeUsecase(gateway, presenter); 
//         } catch (Exception e) {
//             e.printStackTrace();
//             throw new ServletException("Không thể khởi tạo SearchShoeListControllerWeb", e);
//         }
//     }


//     @Override
//     protected void doGet(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {

//         try {
//             // Lấy từ khóa tìm kiếm
//             String keyword = request.getParameter("keyword");
//             if (keyword == null) {
//                 keyword = "";
//             }

//             usecase.execute(keyword);
//             SearchShoeViewModel viewModel = presenter.getViewModel();

//             request.setAttribute("shoesList", viewModel.items);

//             // Forward sang JSP hiển thị
//             RequestDispatcher dispatcher = request.getRequestDispatcher("searchShoeList.jsp");
//             dispatcher.forward(request, response);

//         } catch (Exception e) {
//             e.printStackTrace();
//             response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tìm kiếm sản phẩm");
//         }
//     }

//     @Override
//     protected void doPost(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {
//         doGet(request, response);
//     }
// }
