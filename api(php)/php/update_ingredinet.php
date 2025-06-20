<?php
include "connect.php";
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["iding"], $_POST["title"], $_POST["url"], $_POST["idc"])) {
    $iding = trim($_POST["iding"]);
    $title = trim($_POST["title"]);
    $url = trim($_POST["url"]);
    $idc = trim($_POST["idc"]);
    if (!empty($iding) && !empty($title) && !empty($url) && !empty($idc)) {
        $iding = mysqli_real_escape_string($conn, $iding);
        $title = mysqli_real_escape_string($conn, $title);
        $url = mysqli_real_escape_string($conn, $url);
        $idc = mysqli_real_escape_string($conn, $idc);
        $sql = "UPDATE ingredient SET title = '$title', url = '$url', idc = '$idc' WHERE iding = '$iding'";

        if (mysqli_query($conn, $sql)) {
            if (mysqli_affected_rows($conn) > 0) {
                $response = array("success" => true, "message" => "Cập nhật ingredient thành công.");
            } else {
                $response = array("success" => false, "message" => "Không tìm thấy ingredient để cập nhật hoặc không có thay đổi.");
            }
        } else {
            $response = array("success" => false, "message" => "Lỗi khi cập nhật ingredient: " . mysqli_error($conn));
        }
    } else {
        $response = array("success" => false, "message" => "Vui lòng điền đầy đủ thông tin (iding, title, url, idc).");
    }
} else {
    $response = array("success" => false, "message" => "Dữ liệu không hợp lệ hoặc thiếu.");
}

header('Content-Type: application/json');
echo json_encode($response, JSON_UNESCAPED_UNICODE);
mysqli_close($conn);
?>