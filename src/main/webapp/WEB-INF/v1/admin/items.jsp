<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container text-center">
    <h3>Menu Kort</h3>
    <br/> <br/>
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalCreateItem">
        Opret ny Bund/Top
    </button>
    <br/> <br/>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">Navn</th>
            <th scope="col">Type</th>
            <th scope="col">Pris</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.items}" var="item" varStatus="vs">
            <tr>
                <td>${item.name}</td>
                <td>${item.type}</td>
                <td>${item.price} kr</td>

                <td>
                    <form action="AdminItems" method="post">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="itemId" value="${item.id}">
                        <input type="hidden" name="type" value="${item.type}">
                        <input type="submit" class="btn btn-danger" value="Slet">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="modal fade" id="modalCreateItem" tabindex="-1" aria-labelledby="modalCreateItem" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="">Opret ny bund/top</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="AdminItems" method="POST">
                <div class="modal-body">
                    <input type="hidden" name="action" value="createItem">

                    <div class="form-group">
<%--suppress HtmlFormInputWithoutLabel --%>
                        <input type="itemname" class="form-control" id="inputItemName" name="inputItemName" placeholder="Indtast navn på din nye bund/top">
                    </div>

                    <div class="form-group">
<%--suppress HtmlFormInputWithoutLabel --%>
                        <input type="price" class="form-control" id="inputPrice" name="inputPrice" placeholder="Indtast Pris på varen">
                    </div>

                    <div class="form-group">
<%--suppress HtmlFormInputWithoutLabel --%>
                        <select class="form-control" type="inputRole" name="inputType" >
                            <option value="bottom">Bund</option>
                            <option value="topping">Top</option>
                        </select>
                    </div>

                    <div class="modal-footer">
                          <button type="button" class="btn btn-danger" data-dismiss="modal">Annuller</button>
                          <button type="submit" class="btn btn-success">Opret Bund/Top</button>
                </div>
                </div>
            </form>
        </div>
    </div>
</div>