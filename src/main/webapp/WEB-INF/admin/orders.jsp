<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container text-center">
    <h3>Ordre</h3>
    <br/> <br/>
    <table class="table">
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
            <c:if test="${order.key.paid}"><tr class="table-success"></c:if>
            <c:if test="${!order.key.paid}"><tr class="table-warning"></c:if>
            <c:if test="${order.key.completed}"><tr class="table-active"></c:if>
                <td><a href="#" data-toggle="modal" data-target="#modalChangeBalance${vs.index}">${order.key.orderId}</a></td>
                <td>${order.key.user.name}</td>
                <td>${order.key.user.phoneno}</td>
                <td>${order.value}</td>
                <td>${order.key.paid}</td>
                <td>
                    <c:choose>
                        <c:when test="${order.key.completed}">
                            Ordren er blevet afhentet.
                        </c:when>
                        <c:otherwise>
                            <div class="btn-group" role="group">
                                <form action="AdminOrders" method="post">
                                    <input type="hidden" name="action" value="markDone">
                                    <input type="hidden" name="orderId" value="${order.key.orderId}">
                                    <input type="submit" class="btn btn-success" value="Marker som færdig" <c:if test="${order.key.completed}">disabled</c:if>>
                                </form>

                                <form action="AdminOrders" method="post">
                                    <input type="hidden" name="action" value="deleteOrder">
                                    <input type="hidden" name="orderId" value="${order.key.orderId}">
                                    <input type="submit" class="btn btn-danger" value="Slet ordre" <c:if test="${order.key.completed}">disabled</c:if>>
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
                    <h5 class="modal-title">Ordre nummer ${order.key.orderId}</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body" style="overflow-x: auto;!important">
                    <div id="custinfo">
                        <p>Kunde: ${order.key.user.name}</p>
                        <p>Mail: ${order.key.user.email}</p>
                        <p>Telefon: ${order.key.user.phoneno}</p>
                        <p>Ordretidspunkt: ${order.key.timestamp.toLocaleString()}</p>
                        <p>Kommentar: ${order.key.comment}</p>
                    </div>
                    <div id="custitems">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th scope="col">Antal</th>
                                <th scope="col">Bund</th>
                                <th scope="col">Fyld</th>
                                <th scope="col">Linjepris</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${order.key.cakes}" var="cake">
                                <tr>
                                    <td>${cake.value}</td>
                                    <td>${cake.key.bottom}</td>
                                    <td>${cake.key.topping}</td>
                                    <td>${cake.key.price * cake.value} kr</td>
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
</c:forEach>