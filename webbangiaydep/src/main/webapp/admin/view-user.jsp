<%-- WebContent/admin/view-user.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết người dùng</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; }
        .sidebar { background-color: #343a40; height: 100vh; position: fixed; width: 250px; }
        .sidebar .nav-link { color: #adb5bd; padding: 1rem 1.5rem; }
        .sidebar .nav-link:hover, .sidebar .nav-link.active { color: #fff; background-color: #495057; }
        .main-content { margin-left: 250px; padding: 2rem; }
        .card { box-shadow: 0 0.125rem 0.25rem rgba(0,0,0,.075); border: none; }
    </style>
</head>
<body>
    <!-- SIDEBAR -->
    <nav class="sidebar d-flex flex-column">
        <div class="p-3 bg-dark text-white">
            <h4 class="mb-0"><i class="fas fa-shoe-prints me-2"></i>Shop Giày Đẹp Admin</h4>
        </div>
        <ul class="nav flex-column flex-grow-1">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/dashboard.jsp">
                    <i class="fas fa-tachometer-alt me-2"></i>Dashboard
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/products.jsp">
                    <i class="fas fa-box me-2"></i>Quản lý Sản phẩm
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/categories.jsp">
                    <i class="fas fa-tags me-2"></i>Quản lý Danh mục
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link active" href="${pageContext.request.contextPath}/admin/users.jsp">
                    <i class="fas fa-users me-2"></i>Quản lý Người dùng
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/orders.jsp">
                    <i class="fas fa-shopping-cart me-2"></i>Quản lý Đơn hàng
                </a>
            </li>
            <li class="nav-item mt-auto">
                <a class="nav-link text-danger" href="${pageContext.request.contextPath}/logout">
                    <i class="fas fa-sign-out-alt me-2"></i>Đăng xuất
                </a>
            </li>
        </ul>
    </nav>

    <main class="main-content">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1 class="h3 mb-0"><i class="fas fa-user me-2 text-info"></i>Chi tiết người dùng</h1>
            <a href="${pageContext.request.contextPath}/admin/users.jsp" class="btn btn-secondary"><i class="fas fa-arrow-left"></i> Quay lại</a>
        </div>

        <div class="card">
            <div class="card-body">
                <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
                <c:if test="${not empty user}">
                    <div class="row">
                        <div class="col-md-6">
                            <table class="table table-borderless">
                                <tr><th>ID:</th><td>${user.id}</td></tr>
                                <tr><th>Username:</th><td><strong>${user.username}</strong></td></tr>
                                <tr><th>Họ tên:</th><td>${user.fullName}</td></tr>
                            </table>
                        </div>
                        <div class="col-md-6">
                            <table class="table table-borderless">
                                <tr><th>Email:</th><td>${user.email}</td></tr>
                                <tr><th>Vai trò:</th><td><span class="badge ${user.roleCode == 'ADMIN' ? 'bg-danger' : 'bg-primary'}">${user.roleCode == 'ADMIN' ? 'Quản trị' : 'Người dùng'}</span></td></tr>
                                <tr><th>Trạng thái:</th><td><span class="badge ${user.active ? 'bg-success' : 'bg-secondary'}">${user.active ? 'Hoạt động' : 'Vô hiệu'}</span></td></tr>
                            </table>
                        </div>
                    </div>
                    <div class="mt-3">
                        <a href="${pageContext.request.contextPath}/admin/edit-user.jsp?id=${user.id}" class="btn btn-warning"><i class="fas fa-edit"></i> Sửa</a>
                        <a href="${pageContext.request.contextPath}/admin/delete-user.jsp?id=${user.id}" class="btn btn-danger"><i class="fas fa-trash"></i> Xóa</a>
                    </div>
                </c:if>
            </div>
        </div>
    </main>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>