<?php
include "connect.php";

// Kiểm tra phương thức và dữ liệu đầu vào
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["idp"])) {
    $idp = trim($_POST["idp"]);

    if (!empty($idp)) {
        // Làm sạch idp
        $idp = mysqli_real_escape_string($conn, $idp);

        // Bắt đầu transaction
        mysqli_begin_transaction($conn);

        try {
            // Xóa dữ liệu liên quan trong bảng productimages
            $sqlDeleteImages = "DELETE FROM productimages WHERE idp = '$idp'";
            if (!mysqli_query($conn, $sqlDeleteImages)) {
                throw new Exception("Lỗi xóa ảnh sản phẩm: " . mysqli_error($conn));
            }

            // Xóa dữ liệu liên quan trong bảng thanhphan
            $sqlDeleteThanhPhan = "DELETE FROM thanhphan WHERE idp = '$idp'";
            if (!mysqli_query($conn, $sqlDeleteThanhPhan)) {
                throw new Exception("Lỗi xóa thành phần sản phẩm: " . mysqli_error($conn));
            }

            // Xóa dữ liệu liên quan trong bảng unit_names
            $sqlDeleteUnitNames = "DELETE FROM unit_names WHERE idp = '$idp'";
            if (!mysqli_query($conn, $sqlDeleteUnitNames)) {
                throw new Exception("Lỗi xóa đơn vị sản phẩm: " . mysqli_error($conn));
            }

            // Xóa sản phẩm từ bảng product
            $sqlDeleteProduct = "DELETE FROM product WHERE idp = '$idp'";
            if (!mysqli_query($conn, $sqlDeleteProduct)) {
                throw new Exception("Lỗi xóa sản phẩm: " . mysqli_error($conn));
            }

            // Commit transaction nếu tất cả các thao tác thành công
            mysqli_commit($conn);

            $response = array("success" => true, "message" => "Xóa sản phẩm thành công.");
        } catch (Exception $e) {
            // Rollback transaction nếu có lỗi
            mysqli_rollback($conn);
            $response = array("success" => false, "message" => "Lỗi: " . $e->getMessage());
        }
    } else {
        $response = array("success" => false, "message" => "ID sản phẩm không được để trống!");
    }
} else {
    $response = array("success" => false, "message" => "Dữ liệu không hợp lệ hoặc thiếu.");
}

// Trả về kết quả dưới dạng JSON
header('Content-Type: application/json');
echo json_encode($response, JSON_UNESCAPED_UNICODE);
mysqli_close($conn);
?>