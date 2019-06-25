<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");

 $ptID = $_POST["ptID"];

 $statement = mysqli_prepare($con, "SELECT * FROM SCHEDULE WHERE ptID = ?");
 mysqli_stmt_bind_param($statement, "s", $ptID);
 mysqli_stmt_execute($statement);
 mysqli_stmt_store_result($statement);
 mysqli_stmt_bind_result($statement, $ptID);

 $response = array();
 $response["success"] = true;

 while(mysqli_stmt_fetch($statement)){
     $response["success"] = false;
     $response["ptID"] = $ptID;
 }

 echo json_encode($response);
?>
