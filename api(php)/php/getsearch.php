<?php 
ini_set('display_errors', 1);
error_reporting(E_ALL);
mb_internal_encoding('UTF-8');
header('Content-Type: application/json; charset=utf-8');
include "connect.php";
$conn->set_charset("utf8mb4");
$search = isset($_GET['search']) ? $conn->real_escape_string($_GET['search']) : '';

$sql = "SELECT p.*, 
               IFNULL(GROUP_CONCAT(DISTINCT pi.url ORDER BY pi.idu SEPARATOR '||'), '') AS product_images,
               IFNULL(GROUP_CONCAT(DISTINCT u.unit_name ORDER BY u.idunit SEPARATOR '||'), '') AS unit_names
        FROM Product p
        LEFT JOIN productimages pi ON p.idp = pi.idp
        LEFT JOIN unitinfo u ON p.idp = u.idp
        LEFT JOIN Element e ON p.ide = e.ide
        LEFT JOIN Ingredient ing ON e.iding = ing.iding
        LEFT JOIN Category c ON ing.idc = c.idc
        WHERE p.name LIKE '%$search%' 
           OR p.ingredient LIKE '%$search%'
           OR p.description LIKE '%$search%'
           OR e.title LIKE '%$search%'
        GROUP BY p.idp";
$conn->query("SET SESSION group_concat_max_len = 1000000");
$stmt = $conn->prepare($sql);
$stmt->execute();
$result = $stmt->get_result();
$products = [];
if (!$result) {
    $response = ["error" => "Lỗi query: " . $conn->error];
} else {
    while ($row = $result->fetch_assoc()) {
        $row["product_images"] = !empty($row["product_images"]) ? explode("||", $row["product_images"]) : [];
        $row["unit_names"] = !empty($row["unit_names"]) ? explode("||", $row["unit_names"]) : [];
        $row["element_names"] = !empty($row["element_names"]) ? $row["element_names"] : '';
        $ingredients = [];
        $ingredient_titles = !empty($row["ingredient_titles"]) ? explode("||", $row["ingredient_titles"]) : [];
        $ingredient_bodies = !empty($row["ingredient_bodies"]) ? explode("||", $row["ingredient_bodies"]) : [];
        for ($i = 0; $i < count($ingredient_titles); $i++) {
            $ingredients[] = [
                'title' => $ingredient_titles[$i],
                'body' => isset($ingredient_bodies[$i]) ? $ingredient_bodies[$i] : ''
            ];
        }
        $row["ingredients"] = $ingredients;
        $review_sql = "SELECT reviewerName, rating, comment, date, profileImageUrl 
                      FROM productreview 
                      WHERE idp = ?";
        $review_stmt = $conn->prepare($review_sql);
        if ($review_stmt) {
            $review_stmt->bind_param("s", $row['idp']);
            $review_stmt->execute();
            $review_result = $review_stmt->get_result();
            $reviewitems = [];
            while ($review = $review_result->fetch_assoc()) {
                $reviewitems[] = $review;
            }
            $review_stmt->close();
            $row["reviewitems"] = $reviewitems;
        }
        unset($row["ingredient_titles"], $row["ingredient_bodies"]);
        
        $products[] = $row;
    }
    $response = ["products" => $products];
}

echo json_encode($products, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);
$stmt->close();
$conn->close();
?>
