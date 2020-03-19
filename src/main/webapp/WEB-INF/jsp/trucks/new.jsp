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
        <c:if test="${not empty statusMsg}">
            <div><strong><c:out value="${statusMsg}"/></strong></div>
        </c:if>
    <h2>New Truck</h2>
    <div>
        <form:form action="/trucks/new" method="post" modelAttribute="truck">
            <table>
                <tr>
                    <td>Registration Number:</td>
                    <td><form:input path="regNumber" pattern="^[A-Z][A-Z][0-9]{5}$" required="true"/></td>
                    <td><form:errors path="regNumber"/></td>
                </tr>
                <tr>
                    <td>Crew size:</td>
                    <td><form:input path="crew" pattern="[1-9]{1}" required="true"/></td>
                    <td><form:errors path="crew"/></td>
                </tr>
                <tr>
                    <td>Load capacity:</td>
                    <td><form:input path="capacity" type="number" min="0" max="100" required="true"/></td>
                    <td><form:errors path="capacity"/></td>
                </tr>
                <tr>
                    <td>Vehicle state:</td>
                    <td><form:select path="active">
                        <form:option value="true" label="active" selected="true"/>
                        <form:option value="false" label="broken"/>
                    </form:select>
                    </td>
                    <td><form:errors path="active"/></td>

                </tr>
                <tr>
                    <td>Current city:</td>
                    <td><form:select path="cityCode">
                        <form:option value="" label="--- No city ---"/>
                        <form:options items="${cityList}" itemValue="code" itemLabel="name"/>
                    </form:select>
                    </td>
                    <td><form:errors path="cityName"/></td>

                </tr>
                <tr>
                    <td colspan="1">
                        <input type="submit" value="Add"/>
                    </td>
                    <td colspan="1">
                        <input type="reset" value="Reset"/>
                    </td>
                </tr>
            </table>
        </form:form>
    </div>
    <%-- pageBody --%>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>