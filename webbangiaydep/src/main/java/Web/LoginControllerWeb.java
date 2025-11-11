package Web;

import java.io.IOException;

import business.Login.LoginInputData;
import business.Login.LoginUseCase;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import persistence.User.UserDAO;
import persistence.User.UserGateway;
import presenters.Login.LoginPresenter;
import presenters.Login.LoginViewModel;

@WebServlet("/doLogin")
public class LoginControllerWeb extends HttpServlet {
    private LoginUseCase loginUseCase;

    @Override
    public void init() {
        try {
            UserGateway gateway = new UserDAO();
            this.loginUseCase = new LoginUseCase(gateway);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khởi tạo Login", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || password == null) {
            req.setAttribute("error", "Thiếu thông tin");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        LoginInputData input = new LoginInputData(email.trim(), password);
        LoginPresenter presenter = new LoginPresenter();
        loginUseCase.execute(input, presenter);
        LoginViewModel vm = presenter.getViewModel();

        if (vm.success) {
            HttpSession session = req.getSession();
            session.setAttribute("userId", vm.userId);
            session.setAttribute("roleCode", vm.roleCode);

            if ("ADMIN".equals(vm.roleCode)) {
                resp.sendRedirect("admin/dashboard.jsp");
            } else {
                resp.sendRedirect("index.jsp");
            }
        } else {
            req.setAttribute("error", vm.message);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}