<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>
<h1>Main page</h1>
<a href="${pageContext.request.contextPath}/injectdata">Inject products</a>
<h2> </h2>
<a href="${pageContext.request.contextPath}/addproduct">Add a product</a>
<h3> </h3>
<a href="${pageContext.request.contextPath}/products/all">All products</a>
<h4> </h4>
<a href="${pageContext.request.contextPath}/shoppingcart">Shopping cart</a>
<h5> </h5>
<a href="${pageContext.request.contextPath}/orders/all">Orders</a>
<h5> </h5>
<a href="${pageContext.request.contextPath}/logout">Logout</a>
</body>
</html>
