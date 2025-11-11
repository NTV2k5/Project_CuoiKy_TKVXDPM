<%-- WebContent/admin/add-order.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm Đơn Hàng</title>
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
            <h1 class="h3 mb-0">Thêm Đơn Hàng Mới</h1>
            <a href="${pageContext.request.contextPath}/admin/orders.jsp" class="btn btn-secondary">Quay lại</a>
        </div>

        <div class="card">
            <div class="card-header bg-success text-white">
                <h5 class="mb-0">Thông tin đơn hàng</h5>
            </div>
            <div class="card-body">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>
                <form action="${pageContext.request.contextPath}/admin/create-order" method="post">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">Người dùng (ID)</label>
                            <input type="number" name="userId" class="form-control" required placeholder="VD: 1">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Địa chỉ (ID)</label>
                            <input type="number" name="addressId" class="form-control" required placeholder="VD: 1">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Phương thức thanh toán</label>
                            <select name="paymentMethod" class="form-select" required>
                                <option value="COD">Thanh toán khi nhận hàng</option>
                                <option value="BANK">Chuyển khoản</option>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Tổng tiền</label>
                            <input type="number" step="0.01" name="total" class="form-control" required placeholder="VD: 1500000">
                        </div>
                    </div>

                    <hr class="my-4">
                    <h5>Sản phẩm trong đơn</h5>
                    <div id="items-container">
                        <div class="row g-3 item-row mb-3">
                            <div class="col-md-4">
                                <input type="number" name="items[0].productId" class="form-control" placeholder="ID sản phẩm" required>
                            </div>
                            <div class="col-md-3">
                                <input type="number" name="items[0].quantity" class="form-control" placeholder="Số lượng" required min="1">
                            </div>
                            <div class="col-md-4">
                                <input type="number" step="0.01" name="items[0].price" class="form-control" placeholder="Giá" required>
                            </div>
                            <div class="col-md-1">
                                <button type="button" class="btn btn-danger remove-item">X</button>
                            </div>
                        </div>
                    </div>
                    <button type="button" class="btn btn-outline-primary mb-3" id="add-item">+ Thêm sản phẩm</button>

                    <div class="text-end">
                        <button type="submit" class="btn btn-success btn-lg">Tạo Đơn Hàng</button>
                    </div>
                </form>
            </div>
        </div>
    </main>

    <script>
        let itemIndex = 1;
        document.getElementById('add-item').onclick = function() {
            const container = document.getElementById('items-container');
            const row = document.createElement('div');
            row.className = 'row g-3 item-row mb-3';
            row.innerHTML = `
                <div class="col-md-4">
                    <input type="number" name="items[\${itemIndex}].productId" class="form-control" placeholder="ID sản phẩm" required>
                </div>
                <div class="col-md-3">
                    <input type="number" name="items[\${itemIndex}].quantity" class="form-control" placeholder="Số lượng" required min="1">
                </div>
                <div class="col-md-4">
                    <input type="number" step="0.01" name="items[\${itemIndex}].price" class="form-control" placeholder="Giá" required>
                </div>
                <div class="col-md-1">
                    <button type="button" class="btn btn-danger remove-item">X</button>
                </div>
            `.replace(/\${itemIndex}/g, itemIndex);
            container.appendChild(row);
            itemIndex++;
        };

        document.addEventListener('click', function(e) {
            if (e.target && e.target.classList.contains('remove-item')) {
                e.target.closest('.item-row').remove();
            }
        });
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>