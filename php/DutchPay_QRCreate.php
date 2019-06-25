<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");

 $userID = $_POST["userID"];
 $Amount = $_POST["Amount"];
 $directInputAmount = "";
 
  $statement = mysqli_prepare($con, "UPDATE DUTCHPAY_USER_INFOMATION SET `userState` = 1 WHERE `userID` = ?");
 mysqli_stmt_bind_param($statement, "s" , $userID );
 mysqli_stmt_execute($statement);

 $statement1 = mysqli_prepare($con, "INSERT INTO DUTCHPAY_PAYMENT_INFORMATION_TEMPORARY_STORAGE VALUES(? , ? , ?,  ? , false)");
 mysqli_stmt_bind_param($statement1, "ssii" ,$userID , $userID, $Amount , $directInputAmount);
 mysqli_stmt_execute($statement1);



 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>