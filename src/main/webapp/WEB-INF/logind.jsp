<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<div class="container text-center">
    <h3>Log ind her!</h3>
    <div class="row">
        <div class="col-sm">
        </div>
        <div class="col-sm">
            <form action="Login" method="POST">
                <div class="form-group">
                    <label for="inputEmail">E-mail addresse</label>
                    <input type="email" class="form-control" id="inputEmail" name="inputEmail" placeholder="Indtast din e-mail...">

                </div>
                <div class="form-group">
                    <label for="inputPassword">Kodeord</label>
                    <input type="password" class="form-control" id="inputPassword" name="inputPassword" placeholder="Indtast dit kodeord...">
                </div>
                <c:if test="${requestScope.error}">
                <div class="alert alert-danger" role="alert">
                    ${requestScope.errorMsg}
                </div>
                </c:if>

                <input type="submit" value="Log ind" class="btn btn-primary" />

                <a href="${pageContext.request.contextPath}/Reset">
                    <small id="emailHelp" class="form-text text-muted">Har du glemt dit kodeord? Tryk her</small>
                </a>
            </form>
        </div>
        <div class="col-sm">
        </div>
    </div>
</div>