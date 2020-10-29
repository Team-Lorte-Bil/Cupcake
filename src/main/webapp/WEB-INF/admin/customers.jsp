<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container text-center">
    <h3>Kunder</h3>
    <br/> <br/>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">Kunde nr</th>
            <th scope="col">Navn</th>
            <th scope="col">Saldo</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.customers}" var="customer" varStatus="vs">
            <tr>
                <td>
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal${vs.index}">
                        Launch demo modal
                    </button>

                </td>
                <td>${customer.id}</td>
                <td>${customer.name}</td>
                <td>${customer.accountBalance}</td>
                <td>
                    <form action="AdminOrders" method="post">
                        <input type="hidden" name="action" value="complete">
                        <input type="submit" value="mark complete">
                    </form>
                    <form action="AdminOrders" method="post">
                        <input type="hidden" name="action" value="delete">
                        <input type="submit" value="delete">
                    </form>
                </td>
            </tr>
            <!-- Modals -->
            <div class="modal fade" id="modal${vs.index}" tabindex="-1" aria-labelledby="modal${vs.index}Label" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Ordre nr. ${order.orderId}</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div id="custinfo">
                                <p>Kunde id: ${order.user.id}</p>
                                <p>Kunde navn: ${order.user.name}</p>
                                <p>Saldo: ${order.user.accountBalance}</p>
                            </div>
                            <div id="custitems">

                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary">Save changes</button>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        </tbody>
    </table>
</div>