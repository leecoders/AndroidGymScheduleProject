<?php
 header("Content-Type: text/html; charset=UTF-8");
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");
 mysqli_set_charset($con,"UTF-8");


 $trainerID = $_GET["trainerID"];

 $result = mysqli_query($con, "SELECT GYMUSER.userID , PTCOURSE.ptID , PTCOURSE.ptYear ,  PTCOURSE.ptMonth ,PTCOURSE.ptDay , PTCOURSE.ptTime , PTCOURSE.ptTrainer , SCHEDULE.FeedBackValue
                               FROM SCHEDULE , PTCOURSE , GYMUSER
                               WHERE PTCOURSE.ptID
                               IN (SELECT PTCOURSE.ptID FROM PTCOURSE WHERE PTCOURSE.ptTrainerID ='$trainerID' )
                               AND PTCOURSE.ptID = SCHEDULE.ptID
                               AND SCHEDULE.userID = GYMUSER.userID
                               GROUP BY PTCOURSE.ptID
                               ORDER BY PTCOURSE.ptYear DESC, PTCOURSE.ptMonth ASC, PTCOURSE.ptDay ASC, PTCOURSE.ptTime ASC");

 $response = array();

while($row = mysqli_fetch_array($result)){
  array_push($response, array("userID"   	=> $row[0],
                              "ptID"      	=> $row[1],
                              "ptYear"   	=> $row[2],
                              "ptMonth"  	=> $row[3],
                              "ptDay"    	=> $row[4],
                              "ptTime"   	=> $row[5],
                              "ptTrainer"  	=> $row[6],
			      "FeedBackValue"   => $row[7]));
}

 echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
 mysqli_close($con);
?>
