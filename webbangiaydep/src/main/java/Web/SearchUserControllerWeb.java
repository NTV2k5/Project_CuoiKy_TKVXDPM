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
import persistence.User.UserDTO;
import persistence.User.UserGateway;

@WebServlet("/admin/searchUsers")
public class SearchUserControllerWeb extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("roleCode"))) {
            resp.sendRedirect("../login.jsp?error=Không có quyền");
            return;
        }

        String keyword = req.getParameter("keyword");
        String role = req.getParameter("role");

        try {
            UserGateway gateway = new UserDAO();
            List<UserDTO> users = gateway.searchUsers(keyword, role, 50);
            int total = gateway.getTotalUserCount(keyword, role);
            req.setAttribute("users", users);
            req.setAttribute("totalCount", total);
            req.setAttribute("keyword", keyword);
            req.setAttribute("role", role);
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi tìm kiếm người dùng");
        }

        req.getRequestDispatcher("/admin/search-users.jsp").forward(req, resp);
    }
}