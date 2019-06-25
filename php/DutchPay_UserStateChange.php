<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");

 $userID = $_POST["userID"];
 $userDutchMoney = $_POST["userDutchMoney"];
 $userState = $_POST["userState"];

 $statement = mysqli_prepare($con, "UPDATE DUTCHPAY_USER_INFOMATION SET `userState` = ? , `userDutchMoney` = ? WHERE `userID` = ?");
 mysqli_stmt_bind_param($statement, "iis" , $userState,  $userDutchMoney, $userID );
 mysqli_stmt_execute($statement);

 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>