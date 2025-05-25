<?php
include "connect.php"; // Kết nối database

// Kiểm tra nếu có dữ liệu POST từ client gửi lên
if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['idc'])) {
    $idc = trim($_POST['idc']); // Lấy idc từ request và loại bỏ khoảng trắng thừa

    if (!empty($idc)) {
        // Kiểm tra xem danh mục có tồn tại không
        $sqlCheck = "SELECT * FROM category WHERE idc = ?";
        $stmtCheck = $conn->prepare($sqlCheck);
        $stmtCheck->bind_param("s", $idc);
        $stmtCheck->execute();
        $resultCheck = $stmtCheck->get_result();

        if ($resultCheck->num_rows > 0) {
            // Nếu tồn tại, tiến hành xóa
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

// Trả về JSON response
echo json_encode($arr, JSON_UNESCAPED_UNICODE);
$conn->close();
?>