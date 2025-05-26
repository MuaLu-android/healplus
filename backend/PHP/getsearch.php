<?php 
include "connect.php";
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
           OR ing.title LIKE '%$search%'
           OR c.title LIKE '%$search%'
        GROUP BY p.idp";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $products = [];
    while ($row = $result->fetch_assoc()) {
    	// Tách danh sách ảnh và đơn vị thành mảng
		$row["product_images"] = $row["product_images"] ? explode("||", $row["product_images"]) : [];
		$row["unit_names"] = $row["unit_names"] ? explode("||", $row["unit_names"]) : [];
        $products[] = $row;
    }
    echo json_encode($products, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);
} else {
    echo json_encode(["message" => "Không tìm thấy sản phẩm"], JSON_UNESCAPED_UNICODE);
}

$conn->close();
 ?>