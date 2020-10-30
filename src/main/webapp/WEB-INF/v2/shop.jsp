<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section class="page-section cta" style="background: rgba(109,67,73,0.74);">
    <div class="container">
        <div class="row">
            <div class="col-xl-9 mx-auto"><div class="cta-inner text-center rounded">
                <h2 class="section-heading mb-5"><span class="section-heading-upper">Øens absolut bedste</span><span class="section-heading-lower">Byg din cupcake</span></h2>
                <form action="Cart" method="post">
                    <input type="hidden" name="action" value="add">

                    <div class="form-group"><label>Vælg bund:</label>
                        <select name="bund" id="bund">
                            <c:forEach items="${requestScope.bottoms.entrySet()}" var="bottom">
                                <option value="${bottom.key}">${bottom.key} - ${bottom.value}kr</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group"><label>Vælg topping:</label>
                        <select name="topping" id="topping">
                            <c:forEach items="${requestScope.toppings.entrySet()}" var="topping">
                                <option value="${bottom.key}">${topping.key} - ${topping.value}kr</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group"><label>Vælg antal:</label>

                        <select name="antal" id="antal">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>

                        </select>

                    </div>
                    <input type="submit" value="Tilføj til kurv" class="btn btn-primary" />
                </form>

                <a class="nav-link" href="<c:url value="/Cart"/>">
                    <input type="submit" value="Gå til betaling" class="btn btn-success" />

                </a>


                <p class="address mb-0"><small></small></p>
            </div></div>
        </div>
    </div>
</section>
<section class="page-section about-heading">
    <div class="container"><img class="img-fluid rounded about-heading-img mb-3 mb-lg-0" src="assets/img/bornholm.jpg">
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