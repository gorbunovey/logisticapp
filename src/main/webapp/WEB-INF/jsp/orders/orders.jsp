<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Orders</title>
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
    <h2>All Orders</h2>
    <div>
        <a href="<c:url value="/orders/new"/>">New Order</a>
    </div>
    <div>
        <c:if test="${!empty orderList}">
            <table>
                <thead>
                <tr>
                    <th>Number</th>
                    <th>Status</th>
                    <th>Truck</th>
                    <th>Drivers</th>
                    <th>Order Way Points</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="order" items="${orderList}">
                    <tr>
                        <td class="center">${order.number}</td>
                        <td class="center">${order.active == true ? "open":"closed"}</td>
                        <td class="center">${order.truckRegNumber}</td>
                        <td class="center">
                            <c:if test="${not empty order.driversNumber}">
                                <ul>
                                    <c:forEach var="number" items="${order.driversNumber}">
                                        <li>${number}</li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                        </td>
                        <td class="center">
                            <c:if test="${not empty order.wayPoints}">
                                <ul>
                                    <c:forEach var="wayPoint" items="${order.wayPoints}">
                                        <li>${wayPoint.seqNumber}</li>
                                        <li>${wayPoint.type}</li>
                                        <li>${wayPoint.cargo.number}</li>
                                        <li>${wayPoint.cargo.title}</li>
                                    </c:forEach>
                                </ul>
                            </c:if>
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