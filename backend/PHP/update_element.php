<?php
include "connect.php";

// Kiểm tra phương thức và dữ liệu đầu vào
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["ide"], $_POST["title"], $_POST["url"], $_POST["quantity"], $_POST["iding"])) {
    $ide = trim($_POST["ide"]);
    $title = trim($_POST["title"]);
    $url = trim($_POST["url"]);
    $quantity = trim($_POST["quantity"]);
    $iding = trim($_POST["iding"]);

    // Kiểm tra xem các trường có bị trống không
    if (!empty($ide) && !empty($title) && !empty($url) && !empty($quantity) && !empty($iding)) {
        // Kiểm tra xem quantity có phải là số không
        if (is_numeric($quantity)) {
            // Làm sạch dữ liệu để tránh SQL injection
            $ide = mysqli_real_escape_string($conn, $ide);
            $title = mysqli_real_escape_string($conn, $title);
            $url = mysqli_real_escape_string($conn, $url);
            $quantity = intval($quantity); // Chuyển đổi quantity thành số nguyên
            $iding = mysqli_real_escape_string($conn, $iding);

            // Câu truy vấn UPDATE
            $sql = "UPDATE element SET title = '$title', url = '$url', quantity = $quantity, iding = '$iding' WHERE ide = '$ide'";

            if (mysqli_query($conn, $sql)) {
                if (mysqli_affected_rows($conn) > 0) {
                    $response = array("success" => true, "message" => "Cập nhật element thành công.");
                } else {
                    $response = array("success" => false, "message" => "Không tìm thấy element để cập nhật hoặc không có thay đổi.");
                }
            } else {
                $response = array("success" => false, "message" => "Lỗi khi cập nhật element: " . mysqli_error($conn));
            }
        } else {
            $response = array("success" => false, "message" => "Số lượng (quantity) phải là một số.");
        }
    } else {
        $response = array("success" => false, "message" => "Vui lòng điền đầy đủ thông tin (ide, title, url, quantity, iding).");
    }
} else {
    $response = array("success" => false, "message" => "Dữ liệu không hợp lệ hoặc thiếu.");
}

// Trả về kết quả dưới dạng JSON
header('Content-Type: application/json');
echo json_encode($response, JSON_UNESCAPED_UNICODE);
mysqli_close($conn);
?>