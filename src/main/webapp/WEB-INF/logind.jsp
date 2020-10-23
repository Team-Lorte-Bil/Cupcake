<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container text-center">
    <h3>Log ind her!</h3>
    <div class="row">
        <div class="col-sm">
        </div>
        <div class="col-sm">
            <form>
                <div class="form-group">
                    <label for="exampleInputEmail1">E-mail addresse</label>
                    <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Indtast din e-mail...">

                </div>
                <div class="form-group">
                    <label for="exampleInputPassword1">Kodeord</label>
                    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Indtast dit kodeord...">
                </div>
                <button type="submit" class="btn btn-primary" aria-describedby="emailHelp">Log ind</button>

                <a href="${pageContext.request.contextPath}/Reset">
                    <small id="emailHelp" class="form-text text-muted">Har du glemt dit kodeord? Tryk her</small>
                </a>
            </form>
        </div>
        <div class="col-sm">
        </div>
    </div>
</div>