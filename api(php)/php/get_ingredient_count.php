<?php
include "connect.php";
$sql = "
SELECT * FROM
    ingredient i
WHERE
    i.idc = (
        SELECT
            idc
        FROM
            ingredient
        GROUP BY
            idc
        ORDER BY
            COUNT(idc) DESC
        LIMIT 1
    );
";

$result = $conn->query($sql);

$ingredients_noi_bat = [];
if ($result) {
    while ($row = $result->fetch_assoc()) {
        $ingredients_noi_bat[] = $row;
    }
    $result->free();

    if (!empty($ingredients_noi_bat)) {
        $arr = [
            'success' => true,
            'message' => "Lấy danh sách ingredient nổi bật thành công",
            'result'  => $ingredients_noi_bat
        ];
    } else {
        $arr = [
            'success' => true, 
            'message' => "Không tìm thấy ingredient nổi bật nào.",
            'result'  => [] 
        ];
    }
} else {
    $arr = [
        'success' => false,
        'message' => "Lấy danh sách ingredient nổi bật không thành công. Lỗi: " . $conn->error,
        'result'  => [] 
    ];
}
header('Content-Type: application/json; charset=utf-8');
echo json_encode($ingredients_noi_bat, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
?>