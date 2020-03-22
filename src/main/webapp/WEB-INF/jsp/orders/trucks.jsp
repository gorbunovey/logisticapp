<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>New Order truck choosing</title>
    <c:import url="../head.jsp"/>
</head>
<body>
<c:import url="../header.jsp"/>
<div>
    <%-- pageBody --%>
    <h1>New Order - step 2. Truck choosing</h1>
    <%-- message from redirect flash attribute  --%>
    <c:if test="${not empty statusMsg}">
        <div><strong><c:out value="${statusMsg}"/></strong></div>
    </c:if>
    <%-- message from redirect flash attribute  --%>
    <div><strong> Max mass: <c:out value="${sessionScope.maxMass}"/></strong></div>
    <h2>Available trucks</h2>
        <div>
            <c:if test="${!empty trucks}">
                <form:form method="post">
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
                            <td class="center">${truck.cityName}</td>
                            <td class="center">
                                <input type="submit" formaction="/orders/new/trucks/${truck.regNumber}" value="Choose"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <%-- Pagination --%>
                </form:form>
            </c:if>
        </div>
    <%-- pageBody --%>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>