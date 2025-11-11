package Web;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import persistence.User.UserDAO;
import persistence.User.UserDTO;
import persistence.User.UserGateway;

@WebServlet("/admin/deleteUser")
public class DeleteUserControllerWeb extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                resp.sendRedirect("users?error=Người dùng không tồn tại");
                return;
            }
            req.setAttribute("user", user);
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi tải thông tin");
        }
        req.getRequestDispatcher("/admin/delete-user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null) {
            resp.sendRedirect("users?error=ID không hợp lệ");
            return;
        }
        long id = Long.parseLong(idStr);
        try {
            UserGateway gateway = new UserDAO();
            gateway.deleteUser(id);
            resp.sendRedirect("users?success=Xóa người dùng thành công");
        } catch (Exception e) {
            resp.sendRedirect("users?error=Xóa thất bại");
        }
    }
}