<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<div class="container text-center">
    <h3>Opret en konto her!</h3>
    <div class="row">
        <div class="col-sm">
        </div>
        <div class="col-sm">
            <form action="Register" method="POST">
                <div class="form-group">
                    <label for="inputName">Indtast dit navn</label>
                    <input type="name" class="form-control" id="inputName" name="inputName" placeholder="Indtast dit navn...">
                </div>

                    <div class="form-group">
                        <label for="inputEmail">Indtast din e-mail addresse</label>
                        <input type="email" class="form-control" id="inputEmail" name="inputEmail" placeholder="Indtast din e-mail...">
                    </div>

                <div class="form-group">
                    <label for="inputPhone">Indtast telefon nummer</label>
                    <input type="tel" class="form-control" id="inputPhone" name="inputPhone" placeholder="Indtast dit telefon nummer...">
                </div>

                <div class="form-group">
                    <label for="inputPsw">Indtast kodeord</label>
                    <input type="password" class="form-control" id="inputPsw" name="inputPsw" placeholder="Indtast dit kodeord...">
                </div>

                <input type="submit" value="Opret bruger" class="btn btn-primary" />

            </form>
        </div>
        <div class="col-sm">
        </div>
    </div>
</div>