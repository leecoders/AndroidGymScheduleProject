<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");

 $Restaurant_ID = $_POST["Restaurant_ID"];
 $Restaurant_Name = $_POST["Restaurant_Name"];
 $Restaurant_Category = $_POST["Restaurant_Category"];
 $Restaurant_Gu = $_POST["Restaurant_Gu"];
 $Restaurant_Dong = $_POST["Restaurant_Dong"];

 $statement = mysqli_prepare($con, "INSERT INTO WAIT_REGISTER_RESTAURANT VALUES(?, ?, ?, ?, ?)");
 mysqli_stmt_bind_param($statement, "sssss", $Restaurant_ID, $Restaurant_Name, $Restaurant_Category, $Restaurant_Gu, $Restaurant_Dong);
 mysqli_stmt_execute($statement);

 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>
