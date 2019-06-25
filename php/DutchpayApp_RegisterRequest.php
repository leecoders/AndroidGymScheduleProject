<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");

 $userEmail = $_POST["userEmail"];
 $userPassword = $_POST["userPassword"];
 $userPaymentPassword = $_POST["userPaymentPassword"];
 $userName = $_POST["userName"];
 $userRN = $_POST["userRN"];
 $userGender = $_POST["userGender"];
 $userPhone = $_POST["userPhone"];
 $userMoney = $_POST["userMoney"];
 
 $statement = mysqli_prepare($con, "INSERT INTO DUTCHPAYAPP_USER_INFOMATION VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
 mysqli_stmt_bind_param($statement, "ssssiisi", $userEmail, $userPassword, $userPaymentPassword , $userName, $userRN , $userGender , $userPhone, $userValue );
 mysqli_stmt_execute($statement);

 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>
