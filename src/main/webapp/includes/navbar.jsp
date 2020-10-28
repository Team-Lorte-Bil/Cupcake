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
                <li class="nav-item">
                    ${sessionScope.currentUser.email}
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/Register">Opret konto</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/Login">Log ind</a>
                </li>
                <li class="nav-item">

                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/Cart"><i class="fa fa-shopping-cart"></i></a>
                </li>
            </ul>
        </div>
    </nav>
</div>