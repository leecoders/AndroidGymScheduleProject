<?php
  header("Content-Type: text/html; charset=UTF-8");
  $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");
  mysqli_set_charset($con,"UTF-8");

  $result = mysqli_query($con , "SELECT * FROM GYMUSER");

  $response = array();

  while($row = mysqli_fetch_array($result)){
    array_push($response, array("userID"        =>$row[0] ,
                                "userPassword"  =>$row[1] ,
                                "userName"      =>$row[2] ,
                                "userEmail"     =>$row[3] ,
                                "userGender"    =>$row[4] ,
                                "userHeight"    =>$row[5] ,
                                "userWeight"    =>$row[6] ,
                                "userAge"       =>$row[7] ,
			                          "userValue"     =>$row[8] ,
                                "userPT"        =>$row[9]));
  }

  echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
  mysqli_close($con);
?>
