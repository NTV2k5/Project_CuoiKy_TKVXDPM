package Web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import business.AddToCart.AddToCartRepository;
import business.OrderShoe.*;
import business.ProcessPayment.PaymentGatewayRepository;
import presenters.ViewShoeCart.ViewShoeCartItem;
import presenters.OrderShoe.*;
import presenters.Repository.AddToCartRepositoryImpl;
import persistence.Adapter.InventoryRepositoryImpl;
import persistence.Adapter.OrderShoeRepositoryImpl;
import persistence.Adapter.VNPayGatewayImpl;
import persistence.AddToCart.AddToCartDAO;
import persistence.AddToCart.AddToCartDAOInterFace;
import persistence.OrderShoe.InventoryDAO;
import persistence.OrderShoe.InventoryDAOInterface;
import persistence.OrderShoe.OrderShoeDAO;
import persistence.OrderShoe.OrderShoeDAOInterface;

@WebServlet("/OrderShoeServlet")
public class OrderShoeControllerWeb extends HttpServlet {
    private OrderShoeRepository orderRepository;
    private ProductInventoryRepository inventoryRepository;
    private AddToCartRepository cartRepository;
    private PaymentGatewayRepository vnpayGateway;

    @Override
    public void init() throws ServletException {
        try {
            // Khởi tạo các DAO và Repository cho Order & Inventory
            OrderShoeDAOInterface dao = new OrderShoeDAO();
            InventoryDAOInterface daoInventory = new InventoryDAO();
            this.orderRepository = new OrderShoeRepositoryImpl(dao);
            this.inventoryRepository = new InventoryRepositoryImpl(daoInventory);

            // 2. Khởi tạo Repository cho Giỏ hàng
            AddToCartDAOInterFace cartDao = new AddToCartDAO();
            this.cartRepository = new AddToCartRepositoryImpl(cartDao);
            this.vnpayGateway = new VNPayGatewayImpl(); 

        } catch (Exception e) {
            throw new ServletException("Lỗi khởi tạo Controller", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        // 1. Kiểm tra đăng nhập
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Lấy danh sách sản phẩm đã chọn từ CheckoutSelectedServlet
        @SuppressWarnings("unchecked")
        List<ViewShoeCartItem> cartItems = (List<ViewShoeCartItem>) request.getAttribute("cartItems");

        // Fallback: nếu truy cập trực tiếp thì lấy từ session
        if (cartItems == null || cartItems.isEmpty()) {
            cartItems = (List<ViewShoeCartItem>) session.getAttribute("selectedCartItems");
            System.out.println("=== ORDER DEBUG ===");
            System.out.println("cartItems từ request: " + request.getAttribute("cartItems"));
            System.out.println("cartItems từ session: " + session.getAttribute("selectedCartItems"));
            System.out.println("userId: " + userId);
        }

        if (cartItems == null || cartItems.isEmpty()) {
            request.setAttribute("errorMessage", "Giỏ hàng trống! Vui lòng chọn sản phẩm.");
            request.getRequestDispatcher("/Cart.jsp").forward(request, response);
            return;
        }

        // 3. Tạo InputData cho Usecase
        OrderShoeInputData input = new OrderShoeInputData();
        input.setUserId(userId);
        input.setCustomerName(request.getParameter("customerName"));
        input.setPhone(request.getParameter("phone"));
        input.setAddress(request.getParameter("address"));
        input.setEmail(request.getParameter("email"));
        input.setPaymentMethod(request.getParameter("paymentMethod"));

        // Chuyển cart → danh sách item (variantId từ Long → int)
        List<OrderShoeInputData.RequestItemData> items = new ArrayList<>();
        for (ViewShoeCartItem item : cartItems) {
            OrderShoeInputData.RequestItemData req = new OrderShoeInputData.RequestItemData();
            req.setProductId(item.getProductId());
            req.setVariantId(item.getVariantId());
            req.setQuantity(item.getQuantity());
            items.add(req);
        }
        input.setItems(items);

        // 4. Tạo Presenter mới cho request này (tránh lưu trạng thái cũ)
        OrderShoePresenter presenter = new OrderShoePresenter();

        // 5. Tạo Usecase mới với các Repository đã inject từ init()
        OrderShoeUsecase usecase = new OrderShoeUsecase(
            this.orderRepository,      // ← chỉ dùng interface
            this.inventoryRepository,  // ← chỉ dùng interface
            presenter,
            this.cartRepository,
            this.vnpayGateway
        );

        // 6. Thực hiện đặt hàng
        usecase.execute(input);

        // 7. Lấy kết quả từ Presenter
        OrderShoeViewModel result = presenter.getViewModel();
        request.setAttribute("orderResult", result);
        if ("PENDING_PAYMENT".equals(result.getStatus())) {
        System.out.println("=== VNPAY DEBUG START ===");
        System.out.println("VNPAY URL GENERATED: " + result.getPaymentUrl()); // <--- IN RA CONSOLE DÒNG NÀY
        System.out.println("=== VNPAY DEBUG END ===");
    }

        // 8. Xóa giỏ hàng nếu đặt thành công
       // Thay toàn bộ đoạn xóa giỏ hàng cũ bằng đoạn này:
        if ("SUCCESS".equals(result.getStatus()) || "PENDING_PAYMENT".equals(result.getStatus())) {
            List<ViewShoeCartItem> fullCart = (List<ViewShoeCartItem>) session.getAttribute("cartItems");
            if (fullCart != null) {
                List<ViewShoeCartItem> remaining = new ArrayList<>();
                for (ViewShoeCartItem item : fullCart) {
                    boolean paid = false;
                    for (ViewShoeCartItem paidItem : cartItems) {
                        if (item.getProductId().equals(paidItem.getProductId()) &&
                            item.getSize() == paidItem.getSize() &&
                            item.getColor().equals(paidItem.getColor())) {
                            paid = true; break;
                        }
                    }
                    if (!paid) remaining.add(item);
                }
                if (remaining.isEmpty()) {
                    session.removeAttribute("cartItems");
                } else {
                    session.setAttribute("cartItems", remaining);
                }
            }
            session.removeAttribute("selectedCartItems");
            session.removeAttribute("selectedTotalAmount");
        }

        // 9. Quay lại trang checkout.jsp → JS sẽ tự hiển thị QR hoặc Thank You
        request.getRequestDispatcher("ResultOrder.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Không cho truy cập trực tiếp bằng GET
        response.sendRedirect("Cart.jsp");
    }
}