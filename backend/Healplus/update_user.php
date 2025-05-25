<?php
include "connect.php";
$name        = $_POST['name'];
$email       = $_POST['email'];
$gender      = $_POST['gender'];
$phone       = $_POST['phone'];
$url         = $_POST['url'];
$dateBirth   = $_POST['dateBirth'];
$idauth      = $_POST['idauth'];
$sql = "UPDATE user SET 
            name = ?, 
            email = ?, 
            gender = ?, 
            phone = ?, 
            url = ?, 
            dateBirth = ?, 
            idauth = ?
        WHERE idauth = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("sssssss", 
    $name, $email, $gender, $phone, $url, $dateBirth, $idauth);
// Thực thi truy vấn
if ($stmt->execute()) {
    echo json_encode(["status" => "success", "message" => "Cập nhật thành công"]);
} else {
    echo json_encode(["status" => "error", "message" => "Cập nhật thất bại: " . $stmt->error]);
}
$stmt->close();
$conn->close();
?>