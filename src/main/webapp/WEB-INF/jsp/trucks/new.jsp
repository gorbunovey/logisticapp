<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>New Truck</title>
    <c:import url="../head.jsp"/>
</head>
<body>
<c:import url="../header.jsp"/>
<div>
    <%-- pageBody --%>
    <div>
        <form:form action="/truck/new" method="post" modelAttribute="truck">
            <table>
                <tr>
                    <td>Registration Number:</td>
                    <td><form:input path="regNumber" /></td>
                    <td><form:errors path="regNumber" /></td>
                </tr>
                <tr>
                    <td>Crew Size:</td>
                    <td><form:input path="crewSize" /></td>
                    <td><form:errors path="crewSize" /></td>
                </tr>
                <tr>
                    <td>Load capacity:</td>
                    <td><form:input path="capacity" /></td>
                    <td><form:errors path="capacity" /></td>
                </tr>
                <tr>
                    <td>Vehicle state:</td>
                    <td><form:input path="state" /></td>
                    <td><form:errors path="state" /></td>

                </tr>
                <tr>
                    <td>Current city:</td>
                    <td><form:input path="city" /></td>
                    <td><form:errors path="city" /></td>

                </tr>
                <tr>
                    <td colspan="3">
                        <input type="submit" value="Create Truck" />
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
