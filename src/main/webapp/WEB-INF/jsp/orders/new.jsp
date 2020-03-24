<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>New Order confirmation</title>
    <c:import url="../head.jsp"/>
</head>
<body>
<c:import url="../header.jsp"/>
<div>
    <%-- pageBody --%>
    <h1>New Order confirmation</h1>
    <c:if test="${(!empty sessionScope.wayPoints) and (!empty sessionScope.chosenTruck) }">
        <h1>
            <div>
                <c:set var="lastStop" value="${sessionScope.chosenTruck.cityName}" scope="page"/>
                <c:out value="${lastStop}"/>
                <c:forEach var="point" items="${sessionScope.wayPoints}">
                    <c:set var="newStop"
                           value="${point.type == true ? point.cargo.cityFromName : point.cargo.cityToName}"
                           scope="page"/>
                    <c:if test="${lastStop != newStop}">
                        <c:set var="lastStop" value="${newStop}" scope="page"/>
                        &nbsp;-->&nbsp;<c:out value="${lastStop}"/>
                    </c:if>
                </c:forEach>
            </div>
        </h1>
    </c:if>
    <div>
        <a href="<c:url value="/orders/new/drivers"/>">Back</a>
    </div>
    <h2>Shipment list</h2>
    <div>
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
                <c:set var="seqNumber" value="0" scope="page"/>
                <c:forEach var="point" items="${sessionScope.wayPoints}">
                    <tr>
                        <td class="center">
                            <c:set var="seqNumber" value="${seqNumber + 1}" scope="page"/>
                            <c:out value="${seqNumber}"/>
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
                        Maximum mass = <c:out value="${sessionScope.maxMass}"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </c:if>
    </div>
    <h2>Truck</h2>
    <div>
        <c:if test="${!empty sessionScope.chosenTruck}">
            <table>
                <thead>
                <tr>
                    <th>Registration Number</th>
                    <th>Crew size</th>
                    <th>Load capacity</th>
                    <th>Vehicle state</th>
                    <th>Current city</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td class="center">${sessionScope.chosenTruck.regNumber}</td>
                    <td class="center">${sessionScope.chosenTruck.crew}</td>
                    <td class="center">${sessionScope.chosenTruck.capacity}</td>
                    <td class="center">${sessionScope.chosenTruck.active == true ? "active":"broken"}</td>
                    <td class="center">${sessionScope.chosenTruck.cityName == null ? "no city" : sessionScope.chosenTruck.cityName}</td>
                </tr>
                </tbody>
            </table>
        </c:if>
    </div>
    <h2>Drivers</h2>
    <div>
        <c:if test="${!empty sessionScope.chosenDrivers}">
            <table>
                <thead>
                <tr>
                    <th>Number</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Status</th>
                    <th>Hours</th>
                    <th>Current City</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="driver" items="${sessionScope.chosenDrivers}">
                    <tr>
                        <td class="center">${driver.number}</td>
                        <td class="center">${driver.userFirstName}</td>
                        <td class="center">${driver.userLastName}</td>
                        <td class="center">${driver.status}</td>
                        <td class="center">${driver.hours}</td>
                        <td class="center">${driver.cityName}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    <br/>
    <h1>
        <c:if test="${(!empty sessionScope.wayPoints) and (!empty sessionScope.chosenTruck) and (!empty sessionScope.chosenDrivers)}">
            <div>
                <form:form method="post">
                    <input type="submit" formaction="/orders/new/add" value="Add"/>
                </form:form>
            </div>
            <br/>
        </c:if>
    </h1>
    <%-- pageBody --%>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>