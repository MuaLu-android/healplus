<?php 
include "connect.php";
$idauth = $_POST['idauth'];
$id = $_POST['id'];
$query = 'UPDATE `user` SET `idauth`="'.$token.'" WHERE `id`="'.$id.'"';
    $data = mysqli_query($conn, $query);
    if ($data == true) {
		$arr = [
			'success' => true,
			'message' => "khong thanh cong"
		];
    }else{
		$arr = [
			'success' => false,
			'message' => "khong thanh cong"
		];
	 }

print_r(json_encode($arr));

 ?>