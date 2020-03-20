<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Cargo</title>
    <c:import url="../head.jsp"/>
</head>
<body>
<c:import url="../header.jsp"/>
<div>
<%-- pageBody --%>
    <h2>All Cargo</h2>
    <div>
        <c:if test="${!empty cargoList}">
            <table>
                <thead>
                <tr>
                    <th>Number</th>
                    <th>Title</th>
                    <th>Weight</th>
                    <th>Status</th>
                    <th>Departure City</th>
                    <th>Destination City</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="cargo" items="${cargoList}">
                    <tr>
                        <td class="center">${cargo.number}</td>
                        <td class="center">${cargo.title}</td>
                        <td class="center">${cargo.weight}</td>
                        <td class="center">${cargo.status}</td>
                        <td class="center">${cargo.cityFromName}</td>
                        <td class="center">${cargo.cityToName}</td>
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