<?
	define('HOST','localhost');
	define('USERNAME','danaeri');
	define('PASSWORD','11bokslim');
	define('DB','danaeri');

//$db = mysqli_connect('호스트(localhost)','사용자(root)','DB비밀번호(password)','사용할 db명(testDB)') or die ('fail to connect');
//$db에는 db에 대한 정보가 담겨있음
//$query = "select * from test";
//$data=mysqli_query($db,$query);->쿼리와 디비를 연결시켜 수행함,쿼리의 결과가 data변수에 들어감
/*그리고 

while($result = mysqli_fetch_array($data)){

echo $result['애트리뷰트 이름'];

}이렇게 while문으로 mysqli_fetch_array($data)를 를 돌면서 각각의 튜플들의 결과값이 $result에 저장이 되고 while문 안의 $result에서는 배열에 접근하는 것과 같이 $result['애트리뷰트 이름']; 으로 각각의 레코드 값을 읽어올 수 있습니다.




마지막으로 mysqli_close($db); 를 해주세요~*/

	$con = mysqli_connect(HOST,USERNAME,PASSWORD,DB) or die('unable to connect');
	$user_name = $_POST["user_name"];
	$user_pass = $_POST["password"];

	$user_name='r';
	$user_pass = 'r';


	$mysql_qry = "select * from androidApp where username like '$user_name' and password like '$user_pass'";
	$result = mysqli_query($con,$mysql_qry);
	$data=mysqli_fetch_array($result);

	echo $data[5]."/".$data[6];




?>