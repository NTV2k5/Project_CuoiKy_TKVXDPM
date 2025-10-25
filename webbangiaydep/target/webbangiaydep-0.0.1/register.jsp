<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Đăng ký</title>
</head>
<body>
  <h2>Tạo tài khoản mới</h2>
  <form action="doRegister" method="post">
    <input type="text" name="username" placeholder="Tên đăng nhập"><br>
    <input type="password" name="password" placeholder="Mật khẩu"><br>
    <input type="email" name="email" placeholder="Email"><br>
    <button type="submit">Đăng ký</button>
  </form>
</body>
</html>
