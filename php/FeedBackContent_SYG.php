<?php
    header("Content-Type: text/html; charset=UTF-8");
    $con = mysqli_connect("localhost", "kjg123kg" , "ekgus159!@!@", "kjg123kg");
    mysqli_set_charset($con,"utf8");

    $ptID = $_GET["ptID"];

    $result = mysqli_query($con , "SELECT FeedBack FROM SCHEDULE WHERE ptID = '$ptID';");
    $response = array();

    while($row = mysqli_fetch_array($result)){
      array_push($response, array("FeedBack" => $row[0]));
    }

    echo json_encode(array("response"=>$response));
    mysqli_close($con);
?>
