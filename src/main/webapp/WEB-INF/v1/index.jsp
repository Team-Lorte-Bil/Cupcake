<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

    <div class="container text-center">

    <p style="font-size:25px">Velkommen ombord til øens bedste cupcakes!</p>


    <p> Vælg og bestil herunder! </p>


    <br/> <br/>

        <form action="Cart" method="post">
            <input type="hidden" name="action" value="add">

    <label for="Bund">Bunde:</label>


    <select name="bund" id="bund">
        <c:forEach items="${requestScope.bottoms.entrySet()}" var="bottom">
        <option value="${bottom.key}">${bottom.key} - ${bottom.value}kr</option>
        </c:forEach>
    </select>

    <br/> <br/>
    <label for="Topping">Topping:</label>

    <select name="topping" id="topping">
        <c:forEach items="${requestScope.toppings.entrySet()}" var="topping">
            <option value="${topping.key}">${topping.key} - ${topping.value}kr</option>
        </c:forEach>
    </select>

    <br/> <br/>

    <label for="Antal">Antal:</label>

    <select name="antal" id="antal">
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


    <br/> <br/>

    <input type="submit" value="Tilføj til kurv" class="btn btn-primary" />
    </form>


    <br/> <br/>
    <a class="nav-link" href="<c:url value="/Cart"/>">
        <input type="submit" value="Gå til betaling" class="btn btn-success" />

    </a>
</div>