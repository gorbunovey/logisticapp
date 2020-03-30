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
    <h2>Account</h2>
    <div>
        <c:choose>
            <c:when test="${!empty user}">
                <form:form action="/account" method="post" modelAttribute="user">
                    <form:hidden path="id" />
                    <table>
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
                            <td>Email:</td>
                            <td><form:input path="email" type="email"/></td>
                            <td><form:errors path="email" /></td>
                        </tr>
                        <tr>
                            <td>New password:</td>
                            <td><form:password path="password" /></td>
                            <td><form:errors path="password" /></td>
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
                <c:redirect url = "/index"/>
            </c:otherwise>
        </c:choose>
    </div>
    <%-- pageBody --%>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>