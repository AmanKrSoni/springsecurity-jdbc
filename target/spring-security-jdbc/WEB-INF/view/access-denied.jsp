<%--
  Created by IntelliJ IDEA.
  User: cvt11
  Date: 21/01/19
  Time: 11:58 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Access Denied</title>
    <style>
        .failed{color: red}
    </style>
</head>
<body>
<h2 class="failed" >Access Denied - you are not authorised to access this resource!</h2>
<br><hr>
<a href="${pageContext.request.contextPath}/" >Back To Home</a>
</body>
</html>
