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
        <form:form action="/registration" method="post" modelAttribute="user">
            <div>
                <form:label path="firstName">First Name
                </form:label>
                <form:input path="firstName"/>
                <form:errors path="firstName"/>
            </div>
            <div>
                <form:label path="lastName">Last Name
                </form:label>
                <form:input path="lastName"/>
                <form:errors path="lastName"/>
            </div>
            <div>
                <form:label path="email">Email
                </form:label>
                <form:input path="email" type="email"/>
                <form:errors path="email"/>
            </div>
            <div>
                <form:label path="password">Password
                </form:label>
                <form:input path="password" type="password"/>
                <form:errors path="password"/>
            </div>
            <input type="submit" value="Sign Up" >
        </form:form>
    <%-- pageBody --%>
    <c:import url="../footer.jsp"/>
</div>
</body>
</html>


