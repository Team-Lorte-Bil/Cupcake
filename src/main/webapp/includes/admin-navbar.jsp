<%@ page contentType="text/html; charset=UTF-8" %>
<div class="container">
    <nav class="navbar navbar-expand-md navbar-light bg-light">
        <div class="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}">Menu</a>
                </li>
            </ul>
        </div>

        <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
            <ul class="navbar-nav ml-auto">
                <c:forEach var="i" items="${requestScope.navbar.items}">
                    <c:choose>
                        <c:when test="${i.url == 'nolink'}">
                            <span class="navbar-text">
                                    ${sessionScope.currentUser.email} (Saldo: ${sessionScope.currentUser.accountBalance} kr) |
                            </span>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item <c:if test="${i.active}">active</c:if>">
                                <a class="nav-link" href="<c:url value="${i.url}"/>">${i.name} <c:if test="${i.active}"><span
                                        class="sr-only">(current)</span></c:if></a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/Cart"><i class="fa fa-shopping-cart"></i></a>
                </li>

            </ul>
        </div>
    </nav>
</div>