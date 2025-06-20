<?php
include "connect.php";
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['idp'], $_POST['name'], $_POST['trademark'], $_POST['rating'], $_POST['review'], $_POST['sold'], $_POST['price'], $_POST['preparation'], $_POST['specification'], $_POST['origin'], $_POST['manufacturer'], $_POST['production'], $_POST['ingredient'], $_POST['description'], $_POST['quantity'], $_POST['showRecommended'], $_POST['ide'], $_POST['productiondate'], $_POST['congdung'], $_POST['cachdung'], $_POST['tacdungphu'], $_POST['baoquan'])) {

    $idp = trim($_POST['idp']);
    $name = trim($_POST['name']);
    $trademark = trim($_POST['trademark']);
    $rating = trim($_POST['rating']);
    $review = trim($_POST['review']);
    $sold = trim($_POST['sold']);
    $price = trim($_POST['price']);
    $preparation = trim($_POST['preparation']);
    $specification = trim($_POST['specification']);
    $origin = trim($_POST['origin']);
    $manufacturer = trim($_POST['manufacturer']);
    $production = trim($_POST['production']);
    $ingredient = trim($_POST['ingredient']);
    $description = trim($_POST['description']);
    $quantity = trim($_POST['quantity']);
    $showRecommended = trim($_POST['showRecommended']);
    $ide = trim($_POST['ide']);
    $productiondate = trim($_POST['productiondate']);
    $congdung = trim($_POST['congdung']);
    $cachdung = trim($_POST['cachdung']);
    $tacdungphu = trim($_POST['tacdungphu']);
    $baoquan = trim($_POST['baoquan']);
    $idp = mysqli_real_escape_string($conn, $idp);
    $name = mysqli_real_escape_string($conn, $name);
    $trademark = mysqli_real_escape_string($conn, $trademark);
    $rating = floatval($rating); // Chuyển đổi thành số thực
    $review = intval($review);   // Chuyển đổi thành số nguyên
    $sold = intval($sold); // Chuyển đổi thành số nguyên
    $price = floatval($price);     // Chuyển đổi thành số thực
    $preparation = mysqli_real_escape_string($conn, $preparation);
    $specification = mysqli_real_escape_string($conn, $specification);
    $origin = mysqli_real_escape_string($conn, $origin);
    $manufacturer = mysqli_real_escape_string($conn, $manufacturer);
    $production = mysqli_real_escape_string($conn, $production);
    $ingredient = mysqli_real_escape_string($conn, $ingredient);
    $description = mysqli_real_escape_string($conn, $description);
    $quantity = intval($quantity);   // Chuyển đổi thành số nguyên
    $showRecommended = intval($showRecommended); // Chuyển đổi thành số nguyên
    $ide = mysqli_real_escape_string($conn, $ide);
    $productiondate = mysqli_real_escape_string($conn, $productiondate);
    $congdung = mysqli_real_escape_string($conn, $congdung);
    $cachdung = mysqli_real_escape_string($conn, $cachdung);
    $tacdungphu = mysqli_real_escape_string($conn, $tacdungphu);
    $baoquan = mysqli_real_escape_string($conn, $baoquan);
    mysqli_begin_transaction($conn);

    try {
        $query = "UPDATE `product` SET 
                  `name` = '$name', 
                  `trademark` = '$trademark', 
                  `rating` = $rating, 
                  `review` = $review, 
                  `sold` = $sold, 
                  `price` = $price, 
                  `preparation` = '$preparation', 
                  `origin` = '$origin', 
                  `manufacturer` = '$manufacturer', 
                  `description` = '$description', 
                  `showRecommended` = $showRecommended, 
                  `ide` = '$ide', 
                  `productiondate` = '$productiondate', 
                  `specification` = '$specification', 
                  `ingredient` = '$ingredient', 
                  `quantity` = $quantity, 
                  `congdung` = '$congdung', 
                  `cachdung` = '$cachdung', 
                  `tacdungphu` = '$tacdungphu', 
                  `baoquan` = '$baoquan' 
                  WHERE `idp` = '$idp'";

        $data = mysqli_query($conn, $query);
        if (!$data) {
            throw new Exception("Lỗi cập nhật sản phẩm: " . mysqli_error($conn));
        }
        if (isset($_POST['productImages'])) {
            $productImages = json_decode($_POST['productImages'], true);
            if (is_array($productImages)) {
                $sqlDeleteImages = "DELETE FROM productimages WHERE idp = '$idp'";
                if (!mysqli_query($conn, $sqlDeleteImages)) {
                    throw new Exception("Lỗi xóa ảnh cũ: " . mysqli_error($conn));
                }
                foreach ($productImages as $image) {
                    $imageUrl = mysqli_real_escape_string($conn, $image);
                    $queryImage = "INSERT INTO productimages (url, idp) VALUES ('$imageUrl', '$idp')";
                    if (!mysqli_query($conn, $queryImage)) {
                        throw new Exception("Lỗi thêm ảnh mới: " . mysqli_error($conn));
                    }
                }
            }
        }
        if (isset($_POST['thanhphan'])) {
            $thanhphan = json_decode($_POST['thanhphan'], true);
            if (is_array($thanhphan)) {
                $sqlDeleteThanhPhan = "DELETE FROM thanhphan WHERE idp = '$idp'";
                if (!mysqli_query($conn, $sqlDeleteThanhPhan)) {
                    throw new Exception("Lỗi xóa thành phần cũ: " . mysqli_error($conn));
                }
                foreach ($thanhphan as $tp) {
                    $title = mysqli_real_escape_string($conn, $tp['title']);
                    $body = mysqli_real_escape_string($conn, $tp['body']);
                    $queryThanhPhan = "INSERT INTO thanhphan (idp, title, body) VALUES ('$idp', '$title', '$body')";
                    if (!mysqli_query($conn, $queryThanhPhan)) {
                        throw new Exception("Lỗi thêm thành phần mới: " . mysqli_error($conn));
                    }
                }
            }
        }
        if (isset($_POST['unitNames'])) {  // Client gửi lên 'unitNames'
            $unitNames = json_decode($_POST['unitNames'], true);
            if (is_array($unitNames)) {
                $sqlDeleteUnitInfo = "DELETE FROM unitinfo WHERE idp = '$idp'"; // Tên bảng là unitinfo
                if (!mysqli_query($conn, $sqlDeleteUnitInfo)) {
                    throw new Exception("Lỗi xóa đơn vị cũ: " . mysqli_error($conn));
                }
                foreach ($unitNames as $unitName) {
                    $unitNameValue = mysqli_real_escape_string($conn, $unitName);
                    $queryUnit = "INSERT INTO unitinfo (unit_name, idp) VALUES ('$unitNameValue', '$idp')"; // Tên bảng là unitinfo, sửa lại thứ tự cột
                    if (!mysqli_query($conn, $queryUnit)) {
                        throw new Exception("Lỗi thêm đơn vị mới: " . mysqli_error($conn));
                    }
                }
            }
        }
        mysqli_commit($conn);

        $arr = [
            'success' => true,
            'message' => 'Cập nhật sản phẩm thành công'
        ];

    } catch (Exception $e) {
        mysqli_rollback($conn);
        $arr = [
            'success' => false,
            'message' => 'Cập nhật sản phẩm không thành công'
                    ];
    }

} else {
    $arr = [
        'success' => false,
        'message' => 'Dữ liệu không đầy đủ hoặc không hợp lệ'
    ];
}

header('Content-Type: application/json; charset=utf-8'); // Thêm header để trả về JSON và mã hóa UTF-8
echo json_encode($arr, JSON_UNESCAPED_UNICODE); // Sử dụng JSON_UNESCAPED_UNICODE để hỗ trợ tiếng Việt

mysqli_close($conn);
?>