package Web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.CreateOrder.CreateOrderInputBoundary;
import business.CreateOrder.CreateOrderInputData;
import business.CreateOrder.CreateOrderUseCase;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import persistence.Order.OrderDAO;
import persistence.Order.OrderGateway;
import presenters.CreateOrder.CreateOrderPresenter;
import presenters.CreateOrder.CreateOrderViewModel;

@WebServlet("/admin/create-order")
public class CreateOrderControllerWeb extends HttpServlet {
    private CreateOrderInputBoundary useCase;

    @Override
    public void init() {
        try {
            OrderGateway gateway = new OrderDAO();
            useCase = new CreateOrderUseCase(gateway);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Lỗi khởi tạo", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Giả lập dữ liệu (thực tế lấy từ form)
        long userId = 1;
        long addressId = 1;
        String paymentMethod = "COD";
        double total = 1500000;

        List<CreateOrderInputData.OrderItemInput> items = new ArrayList<>();
        items.add(new CreateOrderInputData.OrderItemInput(1, 2, 500000));
        items.add(new CreateOrderInputData.OrderItemInput(2, 1, 550000));

        CreateOrderInputData input = new CreateOrderInputData(userId, addressId, paymentMethod, total, items);
        CreateOrderPresenter presenter = new CreateOrderPresenter();

        useCase.execute(input, presenter);
        CreateOrderViewModel vm = presenter.getViewModel();

        if (vm.isSuccess()) {
            resp.sendRedirect("orders?success=" + java.net.URLEncoder.encode(vm.getMessage() + " (ID: " + vm.getOrderId() + ")", "UTF-8"));
        } else {
            resp.sendRedirect("orders?error=" + java.net.URLEncoder.encode(vm.getMessage(), "UTF-8"));
        }
    }
}