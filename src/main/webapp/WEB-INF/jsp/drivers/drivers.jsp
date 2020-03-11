<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Drivers</title>
    <c:import url="../head.jsp"/>
</head>
<body>
<c:import url="../header.jsp"/>
<div>
<%-- pageBody --%>
    <div>
        <a href="/drivers/new">New driver</a>
    </div>
    <br/>
    <br/>
    <div>
        <c:if test="${!empty drivers}">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Patronymic Name</th>
                    <th>Status</th>
                    <th>Hours</th>
                    <th>City</th>
                    <th>Current Truck</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="driver" items="${drivers}">
                    <tr>
                        <td class="center">${driver.id}</td>
                        <td class="center">${driver.firstName}</td>
                        <td class="center">${driver.lastName}</td>
                        <td class="center">${driver.patronymicName}</td>
                        <td class="center">${driver.status}</td>
                        <td class="center">${driver.hours}</td>
                        <td class="center">${driver.city.name}</td>
                        <td class="center">${driver.truck.regNumber}</td>
                        <td class="center">
                            <a href="/drivers/edit/${driver.id}">Edit</a>
                            <a href="/drivers/delete/${driver.id}">Delete</a>
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