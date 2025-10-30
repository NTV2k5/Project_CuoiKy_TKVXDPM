<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chi tiết sản phẩm</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/detailShoe.css">
</head>
<body>

<%
    presenters.ViewShoeDetail.ViewShoeDetailItem shoe =
        (presenters.ViewShoeDetail.ViewShoeDetailItem) request.getAttribute("shoeDetail");

    if (shoe != null) {
%>
    <div class="product-detail-container">
    <div class="product-frame">
        <div class="product-card">
            <div class="product-image">
                <img src="<%= request.getContextPath() %>/images/<%= shoe.imageUrl %>" alt="<%= shoe.name %>">
            </div>
            <div class="product-info">
                <h2 class="product-title"><%= shoe.name %></h2>
                <p><b>Hãng:</b> <%= shoe.brand %></p>
                <p><b>Danh mục:</b> <%= shoe.category %></p>
                <p><b>Giá:</b> <%= shoe.price %> VND</p>
                <p><b>Size:</b> <%= shoe.size %> | <b>Màu:</b> <%= shoe.color %></p>
                <p><%= shoe.description %></p>
                <a href="index.jsp" class="btn-primary">Quay lại</a>
            </div>
        </div>
    </div>
</div>
<%
    } else {
%>
    <p class="not-found">Không tìm thấy sản phẩm!</p>
<%
    }
%>

</body>
</html>
