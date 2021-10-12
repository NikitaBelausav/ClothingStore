-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: db:3306
-- Generation Time: Apr 23, 2021 at 04:14 AM
-- Server version: 8.0.23
-- PHP Version: 7.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `buyme`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `login` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`login`) VALUES
('admin');

-- --------------------------------------------------------

--
-- Table structure for table `auction_transactions`
--

CREATE TABLE `auction_transactions` (
  `auction_ID` int NOT NULL,
  `item_ID` int DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `subcategory` varchar(30) DEFAULT NULL,
  `color` varchar(30) DEFAULT NULL,
  `brand` varchar(30) DEFAULT NULL,
  `login` varchar(30) DEFAULT NULL,
  `close_date` date DEFAULT NULL,
  `close_time` time DEFAULT NULL,
  `winner` varchar(30) DEFAULT NULL,
  `init_price` float DEFAULT NULL,
  `bid_increment` float DEFAULT NULL,
  `minimum` float DEFAULT NULL,
  `final_price` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `auction_transactions`
--

INSERT INTO `auction_transactions` (`auction_ID`, `item_ID`, `name`, `subcategory`, `color`, `brand`, `login`, `close_date`, `close_time`, `winner`, `init_price`, `bid_increment`, `minimum`, `final_price`) VALUES
(1, 1, 'Gucci Shoes', 'shoes', 'black', 'gucci', 'dorianht', '2021-05-25', '23:00:00', NULL, 20, 2, 3, NULL),
(2, 1, 'Gucci Shoes', 'shoes', 'black', 'gucci', 'endoman123', '2021-05-22', '23:00:00', 'muskanb12', 20, 1, 2, 40),
(3, 1, 'Gucci Shoes', 'shoes', 'black', 'gucci', 'muskanb12', '2021-05-13', '23:00:00', 'windhollow', 10, 1, 2, 30),
(4, 1, 'Gucci Shoes', 'shoes', 'black', 'gucci', 'windhollow', '2021-05-09', '23:00:00', 'dorianht', 30, 2, 5, 50),
(5, 2, 'Uniqlo Pants', 'pants', 'blue', 'uniqlo', 'dorianht', '2021-05-21', '23:00:00', NULL, 20, 2, 3, NULL),
(6, 2, 'Uniqlo Pants', 'pants', 'blue', 'uniqlo', 'endoman123', '2021-05-17', '23:00:00', 'muskanb12', 20, 1, 2, 40),
(7, 2, 'Uniqlo Pants', 'pants', 'blue', 'uniqlo', 'muskanb12', '2021-03-08', '23:00:00', 'windhollow', 10, 1, 2, 30),
(8, 2, 'Uniqlo Pants', 'pants', 'blue', 'uniqlo', 'windhollow', '2021-04-09', '23:00:00', 'dorianht', 30, 2, 5, 50),
(9, 3, 'Polo Shirt', 'shirts', 'green', 'polo', 'dorianht', '2021-04-25', '23:00:00', NULL, 20, 2, 3, NULL),
(10, 3, 'Polo Shirt', 'shirts', 'green', 'polo', 'endoman123', '2021-05-27', '23:00:00', 'muskanb12', 20, 1, 2, 40),
(11, 3, 'Polo Shirt', 'shirts', 'green', 'polo', 'muskanb12', '2021-07-08', '23:00:00', 'windhollow', 10, 1, 2, 30),
(12, 3, 'Polo Shirt', 'shirts', 'green', 'polo', 'windhollow', '2021-09-09', '23:00:00', 'dorianht', 30, 2, 5, 50);

-- --------------------------------------------------------

--
-- Table structure for table `autobid`
--

CREATE TABLE `autobid` (
  `login` varchar(30) NOT NULL,
  `auction_ID` int NOT NULL,
  `bid_increment` float DEFAULT NULL,
  `upper_limit` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `belongs_to`
--

CREATE TABLE `belongs_to` (
  `category_number` int NOT NULL,
  `item_ID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bid_posts_for`
--

CREATE TABLE `bid_posts_for` (
  `bid_number` int NOT NULL,
  `login` varchar(30) DEFAULT NULL,
  `auction_ID` int DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `bid_date` date DEFAULT NULL,
  `bid_time` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `bid_posts_for`
--

INSERT INTO `bid_posts_for` (`bid_number`, `login`, `auction_ID`, `amount`, `bid_date`, `bid_time`) VALUES
(1, 'endoman123', 4, 50, '2021-04-20', '11:48:28'),
(2, 'muskanb12', 3, 10, '2021-04-20', '11:47:00'),
(3, 'endoman123', 2, 25, '2021-04-21', '11:48:28'),
(4, 'muskanb12', 1, 28, '2021-04-20', '11:47:00'),
(5, 'muskanb12', 6, 29, '2021-04-20', '11:47:00'),
(6, 'muskanb12', 5, 70, '2021-04-20', '11:47:00'),
(7, 'muskanb12', 12, 90, '2021-04-20', '11:47:00'),
(8, 'muskanb12', 11, 850, '2021-04-20', '11:47:00'),
(9, 'muskanb12', 10, 120, '2021-04-20', '11:47:00'),
(10, 'endoman123', 4, 520, '2021-04-20', '11:48:28'),
(11, 'muskanb12', 3, 720, '2021-04-20', '11:47:00'),
(12, 'endoman123', 2, 990, '2021-04-21', '11:48:28'),
(13, 'muskanb12', 1, 920, '2021-04-20', '11:47:00'),
(14, 'muskanb12', 6, 920, '2021-04-20', '11:47:00'),
(15, 'muskanb12', 5, 920, '2021-04-20', '11:47:00'),
(16, 'muskanb12', 12, 920, '2021-04-20', '11:47:00'),
(17, 'muskanb12', 11, 920, '2021-04-20', '11:47:00'),
(18, 'muskanb12', 10, 150, '2021-04-20', '11:47:00');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `category_number` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `customer_rep`
--

CREATE TABLE `customer_rep` (
  `login` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `customer_rep`
--

INSERT INTO `customer_rep` (`login`) VALUES
('rep');

-- --------------------------------------------------------

--
-- Table structure for table `end_user`
--

CREATE TABLE `end_user` (
  `login` varchar(30) NOT NULL,
  `bid_alert` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `end_user`
--

INSERT INTO `end_user` (`login`, `bid_alert`) VALUES
('dorianht', 1),
('endoman123', 1),
('muskanb12', 1),
('windhollow', 1);

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `item_ID` int NOT NULL,
  `name` varchar(20) NOT NULL,
  `subcategory` varchar(30) NOT NULL,
  `color` varchar(30) NOT NULL,
  `brand` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`item_ID`, `name`, `subcategory`, `color`, `brand`) VALUES
(1, 'Gucci Shoes', 'shoes', 'black', 'gucci'),
(2, 'Uniqlo Pants', 'pants', 'blue', 'uniqlo'),
(3, 'Polo Shirt', 'shirt', 'green', 'polo');

-- --------------------------------------------------------

--
-- Table structure for table `item_alerts`
--

CREATE TABLE `item_alerts` (
  `login` varchar(30) NOT NULL,
  `name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `item_alerts`
--

INSERT INTO `item_alerts` (`login`, `name`) VALUES
('dorianht', 'gucci shoes'),
('endoman123', 'gucci');

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `id` int NOT NULL,
  `eu_login` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `cr_login` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `question_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `answer_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`id`, `eu_login`, `cr_login`, `question_text`, `answer_text`) VALUES
(1, 'endoman123', NULL, 'How do I create an auction?', NULL),
(2, 'dorianht', NULL, 'When do I know an auction has ended?', NULL),
(3, 'muskanb12', NULL, 'How do I reset my password?', NULL),
(4, 'windhollow', NULL, 'Can I remove an auction?', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `sub_category_1`
--

CREATE TABLE `sub_category_1` (
  `category_number` int NOT NULL,
  `spec_1` varchar(20) DEFAULT NULL,
  `spec_2` varchar(20) DEFAULT NULL,
  `spec_3` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sub_category_2`
--

CREATE TABLE `sub_category_2` (
  `category_number` int NOT NULL,
  `spec_1` varchar(20) DEFAULT NULL,
  `spec_2` varchar(20) DEFAULT NULL,
  `spec_3` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sub_category_3`
--

CREATE TABLE `sub_category_3` (
  `category_number` int NOT NULL,
  `spec_1` varchar(20) DEFAULT NULL,
  `spec_2` varchar(20) DEFAULT NULL,
  `spec_3` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `login` varchar(30) NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `hash` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `salt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`login`, `email`, `hash`, `salt`) VALUES
('admin', 'admin@buyme.com', '7Dv6iB4qDQARRIstzfi7OA==', '178efc4a880'),
('dorianht', 'dorian.hobot@rutgers.edu', 'Wf4ZSsopreO4BZmi52AZbQ==', '178efc572bb'),
('endoman123', 'jared.tulayan@rutgers.edu', 'SUYvXobrEuW+73Sqg9REhg==', '178efc5b932'),
('muskanb12', 'muskan.burman@rutgers.edu', 'b1qfynQU1QI1JeXMLEQQ0Q==', '178efc61152'),
('rep', 'rep@buyme.com', 'eTRbwE7XQtJ+k1TlWb0QMA==', '178efc76f60'),
('windhollow', 'mikita.belausau@rutgers.edu', 'fjM7wRaniOkLzSGSXIWmPQ==', '178efc68434');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`login`);

--
-- Indexes for table `auction_transactions`
--
ALTER TABLE `auction_transactions`
  ADD PRIMARY KEY (`auction_ID`),
  ADD KEY `item_ID` (`item_ID`),
  ADD KEY `login` (`login`);

--
-- Indexes for table `autobid`
--
ALTER TABLE `autobid`
  ADD PRIMARY KEY (`login`,`auction_ID`),
  ADD KEY `auction_ID` (`auction_ID`);

--
-- Indexes for table `belongs_to`
--
ALTER TABLE `belongs_to`
  ADD PRIMARY KEY (`category_number`,`item_ID`),
  ADD KEY `item_ID` (`item_ID`);

--
-- Indexes for table `bid_posts_for`
--
ALTER TABLE `bid_posts_for`
  ADD PRIMARY KEY (`bid_number`),
  ADD KEY `auction_ID` (`auction_ID`),
  ADD KEY `login` (`login`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_number`);

--
-- Indexes for table `customer_rep`
--
ALTER TABLE `customer_rep`
  ADD PRIMARY KEY (`login`);

--
-- Indexes for table `end_user`
--
ALTER TABLE `end_user`
  ADD PRIMARY KEY (`login`);

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`item_ID`);

--
-- Indexes for table `item_alerts`
--
ALTER TABLE `item_alerts`
  ADD PRIMARY KEY (`login`,`name`);
--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `eu_login` (`eu_login`) USING BTREE,
  ADD KEY `cr_login` (`cr_login`) USING BTREE;

--
-- Indexes for table `sub_category_1`
--
ALTER TABLE `sub_category_1`
  ADD PRIMARY KEY (`category_number`);

--
-- Indexes for table `sub_category_2`
--
ALTER TABLE `sub_category_2`
  ADD PRIMARY KEY (`category_number`);

--
-- Indexes for table `sub_category_3`
--
ALTER TABLE `sub_category_3`
  ADD PRIMARY KEY (`category_number`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`login`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `auction_transactions`
--
ALTER TABLE `auction_transactions`
  MODIFY `auction_ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `bid_posts_for`
--
ALTER TABLE `bid_posts_for`
  MODIFY `bid_number` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `category_number` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `item_ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`login`) REFERENCES `user` (`login`);

--
-- Constraints for table `auction_transactions`
--
ALTER TABLE `auction_transactions`
  ADD CONSTRAINT `auction_transactions_ibfk_1` FOREIGN KEY (`item_ID`) REFERENCES `item` (`item_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `auction_transactions_ibfk_2` FOREIGN KEY (`login`) REFERENCES `user` (`login`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `autobid`
--
ALTER TABLE `autobid`
  ADD CONSTRAINT `autobid_ibfk_1` FOREIGN KEY (`auction_ID`) REFERENCES `auction_transactions` (`auction_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `autobid_ibfk_2` FOREIGN KEY (`login`) REFERENCES `user` (`login`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `belongs_to`
--
ALTER TABLE `belongs_to`
  ADD CONSTRAINT `belongs_to_ibfk_1` FOREIGN KEY (`category_number`) REFERENCES `category` (`category_number`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `belongs_to_ibfk_2` FOREIGN KEY (`item_ID`) REFERENCES `item` (`item_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `bid_posts_for`
--
ALTER TABLE `bid_posts_for`
  ADD CONSTRAINT `bid_posts_for_ibfk_1` FOREIGN KEY (`auction_ID`) REFERENCES `auction_transactions` (`auction_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `bid_posts_for_ibfk_2` FOREIGN KEY (`login`) REFERENCES `user` (`login`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `customer_rep`
--
ALTER TABLE `customer_rep`
  ADD CONSTRAINT `customer_rep_ibfk_1` FOREIGN KEY (`login`) REFERENCES `user` (`login`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `end_user`
--
ALTER TABLE `end_user`
  ADD CONSTRAINT `end_user_ibfk_1` FOREIGN KEY (`login`) REFERENCES `user` (`login`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `item_alerts`
--
ALTER TABLE `item_alerts`
  ADD CONSTRAINT `item_alerts_ibfk_1` FOREIGN KEY (`login`) REFERENCES `user` (`login`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`eu_login`) REFERENCES `end_user` (`login`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `questions_ibfk_2` FOREIGN KEY (`cr_login`) REFERENCES `customer_rep` (`login`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `sub_category_1`
--
ALTER TABLE `sub_category_1`
  ADD CONSTRAINT `sub_category_1_ibfk_1` FOREIGN KEY (`category_number`) REFERENCES `category` (`category_number`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `sub_category_2`
--
ALTER TABLE `sub_category_2`
  ADD CONSTRAINT `sub_category_2_ibfk_1` FOREIGN KEY (`category_number`) REFERENCES `category` (`category_number`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `sub_category_3`
--
ALTER TABLE `sub_category_3`
  ADD CONSTRAINT `sub_category_3_ibfk_1` FOREIGN KEY (`category_number`) REFERENCES `category` (`category_number`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
