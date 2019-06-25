<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");
 
 $Quantity = $_POST["Quantity"];
 $Product = $_POST["Product"];

 $statement = mysqli_prepare($con, "UPDATE ISBY_Product set Quantity = ? WHERE Product = ? ");
 mysqli_stmt_bind_param($statement, "is", $Quantity , $Product);
 mysqli_stmt_execute($statement);

 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>
