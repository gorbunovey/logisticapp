<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>New Order - shipment</title>
    <c:import url="../head.jsp"/>
</head>
<body>
<c:import url="../header.jsp"/>
<div>
    <%-- pageBody --%>
    <h1>New Order - step 1. Shipment list</h1>
    <%-- message from redirect flash attribute  --%>
    <c:if test="${not empty statusMsg}">
        <div><strong><c:out value="${statusMsg}"/></strong></div>
    </c:if>
    <%-- message from redirect flash attribute  --%>

    <h2>Shipment list</h2>
        <div>
            <c:if test="${empty sessionScope.wayPoints}">
                <div>Please, choose the first cargo to load</div>
            </c:if>
            <c:if test="${!empty sessionScope.wayPoints}">
                <div>
                    <a href="<c:url value="/orders/new"/>">New List</a>
                </div>
                <form:form method="post">
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
                            <th>Accumulated mass</th>
                            <th>Max mass</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:set var="seqNumber" value="0" scope="page" />
                        <c:set var="accumulatedMass" value="0" scope="page" />
                        <c:set var="maxMass" value="0" scope="page" />
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

                                <td class="center">
                                    <c:choose>
                                    <c:when test="${point.type}">
                                        <c:set var="accumulatedMass" value="${accumulatedMass + point.cargo.weight}" scope="page"/>
                                        <c:out value = "${accumulatedMass}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="accumulatedMass" value="${accumulatedMass - point.cargo.weight}" scope="page"/>
                                        <c:out value = "${accumulatedMass}"/>
                                    </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="center">
                                    <c:if test="${accumulatedMass > maxMass}">
                                        <c:set var="maxMass" value="${accumulatedMass}" scope="page" />
                                    </c:if>
                                    <c:out value = "${maxMass}"/>
<%--                                        ${sessionScope.maxMass}--%>
                                </td>
                                <td class="center">
                                    <input type="submit" formaction="/orders/new/shipment/${seqNumber}" name="delete" value="Delete"/>
                                    <c:if test="${point.cargo.status == 'load'}">
                                        <input type="submit" formaction="/orders/new/shipment/${point.cargo.number}" name="unload" value="Unload"/>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                        <c:if test="${(!empty sessionScope.wayPoints) && (accumulatedMass == 0)}">
                            <div>
                                <a href="<c:url value="/orders/new/trucks"/>">Choose truck</a>
                            </div>
                        </c:if>
                </form:form>
            </c:if>
        </div>
        <h2>Available cargo</h2>
        <div>
        <c:if test="${!empty cargoList}">
            <form:form method="post">
            <table>
                <thead>
                <tr>
                    <th>Number</th>
                    <th>Title</th>
                    <th>Weight</th>
                    <th>Status</th>
                    <th>Departure City</th>
                    <th>Destination City</th>
                    <th>Action</th>
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
                        <td class="center">
                            <input type="submit" formaction="/orders/new/shipment/${cargo.number}" name="load" value="Load"/>
<%--                            <input type="submit" formaction="/orders/new/shipment/${cargo.number}" name="unload" value="Unload"/>--%>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            </form:form>
            <%-- Pagination --%>
        </c:if>
    </div>
    <%-- pageBody --%>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>