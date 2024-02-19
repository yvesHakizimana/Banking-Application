<jsp:useBean id="account" scope="session" type="com.code.banksystem.bank.models.Account"/>
<jsp:useBean id="user" scope="session" type="com.code.banksystem.bank.models.User"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="com.code.banksystem.bank.controllers.userControllers.UTransaction" %>
<jsp:include page="header.jsp" />

<%
    // Check if the user is logged in
    String username = (String) session.getAttribute("username");
    if(username == null || username.isEmpty() || user == null || account == null ) {
        response.sendRedirect("loginUser.jsp");
    }

%>
<html>
<head>
    <title>Main Menu</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style>
        /* Add your CSS styles here */
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1, h2 {
            text-align: center;
        }
        .account-details {
            margin-top: 20px;
        }
        .account-details table {
            width: 100%;
            border-collapse: collapse;
        }
        .account-details th, .account-details td {
            padding: 10px;
            border: 1px solid #ccc;
            text-align: left;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Welcome to Your Bank</h1>
    <p>Hello ${user.first_name} ${user.last_name}</p>
    <h2>Main Menu</h2>
    <div class="account-details">
        <h3>Account Details</h3>
        <table>
            <tr>
                <th>Account ID</th>
                <td>${account.accountId}</td>
            </tr>
            <tr>
                <th>Balance</th>
                <td>${account.balance}</td>
            </tr>
            <tr>
                <th>Account Type</th>
                <td>${account.accountType}</td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
