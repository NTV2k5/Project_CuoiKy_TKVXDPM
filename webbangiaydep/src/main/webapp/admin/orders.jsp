<%-- admin/orders.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý Đơn hàng - Admin</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
        }
        .sidebar {
            background-color: #343a40;
            height: 100vh;
            position: fixed;
            width: 250px;
        }
        .sidebar .nav-link {
            color: #adb5bd;
            padding: 1rem 1.5rem;
        }
        .sidebar .nav-link:hover,
        .sidebar .nav-link.active {
            color: #fff;
            background-color: #495057;
        }
        .main-content {
            margin-left: 250px;
            padding: 2rem;
        }
        .card {
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075);
            border: none;
        }
        .card-header {
            background-color: #007bff;
            color: white;
        }
        .status-pending { background-color: #ffc107; color: #000; }
        .status-paid { background-color: #0dcaf0; }
        .status-processing { background-color: #0d6efd; }
        .status-shipped { background-color: #198754; color: white; }
        .status-completed { background-color: #198754; color: white; }
        .status-cancelled { background-color: #dc3545; color: white; }
    </style>
</head>
<body>

<!-- SIDEBAR – GIỐNG HỆT PRODUCTS -->
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
            <a class="nav-link active" href="${pageContext.request.contextPath}/admin/orders.jsp">
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
        <h1 class="h3 mb-0"><i class="fas fa-shopping-cart me-2 text-primary"></i>Quản lý Đơn hàng</h1>
        <span class="badge bg-success fs-6">Tổng: ${totalCount} đơn</span>
    </div>

    <!-- SEARCH FORM – GIỐNG PRODUCTS -->
    <div class="card mb-4">
        <div class="card-body">
            <form method="get" action="${pageContext.request.contextPath}/admin/search-order.jsp">
                <div class="row g-3">
                    <div class="col-md-5">
                        <input type="text" class="form-control" name="keyword" placeholder="Tìm mã đơn, email, tên..." value="${keyword}">
                    </div>
                    <div class="col-md-3">
                        <select class="form-select" name="status">
                            <option value="">Tất cả trạng thái</option>
                            <option value="PENDING" ${status == 'PENDING' ? 'selected' : ''}>Chờ xử lý</option>
                            <option value="PAID" ${status == 'PAID' ? 'selected' : ''}>Đã thanh toán</option>
                            <option value="PROCESSING" ${status == 'PROCESSING' ? 'selected' : ''}>Đang xử lý</option>
                            <option value="SHIPPED" ${status == 'SHIPPED' ? 'selected' : ''}>Đã giao</option>
                            <option value="COMPLETED" ${status == 'COMPLETED' ? 'selected' : ''}>Hoàn thành</option>
                            <option value="CANCELLED" ${status == 'CANCELLED' ? 'selected' : ''}>Đã hủy</option>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <button class="btn btn-primary me-2"><i class="fas fa-search me-1"></i>Tìm kiếm</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <!-- ORDERS TABLE -->
    <div class="card">
        <div class="card-header">
            <h5 class="mb-0">Danh sách đơn hàng</h5>
        </div>
        <div class="card-body">
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="alert alert-success">${success}</div>
            </c:if>

            <div class="table-responsive">
                <table class="table table-striped align-middle">
                    <thead>
                        <tr>
                            <th>Mã đơn</th>
                            <th>Khách hàng</th>
                            <th>Ngày đặt</th>
                            <th>Sản phẩm</th>
                            <th>Tổng tiền</th>
                            <th>Thanh toán</th>
                            <th>Trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="o" items="${orders}">
                            <tr>
                                <td><strong>#${o.id}</strong></td>
                                <td>
                                    <div><strong>${o.fullName}</strong></div>
                                    <small class="text-muted">${o.userEmail}</small><br>
                                    <small>${o.phone}</small>
                                </td>
                                <td><fmt:formatDate value="${o.orderDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                <td>${o.itemCount} sản phẩm</td>
                                <td><strong><fmt:formatNumber value="${o.total}" type="currency" currencySymbol="₫"/></strong></td>
                                <td><span class="badge bg-info">${o.paymentMethod}</span></td>
                                <td>
                                    <span class="badge status-${o.status.toLowerCase()}">
                                        <c:choose>
                                            <c:when test="${o.status == 'PENDING'}">Chờ xử lý</c:when>
                                            <c:when test="${o.status == 'PAID'}">Đã thanh toán</c:when>
                                            <c:when test="${o.status == 'PROCESSING'}">Đang xử lý</c:when>
                                            <c:when test="${o.status == 'SHIPPED'}">Đã giao</c:when>
                                            <c:when test="${o.status == 'COMPLETED'}">Hoàn thành</c:when>
                                            <c:when test="${o.status == 'CANCELLED'}">Đã hủy</c:when>
                                        </c:choose>
                                    </span>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/order-detail.jsp?id=${o.id}"
                                       class="btn btn-sm btn-primary"><i class="fas fa-eye"></i></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <c:if test="${empty orders}">
                <p class="text-center text-muted">Không có đơn hàng nào.</p>
            </c:if>
        </div>
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>