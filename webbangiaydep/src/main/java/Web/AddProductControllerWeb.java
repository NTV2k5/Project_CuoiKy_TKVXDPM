package Web;

import java.io.IOException;

import business.AddProduct.AddProductInputData;
import business.AddProduct.AddProductUseCase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import persistence.Product.ProductDAO;
import persistence.Product.ProductGateway;
import presenters.AddProduct.AddProductPresenter;
import presenters.AddProduct.AddProductViewModel;

@WebServlet("/admin/addProduct")
public class AddProductControllerWeb extends HttpServlet {
    private AddProductUseCase addProductUseCase;

    @Override
    public void init() {
        try {
            ProductGateway gateway = new ProductDAO();
            this.addProductUseCase = new AddProductUseCase(gateway);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khởi tạo AddProduct", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Show form
        req.getRequestDispatcher("/admin/add-product.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // Check admin role
        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("roleCode"))) {
            resp.sendRedirect("../login.jsp?error=Không có quyền truy cập");
            return;
        }

        String sku = req.getParameter("sku");
        String name = req.getParameter("name");
        String shortDescription = req.getParameter("shortDescription");
        String description = req.getParameter("description");
        String imageUrl = req.getParameter("imageUrl");
        String brand = req.getParameter("brand");
        String categoryIdStr = req.getParameter("categoryId");
        String isActiveStr = req.getParameter("isActive");

        if (sku == null || name == null || categoryIdStr == null) {
            req.setAttribute("error", "Thiếu thông tin bắt buộc");
            req.getRequestDispatcher("/admin/add-product.jsp").forward(req, resp);
            return;
        }

        int categoryId = Integer.parseInt(categoryIdStr);
        boolean isActive = "on".equals(isActiveStr);

        AddProductInputData input = new AddProductInputData(sku, name, shortDescription, description, imageUrl, brand, categoryId, isActive);
        AddProductPresenter presenter = new AddProductPresenter();
        addProductUseCase.execute(input, presenter);
        AddProductViewModel vm = presenter.getViewModel();

        if (vm.success) {
            resp.sendRedirect("products?success=Thêm sản phẩm thành công");
        } else {
            req.setAttribute("error", vm.message);
            req.getRequestDispatcher("/admin/add-product.jsp").forward(req, resp);
        }
    }
}