<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Kết Quả Đặt Hàng</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { background: #f5f5f5; font-family: 'Segoe UI', sans-serif; }
        .result-card {
            max-width: 700px; margin: 50px auto; background: white; border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05); overflow: hidden; text-align: center; padding-bottom: 30px;
        }
        .header-icon { margin-top: 40px; margin-bottom: 20px; }
        .status-title { font-size: 24px; font-weight: bold; color: #333; margin-bottom: 10px; }
        .status-desc { color: #666; margin-bottom: 30px; }
        .order-info { background: #fcfcfc; border-top: 1px dashed #ddd; border-bottom: 1px dashed #ddd; padding: 20px; text-align: left; }
        .info-row { display: flex; justify-content: space-between; margin-bottom: 10px; font-size: 15px; }
        .total-row { font-size: 18px; font-weight: bold; color: #e74c3c; margin-top: 15px; border-top: 1px solid #eee; padding-top: 15px; }
        .btn-action { border-radius: 30px; padding: 10px 30px; font-weight: 600; margin: 5px; }
    </style>
</head>
<body>

<div class="container">
    <div class="result-card">
        
        <%-- LẤY DỮ LIỆU TỪ REQUEST HOẶC PARAM --%>
        <c:set var="status" value="${not empty orderResult ? orderResult.status : param.paymentSuccess == 'true' ? 'PAID' : 'UNKNOWN'}" />
        <c:set var="orderId" value="${not empty orderResult ? orderResult.orderId : param.orderId}" />
        <c:set var="total" value="${not empty orderResult ? orderResult.totalAmount : 0}" />
        <c:set var="paymentUrl" value="${not empty orderResult ? orderResult.paymentUrl : ''}" />

        <%-- TRƯỜNG HỢP 1: CHỜ THANH TOÁN (VNPAY) --%>
        <c:if test="${status == 'PENDING_PAYMENT'}">
            <div class="header-icon text-warning">
                <i class="fas fa-clock fa-5x"></i>
            </div>
            <h2 class="status-title">Đơn Hàng Đã Được Tạo!</h2>
            <p class="status-desc">Vui lòng thanh toán để hoàn tất đơn hàng.</p>
            
            <div class="order-info mx-4">
                <div class="info-row"><span>Mã đơn hàng:</span> <strong>#${orderId}</strong></div>
                <div class="info-row"><span>Trạng thái:</span> <span class="badge badge-warning">Chờ thanh toán</span></div>
                <div class="info-row"><span>Phương thức:</span> <strong>VNPAY</strong></div>
                <div class="info-row total-row">
                    <span>Tổng thanh toán:</span>
                    <span><fmt:formatNumber value="${total}" type="currency" currencySymbol="₫"/></span>
                </div>
            </div>

            <div class="mt-4">
                <p>Quý khách sẽ được chuyển đến cổng thanh toán VNPAY</p>
                <a href="${paymentUrl}" class="btn btn-danger btn-lg btn-action shadow">
                    <i class="fas fa-credit-card mr-2"></i> THANH TOÁN NGAY
                </a>
            </div>
        </c:if>

        <%-- TRƯỜNG HỢP 2: THÀNH CÔNG (COD HOẶC ĐÃ THANH TOÁN VNPAY) --%>
        <c:if test="${status == 'SUCCESS' || status == 'PENDING' || status == 'PAID'}">
            <div class="header-icon text-success">
                <i class="fas fa-check-circle fa-5x"></i>
            </div>
            <h2 class="status-title">Đặt Hàng Thành Công!</h2>
            <p class="status-desc">Cảm ơn bạn đã mua sắm tại Shop Giày Đẹp.</p>

            <div class="order-info mx-4">
                <div class="info-row"><span>Mã đơn hàng:</span> <strong>#${orderId}</strong></div>
                <div class="info-row"><span>Trạng thái:</span> <span class="badge badge-success">Đã xác nhận</span></div>
                
                <%-- Nếu có số tiền thì hiện (trường hợp vừa đặt xong) --%>
                <c:if test="${total > 0}">
                    <div class="info-row total-row">
                        <span>Tổng thanh toán:</span>
                        <span><fmt:formatNumber value="${total}" type="currency" currencySymbol="₫"/></span>
                    </div>
                </c:if>
            </div>

            <div class="mt-4">
                <a href="index.jsp" class="btn btn-outline-primary btn-action">Về trang chủ</a>
                <a href="${pageContext.request.contextPath}/admin/order-detail.jsp?id=${orderId}" class="btn btn-primary btn-action">Xem chi tiết đơn</a>
            </div>
        </c:if>

        <%-- TRƯỜNG HỢP 3: THẤT BẠI --%>
        <c:if test="${status == 'FAILED' || not empty param.error}">
            <div class="header-icon text-danger">
                <i class="fas fa-times-circle fa-5x"></i>
            </div>
            <h2 class="status-title">Đặt Hàng Thất Bại</h2>
            <p class="status-desc text-danger">
                ${not empty orderResult ? orderResult.message : param.error}
            </p>
            <div class="mt-4">
                <a href="Cart.jsp" class="btn btn-secondary btn-action">Quay lại giỏ hàng</a>
            </div>
        </c:if>

    </div>
</div>

</body>
</html>