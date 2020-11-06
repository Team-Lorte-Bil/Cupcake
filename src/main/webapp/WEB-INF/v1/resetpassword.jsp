<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<div class="container text-center">
    <h3>Nulstil kodeord</h3>
    <div class="row">
        <div class="col-sm">
        </div>
        <div class="col-sm">
            <form action="Reset" method="POST">
                <div class="form-group">
                    <label for="inputEmail">E-mail addresse</label>
                    <input type="email" class="form-control" id="inputEmail" name="inputEmail" placeholder="Indtast din e-mail...">

                </div>
                <c:if test="${requestScope.msg}">
                    <div class="alert alert-success" role="alert">
                            ${requestScope.msgString}
                    </div>
                </c:if>
                <c:if test="${requestScope.error}">
                    <div class="alert alert-danger" role="alert">
                            ${requestScope.errorMsg}
                    </div>
                </c:if>

                <input type="submit" value="Nulstil kodeord" class="btn btn-dark rounded-pill py-2 btn-block" />
            </form>
        </div>
        <div class="col-sm">
        </div>
    </div>
</div>