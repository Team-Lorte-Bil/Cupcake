<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<section class="page-section cta" style="background: rgba(109,67,73,0.74);">
    <div class="container">
        <div class="row">
            <div class="col-xl-9 mx-auto"><span class="text-uppercase d-xl-flex justify-content-xl-center section-heading-lower" style="font-size: 44px;font-weight: 800;color: rgb(255,255,255);">Opret ny konto</span><div class="login-card">
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

                    <input type="submit" value="Opret bruger" class="btn btn-dark rounded-pill py-2 btn-block" />

                </form>
            </div></div>
        </div>
    </div>
</section>
<section class="page-section about-heading">
    <div class="container"><img class="img-fluid rounded about-heading-img mb-3 mb-lg-0" src="assets/img/bornholm.jpg" alt="billede af bornholm">
        <div class="about-heading-content">
            <div class="row">
                <div class="col-lg-10 col-xl-9 mx-auto">
                    <div class="bg-faded rounded p-5">
                        <h2 class="section-heading mb-4"><span class="section-heading-upper">dybdeøkologisk iværksættereventyr fra Bornholm</span><span class="section-heading-lower">Mums filibaba</span></h2>
                        <p>Som ægte iværksættere startet vi virksomheden d. 21 December 2012 hvor vores sårede Jorden blev dømt til at gå under.</p>
                        <p class="mb-0"><span>Det skete imidlertidig ikke og derfor har vi stadig gang i biksen indtil Jorden en dag går under fordi folk stadig dræber dyr for at få aftensmad.<br>Vores vision er at redde planeten ved at lave biodynamiske, økologiske, gluten-, sukker- og laktosefrie cupcakes<br></span><span></span></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>