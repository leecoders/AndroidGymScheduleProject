<?php

	//http://braverokmc2.dothome.co.kr/Login.php?userID=hong&userPassword=1111


	/* mysql_connect('localhost', 'mysql_user', 'mysql_password'); */
	$con =mysql_connect("localhost", "kjg123kg", "ekgus159!@!@", "kjg123kg");


	/* 	3. Mysql 입출력 인코딩을 지정
	 Mysql 연동을 한다면 입출력 인코딩이 달라서가 원인이 될 수 있다고 합니다.
	아래 3줄의 소스를 추가함으로써 해결하였습니다.

	출처: http://jhrun.tistory.com/140 [JHRunning] */

	mysql_query("set session character_set_connection=utf8;");

	mysql_query("set session character_set_results=utf8;");

	mysql_query("set session character_set_client=utf8;");



	mysql_set_charset("utf8");//sql 에서 한글 깨짐
	$stmt ="select * from USER_HWANG ";
	$rs =mysql_query($stmt, $con);
	$response =array();

	while($row =mysql_fetch_array($rs)){
		array_push($response, array("userID" =>$row[0], "userPassword"=>$row[1],
			"userName"=>$row[2],  "userAge" =>$row[3] ) );
	}

	echo json_encode(array("response" =>$response));

	mysql_close($con);









?>
