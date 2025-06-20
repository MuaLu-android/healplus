<?php
header('Content-Type: application/json; charset=utf-8');
include "connect.php";

try {
    $sql = "SELECT p.*, 
            COALESCE(p.quantity, 0) as quantity,
            CASE 
                WHEN p.quantity = 0 THEN 'Hết hàng'
                WHEN p.quantity < 10 THEN 'Sắp hết hàng'
                ELSE 'Còn hàng'
            END as stock_status
            FROM product p
            WHERE p.quantity = 0
            ORDER BY p.quantity ASC";

    $result = $conn->query($sql);
    
    if (!$result) {
        throw new Exception("Lỗi truy vấn: " . $conn->error);
    }

    $products = [];
    while ($row = $result->fetch_assoc()) {
        $formattedProduct = [
            'idp' => $row['idp'],
            'name' => $row['name'],
            'trademark' => $row['trademark'],
            'rating' => (float)$row['rating'],
            'review' => (int)$row['review'],
            'sold' => (int)$row['sold'],
            'expiry' => $row['expiry'],
            'price' => (int)$row['price'],
            'preparation' => $row['preparation'],
            'origin' => $row['origin'],
            'manufacturer' => $row['manufacturer'],
            'description' => $row['description'],
            'ide' => $row['ide'],
            'productiondate' => $row['productiondate'],
            'specification' => $row['specification'],
            'ingredient' => $row['ingredient'],
            'quantity' => (int)$row['quantity'],
            'stock_status' => $row['stock_status'],
            'congdung' => $row['congdung'],
            'cachdung' => $row['cachdung'],
            'tacdungphu' => $row['tacdungphu'],
            'baoquan' => $row['baoquan'],
            'pricing_info' => [
                'base_price' => (int)$row['price'],
                'formatted_price' => number_format($row['price'], 0, ',', '.') . ' đ'
            ],
            'inventory_status' => [
                'quantity' => (int)$row['quantity'],
                'status' => $row['stock_status'],
                'alert_level' => $row['quantity'] <= 5 ? 'high' : 'medium'
            ]
        ];
        if (!empty($row['productiondate'])) {
            $formattedProduct['formatted_production_date'] = date('d/m/Y', strtotime($row['productiondate']));
        }

        $products[] = $formattedProduct;
    }
    $totalCount = count($products);
    
    if (!empty($products)) {
        $arr = [
            'success' => true,
            'message' => "Lấy dữ liệu thành công",
            'total_count' => $totalCount,
            'results' => $products
        ];
    } else {
        $arr = [
            'success' => false,
            'message' => "Không tìm thấy sản phẩm nào có số lượng từ 1-9",
            'total_count' => 0,
            'results' => []
        ];
    }

} catch (Exception $e) {
    $arr = [
        'success' => false,
        'message' => $e->getMessage(),
        'total_count' => 0,
        'results' => []
    ];
} finally {
    echo json_encode($products, JSON_UNESCAPED_UNICODE | JSON_NUMERIC_CHECK | JSON_PRETTY_PRINT);
    if (isset($conn)) {
        $conn->close();
    }
}
?>
