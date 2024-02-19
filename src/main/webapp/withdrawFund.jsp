<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />
<%
    // Check if the user is logged in
    String username = (String) session.getAttribute("username");
    if(username == null || username.isEmpty()) {
        response.sendRedirect("loginUser.jsp");
    }
%>
<html>
<head>
    <title>WithdrawFunds</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<form action="withdraw-fund" method="post">
    <div class="form-group col-md-6">
        <label for="deposit">Amount to withdraw: </label>
        <input type="text" id="deposit" name="withdraw" class="form-control" required>
        <c:if test="${message != null}">
            <p class="text-danger">${message}</p>
        </c:if>
    </div>
    <button class="btn btn-primary">Withdraw</button>
</form>
</body>
</html>
