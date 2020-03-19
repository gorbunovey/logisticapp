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
        <c:if test="${not empty statusMsg}">
            <div><strong><c:out value="${statusMsg}"/></strong></div>
        </c:if>
    <h2>Edit Driver ID#<c:out value="${pathNumber}"/></h2>
    <div>
        <c:choose>
            <c:when test="${!empty driver}">
                <form:form action="/drivers/edit/${pathNumber}" method="post" modelAttribute="driver">
                    <table>
                        <tr>
                            <td>Driver Number:</td>
                            <td><form:input path="number" /></td>
                            <td><form:errors path="number" /></td>
                        </tr>
                        <tr>
                            <td>Current city:</td>
                            <td><form:select path="cityCode">
                                <form:option value="" label="--- No city ---" />
                                <form:options items="${cityList}" itemValue="code" itemLabel="name" />
                            </form:select>
                            </td>
                            <td><form:errors path="cityCode"/></td>
                        </tr>
                        <tr>
                            <td>User:</td>
                            <td><form:select path="userNumber">
                                <form:option value="${driver.userNumber}" label="${driver.userEmail}" />
                                <form:options items="${userList}" itemValue="number" itemLabel="email"/>
                            </form:select>
                                <form:hidden path="userEmail" />
                            </td>
                            <td><form:errors path="userNumber"/></td>

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
                <div>No such driver with ID#<c:out value="${pathNumber}"/></div>
            </c:otherwise>
        </c:choose>
    </div>
    <%-- pageBody --%>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>