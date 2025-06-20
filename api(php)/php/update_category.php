<?php
include "connect.php";

if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['idc']) && isset($_POST['title'])) {
    $idc = trim($_POST['idc']);
    $title = trim($_POST['title']);

    if (!empty($idc) && !empty($title)) {
        $sqlUpdate = "UPDATE category SET title = '$title' WHERE idc = '$idc'";
        if ($conn->query($sqlUpdate) === TRUE) {
            $arr = ['success' => true, 'message' => "Cập nhật danh mục thành công!"];
        } else {
            $arr = ['success' => false, 'message' => "Lỗi khi cập nhật: " . $conn->error];
        }
    } else {
        $arr = ['success' => false, 'message' => "Thiếu dữ liệu cần thiết!"];
    }
} else {
    $arr = ['success' => false, 'message' => "Yêu cầu không hợp lệ!"];
}

echo json_encode($arr, JSON_UNESCAPED_UNICODE);
$conn->close();
?>