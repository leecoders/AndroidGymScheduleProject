<?php
 header("Content-Type: text/html; charset=UTF-8");
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");
 mysqli_set_charset($con,"UTF-8");


 $userID = $_GET["userID"];

 $result = mysqli_query($con, "SELECT PTCOURSE.ptID , PTCOURSE.ptYear , PTCOURSE.ptMonth , PTCOURSE.ptDay , PTCOURSE.ptTrainer , PTCOURSE.ptTime ,  SCHEDULE.FeedBackValue
                               FROM SCHEDULE , PTCOURSE
                               WHERE SCHEDULE.ptID
                               IN (SELECT SCHEDULE.ptID FROM SCHEDULE WHERE SCHEDULE.userID ='$userID' )
                               AND SCHEDULE.ptID = PTCOURSE.ptID
                               GROUP BY SCHEDULE.ptID
                               ORDER BY PTCOURSE.ptYear ASC, PTCOURSE.ptMonth ASC, PTCOURSE.ptDay ASC, PTCOURSE.ptTime ASC  ");

 $response = array();

while($row = mysqli_fetch_array($result)){
  array_push($response, array("ptID"       => $row[0],
                              "ptYear"     => $row[1],
                              "ptMonth"    => $row[2],
                              "ptDay"      => $row[3],
                              "ptTrainer"  => $row[4],
                              "ptTime"     => $row[5],
                              "FeedBackValue"   => $row[6] ));
}

 echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
 mysqli_close($con);
?>
