// Web/ProductListControllerWeb.java (new controller for /admin/products to load all products)
package Web;

import java.io.IOException;
import java.util.List;

import business.SearchProduct.SearchProductInputData;
import business.SearchProduct.SearchProductUseCase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import persistence.Category.CategoryDTO;
import persistence.Product.ProductDAO;
import persistence.Product.ProductGateway;
import presenters.SearchProduct.SearchProductPresenter;
import presenters.SearchProduct.SearchProductViewModel;

@WebServlet("/admin/products")
public class ProductListControllerWeb extends HttpServlet {
    private SearchProductUseCase searchProductUseCase;
    private ProductGateway productGateway;

    @Override
    public void init() throws ServletException {
        try {
            productGateway = new ProductDAO();
            this.searchProductUseCase = new SearchProductUseCase(productGateway);
        } catch (Exception e) {
            throw new ServletException("Lỗi khởi tạo ProductList", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("roleCode"))) {
            resp.sendRedirect("../login.jsp?error=Không có quyền");
            return;
        }

        // Load all products (keyword null, category null, limit 50 for list)
        SearchProductInputData input = new SearchProductInputData(null, null, 50);
        SearchProductPresenter presenter = new SearchProductPresenter();
        searchProductUseCase.execute(input, presenter);
        SearchProductViewModel vm = presenter.getViewModel();

        req.setAttribute("products", vm.products);
        req.setAttribute("totalCount", vm.totalCount);
        req.setAttribute("keyword", ""); // Empty for all
        req.setAttribute("categoryId", null);

        // Load categories for search dropdown
        List<CategoryDTO> categories = productGateway.getAllCategories();
        req.setAttribute("categories", categories);

        // Handle success/error from param
        String success = req.getParameter("success");
        if (success != null) {
            req.setAttribute("success", success);
        }
        String error = req.getParameter("error");
        if (error != null) {
            req.setAttribute("error", error);
        }

        req.getRequestDispatcher("/admin/products.jsp").forward(req, resp);
    }
}