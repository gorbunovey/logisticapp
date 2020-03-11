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
    <h2> Delete Driver #<c:out value="${driver.id}"/> ?</h2>
    <div>
        <form:form action="/drivers/delete/${driver.id}" method="post" modelAttribute="driver"  readonly="true" >
            <table>
                <tr>
                    <td>Driver ID:</td>
                    <td><form:input  path="id" /></td>
                </tr>
                <tr>
                    <td>First Name:</td>
                    <td><form:input path="firstName" readonly="true"/></td>
                </tr>
                <tr>
                    <td>Last Name:</td>
                    <td><form:input path="lastName" /></td>
                </tr>
                <tr>
                    <td>Patronymic Name:</td>
                    <td><form:input path="patronymicName" /></td>

                </tr>
                <tr>
                    <td>Status:</td>
                    <td><form:input path="status" /></td>

                </tr>
                <tr>
                    <td>Hours:</td>
                    <td><form:input path="hours" /></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Delete Driver" />
                    </td>
                </tr>
            </table>
        </form:form>
    </div>
    <%-- pageBody --%>
    <c:import url="../footer.jsp"/>
</div>
</body>
</html>
