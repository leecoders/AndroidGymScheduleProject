<?php
 header("Content-Type: text/html; charset=UTF-8");
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");
 mysqli_set_charset($con,"UTF-8");


 $trainerName = $_GET["trainerName"];

 $result = mysqli_query($con, "SELECT userName , userEmail , userHeight , userWeight , userAge FROM GYMUSER WHERE userName = '$trainerName'");

 $response = array();

while($row = mysqli_fetch_array($result)){
  array_push($response, array("userName" => $row[0],
                              "userEmail" => $row[1],
                              "userHeight" => $row[2],
                              "userWeight" => $row[3],
                              "userAge" => $row[4]));
}

 echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
 mysqli_close($con);
?>
