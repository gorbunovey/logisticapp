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
    <h2>Delete Driver#<c:out value="${id}"/></h2>
    <div>
        <c:choose>
                <c:when test="${!empty driver}">
                    <form:form action="/drivers/delete/${driver.id}" method="post" modelAttribute="driver" >
                        <table>
                            <tr>
                                <td>Driver ID:</td>
                                <td><form:input  path="id" disabled="true"/></td>
                            </tr>
                            <tr>
                                <td>First Name:</td>
                                <td><form:input path="firstName" disabled="true"/></td>
                            </tr>
                            <tr>
                                <td>Last Name:</td>
                                <td><form:input path="lastName" disabled="true"/></td>
                            </tr>
                            <tr>
                                <td>Patronymic Name:</td>
                                <td><form:input path="patronymicName"  disabled="true"/></td>

                            </tr>
                            <tr>
                                <td>Status:</td>
                                <td><form:input path="status" disabled="true"/></td>

                            </tr>
                            <tr>
                                <td>Hours:</td>
                                <td><form:input path="hours" disabled="true"/></td>
                            </tr>
                            <tr>
                                <td>City:</td>
                                <td><form:input path="city.name" disabled="true"/></td>
                            </tr>
                            <tr>
                                <td>Truck:</td>
                                <td><form:input path="truck.regNumber" disabled="true"/></td>
                            </tr>
                            <tr>
                                <td colspan="1">
                                    <input type="submit" value="Delete"/>
                                </td>
                                <td colspan="1">
                                    <input type="reset" value="Reset"/>
                                </td>
                            </tr>
                        </table>
                    </form:form>
                </c:when>
            <c:otherwise>
                <h2>?</h2>
                <div>No such driver with ID#<c:out value="${id}"/></div>
            </c:otherwise>
        </c:choose>
    </div>
    <%-- pageBody --%>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>
