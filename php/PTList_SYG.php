<?php
	  header("Content-Type: text/html; charset=UTF-8");
    $con = mysqli_connect("localhost", "kjg123kg" , "ekgus159!@!@", "kjg123kg");
    mysqli_set_charset($con,"UTF-8");

    $ptYear = $_GET["ptYear"];
    $ptMonth = $_GET["ptMonth"];
    $ptDay = $_GET["ptDay"];
    $ptTrainer = $_GET["ptTrainer"];
    $ptTime = $_GET["ptTime"];

		$ptTrainerName = "무관";
    $ptTimeName    = "무관";

    //strtmp -> 같으면 0을 반환 str1을 str2와 비교해. 크면 > 작으면 <
    //트레이너가 무관이고 시간이 무관이라면
		//PTCOURSE의 ptID와 SCHEDULE의 ptID값을 비교해서 다른값만 출력
		if(!strcmp($ptTrainer , $ptTrainerName) && !strcmp($ptTime , $ptTimeName)) {
        $result = mysqli_query($con , "SELECT * FROM PTCOURSE a left outer join SCHEDULE b on a.ptID = b.ptID
    														WHERE	ptYear = '$ptYear'
												    		 AND ptMonth = '$ptMonth'
												    		 AND ptDay = '$ptDay'
																 AND b.ptID is null
																 ORDER BY a.ptYear DESC, a.ptMonth ASC, a.ptDay ASC, a.ptTime ASC");
        $response = array();
    }

    //트레이너가 무관이고 시간이 무관이아니라면
  	if(!strcmp($ptTrainer , $ptTrainerName)){
      if(strcmp($ptTime , $ptTimeName)){
        $result = mysqli_query($con , "SELECT * FROM PTCOURSE a left outer join SCHEDULE b on a.ptID = b.ptID
    														WHERE ptYear = '$ptYear'
    															AND ptMonth = '$ptMonth'
    															AND ptDay = '$ptDay'
                                  AND ptTime = '$ptTime'
																	AND b.ptID is null
																	ORDER BY a.ptYear DESC, a.ptMonth ASC, a.ptDay ASC, a.ptTime ASC");
        $response = array();
      		}
      }
    // //트레이너가 무관아니고 시간이 무관이라면
    if(strcmp($ptTrainer , $ptTrainerName)){
      if(!strcmp($ptTime , $ptTimeName)){
          $result = mysqli_query($con , "SELECT * FROM PTCOURSE a left outer join SCHEDULE b on a.ptID = b.ptID
      														WHERE ptYear = '$ptYear'
      															AND ptMonth = '$ptMonth'
      															AND ptDay = '$ptDay'
                                    AND ptTrainer = '$ptTrainer'
																		AND b.ptID is null
																		ORDER BY a.ptYear DESC, a.ptMonth ASC, a.ptDay ASC, a.ptTime ASC");
          $response = array();
        		}
        }
    // //트레이너가 무관아니고 시간이 무관이아니라면
    if(strcmp($ptTrainer , $ptTrainerName)){
        if(strcmp($ptTime , $ptTimeName)){
          $result = mysqli_query($con , "SELECT * FROM PTCOURSE a left outer join SCHEDULE b on a.ptID = b.ptID
      														WHERE ptYear = '$ptYear'
      															AND ptMonth = '$ptMonth'
      															AND ptDay = '$ptDay'
      															AND ptTrainer = '$ptTrainer'
      															AND ptTime = '$ptTime'
																		AND b.ptID is null
																		ORDER BY a.ptYear DESC, a.ptMonth ASC, a.ptDay ASC, a.ptTime ASC");
          $response = array();
      }
    }


  	    while($row = mysqli_fetch_array($result)){
  	        array_push($response, array("ptID"=>$row[0],
  	        "ptYear"=>$row[1],
  	        "ptMonth"=>$row[2],
  	        "ptDay"=>$row[3],
  	        "ptTrainer"=>$row[4],
  	        "ptTime"=>$row[5]));
  	    }

    echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
    mysqli_close($con);
?>
