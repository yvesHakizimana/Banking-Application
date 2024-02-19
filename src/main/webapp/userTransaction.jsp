<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />
<html>
<head>
    <title>User Transaction Report</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 80%;
            margin: 20px auto;
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .footer {
            margin-top: 20px;
            text-align: center;
            font-size: 12px;
            color: #777;
        }
        .filter-container {
            margin-bottom: 20px;
        }
    </style>
    <script>
        // JavaScript function to generate incrementing transaction IDs on the client-side
        var transactionIdCounter = 1; // Initialize counter

        function generateTransactionId() {
            var id = transactionIdCounter++; // Increment counter and return the value
            return id;
        }
    </script>
</head>
<body>
<div class="container">
    <h1>My Transaction Report</h1>

    <!-- Filter by Date -->
    <!-- You can add the filtering functionality here if needed -->

    <table id="transactionTable">
        <thead>
        <tr>
            <th>Transaction ID</th>
            <th>Type</th>
            <th>Amount</th>
            <th>Date</th>
            <th>Account</th>
        </tr>
        </thead>
        <tbody>
        <!-- Your transaction data will be dynamically populated here -->
        <c:forEach var="transaction" items="${userTransactions}">
            <tr>
                <td><script>document.write(generateTransactionId());</script></td>
                <td>${transaction.transactionType}</td>
                <td>$${transaction.amount}</td>
                <td>${transaction.formattedTransactionDate}</td>
                <td>${transaction.accountType}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- Hidden input field to store the current transaction ID -->
<%--    <input type="hidden" id="transactionIdContainer" value="0">--%>

</div>

<div class="footer">
    Generated on: <%= new java.util.Date() %>
</div>

</body>
</html>
