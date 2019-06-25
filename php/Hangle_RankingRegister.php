<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");

 $Name = $_POST["Name"];
 $Score = $_POST["Score"];
 $CorrectWord = $_POST["CorrectWord"];
 $WrongWord = $_POST["WrongWord"];
 $MaxCombo = $_POST["MaxCombo"];

 $statement = mysqli_prepare($con, "INSERT INTO HangleKing_Ranking VALUES(?, ?, ?, ?, ?)");
 mysqli_stmt_bind_param($statement, "siiii", $Name , $Score, $CorrectWord, $WrongWord, $MaxCombo);
 mysqli_stmt_execute($statement);

 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>
