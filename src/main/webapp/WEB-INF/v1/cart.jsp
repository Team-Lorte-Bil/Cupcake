<%@ page import="api.Cart" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<div class="container text-center">
    <h3>Din kurv</h3>
    <br/> <br/>


    <%
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null && (cart.getCakes().size() > 0)) {
    %>
    <table class="table table-striped" >
        <thead>
        <tr>
            <th scope="col">Antal</th>
            <th scope="col">Bund</th>
            <th scope="col">Fyld</th>
            <th scope="col">Pris pr. stk.</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <!-- print all cupcakes in session cart -->
        <%--@elvariable id="cakes" type="java.util.List"--%>
        <c:forEach items="${sessionScope.cart.cakes}" var="cake">
            <tr>
                <th scope="row">
<%--suppress HtmlFormInputWithoutLabel --%>
                    <select class="custom-select text-center" disabled>
                        <option selected>${cake.amount}</option>
                        <option value="0">0</option>
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
                </th>
                <td>${cake.cake.bottom}</td>
                <td>${cake.cake.topping}</td>
                <td>${cake.cake.price},- kr</td>
                <td>
                    <form action="Cart" method="post">
                        <input type="hidden" name="action" value="remove">
                        <input type="hidden" name="id" value="${cake.cake.id}">
                        <input type="submit" class="btn btn-danger" value="Fjern fra kurv"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


    <form action="CreateOrder" method="post">
    <div class="form-group">
        <label for="comment">Kommentar til ordren</label>
        <textarea class="form-control" id="comment" name="comment" rows="3"></textarea>
    </div>
    <br/> <br/>

    <p><strong>Total pris:</strong></p>
    <p><strong><u>${sessionScope.cart.cartValue},- kr</u></strong></p>
        <c:choose>
            <c:when test="${sessionScope.currentUser == null}">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/Login">Log ind og bestil</a>
            </c:when>
            <c:otherwise>
                <input type="submit" class="btn btn-primary" value="Afgiv ordre"/>
            </c:otherwise>
        </c:choose>

    </form>

    <br><br>

    <% } else { %>
    <h1>Der er ingen lækre kager i kurven!</h1><br>

    <a href="${pageContext.request.contextPath}">
        <button type="button" class="btn btn-primary">Gå til forsiden</button>
    </a>

    <% }; %>
</div>