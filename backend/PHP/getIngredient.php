<?php
include "connect.php";
$sql = "SELECT * FROM `ingredient`";
$result = $conn->query($sql);
$ingredient = [];
while ($row = $result->fetch_assoc()) {
    $ingredient[] = $row;
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

echo json_encode($ingredient);
$conn->close();
?>