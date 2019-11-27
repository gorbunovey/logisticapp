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
        <c:if test="${!empty drivers}">
            <table>
                <thead>
                <tr>
                    <th>Drivers</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="driver" items="${drivers}">
                    <tr>
                        <td class="center">${driver}</td>
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