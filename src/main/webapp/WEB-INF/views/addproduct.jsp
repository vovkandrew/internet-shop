<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Product</title>
</head>
<body>
    <h1>Please fill in product name and price</h1>
    <form method="post" action="${pageContext.request.contextPath}/addproduct">
    Name: <input type="text" name="name">
    Price: <input type="text" name="price">
    <button type="submit">Add</button>
    </form>
</body>
</html>
