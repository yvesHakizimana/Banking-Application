<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transfer Funds</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style>
        .card {
            margin-top: 20px;
        }
        .card-header {
            background-color: #007bff;
            color: white;
        }
        #profileResult {
            margin-top: 20px;
        }
        #profileResult h1 {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <h2 class="mb-4">Transfer Funds</h2>
            <form action="transfer-process" method="post">
                <div class="form-group">
                    <label for="recipientAccountId">Recipient Account ID</label>
                    <input type="text" class="form-control" id="recipientAccountId" name="recipientAccountId" required>
                </div>
                <div class="form-group">
                    <label for="amount">Amount</label>
                    <input type="number" class="form-control" id="amount" name="amount" min="0" step="0.01" required>
                    <c:if test="${transferError  != null}">
                        <p style="color: red">${transferError}</p>
                    </c:if>

                </div>
            <hr>
            <div id="profileResult">
                <!-- Profile information will be displayed here -->
            </div>
            <div class="card">
                <div class="card-header">
                    User Account Details
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-4">
                            <img src="" id="imageUrl" alt="UserImage" class="img-fluid" style="max-width: 100%; height: auto; width: 200px;" />
                        </div>
                        <div class="col-md-8">
                            <p id="firstName"></p>
                            <p id="lastName"></p>
                            <p id="phoneNumber"></p>
                        </div>
                    </div>

                </div>
            </div>
                <button type="submit" class="m-3 btn btn-primary">Transfer</button>
            </form>
        </div>
    </div>
</div>

<!-- Bootstrap JS and jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<!-- Ensure jQuery is loaded before any code that depends on it -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

<script>
    $(document).ready(function(){
        $('#recipientAccountId, #amount').on('input', function() {
            let accountId = $('#recipientAccountId').val().trim();
            let amount = $('#amount').val().trim();
            if(accountId !== '' && amount !== '') {
                fetchProfile(accountId);
            } else {
                $('#profileResult').hide();
            }
        });
    });

    function fetchProfile(accountId) {
        $.ajax({
            type: 'GET',
            url: 'ProfileFetchServlet',
            data: { accountId: accountId },
            dataType: 'json',
            success: function(response) {
                if(response)  $('#profileResult').show();
                response.profilePictureUrl = "." + response.profilePictureUrl
                console.log(response); // Log the response to the console for debugging
                $('#firstName').text('First Name: ' + response.first_name);
                $('#lastName').text('Last Name: ' + response.last_name);
                $('#phoneNumber').text('Phone Number: ' + response.phoneNumber);
                $('#imageUrl').attr('src', response.profilePictureUrl);
            },
            error: function(xhr, status, error) {
                console.error('Error fetching profile:', error);
                console.log(xhr.responseText); // Log the response text for debugging
            }
        });
    }
</script>
</body>
</html>
