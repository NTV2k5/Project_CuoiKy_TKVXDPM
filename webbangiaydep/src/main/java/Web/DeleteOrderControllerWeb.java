package Web;

import java.io.IOException;
import java.sql.SQLException;

import business.DeleteOrder.DeleteOrderInputBoundary;
import business.DeleteOrder.DeleteOrderInputData;
import business.DeleteOrder.DeleteOrderUseCase;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import persistence.Order.OrderDAO;
import persistence.Order.OrderGateway;
import presenters.DeleteOrder.DeleteOrderPresenter;
import presenters.DeleteOrder.DeleteOrderViewModel;

@WebServlet("/admin/delete-order")
public class DeleteOrderControllerWeb extends HttpServlet {
    private DeleteOrderInputBoundary useCase;

    @Override
    public void init() {
        try {
            OrderGateway gateway = new OrderDAO();
            useCase = new DeleteOrderUseCase(gateway);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Lỗi khởi tạo", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idStr = req.getParameter("id");
        if (idStr == null) {
            resp.sendRedirect("orders?error=Thiếu ID");
            return;
        }

        long id = Long.parseLong(idStr);
        DeleteOrderInputData input = new DeleteOrderInputData(id);
        DeleteOrderPresenter presenter = new DeleteOrderPresenter();

        useCase.execute(input, presenter);
        DeleteOrderViewModel vm = presenter.getViewModel();

        if (vm.isSuccess()) {
            resp.sendRedirect("orders?success=" + java.net.URLEncoder.encode(vm.getMessage(), "UTF-8"));
        } else {
            resp.sendRedirect("orders?error=" + java.net.URLEncoder.encode(vm.getMessage(), "UTF-8"));
        }
    }
}