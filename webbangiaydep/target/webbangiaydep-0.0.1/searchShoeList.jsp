<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="presenters.SearchShoe.SearchShoeItem" %>

<html>
<head>
    <title>Danh sách giày</title>
    <link rel="stylesheet" href="searchShoeList.css">
</head>
<body>
    <h2>Danh sách giày</h2>

    <%
        List<SearchShoeItem> shoesList = (List<SearchShoeItem>) request.getAttribute("shoesList");
        if (shoesList != null && !shoesList.isEmpty()) {
    %>
        <ul>
            <% for (SearchShoeItem shoe : shoesList) { %>
                <li style="margin-bottom:20px; list-style:none;">
                    <h3><%= shoe.name %></h3>
                    <img src="image/<%= shoe.imageUrl %>" alt="<%= shoe.name %>" width="100"/>
                    <p>Giá: <%= shoe.price %></p>

                    <a href="viewShoeDetail?id=<%= shoe.id %>">
                        <button>Xem chi tiết</button>
                    </a>

                    <form action="addToCart" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<%= shoe.id %>" />
                        <button type="submit">Thêm vào giỏ hàng</button>
                    </form>
                </li>
            <% } %>
        </ul>
    <%
        } else {
    %>
        <p>Không tìm thấy sản phẩm nào.</p>
    <%
        }
    %>

</body>
</html>
