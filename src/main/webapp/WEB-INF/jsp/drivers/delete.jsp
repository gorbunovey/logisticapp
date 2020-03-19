<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Delete Driver</title>
    <c:import url="../head.jsp"/>
</head>
<body>
<c:import url="../header.jsp"/>
<div>
    <%-- pageBody --%>
    <h2>Delete Driver#<c:out value="${number}"/></h2>
    <div>
        <c:choose>
                <c:when test="${!empty driver}">
                    <form:form action="/drivers/delete/${driver.number}" method="post" modelAttribute="driver" >
                        <table>
                            <tr>
                                <td>Driver Number:</td>
                                <td>${driver.number}</td>
                            </tr>
                            <tr>
                                <td>First Name:</td>
                                <td>${driver.userFirstName}</td>
                            </tr>
                            <tr>
                                <td>Last Name:</td>
                                <td>${driver.userLastName}</td>
                            </tr>
                            <tr>
                                <td>Status:</td>
                                <td>${driver.status}</td>

                            </tr>
                            <tr>
                                <td>Hours:</td>
                                <td>${driver.hours}</td>
                            </tr>
                            <tr>
                                <td>Current City:</td>
                                <td>${driver.cityName == null ? "no City" : driver.cityName}</td>
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
                <div>No such driver with ID#<c:out value="${number}"/></div>
            </c:otherwise>
        </c:choose>
    </div>
    <%-- pageBody --%>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>
