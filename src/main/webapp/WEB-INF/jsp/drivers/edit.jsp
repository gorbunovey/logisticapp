<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Driver</title>
    <c:import url="../head.jsp"/>
</head>
<body>
<c:import url="../header.jsp"/>
<div>
    <%-- pageBody --%>
    <h2>Edit Driver ID#<c:out value="${id}"/></h2>
    <div>
        <c:choose>
            <c:when test="${!empty driver}">
                <form:form action="/drivers/edit/${driver.id}" method="post" modelAttribute="driver">
                    <table>
                        <tr>
                            <td>Driver ID:</td>
                            <td><form:input path="id" /></td>
                            <td><form:errors path="id" /></td>
                        </tr>
                        <tr>
                            <td>First Name:</td>
                            <td><form:input path="firstName" /></td>
                            <td><form:errors path="firstName" /></td>
                        </tr>
                        <tr>
                            <td>Last Name:</td>
                            <td><form:input path="lastName" /></td>
                            <td><form:errors path="lastName" /></td>
                        </tr>
                        <tr>
                            <td>Patronymic Name:</td>
                            <td><form:input path="patronymicName" /></td>
                            <td><form:errors path="patronymicName" /></td>

                        </tr>
                        <tr>
                            <td>Status:</td>
                            <td><form:input path="status" /></td>
                            <td><form:errors path="status" /></td>

                        </tr>
                        <tr>
                            <td>Hours:</td>
                            <td><form:input path="hours" /></td>
                            <td><form:errors path="hours" /></td>
                        </tr>
                        <tr>
                            <td>City:</td>
                            <td><form:input path="city" /></td>
                            <td><form:errors path="city" /></td>
                        </tr>
                        <tr>
                            <td>Truck:</td>
                            <td><form:input path="truck" /></td>
                            <td><form:errors path="truck" /></td>
                        </tr>
                        <tr>
                            <td colspan="1">
                                <input type="submit" value="Submit changes" />
                            </td>
                            <td colspan="1">
                                <input type="reset" value="Reset" />
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
        <div><c:out value="${statusMessage}"/></div>
    <%-- pageBody --%>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>