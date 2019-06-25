<?php
 header("Content-Type: text/html; charset=UTF-8");
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");
 mysqli_set_charset($con,"UTF-8");


 $Time = $_GET["Time"];

 $result = mysqli_query($con, "SELECT ISBY_Order_Before.orderID , ISBY_Order_Before.Time , ISBY_Order_Before.OrderMan , ISBY_Order_Before.OrderList , ISBY_Order_Before.Approve FROM ISBY_Order_Before where ISBY_Order_Before.Time = '$Time'  ");

 $response = array();

while($row = mysqli_fetch_array($result)){
  array_push($response, array("orderID"       => $row[0],
                              "Time"       => $row[1],
                              "OrderMan"       => $row[2],
                              "OrderList"   => $row[3],
                              "Approve"    => $row[4] ));
}

 echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
 mysqli_close($con);
?>
