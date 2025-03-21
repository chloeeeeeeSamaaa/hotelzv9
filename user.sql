-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 21, 2025 at 12:49 PM
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
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UserID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
