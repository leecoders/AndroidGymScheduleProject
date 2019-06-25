<?php
    $con = mysqli_connect("localhost" ,"kjg123kg","ekgus159!@!@","kjg123kg");

    $userID = $_POST["userID"];

    $statement = mysqli_prepare($con , "DELETE FROM GYMUSER WHERE userID = ?");
    mysqli_stmt_bind_param($statement , "s" , $userID);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
 ?>
