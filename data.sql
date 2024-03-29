CREATE DATABASE  IF NOT EXISTS `editor` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `editor`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: editor
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test` (
  `testId` int NOT NULL AUTO_INCREMENT,
  `testTitle` varchar(20) DEFAULT NULL,
  `testDesc` varchar(5000) DEFAULT NULL,
  `testDiff` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`testId`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (105,'Prime number','Write a program to find whether the given number is prime or not.\nA prime number is a number that is not divisible by any other number. A prime number has only 2 factors: 1 and itself.\n\nExample 1:\ninput: 17\noutput: true\nExplanation: 17 is not divisible by any other number\n\nExample 2:\ninput: 35\noutput: false\nExplanation: 35 is divisible by 7 and 5','Easy'),(106,'Factorial','Write a program to calculate the factorial of a given number. The factorial of N is the product of all the numbers less than or equal to N\n \nExample 1:\ninput: 5\noutput: 120\nExplanation: 5x4x3x2x1 = 120\n \nExample 2:\ninput: 4\noutput: 24\nExplanation: 4x3x2x1 = 24','Easy'),(107,'Sum of digits','Write a program to calculate the sum of all the digits in the given integer. The given integer will be non negative and less than 2^32\n\nExample 1:\nInput: 4985\nOutput: 26\nExplanation: 4+9+8+5 = 26\n\nExample 2:\nInput: 32767\nOutput: 25\nExplanation: 3+2+7+6+7 = 25','Medium');
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testcase`
--

DROP TABLE IF EXISTS `testcase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `testcase` (
  `testCaseId` int NOT NULL AUTO_INCREMENT,
  `input` varchar(50) DEFAULT NULL,
  `output` varchar(50) DEFAULT NULL,
  `testId` int DEFAULT NULL,
  `testCaseType` int DEFAULT NULL,
  PRIMARY KEY (`testCaseId`),
  KEY `testId` (`testId`),
  CONSTRAINT `testcase_ibfk_1` FOREIGN KEY (`testId`) REFERENCES `test` (`testId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=238 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testcase`
--

LOCK TABLES `testcase` WRITE;
/*!40000 ALTER TABLE `testcase` DISABLE KEYS */;
INSERT INTO `testcase` VALUES (220,'4','false',105,1),(221,'6967','true',105,2),(222,'6968','false',105,2),(223,'19927','true',105,2),(224,'19928','false',105,2),(225,'9','false',105,2),(226,'21','false',105,2),(227,'4','24',106,1),(228,'7','5040',106,2),(229,'0','1',106,2),(230,'9','362880',106,2),(231,'5','120',106,2),(232,'32767','25',107,1),(233,'4985','26',107,2),(234,'11119','13',107,2),(235,'0','0',107,2),(236,'3456','18',107,2),(237,'199954','37',107,2);
/*!40000 ALTER TABLE `testcase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userId` varchar(40) NOT NULL,
  `userName` varchar(40) DEFAULT NULL,
  `userPassword` varchar(40) DEFAULT NULL,
  `userType` int DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('admin','Haerambhan','admin',1),('vineet','Vineet','vineet123',2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-03  3:53:25
