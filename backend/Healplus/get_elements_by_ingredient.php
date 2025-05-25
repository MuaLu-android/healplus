<?php
include "connect.php";
// Lấy idc từ request
// Lấy iding từ request
$iding = isset($_GET['iding']) ? $_GET['iding'] : '';

if (empty($iding)) {
    echo json_encode(["error" => "Thiếu iding"]);
    exit;
}

// Truy vấn lấy danh sách elements theo iding
$sql = "SELECT * FROM element WHERE iding = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $iding);
$stmt->execute();
$result = $stmt->get_result();

$elements = [];
while ($row = $result->fetch_assoc()) {
    $elements[] = $row;
}

echo json_encode($elements, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

$stmt->close();
$conn->close();
?>