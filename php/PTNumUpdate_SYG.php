<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");

 $userPT = $_POST["userPT"];
 $userID = $_POST["userID"];

 $statement = mysqli_prepare($con, "UPDATE GYMUSER set userPT = ? WHERE userID = ?");
 mysqli_stmt_bind_param($statement, "is",   $userPT , $userID );
 mysqli_stmt_execute($statement);

 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>
