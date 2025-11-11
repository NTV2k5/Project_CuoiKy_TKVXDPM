package Web;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import persistence.User.UserDAO;
import persistence.User.UserGateway;

@WebServlet("/admin/addUser")
public class AddUserControllerWeb extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("roleCode"))) {
            resp.sendRedirect("../login.jsp?error=Không có quyền");
            return;
        }
        req.getRequestDispatcher("/admin/add-user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("roleCode"))) {
            resp.sendRedirect("../login.jsp?error=Không có quyền");
            return;
        }

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String fullName = req.getParameter("fullName");
        String phone = req.getParameter("phone");
        String roleIdStr = req.getParameter("roleId");

        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            req.setAttribute("error", "Email và mật khẩu là bắt buộc");
            req.getRequestDispatcher("/admin/add-user.jsp").forward(req, resp);
            return;
        }

        int roleId = 2; // Mặc định CUSTOMER
        if (roleIdStr != null && !roleIdStr.isEmpty()) {
            try {
                roleId = Integer.parseInt(roleIdStr);
            } catch (NumberFormatException e) {
                roleId = 2;
            }
        }

        try {
            UserGateway gateway = new UserDAO();
            if (gateway.emailExists(email)) {
                req.setAttribute("error", "Email đã tồn tại");
                req.getRequestDispatcher("/admin/add-user.jsp").forward(req, resp);
                return;
            }

            long id = gateway.createUser(email, password, roleId, fullName, phone);
            if (id > 0) {
                resp.sendRedirect("users?success=Thêm người dùng thành công");
            } else {
                req.setAttribute("error", "Thêm người dùng thất bại");
                req.getRequestDispatcher("/admin/add-user.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi hệ thống");
            req.getRequestDispatcher("/admin/add-user.jsp").forward(req, resp);
        }
    }
}