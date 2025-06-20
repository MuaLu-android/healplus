<?php
include "connect.php";
$idauth = isset($_POST["idauth"]) ? $_POST["idauth"] : '';
$status = isset($_POST["status"]) ? $_POST["status"] : '';

$query = "SELECT * FROM `oder` WHERE `idauth` = ? AND `status` = ? ORDER BY id DESC";
$stmt = mysqli_prepare($conn, $query);
mysqli_stmt_bind_param($stmt, "ss", $idauth, $status);
mysqli_stmt_execute($stmt);
$result_order = mysqli_stmt_get_result($stmt);

$orders = array();
while ($row_order = mysqli_fetch_assoc($result_order)) {
    $query_detail = "SELECT orderdetails.*,
                           product.*,
                           IFNULL(GROUP_CONCAT(DISTINCT e.title ORDER BY e.ide SEPARATOR '||'), '') AS element_names,
                           IFNULL(GROUP_CONCAT(DISTINCT productimages.url ORDER BY productimages.idu SEPARATOR '||'), '') AS product_images,
                           IFNULL(GROUP_CONCAT(DISTINCT unitinfo.unit_name ORDER BY unitinfo.idunit SEPARATOR '||'), '') AS unit_names,
                           GROUP_CONCAT(DISTINCT tp.title ORDER BY tp.idtp SEPARATOR '||') AS ingredient_titles,
                           GROUP_CONCAT(DISTINCT tp.body ORDER BY tp.idtp SEPARATOR '||') AS ingredient_bodies
                    FROM orderdetails
                    INNER JOIN product ON orderdetails.idp = product.idp
                    LEFT JOIN element e ON product.ide = e.ide
                    LEFT JOIN productimages ON product.idp = productimages.idp
                    LEFT JOIN unitinfo ON product.idp = unitinfo.idp
                    LEFT JOIN thanhphan tp ON product.idp = tp.idp
                    WHERE orderdetails.Ido = ?
                    GROUP BY product.idp";

    $stmt_detail = mysqli_prepare($conn, $query_detail);
    mysqli_stmt_bind_param($stmt_detail, "i", $row_order['id']); // "i" cho integer
    mysqli_stmt_execute($stmt_detail);
    $result_detail = mysqli_stmt_get_result($stmt_detail);

    $items = array();
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
        $row_detail["ingredients"] = $ingredients;
        $review_sql = "SELECT reviewerName, rating, comment, date, profileImageUrl FROM productreview WHERE idp = ?";
        $review_stmt = $conn->prepare($review_sql);
        $review_stmt->bind_param("s", $row_detail['idp']); // Giả sử idp là string, nếu là integer thì "i"
        $review_stmt->execute();
        $review_result = $review_stmt->get_result();

        $reviewitems = [];
        while ($review = $review_result->fetch_assoc()) {
            $reviewitems[] = $review;
        }
        $review_stmt->close();
        $row_detail["reviewitems"] = $reviewitems;
        unset(
            $row_detail["ingredient_titles"], $row_detail["ingredient_bodies"]);
            $items[] = $row_detail;
    }

    $row_order['items'] = $items;
    $orders[] = $row_order;
}

if (!empty($orders)) {
    $response = [
        'success' => true,
        'message' => "Lấy thông tin đơn hàng thành công",
        'result'  => $idauth, $status
    ];
} else {
    $response = [
        'success' => false,
        'message' => "Không tìm thấy đơn hàng",
        'result'  => $idauth, $status
    ];
}

header('Content-Type: application/json; charset=utf-8');

echo json_encode($orders, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT); // Giữ nguyên cách trả về orders như code gốc

mysqli_stmt_close($stmt);
if (isset($stmt_detail) && $stmt_detail) { // Kiểm tra xem $stmt_detail có được khởi tạo không
    mysqli_stmt_close($stmt_detail);
}
mysqli_close($conn);
?>