<?php
include "connect.php";
// Kiểm tra phương thức và dữ liệu đầu vào
$fullname = $_POST['fullname'];
$phone = $_POST['phone'];
$address = $_POST['address'];
$type = $_POST['type'];
// Câu truy vấn INSERT
$sql = "INSERT INTO `address_delivery`(`fullname`, `phone`, `address`, `type`, `iduser`) VALUES ('$fullname', '$phone', '$address', $type)";
if (mysqli_query($conn, $sql)) {
    $response = array("success" => true, "message" => "Thêm element thành công.");
} else {
    $response = array("success" => false, "message" => "Lỗi khi thêm element: " . mysqli_error($conn));
}
// Trả về kết quả dưới dạng JSON
header('Content-Type: application/json');
echo json_encode($response, JSON_UNESCAPED_UNICODE);
mysqli_close($conn);
?>