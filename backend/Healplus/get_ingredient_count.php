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

$result = $conn->query($sql); // Thực thi truy vấn

$ingredients_noi_bat = []; // Mảng để lưu trữ các ingredient nổi bật

// Kiểm tra xem truy vấn có thành công và có trả về kết quả không
if ($result) {
    while ($row = $result->fetch_assoc()) {
        $ingredients_noi_bat[] = $row; // Thêm từng ingredient nổi bật vào mảng
    }
    // Giải phóng bộ nhớ của kết quả sau khi đã lấy hết dữ liệu
    $result->free();

    if (!empty($ingredients_noi_bat)) {
        $arr = [
            'success' => true,
            'message' => "Lấy danh sách ingredient nổi bật thành công",
            'result'  => $ingredients_noi_bat // Trả về mảng các ingredient
        ];
    } else {
        // Truy vấn thành công nhưng không có ingredient nào thuộc danh mục nổi bật
        // (Có thể xảy ra nếu bảng ingredient rỗng hoặc không có idc nào nổi bật)
        $arr = [
            'success' => true, // Vẫn là thành công về mặt truy vấn
            'message' => "Không tìm thấy ingredient nổi bật nào.",
            'result'  => [] // Trả về mảng rỗng
        ];
    }
} else {
    // Có lỗi xảy ra trong quá trình thực thi truy vấn SQL
    $arr = [
        'success' => false,
        'message' => "Lấy danh sách ingredient nổi bật không thành công. Lỗi: " . $conn->error,
        'result'  => [] // Trả về mảng rỗng khi có lỗi
    ];
}

// Đặt header cho JSON và xuất kết quả
header('Content-Type: application/json; charset=utf-8');
echo json_encode($ingredients_noi_bat, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
?>