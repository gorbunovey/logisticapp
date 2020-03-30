<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Delete Account</title>
    <c:import url="../head.jsp"/>
</head>
<body>
<c:import url="../header.jsp"/>
<div>
    <%-- pageBody --%>
    <h2>Delete Account#<c:out value="${pathId}"/></h2>
    <div>
        <c:choose>
            <c:when test="${!empty account}">
                <form:form action="/admin/account/delete/${account.id}" method="post">
                    <table>
                        <tr>
                            <td>Id:</td>
                            <td>${account.id}</td>
                        </tr>
                        <tr>
                            <td>First Name:</td>
                            <td>${account.firstName}</td>
                        </tr>
                        <tr>
                            <td>Last Name:</td>
                            <td>${account.lastName}</td>
                        </tr>
                        <tr>
                            <td>Email:</td>
                            <td>${account.email}</td>

                        </tr>
                        <tr>
                            <td>Role:</td>
                            <td>${account.roleName}</td>
                        </tr>
                        <tr>
                            <td colspan="1">
                                <input type="submit" value="Delete"/>
                            </td>
                        </tr>
                    </table>
                </form:form>
            </c:when>
            <c:otherwise>
                <h2>?</h2>
                <div>No such account with ID#<c:out value="${pathId}"/></div>
            </c:otherwise>
        </c:choose>
    </div>
    <%-- pageBody --%>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>
