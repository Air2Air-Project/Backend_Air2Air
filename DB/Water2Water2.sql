CREATE DATABASE  IF NOT EXISTS `water2water` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `water2water`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: water2water
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `answer_board`
--

DROP TABLE IF EXISTS `answer_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answer_board` (
  `seq` int NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `member_id` int NOT NULL,
  `question_board_id` int NOT NULL,
  PRIMARY KEY (`seq`),
  KEY `answerboard_ibfk_1` (`question_board_id`),
  KEY `answer_member_fk_idx` (`member_id`),
  CONSTRAINT `answer_board_ibfk_1` FOREIGN KEY (`question_board_id`) REFERENCES `question_board` (`seq`) ON DELETE CASCADE,
  CONSTRAINT `answer_member_fk` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='after answer, questionboard''s is_answered is true';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer_board`
--

LOCK TABLES `answer_board` WRITE;
/*!40000 ALTER TABLE `answer_board` DISABLE KEYS */;
INSERT INTO `answer_board` VALUES (3,'This is a sample answer.','2024-07-04 06:44:24',2,2),(4,'답변입니다. 그 이유는 어쩌구 저쩌구','2024-07-04 08:03:25',2,3);
/*!40000 ALTER TABLE `answer_board` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`project`@`%`*/ /*!50003 TRIGGER `answerboard_AFTER_INSERT` AFTER INSERT ON `answer_board` FOR EACH ROW BEGIN
	UPDATE `Question_Board`
    SET `is_answered` = 1
    WHERE `seq` = NEW.`question_board_id`;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `member_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(45) NOT NULL,
  `role` enum('ROLE_USER','ROLE_ADMIN') NOT NULL DEFAULT 'ROLE_USER',
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,'test@gmail.com','$2a$10$ocn/rSRNdEVADdeLZPPKDe49MyUbBi2Puuus2XIG35kkqzD7zIoZG','test','ROLE_USER'),(2,'scott@gmail.com','$2a$10$Wixc0lRWbKv/7kmhrxmpuO/qT0BVOIyvfRdiyS7JaMqc0Sj9fhDEu','scott','ROLE_ADMIN');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_board`
--

DROP TABLE IF EXISTS `question_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question_board` (
  `seq` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `content` text NOT NULL,
  `is_answered` tinyint NOT NULL DEFAULT '0',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `member_id` int NOT NULL,
  `question_type` enum('ORP','PHP','VALVE','ETC') NOT NULL DEFAULT 'ETC',
  PRIMARY KEY (`seq`),
  KEY `question_member_fk_idx` (`member_id`),
  CONSTRAINT `question_member_fk` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_board`
--

LOCK TABLES `question_board` WRITE;
/*!40000 ALTER TABLE `question_board` DISABLE KEYS */;
INSERT INTO `question_board` VALUES (2,'Sample Question','This is a sample question.',1,'2024-07-04 06:43:35',1,'ETC'),(3,'질문입니다.','질문입니다. 이건 왜 이렇게 되나요?',1,'2024-07-04 07:59:39',1,'ETC');
/*!40000 ALTER TABLE `question_board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'water2water'
--

--
-- Dumping routines for database 'water2water'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-04 18:11:26
