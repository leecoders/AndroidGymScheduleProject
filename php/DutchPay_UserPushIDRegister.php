<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");

 $userID = $_POST["userID"];
 $userPushID = $_POST["userPushID"];



		 $statement = mysqli_prepare($con, "UPDATE DUTCHPAY_USER_INFOMATION set userPushID = ? WHERE userID = ?");
		 mysqli_stmt_bind_param($statement, "ss",  $userPushID, $userID);
		 mysqli_stmt_execute($statement);


 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>
