-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: localhost
-- Thời gian đã tạo: Th10 19, 2024 lúc 06:19 PM
-- Phiên bản máy phục vụ: 10.4.28-MariaDB
-- Phiên bản PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `studentDB`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `class`
--

CREATE TABLE `class` (
  `class_code` varchar(50) NOT NULL,
  `class_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `class`
--

INSERT INTO `class` (`class_code`, `class_name`) VALUES
('AGILE', 'Phương pháp Agile'),
('AI', 'Trí tuệ nhân tạo'),
('API', 'Giao diện lập trình ứng dụng'),
('ARVR', 'Thực tế tăng cường và thực tế ảo'),
('BIGDATA', 'Dữ liệu lớn'),
('BLOCKCHAIN', 'Blockchain'),
('CLOUD', 'Điện toán đám mây'),
('CYPER', 'Cyberspace và An ninh mạng'),
('DATABASE', 'Quản lý cơ sở dữ liệu'),
('DATA_ANALYSIS', 'Phân tích dữ liệu'),
('DB', 'Cơ sở dữ liệu'),
('DEVOPS', 'DevOps'),
('DS', 'Khoa học dữ liệu'),
('E_COMMERCE', 'Thương mại điện tử'),
('FREELANCE', 'Freelancing trong CNTT'),
('GIS', 'Hệ thông thông tin địa lý'),
('IOT', 'Internet of Things'),
('ITIL', 'Quản lý dịch vụ CNTT'),
('JAVA', 'Lập trình Java'),
('ML', 'Học máy'),
('MOBILE', 'Lập trình di động'),
('NETWORK', 'Mạng máy tính'),
('SCRUM', 'Scrum'),
('SE', 'Kỹ thuật phần mềm'),
('SEC', 'An ninh mạng'),
('SYS', 'Hệ thống thông tin'),
('SYSTEM_ADMIN', 'Quản trị hệ thống'),
('TEST', 'Kiểm thử phần mềm'),
('UI', 'Thiết kế giao diện người dùng'),
('UX', 'Thiết kế trải nghiệm người dùng'),
('VIRTUAL', 'San ảo hóa'),
('WEB', 'Lập trình Web');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `courses`
--

CREATE TABLE `courses` (
  `course_id` int(11) NOT NULL,
  `course_name` varchar(255) NOT NULL,
  `credits` int(11) NOT NULL,
  `class_code` varchar(50) NOT NULL,
  `student_count` int(11) NOT NULL,
  `day_of_week` varchar(10) NOT NULL,
  `start_period` varchar(11) NOT NULL,
  `total_periods` int(11) NOT NULL,
  `room` varchar(50) NOT NULL,
  `instructor` varchar(255) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `courses`
--

INSERT INTO `courses` (`course_id`, `course_name`, `credits`, `class_code`, `student_count`, `day_of_week`, `start_period`, `total_periods`, `room`, `instructor`, `start_date`, `end_date`) VALUES
(1, 'Lập trình Java', 3, 'JAVA', 30, 'Thứ 2', '1, 2, 3', 15, 'A101', 'Nguyễn Văn Thành', '2024-10-20', '2024-12-20'),
(2, 'Cơ sở dữ liệu', 3, 'DB', 25, 'Thứ 3', '1, 2, 3, 4', 15, 'B202', 'Trần Thị Bích Hoà', '2024-10-21', '2024-12-21'),
(3, 'Mạng máy tính', 2, 'NETWORK', 20, 'Thứ 5', '4, 5, 6', 15, 'C303', 'Lê Văn Cường', '2024-10-23', '2024-12-23'),
(4, 'Lập trình Java', 3, 'JAVA', 30, 'Thứ 2', '1, 2, 3', 15, 'A101', 'Nguyễn Văn Thành', '2024-10-20', '2024-12-20'),
(5, 'Lập trình Java', 3, 'JAVA', 30, 'Thứ 3', '2, 3, 4', 15, 'A102', 'Nguyễn Văn Thành', '2024-10-21', '2024-12-21'),
(6, 'Lập trình Java', 3, 'JAVA', 30, 'Thứ 4', '3, 4, 5', 15, 'A103', 'Nguyễn Văn Thành', '2024-10-22', '2024-12-22'),
(7, 'Lập trình Java', 3, 'JAVA', 30, 'Thứ 5', '4, 5, 6', 15, 'A104', 'Nguyễn Văn Thành', '2024-10-23', '2024-12-23'),
(8, 'Lập trình Java', 3, 'JAVA', 30, 'Thứ 6', '5, 6, 7', 15, 'A105', 'Nguyễn Văn Thành', '2024-10-24', '2024-12-24'),
(9, 'Lập trình Java', 15, 'JAVA', 30, 'Thứ 7', '6, 7, 8', 15, 'A106', 'Nguyễn Văn Thành', '2024-10-25', '2024-12-25'),
(10, 'Cơ sở dữ liệu', 3, 'DB', 25, 'Thứ 2', '1, 2, 3', 15, 'B201', 'Trần Thị Bích Hoà', '2024-10-20', '2024-12-20'),
(11, 'Cơ sở dữ liệu', 3, 'DB', 25, 'Thứ 3', '2, 3, 4', 15, 'B202', 'Trần Thị Bích Hoà', '2024-10-21', '2024-12-21'),
(12, 'Cơ sở dữ liệu', 3, 'DB', 25, 'Thứ 4', '3, 4, 5', 15, 'B203', 'Trần Thị Bích Hoà', '2024-10-22', '2024-12-22'),
(13, 'Cơ sở dữ liệu', 3, 'DB', 25, 'Thứ 5', '4, 5, 6', 15, 'B204', 'Trần Thị Bích Hoà', '2024-10-23', '2024-12-23'),
(14, 'Cơ sở dữ liệu', 3, 'DB', 25, 'Thứ 6', '5, 6, 7', 15, 'B205', 'Trần Thị Bích Hoà', '2024-10-24', '2024-12-24'),
(15, 'Cơ sở dữ liệu', 15, 'DB', 25, 'Thứ 7', '6, 7, 8', 15, 'B206', 'Trần Thị Bích Hoà', '2024-10-25', '2024-12-25'),
(16, 'Mạng máy tính', 2, 'NETWORK', 20, 'Thứ 2', '1, 2, 3', 15, 'C301', 'Lê Văn Cường', '2024-10-20', '2024-12-20'),
(17, 'Mạng máy tính', 2, 'NETWORK', 20, 'Thứ 3', '2, 3, 4', 15, 'C302', 'Lê Văn Cường', '2024-10-21', '2024-12-21'),
(18, 'Mạng máy tính', 2, 'NETWORK', 20, 'Thứ 4', '3, 4, 5', 15, 'C303', 'Lê Văn Cường', '2024-10-22', '2024-12-22'),
(19, 'Mạng máy tính', 2, 'NETWORK', 20, 'Thứ 5', '4, 5, 6', 15, 'C304', 'Lê Văn Cường', '2024-10-23', '2024-12-23'),
(20, 'Mạng máy tính', 2, 'NETWORK', 20, 'Thứ 6', '5, 6, 7', 15, 'C305', 'Lê Văn Cường', '2024-10-24', '2024-12-24'),
(21, 'Mạng máy tính', 2, 'NETWORK', 20, 'Thứ 7', '6, 7, 8', 15, 'C306', 'Lê Văn Cường', '2024-10-25', '2024-12-25'),
(22, 'Lập trình Web', 3, 'WEB', 30, 'Thứ 2', '1, 2, 3', 15, 'D401', 'Nguyễn Trọng Tiến Dũng', '2024-10-20', '2024-12-20'),
(23, 'Lập trình Web', 3, 'WEB', 30, 'Thứ 3', '2, 3, 4', 15, 'D402', 'Nguyễn Trọng Tiến Dũng', '2024-10-21', '2024-12-21'),
(24, 'Lập trình Web', 3, 'WEB', 30, 'Thứ 4', '3, 4, 5', 15, 'D403', 'Nguyễn Trọng Tiến Dũng', '2024-10-22', '2024-12-22'),
(25, 'Lập trình Web', 3, 'WEB', 30, 'Thứ 5', '4, 5, 6', 15, 'D404', 'Nguyễn Trọng Tiến Dũng', '2024-10-23', '2024-12-23'),
(26, 'Lập trình Web', 3, 'WEB', 30, 'Thứ 6', '5, 6, 7', 15, 'D405', 'Nguyễn Trọng Tiến Dũng', '2024-10-24', '2024-12-24'),
(27, 'Lập trình Web', 3, 'WEB', 30, 'Thứ 7', '6, 7, 8', 15, 'D406', 'Nguyễn Trọng Tiến Dũng', '2024-10-25', '2024-12-25'),
(28, 'Trí tuệ nhân tạo', 3, 'AI', 26, 'Thứ 2', '1, 2, 3', 15, 'E501', 'Trần Thành Nam', '2024-10-20', '2024-12-20'),
(29, 'Trí tuệ nhân tạo', 3, 'AI', 26, 'Thứ 3', '2, 3, 4', 15, 'E502', 'Trần Thành Nam', '2024-10-21', '2024-12-21'),
(30, 'Trí tuệ nhân tạo', 3, 'AI', 26, 'Thứ 4', '3, 4, 5', 15, 'E503', 'Trần Thành Nam', '2024-10-22', '2024-12-22'),
(31, 'Trí tuệ nhân tạo', 3, 'AI', 26, 'Thứ 5', '4, 5, 6', 15, 'E504', 'Trần Thành Nam', '2024-10-23', '2024-12-23'),
(32, 'Trí tuệ nhân tạo', 3, 'AI', 26, 'Thứ 6', '5, 6, 7', 15, 'E505', 'Trần Thành Nam', '2024-10-24', '2024-12-24'),
(33, 'Giao diện lập trình ứng dụng', 3, 'API', 20, 'Thứ 2', '1, 2, 3', 15, 'F601', 'Đặng Khôi Nguyên', '2024-10-20', '2024-12-20'),
(34, 'Giao diện lập trình ứng dụng', 3, 'API', 20, 'Thứ 3', '2, 3, 4', 15, 'F602', 'Đặng Khôi Nguyên', '2024-10-21', '2024-12-21'),
(35, 'Giao diện lập trình ứng dụng', 3, 'API', 20, 'Thứ 4', '3, 4, 5', 15, 'F603', 'Đặng Khôi Nguyên', '2024-10-22', '2024-12-22'),
(36, 'Thực tế tăng cường và thực tế ảo', 3, 'ARVR', 22, 'Thứ 2', '1, 2, 3', 15, 'G701', 'Nguyễn Tiến Dũng', '2024-10-20', '2024-12-20'),
(37, 'Thực tế tăng cường và thực tế ảo', 3, 'ARVR', 22, 'Thứ 3', '2, 3, 4', 15, 'G702', 'Nguyễn Tiến Dũng', '2024-10-21', '2024-12-21'),
(38, 'Thực tế tăng cường và thực tế ảo', 3, 'ARVR', 22, 'Thứ 4', '3, 4, 5', 15, 'G703', 'Nguyễn Tiến Dũng', '2024-10-22', '2024-12-22'),
(39, 'Dữ liệu lớn', 3, 'BIGDATA', 25, 'Thứ 2', '1, 2, 3', 15, 'H801', 'Trần Hồng Trang', '2024-10-20', '2024-12-20'),
(40, 'Dữ liệu lớn', 3, 'BIGDATA', 25, 'Thứ 3', '2, 3, 4', 15, 'H802', 'Trần Hồng Trang', '2024-10-21', '2024-12-21'),
(41, 'Dữ liệu lớn', 3, 'BIGDATA', 25, 'Thứ 4', '3, 4, 5', 15, 'H803', 'Trần Hồng Trang', '2024-10-22', '2024-12-22'),
(42, 'Blockchain', 3, 'BLOCKCHAIN', 20, 'Thứ 2', '1, 2, 3', 15, 'I901', 'Lê Quý Thọ', '2024-10-20', '2024-12-20'),
(43, 'Blockchain', 3, 'BLOCKCHAIN', 20, 'Thứ 3', '2, 3, 4', 15, 'I902', 'Lê Quý Thọ', '2024-10-21', '2024-12-21'),
(44, 'Blockchain', 3, 'BLOCKCHAIN', 20, 'Thứ 4', '3, 4, 5', 15, 'I903', 'Lê Quý Thọ', '2024-10-22', '2024-12-22'),
(45, 'Điện toán đám mây', 3, 'CLOUD', 22, 'Thứ 2', '1, 2, 3', 15, 'J1001', 'Trọng Bình An', '2024-10-20', '2024-12-20'),
(46, 'Điện toán đám mây', 3, 'CLOUD', 22, 'Thứ 3', '2, 3, 4', 15, 'J1002', 'Trọng Bình An', '2024-10-21', '2024-12-21'),
(47, 'Điện toán đám mây', 3, 'CLOUD', 22, 'Thứ 4', '3, 4, 5', 15, 'J1003', 'Trọng Bình An', '2024-10-22', '2024-12-22'),
(48, 'Cyberspace và An ninh mạng', 3, 'CYPER', 24, 'Thứ 2', '1, 2, 3', 15, 'K1101', 'Khánh Toàn', '2024-10-20', '2024-12-20'),
(49, 'Cyberspace và An ninh mạng', 3, 'CYPER', 24, 'Thứ 3', '2, 3, 4', 15, 'K1102', 'Khánh Toàn', '2024-10-21', '2024-12-21'),
(50, 'Cyberspace và An ninh mạng', 3, 'CYPER', 24, 'Thứ 4', '3, 4, 5', 15, 'K1103', 'Khánh Toàn', '2024-10-22', '2024-12-22'),
(51, 'Quản lý cơ sở dữ liệu', 3, 'DATABASE', 30, 'Thứ 2', '1, 2, 3', 15, 'L1201', 'Mr. John', '2024-10-20', '2024-12-20'),
(52, 'Quản lý cơ sở dữ liệu', 3, 'DATABASE', 30, 'Thứ 3', '2, 3, 4', 15, 'L1202', 'Mr. John', '2024-10-21', '2024-12-21'),
(53, 'Quản lý cơ sở dữ liệu', 3, 'DATABASE', 30, 'Thứ 4', '3, 4, 5', 15, 'L1203', 'Mr. John', '2024-10-22', '2024-12-22'),
(54, 'Phân tích dữ liệu', 3, 'DATA_ANALYSIS', 22, 'Thứ 2', '1, 2, 3', 15, 'M1301', 'Lục Bình', '2024-10-20', '2024-12-20'),
(55, 'Phân tích dữ liệu', 3, 'DATA_ANALYSIS', 22, 'Thứ 3', '2, 3, 4', 15, 'M1302', 'Lục Bình', '2024-10-21', '2024-12-21'),
(56, 'Phân tích dữ liệu', 3, 'DATA_ANALYSIS', 22, 'Thứ 4', '3, 4, 5', 15, 'M1303', 'Lục Bình', '2024-10-22', '2024-12-22'),
(57, 'Cơ sở dữ liệu', 3, 'DB', 25, 'Thứ 2', '1, 2, 3', 15, 'N1401', 'Trịnh Hồng Thắm', '2024-10-20', '2024-12-20'),
(58, 'Cơ sở dữ liệu', 3, 'DB', 25, 'Thứ 3', '2, 3, 4', 15, 'N1402', 'Trịnh Hồng Thắm', '2024-10-21', '2024-12-21'),
(59, 'Cơ sở dữ liệu', 3, 'DB', 25, 'Thứ 4', '3, 4, 5', 15, 'N1403', 'Trịnh Hồng Thắm', '2024-10-22', '2024-12-22'),
(60, 'DevOps', 3, 'DEVOPS', 20, 'Thứ 2', '1, 2, 3', 15, 'O1501', 'Thắm Đượm', '2024-10-20', '2024-12-20'),
(61, 'DevOps', 3, 'DEVOPS', 20, 'Thứ 3', '2, 3, 4', 15, 'O1502', 'Thắm Đượm', '2024-10-21', '2024-12-21'),
(62, 'DevOps', 3, 'DEVOPS', 20, 'Thứ 4', '3, 4, 5', 15, 'O1503', 'Thắm Đượm', '2024-10-22', '2024-12-22'),
(63, 'Khoa học dữ liệu', 3, 'DS', 25, 'Thứ 2', '1, 2, 3', 15, 'P1601', 'Nguyễn Văn Phú', '2024-10-20', '2024-12-20'),
(64, 'Khoa học dữ liệu', 3, 'DS', 25, 'Thứ 3', '2, 3, 4', 15, 'P1602', 'Nguyễn Văn Phú', '2024-10-21', '2024-12-21'),
(65, 'Khoa học dữ liệu', 3, 'DS', 25, 'Thứ 4', '3, 4, 5', 15, 'P1603', 'Nguyễn Văn Phú', '2024-10-22', '2024-12-22'),
(66, 'Thương mại điện tử', 3, 'E_COMMERCE', 30, 'Thứ 2', '1, 2, 3', 15, 'Q1701', 'Nguyễn Văn Quân', '2024-10-20', '2024-12-20'),
(67, 'Thương mại điện tử', 3, 'E_COMMERCE', 30, 'Thứ 3', '2, 3, 4', 15, 'Q1702', 'Nguyễn Văn Quân', '2024-10-21', '2024-12-21'),
(68, 'Thương mại điện tử', 3, 'E_COMMERCE', 30, 'Thứ 4', '3, 4, 5', 15, 'Q1703', 'Nguyễn Văn Quân', '2024-10-22', '2024-12-22'),
(69, 'Freelancing trong CNTT', 3, 'FREELANCE', 20, 'Thứ 2', '1, 2, 3', 15, 'R1801', 'Nguyễn Văn Rạng', '2024-10-20', '2024-12-20'),
(70, 'Freelancing trong CNTT', 3, 'FREELANCE', 20, 'Thứ 3', '2, 3, 4', 15, 'R1802', 'Nguyễn Văn Rạng', '2024-10-21', '2024-12-21'),
(71, 'Freelancing trong CNTT', 3, 'FREELANCE', 20, 'Thứ 4', '3, 4, 5', 15, 'R1803', 'Nguyễn Văn Rạng', '2024-10-22', '2024-12-22'),
(72, 'Hệ thống thông tin địa lý', 3, 'GIS', 20, 'Thứ 2', '1, 2, 3', 15, 'S1901', 'Nguyễn Văn Sơn', '2024-10-20', '2024-12-20'),
(73, 'Hệ thống thông tin địa lý', 3, 'GIS', 20, 'Thứ 3', '2, 3, 4', 15, 'S1902', 'Nguyễn Văn Sơn', '2024-10-21', '2024-12-21'),
(74, 'Hệ thống thông tin địa lý', 3, 'GIS', 20, 'Thứ 4', '3, 4, 5', 15, 'S1903', 'Nguyễn Văn Sơn', '2024-10-22', '2024-12-22'),
(75, 'Internet of Things', 3, 'IOT', 22, 'Thứ 2', '1, 2, 3', 15, 'T2001', 'Nguyễn Văn Tâm', '2024-10-20', '2024-12-20'),
(76, 'Internet of Things', 3, 'IOT', 22, 'Thứ 3', '2, 3, 4', 15, 'T2002', 'Nguyễn Văn Tâm', '2024-10-21', '2024-12-21'),
(77, 'Internet of Things', 3, 'IOT', 22, 'Thứ 4', '3, 4, 5', 15, 'T2003', 'Nguyễn Văn Tâm', '2024-10-22', '2024-12-22'),
(78, 'Quản lý dịch vụ CNTT', 3, 'ITIL', 22, 'Thứ 2', '1, 2, 3', 15, 'U2101', 'Nguyễn Văn Uý', '2024-10-20', '2024-12-20'),
(79, 'Quản lý dịch vụ CNTT', 3, 'ITIL', 22, 'Thứ 3', '2, 3, 4', 15, 'U2102', 'Nguyễn Văn Uý', '2024-10-21', '2024-12-21'),
(80, 'Quản lý dịch vụ CNTT', 3, 'ITIL', 22, 'Thứ 4', '3, 4, 5', 15, 'U2103', 'Nguyễn Văn Uý', '2024-10-22', '2024-12-22'),
(81, 'Lập trình Java', 3, 'JAVA', 20, 'Thứ 2', '1, 2, 3', 15, 'V2201', 'Trần Văn Vinh', '2024-10-20', '2024-12-20'),
(82, 'Lập trình Java', 3, 'JAVA', 20, 'Thứ 3', '2, 3, 4', 15, 'V2202', 'Trần Văn Vinh', '2024-10-21', '2024-12-21'),
(83, 'Lập trình Java', 3, 'JAVA', 20, 'Thứ 4', '3, 4, 5', 15, 'V2203', 'Trần Văn Vinh', '2024-10-22', '2024-12-22'),
(84, 'Học máy', 3, 'ML', 25, 'Thứ 2', '1, 2, 3', 15, 'W2301', 'Nguyễn Văn Vinh', '2024-10-20', '2024-12-20'),
(85, 'Học máy', 3, 'ML', 25, 'Thứ 3', '2, 3, 4', 15, 'W2302', 'Nguyễn Văn Vinh', '2024-10-21', '2024-12-21'),
(86, 'Học máy', 3, 'ML', 25, 'Thứ 4', '3, 4, 5', 15, 'W2303', 'Nguyễn Văn Vinh', '2024-10-22', '2024-12-22'),
(87, 'Lập trình di động', 3, 'MOBILE', 20, 'Thứ 2', '1, 2, 3', 15, 'X2401', 'Trần Văn X', '2024-10-20', '2024-12-20'),
(88, 'Lập trình di động', 3, 'MOBILE', 20, 'Thứ 3', '2, 3, 4', 15, 'X2402', 'Trần Văn X', '2024-10-21', '2024-12-21'),
(89, 'Lập trình di động', 3, 'MOBILE', 20, 'Thứ 4', '3, 4, 5', 15, 'X2403', 'Trần Văn X', '2024-10-22', '2024-12-22'),
(90, 'Mạng máy tính', 3, 'NETWORK', 30, 'Thứ 2', '1, 2, 3', 15, 'Y2501', 'Nguyễn Văn Yến', '2024-10-20', '2024-12-20'),
(91, 'Mạng máy tính', 3, 'NETWORK', 30, 'Thứ 3', '2, 3, 4', 15, 'Y2502', 'Nguyễn Văn Yến', '2024-10-21', '2024-12-21'),
(92, 'Mạng máy tính', 3, 'NETWORK', 30, 'Thứ 4', '3, 4, 5', 15, 'Y2503', 'Nguyễn Văn Yến', '2024-10-22', '2024-12-22'),
(93, 'Scrum', 3, 'SCRUM', 22, 'Thứ 2', '1, 2, 3', 15, 'Z2601', 'Trần Văn Z', '2024-10-20', '2024-12-20'),
(94, 'Scrum', 3, 'SCRUM', 22, 'Thứ 3', '2, 3, 4', 15, 'Z2602', 'Trần Văn Z', '2024-10-21', '2024-12-21'),
(95, 'Scrum', 3, 'SCRUM', 22, 'Thứ 4', '3, 4, 5', 15, 'Z2603', 'Trần Văn Z', '2024-10-22', '2024-12-22'),
(96, 'Kỹ thuật phần mềm', 3, 'SE', 25, 'Thứ 2', '1, 2, 3', 15, 'AA2701', 'Nguyễn Văn A Anh', '2024-10-20', '2024-12-20'),
(97, 'Kỹ thuật phần mềm', 3, 'SE', 25, 'Thứ 3', '2, 3, 4', 15, 'AA2702', 'Nguyễn Văn A Anh', '2024-10-21', '2024-12-21'),
(98, 'Kỹ thuật phần mềm', 3, 'SE', 25, 'Thứ 4', '3, 4, 5', 15, 'AA2703', 'Nguyễn Văn A Anh', '2024-10-22', '2024-12-22'),
(99, 'An ninh mạng', 3, 'SEC', 20, 'Thứ 2', '1, 2, 3', 15, 'BB2801', 'Nguyễn Văn Bảo', '2024-10-20', '2024-12-20'),
(100, 'An ninh mạng', 3, 'SEC', 20, 'Thứ 3', '2, 3, 4', 15, 'BB2802', 'Nguyễn Văn Bảo', '2024-10-21', '2024-12-21'),
(101, 'An ninh mạng', 3, 'SEC', 20, 'Thứ 4', '3, 4, 5', 15, 'BB2803', 'Nguyễn Văn Bảo', '2024-10-22', '2024-12-22');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `registrations`
--

CREATE TABLE `registrations` (
  `registration_id` int(11) NOT NULL,
  `student_id` varchar(20) DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  `registration_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `students`
--

CREATE TABLE `students` (
  `student_id` varchar(20) NOT NULL,
  `student_name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `birth_date` date DEFAULT NULL,
  `class_name` varchar(50) DEFAULT NULL,
  `major` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `students`
--

INSERT INTO `students` (`student_id`, `student_name`, `password`, `birth_date`, `class_name`, `major`) VALUES
('SV002', 'Le Thi B', 'password', '2001-02-15', 'CNTT2', ''),
('SV003', 'Tran Van C', 'abcd1234', '2000-03-22', 'CNTT3', ''),
('SV004', 'Pham Thi D', 'admin123', '1999-12-20', 'CNTT4', ''),
('SV1', 'Nguyen Van A', '1', '2000-01-01', 'CNTT1', '');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `class`
--
ALTER TABLE `class`
  ADD PRIMARY KEY (`class_code`);

--
-- Chỉ mục cho bảng `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`course_id`),
  ADD KEY `class_code` (`class_code`);

--
-- Chỉ mục cho bảng `registrations`
--
ALTER TABLE `registrations`
  ADD PRIMARY KEY (`registration_id`),
  ADD KEY `student_id` (`student_id`),
  ADD KEY `course_id` (`course_id`);

--
-- Chỉ mục cho bảng `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`student_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `courses`
--
ALTER TABLE `courses`
  MODIFY `course_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;

--
-- AUTO_INCREMENT cho bảng `registrations`
--
ALTER TABLE `registrations`
  MODIFY `registration_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `courses`
--
ALTER TABLE `courses`
  ADD CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`class_code`) REFERENCES `class` (`class_code`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `registrations`
--
ALTER TABLE `registrations`
  ADD CONSTRAINT `registrations_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE SET NULL,
  ADD CONSTRAINT `registrations_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
