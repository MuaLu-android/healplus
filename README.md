# Hướng Dẫn Cài Đặt MySQL và Chạy File PHP API Cục Bộ (Localhost)

## Mục đích

Một số API PHP trên hosting server có thể bị lỗi do chưa được cập nhật. Vì vậy, bạn có thể chạy chúng cục bộ bằng XAMPP để tiếp tục phát triển và kiểm thử ứng dụng mà không bị gián đoạn.

---

## 1️⃣ Cài Đặt XAMPP (chứa Apache + MySQL)

### 🔗 Tải XAMPP:

- Windows: https://www.apachefriends.org/index.html
- macOS: https://www.apachefriends.org/index.html

### ✅ Cài đặt:

1. Chạy file cài đặt và làm theo hướng dẫn.
2. Mở XAMPP Control Panel.
3. Bấm Start cho cả 2 service: `Apache` và `MySQL`.

---

## 2️⃣ Tạo Cơ Sở Dữ Liệu MySQL

### Truy cập phpMyAdmin:

- Trình duyệt truy cập: [http://localhost/phpmyadmin](http://localhost/phpmyadmin)

### Thực hiện:

1. Tạo **database mới**, ví dụ: `healplus_db`
2. Import file SQL (https://github.com/MuaLu-android/healplus/blob/main/db/healplus_25_05.sql)  vào database vừa tạo

---

## 3️⃣ Đặt File PHP Vào Thư Mục `htdocs`

1. Mở thư mục cài đặt XAMPP (ví dụ: `C:\xampp\htdocs`)
2. Tạo một thư mục con, ví dụ: `healplus-api`
3. Copy toàn bộ file PHP từ thư mục `backend/PHP` trong dự án vào `htdocs/healplus-api`

## 4️⃣ Cấu Hình Kết Nối Database

Mở file PHP có kết nối CSDL (`connect.php`) và chỉnh sửa:

```php
$host = "localhost";
$db_name = "healplus_db";
$username = "root";
$password = "";
```
## 5️⃣ Cập Nhật Base URL Trong Ứng Dụng Android
const val BASE_URL = "http://10.0.2.2/healplus-api/"  // nếu chạy trên Android Emulator
const val BASE_URL = "http://192.168.x.x/healplus-api/" // nếu chạy thiết bị thật trong cùng WiFi