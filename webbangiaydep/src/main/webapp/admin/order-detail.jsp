<%-- admin/order-detail.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chi tiết đơn hàng #${order.id}</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <!-- SIDEBAR TƯƠNG TỰ -->
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-3"> <!-- Sidebar --></div>
            <div class="col-md-9">
                <h2>Chi tiết đơn hàng #${order.id}</h2>
                <c:if test="${not empty param.success}">
                    <div class="alert alert-success">${param.success}</div>
                </c:if>

                <div class="row">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header">Thông tin khách hàng</div>
                            <div class="card-body">
                                <p><strong>${order.fullName}</strong></p>
                                <p>Email: ${order.userEmail}</p>
                                <p>Điện thoại: ${order.phone}</p>
                                <p>Địa chỉ: ${order.address}</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header">Thông tin đơn hàng</div>
                            <div class="card-body">
                                <p>Ngày đặt: <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm"/></p>
                                <p>Phương thức: <span class="badge bg-info">${order.paymentMethod}</span></p>
                                <p>Tổng tiền: <strong><fmt:formatNumber value="${order.total}" type="currency" currencySymbol="₫"/></strong></p>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card mt-3">
                    <div class="card-header">Sản phẩm</div>
                    <div class="card-body">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Sản phẩm</th>
                                <th>Phân loại</th>
                                <th>Số lượng</th>
                                <th>Giá</th>
                                <th>Thành tiền</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${items}">
                                <tr>
                                    <td>${item.productName}</td>
                                    <td>${item.variantInfo}</td>
                                    <td>${item.quantity}</td>
                                    <td><fmt:formatNumber value="${item.price}" type="currency" currencySymbol="₫"/></td>
                                    <td><fmt:formatNumber value="${item.subtotal}" type="currency" currencySymbol="₫"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="card mt-3">
                    <div class="card-header">Cập nhật trạng thái</div>
                    <div class="card-body">
                        <form action="update-order-status" method="post">
                            <input type="hidden" name="id" value="${order.id}">
                            <div class="row">
                                <div class="col-md-4">
                                    <select name="status" class="form-select">
                                        <option value="PENDING" ${order.status == 'PENDING' ? 'selected' : ''}>Chờ xử lý</option>
                                        <option value="PAID" ${order.status == 'PAID' ? 'selected' : ''}>Đã thanh toán</option>
                                        <option value="PROCESSING" ${order.status == 'PROCESSING' ? 'selected' : ''}>Đang xử lý</option>
                                        <option value="SHIPPED" ${order.status == 'SHIPPED' ? 'selected' : ''}>Đã giao</option>
                                        <option value="COMPLETED" ${order.status == 'COMPLETED' ? 'selected' : ''}>Hoàn thành</option>
                                        <option value="CANCELLED" ${order.status == 'CANCELLED' ? 'selected' : ''}>Đã hủy</option>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <button class="btn btn-primary">Cập nhật</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <a href="orders.jsp" class="btn btn-secondary mt-3">Quay lại</a>
            </div>
        </div>
    </div>
</body>
</html>