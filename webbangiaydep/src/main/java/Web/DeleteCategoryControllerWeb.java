package Web;

import java.io.IOException;

import business.DeleteCategory.DeleteCategoryInputData;
import business.DeleteCategory.DeleteCategoryUseCase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import persistence.Category.CategoryDAO;
import persistence.Category.CategoryGateway;
import presenters.DeleteCategory.DeleteCategoryPresenter;
import presenters.DeleteCategory.DeleteCategoryViewModel;

@WebServlet("/admin/deleteCategory")
public class DeleteCategoryControllerWeb extends HttpServlet {
    private DeleteCategoryUseCase deleteCategoryUseCase;

    @Override
    public void init() throws ServletException {
        try {
            CategoryGateway gateway = new CategoryDAO();
            this.deleteCategoryUseCase = new DeleteCategoryUseCase(gateway);
        } catch (Exception e) {
            throw new ServletException("Lỗi khởi tạo DeleteCategory", e);
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

        if (idStr == null) {
            resp.sendRedirect("categories?error=ID không hợp lệ");
            return;
        }

        long id = Long.parseLong(idStr);

        DeleteCategoryInputData input = new DeleteCategoryInputData(id);
        DeleteCategoryPresenter presenter = new DeleteCategoryPresenter();
        deleteCategoryUseCase.execute(input, presenter);
        DeleteCategoryViewModel vm = presenter.getViewModel();

        if (vm.success) {
            resp.sendRedirect("categories?success=Xóa danh mục thành công");
        } else {
            resp.sendRedirect("categories?error=" + vm.message);
        }
    }
}