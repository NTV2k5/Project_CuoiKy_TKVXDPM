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
@WebServlet("/admin/searchProducts")
public class SearchProductControllerWeb extends HttpServlet {
    private SearchProductUseCase searchProductUseCase;
    private ProductGateway productGateway;

    @Override
    public void init() throws ServletException {
        try {
            productGateway = new ProductDAO();
            this.searchProductUseCase = new SearchProductUseCase(productGateway);
        } catch (Exception e) {
            throw new ServletException("Lỗi khởi tạo SearchProduct", e);
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

        String keyword = req.getParameter("keyword");
        String categoryIdStr = req.getParameter("categoryId");
        Integer categoryId = null;
        if (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) {
            categoryId = Integer.parseInt(categoryIdStr);
        }

        SearchProductInputData input = new SearchProductInputData(keyword, categoryId, 20); // Limit 20
        SearchProductPresenter presenter = new SearchProductPresenter();
        searchProductUseCase.execute(input, presenter);
        SearchProductViewModel vm = presenter.getViewModel();

        req.setAttribute("products", vm.products);
        req.setAttribute("totalCount", vm.totalCount);
        req.setAttribute("keyword", keyword);
        req.setAttribute("categoryId", categoryId);

        // Load categories for dropdown
        List<CategoryDTO> categories = productGateway.getAllCategories();
        req.setAttribute("categories", categories);

        req.getRequestDispatcher("/admin/products.jsp").forward(req, resp);
    }
}