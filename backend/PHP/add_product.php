<?php
include "connect.php";
$name = $_POST['name'] ?? '';
$trademark = $_POST['trademark'] ?? '';
$rating = $_POST['rating'] ?? 0;
$review = $_POST['review'] ?? 0;
$sold = $_POST['sold'] ?? 0;
$expiry = $_POST['expiry'] ?? '';
$price = $_POST['price'] ?? 0;
$preparation = $_POST['preparation'] ?? '';
$specification = $_POST['specification'] ?? '';
$origin = $_POST['origin'] ?? '';
$manufacturer = $_POST['manufacturer'] ?? '';
$production = $_POST['production'] ?? '';
$ingredient = $_POST['ingredient'] ?? '';
$description = $_POST['description'] ?? '';
$quantity = $_POST['quantity'] ?? 0;
$ide = $_POST['ide'] ?? '';
$productiondate = $_POST['productiondate'] ?? '';
$congdung = $_POST['congdung'] ?? '';
$cachdung = $_POST['cachdung'] ?? '';
$tacdungphu = $_POST['tacdungphu'] ?? '';
$baoquan = $_POST['baoquan'] ?? '';
function parseProductImages($input) {
    $input = trim($input, '[]');
    $images = explode(',', $input);
    return array_map(function($url) {
        return trim($url);
    }, $images);
}
function parseThanhPhan($input) {
    $input = trim($input, '[]');
    $items = explode('),', $input);
    $result = [];
    foreach ($items as $item) {
        $item = trim($item, ' ()');
        if (empty($item)) continue;
        if (preg_match('/title=(.*?),\s*body=(.*)/i', $item, $matches)) {
            $result[] = [
                'title' => trim($matches[1]),
                'body' => trim($matches[2])
            ];
        }
    }
    return $result;
}
function parseUnitInfo($input) {
    $input = trim($input, '[]');
    $items = explode('),', $input);
    $result = [];
    foreach ($items as $item) {
        $item = trim($item, ' ()');
        if (empty($item)) continue;
        if (preg_match('/unit_name=(.*?),\s*isSelected=(.*)/i', $item, $matches)) {
            $result[] = [
                'unit_name' => trim($matches[1]),
                'isSelected' => trim($matches[2]) === 'true'
            ];
        }
    }
    return $result;
}
function generateProductId() {
    $prefix = '#P';
    $timestamp = date('ymd');
    $random = substr(md5(uniqid(rand(), true)), 0, 4);
    return $prefix . $timestamp . $random;
}

$idp = generateProductId();

mysqli_begin_transaction($conn);

try {
    $productImages = parseProductImages($_POST['productImages']);
    $thanhphan = parseThanhPhan($_POST['thanhphan']);
    $unitNames = parseUnitInfo($_POST['unitNames']);
    error_log("Parsed productImages: " . print_r($productImages, true));
    error_log("Parsed thanhphan: " . print_r($thanhphan, true));
    error_log("Parsed unitNames: " . print_r($unitNames, true));
    $query = "INSERT INTO `product` (`idp`, `name`, `trademark`, `rating`, `review`, `sold`, `expiry`, 
              `price`, `preparation`, `origin`, `manufacturer`, `description`, `ide`, `productiondate`, 
              `specification`, `ingredient`, `quantity`, `congdung`, `cachdung`, `tacdungphu`, `baoquan`) 
              VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    $stmt = mysqli_prepare($conn, $query);
    if (!$stmt) {
        throw new Exception("Lỗi prepare statement: " . mysqli_error($conn));
    }

    mysqli_stmt_bind_param($stmt, "sssiiissssssssssissss",
        $idp, $name, $trademark, $rating, $review, $sold, $expiry, $price, $preparation,
        $origin, $manufacturer, $description, $ide, $productiondate, $specification,
        $ingredient, $quantity, $congdung, $cachdung, $tacdungphu, $baoquan
    );

    if (!mysqli_stmt_execute($stmt)) {
        throw new Exception("Lỗi thêm sản phẩm: " . mysqli_stmt_error($stmt));
    }

     if (!empty($productImages) && is_array($productImages)) {
        foreach ($productImages as $imageUrl) {
            $imageUrl = mysqli_real_escape_string($conn, $imageUrl);
            $queryImage = "INSERT INTO productimages (url, idp) VALUES ('$imageUrl', '$idp')";
            if (!mysqli_query($conn, $queryImage)) {
                throw new Exception("Lỗi thêm ảnh sản phẩm: " . mysqli_error($conn));
            }
        }
    }
    if (!empty($thanhphan) && is_array($thanhphan)) {
        foreach ($thanhphan as $tp) {
            $title = mysqli_real_escape_string($conn, $tp['title']);
            $body = mysqli_real_escape_string($conn, $tp['body']);
            $queryThanhPhan = "INSERT INTO thanhphan (idp, title, body) VALUES ('$idp', '$title', '$body')";
            if (!mysqli_query($conn, $queryThanhPhan)) {
                throw new Exception("Lỗi thêm thành phần: " . mysqli_error($conn));
            }
        }
    }
    if (!empty($unitNames) && is_array($unitNames)) {
        foreach ($unitNames as $unit) {
            $unitName = mysqli_real_escape_string($conn, $unit['unit_name']);
            $queryUnit = "INSERT INTO unitinfo (unit_name, idp) VALUES ('$unitName', '$idp')";
            if (!mysqli_query($conn, $queryUnit)) {
                throw new Exception("Lỗi thêm đơn vị: " . mysqli_error($conn));
            }
        }
    }
    mysqli_commit($conn);
    $arr = [
        'success' => true,
        'message' => 'Thêm sản phẩm thành công',
        'debug' => [
            'images' => $productImages,
            'thanhphan' => $thanhphan,
            'units' => $unitNames
        ]

    ];
} catch (Exception $e) {
    mysqli_rollback($conn);
    $arr = [
        'success' => false,
        'message' => $e->getMessage()
    ];
}
print_r(json_encode($arr));
?>