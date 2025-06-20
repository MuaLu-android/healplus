<?php
include "connect.php";
$status = isset($_POST["status"]) ? $_POST["status"] : '';
$query = "SELECT * FROM `oder` WHERE `status` = ? ORDER BY id DESC";
$stmt = mysqli_prepare($conn, $query);
mysqli_stmt_bind_param($stmt, "s", $status);
mysqli_stmt_execute($stmt);
$result_order = mysqli_stmt_get_result($stmt);
$orders = array();
while ($row_order = mysqli_fetch_assoc($result_order)) {
    $query_detail = "SELECT od.*,
                           p.*,
                           IFNULL(GROUP_CONCAT(DISTINCT pi.url ORDER BY pi.idu SEPARATOR '||'), '') AS product_images,
                           IFNULL(GROUP_CONCAT(DISTINCT u.unit_name ORDER BY u.idunit SEPARATOR '||'), '') AS unit_names,
                           GROUP_CONCAT(DISTINCT e.title ORDER BY e.ide SEPARATOR '||') AS element_names,
                           GROUP_CONCAT(DISTINCT tp.title ORDER BY tp.idtp SEPARATOR '||') AS ingredient_titles,
                           GROUP_CONCAT(DISTINCT tp.body ORDER BY tp.idtp SEPARATOR '||') AS ingredient_bodies
                    FROM orderdetails od
                    INNER JOIN product p ON od.idp = p.idp
                    LEFT JOIN productimages pi ON p.idp = pi.idp
                    LEFT JOIN unitinfo u ON p.idp = u.idp
                    LEFT JOIN thanhphan tp ON p.idp = tp.idp
                    LEFT JOIN Element e ON p.ide = e.ide  -- Join bảng Element
                    WHERE od.Ido = ?
                    GROUP BY p.idp"; // Group theo idp để gom nhóm thông tin sản phẩm
    $stmt_detail = mysqli_prepare($conn, $query_detail);
    if ($stmt_detail === false) {
        error_log("Lỗi khi chuẩn bị truy vấn chi tiết cho đơn hàng ID " . $row_order['id'] . ": " . mysqli_error($conn));
        continue;
    }
    mysqli_stmt_bind_param($stmt_detail, "i", $row_order['id']);
    if (mysqli_stmt_execute($stmt_detail) === false) {
        error_log("Lỗi khi thực thi truy vấn chi tiết cho đơn hàng ID " . $row_order['id'] . ": " . mysqli_stmt_error($stmt_detail));
        mysqli_stmt_close($stmt_detail); // Đóng statement chi tiết trước khi tiếp tục
        continue; // Bỏ qua đơn hàng hiện tại và tiếp tục với đơn hàng tiếp theo
    }

    $result_detail = mysqli_stmt_get_result($stmt_detail);

    $items = []; // Khởi tạo mảng items cho mỗi đơn hàng
    while ($row_detail = mysqli_fetch_assoc($result_detail)) {
        $row_detail["product_images"] = $row_detail["product_images"] ? explode("||", $row_detail["product_images"]) : [];
        $row_detail["unit_names"] = $row_detail["unit_names"] ? explode("||", $row_detail["unit_names"]) : [];
        $row_detail["element_names"] = $row_detail["element_names"] ? $row_detail["element_names"] : '';
        $ingredients = [];
        $ingredient_titles = $row_detail["ingredient_titles"] ? explode("||", $row_detail["ingredient_titles"]) : [];
        $ingredient_bodies = $row_detail["ingredient_bodies"] ? explode("||", $row_detail["ingredient_bodies"]) : [];
        for ($i = 0; $i < count($ingredient_titles); $i++) {
            $ingredients[] = [
                'title' => $ingredient_titles[$i],
                'body' => isset($ingredient_bodies[$i]) ? $ingredient_bodies[$i] : ''
            ];
        }
        $row_detail["ingredients"] = $ingredients; // Lưu vào mảng ingredients
        $review_sql = "SELECT reviewerName, rating, comment, date, profileImageUrl FROM productreview WHERE idp = ?";
        $review_stmt = $conn->prepare($review_sql);
        $review_stmt->bind_param("s", $row_detail['idp']);
        $review_stmt->execute();
        $review_result = $review_stmt->get_result();

        $reviewitems = [];
        while ($review = $review_result->fetch_assoc()) {
            $reviewitems[] = $review;
        }
        $review_stmt->close();
        $row_detail["reviewitems"] = $reviewitems;
        unset($row_detail["ingredient_titles"], $row_detail["ingredient_bodies"]);
        $items[] = $row_detail;
    }
    mysqli_stmt_close($stmt_detail);
    $row_order['items'] = $items;
    $orders[] = $row_order;
}

if (!empty($orders)) {
    $response = [
        'success' => true,
        'message' => "Lấy thông tin đơn hàng thành công",
        'result' => $orders
    ];
} else {
    $response = [
        'success' => false,
        'message' => "Không tìm thấy đơn hàng",
        'result' => []
    ];
}

header('Content-Type: application/json; charset=utf-8');
echo json_encode($orders, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

mysqli_stmt_close($stmt);
mysqli_close($conn);
?>