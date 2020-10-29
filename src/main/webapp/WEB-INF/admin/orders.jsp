<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container text-center">
    <h3>Aktive ordre</h3>
    <br/> <br/>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">Ordre nummer</th>
            <th scope="col">Kunde</th>
            <th scope="col">Pris</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.orders}" var="order" varStatus="vs">
        <tr>
            <td>
                <a href="#" data-toggle="modal" data-target="#modal${vs.index}">${order.orderId}</a>

            </td>
            <td>${order.getUser().getName()}</td>
            <td>112kr</td>
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
                                <p>Kundenavn: ${order.user.name}</p>
                                <p>Kundemail: ${order.user.email}</p>
                                <p>Kundetelefon: ${order.user.phoneno}</p>
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