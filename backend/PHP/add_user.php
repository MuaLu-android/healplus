<?php 
include "connect.php";
$name = $_POST['name'];
$email = $_POST['email'];
$password = $_POST['password'];
$phone = $_POST['phone'];
$url = $_POST['url'];
$role = $_POST['role'];
$userId = generateRandomId('user_');
$query = 'SELECT * FROM `user` WHERE `email` = "'.$email.'"';
$data = mysqli_query($conn, $query);
$numrow = mysqli_num_rows($data);
if ($numrow > 0) {
	$arr =[
		'success' => true, 
		'message' => "Email da ton tai"
	];
		
}else{
	$query = 'INSERT INTO `user`(`id`, `name`, `email`, `password`, `phone`,`url`,`role`) VALUES ("'.$userId.'", "'.$name.'","'.$email.'","'.$password.'","'.$phone.'","'.$url.'","'.$role.'")';
    $data = mysqli_query($conn, $query);
    if ($data == true) {
		$arr = [
			'success' => true,
			'message' => "thanh cong"
		];	
    }else{

		$arr = [
			'success' => false,
			'message' => "khong thanh cong"
		];
	 }
}
print_r(json_encode($arr));
 ?>