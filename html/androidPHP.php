<?

 $mydb=mysql_connect("localhost","danaeri","11bokslim");//db에 접속하기
 $db_id=mysql_select_db('danaeri',$mydb);




 $id = $_POST['id'];

$password = $_POST['password'];

$query_search = "select * from androidApp where user_id = '".$id."' AND user_passwd = '".$password."'";

$query_exec = mysql_query($query_search) or die(mysql_error());

$rows = mysql_num_rows($query_exec);

 

 if($rows == 0) { 

 echo "No Such User Found"; 

 }

 else  {

    echo "User Found"; 

}



?>