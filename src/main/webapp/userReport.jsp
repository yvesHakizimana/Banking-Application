<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="userReport" scope="request" type="java.util.List"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Account Information</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            padding: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            border: 1px solid #dee2e6;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #007bff;
            color: #fff;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #cce5ff;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Customer Account Information</h2>
    <table class="table">
        <thead>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Username</th>
            <th>Age</th>
            <th>Phone Number</th>
            <th>Account ID</th>
            <th>Account Type</th>
            <th>Balance</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="row" items="${userReport}">
            <tr>
                <td>${row.firstName}</td>
                <td>${row.lastName}</td>
                <td>${row.username}</td>
                <td>${row.age}</td>
                <td>${row.phoneNumber}</td>
                <td>${row.accountId}</td>
                <td>${row.accountType}</td>
                <td>${row.balance}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
