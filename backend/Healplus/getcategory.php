<?php 
include "connect.php";
$sql = "SELECT * FROM `category`";
$result = $conn ->query($sql);
$category = [];
while ($row = $result ->fetch_assoc()){
	$category[] = $row;
}
if (!empty($category)) {
	// code...
	$arr = [
		'success' => true,
		'message' => "Successfully category",
		'result' => $result
	];
}else{
	$arr  = [
		'success' => false,
		'message' => "Unsccessfully category",
		'result' => $result
	];
}

echo json_encode($category);
$conn ->close();

 ?>
