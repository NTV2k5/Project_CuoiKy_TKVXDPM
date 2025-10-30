<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <%= 
            ((presenters.ViewShoeDetail.ViewShoeDetailItem) request.getAttribute("shoeDetail")) != null ?
            ((presenters.ViewShoeDetail.ViewShoeDetailItem) request.getAttribute("shoeDetail")).name :
            "Sản phẩm"
        %>
    </title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/detailShoe.css">
</head>
<body>

<%  
    presenters.ViewShoeDetail.ViewShoeDetailItem shoe =
        (presenters.ViewShoeDetail.ViewShoeDetailItem) request.getAttribute("shoeDetail");
%>

<div class="container">
    <% if (shoe != null) { %>
        <div class="product-card">
            <!-- Ảnh -->
            <div class="product-image">
                <img src="<%= request.getContextPath() %>/images/<%= shoe.imageUrl %>" 
                     alt="<%= shoe.name %>">
            </div>

            <!-- Thông tin -->
            <div class="product-info">
                <h1 class="product-title"><%= shoe.name %></h1>
                <p><strong>Hãng:</strong> <%= shoe.brand %></p>
                <p><strong>Danh mục:</strong> <%= shoe.category %></p>
                <p><strong>Giá:</strong> <span class="price"><%= shoe.price %> VND</span></p>
                <p><strong>Mô tả:</strong> <%= shoe.description %></p>

                <!-- SIZE & COLOR -->
                <% if (shoe.variants != null && !shoe.variants.isEmpty()) { %>
                    <div class="variant-group">
                        <p><strong>Size:</strong></p>
                        <div class="size-options">
                            <%
                                java.util.Set<String> displayedSizes = new java.util.HashSet<>();
                                for (presenters.ViewShoeDetail.ViewShoeDetailItem.Variant v : shoe.variants) {
                                    if (displayedSizes.add(v.size)) {
                                        boolean inStock = v.stock > 0;
                            %>
                                        <label class="size-btn <%= inStock ? "" : "disabled" %>">
                                            <input type="radio" name="size" value="<%= v.size %>" 
                                                   <%= inStock ? "" : "disabled" %> />
                                            <%= v.size %>
                                            <% if (!inStock) { %><span class="out">Hết</span><% } %>
                                        </label>
                            <%
                                    }
                                }
                            %>
                        </div>
                    </div>

                    <div class="variant-group">
                        <p><strong>Màu:</strong></p>
                       <div class="color-options">
                            <%
                                java.util.Set<String> displayedColors = new java.util.HashSet<>();
                                for (presenters.ViewShoeDetail.ViewShoeDetailItem.Variant v : shoe.variants) {
                                    if (displayedColors.add(v.color)) {
                            %>
                                        <label class="color-text-btn">
                                            <input type="radio" name="color" value="<%= v.color %>" />
                                            <%= v.color %>
                                        </label>
                            <%
                                    }
                                }
                            %>
                        </div>
                    </div>
                <% } else { %>
                    <p><em>Không có biến thể size/màu.</em></p>
                <% } %>

                <a href="index.jsp" class="btn-back">Quay lại trang chủ</a>
                <button class="btn-add-cart" onclick="alert('Chưa có giỏ hàng!')">Thêm vào giỏ</button>
            </div>
        </div>
    <% } else { %>
        <div class="not-found">
            <p>Không tìm thấy sản phẩm!</p>
            <a href="index.jsp" class="btn-back">Quay lại</a>
        </div>
    <% } %>
</div>

</body>
</html>