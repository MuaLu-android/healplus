<?php
include "connect.php";

// Kiểm tra phương thức và dữ liệu đầu vào
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["title"], $_POST["url"], $_POST["idc"])) {
    $title = trim($_POST["title"]);
    $url = trim($_POST["url"]);
    $idc = trim($_POST["idc"]);

    // Kiểm tra xem các trường có bị trống không
    if (!empty($title) && !empty($url) && !empty($idc)) {
        // Tạo iding duy nhất
        $iding = uniqid("ing");

        // Làm sạch dữ liệu để tránh SQL injection
        $title = mysqli_real_escape_string($conn, $title);
        $url = mysqli_real_escape_string($conn, $url);
        $idc = mysqli_real_escape_string($conn, $idc);

        // Câu truy vấn INSERT
        $sql = "INSERT INTO ingredient (iding, title, url, idc) VALUES ('$iding', '$title', '$url', '$idc')";

        if (mysqli_query($conn, $sql)) {
            $response = array("success" => true, "message" => "Thêm ingredient thành công.");
        } else {
            $response = array("success" => false, "message" => "Lỗi khi thêm ingredient: " . mysqli_error($conn));
        }
    } else {
        $response = array("success" => false, "message" => "Vui lòng điền đầy đủ thông tin (title, url, idc).");
    }
} else {
    $response = array("success" => false, "message" => "Dữ liệu không hợp lệ hoặc thiếu.");
}

// Trả về kết quả dưới dạng JSON
header('Content-Type: application/json');
echo json_encode($response, JSON_UNESCAPED_UNICODE); // Thêm JSON_UNESCAPED_UNICODE để hỗ trợ tiếng Việt
mysqli_close($conn);
?>