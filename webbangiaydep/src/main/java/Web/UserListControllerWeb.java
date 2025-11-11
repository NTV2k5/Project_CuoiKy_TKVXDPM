package Web;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import persistence.User.UserDAO;
import persistence.User.UserGateway;

@WebServlet("/admin/users")
public class UserListControllerWeb extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("roleCode"))) {
            resp.sendRedirect("../login.jsp?error=Không có quyền");
            return;
        }

        String action = req.getParameter("action");
        String keyword = req.getParameter("keyword");
        String role = req.getParameter("role");

        try {
            UserGateway gateway = new UserDAO();
            List<persistence.User.UserDTO> users;
            int totalCount;

            if ("search".equals(action) && (keyword != null || role != null)) {
                users = gateway.searchUsers(keyword, role, 100);
                totalCount = gateway.getTotalUserCount(keyword, role);
                req.setAttribute("keyword", keyword);
                req.setAttribute("role", role);
            } else {
                users = gateway.getAllUsers();
                totalCount = users.size();
            }

            req.setAttribute("users", users);
            req.setAttribute("totalCount", totalCount);
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi tải danh sách người dùng");
        }

        req.getRequestDispatcher("/admin/users.jsp").forward(req, resp);
    }
}