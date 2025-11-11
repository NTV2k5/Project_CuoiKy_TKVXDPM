<%-- admin/add-product.jsp (updated with sidebar CSS from dashboard) --%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <!DOCTYPE html>
            <html lang="vi">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Thêm Sản phẩm - Admin</title>
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

                    .stat-card {
                        transition: transform 0.2s;
                    }

                    .stat-card:hover {
                        transform: translateY(-2px);
                    }
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
                        <h1 class="h3 mb-0"><i class="fas fa-plus me-2 text-success"></i>Thêm Sản phẩm Mới</h1>
                        <a href="${pageContext.request.contextPath}/admin/products.jsp" class="btn btn-secondary"><i
                                class="fas fa-arrow-left me-1"></i>Quay lại danh sách</a>
                    </div>

                    <div class="card">
                        <div class="card-header">
                            <h5 class="mb-0"><i class="fas fa-plus me-2"></i>Thông tin sản phẩm</h5>
                        </div>
                        <div class="card-body">
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                    <i class="fas fa-exclamation-triangle me-2"></i>${error}
                                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                </div>
                            </c:if>
                            <c:if test="${not empty success}">
                                <div class="alert alert-success alert-dismissible fade show" role="alert">
                                    <i class="fas fa-check-circle me-2"></i>${success}
                                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                </div>
                            </c:if>

                            <form method="post" action="${pageContext.request.contextPath}/admin/addProduct">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label for="sku" class="form-label">SKU <span
                                                    class="text-danger">*</span></label>
                                            <input type="text" class="form-control" id="sku" name="sku" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="name" class="form-label">Tên sản phẩm <span
                                                    class="text-danger">*</span></label>
                                            <input type="text" class="form-control" id="name" name="name" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="brand" class="form-label">Brand</label>
                                            <input type="text" class="form-control" id="brand" name="brand">
                                        </div>
                                        <div class="mb-3">
                                            <label for="categoryId" class="form-label">Danh mục <span
                                                    class="text-danger">*</span></label>
                                            <select class="form-select" id="categoryId" name="categoryId" required>
                                                <option value="">Chọn danh mục</option>
                                                <c:forEach var="category" items="${categories}">
                                                    <option value="${category.id}">${category.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label for="shortDescription" class="form-label">Mô tả ngắn</label>
                                            <textarea class="form-control" id="shortDescription" name="shortDescription"
                                                rows="3"></textarea>
                                        </div>
                                        <div class="mb-3">
                                            <label for="imageUrl" class="form-label">Hình ảnh URL</label>
                                            <input type="url" class="form-control" id="imageUrl" name="imageUrl">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Trạng thái</label>
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" id="isActive"
                                                    name="isActive" checked>
                                                <label class="form-check-label" for="isActive">Hoạt động</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="description" class="form-label">Mô tả chi tiết</label>
                                    <textarea class="form-control" id="description" name="description"
                                        rows="5"></textarea>
                                </div>
                                <div class="d-flex justify-content-end">
                                    <a href="${pageContext.request.contextPath}/admin/products.jsp"
                                        class="btn btn-secondary me-2">Hủy</a>
                                    <button class="btn btn-success"><i class="fas fa-save me-2"></i>Lưu sản
                                        phẩm</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </main>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            </body>

            </html>