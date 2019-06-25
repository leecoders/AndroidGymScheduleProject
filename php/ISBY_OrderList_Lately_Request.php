<?php
    header("Content-Type: text/html; charset=UTF-8");
    $con = mysqli_connect("localhost","kjg123kg","ekgus159!@!@","kjg123kg");
    mysqli_set_charset($con,"UTF-8");
    
    
    $result = mysqli_query($con, "SELECT ISBY_Order_Before.Time , ISBY_Order_Before.OrderList FROM ISBY_Order_Before ORDER BY Time DESC limit 1");
    
    $response = array();
    
    while($row = mysqli_fetch_array($result)){
        array_push($response, array("Time"       => $row[0],
                                    "OrderList"     => $row[1]));
    }
    
    echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
    mysqli_close($con);
    ?>
