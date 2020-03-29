<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Index</title>
    <c:import url="../head.jsp"/>
</head>
<body>
<c:import url="../header.jsp"/>
<div>
    <%-- pageBody --%>
        <c:if test="${error == true}">
            <div><strong><c:out value="Invalid email and password"/></strong></div>
        </c:if>
        <form:form action="/login/process" method="post">
            <div>
                Email: <input name="email" type="email">
            </div>
            <div>
                Password: <input name="password" type="password">
            </div>
            <input type="submit">
        </form:form>
    <%-- pageBody --%>
    <c:import url="../footer.jsp"/>
</div>
</body>
</html>