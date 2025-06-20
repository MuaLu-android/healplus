<?php 
include "connect.php";
// Nhận iding từ request (chuỗi)
$iding = isset($_GET['iding']) ? $_GET['iding'] : '';

$sql = "SELECT p.*,
               e.title AS element_names,  -- Lấy tên element
               IFNULL(GROUP_CONCAT(DISTINCT pi.url ORDER BY pi.idu SEPARATOR '||'), '') AS product_images,
               IFNULL(GROUP_CONCAT(DISTINCT u.unit_name ORDER BY u.idunit SEPARATOR '||'), '') AS unit_names,
               GROUP_CONCAT(DISTINCT tp.title ORDER BY tp.idtp SEPARATOR '||') AS ingredient_titles,
               GROUP_CONCAT(DISTINCT tp.body ORDER BY tp.idtp SEPARATOR '||') AS ingredient_bodies
        FROM Product p
        LEFT JOIN productimages pi ON p.idp = pi.idp
        LEFT JOIN unitinfo u ON p.idp = u.idp
        LEFT JOIN thanhphan tp ON p.idp = tp.idp
        JOIN element e ON p.ide = e.ide
        JOIN ingredient ing ON e.iding = ing.iding
        WHERE ing.iding = ?
        GROUP BY p.idp";

$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $iding); // "s" vì iding là chuỗi
$stmt->execute();
$result = $stmt->get_result();

$products = [];
while ($row = $result->fetch_assoc()) {
    // Tách danh sách ảnh và đơn vị thành mảng
    $row["product_images"] = $row["product_images"] ? explode("||", $row["product_images"]) : [];
    $row["unit_names"] = $row["unit_names"] ? explode("||", $row["unit_names"]) : [];
    $row["element_names"] = $row["element_names"] ? $row["element_names"] : '';
    // Không cần gán lại $row["element_name"] vì đã lấy trực tiếp từ câu truy vấn

    // Tách thành phần chi tiết thành mảng
    $ingredients = [];
    $ingredient_titles = $row["ingredient_titles"] ? explode("||", $row["ingredient_titles"]) : [];
    $ingredient_bodies = $row["ingredient_bodies"] ? explode("||", $row["ingredient_bodies"]) : [];
    // Ghép các thành phần với thông tin chi tiết vào một mảng
    for ($i = 0; $i < count($ingredient_titles); $i++) {
        $ingredients[] = [
            'title' => $ingredient_titles[$i],
            'body' => isset($ingredient_bodies[$i]) ? $ingredient_bodies[$i] : ''
        ];
    }
    $row["ingredients"] = $ingredients; // Lưu vào mảng ingredients
    // Tách thành phần chi tiết review
    $review_sql = "SELECT reviewerName, rating, comment, date, profileImageUrl FROM productreview WHERE idp = ?";
    $review_stmt = $conn->prepare($review_sql);
    $review_stmt->bind_param("s", $row['idp']);
    $review_stmt->execute();
    $review_result = $review_stmt->get_result();

    $reviewitems = [];
    while ($review = $review_result->fetch_assoc()) {
        $reviewitems[] = $review;
    }
    $review_stmt->close();
    $row["reviewitems"] = $reviewitems;
    unset(
        $row["ingredient_titles"], $row["ingredient_bodies"]);
    $products[] = $row;
}

// Trả về JSON
header('Content-Type: application/json');
echo json_encode($products, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

$stmt->close();
$conn->close();
?>