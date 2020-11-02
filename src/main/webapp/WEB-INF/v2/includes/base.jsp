<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Home - Cupcake</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lora:400,400i,700,700i">
    <link rel="stylesheet" href="assets/css/Google-Style-Login.css">
    <link rel="stylesheet" href="assets/css/Navigation-with-Button.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
</head>

<body style="background: linear-gradient(rgba(47, 23, 15, 0.65), rgba(47, 23, 15, 0.65)), url('assets/img/cupcakes-4068129_1920.jpg');background-size: auto, cover;">
<h1 class="text-center text-white d-none d-lg-block site-heading"><span class="site-heading-lower">Olsker Cupcakes</span></h1>
<!-- nav bar -->
<c:choose>
    <c:when test="${sessionScope.currentUser.admin}">
        <%@include file="admin-navbar.jsp"%>
    </c:when>
    <c:otherwise>
        <%@include file="navbar.jsp"%>
    </c:otherwise>
</c:choose>
<!-- navbar end -->
<main role="main" class="container">
    <jsp:include page="${requestScope.content}" flush="true" />
</main>


<footer class="footer text-faded text-center py-5" style="background: rgb(52,58,64);">
    <div class="container">
        <p class="m-0 small" style="color: rgb(255,255,255);">Copyright&nbsp;© Team Lorte Bil 2020</p>
    </div>
</footer>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
<script src="assets/js/current-day.js"></script>
</body>

</html>