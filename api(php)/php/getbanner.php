<?php
include "connect.php";
$sql = "SELECT * FROM `banner`";
$result = $conn->query($sql);
$banners = [];
while ($row = $result->fetch_assoc()) {
    $banners[] = $row;
}
if (!empty($result)) {
    $arr = [
        'success' => true,
        'message' => "thanh cong",
        'result'  => $result
    ];
    // code...
}else{

    $arr = [
        'success' => false,
        'message' => "khong thanh cong",
        'result'  => $result
    ];
}

echo json_encode($banners);
$conn->close();
?>