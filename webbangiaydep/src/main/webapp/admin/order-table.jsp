<%-- WebContent/admin/order-table.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="orderList" value="${not empty param.orders ? param.orders : requestScope.orders}" />

<c:choose>
    <c:when test="${empty orderList}">
        <div class="text-center py-5 text-muted">
            <p>Không tìm thấy đơn hàng nào.</p>
        </div>
    </c:when>
    <c:otherwise>
        <div class="table-responsive">
            <table class="table table-hover align-middle">
                <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Khách hàng</th>
                        <th>SĐT</th>
                        <th>Tổng tiền</th>
                        <th>Ngày đặt</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="o" items="${orderList}">
                        <tr>
                            <td>#${o.id}</td>
                            <td>${o.fullName}<br><small class="text-muted">${o.userEmail}</small></td>
                            <td>${o.phone}</td>
                            <td><fmt:formatNumber value="${o.total}" type="currency" currencySymbol="₫"/></td>
                            <td><fmt:formatDate value="${o.orderDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                            <td>
                                <span class="badge 
                                    ${o.status == 'PENDING' ? 'bg-warning' : 
                                      o.status == 'CONFIRMED' ? 'bg-info' :
                                      o.status == 'SHIPPED' ? 'bg-primary' :
                                      o.status == 'DELIVERED' ? 'bg-success' : 'bg-danger'}">
                                    ${o.status == 'PENDING' ? 'Chờ xử lý' :
                                      o.status == 'CONFIRMED' ? 'Đã xác nhận' :
                                      o.status == 'SHIPPED' ? 'Đang giao' :
                                      o.status == 'DELIVERED' ? 'Đã giao' : 'Đã hủy'}
                                </span>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/edit-order.jsp?id=${o.id}" class="btn btn-sm btn-warning">Sửa</a>
                                <a href="${pageContext.request.contextPath}/admin/delete-order.jsp?id=${o.id}" class="btn btn-sm btn-danger">Xóa</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>