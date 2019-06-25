<?php
    $con = mysqli_connect("localhost" ,"kjg123kg","ekgus159!@!@","kjg123kg");

    $userID = $_POST["userID"];
    $ptID   = $_POST["ptID"];

    $statement = mysqli_prepare($con , "DELETE FROM SCHEDULE WHERE userID = '$userID' AND ptID = '$ptID'");
    mysqli_stmt_bind_param($statement , "si" , $userID , $ptID);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
 ?>
