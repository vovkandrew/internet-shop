<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>All products</title>
</head>
<body>
<h1>All products</h1>
<table border="2">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
    </tr>
    <c:forEach var="product" items="${products}">
    <tr>
        <td>
            <c:out value="${product.id}"/>
        </td>
        <td>
            <c:out value="${product.name}"/>
        </td>
        <td>
            <c:out value="${product.price}"/>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/shoppingcart/add?id=${product.id}">
                Add
            </a>
        </td>
    </tr>
    </c:forEach>
</table>
<h1> </h1>
<a href="${pageContext.request.contextPath}/shoppingcart">Check your cart</a>
</body>
</html>
