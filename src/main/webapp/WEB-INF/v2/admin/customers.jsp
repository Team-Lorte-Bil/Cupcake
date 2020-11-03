<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="page-section cta" style="background: rgba(109,67,73,0.74);">
    <div class="col-xl-9 mx-auto"><div class="cta-inner text-center rounded">
        <h2 class="section-heading mb-5"><span class="section-heading-lower">Kunder</span></h2>
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalCreateUser">
            Opret ny bruger
        </button>
        <br/> <br/>
        <table class="table" summary="table of customers">
            <thead>
            <tr>
                <th scope="col">Kunde nr</th>
                <th scope="col">Navn</th>
                <th scope="col">E-mail</th>
                <th scope="col">Telefon</th>
                <th scope="col">Saldo</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.customers}" var="customer" varStatus="vs">
                <tr>
                    <td>${customer.id}</td>
                    <td>${customer.name}</td>
                    <td>${customer.email}</td>
                    <td>${customer.phoneno}</td>
                    <td>${customer.accountBalance}</td>
                    <td>
                        <div class="btn-group" role="group">
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalChangeBalance${vs.index}">
                                Ændre saldo
                            </button>
                            <c:choose>
                                <c:when test="${customer.admin}">
                                    <form action="AdminCustomers" method="post">
                                        <input type="submit" class="btn btn-danger" value="Slet bruger" disabled>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form action="AdminCustomers" method="post">
                                        <input type="hidden" name="action" value="deleteUser">
                                        <input type="hidden" name="userId" value="${customer.id}">
                                        <input type="submit" class="btn btn-danger" value="Slet bruger">
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
<%--suppress HtmlFormInputWithoutLabel --%>
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
<%--suppress HtmlFormInputWithoutLabel --%>
                                <input type="name" class="form-control" id="inputName" name="inputName" placeholder="Indtast navn...">
                            </div>

                            <div class="form-group">
<%--suppress HtmlFormInputWithoutLabel --%>
                                <input type="email" class="form-control" id="inputEmail" name="inputEmail" placeholder="Indtast e-mail...">
                            </div>

                            <div class="form-group">
<%--suppress HtmlFormInputWithoutLabel --%>
                                <input type="tel" class="form-control" id="inputPhone" name="inputPhone" placeholder="Indtast telefon nummer...">
                            </div>

                            <div class="form-group">
<%--suppress HtmlFormInputWithoutLabel --%>
                                <input type="password" class="form-control" id="inputPsw" name="inputPsw" placeholder="Indtast kodeord...">
                            </div>

                            <div class="form-group">
<%--suppress HtmlFormInputWithoutLabel --%>
                                <input type="number" class="form-control" id="inputBalance" name="inputBalance" placeholder="Indtast saldo (kr)">
                            </div>

                            <div class="form-group">
<%--suppress HtmlFormInputWithoutLabel --%>
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
        </div></div>
</section>
<section class="page-section about-heading">
    <div class="container">
        <div class="about-heading-content"></div>
    </div>
</section>