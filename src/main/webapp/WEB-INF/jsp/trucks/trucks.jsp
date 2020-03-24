<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Trucks</title>
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
    <h2>All Trucks</h2>
    <div>
        <a href="<c:url value="/trucks/new"/>">New truck</a>
    </div>
    <div>
        <c:if test="${!empty trucks}">
            <table>
                <thead>
                <tr>
                    <th>Registration Number</th>
                    <th>Crew size</th>
                    <th>Load capacity</th>
                    <th>Vehicle state</th>
                    <th>Current city</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="truck" items="${trucks}">
                    <tr>
                        <td class="center">${truck.regNumber}</td>
                        <td class="center">${truck.crew}</td>
                        <td class="center">${truck.capacity}</td>
                        <td class="center">${truck.active == true ? "active":"broken"}</td>
                        <td class="center">${truck.cityName == null ? "no city" : truck.cityName}</td>
                        <td class="center">
                            <a href="/trucks/edit/${truck.regNumber}">Edit</a>
                            <a href="/trucks/delete/${truck.regNumber}">Delete</a>
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