package Web;

import java.io.IOException;

import business.SearchCategory.SearchCategoryInputData;
import business.SearchCategory.SearchCategoryUseCase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import persistence.Category.CategoryDAO;
import persistence.Category.CategoryGateway;
import presenters.SearchCategory.SearchCategoryPresenter;
import presenters.SearchCategory.SearchCategoryViewModel;

@WebServlet("/admin/categories")
public class CategoryListControllerWeb extends HttpServlet {
    private SearchCategoryUseCase searchCategoryUseCase;

    @Override
    public void init() throws ServletException {
        try {
            CategoryGateway gateway = new CategoryDAO();
            this.searchCategoryUseCase = new SearchCategoryUseCase(gateway);
        } catch (Exception e) {
            throw new ServletException("Lỗi khởi tạo CategoryList", e);
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

        // Load all categories by default
        SearchCategoryInputData input = new SearchCategoryInputData(null, null, 50);
        SearchCategoryPresenter presenter = new SearchCategoryPresenter();
        searchCategoryUseCase.execute(input, presenter);
        SearchCategoryViewModel vm = presenter.getViewModel();

        req.setAttribute("categories", vm.categories);
        req.setAttribute("totalCount", vm.totalCount);
        req.setAttribute("keyword", "");
        req.setAttribute("parentId", null);

        // Handle success/error
        String success = req.getParameter("success");
        if (success != null) req.setAttribute("success", success);
        String error = req.getParameter("error");
        if (error != null) req.setAttribute("error", error);

        req.getRequestDispatcher("/admin/categories.jsp").forward(req, resp);
    }
}