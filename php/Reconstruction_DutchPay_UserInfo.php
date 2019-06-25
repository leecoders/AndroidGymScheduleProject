<?php
 header("Content-Type: text/html; charset=UTF-8");
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");
 mysqli_set_charset($con,"UTF-8");


 $userID = $_GET["userID"];

 $result = mysqli_query($con, "SELECT * FROM DUTCHPAY_USER_INFOMATION WHERE userID = '$userID'");

 $userInfomations = array();

 while($row = mysqli_fetch_array($result)){
   array_push($userInfomations, array("userID"        =>$row[0] ,
                               "userPassword"  =>$row[1] ,
		       "userPaymentPassword"  =>$row[2] ,
                               "userName"      =>$row[3] ,
		       "userPhoneNumber"      =>$row[4] ,
                               "userEmail"     =>$row[5] ,
                               "userDutchMoney"=>$row[6] ,
                               "userState"     =>$row[7] ,
			"userPushID"     =>$row[8]));
 }

 echo json_encode(array("userInfomations"=>$userInfomations), JSON_UNESCAPED_UNICODE);
 mysqli_close($con);
?>
