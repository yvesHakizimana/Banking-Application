<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Registration</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            padding: 20px;
        }

        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            margin: 0 auto;
            max-width: 600px; /* Adjust the max-width for responsiveness */
        }

        label {
            font-weight: bold;
        }

        input[type="text"],
        input[type="password"],
        select {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border-radius: 5px;
            border: 1px solid #ced4da;
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
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>

<body>

<form action="register-user" method="post" enctype="multipart/form-data">
    <h1>Register User</h1>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="fname">First-Name:</label>
            <input type="text" id="fname" name="fname" class="form-control" required>
        </div>
        <div class="form-group col-md-6">
            <label for="lname">Last-Name:</label>
            <input type="text" id="lname" name="lname" class="form-control" required>
        </div>
        <div class="form-group col-md-6">
            <label for="age">Age:</label>
            <input type="number" id="age" name="age" class="form-control" required>
            <c:if test="${ageMessage != null}" >
                <p style="color: red"><c:out value="${ageMessage}"/> </p>
            </c:if>
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="phoneNumber">PhoneNumber:</label>
            <input type="text" id="phoneNumber" name="phoneNumber" class="form-control" placeholder="Like 07[9238]xxxxxxx" required>
            <c:if test="${phoneNumberError != null}">
                <p style="color: red"><c:out value="${phoneNumberError}"/> </p>
            </c:if>
        </div>
        <div class="form-group col-md-6">
            <label for="email">Email:</label>
            <input type="text" id="email" name="email" class="form-control" required>
            <c:if test="${emailErrors != null}">
                <c:forEach items="${emailErrors}" var="emailError">
                    <p style="color: red"><c:out value="${emailError}" /> </p>
                </c:forEach>
            </c:if>


        </div>
    </div>
    <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" class="form-control" required>
        <c:if test="${errorMessages != null}">
            <c:forEach var="error" items="${errorMessages}">
                <p style="color: red"><c:out value="${error}"/> </p>
            </c:forEach>
        </c:if>
    </div>


    <div class="form-group">
        <label for="accountType">Account Type:</label>
        <select id="accountType" name="accountType" class="form-control">
            <option value="current">Current Account</option>
            <option value="saving">Saving Account</option>
        </select>
    </div>
    <div class="form-group">
        <label for="imageFile">Profile Image:</label>
        <input type="file" id="imageFile" name="imageFile" class="form-control-file">
    </div>
    <input type="submit" value="Register" class="btn btn-primary">
</form>

</body>

</html>
