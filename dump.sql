-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: library
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `library`
--

/*!40000 DROP DATABASE IF EXISTS `library`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `library` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `library`;

--
-- Table structure for table `authors`
--

DROP TABLE IF EXISTS `authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authors` (
  `AuthorID` int NOT NULL AUTO_INCREMENT,
  `AuthorName` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`AuthorID`),
  KEY `AuthorName` (`AuthorName`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authors`
--

LOCK TABLES `authors` WRITE;
/*!40000 ALTER TABLE `authors` DISABLE KEYS */;
INSERT INTO `authors` VALUES (3,'Harri Won'),(1,'Haruki Murakami'),(2,'Kevin Chen');
/*!40000 ALTER TABLE `authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookauthors`
--

DROP TABLE IF EXISTS `bookauthors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookauthors` (
  `BookID` int NOT NULL,
  `AuthorID` int NOT NULL,
  PRIMARY KEY (`BookID`,`AuthorID`),
  KEY `AuthorID` (`AuthorID`),
  CONSTRAINT `bookauthors_ibfk_1` FOREIGN KEY (`BookID`) REFERENCES `books` (`BookID`) ON DELETE CASCADE,
  CONSTRAINT `bookauthors_ibfk_2` FOREIGN KEY (`AuthorID`) REFERENCES `authors` (`AuthorID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookauthors`
--

LOCK TABLES `bookauthors` WRITE;
/*!40000 ALTER TABLE `bookauthors` DISABLE KEYS */;
INSERT INTO `bookauthors` VALUES (1,1),(2,2),(6,3),(7,3),(8,3);
/*!40000 ALTER TABLE `bookauthors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `BookID` int NOT NULL AUTO_INCREMENT,
  `Category` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Reissue` tinyint DEFAULT NULL,
  `PublisherID` int DEFAULT NULL,
  PRIMARY KEY (`BookID`),
  KEY `PublisherID` (`PublisherID`),
  KEY `Category` (`Category`),
  CONSTRAINT `books_ibfk_1` FOREIGN KEY (`BookID`) REFERENCES `publications` (`PublicationID`) ON DELETE CASCADE,
  CONSTRAINT `books_ibfk_2` FOREIGN KEY (`PublisherID`) REFERENCES `publishers` (`PublisherID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'Văn học nước ngoài',0,1),(2,'Văn học nước ngoài',0,2),(5,'Visual Novel',0,NULL),(6,'Visual Novel',0,NULL),(7,'Visual Novel',0,NULL),(8,'Visual Novel',1,NULL);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `printmedia`
--

DROP TABLE IF EXISTS `printmedia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `printmedia` (
  `PrintMediaID` int NOT NULL AUTO_INCREMENT,
  `ReleaseNumber` tinyint DEFAULT NULL,
  `PrintType` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`PrintMediaID`),
  CONSTRAINT `printmedia_ibfk_1` FOREIGN KEY (`PrintMediaID`) REFERENCES `publications` (`PublicationID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `printmedia`
--

LOCK TABLES `printmedia` WRITE;
/*!40000 ALTER TABLE `printmedia` DISABLE KEYS */;
INSERT INTO `printmedia` VALUES (3,6,'Tạp chí'),(4,6,'Tạp chí');
/*!40000 ALTER TABLE `printmedia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publications`
--

DROP TABLE IF EXISTS `publications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publications` (
  `PublicationID` int NOT NULL AUTO_INCREMENT,
  `Title` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `ReleaseDate` date NOT NULL,
  `Country` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Quantity` smallint NOT NULL,
  PRIMARY KEY (`PublicationID`),
  KEY `Title` (`Title`),
  KEY `Country` (`Country`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publications`
--

LOCK TABLES `publications` WRITE;
/*!40000 ALTER TABLE `publications` DISABLE KEYS */;
INSERT INTO `publications` VALUES (1,'Ngôi thứ nhất số ít','2023-05-24','Nhật Bản',7),(2,'Vùng đất quỷ tha ma bắt','2023-03-27','Đài Loan',5),(3,'Tạp chí Ngân hàng','2023-06-14','Việt Nam',3),(4,'Tạp chí Giáo dục','2023-06-13','Việt Nam',1),(5,'Harri Won','2023-06-06','Korea',2),(6,'Harri Won','2023-05-28','Korea',1),(7,'Harri Won','2023-05-28','Korea',1),(8,'Harri Won Life part 3','2023-05-11','Korea',1);
/*!40000 ALTER TABLE `publications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publishers`
--

DROP TABLE IF EXISTS `publishers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publishers` (
  `PublisherID` int NOT NULL AUTO_INCREMENT,
  `PublisherName` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`PublisherID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publishers`
--

LOCK TABLES `publishers` WRITE;
/*!40000 ALTER TABLE `publishers` DISABLE KEYS */;
INSERT INTO `publishers` VALUES (1,'NXB Hội nhà văn'),(2,'NXB Phụ nữ VN');
/*!40000 ALTER TABLE `publishers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `StaffID` int NOT NULL AUTO_INCREMENT,
  `Username` char(30) DEFAULT NULL,
  `Name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `Gender` bit(1) DEFAULT NULL,
  `Address` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Email` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `Phone` char(10) NOT NULL,
  `Position` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`StaffID`),
  KEY `Username` (`Username`),
  KEY `Name` (`Name`),
  KEY `Email` (`Email`),
  KEY `Phone` (`Phone`),
  CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `users` (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `StudentID` char(10) NOT NULL,
  `Username` char(30) DEFAULT NULL,
  `Name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `Gender` bit(1) NOT NULL,
  `Address` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Email` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `Phone` char(10) NOT NULL,
  `ClassName` char(10) DEFAULT NULL,
  `Fine` int DEFAULT NULL,
  `FineStatus` bit(1) NOT NULL,
  PRIMARY KEY (`StudentID`),
  KEY `Username` (`Username`),
  KEY `Name` (`Name`),
  KEY `Email` (`Email`),
  KEY `Phone` (`Phone`),
  CONSTRAINT `students_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `users` (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES ('24A4040001','student1','Nguyễn Văn A',_binary '\0','123 Đường Láng','24a4040001@hvnh.edu.vn','0123456789','K24QTKDA',0,_binary '\0'),('24A4040002','student2','Trần Thị B',_binary '','234 Đường Láng','24a4040002@hvnh.edu.vn','0123456788','K24QTKDA',0,_binary '\0'),('24A4040003','student3','Hoàng Thị C',_binary '','345 Đường Láng','24a4040003@hvnh.edu.vn','0123456787','K24QTKDB',0,_binary '\0');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `Username` char(30) NOT NULL,
  `Password` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`Username`),
  KEY `Password` (`Password`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('librarian','111111'),('student1','111111'),('student2','222222'),('clerk','2222222'),('student3','333333');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `v_books`
--

DROP TABLE IF EXISTS `v_books`;
/*!50001 DROP VIEW IF EXISTS `v_books`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_books` AS SELECT 
 1 AS `PublicationID`,
 1 AS `Title`,
 1 AS `Authors`,
 1 AS `ReleaseDate`,
 1 AS `Country`,
 1 AS `Quantity`,
 1 AS `Category`,
 1 AS `Reissue`,
 1 AS `PublisherName`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_printmedia`
--

DROP TABLE IF EXISTS `v_printmedia`;
/*!50001 DROP VIEW IF EXISTS `v_printmedia`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_printmedia` AS SELECT 
 1 AS `PublicationID`,
 1 AS `Title`,
 1 AS `ReleaseDate`,
 1 AS `Country`,
 1 AS `Quantity`,
 1 AS `ReleaseNumber`,
 1 AS `PrintType`*/;
