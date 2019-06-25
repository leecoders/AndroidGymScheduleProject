<?php

/* http://braverokmc2.dothome.co.kr/Register.php?userID=q1&userPassword=1111&userName=김길&userAge=89 */

	$con =mysql_connect("localhost", "kjg123kg", "ekgus159!@!@", "kjg123kg");


/* 	3. Mysql 입출력 인코딩을 지정
	Mysql 연동을 한다면 입출력 인코딩이 달라서가 원인이 될 수 있다고 합니다.
	아래 3줄의 소스를 추가함으로써 해결하였습니다.

	출처: http://jhrun.tistory.com/140 [JHRunning] */

	mysql_query("set session character_set_connection=utf8;");

	mysql_query("set session character_set_results=utf8;");

	mysql_query("set session character_set_client=utf8;");





	mysql_set_charset("SET NAMES utf8");
	if($con==true){

		//echo "연결";

		//Injection 방어
		$_POST = array_map('mysql_escape_string', $_POST);
		$_GET=array_map('mysql_escape_string', $_GET);

		if(isset($_POST["userID"])){
			$userID =$_POST["userID"];
			$userPassword=$_POST["userPassword"];
			$userName=$_POST["userName"];
			$userAge=$_POST["userAge"];

		}else if(isset($_GET["userID"])	){
			$userID =$_GET["userID"];
			$userPassword=$_GET["userPassword"];
			$userName=$_GET["userName"];
			$userAge=$_GET["userAge"];
		}

	/* 	//한글 인코딩 확인 후 utf8 아니 면 utf8 로 변경
		$enc=mb_detect_encoding($userName, array("UTF-8", "EUC-KR", "SJIS"));
		if($userName !="UTF-8"){
			$userName=iconv($enc, "UTF-8", $userName);
		}

		 *
		 *
		 */
		$stmt= "insert into USER_HWANG values ( '$userID', '$userPassword', '$userName' , $userAge  ) ";
		mysql_query( $stmt);



	/* 	mysqli_stmt_bind_param($statement, "sssi", $userID, $userPassword, $userName, $userAge);
		mysqli_stmt_execute($statement);
		 */
		$response=array();
		$response["success"] =true;

		//echo $userName;
		echo json_encode($response);

	}

?>
