<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="products" scope="request" type="java.util.List<internetshop.model.Product>"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Shopping cart</title>
</head>
<body>
<h1>Shopping cart</h1>
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
            <a href="${pageContext.request.contextPath}/shoppingcart/delete?id=${product.id}">
                Delete
            </a>
        </td>
    </tr>
    </c:forEach>
</table>
<h1> </h1>
<a href="${pageContext.request.contextPath}/shoppingcart/completeorder">
    Complete order
</a>
<h1> </h1>
<a href="${pageContext.request.contextPath}/products/all">
    All products
</a>
</body>
</html>