<%--
  Created by IntelliJ IDEA.
  User: cvt11
  Date: 20/01/19
  Time: 6:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
    <style>
        .failed{color: red}
    </style>
</head>
<body>
<h3>Custom Login Page</h3>
    <form:form action="${pageContext.request.contextPath}/authenticateTheUser" method="post">
        <%--check for error login messsages--%>

        <c:if test="${param.error!=null}">
            <i class="failed" >Sorry! You entered invalid username / password.</i>
        </c:if>
        
        <p>
            Username : <input type="text" name="username"/>
        </p>

        <p>
            Password : <input type="text" name="password"/>
        </p>

        <input type="submit" value="Login" />

    </form:form>

</body>
</html>
