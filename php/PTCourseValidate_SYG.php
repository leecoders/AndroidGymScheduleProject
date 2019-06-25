<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");

 $ptYear = $_POST["ptYear"];
 $ptMonth = $_POST["ptMonth"];
 $ptDay = $_POST["ptDay"];
 $ptTime = $_POST["ptTime"];
 $ptTrainerID = $_POST["ptTrainerID"];

 $statement = mysqli_prepare($con, "SELECT * FROM PTCOURSE WHERE ptYear = ? AND ptMonth = ? AND ptDay = ? AND ptTime = ? AND ptTrainerID = ?");
 mysqli_stmt_bind_param($statement, "sssss", $ptYear , $ptMonth , $ptDay , $ptTime , $ptTrainerID);
 mysqli_stmt_execute($statement);
 mysqli_stmt_store_result($statement);
 mysqli_stmt_bind_result($statement, $ptYear , $ptMonth , $ptDay  , $ptTime , $ptTrainerID);

 $response = array();
 $response["success"] = true;

 while(mysqli_stmt_fetch($statement)){
     $response["success"] = false;
     $response["userID"] = $userID;
 }

 echo json_encode($response);
?>
