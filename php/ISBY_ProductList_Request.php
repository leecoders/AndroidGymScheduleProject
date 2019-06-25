<?php
 header("Content-Type: text/html; charset=UTF-8");
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");
 mysqli_set_charset($con,"UTF-8");


 $result = mysqli_query($con, "SELECT * FROM ISBY_Product ORDER BY Product ASC");

 $response = array();

while($row = mysqli_fetch_array($result)){
  array_push($response, array("Product"       => $row[0],
                              "Price"     => $row[1],
                              "Quantity" => $row[2] ));
}

 echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
 mysqli_close($con);
?>
