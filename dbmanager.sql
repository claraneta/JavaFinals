-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 24, 2019 at 04:02 PM
-- Server version: 10.1.26-MariaDB
-- PHP Version: 7.1.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
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
  `PersonID` int(10) UNSIGNED ZEROFILL NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(50) NOT NULL,
  `usertype` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblaccount`
--

INSERT INTO `tblaccount` (`IDAccount`, `PersonID`, `username`, `password`, `usertype`) VALUES
(0000000001, 0000000000, 'jeanny', 'coder', 1),
(0000000003, 0000000001, 'sirc', 'qwert', 2),
(0000000008, 0000000007, 'userqwert', '1234', 2),
(0000000009, 0000000008, 'userMayie', '1234', 2),
(0000000010, 0000000004, 'userKC', '1234', 2);

--
-- Triggers `tblaccount`
--
DELIMITER $$
CREATE TRIGGER `accountstat` AFTER INSERT ON `tblaccount` FOR EACH ROW UPDATE tbladdpeson set tbladdpeson.AccountStatus = 1 where tbladdpeson.IDPerson = NEW.PersonID
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tbladdpeson`
--

CREATE TABLE `tbladdpeson` (
  `IDPerson` int(10) UNSIGNED ZEROFILL NOT NULL,
  `Name` varchar(32) NOT NULL,
  `Gender` varchar(6) NOT NULL,
  `Email` varchar(32) NOT NULL,
  `Assigned` tinyint(1) NOT NULL DEFAULT '0',
  `AccountStatus` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbladdpeson`
--

INSERT INTO `tbladdpeson` (`IDPerson`, `Name`, `Gender`, `Email`, `Assigned`, `AccountStatus`) VALUES
(0000000001, 'Cris', 'Male', 'cris@gmail.com', 1, 1),
(0000000002, 'Alivio', 'Female', 'alivio@gmail.com', 0, 0),
(0000000003, 'Ibyang', 'MAle', 'iby@gmail.com', 1, 0),
(0000000004, 'KC', 'Male', 'KC@gmail.com', 1, 1),
(0000000005, 'nicole', 'Female', '@gmail.com', 0, 0),
(0000000006, 'Louise', 'Female', 'louise@gmail.com', 0, 0),
(0000000007, 'qwert', 'Male', 'qwerty@gmail.com', 0, 1),
(0000000008, 'Mayie', 'Female', 'mayie@baboten@gmail.com', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbltaskmember`
--

CREATE TABLE `tbltaskmember` (
  `PersonID` int(10) UNSIGNED ZEROFILL NOT NULL,
  `TaskID` int(10) UNSIGNED ZEROFILL NOT NULL,
  `Name` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbltaskmember`
--

INSERT INTO `tbltaskmember` (`PersonID`, `TaskID`, `Name`) VALUES
(0000000001, 0000000006, ' Cris '),
(0000000008, 0000000006, ' Mayie '),
(0000000003, 0000000006, ' Ibyang '),
(0000000004, 0000000006, ' KC ');

--
-- Triggers `tbltaskmember`
--
DELIMITER $$
CREATE TRIGGER `New` AFTER INSERT ON `tbltaskmember` FOR EACH ROW UPDATE tbladdpeson SET tbladdpeson.Assigned=1 WHERE tbladdpeson.IDPerson=New.PersonID
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tbltasks`
--

CREATE TABLE `tbltasks` (
  `TaskID` int(10) UNSIGNED ZEROFILL NOT NULL,
  `TaskName` varchar(32) NOT NULL,
  `TaskSize` int(3) NOT NULL,
  `TaskStatus` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbltasks`
--

INSERT INTO `tbltasks` (`TaskID`, `TaskName`, `TaskSize`, `TaskStatus`) VALUES
(0000000001, 'Windows', 2, 0),
(0000000002, 'Kitchen', 10, 0),
(0000000003, 'Complab', 1, 0),
(0000000004, 'Office', 1, 0),
(0000000006, 'Garden', 4, 1);

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
-- Indexes for table `tbltasks`
--
ALTER TABLE `tbltasks`
  ADD PRIMARY KEY (`TaskID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tblaccount`
--
ALTER TABLE `tblaccount`
  MODIFY `IDAccount` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `tbladdpeson`
--
ALTER TABLE `tbladdpeson`
  MODIFY `IDPerson` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `tbltasks`
--
ALTER TABLE `tbltasks`
  MODIFY `TaskID` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
