<%-- views/admin/dashboard.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Shop Giày Đẹp</title>
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
        .stat-card { transition: transform 0.2s; }
        .stat-card:hover { transform: translateY(-2px); }
    </style>
</head>
<body>
    <!-- Sidebar -->
    <nav class="sidebar d-flex flex-column">
        <div class="p-3 bg-dark text-white">
            <h4 class="mb-0"><i class="fas fa-shoe-prints me-2"></i>Shop Giày Đẹp Admin</h4>
        </div>
        <ul class="nav flex-column flex-grow-1">
            <li class="nav-item">
                <a class="nav-link active" href="${pageContext.request.contextPath}/admin/dashboard.jsp">
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

    <!-- Main Content -->
    <main class="main-content">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1 class="h3 mb-0"><i class="fas fa-tachometer-alt me-2 text-primary"></i>Dashboard</h1>
            <span class="badge bg-success fs-6">Chào mừng Admin! <c:out value="${sessionScope.fullName}" default="Hệ Thống" /></span>
        </div>

        <!-- Stats Cards -->
        <div class="row mb-4">
            <div class="col-md-3 mb-3">
                <div class="card stat-card">
                    <div class="card-body text-center">
                        <i class="fas fa-users fa-3x text-primary mb-2"></i>
                        <h5 class="card-title">Tổng Người dùng</h5>
                        <h2 class="text-primary">150</h2> <!-- Demo number, thay bằng query DB sau -->
                        <p class="text-muted small">+12% so với tháng trước</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3 mb-3">
                <div class="card stat-card">
                    <div class="card-body text-center">
                        <i class="fas fa-box fa-3x text-success mb-2"></i>
                        <h5 class="card-title">Tổng Sản phẩm</h5>
                        <h2 class="text-success">500</h2>
                        <p class="text-muted small">+5% mới thêm</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3 mb-3">
                <div class="card stat-card">
                    <div class="card-body text-center">
                        <i class="fas fa-shopping-cart fa-3x text-info mb-2"></i>
                        <h5 class="card-title">Tổng Đơn hàng</h5>
                        <h2 class="text-info">250</h2>
                        <p class="text-muted small">+20% doanh thu</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3 mb-3">
                <div class="card stat-card">
                    <div class="card-body text-center">
                        <i class="fas fa-chart-line fa-3x text-warning mb-2"></i>
                        <h5 class="card-title">Doanh thu</h5>
                        <h2 class="text-warning">2.5 tỷ ₫</h2>
                        <p class="text-muted small">Tháng này</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Quick Actions -->
        <div class="row">
            <div class="col-md-6 mb-3">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0"><i class="fas fa-tasks me-2"></i>Hành động nhanh</h5>
                    </div>
                    <div class="card-body">
                        <div class="row g-3">
                            <div class="col-6">
                                <a href="${pageContext.request.contextPath}/admin/products/add" class="btn btn-outline-primary w-100">
                                    <i class="fas fa-plus me-1"></i>Thêm Sản phẩm
                                </a>
                            </div>
                            <div class="col-6">
                                <a href="${pageContext.request.contextPath}/admin/users/add" class="btn btn-outline-success w-100">
                                    <i class="fas fa-user-plus me-1"></i>Thêm User
                                </a>
                            </div>
                            <div class="col-6">
                                <a href="${pageContext.request.contextPath}/admin/orders/pending" class="btn btn-outline-info w-100">
                                    <i class="fas fa-clock me-1"></i>Xem Đơn Pending
                                </a>
                            </div>
                            <div class="col-6">
                                <a href="${pageContext.request.contextPath}/admin/reports" class="btn btn-outline-warning w-100">
                                    <i class="fas fa-chart-bar me-1"></i>Báo cáo
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6 mb-3">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0"><i class="fas fa-bell me-2"></i>Thông báo mới</h5>
                    </div>
                    <div class="card-body">
                        <ul class="list-unstyled">
                            <li class="mb-2"><small><i class="fas fa-check text-success me-1"></i>Đơn hàng #123 đã hoàn thành</small></li>
                            <li class="mb-2"><small><i class="fas fa-exclamation-triangle text-warning me-1"></i>Stock sản phẩm SKU001 thấp</small></li>
                            <li class="mb-2"><small><i class="fas fa-user-plus text-info me-1"></i>User mới đăng ký: user11@example.com</small></li>
                            <li><small><i class="fas fa-clock text-secondary me-1"></i>Đơn #456 đang xử lý...</small></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <!-- Recent Orders Table (demo) -->
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0"><i class="fas fa-list me-2"></i>Đơn hàng gần đây</h5>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead class="table-light">
                                    <tr>
                                        <th>ID</th>
                                        <th>Khách hàng</th>
                                        <th>Tổng tiền</th>
                                        <th>Trạng thái</th>
                                        <th>Ngày</th>
                                        <th>Hành động</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>#001</td>
                                        <td>user1@example.com</td>
                                        <td>2.590.000 ₫</td>
                                        <td><span class="badge bg-success">Hoàn thành</span></td>
                                        <td>08/11/2025</td>
                                        <td><button class="btn btn-sm btn-outline-primary">Xem</button></td>
                                    </tr>
                                    <tr>
                                        <td>#002</td>
                                        <td>user2@example.com</td>
                                        <td>4.280.000 ₫</td>
                                        <td><span class="badge bg-warning">Đang xử lý</span></td>
                                        <td>07/11/2025</td>
                                        <td><button class="btn btn-sm btn-outline-primary">Xem</button></td>
                                    </tr>
                                    <!-- Thêm rows demo nếu cần -->
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>