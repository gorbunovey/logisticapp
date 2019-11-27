<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Driver</title>
    <c:import url="../head.jsp"/>
</head>
<body>
<c:import url="../header.jsp"/>
<div>
    <%-- pageBody --%>
    <div>
        <c:if test="${!empty driver}">
            <form>
                <!-- Some form which one for edit and show -->
            </form>
        </c:if>
        <c:if test="${empty driver}">
            <p>No such driver</p>
        </c:if>
    </div>
    <%-- Pagination --%>
    <%-- pageBody --%>
    <c:import url="../footer.jsp"/>
</div>
</body>
</html>