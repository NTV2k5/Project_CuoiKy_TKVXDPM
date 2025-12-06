package Web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import presenters.ViewShoeCart.ViewShoeCartItem;

@WebServlet("/checkout-selected")
public class CheckoutSelectedServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        // 1. Lấy toàn bộ giỏ hàng từ session
        @SuppressWarnings("unchecked")
        List<ViewShoeCartItem> fullCart = (List<ViewShoeCartItem>) session.getAttribute("cartItems");

        if (fullCart == null || fullCart.isEmpty()) {
            response.sendRedirect("Cart.jsp");
            return;
        }

        // 2. Lấy danh sách các món được chọn từ Cart.jsp (name="selected")
        String[] selectedValues = request.getParameterValues("selected");

        if (selectedValues == null || selectedValues.length == 0) {
            request.setAttribute("errorMessage", "Vui lòng chọn ít nhất 1 sản phẩm để thanh toán!");
            request.getRequestDispatcher("/Cart.jsp").forward(request, response);
            return;
        }

        // 3. Tạo danh sách sản phẩm đã chọn + tính tổng tiền
        List<ViewShoeCartItem> selectedItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (String value : selectedValues) {
            // value có dạng: productId-size-color   (ví dụ: 15-40-Đen)
            String[] parts = value.split("-");
            if (parts.length < 3) continue;

            try {
                Long productId = Long.parseLong(parts[0]);
                int size = Integer.parseInt(parts[1]);
                String color = parts[2];

                // Tìm món tương ứng trong giỏ hàng
                for (ViewShoeCartItem item : fullCart) {
                    if (item.getProductId().equals(productId) &&
                        item.getSize() == size &&
                        item.getColor().trim().equals(color)) {

                        selectedItems.add(item);
                        totalAmount = totalAmount.add(BigDecimal.valueOf(item.getTotalPrice()));
                        break;
                    }
                }
            } catch (Exception e) {
                // Bỏ qua nếu lỗi parse
            }
        }

        if (selectedItems.isEmpty()) {
            request.setAttribute("errorMessage", "Không tìm thấy sản phẩm nào đã chọn!");
            request.getRequestDispatcher("/Cart.jsp").forward(request, response);
            return;
        }

        // 4. Đưa dữ liệu sang checkout.jsp
        request.setAttribute("cartItems", selectedItems);
        request.setAttribute("totalAmount", totalAmount);

        // 5. Lưu vào session để tránh mất dữ liệu khi F5 trang checkout
        session.setAttribute("selectedCartItems", selectedItems);
        session.setAttribute("selectedTotalAmount", totalAmount);

        // 6. Chuyển hướng sang trang thanh toán
        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Không cho truy cập trực tiếp bằng GET
        response.sendRedirect("Cart.jsp");
    }
}