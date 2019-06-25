<?php
    header("Content-Type: text/html; charset=UTF-8");
    $con = mysqli_connect("localhost", "kjg123kg" , "ekgus159!@!@", "kjg123kg");


    $userID = $_GET["userID"];

    $result = mysqli_query($con , "SELECT COURSE.courseID , COURSE.courseTime , COURSE.courseProfessor FROM USER , COURSE ,
      SCHEDULE WHERE USER.userID = '$userID' AND USER.userID = SCHEDULE.userID AND SCHEDULE.courseID = COURSE.courseID");

    $response = array();
    while($row = mysqli_fetch_array($result)){
        array_push($response, array("courseID"=>$row[0], "courseTime"=>row[1], "courseProfessor"=>$row[2]));
    }

    echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
    mysqli_close($con);
?>
