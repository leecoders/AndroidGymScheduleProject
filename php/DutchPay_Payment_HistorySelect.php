<?php
 header("Content-Type: text/html; charset=UTF-8");
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");
 mysqli_set_charset($con,"UTF-8");


 $userID = $_GET["userID"];

 $result = mysqli_query($con, "SELECT * FROM `DUTCHPAY_PAYMENT_HISTORY` WHERE `participantName` LIKE '%$userID%'");

 $response = array();

 while($row = mysqli_fetch_array($result)){
   array_push($response, array("historyID"        =>$row[0] ,
                               "Date"  =>$row[1] ,
                               "companyName"      =>$row[2] ,
                               "participantName"     =>$row[3] ,
                               "Amount"=>$row[4]));
 }

 echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
 mysqli_close($con);
?>
