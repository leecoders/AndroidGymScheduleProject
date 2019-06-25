<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");

 $Product = $_POST["Product"];

 $statement = mysqli_prepare($con, "DELETE FROM ISBY_Product WHERE Product = ? ");
 mysqli_stmt_bind_param($statement, "s", $Product);
 mysqli_stmt_execute($statement);

 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>
