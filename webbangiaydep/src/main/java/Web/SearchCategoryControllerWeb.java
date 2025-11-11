package Web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import business.SearchCategory.SearchCategoryInputData;
import business.SearchCategory.SearchCategoryUseCase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import persistence.Category.CategoryDAO;
import persistence.Category.CategoryDTO;
import persistence.Category.CategoryGateway;
import presenters.SearchCategory.SearchCategoryPresenter;
import presenters.SearchCategory.SearchCategoryViewModel;

@WebServlet("/admin/searchCategories")
public class SearchCategoryControllerWeb extends HttpServlet {
    private SearchCategoryUseCase searchCategoryUseCase;

    @Override
    public void init() throws ServletException {
        try {
            CategoryGateway gateway = new CategoryDAO();
            this.searchCategoryUseCase = new SearchCategoryUseCase(gateway);
        } catch (Exception e) {
            throw new ServletException("Lỗi khởi tạo SearchCategory", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            
            HttpSession session = req.getSession(false);
            if (session == null || !"ADMIN".equals(session.getAttribute("roleCode"))) {
                resp.sendRedirect("../login.jsp?error=Không có quyền");
                return;
            }
            
            String keyword = req.getParameter("keyword");
            String parentIdStr = req.getParameter("parentId");
            Integer parentId = null;
            if (parentIdStr != null && !parentIdStr.trim().isEmpty()) {
                try {
                    parentId = Integer.parseInt(parentIdStr);
                } catch (NumberFormatException e) {
                    // ignore
                }
            }
            
            SearchCategoryInputData input = new SearchCategoryInputData(keyword, parentId, 20);
            SearchCategoryPresenter presenter = new SearchCategoryPresenter();
            searchCategoryUseCase.execute(input, presenter);
            SearchCategoryViewModel vm = presenter.getViewModel();
            
            req.setAttribute("categories", vm.categories);
            req.setAttribute("totalCount", vm.totalCount);
            req.setAttribute("keyword", keyword != null ? keyword : "");
            req.setAttribute("parentId", parentId);
            
            // Load all categories for dropdown
            CategoryGateway gateway = new CategoryDAO();
            List<CategoryDTO> allCategories = gateway.getAllCategories();
            req.setAttribute("allCategories", allCategories);
            
            // Success / error
            String success = req.getParameter("success");
            if (success != null) req.setAttribute("success", success);
            String error = req.getParameter("error");
            if (error != null) req.setAttribute("error", error);
            
            req.getRequestDispatcher("/admin/search-category.jsp").forward(req, resp);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }
}