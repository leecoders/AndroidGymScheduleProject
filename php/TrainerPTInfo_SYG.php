<?php
 header("Content-Type: text/html; charset=UTF-8");
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");
 mysqli_set_charset($con,"UTF-8");


 $trainerID = $_GET["trainerID"];

 $result = mysqli_query($con, "SELECT ptID , ptYear , ptMonth , ptDay , ptTime FROM PTCOURSE
                               WHERE ptTrainerID = '$trainerID'
                               ORDER BY ptYear , ptMonth , ptDay");

 $response = array();

while($row = mysqli_fetch_array($result)){
  array_push($response, array("ptID"       => $row[0],
                              "ptYear"     => $row[1],
                              "ptMonth"    => $row[2],
                              "ptDay"      => $row[3],
                              "ptTime"     => $row[4]));
}

 echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
 mysqli_close($con);
?>
