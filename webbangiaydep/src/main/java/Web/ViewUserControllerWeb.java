package Web;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import persistence.User.UserDAO;
import persistence.User.UserDTO;
import persistence.User.UserGateway;

@WebServlet("/admin/viewUser")
public class ViewUserControllerWeb extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("roleCode"))) {
            resp.sendRedirect("../login.jsp?error=Không có quyền");
            return;
        }

        String idStr = req.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            resp.sendRedirect("users?error=ID không hợp lệ");
            return;
        }

        long id = Long.parseLong(idStr);
        try {
            UserGateway gateway = new UserDAO();
            UserDTO user = gateway.getUserById(id);
            if (user == null) {
                resp.sendRedirect("users?error=Người dùng không tồn tại");
                return;
            }
            req.setAttribute("user", user);
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi tải thông tin người dùng");
        }

        req.getRequestDispatcher("/admin/view-user.jsp").forward(req, resp);
    }
}