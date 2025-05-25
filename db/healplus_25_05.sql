-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 25, 2025 at 06:17 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `healplus`
--

-- --------------------------------------------------------

--
-- Table structure for table `banner`
--

CREATE TABLE `banner` (
  `idbner` int(11) NOT NULL,
  `url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `banner`
--

INSERT INTO `banner` (`idbner`, `url`) VALUES
(1, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741150113/banner-adv-10-10_1_h4now1.webp'),
(2, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741150114/Banner-1920X814_k01wdt.jpg'),
(3, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741150114/Banner-web-Ok-1_mnb7on.png'),
(4, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741150114/Banner-1920X814_k01wdt.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `id` int(11) NOT NULL,
  `userId` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `idc` varchar(50) NOT NULL,
  `title` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`idc`, `title`) VALUES
('ct01', 'Thực phẩm chức năng'),
('ct02', 'Dược mỹ phẩm'),
('ct03', 'Thuốc'),
('ct04', 'Chăm sóc cá nhân'),
('ct05', 'Thiết bị y tế'),
('ct06', 'Bệnh');

-- --------------------------------------------------------

--
-- Table structure for table `element`
--

CREATE TABLE `element` (
  `ide` varchar(50) NOT NULL,
  `title` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `iding` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `element`
--

INSERT INTO `element` (`ide`, `title`, `url`, `quantity`, `iding`) VALUES
('elm01', 'Bổ sung Canxi & Vitamin D', 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741336184/bo_sung_canxi_vitamin_d_level_3_1cac767906_qvdcrl.webp', 40, 'ctm1.1'),
('elm02', 'Vitamin tổng hợp', 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741336184/tpcn_vitamin_tong_hop_level_3_6254452b91_haeomp.webp', 27, 'ctm1.1'),
('elm03', 'Dầu cá, Omega 3, DHA', 'https://laulu.io.vn/healplus/images/dau_ca_omega_3_dha_level_3_814328177f.webp', 27, 'ctm1.1'),
('elm04', 'Vitamin C các loại', 'https://laulu.io.vn/healplus/images/tpcn_vitamin_c_cac_loai_level_3_92215a0f32.webp', 27, 'ctm1.1'),
('elm05', 'Bổ sung Sắt & Axit Folic', 'https://laulu.io.vn/healplus/images/bo_sung_sat_axit_folic_level_3_46a5bbf1c4.webp', 27, 'ctm1.1'),
('elm06', 'Vitamin E các loại', 'https://laulu.io.vn/healplus/images/tpcn_vitamin_e_cac_loai_level_3_c1e33728e3.webp', 27, 'ctm1.1'),
('elm07', 'Bổ sung Kẽm & Magie', 'https://laulu.io.vn/healplus/images/bo_sung_kem_magie_level_3_cc1682a7aa.webp', 27, 'ctm1.1'),
('elm08', 'Sinh lý nam', 'https://laulu.io.vn/healplus/images/sinh_ly_nam_level_3_f76dc0b6c6.webp', 27, 'ctm1.2'),
('elm09', 'Cân bằng nội tiết tố', 'https://laulu.io.vn/healplus/images/can_bang_noi_tiet_to_level_3_7fad40d671.webp', 27, 'ctm1.2'),
('elm10', 'Sinh lý nữ', 'https://laulu.io.vn/healplus/images/sinh_ly_nu_level_3_a1988dcde7.webp', 27, 'ctm1.2'),
('elm100', 'Thuốc kháng nấm', 'https://laulu.io.vn/healplus/images/thuoc_khang_nam_level_3_7eb7c670a6.webp', 56, 'ctm1.22'),
('elm101', 'Siro kháng sinh', 'https://laulu.io.vn/healplus/images/00021694_special_kid_zinc_eric_favre_125ml_6593_62af_large_da861d0f7c.webp', 56, 'ctm1.22'),
('elm102', 'Thuốc kháng lao', 'https://laulu.io.vn/healplus/images/00004745_mecefix_be_200_8641_6425_large_0f2b7255e3.webp', 56, 'ctm1.22'),
('elm103', 'Thuốc kháng sinh', 'https://laulu.io.vn/healplus/images/00021694_special_kid_zinc_eric_favre_125ml_6593_62af_large_da861d0f7c.webp', 56, 'ctm1.22'),
('elm104', 'Thuốc trị giun sán', 'https://laulu.io.vn/healplus/images/thuoc_tru_giun_san_level_3_1_f8188e3b48.webp', 56, 'ctm1.22'),
('elm105', 'Thuốc kháng virus', 'https://laulu.io.vn/healplus/images/thuoc_khang_virus_level_3_16166b9d26.webp', 56, 'ctm1.22'),
('elm106', 'Thuốc trị sốt rét', 'https://laulu.io.vn/healplus/images/thuoc_chong_sot_ret_level_3_1d84829daa.webp', 56, 'ctm1.22'),
('elm107', 'Thuốc xịt mũi', 'https://laulu.io.vn/healplus/images/00033767_nasolspray_mekophar_70ml_6394_624f_large_258b43add1.webp', 56, 'ctm1.23'),
('elm108', 'Thuốc nhỏ tai', 'https://laulu.io.vn/healplus/images/00022340_ileffexime_03_ha_tay_5ml_nho_tai_3916_60dc_large_03ef6deef3.webp', 56, 'ctm1.23'),
('elm109', 'Thuốc trị viêm xoang', 'https://laulu.io.vn/healplus/images/00008157_xylofar_2858_60c6_large_e3d070f01a.webp', 56, 'ctm1.23'),
('elm11', 'Hỗ trợ mãn kinh', 'https://laulu.io.vn/healplus/images/ho_tro_man_kinh_level_3_273d1706e6.webp', 27, 'ctm1.2'),
('elm110', 'Ống hít mũi', 'https://laulu.io.vn/healplus/images/IMG_4936_6ae43e4d3c.webp', 56, 'ctm1.23'),
('elm111', 'Dung dịch súc miệng', 'https://laulu.io.vn/healplus/images/1ceb37b4bc0962573b181_fc39127465.webp', 56, 'ctm1.23'),
('elm112', 'Thuốc tai mũi họng', 'https://laulu.io.vn/healplus/images/IMG_4757_1903fe8b6f.webp', 56, 'ctm1.23'),
('elm113', 'Thuốc bôi răng miệng', 'https://laulu.io.vn/healplus/images/DSC_09829_8c7d113485.webp', 56, 'ctm1.23'),
('elm114', 'Thuốc xịt hen suyễn', 'https://laulu.io.vn/healplus/images/00029055_symbicort_rapihaler_astra_120_lieu_2782_6283_large_0ad74af79f.webp', 56, 'ctm1.23'),
('elm115', 'Thuốc trị tăng nhãn áp', 'https://laulu.io.vn/healplus/images/thuoc_tri_tang_nhan_ap_level_3_7c31f5d9b7.webp', 56, 'ctm1.23'),
('elm116', 'Thuốc nhỏ mắt', 'https://laulu.io.vn/healplus/images/thuoc_nho_mat_level_3_0580df6ffa.webp', 56, 'ctm1.23'),
('elm117', 'Thuốc tra mắt', 'https://laulu.io.vn/healplus/images/00503080_eyedin_makcur_5ml_4580_63b6_large_35efb6002f.webp', 56, 'ctm1.23'),
('elm118', 'Thuốc lợi tiểu', 'https://laulu.io.vn/healplus/images/thuoc_loi_tieu_level_3_b1c45e64e8.webp', 56, 'ctm1.24'),
('elm119', 'Thuốc gan mật', 'https://laulu.io.vn/healplus/images/00004637_macibin_300mg_4243_5b56_large_b5cf74ca83.webp', 56, 'ctm1.24'),
('elm12', 'Chức năng gan', 'https://laulu.io.vn/healplus/images/chuc_nang_gan_level_3_aeb34d62e2.webp', 27, 'ctm1.3'),
('elm120', 'Thuốc dạ dày', 'https://laulu.io.vn/healplus/images/IMG_1552_f6fe14d42c.webp', 56, 'ctm1.24'),
('elm121', 'Siro tiêu hoá', 'https://laulu.io.vn/healplus/images/00021121_forikid_tw3_125ml_4457_60af_large_733ed89c70.webp', 56, 'ctm1.24'),
('elm122', 'Thuốc trị tiêu chảy', 'https://laulu.io.vn/healplus/images/IMG_1718_1ac3c4cfae.webp', 56, 'ctm1.24'),
('elm123', 'Thuốc tiêu hoá', 'https://laulu.io.vn/healplus/images/thuoc_tro_tieu_hoa_level_3_39dadf444b.webp', 56, 'ctm1.24'),
('elm124', 'Thuốc trị táo bón', 'https://laulu.io.vn/healplus/images/IMG_1770_f7e7588544.webp', 56, 'ctm1.24'),
('elm125', 'Thuốc trị bệnh gan', 'https://laulu.io.vn/healplus/images/00018811_silymax_mediplantex_4x15_9138_60e3_large_86225d66ba.webp', 56, 'ctm1.24'),
('elm126', 'Thuốc trị tiểu đường', 'https://laulu.io.vn/healplus/images/IMG_1770_f7e7588544.webp', 56, 'ctm1.25'),
('elm127', 'Nước Yến', 'https://laulu.io.vn/healplus/images/nuoc_yen_5e1045e778.webp', 56, 'ctm1.26'),
('elm128', 'Kẹo cứng', 'https://laulu.io.vn/healplus/images/keo_cung_level_3_e6047983d4.webp', 56, 'ctm1.26'),
('elm129', 'Nước uống không gas', 'https://laulu.io.vn/healplus/images/nuoc_uong_khong_gas_level_3_75bed986fd.webp', 56, 'ctm1.26'),
('elm13', 'Tăng sức đề kháng, miễn dịch', 'https://laulu.io.vn/healplus/images/tang_suc_de_khang_mien_dich_level_3_0ae00ae1b9.webp', 27, 'ctm1.3'),
('elm130', 'Đường ăn kiêng', 'https://laulu.io.vn/healplus/images/duong_an_kieng_level_3_5c79fab385.webp', 56, 'ctm1.26'),
('elm131', 'Trà thảo dược', 'https://laulu.io.vn/healplus/images/tra_level_3_5691019dab.webp', 56, 'ctm1.26'),
('elm132', 'Kẹo dẻo', 'https://laulu.io.vn/healplus/images/keo_deo_level_3_84794ed1cd.webp', 56, 'ctm1.26'),
('elm133', 'Tổ Yến', 'https://laulu.io.vn/healplus/images/DSC_09937_efe108b7f6_5c7031fd5c.webp', 56, 'ctm1.26'),
('elm134', 'Dung dịch vệ sinh phụ nữ', 'https://laulu.io.vn/healplus/images/dung_dich_ve_sinh_phu_nu_level_3_19824d8b5c.webp', 56, 'ctm1.27'),
('elm135', 'Vệ sinh tai', 'https://laulu.io.vn/healplus/images/ve_sinh_tai_level_3_a01c8c8cb4.webp', 56, 'ctm1.27'),
('elm136', 'Băng vệ sinh', 'https://laulu.io.vn/healplus/images/bang_ve_sinh_level_3_dec044f9b3.webp', 56, 'ctm1.27'),
('elm137', 'Nước rửa tay', 'https://laulu.io.vn/healplus/images/nuoc_rua_tay_level_3_35711014b0.webp', 56, 'ctm1.27'),
('elm138', 'Kem đánh răng', 'https://laulu.io.vn/healplus/images/kem_danh_rang_level_3_e8e7416b01.webp', 56, 'ctm1.28'),
('elm139', 'Bàn chải điện', 'https://laulu.io.vn/healplus/images/ban_chai_danh_rang_level_3_1d073ac3fb.webp', 56, 'ctm1.28'),
('elm14', 'Bổ mắt, bảo vệ mắt', 'https://laulu.io.vn/healplus/images/bo_mat_bao_ve_mat_level_3_3cb3ad46da.webp', 27, 'ctm1.3'),
('elm140', 'Chỉ nha khoa', 'https://laulu.io.vn/healplus/images/chi_nha_khoa_level_3_fb8eaa117c.webp', 56, 'ctm1.28'),
('elm141', 'Chăm sóc răng', 'https://laulu.io.vn/healplus/images/cham_soc_rang_level_3_8928fad010.webp', 57, 'ctm1.28'),
('elm142', 'Nước súc miệng', 'https://laulu.io.vn/healplus/images/nuoc_suc_mieng_level_3_5b2eb4b90e.webp', 58, 'ctm1.28'),
('elm143', 'Khăn giấy, khăn ướt', 'https://laulu.io.vn/healplus/images/khan_giay_khan_uot_level_3_6e37e96a27.webp', 59, 'ctm1.29'),
('elm144', 'Tinh dầu massage', 'https://laulu.io.vn/healplus/images/tinh_dau_massage_level_3_bcd88a201a.webp', 60, 'ctm1.30'),
('elm145', 'Tinh dầu trị cảm', 'https://laulu.io.vn/healplus/images/tinh_dau_tri_cam_level_3_277d3f76db.webp', 61, 'ctm1.30'),
('elm146', 'Tinh dầu xông', 'https://laulu.io.vn/healplus/images/tinh_dau_xong_level_3_544b69611b.webp', 62, 'ctm1.30'),
('elm147', 'Dụng cụ cạo râu', 'https://laulu.io.vn/healplus/images/dung_cu_cao_rau_level_3_39e74bd544.webp', 63, 'ctm1.31'),
('elm148', 'Dụng cụ tẩy lông', 'https://laulu.io.vn/healplus/images/dung_cu_tay_long_level_3_4ea65aa0ab.webp', 64, 'ctm1.31'),
('elm149', 'Dụng cụ vệ sinh mũi', 'https://laulu.io.vn/healplus/images/dung_cu_ve_sinh_mui_level_3_b9c09af6cf.webp', 65, 'ctm1.32'),
('elm15', 'Hỗ trợ trao đổi chất', 'https://laulu.io.vn/healplus/images/ho_tro_trao_doi_chat_level_3_814280cbb0.webp', 27, 'ctm1.3'),
('elm150', 'Kim các loại', 'https://laulu.io.vn/healplus/images/kim_cac_loai_level_3_a5f2b837b1.webp', 66, 'ctm1.32'),
('elm151', 'Máy massage', 'https://laulu.io.vn/healplus/images/may_massage_level_3_4bf6a7b467.webp', 67, 'ctm1.32'),
('elm152', 'Túi chườm', 'https://laulu.io.vn/healplus/images/tui_chuom_level_3_869011b667.webp', 68, 'ctm1.32'),
('elm153', 'Vớ ngăn tĩnh mạch', 'https://laulu.io.vn/healplus/images/vo_ngan_tinh_mach_level_3_4977786e8d.webp', 69, 'ctm1.32'),
('elm154', 'Găng tay', 'https://laulu.io.vn/healplus/images/gang_tay_level_3_397fdd05f4.webp', 70, 'ctm1.32'),
('elm155', 'Đai lưng', 'https://laulu.io.vn/healplus/images/dai_lung_level_3_e6d8b7018b.webp', 71, 'ctm1.32'),
('elm156', 'Dụng cụ vệ sinh tai', 'https://laulu.io.vn/healplus/images/dung_cu_ve_sinh_tai_level_3_3fac8745c8.webp', 72, 'ctm1.32'),
('elm157', 'Đai nẹp', 'https://laulu.io.vn/healplus/images/dai_nep_level_3_73e05bede7.webp', 73, 'ctm1.32'),
('elm158', 'Máy xông khí dung', 'https://laulu.io.vn/healplus/images/may_xong_khi_dung_fa8810f6bf.webp', 74, 'ctm1.32'),
('elm159', 'Các dụng cụ và sản phẩm khác', 'https://laulu.io.vn/healplus/images/dung_cu_khac_level_3_44ee75a43d.webp', 75, 'ctm1.32'),
('elm16', 'Giải rượu, cai rượu', 'https://laulu.io.vn/healplus/images/giai_ruou_cai_ruou_level_3_95c038b70b.webp', 27, 'ctm1.3'),
('elm160', 'Máy đo huyết áp', 'https://laulu.io.vn/healplus/images/may_do_huyet_ap_level_3_d1cc8315d5.webp', 76, 'ctm1.33'),
('elm161', 'Máy, que thử đường huyết', 'https://laulu.io.vn/healplus/images/may_que_thu_duong_huyet_level_3_5dd4c1b707.webp', 77, 'ctm1.33'),
('elm162', 'Thử thai', 'https://laulu.io.vn/healplus/images/thu_thai_level_3_d43b6243ac.webp', 78, 'ctm1.33'),
('elm163', 'Nhiệt kế', 'https://laulu.io.vn/healplus/images/nhiet_ke_level_3_1f60ec9b7d.webp', 79, 'ctm1.33'),
('elm164', 'Kit Test Covid', 'https://laulu.io.vn/healplus/images/kit_test_covid_level_3_anh_that_a4a7e228ea.webp', 80, 'ctm1.33'),
('elm165', 'Máy đo SpO2', 'https://laulu.io.vn/healplus/images/may_do_spo2_level_3_anh_that_81c8bb7fae.webp', 81, 'ctm1.33'),
('elm166', 'Băng y tế', 'https://laulu.io.vn/healplus/images/bang_y_te_level_3_723e6fe8ea.webp', 82, 'ctm1.34'),
('elm167', 'Bông y tế', 'https://laulu.io.vn/healplus/images/bong_y_te_level_3_f5f1e97bfe.webp', 83, 'ctm1.34'),
('elm168', 'Cồn, nước sát trùng, nước muối', 'https://laulu.io.vn/healplus/images/con_va_nuoc_sat_trung_level_3_14cf1667c6.webp', 84, 'ctm1.34'),
('elm169', 'Chăm sóc vết thương', 'https://laulu.io.vn/healplus/images/cham_soc_vet_thuong_level_3_0d9f2632cc.webp', 85, 'ctm1.34'),
('elm17', 'Chống lão hóa', 'https://laulu.io.vn/healplus/images/chong_lao_hoa_level_3_678036ed5a.webp', 27, 'ctm1.3'),
('elm170', 'Xịt giảm đau, kháng viêm', 'https://laulu.io.vn/healplus/images/xit_giam_dau_level_3_556e16eb7f.webp', 86, 'ctm1.34'),
('elm171', 'Miếng dán giảm đau, hạ sốt', 'https://laulu.io.vn/healplus/images/mieng_dan_giam_dau_level_3_f348574869.webp', 87, 'ctm1.34'),
('elm172', 'Khẩu trang y tế', 'https://laulu.io.vn/healplus/images/khau_trang_y_te_level_3_dd6078fcdc.webp', 88, 'ctm1.35'),
('elm173', 'Khẩu trang vải', ' https://laulu.io.vn/healplus/images/khau_trang_vai_level_3_09b76a7c62.webp', 89, 'ctm1.35'),
('elm18', 'Cơ xương khớp', 'https://laulu.io.vn/healplus/images/co_xuong_khop_level_3_95a7a803b7.webp', 27, 'ctm1.4'),
('elm19', 'Hô hấp, ho, xoang', 'https://laulu.io.vn/healplus/images/ho_hap_ho_xoang_level_3_1ac351eba2.webp', 27, 'ctm1.4'),
('elm20', 'Thận, tiền liệt tuyến', 'https://laulu.io.vn/healplus/images/than_tien_liet_tuyen_level_3_7e9789c0cb.webp', 27, 'ctm1.4'),
('elm21', 'Hỗ trợ điều trị tiểu đường', 'https://laulu.io.vn/healplus/images/ho_tro_dieu_tri_tieu_duong_level_3_4c427c8a78.webp', 27, 'ctm1.4'),
('elm22', 'Hỗ trợ điều trị gout', 'https://laulu.io.vn/healplus/images/ho_tro_dieu_tri_gout_level_3_65d9072f00.webp', 28, 'ctm1.4'),
('elm23', 'Hỗ trợ điều trị ung thư', 'https://laulu.io.vn/healplus/images/ho_tro_dieu_tri_ung_thu_level_3_8b322441b9.webp', 29, 'ctm1.4'),
('elm24', 'Dạ dày, tá tràng', 'https://laulu.io.vn/healplus/images/da_day_ta_trang_level_3_15bf0448a2.webp', 30, 'ctm1.5'),
('elm25', 'Táo bón', 'https://laulu.io.vn/healplus/images/tao_bon_level_3_d5c6858ee6.webp', 31, 'ctm1.5'),
('elm26', 'Vi sinh - Probiotic', 'https://laulu.io.vn/healplus/images/vi_sinh_probiotic_level_3_b7bc79d9ac.webp', 32, 'ctm1.5'),
('elm27', 'Đại tràng', 'https://laulu.io.vn/healplus/images/dai_trang_level_3_f096a0e215.webp', 33, 'ctm1.5'),
('elm28', 'Khó tiêu', 'https://laulu.io.vn/healplus/images/kho_tieu_level_3_e962281443.webp', 34, 'ctm1.5'),
('elm29', 'Bổ não - cải thiện trí nhớ', 'https://laulu.io.vn/healplus/images/hoat_huyet_level_3_c7497e7c74.webp', 35, 'ctm1.6'),
('elm30', 'Hỗ trợ giấc ngủ ngon', 'https://laulu.io.vn/healplus/images/ho_tro_giac_ngu_ngon_level_3_1a522b6739.webp', 36, 'ctm1.6'),
('elm31', 'Tuần hoàn máu', 'https://laulu.io.vn/healplus/images/hoat_huyet_level_3_c7497e7c74.webp', 37, 'ctm1.6'),
('elm32', 'Kiểm soát căng thẳng', 'https://laulu.io.vn/healplus/images/kiem_soat_cang_thang_level_3_5b82258e04.webp', 38, 'ctm1.6'),
('elm33', 'Hoạt huyết', 'https://laulu.io.vn/healplus/images/hoat_huyet_level_3_c7497e7c74.webp', 39, 'ctm1.6'),
('elm34', 'Da', 'https://laulu.io.vn/healplus/images/da_level_3_59b0cc4d72.webp', 40, 'ctm1.7'),
('elm35', 'Hỗ trợ giảm cân', 'https://laulu.io.vn/healplus/images/ho_tro_giam_can_level_3_6105c610e5.webp', 41, 'ctm1.7'),
('elm36', 'Tóc', 'https://laulu.io.vn/healplus/images/toc_level_3_7ae28c8308.webp', 42, 'ctm1.7'),
('elm37', 'Giảm Cholesterol', 'https://laulu.io.vn/healplus/images/giam_cholesterol_level_3_f5f15760fa.webp', 43, 'ctm1.8'),
('elm38', 'Huyết áp', 'https://laulu.io.vn/healplus/images/huyet_ap_level_3_f1d79c5311.webp', 44, 'ctm1.8'),
('elm39', 'Suy giãn tĩnh mạch', 'https://laulu.io.vn/healplus/images/suy_gian_tinh_mach_level_3_41129d7b67.webp', 45, 'ctm1.8'),
('elm40', 'Sữa', 'https://laulu.io.vn/healplus/images/sua_level_3_42d58ad9e3.webp', 46, 'ctm1.9'),
('elm41', 'Dinh dưỡng trẻ em', 'http://laulu.io.vn/healplus/images/dinh_duong_tre_em_level_3_85c4b519c6.webp', 47, 'ctm1.9'),
('elm42', 'Sữa rửa mặt (Kem, gel, sữa)', 'https://laulu.io.vn/healplus/images/sua_rua_mat_kem_gel_sua_level_3_b58238bc61.webp', 48, 'ctm1.10'),
('elm43', 'Kem chống nắng da mặt', 'https://laulu.io.vn/healplus/images/chong_nang_da_mat_level_3_cf98e4420a.webp', 49, 'ctm1.10'),
('elm44', 'Dưỡng da mặt', 'https://laulu.io.vn/healplus/images/duong_da_mat_level_3_831593bde3.webp', 50, 'ctm1.10'),
('elm45', 'Mặt nạ', 'https://laulu.io.vn/healplus/images/mat_na_level_3_c01279ed63.webp', 51, 'ctm1.10'),
('elm46', 'Serum, Essence hoặc Ampoule', 'https://laulu.io.vn/healplus/images/serum_essence_hoac_ampoule_level_3_83a3609f46.webp', 52, 'ctm1.10'),
('elm47', 'Toner (nước hoa hồng) / Lotion', 'https://laulu.io.vn/healplus/images/toner_nuoc_hoa_hong_lotion_level_3_56a3880051.webp', 53, 'ctm1.10'),
('elm48', 'Tẩy tế bào chết', 'https://laulu.io.vn/healplus/images/tay_te_bao_chet_level_3_47bb7db741.webp', 54, 'ctm1.10'),
('elm49', 'Xịt khoáng', 'https://laulu.io.vn/healplus/images/xit_khoang_level_3_10f9e7133e.webp', 55, 'ctm1.10'),
('elm50', 'Nước tẩy trang, dầu tẩy trang', 'https://laulu.io.vn/healplus/images/tay_trang_level_3_0ad60c8039.webp', 56, 'ctm1.10'),
('elm51', 'Sữa tắm, xà bông', 'https://laulu.io.vn/healplus/images/sua_tam_xa_bong_level_3_02e1f2f5a8.webp', 56, 'ctm1.11'),
('elm52', 'Chống nắng toàn thân', 'http://laulu.io.vn/healplus/images/chong_nang_toan_than_level_3_636d52d165.webp', 56, 'ctm1.11'),
('elm53', 'Lăn khử mùi, xịt khử mùi', 'https://laulu.io.vn/healplus/images/khu_mui_level_3_52900e3ee5.webp', 56, 'ctm1.11'),
('elm54', 'Sữa dưỡng thể, kem dưỡng thể', 'https://laulu.io.vn/healplus/images/duong_the_level_3_bcdb90f6b9.webp', 56, 'ctm1.11'),
('elm55', 'Trị nứt da', 'https://laulu.io.vn/healplus/images/tri_nut_da_level_3_c7b2c21531.webp', 56, 'ctm1.11'),
('elm56', 'Kem dưỡng da tay, chân', 'https://laulu.io.vn/healplus/images/duong_tay_chan_level_3_e8bbaf4556.webp', 56, 'ctm1.11'),
('elm57', 'Chăm sóc ngực', 'https://laulu.io.vn/healplus/images/cham_soc_nguc_level_3_edf67328fe.webp', 56, 'ctm1.11'),
('elm58', 'Massage', 'https://laulu.io.vn/healplus/images/massage_level_3_993d7970c6.webp', 56, 'ctm1.11'),
('elm59', 'Trị sẹo, mờ vết thâm', 'https://laulu.io.vn/healplus/images/viem_da_co_dia_level_3_08332b59dd%20%281%29.webp', 56, 'ctm1.12'),
('elm60', 'Kem trị mụn, gel trị mụn', 'https://laulu.io.vn/healplus/images/da_bi_mun_level_3_7ba8d236fe.webp', 56, 'ctm1.12'),
('elm61', 'Dưỡng da bị khô, thiếu ẩm', 'https://laulu.io.vn/healplus/images/da_bi_kho_thieu_am_level_3_6651a18c3f.webp', 56, 'ctm1.12'),
('elm62', 'Kem trị nám, tàn nhang, đốm nâu', 'https://laulu.io.vn/healplus/images/nam_tan_nhang_dom_nau_level_3_1b8266d569.webp', 56, 'ctm1.12'),
('elm63', 'Viêm da cơ địa', 'https://laulu.io.vn/healplus/images/viem_da_co_dia_level_3_08332b59dd%20%281%29.webp', 56, 'ctm1.12'),
('elm64', 'Da bị kích ứng', 'https://laulu.io.vn/healplus/images/da_bi_kich_ung_level_3_4145471ab3.webp', 56, 'ctm1.12'),
('elm65', 'Tái tạo, chống lão hóa da', 'https://laulu.io.vn/healplus/images/tai_tao_chong_lao_hoa_da_level_3_d76f993d3d.webp', 56, 'ctm1.12'),
('elm66', 'Da sạm, xỉn màu', 'https://laulu.io.vn/healplus/images/da_sam_xin_mau_level_3_c77347eb1b.webp', 56, 'ctm1.12'),
('elm67', 'Dầu gội dầu xả', 'https://laulu.io.vn/healplus/images/dau_goi_dau_xa_level_3_40dc655332.webp', 56, 'ctm1.13'),
('elm68', 'Dầu gội trị nấm', 'https://laulu.io.vn/healplus/images/dau_goi_tri_nam_level_3_e978aefc9e.webp', 56, 'ctm1.13'),
('elm69', 'Dưỡng tóc, ủ tóc', 'https://laulu.io.vn/healplus/images/duong_toc_u_toc_level_3_e47c8afe1f.webp', 56, 'ctm1.13'),
('elm70', 'Đặc trị cho tóc', 'https://laulu.io.vn/healplus/images/dac_tri_cho_toc_level_3_1d1be90b53.webp', 56, 'ctm1.13'),
('elm71', 'Trang điểm mặt', 'https://laulu.io.vn/healplus/images/viem_da_co_dia_level_3_08332b59dd%20%281%29.webp', 56, 'ctm1.14'),
('elm72', 'Son môi', 'https://laulu.io.vn/healplus/images/son_moi_level_3_3afe8fcc55.webp', 56, 'ctm1.14'),
('elm73', 'Trị quầng thâm, bọng mắt', 'https://laulu.io.vn/healplus/images/tri_quang_tham_bong_mat_level_3_ee87bb96de.webp', 56, 'ctm1.15'),
('elm74', 'Xoá nếp nhăn vùng mắt', 'https://laulu.io.vn/healplus/images/xoa_nep_nhan_vung_mat_level_3_136c5d4838.webp', 56, 'ctm1.15'),
('elm75', 'Dưỡng da mắt', 'https://laulu.io.vn/healplus/images/duong_da_vung_mat_level_3_ab63bfce95.webp', 56, 'ctm1.15'),
('elm76', 'Dầu dừa', 'https://laulu.io.vn/healplus/images/dau_dua_level_3_8b64ed450d.webp', 56, 'ctm1.16'),
('elm77', 'Tinh dầu', 'https://laulu.io.vn/healplus/images/tinh_dau_level_3_0b6f321fb7.webp', 56, 'ctm1.16'),
('elm78', 'Thuốc chống dị ứng', 'https://laulu.io.vn/healplus/images/thuoc_khang_histamin_khang_di_ung_level_3_da7d489753.webp', 56, 'ctm1.17'),
('elm79', 'Thuốc say tàu xe', 'https://laulu.io.vn/healplus/images/IMG_7312_4749e5da70.webp', 56, 'ctm1.17'),
('elm80', 'Thuốc trị mụn', 'https://laulu.io.vn/healplus/images/thuoc_tri_mun_level_3_04495e2286.webp', 56, 'ctm1.18'),
('elm81', 'Thuốc bôi ngoài da', 'https://laulu.io.vn/healplus/images/00002343_dermatix_15g_6038_6327_large_d3c2e9b839.webp', 56, 'ctm1.18'),
('elm82', 'Thuốc sát khuẩn', 'https://laulu.io.vn/healplus/images/00008384_povidon_iod_10_opc_8206_6103_large_1fc95b8d41.webp', 56, 'ctm1.18'),
('elm83', 'Thuốc bôi sẹo - liền sẹo', 'https://laulu.io.vn/healplus/images/00002343_dermatix_15g_6038_6327_large_d3c2e9b839.webp', 56, 'ctm1.18'),
('elm84', 'Dầu mù u', 'https://laulu.io.vn/healplus/images/00018765_dau_mu_u_plus_thien_khanh_12ml_3289_5bf0_large_d9eef7bb6f.webp', 56, 'ctm1.18'),
('elm85', 'Dầu gội trị gàu', 'https://laulu.io.vn/healplus/images/00032497_dau_goi_tri_gau_nazorel_shampoo_ketoconazol_50ml_6253_6161_large_ef80a935c2.webp', 56, 'ctm1.18'),
('elm86', 'Thuốc trị gout', 'https://laulu.io.vn/healplus/images/thuoc_tri_tang_acid_uric_mau_benh_gout_level_3_8afed13461.webp', 56, 'ctm1.19'),
('elm87', 'Thuốc trị thoái hoá khớp', 'https://laulu.io.vn/healplus/images/00022320_difelene_50mg_thai_nakorn_patana_10v_2213_60e0_large_2288ea5b49.webp', 56, 'ctm1.19'),
('elm88', 'Thuốc giãn cơ', 'https://laulu.io.vn/healplus/images/thuoc_gian_co_level_3_6c2c7f1154.webp', 56, 'ctm1.19'),
('elm89', 'Thuốc xương khớp', 'https://laulu.io.vn/healplus/images/00005958_piascledine_300mg_3262_63ab_large_e48ac60099.webp', 56, 'ctm1.19'),
('elm90', 'Thuốc bổ', 'https://laulu.io.vn/healplus/images/00500235_bo_gan_truong_phuc_3x10_7485_6293_large_b117663369.webp', 56, 'ctm1.20'),
('elm91', 'Dinh dưỡng', 'https://laulu.io.vn/healplus/images/vitamin_khoang_chat_level_2_57b22bd117.webp', 56, 'ctm1.20'),
('elm92', 'Thuốc tăng cường sức đề kháng', 'https://laulu.io.vn/healplus/images/00500791_vien_uong_tang_de_khang_multivitamin_zn_d3_royal_care_60v_1169_62be_large_6f3a54bfa4.webp', 56, 'ctm1.20'),
('elm93', 'Thuốc bù điện giải', 'https://laulu.io.vn/healplus/images/chat_dien_giai_level_3_09a2efd4a7.webp', 56, 'ctm1.20'),
('elm94', 'Bổ xương khớp', 'https://laulu.io.vn/healplus/images/00032918_glucosamine_and_chondroitin_jpanwell_120v_9745_61a5_large_41ffa86dc1.webp', 56, 'ctm1.20'),
('elm95', 'Siro bổ', 'https://laulu.io.vn/healplus/images/DSC_08363_4cc0bfe6e0.webp', 56, 'ctm1.20'),
('elm96', 'Thuốc giảm đau hạ sốt', 'https://laulu.io.vn/healplus/images/00501047_paracold_500mg_effervescent_mekophar_4x4_4705_6361_large_c06df1be8a.webp', 56, 'ctm1.21'),
('elm97', 'Thuốc giảm đau kháng viêm', 'https://laulu.io.vn/healplus/images/thuoc_khang_viem_khong_steroid_level_3_65c814086b.webp', 56, 'ctm1.21'),
('elm98', 'Thuốc kháng viêm', 'https://laulu.io.vn/healplus/images/00014235_sotstop_100ml_3267_5b0c_large_69b16c3a9e.webp', 56, 'ctm1.21'),
('elm99', 'Thuốc trị đau nửa đầu', 'https://laulu.io.vn/healplus/images/thuoc_tri_dau_nua_dau_level_3_1d204288e1.webp', 56, 'ctm1.21');

-- --------------------------------------------------------

--
-- Table structure for table `ingredient`
--

CREATE TABLE `ingredient` (
  `iding` varchar(50) NOT NULL,
  `title` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `idc` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ingredient`
--

INSERT INTO `ingredient` (`iding`, `title`, `url`, `idc`) VALUES
('ctm1.1', 'Vitamin & Khoáng chất', 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741150753/tpcn_vitamin_khoang_chat_level_2_91b99b5a64_srlim1.webp', 'ct01'),
('ctm1.10', 'Chăm sóc da mặt', 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741240761/cham_soc_da_mat_level_2_83d5e5f264_bpkbxo.webp', 'ct02'),
('ctm1.11', 'Chăm sóc cơ thể', 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741240761/cham_soc_co_the_level_2_3498128f6b_yendne.webp', 'ct02'),
('ctm1.12', 'Giải pháp làn da', 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741240761/giai_phap_lan_da_level_2_24c57abcd0_shbndy.webp', 'ct02'),
('ctm1.13', 'Chăm sóc tóc - da đầu', 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741240761/cham_soc_toc_da_dau_level_2_8ad8c93cf9_kc87ki.webp', 'ct02'),
('ctm1.14', 'Mỹ phẩm trang điểm', 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741240837/my_pham_trang_diem_level_2_f3eec172b2_lsozps.webp', 'ct02'),
('ctm1.15', 'Chăm sóc da vùng mắt', 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741240761/cham_soc_da_vung_mat_level_2_a354c2cbf1_scdbt4.webp', 'ct02'),
('ctm1.16', 'Sản phẩm từ thiên nhiên', 'https://laulu.io.vn/healplus/images/cham_soc_da_vung_mat_level_2_a354c2cbf1', 'ct02'),
('ctm1.17', 'Thuốc dị ứng', 'https://laulu.io.vn/healplus/images/thuoc_di_ung_level_2_8e4530d58d.webp', 'ct03'),
('ctm1.18', 'Thuốc da liễu', 'https://laulu.io.vn/healplus/images/thuoc_da_lieu_level_2_121b764a11.webp', 'ct03'),
('ctm1.19', 'Cơ - xương - khớp', 'https://laulu.io.vn/healplus/images/he_co_xuong_level_2_02067f5217.webp', 'ct03'),
('ctm1.2', 'Sinh lí - Nội tiết tố', 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741150949/sinh_li_noi_tiet_to_ec55ecdc29_m1mibx.webp', 'ct01'),
('ctm1.20', 'Thuốc bổ & vitamin', 'https://laulu.io.vn/healplus/images/vitamin_khoang_chat_level_2_57b22bd117.webp', 'ct03'),
('ctm1.21', 'Thuốc giảm đau, hạ sốt, kháng viêm', 'https://laulu.io.vn/healplus/images/thuoc_giam_dau_khong_opioid_ha_sot_level_3_fa9b2d11bd.webp', 'ct03'),
('ctm1.22', 'Thuốc kháng sinh, kháng nấm', 'https://laulu.io.vn/healplus/images/thuoc_khang_sinh_duong_toan_than_level_2_b033595f14.webp', 'ct03'),
('ctm1.23', 'Thuốc Mắt, Tai, Mũi, Họng', 'https://laulu.io.vn/healplus/images/he_co_xuong_level_2_02067f5217.webp', 'ct03'),
('ctm1.24', 'Thuốc tiêu hoá & gan mật', 'https://laulu.io.vn/healplus/images/thuoc_khang_sinh_duong_toan_than_level_2_b033595f14.webp', 'ct03'),
('ctm1.25', 'Thuốc trị tiểu đường', 'https://laulu.io.vn/healplus/images/00004324_lantus_100iuml_10ml_lo_1217_6095_large_8b0de819b3.webp', 'ct03'),
('ctm1.26', 'Thực phẩm - Đồ uống', 'https://laulu.io.vn/healplus/images/thuc_pham_do_uong_level_2_eb6bd25816.webp', 'ct04'),
('ctm1.27', 'Vệ sinh cá nhân', 'https://laulu.io.vn/healplus/images/ve_sinh_ca_nhan_level_2_e3eea1b065.webp', 'ct04'),
('ctm1.28', 'Chăm sóc răng miệng', 'https://laulu.io.vn/healplus/images/cham_soc_rang_mieng_level_2_e8db475de8.webp', 'ct04'),
('ctm1.29', 'Đồ dùng gia đình', 'https://laulu.io.vn/healplus/images/do_dung_gia_dinh_level_2_782f681291.webp', 'ct04'),
('ctm1.3', 'Cải thiện tăng cường chức năng', 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741150949/sinh_li_noi_tiet_to_ec55ecdc29_m1mibx.webp', 'ct01'),
('ctm1.30', 'Tinh dầu các loại', 'https://laulu.io.vn/healplus/images/tinh_dau_cac_loai_level_2_73f496ca53.webp', 'ct04'),
('ctm1.31', 'Thiết bị làm đẹp', 'https://laulu.io.vn/healplus/images/thiet_bi_lam_dep_level_2_2a5fd4372b.webp', 'ct04'),
('ctm1.32', 'Dụng cụ y tế', 'https://laulu.io.vn/healplus/images/dung_cu_y_te_level_2_9b136905b5.webp', 'ct05'),
('ctm1.33', 'Dụng cụ theo dõi', 'https://laulu.io.vn/healplus/images/dung_cu_theo_doi_level_2_fac9d43d8f.webp', 'ct05'),
('ctm1.34', 'Dụng cụ sơ cứu', 'https://laulu.io.vn/healplus/images/dung_cu_so_cuu_level_2_8c6a7cfa0d.webp', 'ct05'),
('ctm1.35', 'Khẩu trang', 'https://laulu.io.vn/healplus/images/khau_trang_level_2_6f09dcca5c.webp', 'ct05'),
('ctm1.36', 'Ung thư', 'https://laulu.io.vn/healplus/images/Ung_thu_637f743959.webp', 'ct06'),
('ctm1.37', 'Tim mạch', 'https://laulu.io.vn/healplus/images/Tim_mach_f058f1eba6.webp', 'ct06'),
('ctm1.38', 'Nội tiết - chuyển hóa', 'https://laulu.io.vn/healplus/images/Noi_tiet_Chuyen_hoa_be0acfd9e4.webp', 'ct06'),
('ctm1.39', 'Cơ - Xương - Khớp', 'https://laulu.io.vn/healplus/images/Co_Xuong_Khop_5ae32d7e8c.webp', 'ct06'),
('ctm1.4', 'Hỗ trợ điều trị', 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741150946/ho_tro_dieu_tri_level_2_00d86ca048_vt6cbb.webp', 'ct01'),
('ctm1.5', 'Hỗ trợ tiêu hóa', 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741150948/ho_tro_tieu_hoa_level_2_df7385ed6e_ouwczh.webp', 'ct01'),
('ctm1.6', 'Thần kinh não', 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741150950/than_kinh_nao_level_2_b0cc93af6f_q3ec8e.webp', 'ct01'),
('ctm1.7', 'Hỗ trợ làm đẹp', 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741241090/ho_tro_lam_dep_level_2_87dfb56752_h8epvg.webp', 'ct01'),
('ctm1.8', 'Sức khỏe tim mạch', 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741241090/suc_khoe_tim_mach_level_2_1fc9d156fd_ra08id.webp', 'ct01'),
('ctm1.9', 'Dinh dưỡng', 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741241090/dinh_duong_level_2_6b1af6b735_zf65dt.webp', 'ct01');

-- --------------------------------------------------------

--
-- Table structure for table `oder`
--

CREATE TABLE `oder` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `idauth` varchar(255) NOT NULL,
  `address` text NOT NULL,
  `quantity` int(11) NOT NULL,
  `sumMoney` float NOT NULL,
  `datetime` date DEFAULT NULL,
  `note` text DEFAULT NULL,
  `status` varchar(225) DEFAULT NULL,
  `pay` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `oder`
--

INSERT INTO `oder` (`id`, `name`, `phone`, `email`, `idauth`, `address`, `quantity`, `sumMoney`, `datetime`, `note`, `status`, `pay`) VALUES
(23, 'Nguyễn Văn A', '0123456789', 'item.email', '0xxhEUtiakQd4S9YjSELwPxkSlB3', 'Hùng Tùng Mậu , Hà Nội ', 4, 195, '2025-05-10', '', 'Đã giao hàng', NULL),
(24, 'Nguyễn Hoa Bình', '0355761327', 'item.email', 'LCelOnIwC8hcpJhgJFjhYF2Yj9M2', 'sfuuig, ádffgg', 3, 1676880, '2025-05-10', '', 'Đã giao hàng', NULL),
(25, 'Nguyễn Hoa Bình', '0355761327', 'item.email', 'LCelOnIwC8hcpJhgJFjhYF2Yj9M2', 'sfuuig, ádffgg', 1, 48, '2025-05-10', '', 'Đã giao hàng', NULL),
(26, 'Nguyễn Văn Bình', '0355761327', 'user3434@gmail.com', 'LCelOnIwC8hcpJhgJFjhYF2Yj9M2', 'Hùng Tùng Mậu, Hà Nội', 4, 1157700, '2025-05-20', '', 'Đã giao hàng', NULL),
(27, 'Nguyễn Văn Bình', '0355761327', 'user3434@gmail.com', 'LCelOnIwC8hcpJhgJFjhYF2Yj9M2', 'Hùng Tùng Mậu, Hà Nội', 1, 558960, '2025-05-20', '', 'Đã giao hàng', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `orderdetails`
--

CREATE TABLE `orderdetails` (
  `id` int(11) NOT NULL,
  `Ido` int(11) NOT NULL,
  `idp` varchar(255) NOT NULL,
  `total` float NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orderdetails`
--

INSERT INTO `orderdetails` (`id`, `Ido`, `idp`, `total`, `quantity`) VALUES
(24, 17, '#00345388', 350, 2),
(25, 17, '#00500791', 175, 2),
(26, 18, '#00500791', 175, 3),
(27, 19, '#00500791', 175, 3),
(28, 19, '#00045449', 548, 2),
(29, 19, '#00045955', 480, 1),
(30, 19, '#00345388', 350, 1),
(31, 20, '#00500791', 175, 3),
(32, 21, '#00045955', 48000, 1),
(33, 22, '#00045955', 48, 1),
(34, 22, '#00045955', 48, 3),
(35, 23, '#00045955', 48, 4),
(36, 24, '#00045449', 548000, 3),
(37, 25, '#00045955', 48, 1),
(38, 26, '#00345390', 320000, 3),
(39, 26, '#00500791', 175000, 1),
(40, 27, '#00045449', 548000, 1);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `idp` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `trademark` varchar(255) NOT NULL,
  `rating` float NOT NULL,
  `review` int(11) NOT NULL,
  `sold` int(11) NOT NULL,
  `expiry` varchar(255) NOT NULL,
  `price` int(11) NOT NULL,
  `preparation` text NOT NULL,
  `origin` varchar(255) NOT NULL,
  `manufacturer` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `ide` varchar(50) NOT NULL,
  `productiondate` text DEFAULT NULL,
  `specification` text DEFAULT NULL,
  `ingredient` text DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `congdung` text DEFAULT NULL,
  `cachdung` text DEFAULT NULL,
  `tacdungphu` text DEFAULT NULL,
  `baoquan` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`idp`, `name`, `trademark`, `rating`, `review`, `sold`, `expiry`, `price`, `preparation`, `origin`, `manufacturer`, `description`, `ide`, `productiondate`, `specification`, `ingredient`, `quantity`, `congdung`, `cachdung`, `tacdungphu`, `baoquan`) VALUES
('#00022576', 'Dung dịch LineaBon K2+D3 ErgoPharm hỗ trợ bổ sung Vitamin (10ml)', 'Ergopharm', 5, 0, 0, '9 tháng kể từ ngày sản xuất', 295000, 'Dung dịch', 'Úc', 'ERGOPHARM', 'LineaBon K2+D3 bổ sung Vitamin D3 và vitamin K2, giúp hỗ trợ hấp thu canxi, giúp giảm nguy cơ loãng xương.', 'elm01', '21/04/2024', 'Hộp', 'Vitamin D3, Vitamin K2', 1000, 'LineaBon K2+D3 bổ sung Vitamin D3 và vitamin K2, giúp hỗ trợ hấp thu canxi, giúp giảm nguy cơ loãng xương.', 'Trẻ em từ 0 - 12 tháng: 6 giọt mỗi ngày.\r\nTrẻ em từ 1 - 3 tuổi: 6 - 8 giọt mỗi ngày.\r\nTrẻ em từ 3 - 12 tuổi: 8 - 12 giọt mỗi ngày.\r\nNgười lớn: 12 giọt mỗi ngày.', 'Chưa có thông tin về tác dụng phụ của sản phẩm.', 'Nơi khô ráo, tránh ánh sáng và nhiệt độ dưới 25 độ C'),
('#00045449', 'Siro ống uống Canxi-D3-K2 5ml Kingphar bổ sung canxi & vitamin D3 cho cơ thể (6 vỉ x 5 ống)', 'Kingphar', 5, 0, 432, '9 tháng kể từ ngày sản xuất', 548000, 'Hỗn dịch', 'Việt Nam', 'CÔNG TY TNHH SẢN XUẤT VÀ THƯƠNG MẠI VINH THỊNH VƯỢNG', 'Canxi-D3-K2 Kingphar bổ sung canxi, vitamin D3, K2 cho cơ thể, hỗ trợ giảm nguy cơ thiếu hụt canxi, vitamin D3, K2.', 'elm01', '24/3/2019', 'Hộp 6 Vỉ x 5 Ống', 'Calci glucoheptonat, Vitamin D3, Vitamin K2', 1, 'Canxi-D3-K2 Kingphar bổ sung canxi, vitamin D3, K2 cho cơ thể, hỗ trợ giảm nguy cơ thiếu hụt canxi, vitamin D3, K2.', 'Cách dùng\r\n\r\nTrẻ em 1 - 6 tuổi: Uống 5ml/lần, ngày 2 lần.\r\n\r\nTrẻ em từ 7 tuổi và người lớn: Uống 10ml/lần, ngày 2 lần.\r\n\r\nNên uống vào buổi sáng hoặc trưa.\r\n\r\nLắc đều trước khi sử dụng.\r\n\r\nĐối tượng sử dụng\r\n\r\nCanxi-D3-K2 Kingphar thích hợp dùng cho các trường hợp sau:\r\n\r\nTrẻ em từ 1 tuổi trở lên đang trong giai đoạn phát triển cần bổ sung canxi.\r\nPhụ nữ có thai và cho con bú thiếu hụt canxi.\r\nNgười lớn có nguy cơ loãng xương, người lớn bị loãng xương cần bổ sung canxi.', 'Chưa có thông tin về tác dụng phụ của sản phẩm.', 'Bảo quản nơi khô ráo, nhiệt độ không quá 30⁰C, tránh ánh sáng.\r\n\r\nĐể xa tầm tay trẻ em.\r\n\r'),
('#00045955', 'Viên uống NutriGrow Nutrimed bổ sung canxi, vitamin D3, vitamin K2, hấp thu canxi (60 viên)', 'NUTRIMED', 4.5, 2, 131, '9 tháng kể từ ngày sản xuất', 480000, 'Viên nén', 'Hoa Kỳ', 'EAGLE NUTRITIONAL INC', 'Nutrigrow bổ sung canxi, vitamin D3, vitamin K2, giúp tăng cường hấp thu canxi, giúp xương chắc khỏe. Hỗ trợ phát triển chiều cao cho trẻ.', 'elm01', '23/3/2019', 'Hộp 60 Viên', 'Calcium Citrate, Calcium Carbonate, Vitamin D3, Vitamin K2, Dibasic Calcium Phosphate, Magie oxide, Kẽm oxide, boron citrate, Đồng Oxit', 1, 'Nutrigrow bổ sung canxi, vitamin D3, vitamin K2, giúp tăng cường hấp thu canxi, giúp xương chắc khỏe.\r\n\r\nHỗ trợ phát triển chiều cao cho trẻ.', 'Cách dùng\r\n\r\nTrẻ từ 8 - 16 tuổi: 1 viên, ngày 2 lần.\r\n\r\nTrên 16 tuổi: 1 - 2 viên, ngày 2 lần.\r\n\r\nĐối tượng sử dụng\r\n\r\nNutrigrow thích hợp dùng cho trẻ em trên 8 tuổi cần bổ sung canxi.', 'Chưa có thông tin về tác dụng phụ của sản phẩm.', 'Bảo quản nơi khô ráo, thoáng mát, nhiệt độ không quá 30 độ C, tránh ánh sáng.\r\n\r\nĐể xa tầm tay trẻ em.\r\n\r'),
('#00345388', 'Viên uống Teens Active Vitamins For Life bổ sung vitamin và khoáng chất cho thanh thiếu niên (60 viên)', 'Vitamins For Life', 5, 0, 193, '9 tháng kể từ ngày sản xuất', 350000, 'Viên nén', 'Hoa Kỳ', 'Activ', 'Teens Active bổ sung vitamin và khoáng chất cho thanh thiếu niên, hỗ trợ cân bằng dinh dưỡng.', 'elm02', '26/3/2019', 'Hộp 60 viên', 'Vitamin B6, Vitamin E, Betacarotene, Vitamin B1, Vitamin B2, Vitamin B3, Vitamin B12, Folic Acid, Biotin, Pantothenic acid, Calcium, Vitamin K, Iron, Vitamin D3, Phosphorous, Iodine, Magnesium oxide, Zinc, Manganese, Selenium, Chromium, Copper, Molybdenum, Vitamin C', 1, 'Teens Active bổ sung vitamin và khoáng chất cho thanh thiếu niên, hỗ trợ cân bằng dinh dưỡng.', 'Cách dùng\r\n\r\nMỗi ngày uống 1 viên. Uống sau khi ăn.\r\n\r\nĐối tượng sử dụng\r\n\r\nTeens Active thích hợp sử dụng cho trẻ từ 10 tuổi trở lên.', 'Chưa có thông tin về tác dụng phụ của sản phẩm.', 'Bảo quản nơi khô ráo, thoáng mát, nhiệt độ không quá 30 độ C, tránh ánh sáng trực tiếp.\r\n\r\nĐể xa tầm tay trẻ em.\r\n\r'),
('#00345389', 'Viên uống Omega 3 Fish Oil 1000mg Good Health giúp phát triển não bộ, tốt cho mắt và tim mạch (150 viên)', 'Good Health', 5, 0, 0, '9 tháng kể từ ngày sản xuất', 395000, 'Viên nang', 'New Zealand', 'GOODHEALTH', 'Viên dầu cá Omega 3 Fish Oil 1000Mg Goodhealth bổ sung EPA (icosapent) và DHA giúp phát triển não bộ, tốt cho mắt và tim mạch.', 'elm03', '27/3/2019', 'Hộp 150 Viên', 'Dầu cá, EPA', 50, 'Viên dầu cá Omega 3 Fish Oil 1000Mg Goodhealth bổ sung EPA và DHA giúp phát triển não bộ, tốt cho mắt và tim mạch.', 'Cách dùng\r\nNgười lớn: Uống 1 - 3 viên/ngày cùng với bữa ăn hoặc theo lời khuyên của chuyên gia.\r\n\r\nTrẻ em (từ 2 tuổi trở lên): Uống 1 - 2 viên/ngày cùng bữa ăn hoặc theo lời khuyên của chuyên gia.\r\n\r\nTrẻ dưới 5 tuổi nên cắt nhỏ viên nang và cho vào thức ăn để tránh nguy cơ bị hóc.\r\n\r\nĐối tượng sử dụng\r\nViên dầu cá Omega 3 Fish Oil 1000Mg Goodhealth thích hợp sử dụng cho cả trẻ em và người lớn.', 'Chưa có thông tin về tác dụng phụ của sản phẩm.', 'Bảo quản nơi khô ráo, thoáng mát, nhiệt độ không quá 30oC, tránh ánh sáng trực tiếp.\r\n\r\nĐể xa tầm tay trẻ em.'),
('#00345390', 'Dung dịch Vitamin D3+ DHA Hatro Pharvina tăng sức đề kháng, giảm nguy cơ còi xương (20ml)', 'Hatro', 5, 0, 0, '9 tháng kể từ ngày sản xuất', 320000, 'Dung dịch', 'MANUFACTORY - PHARVINA PHARMACEUTI- CAL JOINT STOCK COMPANY', 'Việt Nam', 'Dung dịch uống Hatro Vitamin D3+ DHA bổ sung vitamin D3, DHA, EPA giúp tăng cường sức đề kháng và tăng hấp thu canxi, giảm nguy cơ còi xương ở trẻ sơ sinh và trẻ nhỏ.', 'elm03', '28/3/2019', 'Hộp x 20ml', 'Vitamin D3, Dầu cá, Vitamin E', 50, 'Hatro Vitamin D3+ DHA bổ sung vitamin D3, DHA, EPA giúp tăng cường sức đề kháng và tăng hấp thu canxi, giảm nguy cơ còi xương ở trẻ sơ sinh và trẻ nhỏ.', 'Cách dùng\r\n\r\nUống 0.5ml/lần, ngày uống 1 lần.\r\n\r\nDùng đường uống trực tiếp vào miệng trẻ, hoặc pha vào thức ăn của trẻ.\r\n\r\nĐối tượng sử dụng\r\n\r\nHatro Vitamin D3+ DHA dùng trong các trường hợp trẻ em chậm lớn, có các triệu chứng kém hấp thu và thiếu hụt canxi: rụng tóc vành khăn, chậm mọc răng, quấy khóc.\r\n\r\nCó thể dùng được cho trẻ dưới 2 tuổi, phụ nữ có thai và cho con bú.', 'Chưa có thông tin về tác dụng phụ của sản phẩm.\r\n\r', 'Bảo quản nơi khô ráo, thoáng mát, nhiệt độ không quá 30oC, tránh ánh sáng trực tiếp.\r\n\r\nĐể xa tầm tay trẻ em.\r\n\r'),
('#00345466', 'Thực phẩm bảo vệ sức khỏe Omexxel E400 hỗ trợ chống oxy hóa, tốt cho da (3 vỉ x 10 viên)', 'OMEXXEL', 4.5, 2, 0, '2 năm kể từ ngày sản xuất', 264000, 'Viên nang mềm', 'Hoa Kỳ', 'PHARMAXX INC', 'Omexxel E400 hỗ trợ chống oxy hóa, tốt cho da.', 'elm03', '22/04/2024', 'Hộp 3 Vỉ x 10 Viên', 'Vitamin E, DẦU CÁ BIỂN', 1000, 'Omexxel E400 hỗ trợ chống oxy hóa, tốt cho da.', 'Người lớn: Uống một viên nang mềm với nước, một lần một ngày sau bữa ăn như một chất bổ sung chế độ ăn uống.', 'Chưa có thông tin về tác dụng phụ của sản phẩm.', 'Đậy kín sản phẩm, bảo quản nơi khô ráo, thoáng mát, tránh ánh sáng trực tiếp.\r\nĐể xa tầm tay trẻ em.'),
('#00500791', 'Viên uống Multivitamin +Zn +D3 Royal Care hỗ trợ tăng cường sức khỏe, nâng cao sức đề kháng (60 viên)', 'Royal Care', 5, 0, 515, '9 tháng kể từ ngày sản xuất', 175000, 'Viên nang cứng', 'Việt Nam', 'VESTA', 'Multivitamin +Zn +D3 Royal Care hỗ trợ tăng cường sức khỏe, nâng cao sức đề kháng, giảm mệt mỏi.', 'elm02', '25/3/2019', 'Hộp 60 Viên', 'Vitamin B1, Vitamin C, Aquamin F, Kẽm Gluconat, Đông trùng hạ thảo, Magnesium oxide, Thái tử sâm, Vitamin E, Vitamin PP, Iron ferronyl, Vitamin H, Vitamin B6, Vitamin B5, Mangan gluconat, Crom picolinat, Selen 2000ppm, Vitamin K, Vitamin B9, Vitamin B12, Vitamin A, Vitamin D3, Vitamin B2', 1, 'Multivitamin + Zn + D3 Royal Care hỗ trợ tăng cường sức khỏe, nâng cao sức đề kháng, giảm mệt mỏi.', 'Cách dùng\r\n\r\nNgười lớn và trẻ em trên 6 tuổi: Ngày uống 1 viên.\r\n\r\nNên sử dụng sản phẩm ít nhất 2 giờ trước khi đi ngủ.\r\n\r\nĐối tượng sử dụng\r\n\r\nMultivitamin + Zn + D3 Royal Care dùng cho trẻ em trên 6 tuổi và người lớn sức khỏe kém, đề kháng kém.', 'Chưa có thông tin về tác dụng phụ của sản phẩm.', 'Bảo quản nơi khô ráo, thoáng mát, nhiệt độ không quá 30 độ C, tránh ánh sáng trực tiếp.\r\n\r\nĐể xa tầm tay trẻ em.\r\n\r');

-- --------------------------------------------------------

--
-- Table structure for table `productimages`
--

CREATE TABLE `productimages` (
  `idu` int(11) NOT NULL,
  `url` varchar(255) NOT NULL,
  `idp` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `productimages`
--

INSERT INTO `productimages` (`idu`, `url`, `idp`) VALUES
(336, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741152014/DSC_05513_4a1beddec4_fz8cne.webp', '#00045955'),
(337, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741152014/DSC_05515_cca60dfb89_yvecar.webp', '#00045955'),
(338, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741152014/DSC_05515_cca60dfb89_yvecar.webp', '#00045955'),
(339, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741152015/DSC_05520_3e8211d9fa_gc3flx.webp', '#00045955'),
(340, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741152015/DSC_05516_1cca670a3c_tdn2qw.webp', '#00045955'),
(341, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741230065/DSC_04876_432156df93_iskn1j.webp', '#00045449'),
(342, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741230064/DSC_04875_5dae04ada2_vlidmm.webp', '#00045449'),
(343, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741230064/DSC_04871_10dedb045f_ycmp05.webp', '#00045449'),
(344, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741230064/DSC_04869_8a2b633863_rqi2u2.webp', '#00045449'),
(345, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741230064/DSC_04872_e30d362f18_dpg4ut.webp', '#00045449'),
(346, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741230064/DSC_04864_8b87847a4a_ixvdcr.webp', '#00045449'),
(347, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741230064/DSC_04870_fc0f928bdd_fmyrnq.webp', '#00045449'),
(348, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741230064/canxi_d3k2_b90f648e94_ut4mbs.jpg', '#00045449'),
(349, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741230064/DSC_04874_6c29236c37_fg9som.webp', '#00045449'),
(350, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741231557/00500791_vien_uong_tang_de_khang_multivitamin_zn_d3_royal_care_60v_1169_62be_large_6f3a54bfa4_hbmvpg.webp', '#00500791'),
(351, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741231557/00500791_vien_uong_tang_de_khang_multivitamin_zn_d3_royal_care_60v_3175_62be_large_8c663c4da1_kz5gpa.jpg', '#00500791'),
(354, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741231558/00500791_vien_uong_tang_de_khang_multivitamin_zn_d3_royal_care_60v_4166_62be_large_733dcfe351_vdzgjx.jpg', '#00500791'),
(355, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741231558/00500791_vien_uong_tang_de_khang_multivitamin_zn_d3_royal_care_60v_4244_62be_large_ffb905e426_ptvsnd.webp', '#00500791'),
(356, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741231558/00500791_vien_uong_tang_de_khang_multivitamin_zn_d3_royal_care_60v_1656662730_bc0208a5ed_guqneb.webp', '#00500791'),
(357, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741231558/00500791_vien_uong_tang_de_khang_multivitamin_zn_d3_royal_care_60v_6821_62be_large_12c94a0905_pc7rp4.webp', '#00500791'),
(359, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741233173/00345388_teens_active_bo_sung_vitamin_cho_thieu_nien_7803_63a9_large_286b8b5d3a_zar4iu.webp', '#00345388'),
(360, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741233171/00345388_teens_active_bo_sung_vitamin_cho_thieu_nien_5201_63a9_large_53a59c40b8_aby487.webp', '#00345388'),
(361, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741233168/00345388_teens_active_bo_sung_vitamin_cho_thieu_nien_4134_63a9_large_3d632b46aa_txglbs.jpg', '#00345388'),
(362, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741233168/00345388_teens_active_bo_sung_vitamin_cho_thieu_nien_5186_63a9_large_c8259bcfea_e1f7y6.webp', '#00345388'),
(363, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741233166/00345388_teens_active_bo_sung_vitamin_cho_thieu_nien_1121_63a9_large_8b12cc1cb2_qae5qv.jpg', '#00345388'),
(364, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741233166/00345388_teens_active_bo_sung_vitamin_cho_thieu_nien_2374_63a9_large_b0b2b8d092_cjzr4o.webp', '#00345388'),
(365, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741233166/00345388_teens_active_bo_sung_vitamin_cho_thieu_nien_3719_63a9_large_c74c06ca5f_gtyhzu.jpg', '#00345388'),
(366, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741233166/00345388_teens_active_bo_sung_vitamin_cho_thieu_nien_1959_63a9_large_bd808b7f8d_av3kch.webp', '#00345388'),
(367, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1741231558/00500791_vien_uong_tang_de_khang_multivitamin_zn_d3_royal_care_60v_1656662730_bc0208a5ed_guqneb.webp', '#00345388'),
(368, 'https://laulu.io.vn/healplus/images_product/IMG_0262_9753e81896.webp', '#00345390'),
(369, 'https://laulu.io.vn/healplus/images_product/IMG_0264_824f604dd3.webp', '#00345390'),
(370, 'https://laulu.io.vn/healplus/images_product/hatro_d8a3ee3bce.webp', '#00345390'),
(371, 'https://laulu.io.vn/healplus/images_product/IMG_0265_4d2accf0fd.webp', '#00345390'),
(372, 'https://laulu.io.vn/healplus/images_product/IMG_0266_da5289a811.webp', '#00345390'),
(373, 'https://laulu.io.vn/healplus/images_product/IMG_0267_e0735a67e9.webp', '#00345390'),
(374, 'https://laulu.io.vn/healplus/images_product/IMG_0268_3c223f11d5.jpg', '#00345390'),
(375, 'https://laulu.io.vn/healplus/images_product/IMG_0272_1f692137d9.webp', '#00345390'),
(376, 'https://laulu.io.vn/healplus/images_product/IMG_0273_4d99c99703.jpg', '#00345390'),
(377, 'https://laulu.io.vn/healplus/images_product/IMG_0274_26cd1973dd.jpg', '#00345390'),
(379, 'https://laulu.io.vn/healplus/images_product/00029095_omega_3_fish_oil_1000mg_goodhealth_150v_4015_62ae_large_fb91da169d.jpg', '#00345389'),
(380, 'https://laulu.io.vn/healplus/images_product/00029095_omega_3_fish_oil_1000mg_goodhealth_150v_4494_62ae_large_d99a1c06aa.webp', '#00345389'),
(381, 'https://laulu.io.vn/healplus/images_product/00029095_omega_3_fish_oil_1000mg_goodhealth_150v_6300_62ae_large_d44c5a66ef.jpg', '#00345389'),
(382, 'https://laulu.io.vn/healplus/images_product/00029095_omega_3_fish_oil_1000mg_goodhealth_150v_9978_62ae_large_addaceaed4.webp', '#00345389'),
(383, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1748064983/DSC_08986_a3ca1e01d8_mjesm7.webp', '#00345466'),
(384, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1748064982/DSC_08988_3dba792ae1_hencyo.webp', '#00345466'),
(385, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1748064982/DSC_08988_3dba792ae1_hencyo.webp', '#00345466'),
(386, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1748064981/DSC_08993_b42324b95f_h5vjic.webp', '#00345466'),
(387, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1748064981/DSC_08993_b42324b95f_h5vjic.webp', '#00345466'),
(388, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1748067781/00022576_lineabon_k2d3_ergopharm_10ml_tre_em_5723_63a9_large_9b6383b4b6_vbksgr.webp', '#00022576'),
(389, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1748067932/00022576_lineabon_k2d3_ergopharm_10ml_tre_em_8638_63a9_large_d5f094f25a_1_zwre5j.webp', '#00022576'),
(390, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1748067783/00022576_lineabon_k2d3_ergopharm_10ml_tre_em_5824_63a9_large_a62f94db03_nzjoog.webp', '#00022576'),
(391, 'https://res.cloudinary.com/dhl2sbjo5/image/upload/v1748067781/00022576_lineabon_k2d3_ergopharm_10ml_tre_em_5740_63a9_large_dd3630311d_xkl70x.webp', '#00022576');

-- --------------------------------------------------------

--
-- Table structure for table `productreview`
--

CREATE TABLE `productreview` (
  `id` int(11) NOT NULL,
  `idp` varchar(50) NOT NULL,
  `reviewerName` varchar(255) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `date` varchar(255) NOT NULL,
  `rating` float NOT NULL,
  `profileImageUrl` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `productreview`
--

INSERT INTO `productreview` (`id`, `idp`, `reviewerName`, `comment`, `date`, `rating`, `profileImageUrl`) VALUES
(4, '#00045955', 'Nguyễn Văn Bình', 'Tôi rất hài lòng về chất lượng của sản phẩm, tôi đánh giá cho 5 sao', '20/05/2025', 4, 'https://laulu.io.vn/healplus/uploads/1000001083.png'),
(5, '#00045955', 'Nguyễn Văn Bình', 'Hiệu quả rõ rệt sau 2 ngày sử dụng, giá cả hợp lí', '20/05/2025', 5, 'https://laulu.io.vn/healplus/uploads/1000001083.png'),
(6, '#00345466', 'Nguyễn Văn Phú', 'Tôi hài lòng với chất lượng của sản phẩm, tôi sẽ quay lại khi cần thiết ', '24/05/2025', 5, 'https://laulu.io.vn/healplus/uploads/1000001096.jpg'),
(7, '#00345466', 'Nguyễn Văn Phú', 'Đã dùng thử và cho thấy hiệu quả rõ rệt, giá cả hợp lý ', '24/05/2025', 4, 'https://laulu.io.vn/healplus/uploads/1000001096.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `thanhphan`
--

CREATE TABLE `thanhphan` (
  `idtp` int(11) NOT NULL,
  `idp` text DEFAULT NULL,
  `title` text DEFAULT NULL,
  `body` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `thanhphan`
--

INSERT INTO `thanhphan` (`idtp`, `idp`, `title`, `body`) VALUES
(1, '#00045955', 'Calcium Citrate', '476mg'),
(2, '#00045955', 'Calcium Carbonate', '375mg'),
(3, '#00045955', 'Vitamin D3', '200iu'),
(4, '#00045955', 'Vitamin K2', '30mcg'),
(5, '#00045955', 'Dibasic Calcium Phosphate', '152mg'),
(6, '#00045955', 'Magie oxide', '20mg'),
(7, '#00045955', 'Kẽm oxide', '1.25mg'),
(8, '#00045955', 'boron citrate', '926mcg'),
(9, '#00045955', 'Đồng Oxit', '156mcg'),
(10, '#00045449', 'Calci glucoheptonat', '550mg'),
(11, '#00045449', 'Vitamin D3', '100iu'),
(12, '#00045449', 'Vitamin K2', '10mcg'),
(13, '#00345389', 'Dầu cá', '1000mg'),
(14, '#00345389', 'EPA', '180mg'),
(15, '#00345390', 'Vitamin D3', '400iu'),
(16, '#00345390', 'Dầu cá', '400mg'),
(17, '#00345390', 'Vitamin E', '5iu'),
(18, '#00045955', 'Calcium Citrate', '476mg'),
(19, '#00045955', 'Calcium Carbonate', '375mg'),
(20, '#00045955', 'Vitamin D3', '200iu'),
(21, '#00045955', 'Vitamin K2', '30mcg'),
(22, '#00045955', 'Dibasic Calcium Phosphate', '152mg'),
(23, '#00045955', 'Magie oxide', '20mg'),
(24, '#00045955', 'Kẽm oxide', '1.25mg'),
(25, '#00045955', 'boron citrate', '926mcg'),
(26, '#00045955', 'Đồng Oxit', '156mcg'),
(27, '#00045449', 'Calci glucoheptonat', '550mg'),
(28, '#00045449', 'Vitamin D3', '100iu'),
(29, '#00045449', 'Vitamin K2', '10mcg'),
(30, '#00345389', 'Dầu cá', '1000mg'),
(31, '#00345389', 'EPA', '180mg'),
(32, '#00345390', 'Vitamin D3', '400iu'),
(33, '#00345390', 'Dầu cá', '400mg'),
(34, '#00345390', 'Vitamin E', '5iu'),
(35, '#00045955', 'Calcium Citrate', '476mg'),
(36, '#00045955', 'Calcium Carbonate', '375mg'),
(37, '#00045955', 'Vitamin D3', '200iu'),
(38, '#00045955', 'Vitamin K2', '30mcg'),
(39, '#00045955', 'Dibasic Calcium Phosphate', '152mg'),
(40, '#00045955', 'Magie oxide', '20mg'),
(41, '#00045955', 'Kẽm oxide', '1.25mg'),
(42, '#00045955', 'boron citrate', '926mcg'),
(43, '#00045955', 'Đồng Oxit', '156mcg'),
(44, '#00045449', 'Calci glucoheptonat', '550mg'),
(45, '#00045449', 'Vitamin D3', '100iu'),
(46, '#00045449', 'Vitamin K2', '10mcg'),
(47, '#00345389', 'Dầu cá', '1000mg'),
(48, '#00345389', 'EPA', '180mg'),
(49, '#00345390', 'Vitamin D3', '400iu'),
(50, '#00345390', 'Dầu cá', '400mg'),
(51, '#00345390', 'Vitamin E', '5iu'),
(52, '#00045955', 'Calcium Citrate', '476mg'),
(53, '#00045955', 'Calcium Carbonate', '375mg'),
(54, '#00045955', 'Vitamin D3', '200iu'),
(55, '#00045955', 'Vitamin K2', '30mcg'),
(56, '#00045955', 'Dibasic Calcium Phosphate', '152mg'),
(57, '#00045955', 'Magie oxide', '20mg'),
(58, '#00045955', 'Kẽm oxide', '1.25mg'),
(59, '#00045955', 'boron citrate', '926mcg'),
(60, '#00045955', 'Đồng Oxit', '156mcg'),
(61, '#00045449', 'Calci glucoheptonat', '550mg'),
(62, '#00045449', 'Vitamin D3', '100iu'),
(63, '#00045449', 'Vitamin K2', '10mcg'),
(64, '#00345389', 'Dầu cá', '1000mg'),
(65, '#00345389', 'EPA', '180mg'),
(66, '#00345390', 'Vitamin D3', '400iu'),
(67, '#00345390', 'Dầu cá', '400mg'),
(68, '#00345390', 'Vitamin E', '5iu'),
(69, '#00045955', 'Calcium Citrate', '476mg'),
(70, '#00045955', 'Calcium Carbonate', '375mg'),
(71, '#00045955', 'Vitamin D3', '200iu'),
(72, '#00045955', 'Vitamin K2', '30mcg'),
(73, '#00045955', 'Dibasic Calcium Phosphate', '152mg'),
(74, '#00045955', 'Magie oxide', '20mg'),
(75, '#00045955', 'Kẽm oxide', '1.25mg'),
(76, '#00045955', 'boron citrate', '926mcg'),
(77, '#00045955', 'Đồng Oxit', '156mcg'),
(78, '#00045449', 'Calci glucoheptonat', '550mg'),
(79, '#00045449', 'Vitamin D3', '100iu'),
(80, '#00045449', 'Vitamin K2', '10mcg'),
(81, '#00345389', 'Dầu cá', '1000mg'),
(82, '#00345389', 'EPA', '180mg'),
(83, '#00345390', 'Vitamin D3', '400iu'),
(84, '#00345390', 'Dầu cá', '400mg'),
(85, '#00345390', 'Vitamin E', '5iu'),
(86, '#00045955', 'Calcium Citrate', '476mg'),
(87, '#00045955', 'Calcium Carbonate', '375mg'),
(88, '#00045955', 'Vitamin D3', '200iu'),
(89, '#00045955', 'Vitamin K2', '30mcg'),
(90, '#00045955', 'Dibasic Calcium Phosphate', '152mg'),
(91, '#00045955', 'Magie oxide', '20mg'),
(92, '#00045955', 'Kẽm oxide', '1.25mg'),
(93, '#00045955', 'boron citrate', '926mcg'),
(94, '#00045955', 'Đồng Oxit', '156mcg'),
(95, '#00045449', 'Calci glucoheptonat', '550mg'),
(96, '#00045449', 'Vitamin D3', '100iu'),
(97, '#00045449', 'Vitamin K2', '10mcg'),
(98, '#00345389', 'Dầu cá', '1000mg'),
(99, '#00345389', 'EPA', '180mg'),
(100, '#00345390', 'Vitamin D3', '400iu'),
(101, '#00345390', 'Dầu cá', '400mg'),
(102, '#00345390', 'Vitamin E', '5iu'),
(103, '#00345466', '400mg', NULL),
(104, '#00345466', 'DẦU CÁ BIỂN', '500mg'),
(105, '#00022576', 'Vitamin D3', '400iu'),
(106, '#00022576', 'Vitamin K2', '22.5mcg');

-- --------------------------------------------------------

--
-- Table structure for table `unitinfo`
--

CREATE TABLE `unitinfo` (
  `idunit` int(11) NOT NULL,
  `unit_name` varchar(255) NOT NULL,
  `idp` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `unitinfo`
--

INSERT INTO `unitinfo` (`idunit`, `unit_name`, `idp`) VALUES
(47, 'Hộp', '#00045955'),
(48, 'Hộp', '#00045449'),
(49, 'Hộp', '#00500791'),
(50, 'Hộp', '#00345388'),
(51, 'Hộp', '#00345389'),
(52, 'Hộp', '#00345390'),
(53, 'Hộp', '#00345466'),
(54, 'Hộp', '#00022576');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `dateBirth` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `url` text DEFAULT NULL,
  `idauth` varchar(255) NOT NULL,
  `token` text DEFAULT NULL,
  `bonuspoint` int(11) NOT NULL,
  `role` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `banner`
--
ALTER TABLE `banner`
  ADD PRIMARY KEY (`idbner`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`idc`);

--
-- Indexes for table `element`
--
ALTER TABLE `element`
  ADD PRIMARY KEY (`ide`),
  ADD KEY `iding` (`iding`);

--
-- Indexes for table `ingredient`
--
ALTER TABLE `ingredient`
  ADD PRIMARY KEY (`iding`),
  ADD KEY `idc` (`idc`);

--
-- Indexes for table `oder`
--
ALTER TABLE `oder`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orderdetails`
--
ALTER TABLE `orderdetails`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`idp`),
  ADD KEY `ide` (`ide`);

--
-- Indexes for table `productimages`
--
ALTER TABLE `productimages`
  ADD PRIMARY KEY (`idu`),
  ADD KEY `idp` (`idp`);

--
-- Indexes for table `productreview`
--
ALTER TABLE `productreview`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idp` (`idp`);

--
-- Indexes for table `thanhphan`
--
ALTER TABLE `thanhphan`
  ADD PRIMARY KEY (`idtp`);

--
-- Indexes for table `unitinfo`
--
ALTER TABLE `unitinfo`
  ADD PRIMARY KEY (`idunit`),
  ADD KEY `idp` (`idp`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `banner`
--
ALTER TABLE `banner`
  MODIFY `idbner` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `oder`
--
ALTER TABLE `oder`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `orderdetails`
--
ALTER TABLE `orderdetails`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `productimages`
--
ALTER TABLE `productimages`
  MODIFY `idu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=392;

--
-- AUTO_INCREMENT for table `productreview`
--
ALTER TABLE `productreview`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `thanhphan`
--
ALTER TABLE `thanhphan`
  MODIFY `idtp` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=107;

--
-- AUTO_INCREMENT for table `unitinfo`
--
ALTER TABLE `unitinfo`
  MODIFY `idunit` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `element`
--
ALTER TABLE `element`
  ADD CONSTRAINT `element_ibfk_1` FOREIGN KEY (`iding`) REFERENCES `ingredient` (`iding`) ON DELETE CASCADE;

--
-- Constraints for table `ingredient`
--
ALTER TABLE `ingredient`
  ADD CONSTRAINT `ingredient_ibfk_1` FOREIGN KEY (`idc`) REFERENCES `category` (`idc`) ON DELETE CASCADE;

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`ide`) REFERENCES `element` (`ide`) ON DELETE CASCADE;

--
-- Constraints for table `productimages`
--
ALTER TABLE `productimages`
  ADD CONSTRAINT `productimages_ibfk_1` FOREIGN KEY (`idp`) REFERENCES `product` (`idp`) ON DELETE CASCADE;

--
-- Constraints for table `unitinfo`
--
ALTER TABLE `unitinfo`
  ADD CONSTRAINT `unitinfo_ibfk_1` FOREIGN KEY (`idp`) REFERENCES `product` (`idp`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
