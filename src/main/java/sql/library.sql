-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 16, 2023 at 05:08 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Database: `library`
--
CREATE DATABASE IF NOT EXISTS `library` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `library`;

-- --------------------------------------------------------

--
-- Table structure for table `bookgenres`
--

DROP TABLE IF EXISTS `bookgenres`;
CREATE TABLE `bookgenres` (
                              `bookID` int(11) NOT NULL,
                              `genreID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bookgenres`
--

INSERT INTO `bookgenres` (`bookID`, `genreID`) VALUES
                                                   (1, 1),
                                                   (1, 2),
                                                   (2, 1),
                                                   (2, 2),
                                                   (3, 2),
                                                   (4, 3),
                                                   (4, 1),
                                                   (5, 2);

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
                         `bookID` int(11) NOT NULL,
                         `bookName` varchar(255) NOT NULL,
                         `author` varchar(255) NOT NULL,
                         `description` varchar(255) DEFAULT NULL,
                         `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`bookID`, `bookName`, `author`, `description`, `quantity`) VALUES
                                                                                    (1, 'one piece', 'oda', 'it is a manga of pirates', 1000),
                                                                                    (2, 'gintama', 'Hideaki Sorachi', 'it is a manga of gintama', 20),
                                                                                    (3, 'naruto', 'Masashi Kishimoto', 'it is a manga of ninjas', 5),
                                                                                    (4, 'Mieruko-chan', 'Tomoki Izumi', 'horror manga with ability to see ghost', 4),
                                                                                    (5, 'blue lock', 'yusuke nomura', 'hardcore soccer', 66);

-- --------------------------------------------------------

--
-- Table structure for table `genres`
--

DROP TABLE IF EXISTS `genres`;
CREATE TABLE `genres` (
                          `genreID` int(11) NOT NULL,
                          `genreName` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `genres`
--

INSERT INTO `genres` (`genreID`, `genreName`) VALUES
                                                  (1, 'comedy'),
                                                  (2, 'action'),
                                                  (3, 'horror'),
                                                  (4, 'romance'),
                                                  (5, 'sci-fi');

-- --------------------------------------------------------

--
-- Table structure for table `userbooks`
--

DROP TABLE IF EXISTS `userbooks`;
CREATE TABLE `userbooks` (
                             `userID` int(11) NOT NULL,
                             `bookID` int(11) NOT NULL,
                             `borrowDate` datetime NOT NULL,
                             `dueDate` datetime NOT NULL,
                             `returnedDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `userbooks`
--

INSERT INTO `userbooks` (`userID`, `bookID`, `borrowDate`, `dueDate`, `returnedDate`) VALUES
    (1, 1, '2023-11-11 13:23:44', '2023-12-11 13:23:44', '2023-11-12 13:23:44');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `userID` int(11) NOT NULL,
                         `userName` varchar(50) NOT NULL,
                         `email` varchar(255) NOT NULL,
                         `password` varchar(255) NOT NULL,
                         `address` varchar(80) DEFAULT NULL,
                         `phone` varchar(20) DEFAULT NULL,
                         `fees` int(11) DEFAULT NULL,
                         `userType` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userID`, `userName`, `email`, `password`, `address`, `phone`, `fees`, `userType`) VALUES
                                                                                                            (1, 'jerry', 'jerry@gmail.com', 'aaa', NULL, NULL, 0, 0),
                                                                                                            (2, 'bill', 'bill@gmail.com', 'aaacsac', 'wall', '021321312', 0, 0),
                                                                                                            (3, 'admin', 'admin@gmail.com', 'aadqffewf', 'wall', '021321312', 0, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookgenres`
--
ALTER TABLE `bookgenres`
    ADD KEY `bookID` (`bookID`),
    ADD KEY `genreID` (`genreID`);

--
-- Indexes for table `books`
--
ALTER TABLE `books`
    ADD PRIMARY KEY (`bookID`);

--
-- Indexes for table `genres`
--
ALTER TABLE `genres`
    ADD PRIMARY KEY (`genreID`),
    ADD UNIQUE KEY `genreName` (`genreName`);

--
-- Indexes for table `userbooks`
--
ALTER TABLE `userbooks`
    ADD KEY `userID` (`userID`),
    ADD KEY `bookID` (`bookID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
    ADD PRIMARY KEY (`userID`),
    ADD UNIQUE KEY `userName` (`userName`),
    ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
    MODIFY `bookID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `genres`
--
ALTER TABLE `genres`
    MODIFY `genreID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
    MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bookgenres`
--
ALTER TABLE `bookgenres`
    ADD CONSTRAINT `bookgenres_ibfk_1` FOREIGN KEY (`bookID`) REFERENCES `books` (`bookID`),
    ADD CONSTRAINT `bookgenres_ibfk_2` FOREIGN KEY (`genreID`) REFERENCES `genres` (`genreID`);

--
-- Constraints for table `userbooks`
--
ALTER TABLE `userbooks`
    ADD CONSTRAINT `userbooks_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`),
    ADD CONSTRAINT `userbooks_ibfk_2` FOREIGN KEY (`bookID`) REFERENCES `books` (`bookID`);
COMMIT;
