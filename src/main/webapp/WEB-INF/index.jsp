<%@ page import="domain.items.CakeOption" %>
<%@ page import="java.util.Map" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% CakeOption cake = new CakeOption(); %>

    <div class="container text-center">

    <p style="font-size:25px">Velkommen ombord til øens bedste cupcakes!</p>


    <p1> Vælg og bestil herunder! </p1>


    <br/> <br/>

        <form action="Cart" method="post">
            <input type="hidden" name="action" value="add">

    <label for="Bund">Bunde:</label>

    <select name="bund" id="bund">
        <%
            for (Map.Entry<String, Integer> entry : cake.getBottoms().entrySet()) {
                %>
        <option value="<%=entry.getKey()%>"><%=entry.getKey()%> - <%=entry.getValue()%>kr</option>
        <%}%>
    </select>

    <br/> <br/>
    <label for="Topping">Topping:</label>

    <select name="topping" id="topping">
        <%
            for (Map.Entry<String, Integer> entry : cake.getToppings().entrySet()) {
        %>
        <option value="<%=entry.getKey()%>"><%=entry.getKey()%> - <%=entry.getValue()%>kr</option>
        <%}%>
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

    <input type="submit" value="Tilføj til kurv"/>
    </form>


    <br/> <br/>
    <a class="nav-link" href="/Cart">
        <input type="submit" value="Gå til betaling"/>
        </form>
    </a>
</div>