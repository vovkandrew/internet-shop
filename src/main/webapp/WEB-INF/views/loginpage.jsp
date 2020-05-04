<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Please input your login and password</h1>
<h2 style="color: crimson">${errorMessage}</h2>
<form method="post" action="${pageContext.request.contextPath}/login">
    Login: <input type="text" name="login">
    Password: <input type="password" name="pwd">
    <button type="submit">Login</button>
</form>
<h1> </h1>
<a href="${pageContext.request.contextPath}/registration">
    Click to register
</a>
</body>
</html>
