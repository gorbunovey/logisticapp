<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>New Order - truck choosing</title>
    <c:import url="../head.jsp"/>
</head>
<body>
<c:import url="../header.jsp"/>
<div>
    <%-- pageBody --%>
    <h1>New Order - step 2. Truck choosing</h1>
    <c:if test="${!empty sessionScope.wayPoints}">
        <h1>
            <div>
                <c:set var="lastStop" value="${sessionScope.wayPoints.get(0).cargo.cityFromName}" scope="page" />
                <c:out value="${lastStop}"/>
                <c:forEach var="point" items="${sessionScope.wayPoints}">
                    <c:set var="newStop" value="${point.type == true ? point.cargo.cityFromName : point.cargo.cityToName}" scope="page" />
                    <c:if test="${lastStop != newStop}">
                        <c:set var="lastStop" value="${newStop}" scope="page" />
                        &nbsp;-->&nbsp;<c:out value="${lastStop}"/>
                    </c:if>
                </c:forEach>
            </div>
        </h1>
    </c:if>
    <div>
        <a href="<c:url value="/orders/new/shipment"/>">Back</a>
    </div>
    <%-- message from redirect flash attribute  --%>
    <c:if test="${not empty statusMsg}">
        <div><strong><c:out value="${statusMsg}"/></strong></div>
    </c:if>
    <%-- message from redirect flash attribute  --%>
    <h2>Shipment list</h2>
        <div>
            <c:if test="${empty sessionScope.wayPoints}">
                <div>
                Sorry, shipment list is empty. <br/>
                Try to make a new one
                </div>
                <div>
                    <a href="<c:url value="/orders/new"/>">New List</a>
                </div>
                <br/>
            </c:if>
            <c:if test="${!empty sessionScope.wayPoints}">
                <table>
                    <thead>
                    <tr>
                        <th>Sequence number</th>
                        <th>City</th>
                        <th>Process</th>
                        <th>№</th>
                        <th>Cargo</th>
                        <th>From</th>
                        <th>To</th>
                        <th>kg</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="seqNumber" value="0" scope="page" />
                    <c:forEach var="point" items="${sessionScope.wayPoints}">
                        <tr>
                            <td class="center">
                                <c:set var="seqNumber" value="${seqNumber + 1}" scope="page"/>
                                <c:out value = "${seqNumber}"/>
                            </td>
                            <td class="center">
                                    ${point.type == true ? point.cargo.cityFromName : point.cargo.cityToName}
                            </td>
                            <td class="center">
                                    ${point.type == true ? "loading" : "unloading"}
                            </td>
                            <td class="center">${point.cargo.number}</td>
                            <td class="center">${point.cargo.title}</td>
                            <td class="center">${point.cargo.cityFromName}</td>
                            <td class="center">${point.cargo.cityToName}</td>
                            <td class="center">${point.cargo.weight}</td>

                        </tr>
                    </c:forEach>
                    <tr>
                        <td rowspan="8" class="center">
                            Maximum mass = <c:out value = "${sessionScope.maxMass}"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </c:if>
        </div>
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