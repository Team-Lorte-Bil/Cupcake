<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container text-center">
    <h3>Administrator side</h3>
    <br/> <br/>

    <div class="row">
        <div class="col">
            <div class="card text-white bg-primary mb-3" style="max-width: 18rem;">
                <div class="card-header">Antal ordre</div>
                    <div class="card-body">
                    <h5 class="card-title">${requestScope.countOrders}</h5>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card text-white bg-warning mb-3" style="max-width: 18rem;">
                <div class="card-header">Antal kunder</div>
                <div class="card-body">
                    <h5 class="card-title">${requestScope.countCustomers}</h5>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card text-white bg-success mb-3" style="max-width: 18rem;">
                <div class="card-header">Total omsætning</div>
                <div class="card-body">
                    <h5 class="card-title">${requestScope.totalSale} kr</h5>
                </div>
            </div>
        </div>
    </div>
</div>