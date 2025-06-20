<?php
include "connect.php";

$id = isset($_POST['id']) ? intval($_POST['id']) : 0;
$status = isset($_POST['status']) ? $_POST['status'] : '';

if ($id <= 0) {
    echo json_encode(['success' => false, 'message' => "ID đơn hàng không hợp lệ."]);
    exit;
}

mysqli_begin_transaction($conn);

try {
    if ($status === "Đã giao hàng") {

        $query_details = "SELECT idp, quantity FROM orderdetails WHERE Ido = $id";
        $result_details = mysqli_query($conn, $query_details);

        while ($row = mysqli_fetch_assoc($result_details)) {
            $idp = $row['idp'];
            $quantity = intval($row['quantity']);

            $query_update_sold = "UPDATE product SET sold = sold + $quantity WHERE idp = '$idp'";
            mysqli_query($conn, $query_update_sold);
        }
    }
    $query_update_status = "UPDATE oder SET status = '$status' WHERE id = $id";
    mysqli_query($conn, $query_update_status);

    mysqli_commit($conn);

    echo json_encode(['success' => true, 'message' => 'Cập nhật trạng thái và sold thành công.']);

} catch (Exception $e) {

    mysqli_rollback($conn);
    echo json_encode(['success' => false, 'message' => 'Lỗi: ' . $e->getMessage()]);
}

mysqli_close($conn);
?>