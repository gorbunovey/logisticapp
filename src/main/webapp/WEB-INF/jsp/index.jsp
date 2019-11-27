<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <p>Some text...</p>
    <p>There will be init DB button</p>
    <p>There will be drop DB butto</p>
    </div>
    <%-- Pagination --%>
    <%-- pageBody --%>
    <c:import url="footer.jsp"/>
</div>
</body>
</html>