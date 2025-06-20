<?php
include "connect.php";
// Lấy idc từ request
$idc = isset($_GET['idc']) ? $_GET['idc'] : '';

$sql = "SELECT * FROM ingredient WHERE idc = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $idc);
$stmt->execute();
$result = $stmt->get_result();

$ingredients = [];
while ($row = $result->fetch_assoc()) {
    $ingredients[] = $row;
}

echo json_encode($ingredients, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

$stmt->close();
$conn->close();
?>