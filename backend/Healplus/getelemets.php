<?php
include "connect.php";
$sql = "SELECT * FROM `element`";
$result = $conn->query($sql);
$element = [];
while ($row = $result->fetch_assoc()) {
    $element[] = $row;
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

echo json_encode($element);
$conn->close();
?>