<?php
include "connect.php";

// Kiểm tra phương thức và dữ liệu đầu vào
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["iding"])) {
    $iding = trim($_POST["iding"]);

    if (!empty($iding)) {
        // Làm sạch iding
        $iding = mysqli_real_escape_string($conn, $iding);

        // Câu truy vấn DELETE
        $sql = "DELETE FROM ingredient WHERE iding = '$iding'";

        if (mysqli_query($conn, $sql)) {
            if (mysqli_affected_rows($conn) > 0) {
                $response = array("success" => true, "message" => "Xóa ingredient thành công.");
            } else {
                $response = array("success" => false, "message" => "Không tìm thấy ingredient để xóa.");
            }
        } else {
            $response = array("success" => false, "message" => "Lỗi khi xóa ingredient: " . mysqli_error($conn));
        }
    } else {
        $response = array("success" => false, "message" => "ID ingredient không được để trống!");
    }
} else {
    $response = array("success" => false, "message" => "Dữ liệu không hợp lệ hoặc thiếu.");
}

// Trả về kết quả dưới dạng JSON
header('Content-Type: application/json');
echo json_encode($response, JSON_UNESCAPED_UNICODE);
mysqli_close($conn);
?>