<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");

 $userID = $_POST["userID"];
 $assignedAmount = $_POST["assignedAmount"];
 $prePaymentCheck = $_POST["prePaymentCheck"];


if(!strcmp($prePaymentCheck , "0")){

		 $statement = mysqli_prepare($con, "UPDATE DUTCHPAY_PAYMENT_INFORMATION_TEMPORARY_STORAGE set assignedAmount = ?  , prePayment = false WHERE userID = ?");
		 mysqli_stmt_bind_param($statement, "is",  $assignedAmount , $userID );
		 mysqli_stmt_execute($statement);

} else {

		 $statement = mysqli_prepare($con, "UPDATE DUTCHPAY_PAYMENT_INFORMATION_TEMPORARY_STORAGE set assignedAmount = ?  ,prePayment = true WHERE userID = ?");
		 mysqli_stmt_bind_param($statement, "is",  $assignedAmount , $userID );
		 mysqli_stmt_execute($statement);
}

 $statement1 = mysqli_prepare($con, "UPDATE DUTCHPAY_USER_INFOMATION SET userState = 2 WHERE userID IN (SELECT * FROM(SELECT userID FROM DUTCHPAY_PAYMENT_INFORMATION_TEMPORARY_STORAGE WHERE hostID = ?)AS X)");
		 mysqli_stmt_bind_param($statement1, "s", $userID );
		 mysqli_stmt_execute($statement1);

 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>
