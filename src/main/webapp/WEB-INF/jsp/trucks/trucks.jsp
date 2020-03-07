<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trucks</title>
    <c:import url="../head.jsp"/>
</head>
<body>
<c:import url="../header.jsp"/>
<div>
    <%-- pageBody --%>
        <div>
            <a href="/trucks/new">New truck</a>
        </div>
        <br/>
        <br/>
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
                            <td class="center">${truck.crewSize}</td>
                            <td class="center">${truck.capacity}</td>
                            <td class="center">${truck.state}</td>
                            <td class="center">${truck.city}</td>
                            <td class="center">
                                <a href="/trucks/${truck.regNumber}">Edit</a>
                                <a href="/trucks/delete/${truck.regNumber}">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    <%-- Pagination --%>
    <%-- pageBody --%>
    <c:import url="../footer.jsp"/>
</div>
</body>
</html>
