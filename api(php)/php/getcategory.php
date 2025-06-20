<?php
include "connect.php";
$sqlTotal = "SELECT CAST(SUM(quantity) AS FLOAT) as total FROM `element`";
$totalResult = $conn->query($sqlTotal);
$totalRow = $totalResult->fetch_assoc();
$totalQuantity = (float)$totalRow['total'];
$sql = "SELECT 
            c.*,
            CAST(COALESCE(SUM(e.quantity), 0) AS SIGNED) as quantity,
            COALESCE((SUM(e.quantity) * 100.0 / $totalQuantity), 0) as percentage
        FROM `category` c
        LEFT JOIN `ingredient` i ON c.idc = i.idc
        LEFT JOIN `element` e ON i.iding = e.iding
        GROUP BY c.idc, c.title";

$result = $conn->query($sql);
$categories = [];

if ($result && $result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        // Convert types
        $row['quantity'] = (int)$row['quantity'];
        $row['percentage'] = round((float)$row['percentage'], 2);
        $categories[] = $row;
    }
    
    $arr = [
        'success' => true,
        'message' => "Successfully category",
        'result' => $categories,
        'total_quantity' => $totalQuantity
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Unsuccessfully category",
        'result' => [],
        'total_quantity' => 0.0
    ];
}

echo json_encode($categories, JSON_NUMERIC_CHECK | JSON_UNESCAPED_UNICODE);
$conn->close();
?>

