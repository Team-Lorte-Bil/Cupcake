<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../includes/header.jsp"%>
<div class="container text-center">
    <h3>Din kurv</h3>
    <br/> <br/>

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
                    <select class="custom-select text-center">
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
                    <form action="FrontController" method="post">
                        <input type="hidden" name="target" value="cart">
                        <input type="hidden" name="type" value="removeitem">
                        <input type="hidden" name="id" value="${cake.key.id}">
                        <input type="submit" class="btn btn-danger" value="Fjern fra kurv"></input>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    </label>
    <textarea id="subject" name="subject" placeholder="kommentar til bestillingen" style="height:100px" lenght="150px" ></textarea>
    <br/> <br/>

    <p><b>Total pris:</b></p>
    <p><b><u>${sessionScope.totalprice},- kr</u></b></p>

    <form action="FrontController" method="post">
        <input type="hidden" name="target" value="checkout">
        <input type="hidden" name="cakes" value="${cakes}">
        <input type="submit" class="btn btn-primary" value="Afgiv ordre"></input>
    </form>
</div>

<%@include file="../includes/footer.jsp"%>