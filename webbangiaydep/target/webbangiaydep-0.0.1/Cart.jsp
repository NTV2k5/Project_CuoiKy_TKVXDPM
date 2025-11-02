<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giỏ Hàng</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <style>
        .cart-img { width: 80px; height: 80px; object-fit: cover; }
        .cart-table td, .cart-table th { vertical-align: middle; }
    </style>
</head>
<body>
<div class="container mt-5">
    <h2>Giỏ Hàng của Bạn</h2>

    <c:if test="${empty cartItems}">
        <div class="alert alert-info">Giỏ hàng trống!</div>
    </c:if>

    <c:if test="${not empty cartItems}">
        <table class="table table-bordered cart-table mt-3">
            <thead>
            <tr>
                <th>Ảnh</th>
                <th>Sản phẩm</th>
                <th>Size</th>
                <th>Màu</th>
                <th>Số lượng</th>
                <th>Đơn giá</th>
                <th>Tổng</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${cartItems}">
                <tr>
                    <td>
                        <img src="${pageContext.request.contextPath}/images/${item.imageUrl}" 
                            class="cart-img" 
                            alt="${item.productName}"
                            onerror="this.src='${pageContext.request.contextPath}/images/no-image.jpg'">
                    </td>
                    <td>${item.productName}</td>
                    <td>${item.size}</td>
                    <td>${item.color}</td>
                    <td>${item.quantity}</td>
                    <td>${item.price}</td>
                    <td>${item.totalPrice}</td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="6" class="text-right"><strong>Tổng cộng:</strong></td>
                <td><strong>${totalAmount}</strong></td>
            </tr>
            </tfoot>
        </table>

        <div class="text-right">
            <a href="checkout.jsp" class="btn btn-success">Thanh toán</a>
            <a href="index.jsp" class="btn btn-primary">Tiếp tục mua sắm</a>
        </div>
    </c:if>
</div>
</body>
</html>
