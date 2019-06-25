<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");


 $Price = $_POST["Price"];
 $Product = $_POST["Product"];
 $Product1 = $_POST["Product1"];

 $statement = mysqli_prepare($con, "UPDATE ISBY_Product set Product = ? , Price = ? WHERE Product = ? ");
 mysqli_stmt_bind_param($statement, "sis", $Product , $Price ,$Product1);
 mysqli_stmt_execute($statement);

 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>
