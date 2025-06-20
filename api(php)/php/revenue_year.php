<?php
include "connect.php";
header('Content-Type: application/json');
define('DELIVERED_ORDER_STATUS_STRING', 'Đã giao hàng');
$input_year = isset($_GET['year']) ? intval($_GET['year']) : 0;
$revenue = [];

$query_monthly = "SELECT 
                    MONTH(`datetime`) AS order_month,
                    YEAR(`datetime`) AS order_year,
                    SUM(`sumMoney`) AS total_revenue
                FROM 
                    `oder`
                WHERE 
                    `status` = ?
                    AND YEAR(`datetime`) = ?
                GROUP BY 
                    YEAR(`datetime`), MONTH(`datetime`)
                ORDER BY 
                    `order_year`, `order_month` ASC";

$stmt_monthly = mysqli_prepare($conn, $query_monthly);
$status_delivered_string = DELIVERED_ORDER_STATUS_STRING;
mysqli_stmt_bind_param($stmt_monthly, "si", $status_delivered_string, $input_year);
mysqli_stmt_execute($stmt_monthly);
$result_monthly = mysqli_stmt_get_result($stmt_monthly);

if ($result_monthly) {
    while ($row = mysqli_fetch_assoc($result_monthly)) {
        $revenue[] = [
            'order_month' => intval($row['order_month']),
            'order_year' => intval($row['order_year']),
            'total_revenue' => floatval($row['total_revenue'])
        ];
    }
    mysqli_free_result($result_monthly);
}
mysqli_stmt_close($stmt_monthly);
$detailed_delivered_orders = [];

$query_details = "SELECT 
                    `id`,
                    `datetime` AS datetime_val,
                    `sumMoney`
                FROM 
                    `oder`
                WHERE 
                    `status` = ? 
                    AND YEAR(`datetime`) = ?
                ORDER BY 
                    `datetime` ASC";

$stmt_details = mysqli_prepare($conn, $query_details);
mysqli_stmt_bind_param($stmt_details, "si", $status_delivered_string, $input_year);
mysqli_stmt_execute($stmt_details);
$result_details = mysqli_stmt_get_result($stmt_details);

if ($result_details) {
    while ($row_detail = mysqli_fetch_assoc($result_details)) {
        $detailed_delivered_orders[] = [
            'id' => intval($row_detail['id']),
            'datetime' => $row_detail['datetime_val'],
            'sumMoney' => floatval($row_detail['sumMoney'])
        ];
    }
    mysqli_free_result($result_details);
}
mysqli_stmt_close($stmt_details);
echo json_encode([
    'revenue' => $revenue,
    'detaily_orders' => $detailed_delivered_orders
], JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

mysqli_close($conn);
?>