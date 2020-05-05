<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>All orders</title>
</head>
<body>
<h1>All orders</h1>
<table border="2">
    <tr>
        <th>User ID</th>
        <th>User Name</th>
        <th>Order ID</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.user.id}"/>
            </td>
            <td>
                <c:out value="${order.user.name}"/>
            </td>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/orderdetails?id=${order.id}">
                    Details
                </a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/orders/all/delete?id=${order.id}">
                    Delete
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

