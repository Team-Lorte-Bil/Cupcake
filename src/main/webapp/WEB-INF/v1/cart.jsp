<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<div class="container text-center">
    <h3>Din kurv</h3>
    <br/> <br/>


    <%
        if (session.getAttribute("cakes") != null) {
    %>
    <table class="table table-striped">
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
        <c:forEach items="${cakes}" var="cake">
            <tr>
                <th scope="row">
                    <select class="custom-select text-center" disabled>
                        <option selected>${cake.value}</option>
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
                <td>${cake.key.bottom}</td>
                <td>${cake.key.topping}</td>
                <td>${cake.key.price},- kr</td>
                <td>
                    <form action="Cart" method="post">
                        <input type="hidden" name="action" value="remove">
                        <input type="hidden" name="id" value="${cake.key.id}">
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

    <p><b>Total pris:</b></p>
    <p><b><u>${sessionScope.totalprice},- kr</u></b></p>
        <input type="hidden" name="cakes" value="${sessionScope.cakes}">
        <input type="hidden" name="totalprice" value="${sessionScope.totalprice}">


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