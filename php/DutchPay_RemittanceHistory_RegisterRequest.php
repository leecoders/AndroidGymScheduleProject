<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");

 $remittanceID = "";
 $remittanceAmount = $_POST["remittanceAmount"];
 $remittererID	 = $_POST["remittererID"];
 $receiverID	 = $_POST["receiverID"];

 $statement = mysqli_prepare($con, "INSERT INTO DUTCHPAY_REMITTANCE_HISTORY VALUES(? , ? , ?,  ? , DEFAULT)");
 mysqli_stmt_bind_param($statement, "iiss" , $remittanceID , $remittanceAmount, $remittererID , $receiverID);
 mysqli_stmt_execute($statement);


 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>