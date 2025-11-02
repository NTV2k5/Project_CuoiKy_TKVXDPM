// Web/AddToCartControllerWeb.java
package Web;

import business.AddToCart.*;
import persistence.AddToCart.AddToCartDAO;
import persistence.AddToCart.AddToCartGateway;
import presenters.AddToCart.AddToCartPresenter;
import presenters.AddToCart.AddToCartViewModel;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/AddToCart")
public class AddToCartControllerWeb extends HttpServlet {

    private AddToCartUsecase addToCartUsecase;

    @Override
    public void init() {
        try {
            AddToCartGateway gateway = new AddToCartDAO();
            this.addToCartUsecase = new AddToCartUsecase(gateway);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khởi tạo AddToCart", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            System.out.println("DEBUG: productId param = " + req.getParameter("productId"));
            System.out.println("DEBUG: ContentType = " + req.getContentType());
            String productIdStr = req.getParameter("productId");
            String size = req.getParameter("size");
            String color = req.getParameter("color");
            String quantityStr = req.getParameter("quantity");
            String priceStr = req.getParameter("price");

            if (isEmpty(productIdStr)) { sendError(out, "Thiếu productId"); return; }
            if (isEmpty(size)) { sendError(out, "Chưa chọn size"); return; }
            if (isEmpty(color)) { sendError(out, "Chưa chọn màu"); return; }
            if (isEmpty(quantityStr)) { sendError(out, "Thiếu số lượng"); return; }
            if (isEmpty(priceStr)) { sendError(out, "Thiếu giá"); return; }

            int productId = Integer.parseInt(productIdStr.trim());
            int quantity = Integer.parseInt(quantityStr.trim());
            double price = Double.parseDouble(priceStr.trim());

            if (productId <= 0) { sendError(out, "productId không hợp lệ"); return; }
            if (quantity <= 0) { sendError(out, "Số lượng phải > 0"); return; }
            if (price <= 0) { sendError(out, "Giá phải > 0"); return; }

            HttpSession session = req.getSession();
            Integer userId = (Integer) session.getAttribute("userId");
            String sessionId = session.getId();

            AddToCartInputData input = new AddToCartInputData(
                productId, size.trim(), color.trim(), quantity, price, userId, sessionId
            );

            AddToCartPresenter presenter = new AddToCartPresenter();
            addToCartUsecase.execute(input, presenter);
            AddToCartViewModel viewModel = presenter.getViewModel();

            out.print("{");
            out.print("\"success\":" + viewModel.success + ",");
            out.print("\"message\":\"" + escapeJson(viewModel.message) + "\",");
            out.print("\"totalItems\":" + viewModel.totalItems);
            out.print("}");
            resp.setStatus(200);

        } catch (NumberFormatException e) {
            sendError(out, "Dữ liệu số không hợp lệ");
        } catch (Exception e) {
            sendError(out, "Lỗi hệ thống: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().print("POST /AddToCart để thêm vào giỏ hàng");
    }

    private void sendError(PrintWriter out, String message) {
        out.print("{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\b", "\\b")
                  .replace("\f", "\\f")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}