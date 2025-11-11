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

@WebServlet("/admin/editUser")
public class UpdateUserControllerWeb extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("roleCode"))) {
            resp.sendRedirect("../login.jsp?error=Không có quyền");
            return;
        }

        String idStr = req.getParameter("id");
        if (idStr == null) {
            resp.sendRedirect("users?error=ID không hợp lệ");
            return;
        }

        long id = Long.parseLong(idStr);
        try {
            UserGateway gateway = new UserDAO();
            UserDTO user = gateway.getUserById(id);
            if (user == null) {
                resp.sendRedirect("users?error=Không tìm thấy người dùng");
                return;
            }
            req.setAttribute("user", user);
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi tải dữ liệu");
        }

        req.getRequestDispatcher("/admin/edit-user.jsp").forward(req, resp);
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
        String fullName = req.getParameter("fullName");
        String phone = req.getParameter("phone");
        String roleIdStr = req.getParameter("roleId");
        String activeStr = req.getParameter("active");

        if (idStr == null || fullName == null) {
            req.setAttribute("error", "Thiếu thông tin");
            req.getRequestDispatcher("/admin/edit-user.jsp").forward(req, resp);
            return;
        }

        long id = Long.parseLong(idStr);
        int roleId = 2;
        if (roleIdStr != null && !roleIdStr.isEmpty()) {
            try {
                roleId = Integer.parseInt(roleIdStr);
            } catch (NumberFormatException e) {
                roleId = 2;
            }
        }
        boolean isActive = "on".equals(activeStr) || "true".equals(activeStr);

        try {
            UserGateway gateway = new UserDAO();
            gateway.updateUser(id, fullName, phone, roleId, isActive);
            resp.sendRedirect("users?success=Cập nhật thành công");
        } catch (Exception e) {
            req.setAttribute("error", "Cập nhật thất bại");
            req.setAttribute("user", new UserDTO(id, "", fullName, phone, roleId, "", isActive));
            req.getRequestDispatcher("/admin/edit-user.jsp").forward(req, resp);
        }
    }
}