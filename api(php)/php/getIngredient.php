<?php
include "connect.php";

// Lấy tổng số lượng sản phẩm (cast to float)
$sqlTotal = "SELECT CAST(SUM(quantity) AS FLOAT) as total FROM `element`";
$totalResult = $conn->query($sqlTotal);
$totalRow = $totalResult->fetch_assoc();
$totalQuantity = (float)$totalRow['total'];

// Join bảng ingredient với element và tính tổng quantity cho mỗi ingredient
$sql = "SELECT i.*, 
        CAST(COALESCE(SUM(e.quantity), 0) AS SIGNED) as quantity,
        COALESCE((SUM(e.quantity) * 100.0 / $totalQuantity), 0) as percentage
        FROM `ingredient` i
        LEFT JOIN `element` e ON i.iding = e.iding
        GROUP BY i.iding, i.title, i.url, i.idc";

$result = $conn->query($sql);
$ingredients = [];

if ($result && $result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        // Convert quantity to integer
        $row['quantity'] = (int)$row['quantity'];
        // Format percentage to 2 decimal places
        $row['percentage'] = round((float)$row['percentage'], 2);
        $ingredients[] = $row;
    }
    
    $arr = [
        'success' => true,
        'message' => "thành công",
        'result' => $ingredients,
        'total_quantity' => $totalQuantity
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "không thành công",
        'result' => [],
        'total_quantity' => 0.0
    ];
}

echo json_encode($ingredients, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);
$conn->close();
?>
