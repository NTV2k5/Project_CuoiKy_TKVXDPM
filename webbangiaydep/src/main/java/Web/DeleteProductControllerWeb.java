package Web;

import java.io.IOException;

import business.DeleteProduct.DeleteProductInputData;
import business.DeleteProduct.DeleteProductUseCase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import persistence.Product.ProductDAO;
import persistence.Product.ProductGateway;
import presenters.DeleteProduct.DeleteProductPresenter;
import presenters.DeleteProduct.DeleteProductViewModel;

@WebServlet("/admin/deleteProduct")
public class DeleteProductControllerWeb extends HttpServlet {
    private DeleteProductUseCase deleteProductUseCase;

    @Override
    public void init() throws ServletException {
        try {
            ProductGateway gateway = new ProductDAO();
            this.deleteProductUseCase = new DeleteProductUseCase(gateway);
        } catch (Exception e) {
            throw new ServletException("Lỗi khởi tạo DeleteProduct", e);
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
            resp.sendRedirect("products?error=ID không hợp lệ");
            return;
        }

        long id = Long.parseLong(idStr);

        DeleteProductInputData input = new DeleteProductInputData(id);
        DeleteProductPresenter presenter = new DeleteProductPresenter();
        deleteProductUseCase.execute(input, presenter);
        DeleteProductViewModel vm = presenter.getViewModel();

        if (vm.success) {
            resp.sendRedirect("products?success=Xóa thành công");
        } else {
            resp.sendRedirect("products?error=" + vm.message);
        }
    }
}