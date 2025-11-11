<%-- admin/edit-product.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa Sản phẩm - Admin</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <!-- Sidebar (copy from dashboard) -->
    <nav class="sidebar d-flex flex-column">
        <!-- ... same as dashboard ... -->
    </nav>

    <main class="main-content">
        <h1 class="h3 mb-4"><i class="fas fa-edit me-2 text-warning"></i>Chỉnh sửa Sản phẩm</h1>

        <div class="card">
            <div class="card-body">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                <form method="post" action="${pageContext.request.contextPath}/admin/editProduct">
                    <input type="hidden" name="id" value="${product.id}">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label class="form-label">SKU</label>
                                <input type="text" class="form-control" name="sku" value="${product.sku}" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Tên sản phẩm</label>
                                <input type="text" class="form-control" name="name" value="${product.name}" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Brand</label>
                                <input type="text" class="form-control" name="brand" value="${product.brand}">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Danh mục</label>
                                <select class="form-select" name="categoryId" required>
                                    <option value="">Chọn danh mục</option>
                                    <c:forEach var="category" items="${categories}">
                                        <option value="${category.id}" ${product.categoryId == category.id ? 'selected' : ''}>${category.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label class="form-label">Mô tả ngắn</label>
                                <textarea class="form-control" name="shortDescription" rows="3">${product.shortDescription}</textarea>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Hình ảnh URL</label>
                                <input type="url" class="form-control" name="imageUrl" value="${product.imageUrl}">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Trạng thái</label>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="isActive" id="isActive" ${product.isActive ? 'checked' : ''}>
                                    <label class="form-check-label" for="isActive">Hoạt động</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Mô tả chi tiết</label>
                        <textarea class="form-control" name="description" rows="5">${product.description}</textarea>
                    </div>
                    <button class="btn btn-warning"><i class="fas fa-save me-2"></i>Cập nhật</button>
                    <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-secondary">Hủy</a>
                </form>
            </div>
        </div>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>