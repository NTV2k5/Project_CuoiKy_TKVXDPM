package Web;

import java.io.IOException;

import business.Register.RegisterInputData;
import business.Register.RegisterUseCase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import persistence.User.UserDAO;
import persistence.User.UserGateway;
import presenters.Register.RegisterPresenter;
import presenters.Register.RegisterViewModel;

@WebServlet("/doRegister")
public class RegisterControllerWeb extends HttpServlet {
    private RegisterUseCase registerUseCase;

    @Override
    public void init() {
        try {
            UserGateway gateway = new UserDAO();
            this.registerUseCase = new RegisterUseCase(gateway);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khởi tạo Register", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String fullName = req.getParameter("fullName");
        String phone = req.getParameter("phone");

        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty() || fullName == null || fullName.trim().isEmpty()) {
            req.setAttribute("error", "Thiếu thông tin bắt buộc");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        RegisterInputData input = new RegisterInputData(email.trim(), password, fullName.trim(), phone != null ? phone.trim() : "", 2); // Default CUSTOMER
        RegisterPresenter presenter = new RegisterPresenter();
        registerUseCase.execute(input, presenter);
        RegisterViewModel vm = presenter.getViewModel();

        if (vm != null && vm.success) { // Null check for vm
            resp.sendRedirect("login.jsp?success=Đăng ký thành công");
        } else {
            req.setAttribute("error", (vm != null ? vm.message : "Lỗi không xác định"));
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}