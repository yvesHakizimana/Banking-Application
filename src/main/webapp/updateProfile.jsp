<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" type="com.code.banksystem.bank.models.User"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />
<html>
<head>
    <title>Edit Profile</title>
    <style>
        /* Form Container */
        .form-container {
            max-width: 600px;
            margin: 0 auto;
            padding: 30px;
            border-radius: 20px;
            background-color: #f9f9f9;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin-top: 30px;
        }

        /* Form Header */
        .form-header {
            text-align: center;
            margin-bottom: 30px;
        }

        .form-header h1 {
            font-size: 32px;
            color: #333;
        }

        /* Form */
        form {
            display: flex;
            flex-direction: column;
        }

        label {
            font-weight: bold;
            margin-bottom: 10px;
        }

        input[type="text"], input[type="number"] {
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }

        /* Submit Button */
        input[type="submit"] {
            padding: 10px 20px;
            font-size: 16px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="form-container">
    <div class="form-header">
        <h1>Edit Profile</h1>
    </div>
    <form action="update-profile" method="post" enctype="multipart/form-data">
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" value="${user.first_name}">

        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" value="${user.last_name}">

        <label for="age">Age:</label>
        <input type="number" id="age" name="age" value="${user.age}">

        <label for="phoneNumber">Phone Number:</label>
        <input type="text" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}">
        <c:if test="${phoneError} ">
            <p style="color:red;">${phoneError}</p>
        </c:if>

        <label for="accountType">Account Type:</label>
        <input type="text" id="accountType" name="accountType" value="${user.accountType}">
        <c:if test="${accountError} ">
            <p style="color:red;">${accountError}</p>
        </c:if>

        <!-- Add a file input for photo -->
        <label for="photo">Photo:</label>
        <input type="file" id="photo" name="photo">

        <input type="submit" value="Update Profile">
    </form>
</div>
</body>
</html>
