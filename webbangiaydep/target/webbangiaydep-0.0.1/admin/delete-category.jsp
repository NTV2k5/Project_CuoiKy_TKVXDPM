<%-- admin/delete-category.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xóa Danh mục - Admin</title>
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
    <!-- Sidebar - Giống hệt dashboard -->
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
                <a class="nav-link active" href="${pageContext.request.contextPath}/admin/categories.jsp">
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
            <h1 class="h3 mb-0"><i class="fas fa-exclamation-triangle me-2 text-danger"></i>Xác nhận xóa danh mục</h1>
            <a href="${pageContext.request.contextPath}/admin/categories.jsp" class="btn btn-secondary">
                <i class="fas fa-arrow-left me-1"></i>Quay lại
            </a>
        </div>

        <div class="card">
            <div class="card-header">
                <h5 class="mb-0"><i class="fas fa-trash-alt me-2"></i>Xóa danh mục: <strong>${category.name}</strong></h5>
            </div>
            <div class="card-body">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger"><i class="fas fa-times-circle"></i> ${error}</div>
                </c:if>

                <div class="alert alert-warning">
                    <i class="fas fa-exclamation-triangle me-2"></i>
                    Bạn có chắc chắn muốn xóa danh mục này? 
                    <strong>Hành động này không thể hoàn tác</strong> và sẽ ảnh hưởng đến các sản phẩm liên quan.
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <table class="table table-sm table-borderless">
                            <tr><th>ID:</th><td>${category.id}</td></tr>
                            <tr><th>Code:</th><td><code>${category.code}</code></td></tr>
                            <tr><th>Tên:</th><td><strong>${category.name}</strong></td></tr>
                        </table>
                    </div>
                    <div class="col-md-6">
                        <table class="table table-sm table-borderless">
                            <tr><th>Mô tả:</th><td>${category.description}</td></tr>
                            <tr><th>Danh mục cha:</th>
                                <td>
                                    <c:choose>
                                        <c:when test="${category.parentId != null}">
                                            <c:forEach var="parent" items="${allCategories}">
                                                <c:if test="${parent.id == category.parentId}">${parent.name}</c:if>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise><em class="text-muted">Cấp cha</em></c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>

                <hr>

                <form method="post" action="${pageContext.request.contextPath}/admin/deleteCategory"
                      onsubmit="return confirm('Xác nhận xóa danh mục này?')">
                    <input type="hidden" name="id" value="${category.id}">
                    <div class="d-flex justify-content-end gap-2">
                        <a href="${pageContext.request.contextPath}/admin/categories.jsp" class="btn btn-secondary">
                            <i class="fas fa-ban"></i> Hủy
                        </a>
                        <button type="submit" class="btn btn-danger">
                            <i class="fas fa-trash"></i> Xóa vĩnh viễn
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>