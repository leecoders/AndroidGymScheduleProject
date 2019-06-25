<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");

 $userID = $_POST["userID"];
 
 $statement = mysqli_prepare($con, "UPDATE DUTCHPAY_USER_INFOMATION SET userState = 0 WHERE userID IN (SELECT * FROM(SELECT userID FROM DUTCHPAY_PAYMENT_INFORMATION_TEMPORARY_STORAGE WHERE hostID = ?)AS X)");
 mysqli_stmt_bind_param($statement, "s" , $userID );
 mysqli_stmt_execute($statement);

 $statement1 = mysqli_prepare($con, "DELETE FROM DUTCHPAY_PAYMENT_INFORMATION_TEMPORARY_STORAGE WHERE `hostID` = ?");
 mysqli_stmt_bind_param($statement1, "s" , $userID );
 mysqli_stmt_execute($statement1);

 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>