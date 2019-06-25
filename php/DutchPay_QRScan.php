<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");

 $hostID = $_POST["hostID"];
 $userID = $_POST["userID"];
 $Amount = $_POST["Amount"];
 $directInputAmount = "";

 $statement = mysqli_prepare($con, "INSERT INTO DUTCHPAY_PAYMENT_INFORMATION_TEMPORARY_STORAGE VALUES(? , ? , ? , ? , false)");
 mysqli_stmt_bind_param($statement, "ssii" ,$hostID , $userID, $Amount , $directInputAmount );
 mysqli_stmt_execute($statement);

 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>