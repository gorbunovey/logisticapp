<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>New Driver</title>
    <c:import url="../head.jsp"/>
</head>
<body>
<c:import url="../header.jsp"/>
<div>
    <%-- pageBody --%>
    <div>
        <form:form action="/drivers/new" method="post" modelAttribute="driver">
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
                    <td colspan="3">
                        <input type="submit" value="Create Driver" />
                    </td>
                </tr>
            </table>
        </form:form>
    </div>
        <p>${message}</p>
    <%-- Pagination --%>
    <%-- pageBody --%>
    <c:import url="../footer.jsp"/>
</div>
</body>
</html>
