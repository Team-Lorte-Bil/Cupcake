<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-dark navbar-expand-md bg-dark navigation-clean-button" style="font-family: Raleway, sans-serif;font-weight: 800;">
    <div class="container">
        <button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1">
            <span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navcol-1">
            <ul class="nav navbar-nav mr-auto">
                <li class="nav-item"><a class="nav-link " href="${pageContext.request.contextPath}/AdminStart">Start</a></li>
                <li class="nav-item"><a class="nav-link " href="${pageContext.request.contextPath}/AdminOrders">Ordre</a></li>
                <li class="nav-item"><a class="nav-link " href="${pageContext.request.contextPath}/AdminCustomers">Kunder</a></li>
                <li class="nav-item"><a class="nav-link " href="${pageContext.request.contextPath}/AdminItems">Produkter</a></li>
            </ul>
            <span class="navbar-text actions">
                <a class="login" href="${pageContext.request.contextPath}/Logout">Log ud</a>
            </span>
        </div>
    </div>
</nav>