<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");

 $userID = $_POST["userID"];
 $userPassword = $_POST["userPassword"];
 $userName = $_POST["userName"];
 $userEmail = $_POST["userEmail"];
 $userGender = $_POST["userGender"];
 $userHeight = $_POST["userHeight"];
 $userWeight = $_POST["userWeight"];
 $userAge = $_POST["userAge"];
 $userValue = $_POST["userValue"];
 $userPT = $_POST["userPT"];
 
 $statement = mysqli_prepare($con, "INSERT INTO GYMUSER VALUES(?, ?, ?, ?, ?, ?, ?, ?, ? ,?)");
 mysqli_stmt_bind_param($statement, "ssssssssss", $userID, $userPassword, $userName, $userEmail , $userGender, $userHeight, $userWeight, $userAge, $userValue , $userPT);
 mysqli_stmt_execute($statement);

 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>
