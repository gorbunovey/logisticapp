<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav>
    <div>
        <sec:authorize access="hasRole('DRIVER')">
            <a href="/driver">Details</a>
        </sec:authorize>
        <sec:authorize access="hasRole('STUFF')">
            <a href="/drivers">Drivers</a>
            <a href="/trucks">Trucks</a>
            <a href="/orders">Orders</a>
            <a href="/cargo">Cargo</a>
        </sec:authorize>
        <sec:authorize access="hasRole('ADMIN')">
            <a href="/admin/accounts">Accounts</a>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <a href="/account">Account</a>
            <a href="/logout">Logout</a>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <a href="/index">Index</a>
            <a href="/about">About</a>
            <a href="/login">Login</a>
            <a href="/registration">Registration</a>
        </sec:authorize>
        <%-- Greeting --%>
        <span>Hello,&nbsp
                <sec:authorize access="isAuthenticated()">
                    <sec:authentication property="principal.firstName"/>!
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                    Guest!
                </sec:authorize>
        </span>
    </div>
    <br><br>
</nav>
