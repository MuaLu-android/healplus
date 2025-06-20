<?php
include "connect.php";
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['orderId'], $_POST['pay'])) {

    $orderId = intval($_POST['orderId']);
    $pay = mysqli_real_escape_string($conn, $_POST['pay']); // Làm sạch dữ liệu
    $query = "UPDATE `oder` SET `pay` = '$pay' WHERE `id` = $orderId";
    $data = mysqli_query($conn, $query);

    if ($data) {
        $arr = [
            'success' => true,
            'message' => 'Cập nhật thông tin thanh toán khi nhận hàng thành công'
        ];
    } else {
        $arr = [
            'success' => false,
            'message' => 'Cập nhật thông tin thanh toán khi nhận hàng không thành công: ' . mysqli_error($conn)
        ];
    }

} else {
    $arr = [
        'success' => false,
        'message' => 'Dữ liệu không đầy đủ hoặc không hợp lệ'
    ];
}

header('Content-Type: application/json; charset=utf-8');
echo json_encode($arr, JSON_UNESCAPED_UNICODE);

mysqli_close($conn);
?>