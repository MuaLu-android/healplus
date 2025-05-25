<?php
include "connect.php";

$sql = "SELECT p.*, 
               IFNULL(GROUP_CONCAT(DISTINCT pi.url ORDER BY pi.idu SEPARATOR '||'), '') AS product_images,
               IFNULL(GROUP_CONCAT(DISTINCT u.unit_name ORDER BY u.idunit SEPARATOR '||'), '') AS unit_names,
               GROUP_CONCAT(DISTINCT e.title ORDER BY e.ide SEPARATOR '||') AS element_names,
               GROUP_CONCAT(DISTINCT tp.title ORDER BY tp.idtp SEPARATOR '||') AS ingredient_titles,
               GROUP_CONCAT(DISTINCT tp.body ORDER BY tp.idtp SEPARATOR '||') AS ingredient_bodies
        FROM Product p
        LEFT JOIN productimages pi ON p.idp = pi.idp
        LEFT JOIN unitinfo u ON p.idp = u.idp
        LEFT JOIN thanhphan tp ON p.idp = tp.idp
        LEFT JOIN element e ON p.ide = e.ide
        GROUP BY p.idp
        ORDER BY p.review DESC
        LIMIT 6";

$stmt = $conn->prepare($sql);
$stmt->execute();
$result = $stmt->get_result();
$products = [];

while ($row = $result->fetch_assoc()) {
    // Process product images
    $row["product_images"] = $row["product_images"] ? explode("||", $row["product_images"]) : [];
    
    // Process unit names
    $row["unit_names"] = $row["unit_names"] ? explode("||", $row["unit_names"]) : [];
    
    // Process element names
    $row["element_names"] = $row["element_names"] ? $row["element_names"] : '';

    // Process ingredients
    $ingredients = [];
    $ingredient_titles = $row["ingredient_titles"] ? explode("||", $row["ingredient_titles"]) : [];
    $ingredient_bodies = $row["ingredient_bodies"] ? explode("||", $row["ingredient_bodies"]) : [];
    
    for ($i = 0; $i < count($ingredient_titles); $i++) {
        $ingredients[] = [
            'title' => $ingredient_titles[$i],
            'body' => isset($ingredient_bodies[$i]) ? $ingredient_bodies[$i] : ''
        ];
    }
    $row["ingredients"] = $ingredients;

    // Get reviews for the product
    $review_sql = "SELECT reviewerName, rating, comment, date, profileImageUrl 
                   FROM productreview 
                   WHERE idp = ?";
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

    // Remove unnecessary fields
    unset($row["ingredient_titles"], $row["ingredient_bodies"]);
    
    $products[] = $row;
}

// Return JSON response
header('Content-Type: application/json');
echo json_encode($products, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);

$stmt->close();
$conn->close();
?>
