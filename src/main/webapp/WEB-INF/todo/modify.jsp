<%--
  Created by IntelliJ IDEA.
  User: peter
  Date: 2023-12-13
  Time: AM 9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"%>
<html>
<head>
    <title>Todo Modify/Remove</title>
</head>
<body>
<form id="form1" action="/todo/modify" method="post">
    <div>
        <%--        상세보기에는 readonly가 들어가야함--%>
        <input type="text" name="tno" value="${dto.tno}" readonly>
    </div>
    <div>
        <input type="text" name="title" value="${dto.title}" >
    </div>
    <div>
        <%--        형식이 정해져있어 readonly가 필요없음--%>
        <input type="date" name="dueDate" value="${dto.dueDate}" >
    </div>
    <div>
        <input type="checkbox" name="finished" ${dto.finished ? "checked" : ""} >
    </div>
    <div>
        <button type="submit">Modify</button>
    </div>
</form>
<form id="form2" action="/todo/remove" method="post">
    <input type="hidden" name="tno" value="${dto.tno}" readonly>
    <div>
        <button type="submit">Remove</button>
    </div>
</form>

</body>
</html>
