package Web;

import java.io.IOException;

import business.AddCategory.AddCategoryInputData;
import business.AddCategory.AddCategoryUseCase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import persistence.Category.CategoryDAO;
import persistence.Category.CategoryGateway;
import presenters.AddCategory.AddCategoryPresenter;
import presenters.AddCategory.AddCategoryViewModel;

@WebServlet("/admin/addCategory")
public class AddCategoryControllerWeb extends HttpServlet {
    private AddCategoryUseCase addCategoryUseCase;

    @Override
    public void init() throws ServletException {
        try {
            CategoryGateway gateway = new CategoryDAO();
            this.addCategoryUseCase = new AddCategoryUseCase(gateway);
        } catch (Exception e) {
            throw new ServletException("Lỗi khởi tạo AddCategory", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Show form
        req.getRequestDispatcher("/admin/add-category.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("roleCode"))) {
            resp.sendRedirect("../login.jsp?error=Không có quyền");
            return;
        }

        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String parentIdStr = req.getParameter("parentId");

        if (code == null || name == null) {
            req.setAttribute("error", "Thiếu thông tin bắt buộc");
            req.getRequestDispatcher("/admin/add-category.jsp").forward(req, resp);
            return;
        }

        Integer parentId = null;
        if (parentIdStr != null && !parentIdStr.trim().isEmpty()) {
            parentId = Integer.parseInt(parentIdStr);
        }

        AddCategoryInputData input = new AddCategoryInputData(code, name, description, parentId);
        AddCategoryPresenter presenter = new AddCategoryPresenter();
        addCategoryUseCase.execute(input, presenter);
        AddCategoryViewModel vm = presenter.getViewModel();

        if (vm.success) {
            resp.sendRedirect("categories?success=Thêm danh mục thành công");
        } else {
            req.setAttribute("error", vm.message);
            req.getRequestDispatcher("/admin/add-category.jsp").forward(req, resp);
        }
    }
}