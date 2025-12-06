package Web;

import business.AddToCart.*;
import persistence.AddToCart.AddToCartDAO;
import persistence.AddToCart.AddToCartDAOInterFace;
import presenters.AddToCart.AddToCartPresenter;
import presenters.AddToCart.AddToCartViewModel;
import presenters.Repository.AddToCartRepositoryImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/AddToCart")
public class AddToCartControllerWeb extends HttpServlet {

    private AddToCartUseCase addToCartUseCase;

    @Override
    public void init() {
        try {
            AddToCartDAOInterFace dao = new AddToCartDAO();
            AddToCartRepository repository = new AddToCartRepositoryImpl(dao);
            this.addToCartUseCase = new AddToCartUseCase(repository);
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
            // === 1. Lấy và validate tham số ===
            String productIdStr = req.getParameter("productId");
            String variantIdStr = req.getParameter("variantId");
            String quantityStr = req.getParameter("quantity");

            if (isEmpty(productIdStr)) { sendError(out, "Thiếu productId"); return; }
            if (isEmpty(variantIdStr)) { sendError(out, "Thiếu variantId"); return; }
            if (isEmpty(quantityStr)) { sendError(out, "Thiếu số lượng"); return; }

            Long productId = Long.parseLong(productIdStr.trim()); 
            Long variantId = Long.parseLong(variantIdStr.trim()); 
            int quantity = Integer.parseInt(quantityStr.trim());

            if (quantity <= 0) { sendError(out, "Số lượng phải > 0"); return; }

            // === 2. Lấy userId từ session ===
            HttpSession session = req.getSession();
            Object userIdObj = session.getAttribute("userId");
            Long userId = null;
            if (userIdObj instanceof Number) 
            {
                userId = ((Number) userIdObj).longValue();
            }
            // === 3. Tạo Input Data ===
            AddToCartInputData input = new AddToCartInputData(userId, productId, variantId, quantity);

            // === 4. Tạo Presenter + ViewModel riêng cho mỗi request (rất quan trọng!) ===
            AddToCartViewModel viewModel = new AddToCartViewModel();
            AddToCartPresenter presenter = new AddToCartPresenter(viewModel);

            // === 5. Thực thi UseCase ===
            addToCartUseCase.execute(input, presenter);

            // === 6. Trả về JSON từ ViewModel ===
            AddToCartViewModel vm = presenter.getViewModel();

            out.print("{");
            out.print("\"success\":" + vm.success + ",");
            out.print("\"message\":\"" + escapeJson(vm.message) + "\",");
            out.print("\"totalItems\":" + vm.totalItems + ",");
            out.print("\"totalPrice\":" + vm.totalPrice);
            out.print("}");
            resp.setStatus(200);

        } catch (NumberFormatException e) {
            sendError(out, "Dữ liệu không hợp lệ");
        } catch (Exception e) {
            sendError(out, "Lỗi hệ thống: " + e.getMessage());
            e.printStackTrace();
        }
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
                  .replace("\n", "\\n")
                  .replace("\r", "\\r");
    }
}