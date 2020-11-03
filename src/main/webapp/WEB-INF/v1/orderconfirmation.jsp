<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<div class="container text-center">
    <h1>
        Din ordre ${requestScope.order.orderId} er bekræftet.
    </h1>
    <p>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">Antal</th>
            <th scope="col">Bund</th>
            <th scope="col">Fyld</th>
            <th scope="col">Linje pris</th>
        </tr>
        </thead>
        <tbody>
        <!-- print all cupcakes in session cart -->
        <c:forEach items="${requestScope.cakes}" var="cake">
            <tr>
                <td>${cake.getAmount()}</td>
                <td>${cake.getCake().getBottom()}</td>
                <td>${cake.getCake().getTopping()}</td>
                <td>${cake.getCake().getPrice() * cake.getAmount()} kr</td>
            </tr>
        </c:forEach>
        <tr> <!-- empty row for spacing -->
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr> <!-- empty row for spacing -->
        <tr>
            <td></td>
            <td></td>
            <td>Total</td>
            <td>${sessionScope.totalprice},- kr</td>
        </tr>
        </tbody>
    </table>
    <p>
        Vi glæder os til at se dig i butikken!
        <br/> <br/>

        <a href="${pageContext.request.contextPath}">Tryk her for at komme tilbage til forsiden </a>
    </p>
</div>