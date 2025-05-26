<?php
include "connect.php"; 
if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['idc'])) {
    $idc = trim($_POST['idc']); 
    if (!empty($idc)) {
        $sqlCheck = "SELECT * FROM category WHERE idc = ?";
        $stmtCheck = $conn->prepare($sqlCheck);
        $stmtCheck->bind_param("s", $idc);
        $stmtCheck->execute();
        $resultCheck = $stmtCheck->get_result();
        if ($resultCheck->num_rows > 0) {
            $sqlDelete = "DELETE FROM category WHERE idc = ?";
            $stmtDelete = $conn->prepare($sqlDelete);
            $stmtDelete->bind_param("s", $idc);
            if ($stmtDelete->execute()) {
                $arr = [
                    'success' => true,
                    'message' => "Xóa danh mục thành công!"
                ];
            } else {
                $arr = [
                    'success' => false,
                    'message' => "Lỗi khi xóa danh mục: " . $conn->error
                ];
            }
        } else {
            $arr = [
                'success' => false,
                'message' => "Danh mục không tồn tại!"
            ];
        }
    } else {
        $arr = [
            'success' => false,
            'message' => "ID danh mục không được để trống!"
        ];
    }
} else {
    $arr = [
        'success' => false,
        'message' => "Thiếu tham số idc!"
    ];
}
echo json_encode($arr, JSON_UNESCAPED_UNICODE);
$conn->close();
?>