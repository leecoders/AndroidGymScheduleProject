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

	if($con==true){
	//	echo "yes 연결 되었습니다.";

		$_POST = array_map('mysql_escape_string', $_POST);
		$_GET=array_map('mysql_escape_string', $_GET);

		if(isset($_GET["userID"])){
			$userID =$_GET["userID"];
			$userPassword =$_GET["userPassword"];

		}else if(isset($_POST["userID"])){
			$userID =$_POST["userID"];
			$userPassword =$_POST["userPassword"];
		}


		if(isset($userID) && isset($userPassword)){


				$stmt = "select * from USER_HWANG where `userID` = '$userID' and `userPassword` = '$userPassword' ";
				$rs =mysql_query($stmt, $con);

				if($rs === FALSE) {
					die(mysql_error()); // TODO: better error handling
				}


	/*
				echo "<table border='1' >";
				echo
					"<tr>
				<th>번호</th>
				<th>이름</th>
				<th>내용</th>
				<th>이메일</th>
				<th>날짜</th>
				</tr>
				"; */

				$response =array();
				$response["success"]=false;

			if($row=mysql_fetch_array($rs)){

				$response["success"]=true;
				$response["userID"]=$row[userID];
				$response["userPassword"]=$row[userPassword];
				$response["userName"]=$row[userName];
				$response["userAge"]=$row[userAge];
			/* 	echo "
				<tr>
				<td>$row[userID]</td>
				<td>$row[userPassword] </td>
				<td>$row[userName] </td>
				<td>$row[userAge]</td>
				<td></td>

				</tr>"; */

			}


			echo json_encode($response);

			mysql_close();


		}


	}else{
		//echo "no - 연결 실패 하였습습니다.";
	}







?>
