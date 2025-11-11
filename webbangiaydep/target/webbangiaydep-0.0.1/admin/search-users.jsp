<!-- WebContent/admin/search-users.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kết quả tìm kiếm người dùng - Admin</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; }
        .sidebar { background-color: #343a40; height: 100vh; position: fixed; width: 250px; }
        .sidebar .nav-link { color: #adb5bd; padding: 1rem 1.5rem; }
        .sidebar .nav-link:hover, .sidebar .nav-link.active { color: #fff; background-color: #495057; }
        .main-content { margin-left: 250px; padding: 2rem; }
        .card { box-shadow: 0 0.125rem 0.25rem rgba(0,0,0,.075); border: none; }
        .card-header { background-color: #007bff; color: white; }
    </style>
</head>
<body>

<!-- SIDEBAR – GIỐNG HỆT SEARCH-PRODUCT -->
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
    <!-- HEADER -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h3 mb-0"><i class="fas fa-search me-2 text-info"></i>Kết quả tìm kiếm</h1>
        <a href="${pageContext.request.contextPath}/admin/users.jsp" class="btn btn-secondary"><i class="fas fa-arrow-left me-1"></i>Quay lại</a>
    </div>

    <!-- CARD -->
    <div class="card">
        <div class="card-header d-flex justify-content-between align-items-center">
            <h5 class="mb-0">
                Kết quả cho: <strong>"${keyword}"</strong>
                <span class="badge bg-info ms-2">${totalCount} người dùng</span>
            </h5>
            <a href="${pageContext.request.contextPath}/admin/add-user.jsp" class="btn btn-success"><i class="fas fa-plus me-1"></i>Thêm mới</a>
        </div>
        <div class="card-body">
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="alert alert-success">${success}</div>
            </c:if>

            <!-- BẢNG NGƯỜI DÙNG -->
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Họ tên</th>
                            <th>Email</th>
                            <th>SĐT</th>
                            <th>Vai trò</th>
                            <th>Trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${users}">
                            <tr>
                                <td>${user.id}</td>
                                <td>${user.fullName}</td>
                                <td>${user.email}</td>
                                <td>${user.phone}</td>
                                <td>
                                    <span class="badge ${user.role == 'ADMIN' ? 'bg-danger' : 'bg-primary'}">
                                        ${user.role == 'ADMIN' ? 'Quản trị' : 'Người dùng'}
                                    </span>
                                </td>
                                <td>
                                    <span class="badge ${user.isActive ? 'bg-success' : 'bg-secondary'}">
                                        ${user.isActive ? 'Hoạt động' : 'Ẩn'}
                                    </span>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/edit-user.jsp?id=${user.id}"
                                       class="btn btn-sm btn-warning"><i class="fas fa-edit"></i></a>
                                    <form method="post" action="${pageContext.request.contextPath}/admin/deleteUser"
                                          style="display:inline;">
                                        <input type="hidden" name="id" value="${user.id}">
                                        <button class="btn btn-sm btn-danger"
                                                onclick="return confirm('Xóa người dùng này?')"><i class="fas fa-trash"></i></button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <c:if test="${empty users}">
                <p class="text-center text-muted">Không tìm thấy người dùng nào phù hợp với "${keyword}".</p>
            </c:if>
        </div>
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>