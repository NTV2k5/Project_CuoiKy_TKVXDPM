<%-- admin/search-product.jsp (dedicated search results page) --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kết quả tìm kiếm - Admin</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; }
        .sidebar { background-color: #343a40; height: 100vh; position: fixed; width: 250px; }
        .sidebar .nav-link { color: #adb5bd; padding: 1rem 1.5rem; }
        .sidebar .nav-link:hover, .sidebar .nav-link.active { color: #fff; background-color: #495057; }
        .main-content { margin-left: 250px; padding: 2rem; }
        .card { box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075); border: none; }
        .card-header { background-color: #007bff; color: white; }
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
            <h1 class="h3 mb-0"><i class="fas fa-search me-2 text-info"></i>Kết quả tìm kiếm</h1>
            <a href="${pageContext.request.contextPath}/admin/products.jsp" class="btn btn-secondary"><i class="fas fa-arrow-left me-1"></i>Quay lại</a>
        </div>

        <div class="card">
            <div class="card-header d-flex justify-content-between">
                <h5 class="mb-0">Kết quả cho: "${keyword}" <span class="badge bg-info">${totalCount} sản phẩm</span></h5>
                <a href="${pageContext.request.contextPath}/admin/add-product.jsp" class="btn btn-success"><i class="fas fa-plus me-1"></i>Thêm mới</a>
            </div>
            <div class="card-body">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>SKU</th>
                                <th>Tên</th>
                                <th>Brand</th>
                                <th>Danh mục</th>
                                <th>Giá</th>
                                <th>Trạng thái</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="product" items="${products}">
                                <tr>
                                    <td>${product.id}</td>
                                    <td>${product.sku}</td>
                                    <td>${product.name}</td>
                                    <td>${product.brand}</td>
                                    <td>${product.categoryName}</td>
                                    <td>${product.price} ₫</td>
                                    <td><span class="badge ${product.isActive ? 'bg-success' : 'bg-secondary'}">${product.isActive ? 'Hoạt động' : 'Ẩn'}</span></td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/admin/edit-product.jsp?id=${product.id}" class="btn btn-sm btn-warning"><i class="fas fa-edit"></i></a>
                                        <form method="post" action="${pageContext.request.contextPath}/admin/deleteProduct" style="display:inline;">
                                            <input type="hidden" name="id" value="${product.id}">
                                            <button class="btn btn-sm btn-danger" onclick="return confirm('Xóa?')"><i class="fas fa-trash"></i></button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <c:if test="${empty products}">
                    <p class="text-center text-muted">Không tìm thấy sản phẩm nào phù hợp với "${keyword}".</p>
                </c:if>
            </div>
        </div>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>