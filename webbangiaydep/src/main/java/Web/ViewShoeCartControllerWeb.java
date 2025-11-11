// // Web/ViewShoeCartControllerWeb.java
// package Web;

// import jakarta.servlet.*;
// import jakarta.servlet.annotation.WebServlet;
// import jakarta.servlet.http.*;
// import java.io.IOException;
// import java.sql.SQLException;

// import business.ViewShoeCart.ViewShoeCartInputBoundary;
// import business.ViewShoeCart.ViewShoeCartInputData;
// import business.ViewShoeCart.ViewShoeCartUsecase;
// import presenters.ViewShoeCart.ViewShoeCartPresenter;
// import presenters.ViewShoeCart.ViewShoeCartViewModel;
// import persistence.ViewShoeCart.ViewShoeCartGateway;
// import persistence.ViewShoeCart.ViewShoeCartDAO;

// @WebServlet("/viewShoeCart")
// public class ViewShoeCartControllerWeb extends HttpServlet {

//     private ViewShoeCartInputBoundary viewShoeCartUseCase;
//     private ViewShoeCartPresenter presenter;

//     @Override
//     public void init() throws ServletException {  // ← Thêm throws ServletException
//         try {
//             ViewShoeCartViewModel viewModel = new ViewShoeCartViewModel();
//             presenter = new ViewShoeCartPresenter(viewModel);

//             // ← Bắt ngoại lệ từ constructor DAO
//             ViewShoeCartGateway gateway = new ViewShoeCartDAO();

//             viewShoeCartUseCase = new ViewShoeCartUsecase(gateway, presenter);

//         } catch (ClassNotFoundException | SQLException e) {
//             // Ghi log + ném ServletException để Servlet Container biết
//             throw new ServletException("Không thể khởi tạo ViewShoeCartDAO: " + e.getMessage(), e);
//         }
//     }
    
//     @Override
//     protected void doGet(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {

//         String userId = (String) request.getSession().getAttribute("userId");
//         // ← Không gán "guest" → để null nếu chưa đăng nhập
//         String sessionId = request.getSession().getId();

//         ViewShoeCartInputData inputData = new ViewShoeCartInputData(userId, sessionId);
//         viewShoeCartUseCase.execute(inputData);

//         ViewShoeCartViewModel viewModel = presenter.getViewModel();
//         request.setAttribute("cartItems", viewModel.items);
//         request.setAttribute("totalAmount", viewModel.getTotalAmount());

//         request.getRequestDispatcher("Cart.jsp").forward(request, response);
//     }
// }