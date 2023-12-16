<%--
  Created by IntelliJ IDEA.
  User: peter
  Date: 2023-12-13
  Time: AM 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"%>
<html>
<head>
    <title>Register</title>
</head>
<body>
<form action="/todo/register" method="post">
    <div>
<%--        placegolder : 글자 입력할 때 사용.--%>
        <input type="text" name="title" placeholder="INSERT TITLE">
    </div>
    <div>
        <input type="date" name="dueDate">
    </div>
    <div>
        <button type="reset">RESET</button>
        <button type="submit">REGISTER</button>
    </div>
</form>
</body>
</html>
