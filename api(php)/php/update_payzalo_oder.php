<?php
include "connect.php";
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['orderId'], $_POST['payzalo'])) {

    $orderId = intval($_POST['orderId']);
    $payzalo = mysqli_real_escape_string($conn, $_POST['payzalo']); // Làm sạch dữ liệu
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

header('Content-Type: application/json; charset=utf-8');
echo json_encode($arr, JSON_UNESCAPED_UNICODE);

mysqli_close($conn);
?>