SET character_set_client = @saved_cs_client;

--
-- Current Database: `library`
--

USE `library`;

--
-- Final view structure for view `v_books`
--

/*!50001 DROP VIEW IF EXISTS `v_books`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_books` AS select `p`.`PublicationID` AS `PublicationID`,`p`.`Title` AS `Title`,group_concat(`a`.`AuthorName` separator ',') AS `Authors`,`p`.`ReleaseDate` AS `ReleaseDate`,`p`.`Country` AS `Country`,`p`.`Quantity` AS `Quantity`,`b`.`Category` AS `Category`,`b`.`Reissue` AS `Reissue`,`pb`.`PublisherName` AS `PublisherName` from ((((`publications` `p` join `books` `b` on((`p`.`PublicationID` = `b`.`BookID`))) join `bookauthors` `ba` on((`b`.`BookID` = `ba`.`BookID`))) join `authors` `a` on((`ba`.`AuthorID` = `a`.`AuthorID`))) join `publishers` `pb` on((`b`.`PublisherID` = `pb`.`PublisherID`))) group by `p`.`PublicationID`,`p`.`Title`,`p`.`ReleaseDate`,`p`.`Country`,`p`.`Quantity`,`b`.`Category`,`b`.`Reissue`,`pb`.`PublisherName` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_printmedia`
--

/*!50001 DROP VIEW IF EXISTS `v_printmedia`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_printmedia` AS select `p`.`PublicationID` AS `PublicationID`,`p`.`Title` AS `Title`,`p`.`ReleaseDate` AS `ReleaseDate`,`p`.`Country` AS `Country`,`p`.`Quantity` AS `Quantity`,`pm`.`ReleaseNumber` AS `ReleaseNumber`,`pm`.`PrintType` AS `PrintType` from (`publications` `p` join `printmedia` `pm` on((`p`.`PublicationID` = `pm`.`PrintMediaID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-15 12:44:02
