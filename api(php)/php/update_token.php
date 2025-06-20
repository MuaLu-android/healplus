<?php 
include "connect.php";
$token = $_POST['token'];
$uid = $_POST['uid'];
//check data
$query = 'UPDATE product SET review = review + 1 WHERE `uid`="'.$uid.'"';
    $data = mysqli_query($conn, $query);
    if ($data == true) {
		$arr = [
			'success' => true,
			'message' => "khong thanh cong"
		];	
		// code...
    }else{
		$arr = [
			'success' => false,
			'message' => "khong thanh cong"
		];
	 }

print_r(json_encode($arr));

 ?>