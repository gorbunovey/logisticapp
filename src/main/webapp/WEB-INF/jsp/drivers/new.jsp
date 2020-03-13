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
    <h2>New Driver</h2>
    <div>
        <form:form action="/drivers/new" method="post" modelAttribute="driver">
            <table>
                <tr>
                    <td>Driver ID:</td>
                    <td><form:input path="id" pattern = "^([0-9]|([1-9][0-9]{1,8}))$"  required="true"/></td>
                    <td><form:errors path="id"  /></td>
                </tr>
                <tr>
                    <td>First Name:</td>
                    <td><form:input path="firstName" pattern = "[^\d#&<>\"~;$^%{}?]{2,45}" required="true"/></td>
                    <td><form:errors path="firstName" /></td>
                </tr>
                <tr>
                    <td>Last Name:</td>
                    <td><form:input path="lastName" pattern = "[^\d#&<>\"~;$^%{}?]{2,45}" required="true"/></td>
                    <td><form:errors path="lastName" /></td>
                </tr>
                <tr>
                    <td>Patronymic Name:</td>
                    <td><form:input path="patronymicName" pattern = "[^\d#&<>\"~;$^%{}?]{2,45}"/></td>
                    <td><form:errors path="patronymicName" /></td>

                </tr>
                <tr>
                    <td>Status:</td>
                    <td><form:input path="status" pattern = "[^\s\d#&<>\"~;$^%{}?]{2,20}" required="true"/></td>
                    <td><form:errors path="status" /></td>

                </tr>
                <tr>
                    <td>Hours:</td>
                    <td><form:input path="hours" pattern = "^([0-9]|([1-3][0-9][0-9]))$" required="true"/></td>
                    <td><form:errors path="hours" /></td>
                </tr>
                <tr>
                    <td colspan="1">
                        <input type="submit" value="Add" />
                    </td>
                    <td colspan="1">
                        <input type="reset" value="Reset" />
                    </td>
                </tr>
            </table>
        </form:form>
    </div>
        <div><c:out value="${statusMessage}"/></div>
    <%-- pageBody --%>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>
