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
            <tr>
                <td><a href="#" data-toggle="modal" data-target="#modalChangeBalance${vs.index}">${order.key.orderId}</a></td>
                <td>${order.key.user.name}</td>
                <td>${order.key.user.phoneno}</td>
                <td>${order.value}</td>
                <td>${order.key.paid}</td>
                <td>
                    <div class="btn-group" role="group">
                        <c:choose>
                            <c:when test="${customer.admin}">
                                <form action="AdminOrders" method="post">
                                    <input type="submit" class="btn btn-success" value="Marker som færdig" disabled>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form action="AdminOrders" method="post">
                                    <input type="hidden" name="action" value="deleteUser">
                                    <input type="hidden" name="userId" value="${customer.id}">
                                    <input type="submit" class="btn btn-success" value="Marker som færdig">
                                </form>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${customer.admin}">
                                <form action="AdminOrders" method="post">
                                    <input type="submit" class="btn btn-danger" value="Slet ordre" disabled>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form action="AdminOrders" method="post">
                                    <input type="hidden" name="action" value="deleteUser">
                                    <input type="hidden" name="userId" value="${customer.id}">
                                    <input type="submit" class="btn btn-danger" value="Slet ordre">
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </td>
            </tr>
            <!-- Change balance modal -->
            <div class="modal fade" id="modalChangeBalance${vs.index}" tabindex="-1" aria-labelledby="modalChangeBalanceLabel${vs.index}" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Ændre saldo for</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <form action="AdminCustomers" method="POST">
                            <div class="modal-body">
                                <input type="hidden" name="action" value="changeBalance">
                                <input type="hidden" name="userId" value="${customer.id}">
                                <div class="form-group">
                                    <input type="number" class="form-control" id="newBalance" name="newBalance" placeholder="Indtast saldo (kr)" value="${customer.accountBalance}">
                                </div>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger" data-dismiss="modal">Annuller</button>
                                <button type="submit" class="btn btn-success">Opdater saldo</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- Create new user modal -->
<div class="modal fade" id="modalCreateUser" tabindex="-1" aria-labelledby="modalCreateUserLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Opret ny bruger</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="AdminCustomers" method="POST">
                <div class="modal-body">
                    <input type="hidden" name="action" value="createUser">
                    <div class="form-group">
                        <input type="name" class="form-control" id="inputName" name="inputName" placeholder="Indtast navn...">
                    </div>

                    <div class="form-group">
                        <input type="email" class="form-control" id="inputEmail" name="inputEmail" placeholder="Indtast e-mail...">
                    </div>

                    <div class="form-group">
                        <input type="tel" class="form-control" id="inputPhone" name="inputPhone" placeholder="Indtast telefon nummer...">
                    </div>

                    <div class="form-group">
                        <input type="password" class="form-control" id="inputPsw" name="inputPsw" placeholder="Indtast kodeord...">
                    </div>

                    <div class="form-group">
                        <input type="number" class="form-control" id="inputBalance" name="inputBalance" placeholder="Indtast saldo (kr)">
                    </div>

                    <div class="form-group">
                        <select class="form-control" id="inputRole" name="inputRole">
                            <option value="User">Kunde</option>
                            <option value="Admin">Administrator</option>
                        </select>
                    </div>


                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Annuller</button>
                    <button type="submit" class="btn btn-success">Opret bruger</button>
                </div>
            </form>
        </div>
    </div>
</div>