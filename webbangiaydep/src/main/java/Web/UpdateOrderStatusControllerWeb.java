package Web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import business.UpdateOrderStatus.*;
import persistence.Order.OrderDAO;
import persistence.Order.OrderGateway;
import presenters.UpdateOrderStatus.UpdateOrderStatusPresenter;
import presenters.UpdateOrderStatus.UpdateOrderStatusViewModel;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

// Web/UpdateOrderStatusControllerWeb.java
@WebServlet("/admin/update-order-status")
public class UpdateOrderStatusControllerWeb extends HttpServlet {
    private UpdateOrderStatusInputBoundary useCase;

    @Override
    public void init() throws ServletException {
        try {
            OrderGateway gateway = new OrderDAO();
            useCase = new UpdateOrderStatusUseCase(gateway);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Lỗi khởi tạo OrderDAO", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("roleCode"))) {
            resp.sendRedirect("../login.jsp?error=Không có quyền");
            return;
        }

        String idStr = req.getParameter("id");
        String status = req.getParameter("status");
        Long adminId = (Long) session.getAttribute("userId");

        if (idStr == null || status == null) {
            resp.sendRedirect("orders?error=Thiếu thông tin");
            return;
        }

        long id = Long.parseLong(idStr);
        UpdateOrderStatusInputData input = new UpdateOrderStatusInputData(id, status, adminId);
        UpdateOrderStatusPresenter presenter = new UpdateOrderStatusPresenter();

        useCase.execute(input, presenter);
        UpdateOrderStatusViewModel vm = presenter.getViewModel();

        String redirect = "order-detail?id=" + id;
        if (vm.isSuccess()) {
            redirect += "&success=" + URLEncoder.encode(vm.getMessage(), "UTF-8");
        } else {
            redirect += "&error=" + URLEncoder.encode(vm.getMessage(), "UTF-8");
        }
        resp.sendRedirect(redirect);
    }
}