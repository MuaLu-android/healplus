<?php
include "connect.php";
$reviewerName = isset($_POST['reviewerName']) ? mysqli_real_escape_string($conn, $_POST['reviewerName']) : '';
$rating = isset($_POST['rating']) ? floatval($_POST['rating']) : '';
$comment = isset($_POST['comment']) ? mysqli_real_escape_string($conn, $_POST['comment']) : '';
$date = isset($_POST['date']) ? mysqli_real_escape_string($conn, $_POST['date']) : '';
$profileImageUrl = isset($_POST['profileImageUrl']) ? mysqli_real_escape_string($conn, $_POST['profileImageUrl']) : '';
$idp = isset($_POST['idp']) ? mysqli_real_escape_string($conn, $_POST['idp']) : '';
mysqli_begin_transaction($conn);
try {
    $query_insert_review = "INSERT INTO `productreview` (`idp`, `reviewerName`, `rating`, `comment`, `date`, `profileImageUrl`) 
                            VALUES ('$idp', '$reviewerName', $rating, '$comment', '$date', '$profileImageUrl')";
    mysqli_query($conn, $query_insert_review);

    // 2. Cập nhật số lượt review của sản phẩm (tăng thêm 1)
    $query_update_review_count = "UPDATE `product` SET `review` = `review` + 1 WHERE `idp` = '$idp'";
    mysqli_query($conn, $query_update_review_count);

    // 3. Tính trung bình rating của sản phẩm từ bảng productreview
    $query_avg_rating = "SELECT AVG(`rating`) AS avg_rating FROM `productreview` WHERE `idp` = '$idp'";
    $result_avg = mysqli_query($conn, $query_avg_rating);
    $row_avg = mysqli_fetch_assoc($result_avg);
    $average_rating = round($row_avg['avg_rating'], 1); // Làm tròn 1 chữ số thập phân

    // 4. Cập nhật lại rating của sản phẩm trong bảng product
    $query_update_product_rating = "UPDATE `product` SET `rating` = '$average_rating' WHERE `idp` = '$idp'";
    mysqli_query($conn, $query_update_product_rating);

    // Commit nếu không có lỗi
    mysqli_commit($conn);

    $arr = [
        'success' => true,
        'message' => "Thêm đánh giá và cập nhật sản phẩm thành công"
    ];
} catch (Exception $e) {
    mysqli_rollback($conn);
    $arr = [
        'success' => false,
        'message' => "Lỗi khi thêm đánh giá: " . $e->getMessage()
    ];
}
mysqli_close($conn);

// Trả về kết quả dưới dạng JSON
header('Content-Type: application/json; charset=utf-8');
echo json_encode($arr);
?>