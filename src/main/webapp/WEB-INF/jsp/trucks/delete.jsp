<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Delete Truck</title>
    <c:import url="../head.jsp"/>
</head>
<body>
<c:import url="../header.jsp"/>
<div>
    <%-- pageBody --%>
    <h2>Delete Truck #<c:out value="${regNumber}"/></h2>
    <div>
        <c:choose>
            <c:when test="${!empty truck}">
                <form:form action="/trucks/delete/${truck.regNumber}" method="post">
                    <table>
                        <tr>
                            <td>Registration Number:</td>
                            <td>${truck.regNumber}</td>
                        </tr>
                        <tr>
                            <td>Crew size:</td>
                            <td>${truck.crew}</td>
                        </tr>
                        <tr>
                            <td>Load capacity:</td>
                            <td>${truck.capacity}</td>
                        </tr>
                        <tr>
                            <td>Vehicle state:</td>
                            <td>${truck.active == true ? "active":"broken"}</td>
                        </tr>
                        <tr>
                            <td>Current city:</td>
                            <td>${truck.cityName == null ? "no City" : truck.cityName}</td>

                        </tr>

                        <tr>
                            <td colspan="1">
                                <input type="submit" value="Delete"/>
                            </td>
                        </tr>
                    </table>
                </form:form>
            </c:when>
            <c:otherwise>
                <h2>?</h2>
                <div>No such truck with registration Number #<c:out value="${regNumber}"/></div>
            </c:otherwise>
        </c:choose>
    </div>
    <%-- pageBody --%>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>
