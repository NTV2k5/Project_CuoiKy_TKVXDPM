package Web;

import java.io.IOException;
import java.util.List;

import business.UpdateCategory.UpdateCategoryInputData;
import business.UpdateCategory.UpdateCategoryUseCase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import persistence.Category.CategoryDAO;
import persistence.Category.CategoryDTO;
import persistence.Category.CategoryGateway;
import presenters.UpdateCategory.UpdateCategoryPresenter;
import presenters.UpdateCategory.UpdateCategoryViewModel;

@WebServlet("/admin/editCategory")
public class UpdateCategoryControllerWeb extends HttpServlet {
    private UpdateCategoryUseCase updateCategoryUseCase;

    @Override
    public void init() throws ServletException {
        try {
            CategoryGateway gateway = new CategoryDAO();
            this.updateCategoryUseCase = new UpdateCategoryUseCase(gateway);
        } catch (Exception e) {
            throw new ServletException("Lỗi khởi tạo UpdateCategory", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            resp.sendRedirect("categories?error=ID không hợp lệ");
            return;
        }

        long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            resp.sendRedirect("categories?error=ID không hợp lệ");
            return;
        }

        CategoryGateway gateway = null;
        try {
            gateway = new CategoryDAO();
            CategoryDTO category = gateway.getCategoryById(id);
            if (category == null) {
                resp.sendRedirect("categories?error=Danh mục không tồn tại");
                return;
            }
            req.setAttribute("category", category);

            List<CategoryDTO> categories = gateway.getAllCategories();
            req.setAttribute("categories", categories);

            req.getRequestDispatcher("/admin/edit-category.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Lỗi kết nối DB", e);
        }
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
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String parentIdStr = req.getParameter("parentId");

        if (idStr == null || name == null || name.trim().isEmpty()) {
            req.setAttribute("error", "Thiếu thông tin bắt buộc");
            req.getRequestDispatcher("/admin/edit-category.jsp").forward(req, resp);
            return;
        }

        long id = Long.parseLong(idStr);
        Integer parentId = null;
        if (parentIdStr != null && !parentIdStr.trim().isEmpty()) {
            try {
                parentId = Integer.parseInt(parentIdStr);
            } catch (NumberFormatException e) {
                // ignore
            }
        }

        UpdateCategoryInputData input = new UpdateCategoryInputData(id, code, name, description, parentId);
        UpdateCategoryPresenter presenter = new UpdateCategoryPresenter();
        updateCategoryUseCase.execute(input, presenter);
        UpdateCategoryViewModel vm = presenter.getViewModel();

        if (vm.success) {
            resp.sendRedirect("categories?success=Cập nhật thành công");
        } else {
            req.setAttribute("error", vm.message);
            req.setAttribute("category", input); // Gợi ý: giữ dữ liệu
            req.getRequestDispatcher("/admin/edit-category.jsp").forward(req, resp);
        }
    }
}