<%-- WebContent/admin/edit-order.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Cập nhật Đơn Hàng #${order.id}</title>
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
            <h1 class="h3 mb-0">Cập nhật Đơn Hàng #${order.id}</h1>
            <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-secondary">Quay lại</a>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header bg-warning text-dark">
                        <h5 class="mb-0">Thông tin khách hàng</h5>
                    </div>
                    <div class="card-body">
                        <p><strong>Email:</strong> ${order.userEmail}</p>
                        <p><strong>Họ tên:</strong> ${order.fullName}</p>
                        <p><strong>SĐT:</strong> ${order.phone}</p>
                        <p><strong>Địa chỉ:</strong> ${order.address}</p>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header bg-info text-white">
                        <h5 class="mb-0">Cập nhật trạng thái</h5>
                    </div>
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/admin/update-order-status" method="post">
                            <input type="hidden" name="id" value="${order.id}">
                            <div class="mb-3">
                                <label class="form-label">Trạng thái hiện tại</label>
                                <div class="badge bg-primary fs-6">${order.status}</div>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Chọn trạng thái mới</label>
                                <select name="status" class="form-select" required>
                                    <option value="PENDING" ${order.status == 'PENDING' ? 'selected' : ''}>Chờ xử lý</option>
                                    <option value="CONFIRMED" ${order.status == 'CONFIRMED' ? 'selected' : ''}>Đã xác nhận</option>
                                    <option value="SHIPPED" ${order.status == 'SHIPPED' ? 'selected' : ''}>Đang giao</option>
                                    <option value="DELIVERED" ${order.status == 'DELIVERED' ? 'selected' : ''}>Đã giao</option>
                                    <option value="CANCELLED" ${order.status == 'CANCELLED' ? 'selected' : ''}>Đã hủy</option>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-warning">Cập nhật</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="card mt-4">
            <div class="card-header bg-secondary text-white">
                <h5 class="mb-0">Chi tiết sản phẩm</h5>
            </div>
            <div class="card-body">
                <table class="table table-sm">
                    <thead>
                        <tr>
                            <th>Sản phẩm</th>
                            <th>Phân loại</th>
                            <th>SL</th>
                            <th>Giá</th>
                            <th>Thành tiền</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${orderItems}">
                            <tr>
                                <td>${item.productName}</td>
                                <td>${item.variantInfo}</td>
                                <td>${item.quantity}</td>
                                <td><fmt:formatNumber value="${item.price}" type="currency" currencySymbol="₫"/></td>
                                <td><fmt:formatNumber value="${item.subtotal}" type="currency" currencySymbol="₫"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr class="table-primary">
                            <th colspan="4" class="text-end">Tổng cộng:</th>
                            <th><fmt:formatNumber value="${order.total}" type="currency" currencySymbol="₫"/></th>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </main>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>