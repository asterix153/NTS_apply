<?
	define('HOST','localhost');
	define('USERNAME','danaeri');
	define('PASSWORD','11bokslim');
	define('DB','danaeri');

//$db = mysqli_connect('ȣ��Ʈ(localhost)','�����(root)','DB��й�ȣ(password)','����� db��(testDB)') or die ('fail to connect');
//$db���� db�� ���� ������ �������
//$query = "select * from test";
//$data=mysqli_query($db,$query);->������ ��� ������� ������,������ ����� data������ ��
/*�׸��� 

while($result = mysqli_fetch_array($data)){

echo $result['��Ʈ����Ʈ �̸�'];

}�̷��� while������ mysqli_fetch_array($data)�� �� ���鼭 ������ Ʃ�õ��� ������� $result�� ������ �ǰ� while�� ���� $result������ �迭�� �����ϴ� �Ͱ� ���� $result['��Ʈ����Ʈ �̸�']; ���� ������ ���ڵ� ���� �о�� �� �ֽ��ϴ�.




���������� mysqli_close($db); �� ���ּ���~*/

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