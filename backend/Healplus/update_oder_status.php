<?php
include "connect.php"; // Bao gồm file kết nối cơ sở dữ liệu của bạn

// Lấy ID đơn hàng và trạng thái mới từ POST
$orderId = isset($_POST["id"]) ? intval($_POST["id"]) : 0;
$newStatus = isset($_POST["status"]) ? $_POST["status"] : '';

// Kiểm tra dữ liệu đầu vào
if ($orderId <= 0 || empty($newStatus)) {
    $response = [
        'success' => false,
        'message' => "Vui lòng cung cấp ID đơn hàng và trạng thái mới hợp lệ."
    ];
    header('Content-Type: application/json; charset=utf-8');
    echo json_encode($response, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);
    exit;
}

// Truy vấn SQL để cập nhật trạng thái đơn hàng
$query = "UPDATE `oder` SET `status` = ? WHERE `id` = ?";

// Sử dụng prepared statement để bảo mật
$stmt = mysqli_prepare($conn, $query);

// Kiểm tra nếu prepared statement thất bại
if ($stmt === false) {
    error_log("Lỗi khi chuẩn bị truy vấn cập nhật trạng thái: " . mysqli_error($conn));
    $response = [
        'success' => false,
        'message' => "Lỗi nội bộ server khi chuẩn bị truy vấn."
    ];
    header('Content-Type: application/json; charset=utf-8');
    echo json_encode($response, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);
    mysqli_close($conn);
    exit;
}

// Bind các tham số: trạng thái mới (string) và ID đơn hàng (integer)
mysqli_stmt_bind_param($stmt, "si", $newStatus, $orderId); // "s" cho string, "i" cho integer

// Thực thi truy vấn
if (mysqli_stmt_execute($stmt)) {
    // Kiểm tra số dòng bị ảnh hưởng để biết cập nhật có thành công không
    if (mysqli_stmt_affected_rows($stmt) > 0) {
        $response = [
            'success' => true,
            'message' => "Cập nhật trạng thái đơn hàng ID " . $orderId . " thành công."
        ];
    } else {
        // Không có dòng nào bị ảnh hưởng, có thể do ID đơn hàng không tồn tại
        $response = [
            'success' => false,
            'message' => "Không tìm thấy đơn hàng với ID " . $orderId . " hoặc trạng thái đã giống."
        ];
    }
} else {
    // Lỗi khi thực thi truy vấn
    error_log("Lỗi khi thực thi truy vấn cập nhật trạng thái cho đơn hàng ID " . $orderId . ": " . mysqli_stmt_error($stmt));
    $response = [
        'success' => false,
        'message' => "Lỗi khi cập nhật trạng thái đơn hàng."
    ];
}

// Trả về phản hồi JSON
header('Content-Type: application/json; charset=utf-8');
echo json_encode($response, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

// Đóng statement và kết nối
mysqli_stmt_close($stmt);
mysqli_close($conn);
?>