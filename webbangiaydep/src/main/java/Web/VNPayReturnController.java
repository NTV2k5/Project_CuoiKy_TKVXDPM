package Web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

import business.ProcessPayment.ProcessPaymentInputBoundary;
import business.ProcessPayment.ProcessPaymentInputData;
import business.ProcessPayment.ProcessPaymentUseCase;
import persistence.Order.OrderDAO;
import persistence.Order.OrderGateway;
import presenters.ProcessPayment.ProcessPaymentPresenter;
import presenters.ProcessPayment.ProcessPaymentViewModel;

@WebServlet("/vnpay-return")
public class VNPayReturnController extends HttpServlet {

    private ProcessPaymentInputBoundary processPaymentUseCase;

    @Override
    public void init() throws ServletException {
        try {
            // Khởi tạo Gateway và UseCase
            OrderGateway orderGateway = new OrderDAO();
            this.processPaymentUseCase = new ProcessPaymentUseCase(orderGateway);
        } catch (Exception e) {
            throw new ServletException("Lỗi khởi tạo VNPayReturnController", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // 1. Lấy tham số từ URL VNPAY trả về
            String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
            String vnp_TxnRef = request.getParameter("vnp_TxnRef");       // Mã đơn hàng
            String vnp_TransactionNo = request.getParameter("vnp_TransactionNo"); // Mã giao dịch
            String vnp_Amount = request.getParameter("vnp_Amount");       // Số tiền (nhân 100)

            long orderId = Long.parseLong(vnp_TxnRef);
            BigDecimal amount = (vnp_Amount != null) 
                ? new BigDecimal(vnp_Amount).divide(new BigDecimal(100)) 
                : BigDecimal.ZERO;

            // 2. Đóng gói InputData
            ProcessPaymentInputData inputData = new ProcessPaymentInputData(
                orderId, 
                vnp_TransactionNo, 
                vnp_ResponseCode, 
                amount
            );

            // 3. Khởi tạo Presenter
            ProcessPaymentPresenter presenter = new ProcessPaymentPresenter();

            // 4. Thực thi Use Case
            processPaymentUseCase.execute(inputData, presenter);

            // 5. Lấy kết quả
            ProcessPaymentViewModel result = presenter.getViewModel();

            // 6. Điều hướng
            if (result.success) {
                // THÀNH CÔNG: Chuyển về ResultOrder.jsp
                response.sendRedirect("ResultOrder.jsp?paymentSuccess=true&orderId=" + result.orderId);
            } else {
                // THẤT BẠI: Chuyển về ResultOrder.jsp kèm lỗi
                String encodedMsg = java.net.URLEncoder.encode(result.message, "UTF-8");
                response.sendRedirect("ResultOrder.jsp?error=" + encodedMsg);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=Lỗi xử lý thanh toán");
        }
    }
}