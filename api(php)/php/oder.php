<?php
include "connect.php";
$name = isset($_POST['name']) ? mysqli_real_escape_string($conn, $_POST['name']) : '';
$phone = isset($_POST['phone']) ? mysqli_real_escape_string($conn, $_POST['phone']) : '';
$email = isset($_POST['email']) ? mysqli_real_escape_string($conn, $_POST['email']) : '';
$idauth = isset($_POST['idauth']) ? mysqli_real_escape_string($conn, $_POST['idauth']) : '';
$address = isset($_POST['address']) ? mysqli_real_escape_string($conn, $_POST['address']) : '';
$datetime = isset($_POST['datetime']) ? mysqli_real_escape_string($conn, $_POST['datetime']) : '';
$note = isset($_POST['note']) ? mysqli_real_escape_string($conn, $_POST['note']) : '';
$quantity = isset($_POST['quantity']) ? intval($_POST['quantity']) : 0;
$sumMoney = isset($_POST['sumMoney']) ? intval($_POST['sumMoney']) : 0;
$status = isset($_POST['status']) ? mysqli_real_escape_string($conn, $_POST['status']) : '';
$detail = isset($_POST['detail']) ? $_POST['detail'] : '[]'; // Mặc định là mảng rỗng nếu không có
mysqli_begin_transaction($conn);

try {
    $queryOrder = "INSERT INTO `oder`(`name`, `phone`, `email`, `idauth`, `address`, `datetime`, `note`, `quantity`, `sumMoney`, `status`) 
                   VALUES ('$name', '$phone', '$email', '$idauth', '$address', '$datetime', '$note', $quantity, $sumMoney, '$status')";
    $dataOrder = mysqli_query($conn, $queryOrder);

    if (!$dataOrder) {
        throw new Exception('Lỗi thêm đơn hàng: ' . mysqli_error($conn));
    }
    $orderId = mysqli_insert_id($conn);
    $detail = json_decode($detail, true);
    if (is_array($detail)) {
        foreach ($detail as $item) {
            $idp = mysqli_real_escape_string($conn, $item['idp']);
            $price = floatval($item['price']); // Sử dụng floatval để đảm bảo là số thực
            $quantityItem = intval($item['quantity']); // Đổi tên biến để tránh trùng lặp

            $queryDetail = "INSERT INTO `orderdetails`(`Ido`, `idp`, `total`, `quantity`) 
                            VALUES ('$orderId', '$idp', '$price', '$quantityItem')"; // Sử dụng $quantityItem
            $dataDetail = mysqli_query($conn, $queryDetail);

            if (!$dataDetail) {
                throw new Exception('Lỗi thêm chi tiết đơn hàng: ' . mysqli_error($conn));
            }
        }
    } else {
        throw new Exception('Lỗi: detail không phải là mảng hợp lệ');
    }
    mysqli_commit($conn);

    $arr = [
        'success' => true,
        'message' => 'Thêm đơn hàng thành công',
        'orderId' => $orderId // Thêm orderId vào response
    ];

} catch (Exception $e) {
    mysqli_rollback($conn);
    $arr = [
        'success' => false,
        'message' => 'Thêm đơn hàng không thành công: ' . $e->getMessage()
    ];
}

header('Content-Type: application/json; charset=utf-8');
echo json_encode($arr, JSON_UNESCAPED_UNICODE);

mysqli_close($conn);
?>