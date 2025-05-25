<?php
include "connect.php";
// Kiểm tra phương thức và dữ liệu đầu vào
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["title"], $_POST["url"], $_POST["quantity"], $_POST["iding"])) {
    $title = trim($_POST["title"]);
    $url = trim($_POST["url"]);
    $quantity = trim($_POST["quantity"]);
    $iding = trim($_POST["iding"]);

    // Kiểm tra xem các trường có bị trống không
    if (!empty($title) && !empty($url) && !empty($quantity) && !empty($iding)) {
        // Kiểm tra xem quantity có phải là số không
        if (is_numeric($quantity)) {
            // Tạo ide duy nhất
            $ide = uniqid("ele_");

            // Làm sạch dữ liệu để tránh SQL injection
            $title = mysqli_real_escape_string($conn, $title);
            $url = mysqli_real_escape_string($conn, $url);
            $quantity = intval($quantity); // Chuyển đổi quantity thành số nguyên
            $iding = mysqli_real_escape_string($conn, $iding);

            // Câu truy vấn INSERT
            $sql = "INSERT INTO element (ide, title, url, quantity, iding) VALUES ('$ide', '$title', '$url', $quantity, '$iding')";

            if (mysqli_query($conn, $sql)) {
                $response = array("success" => true, "message" => "Thêm element thành công.");
            } else {
                $response = array("success" => false, "message" => "Lỗi khi thêm element: " . mysqli_error($conn));
            }
        } else {
            $response = array("success" => false, "message" => "Số lượng (quantity) phải là một số.");
        }
    } else {
        $response = array("success" => false, "message" => "Vui lòng điền đầy đủ thông tin (title, url, quantity, iding).");
    }
} else {
    $response = array("success" => false, "message" => "Dữ liệu không hợp lệ hoặc thiếu.");
}

// Trả về kết quả dưới dạng JSON
header('Content-Type: application/json');
echo json_encode($response, JSON_UNESCAPED_UNICODE);
mysqli_close($conn);
?>