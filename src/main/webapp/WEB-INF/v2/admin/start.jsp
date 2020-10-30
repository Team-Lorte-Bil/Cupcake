<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="page-section cta" style="background: rgba(109,67,73,0.74);">
    <div class="container">
        <div class="row">
            <div class="col-xl-9 mx-auto"><div class="cta-inner text-center rounded">
                <h2 class="section-heading mb-5"><span class="section-heading-lower">Oversigt</span></h2>
                <div class="row">
                    <div class="col">
                        <div class="card text-white bg-primary mb-4" style="max-width: 18rem;">
                            <div class="card-header">Antal ordre</div>
                            <div class="card-body">
                                <h5 class="card-title">${requestScope.countOrders}</h5>
                            </div>
                        </div>
                    </div>
                    <div class="col">
                        <div class="card text-white bg-warning mb-4" style="max-width: 18rem;">
                            <div class="card-header">Antal kunder</div>
                            <div class="card-body">
                                <h5 class="card-title">${requestScope.countCustomers}</h5>
                            </div>
                        </div>
                    </div>
                    <div class="col">
                        <div class="card text-white bg-success mb-4" style="max-width: 18rem;">
                            <div class="card-header">Total omsætning</div>
                            <div class="card-body">
                                <h5 class="card-title">${requestScope.totalSale} kr</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div></div>
        </div>
    </div>
</section>
<section class="page-section about-heading">
    <div class="container">
        <div class="about-heading-content"></div>
    </div>
</section>