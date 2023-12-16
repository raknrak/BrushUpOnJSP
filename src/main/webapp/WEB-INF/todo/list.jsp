<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: peter
  Date: 2023-12-12
  Time: PM 4:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"%>
<html>
<head>
    <title>List</title>
</head>
<body>
  <h1> Todo List</h1>
  <ul>
<%--    dtoList 전체 데이터를 dto 레코드 단위로 나눠서--%>
    <c:forEach items="${dtoList}" var="dto">
        <li>
                <%--줄 단위로 출력해라--%>
                <%--<li>${dto}</li>--%>
            <span><a href="/todo/read?tno=${dto.tno}">${dto.tno}</a></span>
            <span>${dto.title}</span>
            <span>${dto.dueDate}</span>
            <span>${dto.finished? "DONE": "NOT YET"}</span>
        </li>

    </c:forEach>
  </ul>
</body>
</html>
