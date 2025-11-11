package Web;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutControllerWeb extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Clear session
        HttpSession session = req.getSession(false); // Không tạo session mới nếu chưa có
        if (session != null) {
            session.invalidate(); // Xóa toàn bộ session (userId, roleCode, cart, etc.)
        }

        // 2. URL-encode message để tránh lỗi Unicode in Location header
        String successMsg = URLEncoder.encode("Đã đăng xuất thành công", StandardCharsets.UTF_8);

        // 3. Redirect về trang login
        resp.sendRedirect(req.getContextPath() + "/login.jsp?success=" + successMsg);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp); // Hỗ trợ cả GET/POST cho logout
    }
}