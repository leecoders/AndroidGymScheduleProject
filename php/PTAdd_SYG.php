<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");

 $userID = $_POST["userID"];
 $ptID = $_POST["ptID"];

 $FeedBack = "";
 $FeedBackValue = "0";
 $statement = mysqli_prepare($con, "INSERT INTO SCHEDULE VALUES (? , ?  , ? , ?)");
 mysqli_stmt_bind_param($statement, "sisi", $userID , $ptID , $FeedBack , $FeedBackValue);
 mysqli_stmt_execute($statement);

 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>
