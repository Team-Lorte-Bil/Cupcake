<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<section class="page-section cta" style="background: rgba(109,67,73,0.74);">
<div class="container">
<div class="row">
<div class="col-xl-9 mx-auto">
<div class="cta-inner text-center rounded">
<h2 class="section-heading mb-5"><span class="section-heading-lower">Din ordre ${requestScope.order.orderId} er bekræftet</span></h2><div class="shopping-cart">
<div class="px-4 px-lg-0">

<div class="pb-5">
<div class="container">
<div class="row">
<div class="col-lg-12 p-5 bg-white rounded shadow-sm mb-5">


<!-- Shopping cart table -->
<div class="table-responsive">
<table class="table">
<thead>
<tr>
    <th scope="col" class="border-0 bg-light">
        <div class="p-2 px-3 text-uppercase">Bund</div>
    </th>
    <th scope="col" class="border-0 bg-light">
        <div class="py-2 text-uppercase">Top</div>
    </th>
    <th scope="col" class="border-0 bg-light">
        <div class="py-2 text-uppercase">Antal</div>
    </th>
    <th scope="col" class="border-0 bg-light">
        <div class="py-2 text-uppercase">Linjepris</div>
    </th>
</tr>
</thead>
<tbody>
<c:forEach items="${requestScope.cart.cakes}" var="cake">
    <tr>
        <td class="border-1 align-middle"><strong>${cake.cake.bottom}</strong></td>
        <td class="border-1 align-middle"><strong>${cake.cake.topping}</strong></td>
        <td class="border-1 align-middle"><strong>${cake.amount}</strong></td>
        <td class="border-1 align-middle"><strong>${cake.cake.price * cake.amount} kr</strong></td>
    </tr>
    <!-- end of foreach -->
</c:forEach>

    </tbody>

    </table>
    </div>
    <!-- End -->
    </div>
    <c:choose>
        <c:when test="${requestScope.order.paid}">
            <div>
                <p>
                    Ordren er betalt!
                </p>
            </div>
        </c:when>
        <c:otherwise>
            <div>
                <p>
                    Ordren er ikke betalt!
                </p>
            </div>
        </c:otherwise>
    </c:choose>
    </div>
    <a href="${pageContext.request.contextPath}">
    <button type="button" class="btn btn-dark rounded-pill py-2 btn-block">Gå til forsiden</button>
    </a>
    </div>
    </div>
    </div>
    </div>
    <p class="address mb-5"><em></em></p>
    <p class="address mb-0"><small></small></p>
    </div>
    </div>
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