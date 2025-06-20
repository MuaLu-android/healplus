<?php 
header('Content-Type: application/json; charset=utf-8');
include "connect.php";

try {
    $sumQuery = "SELECT COUNT(*) as total FROM product";
    $sumResult = $conn->query($sumQuery);
    $sumquantity = 0;
    if ($sumResult) {
        $sumRow = $sumResult->fetch_assoc();
        $sumquantity = (int)$sumRow['total'];
    }
    $sql = "SELECT e.ide, e.title, e.url, COUNT(p.idp) as quantity 
            FROM element e 
            LEFT JOIN product p ON e.ide = p.ide 
            GROUP BY e.ide, e.title, e.url";

    $result = $conn->query($sql);
    
    if (!$result) {
        throw new Exception("Lỗi truy vấn: " . $conn->error);
    }

    $elements = [];
    while ($row = $result->fetch_assoc()) {
        $quantity = (int)$row['quantity'];
        $percentage = ($sumquantity > 0) ? round(($quantity / $sumquantity) * 100, 2) : 0.0;
        $updateSql = "UPDATE element SET quantity = ? WHERE ide = ?";
        $stmt = $conn->prepare($updateSql);
        if ($stmt === false) {
            throw new Exception("Lỗi prepared statement: " . $conn->error);
        }
        
        $stmt->bind_param("is", $quantity, $row['ide']);
        $stmt->execute();
        $stmt->close();
        $elements[] = [
            'ide' => $row['ide'],
            'title' => $row['title'],
            'url' => $row['url'],
            'quantity' => $quantity,
            'percentage' => $percentage
        ];
    }
    
    if (!empty($elements)) {
        $arr = [
            'success' => true,
            'message' => "Cập nhật thành công",
            'result' => $elements,
            'total_quantity' => $sumquantity
        ];
    } else {
        $arr = [
            'success' => false,
            'message' => "Không tìm thấy dữ liệu",
            'result' => [],
            'total_quantity' => $sumquantity
        ];
    }

} catch (Exception $e) {
    $arr = [
        'success' => false,
        'message' => $e->getMessage(),
        'result' => [],
        'total_quantity' => 0
    ];
} finally {
    echo json_encode($elements, JSON_UNESCAPED_UNICODE | JSON_NUMERIC_CHECK);
    if (isset($conn)) {
        $conn->close();
    }
}
?>
