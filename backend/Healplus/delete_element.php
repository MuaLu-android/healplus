<?php
include "connect.php";

// Kiểm tra phương thức và dữ liệu đầu vào
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["ide"])) {
    $ide = trim($_POST["ide"]);

    if (!empty($ide)) {
        // Làm sạch ide
        $ide = mysqli_real_escape_string($conn, $ide);

        // Câu truy vấn DELETE
        $sql = "DELETE FROM element WHERE ide = '$ide'";

        if (mysqli_query($conn, $sql)) {
            if (mysqli_affected_rows($conn) > 0) {
                $response = array("success" => true, "message" => "Xóa element thành công.");
            } else {
                $response = array("success" => false, "message" => "Không tìm thấy element để xóa.");
            }
        } else {
            $response = array("success" => false, "message" => "Lỗi khi xóa element: " . mysqli_error($conn));
        }
    } else {
        $response = array("success" => false, "message" => "ID element không được để trống!");
    }
} else {
    $response = array("success" => false, "message" => "Dữ liệu không hợp lệ hoặc thiếu.");
}

// Trả về kết quả dưới dạng JSON
header('Content-Type: application/json');
echo json_encode($response, JSON_UNESCAPED_UNICODE);
mysqli_close($conn);
?>