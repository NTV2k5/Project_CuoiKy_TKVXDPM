<%-- admin/delete-product.jsp (confirmation page for delete) --%>
<%@ page contentType="text/html;charset=UTF-8" language="language" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xóa Sản phẩm - Admin</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; }
        .sidebar { background-color: #343a40; height: 100vh; position: fixed; width: 250px; }
        .sidebar .nav-link { color: #adb5bd; padding: 1rem 1.5rem; }
        .sidebar .nav-link:hover, .sidebar .nav-link.active { color: #fff; background-color: #495057; }
        .main-content { margin-left: 250px; padding: 2rem; }
        .card { box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075); border: none; }
        .card-header { background-color: #dc3545; color: white; }
    </style>
</head>
<body>
    <!-- Sidebar (same as dashboard) -->
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
                <a class="nav-link active" href="${pageContext.request.contextPath}/admin/products.jsp">
                    <i class="fas fa-box me-2"></i>Quản lý Sản phẩm
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/categories.jsp">
                    <i class="fas fa-tags me-2"></i>Quản lý Danh mục
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/users.jsp">
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
            <h1 class="h3 mb-0"><i class="fas fa-trash me-2 text-danger"></i>Xác nhận xóa sản phẩm</h1>
            <a href="${pageContext.request.contextPath}/admin/products.jsp" class="btn btn-secondary"><i class="fas fa-arrow-left me-1"></i>Quay lại danh sách</a>
        </div>

        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Xóa sản phẩm: ${product.name}</h5>
            </div>
            <div class="card-body">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                <div class="alert alert-warning">
                    <i class="fas fa-warning me-2"></i>Bạn có chắc chắn muốn xóa sản phẩm này? Hành động này không thể hoàn tác.
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <dl class="row">
                            <dt class="col-sm-4">ID:</dt>
                            <dd class="col-sm-8">${product.id}</dd>
                            <dt class="col-sm-4">SKU:</dt>
                            <dd class="col-sm-8">${product.sku}</dd>
                            <dt class="col-sm-4">Tên:</dt>
                            <dd class="col-sm-8">${product.name}</dd>
                            <dt class="col-sm-4">Brand:</dt>
                            <dd class="col-sm-8">${product.brand}</dd>
                        </dl>
                    </div>
                    <div class="col-md-6">
                        <dl class="row">
                            <dt class="col-sm-4">Danh mục:</dt>
                            <dd class="col-sm-8">${product.categoryName}</dd>
                            <dt class="col-sm-4">Giá:</dt>
                            <dd class="col-sm-8">${product.price} ₫</dd>
                            <dt class="col-sm-4">Trạng thái:</dt>
                            <dd class="col-sm-8"><span class="badge ${product.isActive ? 'bg-success' : 'bg-secondary'}">${product.isActive ? 'Hoạt động' : 'Ẩn'}</span></dd>
                        </dl>
                    </div>
                </div>

                <form method="post" action="${pageContext.request.contextPath}/admin/deleteProduct">
                    <input type="hidden" name="id" value="${product.id}">
                    <div class="d-flex justify-content-end">
                        <a href="${pageContext.request.contextPath}/admin/products.jsp" class="btn btn-secondary me-2">Hủy</a>
                        <button class="btn btn-danger"><i class="fas fa-trash me-2"></i>Xóa sản phẩm</button>
                    </div>
                </form>
            </div>
        </div>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>