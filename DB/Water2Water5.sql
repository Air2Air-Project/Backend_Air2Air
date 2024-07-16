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
-- Table structure for table `alert`
--

DROP TABLE IF EXISTS `alert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alert` (
  `alert_id` int NOT NULL AUTO_INCREMENT,
  `region_id` int NOT NULL,
  `alert_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `alert_type` enum('ACTIVITY','POLLUTION') NOT NULL,
  PRIMARY KEY (`alert_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert`
--

LOCK TABLES `alert` WRITE;
/*!40000 ALTER TABLE `alert` DISABLE KEYS */;
INSERT INTO `alert` VALUES (1,14,'2024-07-14 14:56:52','ACTIVITY'),(2,14,'2024-07-14 15:01:18','ACTIVITY'),(3,14,'2024-07-14 15:03:22','POLLUTION'),(4,14,'2024-07-14 15:04:19','POLLUTION'),(5,14,'2024-07-14 15:07:16','ACTIVITY'),(6,14,'2024-07-14 15:08:01','POLLUTION'),(7,14,'2024-07-14 15:08:29','POLLUTION'),(8,14,'2024-07-14 15:17:37','POLLUTION'),(9,14,'2024-07-14 15:17:49','POLLUTION'),(10,14,'2024-07-14 15:49:32','POLLUTION'),(11,14,'2024-07-14 15:51:24','POLLUTION'),(12,14,'2024-07-14 15:52:59','POLLUTION'),(13,14,'2024-07-14 15:58:27','ACTIVITY'),(14,20,'2024-07-14 16:18:03','ACTIVITY');
/*!40000 ALTER TABLE `alert` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='after answer, questionboard''s is_answered is true';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer_board`
--

LOCK TABLES `answer_board` WRITE;
/*!40000 ALTER TABLE `answer_board` DISABLE KEYS */;
INSERT INTO `answer_board` VALUES (3,'수정 완료된 답변입니다.','2024-07-06 06:41:12',2,3),(12,'수정 되나염?','2024-07-15 03:16:35',2,119),(15,'히히 난 자유다 히히 찐막이다~','2024-07-15 03:49:47',2,113),(17,'답변 등록하기','2024-07-15 09:09:46',2,127);
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
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`project`@`%`*/ /*!50003 TRIGGER `answer_board_AFTER_DELETE` AFTER DELETE ON `answer_board` FOR EACH ROW BEGIN
	UPDATE `Question_Board`
    SET `is_answered` = 0
    WHERE `seq` = OLD.`question_board_id`;
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
  `region_id` int NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `phone_number_UNIQUE` (`phone_number`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,'test@gmail.com','$2a$10$pchyYOKHgz4otXalOI5jgugt.TNx5KrK7jf6WlA/nfNLvPuxQ/jI2','test','ROLE_USER',17,'01011111111'),(2,'scott@gmail.com','$2a$10$Wixc0lRWbKv/7kmhrxmpuO/qT0BVOIyvfRdiyS7JaMqc0Sj9fhDEu','scott','ROLE_ADMIN',5,'01022222222'),(3,'baejimil@naver.com','$2a$10$G2msSuPkZ/JvgvZ2/6eaaOVUy.05iRWyKQp3DbXnS19kI1vWHiVNu','baegimil','ROLE_USER',15,'01033333333'),(4,'woojung@naver.com','$2a$10$snmCUeFS4cd9QK3Jrjf/EuF4ipUTpF.CCkwJXgyjkhOPHkHU0xgQq','woojung','ROLE_USER',35,'01044444444'),(5,'youngin@naver.com','$2a$10$e4sWQpk5lb8BxlOeyTXksuAfFel05llFPNK7/lUzYHx0Hnqo3CDki','young12','ROLE_USER',14,'01055556666'),(6,'hoho.@gmail.com','$2a$10$czP8fDR/BcMzyBONk7Xa8ex/SBbOS3HPZIrlxzPqz2wUsF.CtsJiu','hiho','ROLE_USER',20,'01066666666');
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
  `question_type` enum('STATION','ALERT','DUST','ETC') NOT NULL DEFAULT 'ETC',
  PRIMARY KEY (`seq`),
  KEY `question_member_fk_idx` (`member_id`),
  CONSTRAINT `question_member_fk` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_board`
--

LOCK TABLES `question_board` WRITE;
/*!40000 ALTER TABLE `question_board` DISABLE KEYS */;
INSERT INTO `question_board` VALUES (1,'문의사항있습니다1','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'DUST'),(2,'문의사항있습니다2','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'STATION'),(3,'문의사항있습니다3','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',1,'2024-07-06 06:41:12',4,'STATION'),(4,'문의사항있습니다4','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'STATION'),(5,'문의사항있습니다5','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',1,'DUST'),(6,'문의사항있습니다6','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'STATION'),(7,'문의사항있습니다7','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'DUST'),(8,'수정된 게시물 입니다.','수정 완료',0,'2024-07-06 06:41:12',4,'DUST'),(9,'문의사항있습니다9','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'ALERT'),(10,'문의사항있습니다10','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',1,'STATION'),(11,'문의사항있습니다11','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'DUST'),(12,'문의사항있습니다12','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'DUST'),(13,'문의사항있습니다13','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',4,'ETC'),(14,'문의사항있습니다14','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'STATION'),(15,'문의사항있습니다15fdfdf','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',1,'ALERT'),(16,'문의사항있습니다16','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'STATION'),(17,'문의사항있습니다17','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'DUST'),(19,'문의사항있습니다19','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'DUST'),(20,'문의사항있습니다20','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',1,'ETC'),(21,'문의사항있습니다21','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'STATION'),(22,'문의사항있습니다22','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'ETC'),(23,'문의사항있습니다23','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',4,'STATION'),(24,'문의사항있습니다24','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'DUST'),(25,'문의사항있습니다25','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',1,'DUST'),(26,'문의사항있습니다26','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'STATION'),(27,'문의사항있습니다27','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'DUST'),(28,'문의사항있습니다28','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',4,'STATION'),(29,'문의사항있습니다29','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'ETC'),(31,'문의사항있습니다31','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'ALERT'),(32,'문의사항있습니다32','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'ALERT'),(33,'문의사항있습니다33','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',4,'ALERT'),(34,'문의사항있습니다34','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'ALERT'),(36,'문의사항있습니다36','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'DUST'),(37,'문의사항있습니다37','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'ETC'),(38,'문의사항있습니다38','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',4,'ALERT'),(39,'문의사항있습니다39','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'DUST'),(41,'문의사항있습니다41','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'DUST'),(42,'문의사항있습니다42','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'DUST'),(43,'문의사항있습니다43','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',4,'DUST'),(44,'문의사항있습니다44','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'STATION'),(46,'문의사항있습니다46','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'STATION'),(47,'문의사항있습니다47','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'STATION'),(48,'문의사항있습니다48','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',4,'DUST'),(49,'문의사항있습니다49','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'STATION'),(51,'문의사항있습니다51','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'STATION'),(52,'문의사항있습니다52','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'ETC'),(53,'문의사항있습니다53','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',4,'STATION'),(54,'문의사항있습니다54','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'STATION'),(56,'문의사항있습니다56','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'DUST'),(57,'문의사항있습니다57','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'ALERT'),(58,'문의사항있습니다58','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',4,'ETC'),(59,'문의사항있습니다59','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'ETC'),(61,'문의사항있습니다61','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'DUST'),(62,'문의사항있습니다62','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'DUST'),(63,'문의사항있습니다63','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',4,'ALERT'),(64,'문의사항있습니다64','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'STATION'),(66,'문의사항있습니다66','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'ETC'),(67,'문의사항있습니다67','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'STATION'),(68,'문의사항있습니다68','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',4,'DUST'),(69,'문의사항있습니다69','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'ETC'),(71,'문의사항있습니다71','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'DUST'),(72,'문의사항있습니다72','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'DUST'),(73,'문의사항있습니다73','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',4,'ETC'),(74,'문의사항있습니다74','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'ETC'),(76,'문의사항있습니다76','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'ALERT'),(77,'문의사항있습니다77','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'DUST'),(78,'문의사항있습니다78','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',4,'ETC'),(79,'문의사항있습니다79','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'ETC'),(81,'문의사항있습니다81','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'ALERT'),(82,'문의사항있습니다82','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'STATION'),(83,'문의사항있습니다83','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',4,'STATION'),(84,'문의사항있습니다84','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'ALERT'),(86,'문의사항있습니다86','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'DUST'),(87,'문의사항있습니다87','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'STATION'),(89,'문의사항있습니다89','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'DUST'),(91,'문의사항있습니다91','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'ALERT'),(92,'문의사항있습니다92','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'ALERT'),(93,'문의사항있습니다93','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',4,'ETC'),(94,'문의사항있습니다94','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'DUST'),(96,'문의사항있습니다96','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',2,'ALERT'),(97,'문의사항있습니다97','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',3,'ETC'),(98,'문의사항있습니다98','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',4,'ETC'),(99,'문의사항있습니다99','문의사항입니다. 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구 어쩌구저쩌구',0,'2024-07-06 06:41:12',5,'DUST'),(113,'테스트용 문의사항','안녕하세요. 다름이아니라 어쩌구 저쩌구 문의사항입니다.',1,'2024-07-12 06:54:22',4,'DUST'),(116,'테스트용 문의사항','안녕하세요. 다름이아니라 어쩌구 저쩌구 문의사항입니다.',0,'2024-07-12 08:21:21',4,'DUST'),(119,'ㅇㄴㄴㅇfdfdf','ㅇㄴㅇㄴ\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n',1,'2024-07-13 08:09:48',1,'ALERT'),(127,'ㅇㄴㅇㄴ 징짜 어이가 ㅇ벗음','ㅇㄴㅇㄴㅇㄴ',1,'2024-07-15 07:55:20',1,'STATION');
/*!40000 ALTER TABLE `question_board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `region`
--

DROP TABLE IF EXISTS `region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `region` (
  `region_id` int NOT NULL,
  `large` varchar(45) NOT NULL,
  `middle` varchar(45) NOT NULL,
  `small` varchar(45) NOT NULL,
  `station_name` varchar(45) NOT NULL,
  `dmX` varchar(45) NOT NULL,
  `dmY` varchar(45) NOT NULL,
  PRIMARY KEY (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `region`
--

LOCK TABLES `region` WRITE;
/*!40000 ALTER TABLE `region` DISABLE KEYS */;
INSERT INTO `region` VALUES (1,'창원시','마산합포구','진동면 삼진의거대로 621','삼진로','35.11497','128.48581'),(2,'창원시','진해구','경화로16번길 31','경화동','35.154972','128.689578'),(3,'창원시','마산합포구','월영동16길 22','월영동','35.183108','128.560665'),(4,'하동군','하동읍','군청로 23','하동읍','35.066944','127.751389'),(5,'하동군','금성면','금성중앙길 14','금성면','34.965927','127.794191'),(6,'김해시','호계로','517번길 8','동상동','35.236667','128.883333'),(7,'김해시','활천로','303','삼방동','35.243889','128.912222'),(8,'김해시','능동로','149','장유동','35.202364','128.807221'),(9,'김해시','진영읍','김해대로365번길 6-24','진영읍','35.3077015','128.7311228'),(10,'김해시','김해대로','2515','김해대로','35.227745','128.901042'),(11,'김해시','진례면','진례로 241','진례면','35.245937','128.750295'),(12,'거제시','남부면','저구리 산 116번지','저구리','34.71005','128.58745'),(13,'거제시','아주동','산164-1','아주동','34.863611','128.690278'),(14,'거제시','계룡로','125','고현동','34.88053905','128.6211295'),(15,'사천시','사천읍','읍내로 52','사천읍','35.082551','128.091236'),(16,'사천시','향촌5길','28','향촌동','34.93370386','128.0934468'),(17,'창원시','의창구','대산면 갈전로99번길 100 ','대산면','35.371633','128.676483'),(18,'양산시','북안남5길','21','북부동','35.345556','129.04'),(19,'양산시','삼호9길','11','삼호동','35.414667','129.172083'),(20,'양산시','물금읍','황산로 384','물금읍','35.310414','128.987347'),(21,'밀양시','중앙로','346','내일동','35.493678','128.754168'),(22,'통영시','안개4길','53','무전동','34.857173','128.432385'),(23,'고성군','고성읍','중앙로 35','고성읍','34.97454361','128.3243698'),(24,'거창군','거창읍','거함대로 3252','거창읍','35.6782','127.920092'),(25,'함안군','가야읍','함안대로 505','가야읍','35.2720132','128.408105'),(26,'함양군','함양읍','고운로 35','함양읍','35.5211721','127.7244433'),(27,'남해군','남해읍','남해대로 2745','남해읍','34.8318834','127.899848'),(28,'산청군','산청읍','산엔청로 1','산청읍','35.412344','127.87597'),(29,'거창군','남상면','인평길 36','남상면','35.643167','127.9105'),(30,'의령군','의령읍','의병로8길 44','의령읍','35.315748','128.253757'),(31,'창녕군','창녕읍','우포2로 1189-35','창녕읍','35.5452909','128.4883674'),(32,'합천군','합천읍','대야로 888-20','합천읍','35.56723','128.16517'),(33,'창원시','성산구','적현로 424','마산항','35.191823','128.591172'),(34,'하동군','금성면','경제산업로 509','하동항','34.5643','127.4918'),(35,'사천시','향촌동','1292','삼천포항','34.91826562','128.0843094'),(36,'창원시','마산회원구','회원동 11번길 7','회원동','35.21815','128.57425'),(37,'창원시','마산회원구','봉양로 148','봉암동','35.2178','128.6023'),(38,'창원시','마산회원구','내서읍 광려로 8','내서읍','35.22583036','128.5032583'),(39,'진주시','북장대로64번길','14','상봉동','35.195891','128.074596'),(40,'진주시','진주대로','1052','대안동','35.193333','128.084167'),(41,'진주시','동진로','279','상대동(진주)','35.180609','128.121781'),(42,'진주시','정촌면','예하리 1340','정촌면','35.12460167','128.0999225'),(43,'창원시','의창구','우곡로101번길 28','명서동','35.243402','128.641756'),(44,'창원시','성산구','공단로 303','웅남동','35.217333','128.656617'),(45,'창원시','성산구','외리로14번길 18','성주동','35.1993934','128.7108707'),(46,'창원시','의창구','용지로 239번길 19-4','용지동','35.236111','128.683889'),(47,'창원시','의창구','원이대로 450','반송로','35.2341412','128.6646236'),(48,'창원시','성산구','창이대로 706번길 16-23','사파동','35.221729','128.69825');
/*!40000 ALTER TABLE `region` ENABLE KEYS */;
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

-- Dump completed on 2024-07-16 21:32:09
