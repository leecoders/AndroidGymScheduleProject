<?php
 header("Content-Type: text/html; charset=UTF-8");
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");
 mysqli_set_charset($con,"UTF-8");


 $userPhoneNumber = $_GET["userPhoneNumber"];

 $result = mysqli_query($con, "SELECT * FROM DUTCHPAY_USER_INFOMATION WHERE userPhoneNumber = '$userPhoneNumber'");

 $response = array();

 while($row = mysqli_fetch_array($result)){
   array_push($response, array("userID"       		  =>$row[0] ,
                               "userName"    		  =>$row[3] ,
		      				   "userPhoneNumber"      =>$row[4] ,
                               "userDutchMoney"		  =>$row[6] ,
							   "userPushID"    		  =>$row[8]));
 }

 echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
 mysqli_close($con);
?>
