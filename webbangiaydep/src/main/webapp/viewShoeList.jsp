<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Danh sách giày</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="container">
    <h2>Danh sách giày</h2>

    <div class="grid">
        <%
            java.util.List shoesList = (java.util.List) request.getAttribute("shoesList");
            if (shoesList != null) {
                for (Object obj : shoesList) {
                    presenters.ViewShoeList.ViewShoeListItem shoe = (presenters.ViewShoeList.ViewShoeListItem) obj;
        %>
                    <div class="card">
                        <img src="<%= request.getContextPath() %>/image/<%= shoe.imageUrl %>" alt="<%= shoe.name %>" width="200" height="150">

                        <h4><%= shoe.name %></h4>
                        <p>Giá: <strong><%= shoe.price %></strong></p>
                        <a href="<%= request.getContextPath() %>/shoe-detail?id=<%= shoe.id %>" class="btn-detail">Chi tiết</a>
                        <button>Thêm vào giỏ</button>
                    </div>
        <%
                }
            }
        %>
    </div>
    <div style="text-align:center;margin-top:20px;">
        <a href="<%= request.getContextPath() %>/index.jsp" class="btn-view-all">⬅ Quay lại trang chủ</a>
    </div>
</div>
</body>
</html>
