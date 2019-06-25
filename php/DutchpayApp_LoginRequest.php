<?php
 header("Content-Type: text/html; charset=UTF-8");
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");
 mysqli_set_charset($con,"UTF-8");


 $userEmail = $_GET["userEmail"];
 $userPassword = $_GET["userPassword"];

 $result = mysqli_query($con, "SELECT * FROM DUTCHPAYAPP_USER_INFOMATION WHERE userEmail = '$userEmail' AND userPassword = '$userPassword'");
$row = mysqli_fetch_array($result );
$userInfo = array();
$userInfo["userEmail"] = $row[0];
$userInfo["userPassword"] = $row[1];
$userInfo["userPaymentPassword"] = $row[2];
$userInfo["userName"] = $row[3];
$userInfo["userRN"] = $row[4];
$userInfo["userGender"] = $row[5];
$userInfo["userPhone"] = $row[6];
$userInfo["userMoney"] = $row[7];
if($userInfo["userEmail"] == null){
echo json_encode(null);
}
 else {
echo json_encode($userInfo);
}
 
 mysqli_close($con);
?>
