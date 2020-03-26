<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Details</title>
    <c:import url="../head.jsp"/>
</head>
<body>
<c:import url="../header.jsp"/>
<div>
    <%-- pageBody --%>
    <h2>Driver <c:out value="${pathNumber}"/></h2>
    <div>
        <c:if test="${!empty driver}">
            <form:form method="post">
                <table>
                    <tr>
                        <td>Driver Number:</td>
                        <td>${driver.number}</td>
                    </tr>
                    <tr>
                        <td>Shift:</td>
                        <td>
                            <c:choose>
                                <c:when test="${driver.onShift}">
                                    <input type="submit" formaction="/driver/${driver.number}/shift/false"
                                           value="Finished"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="submit" formaction="/driver/${driver.number}/shift/true"
                                           value="Started"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <c:if test="${driver.onShift}">
                        <tr>
                            <td>Status:</td>
                            <td>${driver.status}</td>
                        </tr>
                        <tr>
                            <td>Set new status:</td>
                            <td>
                                <c:forEach var="status" items="${statuses}">
<%-- TODO: Когда отрефакторю OrderDTO - расскоментировать чтобы у водителя 1-местной фуры не было кнопки "второй водитель" --%>
<%--  и у водителя без заказа не было кнопки "погр-разр работы" --%>
<%--                                    <c:if test="${(order.truck.crew == 1 and status == statuses[1]) or (empty order and status == statuses[2])}">--%>
<%--                                      <<<continue>>>--%>
<%--                                    </c:if>--%>
                                    <c:if test="${driver.status != status}">
                                        <input type="submit" formaction="/driver/${driver.number}/status/${status}"
                                               value="${status}"/>
                                    </c:if>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${!empty order}">
                        <tr>
                            <td>Order Number:</td>
                            <td>${order.number}</td>
                        </tr>
                        <tr>
                            <td>Truck registration number:</td>
                            <td>${order.truckRegNumber}</td>
                        </tr>
                        <c:if test="${order.driversNumber.size() > 1}">
                        <tr>
                            <td>Co-drivers numbers:</td>
                            <td>
                                <c:forEach var="co_driver" items="${order.driversNumber}">
                                    <c:if test="${co_driver  != driver.number}">
                                        ${co_driver}
                                    </c:if>
                                </c:forEach>
                            </td>
                        </tr>
                        </c:if>
                    </c:if>
                </table>
            </form:form>
        </c:if>
    </div>
    <c:if test="${!empty order}">
        <h2>Order <c:out value="${order.number}"/> details</h2>
        <div>
            <form:form method="post">
                <table>
                    <thead>
                    <tr>
                        <th>No.</th>
                        <th>City</th>
                        <th>Cargo number</th>
                        <th>Cargo title</th>
                        <th>Cargo weight</th>
                        <th>Process</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="point" items="${order.wayPoints}">
                        <tr>
                            <td class="center">
                                <c:out value="${point.seqNumber}"/>
                            </td>
                            <td class="center">
                                    ${point.type == true ? point.cargo.cityFromName : point.cargo.cityToName}
                            </td>
                            <td class="center">${point.cargo.number}</td>
                            <td class="center">${point.cargo.title}</td>
                            <td class="center">${point.cargo.weight}</td>
                            <td class="center">
                                    ${point.type == true ? "Loading" : "Unloading"}
                            </td>
                            <td class="center">
                                <c:choose>
                                    <c:when test="${point.type}">
                                        <c:choose>
                                            <c:when test="${point.cargo.status == 'loaded'}">Done</c:when>
                                            <c:otherwise>
                                                <input type="submit"
                                                       formaction="/driver/${driver.number}/cargo/${point.cargo.number}/loaded"
                                                       value="Ok"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${point.cargo.status == 'unloaded'}">Done</c:when>
                                            <c:otherwise>
                                                <input type="submit"
                                                       formaction="/driver/${driver.number}/cargo/${point.cargo.number}/delivered"
                                                       value="Ok"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </form:form>
        </div>
    </c:if>
    <%-- pageBody --%>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>
