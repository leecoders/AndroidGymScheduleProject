<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");

 $orderID = "";
 $Time = $_POST["Time"];
 $OrderMan = $_POST["OrderMan"];
 $OrderList = $_POST["OrderList"];
 $Approve = $_POST["Approve"];

 $statement = mysqli_prepare($con, "INSERT INTO ISBY_Order_Before VALUES(?, ?, ? , ?, ?)");
 mysqli_stmt_bind_param($statement, "isssi", $orderID , $Time , $OrderMan ,$OrderList , $Approve);
 mysqli_stmt_execute($statement);

 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>
