<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
		$name = $_POST['name'];
		$username = $_POST['username'];
		$password = $_POST['password'];
		$email = $_POST['email'];
		
		if($name == '' || $username == '' || $password == '' || $email == ''){
			echo 'please fill all values';
		}else{
			//require_once('subPHP.php');
			define('HOST','localhost');
			define('USERNAME','danaeri');
			define('PASSWORD','11bokslim');
			define('DB','danaeri');

			$con = mysqli_connect(HOST,USERNAME,PASSWORD,DB) or die('unable to connect');

			$sql = "SELECT * FROM androidApp WHERE username='$username' OR email='$email'";
			
			$check = mysqli_fetch_array(mysqli_query($con,$sql));
			
			if(isset($check)){
				echo 'username or email already exist';
			}else{				
				$sql = "INSERT INTO androidApp (name,username,password,email) VALUES('$name','$username','$password','$email')";
				if(mysqli_query($con,$sql)){
					echo 'successfully registered';
				}else{
					echo 'oops! Please try again!';
				}
			}
			mysqli_close($con);
		}
}else{
echo 'error';
}

?>