<?php
include "connect.php";
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["title"], $_POST["url"], $_POST["idc"])) {
    $title = trim($_POST["title"]);
    $url = trim($_POST["url"]);
    $idc = trim($_POST["idc"]);
    if (!empty($title) && !empty($url) && !empty($idc)) {
        $iding = uniqid("ing");
        $title = mysqli_real_escape_string($conn, $title);
        $url = mysqli_real_escape_string($conn, $url);
        $idc = mysqli_real_escape_string($conn, $idc);
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
header('Content-Type: application/json');
echo json_encode($response, JSON_UNESCAPED_UNICODE);
mysqli_close($conn);
?>