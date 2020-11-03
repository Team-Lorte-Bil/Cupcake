<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="page-section cta" style="background: rgba(109,67,73,0.74);">
    <div class="col-xl-9 mx-auto"><div class="cta-inner text-center rounded">
        <h2 class="section-heading mb-5"><span class="section-heading-lower">Ordre</span></h2>
        <table class="table" summary="table with orders">
            <thead>
            <tr>
                <th scope="col">Ordre nummer</th>
                <th scope="col">Kundenavn</th>
                <th scope="col">Telefon</th>
                <th scope="col">Samlet pris</th>
                <th scope="col">Betalt</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.orders}" var="order" varStatus="vs">
                <c:if test="${order.paid}"><tr class="table-success"></c:if>
                <c:if test="${!order.paid}"><tr class="table-warning"></c:if>
                <c:if test="${order.completed}"><tr class="table-active"></c:if>
                <td><a href="#" data-toggle="modal" data-target="#modalChangeBalance${vs.index}">${order.orderId}</a></td>
                <td>${order.user.name}</td>
                <td>${order.user.phoneno}</td>
                <td>${order.price}</td>
                <td>${order.paid}</td>
                <td>
                    <c:choose>
                        <c:when test="${order.completed}">
                            Ordren er blevet afhentet.
                        </c:when>
                        <c:otherwise>
                            <div class="btn-group" role="group">
                                <form action="AdminOrders" method="post">
                                    <input type="hidden" name="action" value="markDone">
                                    <input type="hidden" name="orderId" value="${order.orderId}">
                                    <input type="submit" class="btn btn-success" value="Marker som færdig" <c:if test="${order.completed}">disabled</c:if>>
                                </form>

                                <form action="AdminOrders" method="post">
                                    <input type="hidden" name="action" value="deleteOrder">
                                    <input type="hidden" name="orderId" value="${order.orderId}">
                                    <input type="submit" class="btn btn-danger" value="Slet ordre" <c:if test="${order.completed}">disabled</c:if>>
                                </form>
                            </div>
                        </c:otherwise>
                    </c:choose>

                </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>


        <c:forEach items="${requestScope.orders}" var="order" varStatus="vs">
            <!-- Show order details -->
            <div class="modal fade" id="modalChangeBalance${vs.index}" tabindex="-1" aria-labelledby="modalChangeBalanceLabel${vs.index}" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Ordre nummer ${order.orderId}</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body" style="overflow-x: auto;!important">
                            <div id="custinfo">
                                <p>Kunde: ${order.user.name}</p>
                                <p>Mail: ${order.user.email}</p>
                                <p>Telefon: ${order.user.phoneno}</p>
                                <p>Ordretidspunkt: ${order.timestamp.toLocaleString()}</p>
                                <p>Kommentar: ${order.comment}</p>
                            </div>
                            <div id="custitems">
                                <table class="table table-striped" summary="table with ordered items">
                                    <thead>
                                    <tr>
                                        <th scope="col">Antal</th>
                                        <th scope="col">Bund</th>
                                        <th scope="col">Fyld</th>
                                        <th scope="col">Linjepris</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${order.cakes}" var="cake">
                                        <tr>
                                            <td>${cake.amount}</td>
                                            <td>${cake.cake.bottom}</td>
                                            <td>${cake.cake.topping}</td>
                                            <td>${cake.cake.price * cake.amount} kr</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Luk</button>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach></div>
</section>
<section class="page-section about-heading">
    <div class="container">
        <div class="about-heading-content"></div>
    </div>
</section>