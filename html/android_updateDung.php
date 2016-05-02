<?
	/*define('HOST','localhost');
	define('USERNAME','danaeri');
	define('PASSWORD','11bokslim');
	define('DB','danaeri');

	$con = mysqli_connect(HOST,USERNAME,PASSWORD,DB) or die('unable to connect');
	echo "here";
*/
	 $mydb=mysql_connect("localhost","danaeri","11bokslim");//db에 접속하기
	$db_id=mysql_select_db('danaeri',$mydb);

	mysql_query("update androidApp set dung=dung+1");

	echo "success";
	

?>