-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: rahul
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18458 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (18457,'RoKoMSD');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `book_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `author` varchar(255) NOT NULL,
  `genre` varchar(100) DEFAULT NULL,
  `total_copies` int NOT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'Mrutyunjay','Shivaji Sawant','Novel',8),(2,'Light House','Shantanu Naidu','BioGraphy',15),(3,'The God of Small Things','Arundhati Roy','Fiction',8),(4,'Train to Pakistan','Khushwant Singh','Historical Fiction',15),(5,'The White Tiger','Aravind Adiga','Fiction',11),(6,'A Suitable Boy','Vikram Seth','Fiction',8),(7,'Midnight\'s Children','Salman Rushdie','Magical Realism',10),(8,'The Guide','R. K. Narayan','Fiction',5),(9,'Malgudi Days','R. K. Narayan','Short Stories',10),(10,'Gitanjali','Rabindranath Tagore','Poetry',6),(11,'Interpreter of Maladies','Jhumpa Lahiri','Short Stories',10),(13,'The Namesake','Jhumpa Lahiri','Fiction',12),(14,'The Palace of Illusions','Chitra Banerjee Divakaruni','Mythology',10),(15,'Fighter','Ram','Novel',8),(16,'The Inheritance of Loss','Kiran Desai','Fiction',8),(17,'Shantaram','Gregory David Roberts','Adventure',10),(18,'Sacred Games','Vikram Chandra','Thriller',12),(19,'The Immortals of Meluha','Amish Tripathi','Mythology',15),(20,'Chanakya\'s Chant','Ashwin Sanghi','Historical Fiction',10),(21,'The Blue Umbrella','Ruskin Bond','Children\'s Fiction',10),(22,'Love Story','Carry','RomCom',12),(23,'rohan','shantanu naidu','biography',12),(25,'ms dhoni untold story','akash','sport',12),(26,'Life Lessons','Rohan Kariappa','Novel',10),(27,'HouseFull','Rohan Kariappa','Philosophy',6),(28,'Blue Water','Ganesh shisal','Novel',3),(29,'Story of Warrior','Rohan Patil','Novel',12);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `copies`
--

DROP TABLE IF EXISTS `copies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `copies` (
  `copy_id` int NOT NULL AUTO_INCREMENT,
  `book_id` int DEFAULT NULL,
  `availability_status` varchar(50) NOT NULL DEFAULT 'available',
  PRIMARY KEY (`copy_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `copies_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books` (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=301 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `copies`
--

LOCK TABLES `copies` WRITE;
/*!40000 ALTER TABLE `copies` DISABLE KEYS */;
INSERT INTO `copies` VALUES (1,1,'available'),(2,1,'archived'),(3,1,'lost'),(4,1,'available'),(5,1,'available'),(6,1,'available'),(7,1,'available'),(8,1,'available'),(9,1,'available'),(10,1,'available'),(11,2,'available'),(12,2,'available'),(13,2,'available'),(14,2,'available'),(15,2,'available'),(16,2,'available'),(17,2,'available'),(18,2,'available'),(19,2,'available'),(20,2,'available'),(21,2,'available'),(22,2,'borrowed'),(23,2,'available'),(24,2,'available'),(25,2,'available'),(26,3,'available'),(27,3,'available'),(28,3,'available'),(29,3,'available'),(30,3,'available'),(31,3,'available'),(32,3,'archived'),(33,3,'available'),(34,3,'archived'),(35,3,'available'),(36,4,'archived'),(37,4,'archived'),(38,4,'archived'),(39,4,'archived'),(40,4,'archived'),(41,4,'archived'),(42,4,'archived'),(43,4,'archived'),(44,4,'archived'),(45,4,'archived'),(46,4,'archived'),(47,4,'archived'),(48,4,'archived'),(49,4,'archived'),(50,4,'archived'),(51,5,'lost'),(52,5,'available'),(53,5,'available'),(54,5,'available'),(55,5,'available'),(56,5,'available'),(57,5,'available'),(58,5,'available'),(59,5,'available'),(60,5,'available'),(61,5,'available'),(62,5,'available'),(63,6,'available'),(64,6,'available'),(65,6,'available'),(66,6,'available'),(67,6,'borrowed'),(68,6,'available'),(69,6,'available'),(70,6,'available'),(71,7,'available'),(72,7,'available'),(73,7,'available'),(74,7,'available'),(75,7,'available'),(76,7,'available'),(77,7,'available'),(78,7,'available'),(79,7,'available'),(80,7,'available'),(81,8,'available'),(82,8,'available'),(83,8,'available'),(84,8,'available'),(85,8,'available'),(86,9,'available'),(87,9,'available'),(88,9,'available'),(89,9,'available'),(90,9,'available'),(91,9,'available'),(92,9,'available'),(93,9,'available'),(94,9,'available'),(95,9,'available'),(96,10,'archived'),(97,10,'archived'),(98,10,'archived'),(99,10,'archived'),(100,10,'archived'),(101,10,'archived'),(102,10,'archived'),(103,11,'available'),(104,11,'available'),(105,11,'available'),(106,11,'available'),(107,11,'available'),(108,11,'available'),(109,11,'available'),(110,11,'available'),(111,11,'available'),(112,11,'available'),(123,13,'available'),(124,13,'archived'),(125,13,'archived'),(126,13,'archived'),(127,13,'archived'),(128,13,'archived'),(129,13,'archived'),(130,13,'archived'),(131,13,'archived'),(132,13,'archived'),(133,13,'archived'),(134,13,'archived'),(135,14,'available'),(136,14,'available'),(137,14,'available'),(138,14,'available'),(139,14,'available'),(140,14,'available'),(141,14,'available'),(142,14,'available'),(143,14,'available'),(144,14,'available'),(145,15,'lost'),(146,15,'available'),(147,15,'available'),(148,15,'available'),(149,15,'available'),(150,15,'available'),(151,15,'available'),(152,15,'available'),(153,15,'available'),(154,16,'available'),(155,16,'available'),(156,16,'available'),(157,16,'available'),(158,16,'available'),(159,16,'available'),(160,16,'available'),(161,16,'available'),(162,17,'available'),(163,17,'available'),(164,17,'available'),(165,17,'available'),(166,17,'available'),(167,17,'available'),(168,17,'available'),(169,17,'available'),(170,17,'available'),(171,17,'available'),(172,18,'available'),(173,18,'available'),(174,18,'available'),(175,18,'available'),(176,18,'available'),(177,18,'available'),(178,18,'available'),(179,18,'available'),(180,18,'available'),(181,18,'available'),(182,19,'available'),(183,19,'available'),(184,19,'available'),(185,19,'available'),(186,19,'available'),(187,19,'available'),(188,19,'available'),(189,19,'available'),(190,19,'available'),(191,19,'available'),(192,19,'available'),(193,19,'available'),(194,19,'available'),(195,19,'available'),(196,19,'available'),(197,20,'available'),(198,20,'available'),(199,20,'available'),(200,20,'available'),(201,20,'available'),(202,20,'available'),(203,20,'available'),(204,20,'available'),(205,20,'available'),(206,20,'available'),(207,21,'available'),(208,21,'available'),(209,21,'available'),(210,21,'available'),(211,21,'available'),(212,21,'available'),(213,21,'available'),(214,21,'available'),(215,21,'available'),(216,21,'available'),(217,22,'available'),(218,22,'available'),(219,22,'available'),(220,22,'available'),(221,22,'available'),(222,22,'available'),(223,22,'available'),(224,22,'available'),(225,22,'available'),(226,22,'available'),(227,22,'available'),(228,22,'available'),(229,23,'available'),(230,23,'available'),(231,23,'available'),(232,23,'available'),(233,23,'available'),(234,23,'available'),(235,23,'available'),(236,23,'available'),(237,23,'available'),(238,23,'available'),(239,23,'available'),(240,23,'available'),(242,23,'available'),(243,23,'available'),(244,23,'available'),(245,23,'available'),(246,23,'available'),(247,23,'available'),(248,23,'available'),(249,23,'available'),(250,23,'available'),(251,23,'available'),(252,25,'available'),(253,25,'available'),(254,25,'available'),(255,25,'available'),(256,25,'available'),(257,25,'available'),(258,25,'available'),(259,25,'available'),(260,25,'available'),(261,25,'available'),(262,25,'available'),(263,25,'available'),(264,26,'available'),(265,26,'available'),(266,26,'available'),(267,26,'available'),(268,26,'available'),(269,26,'available'),(270,26,'available'),(271,26,'available'),(272,26,'available'),(273,26,'available'),(274,26,'available'),(275,26,'available'),(278,27,'archived'),(279,27,'archived'),(280,27,'archived'),(281,27,'archived'),(282,28,'available'),(283,28,'available'),(284,28,'available'),(285,18,'available'),(286,18,'available'),(287,29,'available'),(288,29,'available'),(289,29,'available'),(290,29,'available'),(291,29,'available'),(292,29,'available'),(293,29,'available'),(294,29,'available'),(295,29,'available'),(296,29,'available'),(297,29,'available'),(298,29,'available'),(299,27,'archived'),(300,27,'archived');
/*!40000 ALTER TABLE `copies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emp`
--

DROP TABLE IF EXISTS `emp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emp` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emp`
--

LOCK TABLES `emp` WRITE;
/*!40000 ALTER TABLE `emp` DISABLE KEYS */;
INSERT INTO `emp` VALUES (1,'rahul');
/*!40000 ALTER TABLE `emp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanhistory`
--

DROP TABLE IF EXISTS `loanhistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loanhistory` (
  `loan_id` int NOT NULL AUTO_INCREMENT,
  `copy_id` int DEFAULT NULL,
  `member_id` int DEFAULT NULL,
  `borrow_date` date NOT NULL,
  `return_date` date DEFAULT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'borrowed',
  `expected_return_date` date DEFAULT NULL,
  PRIMARY KEY (`loan_id`),
  KEY `copy_id` (`copy_id`),
  KEY `member_id` (`member_id`),
  CONSTRAINT `loanhistory_ibfk_1` FOREIGN KEY (`copy_id`) REFERENCES `copies` (`copy_id`),
  CONSTRAINT `loanhistory_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loanhistory`
--

LOCK TABLES `loanhistory` WRITE;
/*!40000 ALTER TABLE `loanhistory` DISABLE KEYS */;
INSERT INTO `loanhistory` VALUES (1,8,1002,'2024-09-12','2024-11-17','returned','2024-09-26'),(2,3,1003,'2024-09-20',NULL,'borrowed','2024-10-04'),(3,12,1004,'2024-09-30',NULL,'borrowed','2024-10-14'),(4,16,1005,'2024-10-05',NULL,'borrowed','2024-10-19'),(5,22,1006,'2024-10-11',NULL,'borrowed','2024-10-25'),(6,27,1007,'2024-10-15','2024-11-17','returned','2024-10-29'),(7,19,1008,'2024-10-21',NULL,'borrowed','2024-11-04'),(8,30,1009,'2024-10-26',NULL,'borrowed','2024-11-09'),(9,14,1010,'2024-11-01',NULL,'borrowed','2024-11-15'),(10,24,1011,'2024-11-06',NULL,'borrowed','2024-11-20'),(11,1,1002,'2024-09-02','2024-09-10','returned','2024-09-16'),(12,2,1003,'2024-09-05','2024-09-12','returned','2024-09-19'),(13,4,1004,'2024-09-10','2024-09-19','returned','2024-09-24'),(14,5,1005,'2024-09-18','2024-09-27','returned','2024-10-02'),(15,6,1006,'2024-09-25','2024-10-04','returned','2024-10-09'),(16,7,1007,'2024-09-01','2024-09-09','returned','2024-09-14'),(17,10,1008,'2024-09-04','2024-09-14','returned','2024-09-18'),(18,11,1009,'2024-09-12','2024-09-20','returned','2024-09-25'),(19,9,1010,'2024-09-10','2024-09-18','returned','2024-09-23'),(20,13,1011,'2024-10-01','2024-10-10','returned','2024-10-15'),(21,15,1012,'2024-10-02','2024-10-12','returned','2024-10-17'),(22,17,1002,'2024-10-07','2024-10-17','returned','2024-10-22'),(23,18,1003,'2024-10-10','2024-10-20','returned','2024-10-25'),(24,20,1004,'2024-10-12','2024-10-22','returned','2024-10-27'),(25,21,1005,'2024-10-14','2024-10-24','returned','2024-10-29'),(26,23,1006,'2024-10-16','2024-10-26','returned','2024-10-31'),(27,25,1007,'2024-10-18','2024-10-28','returned','2024-11-02'),(28,26,1008,'2024-10-20','2024-10-30','returned','2024-11-04'),(29,28,1009,'2024-10-22','2024-11-01','returned','2024-11-06'),(30,29,1010,'2024-10-24','2024-11-03','returned','2024-11-08'),(31,51,1002,'2024-11-17',NULL,'lost','2024-11-24'),(32,123,1002,'2024-11-17','2024-11-17','returned','2024-11-24'),(33,123,1002,'2024-10-14','2024-11-17','returned','2024-11-24'),(34,67,1002,'2024-10-14','2024-11-17','returned','2024-11-24'),(35,67,1002,'2024-10-03',NULL,'borrowed','2024-11-24'),(36,86,1007,'2024-11-17','2024-11-17','returned','2024-11-24'),(37,78,1006,'2024-10-14','2024-11-17','returned','2024-10-21'),(38,145,1006,'2024-11-17',NULL,'lost','2024-11-24');
/*!40000 ALTER TABLE `loanhistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `member_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `balance` decimal(10,2) DEFAULT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'active',
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1018 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES (1002,'Rohan Patil','rohan@gmail.com','8208757572','Kabnur','Rohan@7572',802.00,'inactive'),(1003,'Aarav Sharma','aarav.sharma@gmail.com','9876543210','123, Green Street, Mumbai','pass1234',2500.00,'active'),(1004,'Ishita Gupta','Ishita@gmail.com','9876543211','45, Lakeview Road, Delhi','pass1234',1200.00,'active'),(1005,'Rohan Mehta','rohan.mehta@gmail.com','9876543212','67, Hilltop Avenue, Bangalore','pass1234',1800.00,'active'),(1006,'Ananya Kapoor','ananya.kapoor@gmail.com','9876543213','89, Riverbend Road, Kolkata','pass1234',2530.00,'inactive'),(1007,'Kabir Khan','kabir123@gmail.com','9877899877','Kamatipura','Kabir@123',2522.00,'inactive'),(1008,'Meera Iyer','meera.iyer@gmail.com','9876543215','45, Banyan Street, Chennai','pass1234',1400.00,'active'),(1009,'Akash Patil','aditya.bansal@gmail.com','9876543216','78, Parkside Drive, Jaipur','pass1234',2200.00,'active'),(1010,'Pooja Nair','pooja.nair@gmail.com','9876543217','34, Lotus Court, Hyderabad','pass1234',3000.00,'inactive'),(1011,'Aryan Singh','aryan.singh@gmail.com','9876543218','56, Maple Drive, Lucknow','pass1234',1100.00,'active'),(1012,'Rohan Kamble','rohan.code@gmail.com','9876545678','Panhala','pass1234',2711.00,'active'),(1013,'Rahul Shelake','rahulshelake.connect@gmail.com','8605126831','Donoli, Kolhapur','Rahul@123',1212.00,'active'),(1014,'Abhi Ghadage','abhi.ghadage123@gmail.com','9876565432','Satara','Abhi@123',1232.00,'active'),(1015,'Rahul Kumar','rahul.kumar@gmail.com','9879879879','Satara','Rahul.Gote',2234.00,'active'),(1016,'Ram Kaur','ram.@gmail.com','9876549876','Panhala','Ram@123',1232.00,'active'),(1017,'Ganesh Shiye','ganesh@gmail.com','8976546782','Satara','Ganesh@123',1234.00,'inactive');
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-19 12:57:12
