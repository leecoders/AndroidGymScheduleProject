<?php
    $con = mysqli_connect("localhost", "kjg123kg" , "ekgus159!@!@", "kjg123kg");

    $result = mysqli_query($con , "SELECT * FROM USER;");
    $response = array();

    while($row = mysqli_fetch_array($result)){
        array_push($response, array(
        "userID"        =>  $row[0],
        "userPassword"  =>  $row[1],
        "userGender"    =>  $row[2],
        "userMajor"     =>  $row[3),
        "userEmail"     =>  $row[4]
      );
    }
    echo json_encode(array("response"=>$response));
    mysqli_close($con);
?>
