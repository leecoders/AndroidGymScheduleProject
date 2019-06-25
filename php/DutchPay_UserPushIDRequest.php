<?php
 header("Content-Type: text/html; charset=UTF-8");
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");
 mysqli_set_charset($con,"UTF-8");


 $userID = $_GET["userID"];

 $result = mysqli_query($con, "SELECT userPushID FROM DUTCHPAY_USER_INFOMATION WHERE userID IN (SELECT * FROM(SELECT userID FROM DUTCHPAY_PAYMENT_INFORMATION_TEMPORARY_STORAGE WHERE hostID = '$userID')AS X)");
 $response = array();

 while($row = mysqli_fetch_array($result)){
   array_push($response, array("userPushID"        =>$row[0]));
 }

 echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
 mysqli_close($con);
?>
