<?php
 header("Content-Type: text/html; charset=UTF-8");
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");
 mysqli_set_charset($con,"UTF-8");


 $userID = $_GET["userID"];

 $result = mysqli_query($con, "SELECT * FROM DUTCHPAY_PAYMENT_INFORMATION_TEMPORARY_STORAGE WHERE hostID = '$userID'");

 $response = array();

 while($row = mysqli_fetch_array($result)){
   array_push($response, array("hostID"        =>$row[0] ,
                               "userID"  =>$row[1] ,
                               "Amount"      =>$row[2] ,
"assignedAmount"      =>$row[3] ,
"prePayment"     =>$row[4]));
 }

 echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
 mysqli_close($con);
?>
