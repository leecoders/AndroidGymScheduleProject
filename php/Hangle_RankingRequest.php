<?php
 header("Content-Type: text/html; charset=UTF-8");
 $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");
 mysqli_set_charset($con,"UTF-8");


 $result = mysqli_query($con, "SELECT * FROM HangleKing_Ranking ORDER BY Score DESC ,  MaxCombo DESC ,CorrectWord DESC");

 $response = array();

while($row = mysqli_fetch_array($result)){
  array_push($response, array("Name"       => $row[0],
                              "Score"     => $row[1],
                              "CorrectWord"    => $row[2],
                              "WrongWord"      => $row[3],
                              "MaxCombo"  => $row[4] ));
}

 echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
 mysqli_close($con);
?>
