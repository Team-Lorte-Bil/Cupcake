<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-dark navbar-expand-md bg-dark navigation-clean-button" style="font-family: Raleway, sans-serif;font-weight: 800;">
    <div class="container">
        <button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1">
            <span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navcol-1">
            <ul class="nav navbar-nav mr-auto">
                <li class="nav-item"><a class="nav-link " href="${pageContext.request.contextPath}">Startside</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/Shop">Menukort</a></li>
            </ul>
            <span class="navbar-text actions">
                <c:forEach var="i" items="${requestScope.navbar.items}">
                    <c:choose>
                        <c:when test="${i.url == 'nolink'}">
                            <span class="navbar-text">
                                    ${sessionScope.currentUser.email} (Saldo: ${sessionScope.currentUser.accountBalance} kr) |
                            </span>
                        </c:when>
                        <c:otherwise>
                            <a class="login" href="<c:url value="${i.url}"/>">${i.name}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <a class="login" href="${pageContext.request.contextPath}/Cart"><i class="fa fa-shopping-cart"></i></a>
            </span>
        </div>
    </div>
</nav>