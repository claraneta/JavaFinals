-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 24, 2019 at 10:19 AM
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dbmanager`
--

-- --------------------------------------------------------

--
-- Table structure for table `tblaccount`
--

CREATE TABLE IF NOT EXISTS `tblaccount` (
  `IDAccount` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `PersonID` int(10) unsigned zerofill NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(50) NOT NULL,
  `usertype` int(2) NOT NULL,
  PRIMARY KEY (`IDAccount`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `tblaccount`
--

INSERT INTO `tblaccount` (`IDAccount`, `PersonID`, `username`, `password`, `usertype`) VALUES
(0000000001, 0000000000, 'jeanny', 'coder', 1),
(0000000002, 0000000000, 'student', 'student', 2),
(0000000003, 0000000001, 'sirc', 'sirc', 2),
(0000000008, 0000000007, 'userqwert', '1234', 2);

--
-- Triggers `tblaccount`
--
DROP TRIGGER IF EXISTS `accountstat`;
DELIMITER //
CREATE TRIGGER `accountstat` AFTER INSERT ON `tblaccount`
 FOR EACH ROW UPDATE tbladdpeson set tbladdpeson.AccountStatus = 1 where tbladdpeson.IDPerson = NEW.PersonID
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tbladdpeson`
--

CREATE TABLE IF NOT EXISTS `tbladdpeson` (
  `IDPerson` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `Name` varchar(32) NOT NULL,
  `Gender` varchar(6) NOT NULL,
  `Email` varchar(32) NOT NULL,
  `Assigned` tinyint(1) NOT NULL DEFAULT '0',
  `AccountStatus` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`IDPerson`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `tbladdpeson`
--

INSERT INTO `tbladdpeson` (`IDPerson`, `Name`, `Gender`, `Email`, `Assigned`, `AccountStatus`) VALUES
(0000000001, 'Cris', 'Male', 'cris@gmail.com', 1, 1),
(0000000002, 'Alivio', 'Female', 'alivio@gmail.com', 1, 0),
(0000000003, 'Ibyang', 'MAle', 'iby@gmail.com', 1, 0),
(0000000004, 'KC', 'Male', 'KC@gmail.com', 0, 0),
(0000000005, 'nicole', 'Female', '@gmail.com', 0, 0),
(0000000006, 'Louise', 'Female', 'louise@gmail.com', 0, 0),
(0000000007, 'qwert', 'Male', 'qwerty@gmail.com', 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbldeletedtasks`
--

CREATE TABLE IF NOT EXISTS `tbldeletedtasks` (
  `TaskID` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `TaskName` varchar(32) NOT NULL,
  `TaskSize` int(3) NOT NULL,
  PRIMARY KEY (`TaskID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tbltaskmember`
--

CREATE TABLE IF NOT EXISTS `tbltaskmember` (
  `PersonID` int(10) unsigned zerofill NOT NULL,
  `TaskID` int(10) unsigned zerofill NOT NULL,
  `Name` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbltaskmember`
--

INSERT INTO `tbltaskmember` (`PersonID`, `TaskID`, `Name`) VALUES
(0000000001, 0000000004, ' Cris '),
(0000000002, 0000000001, ' Alivio '),
(0000000003, 0000000001, ' Ibyang ');

--
-- Triggers `tbltaskmember`
--
DROP TRIGGER IF EXISTS `New`;
DELIMITER //
CREATE TRIGGER `New` AFTER INSERT ON `tbltaskmember`
 FOR EACH ROW UPDATE tbladdpeson SET tbladdpeson.Assigned=1 WHERE tbladdpeson.IDPerson=New.PersonID
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tbltasks`
--

CREATE TABLE IF NOT EXISTS `tbltasks` (
  `TaskID` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `TaskName` varchar(32) NOT NULL,
  `TaskSize` int(3) NOT NULL,
  PRIMARY KEY (`TaskID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `tbltasks`
--

INSERT INTO `tbltasks` (`TaskID`, `TaskName`, `TaskSize`) VALUES
(0000000001, 'Windows', 2),
(0000000002, 'Kitchen', 10),
(0000000003, 'Complab', 3),
(0000000004, 'Office', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tblusertype`
--

CREATE TABLE IF NOT EXISTS `tblusertype` (
  `IDUsertype` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `UserType` varchar(10) NOT NULL,
  PRIMARY KEY (`IDUsertype`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
