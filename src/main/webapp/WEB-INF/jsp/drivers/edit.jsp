<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Driver</title>
    <c:import url="../head.jsp"/>
</head>
<body>
<c:import url="../header.jsp"/>
<div>
    <%-- pageBody --%>
    <h2> Edit Driver # ?</h2>
    <div>
        <c:if test="${!empty driver}">
            <c:choose>
                <c:when test="${!empty driver}">
                    <form>
                        <c:out value="${driver}"/>
                        <!-- Some form which one for edit and show -->
                    </form>
                </c:when>
                <c:otherwise>

                </c:otherwise>
            </c:choose>


        </c:if>
        <c:if test="${empty driver}">
            <p>No such driver</p>
        </c:if>
    </div>
    <%-- pageBody --%>
    <c:import url="../footer.jsp"/>
</div>
</body>
</html>