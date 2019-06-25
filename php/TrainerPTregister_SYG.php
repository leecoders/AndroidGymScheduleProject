<?php
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");

 $ptID = "";
 $year = $_POST["year"];
 $month = $_POST["month"];
 $day = $_POST["day"];
 $trainer = $_POST["trainer"];
 $time = $_POST["time"];
 $trainerID = $_POST["trainerID"];


 $statement = mysqli_prepare($con, "INSERT INTO PTCOURSE VALUES( ? ,?, ?, ?, ?, ?, ?)");
 mysqli_stmt_bind_param($statement, "sssssss",$ptID ,  $year, $month, $day, $trainer , $time, $trainerID);
 mysqli_stmt_execute($statement);

 $response = array();
 $response["success"] = true;

 echo json_encode($response);
?>
