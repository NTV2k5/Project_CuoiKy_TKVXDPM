package Web;

import java.io.IOException;
import java.util.List;

import business.UpdateProduct.UpdateProductInputData;
import business.UpdateProduct.UpdateProductUseCase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import persistence.Category.CategoryDTO;
import persistence.Product.ProductDAO;
import persistence.Product.ProductDTO;
import persistence.Product.ProductGateway;
import presenters.UpdateProduct.UpdateProductPresenter;
import presenters.UpdateProduct.UpdateProductViewModel;


@WebServlet("/admin/editProduct")
public class UpdateProductControllerWeb extends HttpServlet {
    private UpdateProductUseCase updateProductUseCase;
    private ProductGateway productGateway;

    @Override
    public void init() throws ServletException {
        try {
            productGateway = new ProductDAO();
            this.updateProductUseCase = new UpdateProductUseCase(productGateway);
        } catch (Exception e) {
            throw new ServletException("Lỗi khởi tạo UpdateProduct", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null) {
            resp.sendRedirect("products?error=ID không hợp lệ");
            return;
        }
        long id = Long.parseLong(idStr);
        ProductDTO product = productGateway.getProductById(id);
        if (product == null) {
            resp.sendRedirect("products?error=Sản phẩm không tồn tại");
            return;
        }
        req.setAttribute("product", product);

        // Load categories
        List<CategoryDTO> categories = productGateway.getAllCategories();
        req.setAttribute("categories", categories);

        req.getRequestDispatcher("/admin/edit-product.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("roleCode"))) {
            resp.sendRedirect("../login.jsp?error=Không có quyền");
            return;
        }

        String idStr = req.getParameter("id");
        String sku = req.getParameter("sku");
        String name = req.getParameter("name");
        String shortDescription = req.getParameter("shortDescription");
        String description = req.getParameter("description");
        String imageUrl = req.getParameter("imageUrl");
        String brand = req.getParameter("brand");
        String categoryIdStr = req.getParameter("categoryId");
        String isActiveStr = req.getParameter("isActive");

        if (idStr == null || name == null || categoryIdStr == null) {
            req.setAttribute("error", "Thiếu thông tin");
            req.getRequestDispatcher("/admin/edit-product.jsp").forward(req, resp);
            return;
        }

        long id = Long.parseLong(idStr);
        int categoryId = Integer.parseInt(categoryIdStr);
        boolean isActive = "on".equals(isActiveStr);

        UpdateProductInputData input = new UpdateProductInputData(id, sku, name, shortDescription, description, imageUrl, brand, categoryId, isActive);
        UpdateProductPresenter presenter = new UpdateProductPresenter();
        updateProductUseCase.execute(input, presenter);
        UpdateProductViewModel vm = presenter.getViewModel();

        if (vm.success) {
            resp.sendRedirect("products?success=Cập nhật thành công");
        } else {
            req.setAttribute("error", vm.message);
            req.getRequestDispatcher("/admin/edit-product.jsp").forward(req, resp);
        }
    }
}