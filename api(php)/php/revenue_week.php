<?php
include "connect.php";
header('Content-Type: application/json');
define('DELIVERED_ORDER_STATUS_STRING', 'Đã giao hàng');

$input_week_start = isset($_GET['start_date']) ? $_GET['start_date'] : null;


$start_date = date('Y-m-d', strtotime($input_week_start));
$end_date = date('Y-m-d', strtotime($start_date . ' +6 days'));

// Mảng 1: Doanh thu theo từng ngày trong tuần
$revenue = [];

$query_daily = "SELECT 
                    DAY(`datetime`) AS order_day,
                    MONTH(`datetime`) AS order_month,
                    YEAR(`datetime`) AS order_year,
                    SUM(`sumMoney`) AS total_revenue
                FROM 
                    `oder`
                WHERE 
                    `status` = ?
                    AND DATE(`datetime`) BETWEEN ? AND ?
                GROUP BY 
                    YEAR(`datetime`), MONTH(`datetime`), DAY(`datetime`)
                ORDER BY 
                    `datetime` ASC";

$stmt_daily = mysqli_prepare($conn, $query_daily);
$status_delivered_string = DELIVERED_ORDER_STATUS_STRING;
mysqli_stmt_bind_param($stmt_daily, "sss", $status_delivered_string, $start_date, $end_date);
mysqli_stmt_execute($stmt_daily);
$result_daily = mysqli_stmt_get_result($stmt_daily);

if ($result_daily) {
    while ($row = mysqli_fetch_assoc($result_daily)) {
        $revenue[] = [
            'order_day' => intval($row['order_day']),
            'order_month' => intval($row['order_month']),
            'order_year' => intval($row['order_year']),
            'total_revenue' => floatval($row['total_revenue'])
        ];
    }
    mysqli_free_result($result_daily);
}
mysqli_stmt_close($stmt_daily);

// Mảng 2: Danh sách đơn hàng chi tiết
$detailed_delivered_orders = [];

$query_details = "SELECT 
                    `id`,
                    `datetime` AS datetime_val,
                    `sumMoney`
                FROM 
                    `oder`
                WHERE 
                    `status` = ? 
                    AND DATE(`datetime`) BETWEEN ? AND ?
                ORDER BY 
                    `datetime` ASC";

$stmt_details = mysqli_prepare($conn, $query_details);
mysqli_stmt_bind_param($stmt_details, "sss", $status_delivered_string, $start_date, $end_date);
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

// Trả kết quả JSON
echo json_encode([
    'revenue' => $revenue,
    'detaily_orders' => $detailed_delivered_orders
], JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

mysqli_close($conn);
?>