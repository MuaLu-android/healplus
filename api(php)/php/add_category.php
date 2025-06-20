<?php
include "connect.php";
if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['title'])) {
    $title = trim($_POST['title']);
    if (!empty($title)) {
        $sql = "SELECT MAX(CAST(SUBSTRING(idc, 3) AS UNSIGNED)) AS max_id FROM category";
        $result = $conn->query($sql);
        $row = $result->fetch_assoc();
        $maxId = $row['max_id'] ? $row['max_id'] : 0;
        $newIdc = "ct" . str_pad($maxId + 1, 2, "0", STR_PAD_LEFT);
        $sqlInsert = "INSERT INTO category (idc, title) VALUES ('$newIdc', '$title')";
        if ($conn->query($sqlInsert) === TRUE) {
            $arr = [
                'success' => true,
                'message' => "Thêm danh mục thành công!"
            ];
        } else {
            $arr = [
                'success' => false,
                'message' => "Lỗi khi thêm danh mục: " . $conn->error
            ];
        }
    } else {
        $arr = [
            'success' => false,
            'message' => "Tên danh mục không được để trống!"
        ];
    }
} else {
    $arr = [
        'success' => false,
        'message' => "Thiếu tham số title!"
    ];
}
echo json_encode($arr, JSON_UNESCAPED_UNICODE);
$conn->close();
?>