-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 21, 2025 at 12:48 PM
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
-- Database: `hotel`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `UserID` int(11) NOT NULL,
  `Username` varchar(255) NOT NULL,
  `Firstname` varchar(255) NOT NULL,
  `Lastname` varchar(255) NOT NULL,
  `Address` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Gender` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`UserID`, `Username`, `Firstname`, `Lastname`, `Address`, `Email`, `Gender`, `Password`) VALUES
(1, 'admin123', 'Miku', 'Nakano', 'Anislag', 'rosiecutie@gmail.com', 'Male', 'admin123');

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `paymentID` int(11) NOT NULL,
  `userID` varchar(255) NOT NULL,
  `paymentMethod` varchar(50) DEFAULT NULL,
  `paymentAmount` double NOT NULL,
  `paymentDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`paymentID`, `userID`, `paymentMethod`, `paymentAmount`, `paymentDate`) VALUES
(23, '5', 'CASH', 1000, '2024-12-12 04:55:53'),
(24, '5', 'CASH', 6400, '2024-12-12 08:15:18'),
(25, '5', 'CASH', 800, '2024-12-13 12:07:03'),
(26, '3', 'CASH', 500, '2024-12-13 12:15:56'),
(27, '10', 'CASH', 890, '2024-12-13 12:24:37'),
(28, '15', 'GCASH', 6230, '2024-12-17 02:38:31');

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

CREATE TABLE `reservation` (
  `ReserveID` varchar(10) NOT NULL,
  `userID` varchar(10) NOT NULL,
  `Name` varchar(100) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `MobileNo` varchar(15) DEFAULT NULL,
  `CheckIn` date DEFAULT NULL,
  `CheckOut` date DEFAULT NULL,
  `bedType` varchar(20) DEFAULT NULL,
  `roomNo` varchar(255) DEFAULT NULL,
  `roomType` varchar(20) DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`ReserveID`, `userID`, `Name`, `Address`, `MobileNo`, `CheckIn`, `CheckOut`, `bedType`, `roomNo`, `roomType`, `amount`, `status`) VALUES
('R001', '10', 'MIKUU NAKANOO', 'Panglao', '0987567', '2024-12-20', '2024-12-21', 'Single', 'R0003', 'NON A/C', 800.00, 'Approved'),
('R001', '5', 'Hayian', 'Anislag', '09803384012', '2024-12-20', '2024-12-21', 'Single', 'R0001', 'A/C', 500.00, 'Approved');

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `RoomID` varchar(255) NOT NULL,
  `RoomType` varchar(255) NOT NULL,
  `BedType` varchar(255) NOT NULL,
  `Amount` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`RoomID`, `RoomType`, `BedType`, `Amount`) VALUES
('R0001', 'A/C', 'Single', '500'),
('R0002', 'NON A/C', 'Single', '400'),
('R0003', 'A/C', 'Double', '800'),
('R0004', 'NON A/C', 'Double', '700'),
('R0005', 'A/C', 'Single', '200'),
('R0006', 'A/C', 'Single', '890'),
('R0007', 'A/C', 'Double', '800'),
('R0008', 'A/C', 'Single', '900'),
('R0009', 'A/C', 'Single', '9000');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `UserID` int(11) NOT NULL,
  `Username` varchar(255) NOT NULL,
  `Address` varchar(255) NOT NULL,
  `MobileNo` varchar(255) NOT NULL,
  `Firstname` varchar(255) NOT NULL,
  `Lastname` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Gender` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`UserID`, `Username`, `Address`, `MobileNo`, `Firstname`, `Lastname`, `Email`, `Gender`, `Password`) VALUES
(2, 'ADMIN', 'Sambog', '09311231', 'SDAS', 'SADADA', 'SADAS', '', '123'),
(3, 'YANYANNNNNN', 'Pandol', '09123123', 'gaga', 'gugu', 'dsfada', 'Male', '123'),
(4, 'HAHA', 'Sikatuna', '0967464544', 'HAHAHA', 'AHHAHA', 'AHUAHUA', 'Female', '123'),
(5, 'HAYIANNNN', 'sambog', '09803384012', 'Hayian', 'Requina', 'yaniecutie@gmail.com', 'Male', '12345678'),
(8, 'kabaw', 'Tagbilaran', '09213131', 'sada', 'asda', 'sadad', 'Female', '123'),
(10, 'MITOOOO', 'Panglao', '0987567', 'asda', 'adada', 'kuro@gmail.com', 'Female', '123'),
(12, 'shiki', 'Catagbian', '0946545', 'darkie', 'blak', 'kuro@gmail.com', 'Male', '123'),
(13, 'rias', 'hell', '0979654', 'rias', 'gremory', 'riassama@gmail.com', 'Female', 'riasgwapa123'),
(15, 'hayian', 'Anislag,Corella,Bohol', '09765566', 'hayiann', 'requinaa', 'cuyie@gmail.com', 'Male', '12345678');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`UserID`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`paymentID`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`ReserveID`,`userID`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`RoomID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UserID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `paymentID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
