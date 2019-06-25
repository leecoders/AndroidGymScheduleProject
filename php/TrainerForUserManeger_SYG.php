<?php
 header("Content-Type: text/html; charset=UTF-8");
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");
 mysqli_set_charset($con,"UTF-8");


 $trainerID = $_GET["trainerID"];

 $result = mysqli_query($con, "SELECT GYMUSER.userID ,  GYMUSER.userName , GYMUSER.userEmail , GYMUSER.userGender , GYMUSER.userHeight , GYMUSER.userWeight , GYMUSER.userAge , PTCOURSE.ptYear , PTCOURSE.ptMonth , PTCOURSE.ptDay , PTCOURSE.ptTime
                               FROM SCHEDULE , PTCOURSE , GYMUSER
                               WHERE PTCOURSE.ptID
                               IN (SELECT PTCOURSE.ptID FROM PTCOURSE WHERE PTCOURSE.ptTrainerID ='$trainerID' )
                               AND PTCOURSE.ptID = SCHEDULE.ptID
                               AND SCHEDULE.userID = GYMUSER.userID");

 $response = array();

while($row = mysqli_fetch_array($result)){
  array_push($response, array("userID"       => $row[0],
                              "userName"     => $row[1],
                              "userEmail"    => $row[2],
                              "userGender"   => $row[3],
                              "userHeight"   => $row[4],
                              "userWeight"   => $row[5],
                              "userAge"      => $row[6],
                              "ptYear"       => $row[7],
                              "ptMonth"      => $row[8],
                              "ptDay"        => $row[9],
                              "ptTime"       => $row[10]));
}

 echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
 mysqli_close($con);
?>
