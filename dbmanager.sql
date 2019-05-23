-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 23, 2019 at 07:01 AM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 7.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbmanager`
--

-- --------------------------------------------------------

--
-- Table structure for table `tblaccount`
--

CREATE TABLE `tblaccount` (
  `IDAccount` int(10) UNSIGNED ZEROFILL NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(50) NOT NULL,
  `usertype` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblaccount`
--

INSERT INTO `tblaccount` (`IDAccount`, `username`, `password`, `usertype`) VALUES
(0000000001, 'jeanny', 'coder', 1),
(0000000002, 'student', 'student', 2);

-- --------------------------------------------------------

--
-- Table structure for table `tbladdpeson`
--

CREATE TABLE `tbladdpeson` (
  `IDPerson` int(10) UNSIGNED ZEROFILL NOT NULL,
  `Name` varchar(32) NOT NULL,
  `Gender` varchar(6) NOT NULL,
  `Email` varchar(32) NOT NULL,
  `Assigned` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbladdpeson`
--

INSERT INTO `tbladdpeson` (`IDPerson`, `Name`, `Gender`, `Email`, `Assigned`) VALUES
(0000000001, 'Cris', 'Male', 'cris@gmail.com', 'False'),
(0000000002, 'Alivio', 'Female', 'alivio@gmail.com', 'False'),
(0000000003, 'Ibyang', 'MAle', 'iby@gmail.com', 'False'),
(0000000004, 'KC', 'Male', 'KC@gmail.com', 'False');

-- --------------------------------------------------------

--
-- Table structure for table `tbldeletedtasks`
--

CREATE TABLE `tbldeletedtasks` (
  `TaskID` int(10) UNSIGNED ZEROFILL NOT NULL,
  `TaskName` varchar(32) NOT NULL,
  `TaskSize` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbltaskmember`
--

CREATE TABLE `tbltaskmember` (
  `PersonID` int(10) UNSIGNED ZEROFILL NOT NULL,
  `TaskID` int(10) UNSIGNED ZEROFILL NOT NULL,
  `Name` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbltasks`
--

CREATE TABLE `tbltasks` (
  `TaskID` int(10) UNSIGNED ZEROFILL NOT NULL,
  `TaskName` varchar(32) NOT NULL,
  `TaskSize` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbltasks`
--

INSERT INTO `tbltasks` (`TaskID`, `TaskName`, `TaskSize`) VALUES
(0000000001, 'Windows', 2),
(0000000002, 'Kitchen', 10);

-- --------------------------------------------------------

--
-- Table structure for table `tblusertype`
--

CREATE TABLE `tblusertype` (
  `IDUsertype` int(10) UNSIGNED ZEROFILL NOT NULL,
  `UserType` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tblaccount`
--
ALTER TABLE `tblaccount`
  ADD PRIMARY KEY (`IDAccount`);

--
-- Indexes for table `tbladdpeson`
--
ALTER TABLE `tbladdpeson`
  ADD PRIMARY KEY (`IDPerson`);

--
-- Indexes for table `tbldeletedtasks`
--
ALTER TABLE `tbldeletedtasks`
  ADD PRIMARY KEY (`TaskID`);

--
-- Indexes for table `tbltasks`
--
ALTER TABLE `tbltasks`
  ADD PRIMARY KEY (`TaskID`);

--
-- Indexes for table `tblusertype`
--
ALTER TABLE `tblusertype`
  ADD PRIMARY KEY (`IDUsertype`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tblaccount`
--
ALTER TABLE `tblaccount`
  MODIFY `IDAccount` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `tbladdpeson`
--
ALTER TABLE `tbladdpeson`
  MODIFY `IDPerson` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `tbldeletedtasks`
--
ALTER TABLE `tbldeletedtasks`
  MODIFY `TaskID` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbltasks`
--
ALTER TABLE `tbltasks`
  MODIFY `TaskID` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `tblusertype`
--
ALTER TABLE `tblusertype`
  MODIFY `IDUsertype` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
