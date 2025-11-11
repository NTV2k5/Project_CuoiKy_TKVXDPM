// package Web;

// import business.AddToCart.*;
// import persistence.AddToCart.AddToCartDAO;
// import presenters.AddToCart.AddToCartPresenter;
// import presenters.AddToCart.AddToCartViewModel;
// import persistence.AddToCart.AddToCartGateway;
// import jakarta.servlet.annotation.WebServlet;
// import jakarta.servlet.http.*;
// import java.io.IOException;
// import java.io.PrintWriter;

// @WebServlet("/AddToCart")
// public class AddToCartControllerWeb extends HttpServlet {

//     private AddToCartUseCase addToCartUseCase;

//     @Override
//     public void init() {
//         try {
//             AddToCartGateway gateway = new AddToCartDAO();
//             this.addToCartUseCase = new AddToCartUseCase(gateway);
//         } catch (Exception e) {
//             throw new RuntimeException("Lỗi khởi tạo AddToCart", e);
//         }
//     }

//     @Override
//     protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//         resp.setContentType("application/json");
//         resp.setCharacterEncoding("UTF-8");
//         PrintWriter out = resp.getWriter();

//         try {
//             String productIdStr = req.getParameter("productId");
//             String variantIdStr = req.getParameter("variantId");
//             String quantityStr = req.getParameter("quantity");
//             String priceStr = req.getParameter("productPrice");

//             if (isEmpty(productIdStr)) { sendError(out, "Thiếu productId"); return; }
//             if (isEmpty(variantIdStr)) { sendError(out, "Thiếu variantId"); return; }
//             if (isEmpty(quantityStr)) { sendError(out, "Thiếu số lượng"); return; }
//             if (isEmpty(priceStr)) { sendError(out, "Thiếu giá sản phẩm"); return; }

//             int productId = Integer.parseInt(productIdStr.trim());
//             int variantId = Integer.parseInt(variantIdStr.trim());
//             int quantity = Integer.parseInt(quantityStr.trim());
//             double price = Double.parseDouble(priceStr.trim());

//             if (productId <= 0) { sendError(out, "productId không hợp lệ"); return; }
//             if (variantId <= 0) { sendError(out, "variantId không hợp lệ"); return; }
//             if (quantity <= 0) { sendError(out, "Số lượng phải > 0"); return; }

//             HttpSession session = req.getSession();
//             Integer userIdObj = (Integer) session.getAttribute("userId");
//             int userId = (userIdObj != null) ? userIdObj : 0;

//             AddToCartInputData input = new AddToCartInputData(userId, productId, variantId, quantity, price);

//             AddToCartPresenter presenter = new AddToCartPresenter();
//             addToCartUseCase.execute(input, presenter);
//             AddToCartViewModel vm = presenter.getViewModel();

//             out.print("{");
//             out.print("\"success\":" + vm.success + ",");
//             out.print("\"message\":\"" + escapeJson(vm.message) + "\",");
//             out.print("\"totalItems\":" + vm.totalItems);
//             out.print("}");
//             resp.setStatus(200);
//             System.out.println("variantId nhận được: " + variantIdStr);

//         } catch (NumberFormatException e) {
//             sendError(out, "Dữ liệu số không hợp lệ");
//         } catch (Exception e) {
//             sendError(out, "Lỗi hệ thống: " + e.getMessage());
//         }
//     }

//     private void sendError(PrintWriter out, String message) {
//         out.print("{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
//     }

//     private boolean isEmpty(String s) {
//         return s == null || s.trim().isEmpty();
//     }

//     private String escapeJson(String str) {
//         if (str == null) return "";
//         return str.replace("\\", "\\\\").replace("\"", "\\\"");
//     }
// }