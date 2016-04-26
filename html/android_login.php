<?php

	define('HOST','localhost');
	define('USERNAME','danaeri');
	define('PASSWORD','11bokslim');
	define('DB','danaeri');

	$con = mysqli_connect(HOST,USERNAME,PASSWORD,DB) or die('unable to connect');
	$user_name = $_POST["user_name"];
	$user_pass = $_POST["password"];
	$mysql_qry = "select * from androidApp where username like '$user_name' and password like '$user_pass'";
	$result = mysqli_query($con,$mysql_qry);
	if(mysqli_num_rows($result)>0){
		echo "login success";
	}
	else{
		echo "not success";
	}

?>