<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Index</title>
    <c:import url="head.jsp"/>
</head>
<body>
<c:import url="header.jsp"/>
<div>
    <%-- pageBody --%>
    <div>
        <h2>Logistic Web Application</h2>
        <sec:authorize access="!isAuthenticated()">
            <div>
                To get access, please login or register a new account
            </div>
            <br/>
            <div>
                <a href="/login">Login</a><br/><br/>
                <a href="/registration">Registration</a><br/><br/>
            </div>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <div></div><br/>
                <p>Hello, <sec:authentication property="principal.firstName"/>! Nice to see You again!</p>
            </div>
        </sec:authorize>
    </div>
    <%-- pageBody --%>
    <c:import url="footer.jsp"/>
</div>
</body>
</html>