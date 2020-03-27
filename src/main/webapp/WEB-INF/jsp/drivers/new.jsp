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
        <c:if test="${not empty statusMsg}">
            <div><strong><c:out value="${statusMsg}"/></strong></div>
        </c:if>
    <h2>New Driver</h2>
    <div>
        <form:form action="/drivers/new" method="post" modelAttribute="driver">
            <table>
                <tr>
                    <td>Driver Number:</td>
                    <td><form:input path="number" pattern = "^([0-9]|([1-9][0-9]{1,8}))$" required="true"/></td>
                    <td><form:errors path="number"  /></td>
                </tr>
                <tr>
                    <td>Current city:</td>
                    <td><form:select path="cityCode">
                        <form:option value="" label="--- No city ---"/>
                        <form:options items="${cityList}" itemValue="code" itemLabel="name"/>
                    </form:select>
                    </td>
                    <td><form:errors path="cityCode"/></td>
                </tr>
                <tr>
                    <td>User:</td>
                    <td><form:select path="userId">
                        <form:options items="${userList}" itemValue="id" itemLabel="email"/>
                    </form:select>
                    </td>
                    <td><form:errors path="userId"/></td>

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
    <%-- pageBody --%>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>
