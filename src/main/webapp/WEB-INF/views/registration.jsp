<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
    <h1>Please input your user details</h1>
    <h2 style="color: crimson">${message}</h2>
    <form method="post" action="${pageContext.request.contextPath}/registration">
        Login: <input type="text" name="login">
        Username: <input type="text" name="username">
        Password: <input type="password" name="pwd">
        Password: <input type="password" name="pwdRep">
        <button type="submit">Register</button>
    </form>
</body>
</html>
