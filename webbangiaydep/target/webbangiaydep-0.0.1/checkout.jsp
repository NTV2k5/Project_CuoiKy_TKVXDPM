<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thanh Toán - Shop Giày Đẹp</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { background: #f8f9fa; font-family: 'Segoe UI', sans-serif; }
        .checkout-container { max-width: 900px; margin: 40px auto; background: white; border-radius: 12px; overflow: hidden; box-shadow: 0 10px 30px rgba(0,0,0,0.1); }
        .checkout-header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px; text-align: center; }
        .section { padding: 30px; border-bottom: 1px solid #eee; }
        .section:last-child { border-bottom: none; }
        .payment-option { cursor: pointer; padding: 15px; border: 2px solid #ddd; border-radius: 10px; margin: 10px 0; transition: all 0.3s; display: flex; align-items: center; }
        .payment-option.selected { border-color: #667eea; background: #f0f3ff; }
        .payment-option i { font-size: 24px; margin-right: 15px; color: #667eea; width: 30px; text-align: center; }
        .total-price { font-size: 1.5rem; font-weight: bold; color: #e74c3c; }
        .product-img { width: 60px; height: 60px; object-fit: cover; border-radius: 8px; margin-right: 15px; border: 1px solid #eee; }
    </style>
</head>
<body>

<div class="checkout-container">
    <div class="checkout-header">
        <h2><i class="fas fa-shopping-cart"></i> Xác Nhận Đơn Hàng</h2>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-danger m-3 text-center">${error}</div>
    </c:if>

    <form id="checkoutForm" action="${pageContext.request.contextPath}/OrderShoeServlet" method="post">
        
        <div class="section">
            <h4><i class="fas fa-map-marker-alt text-primary mr-2"></i> Thông Tin Nhận Hàng</h4>
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label>Họ và tên <span class="text-danger">*</span></label>
                        <input type="text" name="customerName" class="form-control" value="${sessionScope.user.fullName}" required>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label>Số điện thoại <span class="text-danger">*</span></label>
                        <input type="text" name="phone" class="form-control" value="${sessionScope.user.phone}" required>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label>Địa chỉ giao hàng <span class="text-danger">*</span></label>
                <input type="text" name="address" class="form-control" placeholder="Số nhà, đường, phường/xã..." required>
            </div>
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" class="form-control" value="${sessionScope.user.email}">
            </div>
        </div>

        <div class="section">
            <h4><i class="fas fa-credit-card text-primary mr-2"></i> Phương Thức Thanh Toán</h4>
            <div class="payment-option selected" onclick="selectPayment('COD')">
                <i class="fas fa-truck"></i>
                <div><strong>Thanh toán khi nhận hàng (COD)</strong></div>
            </div>
            <div class="payment-option" onclick="selectPayment('ONLINE')">
                <i class="fas fa-qrcode"></i>
                <div><strong>Thanh toán Online qua VNPAY</strong></div>
            </div>
            <input type="hidden" name="paymentMethod" id="paymentMethod" value="COD">
        </div>

        <div class="section">
            <h4><i class="fas fa-box-open text-primary mr-2"></i> Đơn Hàng Của Bạn</h4>
            <c:if test="${not empty cartItems}">
                <div class="mt-4">
                    <c:forEach var="item" items="${cartItems}">
                        <div class="media mb-3 border-bottom pb-3">
                            <img src="${pageContext.request.contextPath}/images/${item.imageUrl}" class="product-img"
                                 onerror="this.src='https://via.placeholder.com/60'">
                            <div class="media-body">
                                <h6 class="mt-0 font-weight-bold">${item.productName}</h6>
                                <div class="text-muted small">
                                    Size: ${item.size} | Màu: ${item.color} | SL: x${item.quantity}
                                </div>
                                <div class="text-danger font-weight-bold mt-1">
                                    <fmt:formatNumber value="${item.totalPrice}" type="currency" currencySymbol="₫"/>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            <div class="text-right mt-3">
                <span class="h5 mr-2">Tổng thanh toán:</span>
                <span class="total-price">
                    <fmt:formatNumber value="${totalAmount}" type="currency" currencySymbol="₫"/>
                </span>
            </div>
        </div>

        <div class="section text-center bg-light">
            <button type="submit" class="btn btn-primary btn-lg px-5 shadow">XÁC NHẬN ĐẶT HÀNG</button>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
<script>
    function selectPayment(method) {
        $('.payment-option').removeClass('selected');
        if(method === 'COD') $('.payment-option:first').addClass('selected');
        else $('.payment-option:last').addClass('selected');
        $('#paymentMethod').val(method);
    }
</script>
</body>
</html>