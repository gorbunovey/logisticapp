<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Drivers</title>
    <c:import url="../head.jsp"/>
</head>
<body>
<c:import url="../header.jsp"/>
<div>
<%-- pageBody --%>
    <%-- message from redirect flash attribute  --%>
    <c:if test="${not empty statusMsg}">
        <div><strong><c:out value="${statusMsg}"/></strong></div>
    </c:if>
    <%-- message from redirect flash attribute  --%>
    <h2>All Drivers</h2>
    <div>
        <a href="<c:url value="/drivers/new"/>">New driver</a>
    </div>
    <div>
        <c:if test="${!empty drivers}">
            <table>
                <thead>
                <tr>
                    <th>Number</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Status</th>
                    <th>Hours</th>
                    <th>Current City</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="driver" items="${drivers}">
                    <tr>
                        <td class="center">${driver.number}</td>
                        <td class="center">${driver.userFirstName}</td>
                        <td class="center">${driver.userLastName}</td>
                        <td class="center">${driver.status}</td>
                        <td class="center">${driver.hours}</td>
                        <td class="center">${driver.cityName}</td>
                        <td class="center">
                            <a href="/drivers/edit/${driver.number}">Edit</a>
                            <a href="/drivers/delete/${driver.number}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <%-- Pagination --%>
        </c:if>
    </div>
<%-- pageBody --%>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>