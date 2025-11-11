<%-- WebContent/admin/search-order.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Kết quả tìm kiếm đơn hàng</title>
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
        <div class="p-3 bg-dark text-white"><h4 class="mb-0">Shop Giày Đẹp Admin</h4></div>
        <ul class="nav flex-column flex-grow-1">
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/dashboard.jsp">Dashboard</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/products.jsp">Quản lý Sản phẩm</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/categories.jsp">Quản lý Danh mục</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/users.jsp">Quản lý Người dùng</a></li>
            <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/admin/orders.jsp">Quản lý Đơn hàng</a></li>
            <li class="nav-item mt-auto"><a class="nav-link text-danger" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
        </ul>
    </nav>

    <main class="main-content">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1 class="h3 mb-0">Kết quả tìm kiếm đơn hàng</h1>
            <a href="${pageContext.request.contextPath}/admin/orders.jsp" class="btn btn-secondary">Quay lại</a>
        </div>

        <div class="card">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0">
                    Tìm kiếm: <strong>"${keyword}"</strong> | 
                    Trạng thái: <strong>${status == '' ? 'Tất cả' : status}</strong>
                    <span class="badge bg-light text-dark ms-2">${totalCount}</span>
                </h5>
            </div>
            <div class="card-body">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>
                <c:import url="/admin/order-table.jsp">
                    <c:param name="orders" value="${orders}" />
                </c:import>
            </div>
        </div>
    </main>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>