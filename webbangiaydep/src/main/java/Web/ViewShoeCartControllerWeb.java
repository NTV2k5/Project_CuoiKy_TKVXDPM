package Web;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import persistence.ViewShoeCart.ViewShoeCartDAO;
import persistence.ViewShoeCart.ViewShoeCartInterface;

import java.io.IOException;
import java.sql.SQLException;

import business.ViewShoeCart.*;
import presenters.ViewShoeCart.*;

@WebServlet("/viewShoeCart")
public class ViewShoeCartControllerWeb extends HttpServlet {

    private ViewShoeCartInputBoundary viewShoeCartUseCase;
    private ViewShoeCartPresenter presenter;

    @Override
    public void init() throws ServletException {
        try {
            ViewShoeCartViewModel viewModel = new ViewShoeCartViewModel();
            presenter = new ViewShoeCartPresenter(viewModel);

            // DAO ném ngoại lệ → bắt ở đây
            ViewShoeCartInterface gateway = new ViewShoeCartDAO();
            viewShoeCartUseCase = new ViewShoeCartUsecase(gateway, presenter);

        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Khởi tạo ViewShoeCart thất bại - Không kết nối được CSDL", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object userIdObj = session.getAttribute("userId");

        Long userId = null;

        // Xử lý userId đúng mọi trường hợp bạn từng set (String, Integer, Long)
        if (userIdObj != null) {
            if (userIdObj instanceof Number) {
                userId = ((Number) userIdObj).longValue();
            } else if (userIdObj instanceof String) {
                try {
                    userId = Long.parseLong((String) userIdObj);
                } catch (NumberFormatException e) {
                    // userId không hợp lệ → coi như chưa đăng nhập
                    userId = null;
                }
            }
        }

        // Nếu chưa đăng nhập → vẫn cho xem giỏ (có thể rỗng)
        ViewShoeCartInputData inputData = new ViewShoeCartInputData(userId != null ? userId.intValue() : 0);
        // Hoặc tốt hơn: sửa InputData nhận Long → nhưng tạm thời giữ int như hiện tại

        viewShoeCartUseCase.execute(inputData);

        ViewShoeCartViewModel viewModel = presenter.getViewModel();

        // Gửi dữ liệu sang JSP
        request.setAttribute("cartItems", viewModel.items);
        // request.setAttribute("totalAmount", viewModel.getTotalAmount());
        request.setAttribute("message", viewModel.getMessage());

        // Forward đến trang giỏ hàng
        request.getRequestDispatcher("/Cart.jsp").forward(request, response);
    }
}