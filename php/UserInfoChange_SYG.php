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

 $userValue = $_POST["userValue"];

 $a = "a";

//forAdmin
if(!strcmp($userValue ,$a)){
 $statement = mysqli_prepare($con, "UPDATE GYMUSER set userPassword = ? , userName = ? , userEmail = ? , userGender =? , userHeight = ? , userWeight = ? , userAge = ? , userPT = ? WHERE userID = ?");
 mysqli_stmt_bind_param($statement, "sssssssis", $userPassword, $userName, $userEmail , $userGender, $userHeight, $userWeight, $userAge,  $userPT , $userID );
 mysqli_stmt_execute($statement);
}

//forUser
else {
 $statement = mysqli_prepare($con, "UPDATE GYMUSER set userPassword = ? , userName = ? , userEmail = ? , userGender =? , userHeight = ? , userWeight = ? , userAge = ? WHERE userID = ?");
 mysqli_stmt_bind_param($statement, "ssssssss", $userPassword, $userName, $userEmail , $userGender, $userHeight, $userWeight, $userAge, $userID );
 mysqli_stmt_execute($statement);
}

 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>
