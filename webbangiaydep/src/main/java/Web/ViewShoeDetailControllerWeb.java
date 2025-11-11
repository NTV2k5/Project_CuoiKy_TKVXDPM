// package Web;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.annotation.WebServlet;
// import jakarta.servlet.http.*;
// import jakarta.servlet.RequestDispatcher;
// import java.io.IOException;
// import java.sql.SQLException;

// import business.ViewShoeDetail.ViewShoeDetailUseCase;
// import presenters.ViewShoeDetail.ViewShoeDetailPresenter;
// import presenters.ViewShoeDetail.ViewShoeDetailViewModel;

// @WebServlet("/shoe-detail")
// public class ViewShoeDetailControllerWeb extends HttpServlet 
// {
//     @Override
//     protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
//     {
//         String shoeIdParam = request.getParameter("id");
//         if (shoeIdParam == null) {
//             throw new ServletException("Thiếu mã sản phẩm (id).");
//         }
//         int shoeId = Integer.parseInt(shoeIdParam);

//         // Gọi UseCase để lấy chi tiết sản phẩm
//         ViewShoeDetailViewModel viewModel = new ViewShoeDetailViewModel();
//         ViewShoeDetailPresenter presenter = new ViewShoeDetailPresenter(viewModel);
//         ViewShoeDetailUseCase usecase = new ViewShoeDetailUseCase(presenter);
//         try {
//             usecase.execute(shoeId);
//         } catch (ClassNotFoundException | SQLException e) {
//             e.printStackTrace();
//         }

//         // Gửi dữ liệu sang JSP
//         request.setAttribute("shoeDetail", presenter.getViewModel().getShoeDetail());

//         // Chuyển tiếp đến trang hiển thị
//         RequestDispatcher dispatcher = request.getRequestDispatcher("detailShoe.jsp");
//         dispatcher.forward(request, response);
//     }
// }
