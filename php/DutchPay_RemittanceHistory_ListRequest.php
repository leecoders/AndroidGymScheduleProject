<?php
 header("Content-Type: text/html; charset=UTF-8");
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");
 mysqli_set_charset($con,"UTF-8");


 $userID = $_GET["userID"];

 $result = mysqli_query($con, "SELECT * FROM `DUTCHPAY_REMITTANCE_HISTORY` WHERE `remittererID` = '$userID' OR receiverID = '$userID' ORDER By date DESC ");

 $response = array();

 while($row = mysqli_fetch_array($result)){
   array_push($response, array("remittanceID"        =>$row[0] ,
                               "remittanceAmount"  =>$row[1] ,
                               "remittererID"      =>$row[2] ,
                               "receiverID"     =>$row[3] ,
                               "date"=>$row[4]));
 }

 echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
 mysqli_close($con);
?>
