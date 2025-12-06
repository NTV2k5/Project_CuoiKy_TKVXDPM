<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giỏ Hàng của Bạn</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <style>
        .cart-img { width: 80px; height: 80px; object-fit: cover; border-radius: 8px; }
        .cart-table td, .cart-table th { vertical-align: middle; text-align: center; }
        .cart-table th { background-color: #f8f9fa; }
        .item-checkbox { transform: scale(1.2); }
        #selectedTotal { font-size: 1.4rem; color: #e74c3c; font-weight: bold; }
    </style>
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Giỏ Hàng của Bạn</h2>

    <c:if test="${empty cartItems}">
        <div class="alert alert-info text-center">Giỏ hàng trống! <a href="index.jsp">Mua sắm ngay</a></div>
    </c:if>

    <c:if test="${not empty cartItems}">
        <form id="cartForm">
            <table class="table table-bordered cart-table">
                <thead class="thead-light">
                <tr>
                    <th width="5%">
                        <input type="checkbox" id="selectAll" title="Chọn tất cả">
                    </th>
                    <th width="12%">Ảnh</th>
                    <th width="25%">Sản phẩm</th>
                    <th width="10%">Size</th>
                    <th width="10%">Màu</th>
                    <th width="10%">Số lượng</th>
                    <th width="14%">Đơn giá</th>
                    <th width="14%">Thành tiền</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${cartItems}" varStatus="loop">
                    <tr>
                        <td>
                            <input type="checkbox" 
                                   class="item-checkbox" 
                                   name="selected" 
                                   value="${item.productId}-${item.size}-${item.color}"
                                   data-price="${item.totalPrice}">
                        </td>
                        <td>
                            <img src="${pageContext.request.contextPath}/images/${item.imageUrl}" 
                                 class="cart-img" 
                                 alt="${item.productName}"
                                 onerror="this.src='${pageContext.request.contextPath}/images/no-image.jpg'">
                        </td>
                        <td class="text-left font-weight-bold">${item.productName}</td>
                        <td>${item.size}</td>
                        <td>${item.color}</td>
                        <td>${item.quantity}</td>
                        <td><fmt:formatNumber value="${item.price}" type="currency" currencySymbol="" /> ₫</td>
                        <td class="item-total"><fmt:formatNumber value="${item.totalPrice}" type="currency" currencySymbol="" /> ₫</td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr class="table-warning">
                    <td colspan="7" class="text-right font-weight-bold">
                        Tổng tiền thanh toán:
                    </td>
                    <td class="font-weight-bold" id="selectedTotal">0 ₫</td>
                </tr>
                </tfoot>
            </table>

            <div class="text-right mt-4">
                <button type="button" class="btn btn-danger btn-lg mr-3" onclick="checkoutSelected()">
                    Thanh toán đã chọn (<span id="selectedCount">0</span>)
                </button>
                <a href="index.jsp" class="btn btn-secondary btn-lg">Tiếp tục mua sắm</a>
            </div>
        </form>
    </c:if>
</div>

<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
<script>
$(document).ready(function() {
    // CHỈ CHẠY KHI CÓ GIỎ HÀNG – KHÔNG BAO GIỜ LỖI NULL NỮA!
    const $selectAll = $('#selectAll');
    const $checkboxes = $('.item-checkbox');

    // Nếu không có sản phẩm → không chạy script này
    if ($selectAll.length === 0) return;

    function calculateTotal() {
        let total = 0;
        $checkboxes.filter(':checked').each(function() {
            total += parseFloat($(this).data('price') || 0);
        });
        $('#selectedTotal').text(total.toLocaleString('vi-VN') + ' ₫');
        $('#selectedCount').text($checkboxes.filter(':checked').length);
    }

    // Chọn/tắt tất cả
    $selectAll.on('change', function() {
        $checkboxes.prop('checked', this.checked);
        calculateTotal();
    });

    // Tick từng món
    $checkboxes.on('change', calculateTotal);

    // Thanh toán đã chọn
    window.checkoutSelected = function() {
        const checked = $checkboxes.filter(':checked');
        if (checked.length === 0) {
            alert('Vui lòng chọn ít nhất 1 sản phẩm để thanh toán!');
            return;
        }

        console.log('Items selected:', checked.length);
        let values = [];
        checked.each(function() {
            values.push(this.value);
        });
        console.log('Values to be sent:', values);
        const form = $('<form>', {
            method: 'POST',
            action: '${pageContext.request.contextPath}/checkout-selected'
        });

        checked.each(function() {
            $('<input>').attr({
                type: 'hidden',
                name: 'selected',
                value: this.value
            }).appendTo(form);
        });

        form.appendTo('body').submit();
    };

    // Tính tổng lần đầu
    calculateTotal();
});
</script>
</body>
</html>