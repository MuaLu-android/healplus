<?php
include "connect.php";

// Kiểm tra phương thức và dữ liệu đầu vào
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['orderId'], $_POST['payzalo'])) {

    $orderId = intval($_POST['orderId']);
    $payzalo = mysqli_real_escape_string($conn, $_POST['payzalo']); // Làm sạch dữ liệu

    // Cập nhật thông tin thanh toán ZaloPay trong bảng 'oder'
    $query = "UPDATE `oder` SET `payzalo` = '$payzalo' WHERE `id` = $orderId";
    $data = mysqli_query($conn, $query);

    if ($data) {
        $arr = [
            'success' => true,
            'message' => 'Cập nhật thông tin thanh toán ZaloPay thành công'
        ];
    } else {
        $arr = [
            'success' => false,
            'message' => 'Cập nhật thông tin thanh toán ZaloPay không thành công: ' . mysqli_error($conn)
        ];
    }

} else {
    $arr = [
        'success' => false,
        'message' => 'Dữ liệu không đầy đủ hoặc không hợp lệ'
    ];
}

// Trả về kết quả dưới dạng JSON
header('Content-Type: application/json; charset=utf-8');
echo json_encode($arr, JSON_UNESCAPED_UNICODE);

mysqli_close($conn);
?>