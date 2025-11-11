<%-- admin/users.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý Người dùng - Admin</title>
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
        .badge-admin { background-color: #dc3545; }
        .badge-user { background-color: #0d6efd; }
        .badge-active { background-color: #198754; }
        .badge-inactive { background-color: #6c757d; }
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
            <h1 class="h3 mb-0"><i class="fas fa-users me-2 text-primary"></i>Quản lý Người dùng</h1>
            <span class="badge bg-success fs-6">Tổng: ${totalCount} người dùng</span>
        </div>

        <!-- Search Form -->
        <div class="card mb-4">
            <div class="card-body">
                <form method="get" action="${pageContext.request.contextPath}/admin/search-users.jsp">
                    <input type="hidden" name="action" value="search">
                    <div class="row g-3">
                        <div class="col-md-5">
                            <input type="text" class="form-control" name="keyword" placeholder="Tìm email, họ tên, số điện thoại..." value="${param.keyword}">
                        </div>
                        <div class="col-md-3">
                            <select class="form-select" name="role">
                                <option value="">Tất cả vai trò</option>
                                <option value="ADMIN" ${param.role == 'ADMIN' ? 'selected' : ''}>Quản trị viên</option>
                                <option value="CUSTOMER" ${param.role == 'CUSTOMER' ? 'selected' : ''}>Khách hàng</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <button class="btn btn-primary me-2"><i class="fas fa-search"></i> Tìm kiếm</button>
                            <a href="${pageContext.request.contextPath}/admin/add-user.jsp" class="btn btn-success"><i class="fas fa-plus"></i> Thêm mới</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Users Table -->
        <div class="card">
            <div class="card-body">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show">
                        ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                <c:if test="${not empty param.success}">
                    <div class="alert alert-success alert-dismissible fade show">
                        ${param.success}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-light">
                        <tr>
                            <th>ID</th>
                            <th>Email</th>
                            <th>Họ tên</th>
                            <th>Số điện thoại</th>
                            <th>Vai trò</th>
                            <th>Trạng thái</th>
                            <th class="text-center">Hành động</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="u" items="${users}">
                            <tr>
                                <td>${u.id}</td>
                                <td><strong>${u.email}</strong></td>
                                <td>${u.fullName}</td>
                                <td>${u.phone}</td>
                                <td>
                                    <span class="badge ${u.roleCode == 'ADMIN' ? 'bg-danger' : 'bg-primary'}">
                                        ${u.roleCode}
                                    </span>
                                </td>
                                <td>
    <span class="badge ${u.isActive() ? 'bg-success' : 'bg-secondary'}">
        ${u.isActive() ? 'Hoạt động' : 'Vô hiệu'}
    </span>
</td>
                                <td class="text-center">
                                    <a href="${pageContext.request.contextPath}/admin/edit-user.jsp?id=${u.id}" 
                                       class="btn btn-sm btn-warning" title="Sửa">
                                        <i class="fas fa-edit"></i>
                                    </a>
                                    <form action="${pageContext.request.contextPath}/admin/delete-user.jsp" method="post" style="display:inline;" 
                                          onsubmit="return confirm('Xóa người dùng này?')">
                                        <input type="hidden" name="id" value="${u.id}">
                                        <button type="submit" class="btn btn-sm btn-danger" title="Xóa">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <c:if test="${empty users}">
                    <div class="text-center text-muted py-4">
                        <i class="fas fa-users-slash fa-3x mb-3"></i>
                        <p>Không tìm thấy người dùng nào.</p>
                    </div>
                </c:if>

                <div class="mt-3">
                    <small class="text-muted">Tổng cộng: <strong>${totalCount}</strong> người dùng</small>
                </div>
            </div>
        </div>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>