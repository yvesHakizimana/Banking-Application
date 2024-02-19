<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bank Status</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <!-- Custom CSS -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            padding: 20px;
        }
        .container {
            margin-top: 30px;
        }
        .card {
            margin-bottom: 20px;
            transition: transform 0.3s ease-in-out;
        }
        .card:hover {
            transform: scale(1.05);
        }
        .card-header {
            background-color: #007bff;
            color: #fff;
        }
        .card-body {
            padding: 20px;
        }
        .table-responsive {
            margin-top: 20px;
        }
        .table th, .table td {
            transition: background-color 0.3s ease-in-out;
        }
        .table th:hover, .table td:hover {
            background-color: rgba(0, 123, 255, 0.1);
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
    <h1 class="text-center mb-4">Bank Status</h1>
    <div class="row">
        <div class="col-md-4">
            <div class="card">
                <div class="card-header">
                    Accounts Created
                </div>
                <div class="card-body">
                    <h2 class="text-center"><span id="accounts-created">0</span></h2>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card">
                <div class="card-header">
                    Total Deposits
                </div>
                <div class="card-body">
                    <h2 class="text-center"><span id="total-deposits">0</span></h2>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card">
                <div class="card-header">
                    Total Withdrawals
                </div>
                <div class="card-body">
                    <h2 class="text-center"><span id="total-withdrawals">0</span></h2>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    Total Balance in Bank
                </div>
                <div class="card-body">
                    <h2 class="text-center"><span id="total-balance">0</span></h2>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    Grouped Transactions
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>Transaction Type</th>
                                <th>Total Amount</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="transaction" items="${groupedTransactions}">
                                <tr>
                                    <td>${transaction.transactionType}</td>
                                    <td>${transaction.amount}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header">
                    Account Types Summary
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-6">
                            <h4>Saving Accounts: <span id="savingAccounts">0</span></h4>
                        </div>
                        <div class="col-md-6">
                            <h4>Current Accounts: <span id="currentAccounts">0</span></h4>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
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
</div>
<div class="container">
    <h2>Customer Transaction Information</h2>
    <table class="table">
        <thead>
        <tr>
            <th>Username</th>
            <th>Phone Number</th>
            <th>Account ID</th>
            <th>Transaction Type</th>
            <th>Amount</th>
            <th>Transaction Date</th>
            <th>Account Type</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="transaction" items="${transactionsReport}">
            <tr>
                <td>${transaction.username}</td>
                <td>${transaction.phoneNumber}</td>
                <td>${transaction.account_id}</td>
                <td>${transaction.transactionType}</td>
                <td>${transaction.amount}</td>
                <td>${transaction.formattedTransactionDate}</td>
                <td>${transaction.accountType}</td>
            </tr>
        </c:forEach>
        <!-- Add more rows as needed -->
        </tbody>
    </table>
</div>

<script>
    // Function to animate counting up to a target value
    function countUp(element, targetValue) {
        $({ count: 0 }).animate({ count: targetValue }, {
            duration: 2000, // Change the duration here (in milliseconds)
            step: function() {
                $(element).text(Math.floor(this.count));
            },
            complete: function() {
                $(element).text(targetValue);
            }
        });
    }

    // Animate the counting for accounts created
    countUp("#accounts-created", ${accountsCreated});

    // Animate the counting for total deposits
    countUp("#total-deposits", ${totalDeposits});

    // Animate the counting for total withdrawals
    countUp("#total-withdrawals", ${totalWithdrawals});

    // Animate the counting for total balance
    countUp("#total-balance", ${totalBalance});

    // Animate the counting for total balance
    countUp("#savingAccounts", ${savingAccounts});

    // Animate the counting for total balance
    countUp("#currentAccounts", ${currentAccounts});
</script>

</body>
</html>
