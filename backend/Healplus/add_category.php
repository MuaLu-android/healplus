<?php
include "connect.php"; // Kết nối database

// Kiểm tra nếu có dữ liệu POST từ client gửi lên
if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['title'])) {
    $title = trim($_POST['title']); // Lấy giá trị title từ request và loại bỏ khoảng trắng thừa

    if (!empty($title)) {
        // Lấy idc lớn nhất hiện tại (bỏ "ct" và chuyển phần số sang kiểu INT)
        $sql = "SELECT MAX(CAST(SUBSTRING(idc, 3) AS UNSIGNED)) AS max_id FROM category";
        $result = $conn->query($sql);
        $row = $result->fetch_assoc();
        $maxId = $row['max_id'] ? $row['max_id'] : 0; // Nếu bảng rỗng, đặt maxId = 0
        $newIdc = "ct" . str_pad($maxId + 1, 2, "0", STR_PAD_LEFT); // Định dạng idc như ct01, ct02,...

        // Thêm danh mục vào bảng
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

// Trả về JSON response
echo json_encode($arr, JSON_UNESCAPED_UNICODE);
$conn->close();
?>