package Web;

import java.io.IOException;
import java.sql.SQLException;

import business.GetOrders.GetOrdersInputBoundary;
import business.GetOrders.GetOrdersInputData;
import business.GetOrders.GetOrdersUseCase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import persistence.Order.OrderDAO;
import persistence.Order.OrderGateway;
import presenters.GetOrders.GetOrdersPresenter;
import presenters.GetOrders.GetOrdersViewModel;

@WebServlet("/admin/orders")
public class OrderListControllerWeb extends HttpServlet {
    private GetOrdersInputBoundary useCase;

    @Override
    public void init() throws ServletException {
        try {
            OrderGateway gateway = new OrderDAO();
            useCase = new GetOrdersUseCase(gateway);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Lỗi khởi tạo OrderDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("roleCode"))) {
            resp.sendRedirect("../login.jsp?error=Không có quyền");
            return;
        }

        String keyword = req.getParameter("keyword");
        String status = req.getParameter("status");
        GetOrdersInputData input = new GetOrdersInputData(keyword, status);
        GetOrdersPresenter presenter = new GetOrdersPresenter();

        useCase.execute(input, presenter);
        GetOrdersViewModel vm = presenter.getViewModel();

        req.setAttribute("orders", vm.getOrders());
        req.setAttribute("totalCount", vm.getTotalCount());
        req.setAttribute("error", vm.getError());
        req.setAttribute("keyword", keyword);
        req.setAttribute("status", status);

        req.getRequestDispatcher("/admin/orders.jsp").forward(req, resp);
    }
}