<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            text-align: center;
            width: 400px; /* Set the width of the container */
        }
        input[type="text"], input[type="password"] {
            width: calc(100% - 22px); /* Adjusted width to account for padding and borders */
            padding: 10px;
            margin: 10px 0;
            border-radius: 5px;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            border: none;
            border-radius: 5px;
            background-color: #007bff;
            color: #fff;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>User Login</h2>
    <form action="login-user" method="post">
        <input type="text" name="username" placeholder="Email" required><br>
        <input type="password" name="password" placeholder="Password" required><br>
        <c:if test="${message != null}">
            <p style="color: red">${message}</p>
        </c:if>
        <c:if test="${errorMessage != null}">
            <p style="color: red">${errorMessage}</p>
        </c:if>

        <input type="submit" value="Login">
    </form>
    <p class="btn btn-primary">No account
        <a href="registerUser.jsp" > Register your-self</a>
    </p>
</div>
</body>
</html>
