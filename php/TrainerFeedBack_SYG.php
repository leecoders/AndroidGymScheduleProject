<?php
header("Content-Type: text/html; charset=UTF-8");
$con = mysqli_connect("localhost", "kjg123kg" , "ekgus159!@!@", "kjg123kg");
mysqli_set_charset($con,"utf8");

 $FeedBack = $_POST["FeedBack"];
 $Value = $_POST["Value"];
 $ptID = $_POST["ptID"];

 $statement = mysqli_prepare($con, "UPDATE SCHEDULE SET FeedBack = ?  , FeedBackValue = ? WHERE ptID = ?");
 mysqli_stmt_bind_param($statement, "sii", $FeedBack , $Value ,$ptID);
 mysqli_stmt_execute($statement);

 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>
