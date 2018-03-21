start transaction;

drop database if exists `Acme-Explorer`;

create database `Acme-Explorer`;

use `Acme-Explorer`;

drop user 'acme-user';
drop user 'acme-manager';

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete
	on `Acme-Explorer`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `Acme-Explorer`.* to 'acme-manager'@'%';


-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: Acme-Explorer
-- ------------------------------------------------------
-- Server version	5.5.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cgls5lrufx91ufsyh467spwa3` (`userAccount_id`),
  CONSTRAINT `FK_cgls5lrufx91ufsyh467spwa3` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor`
--

LOCK TABLES `actor` WRITE;
/*!40000 ALTER TABLE `actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_idt4b4u259p6vs4pyr9lax4eg` (`userAccount_id`),
  CONSTRAINT `FK_idt4b4u259p6vs4pyr9lax4eg` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (24,0,'Calle Fontenla, 2','ponsavi@acme.org','Pepón','+34 (545) 6565','Samper Villagrán','\0',23);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cancelReason` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `creationMoment` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creditCard_id` int(11) DEFAULT NULL,
  `explorer_id` int(11) NOT NULL,
  `trip_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_itesolowt3qc6vnn6e7gf6e33` (`creditCard_id`),
  KEY `FK_hpdjjcbp26hxonyuhcrnyemi4` (`explorer_id`),
  KEY `FK_aa8ob0qnub9wp8splrk7qoh0t` (`trip_id`),
  CONSTRAINT `FK_aa8ob0qnub9wp8splrk7qoh0t` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_hpdjjcbp26hxonyuhcrnyemi4` FOREIGN KEY (`explorer_id`) REFERENCES `explorer` (`id`),
  CONSTRAINT `FK_itesolowt3qc6vnn6e7gf6e33` FOREIGN KEY (`creditCard_id`) REFERENCES `creditcard` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit`
--

DROP TABLE IF EXISTS `audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `auditionMoment` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `savedMode` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `auditor_id` int(11) DEFAULT NULL,
  `trip_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_il9mw2gmji4slnu1l80k8v0wq` (`auditor_id`),
  KEY `FK_pkloytagfwgpgmuytio9spx1x` (`trip_id`),
  CONSTRAINT `FK_pkloytagfwgpgmuytio9spx1x` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_il9mw2gmji4slnu1l80k8v0wq` FOREIGN KEY (`auditor_id`) REFERENCES `auditor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit`
--

LOCK TABLES `audit` WRITE;
/*!40000 ALTER TABLE `audit` DISABLE KEYS */;
/*!40000 ALTER TABLE `audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit_attachmenturls`
--

DROP TABLE IF EXISTS `audit_attachmenturls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit_attachmenturls` (
  `Audit_id` int(11) NOT NULL,
  `attachmentURLs` varchar(255) DEFAULT NULL,
  KEY `FK_6nswdfhv4yin7825ccd7gpbmh` (`Audit_id`),
  CONSTRAINT `FK_6nswdfhv4yin7825ccd7gpbmh` FOREIGN KEY (`Audit_id`) REFERENCES `audit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_attachmenturls`
--

LOCK TABLES `audit_attachmenturls` WRITE;
/*!40000 ALTER TABLE `audit_attachmenturls` DISABLE KEYS */;
/*!40000 ALTER TABLE `audit_attachmenturls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditor`
--

DROP TABLE IF EXISTS `auditor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_img3kaubb62u0h0jujkh1tejo` (`userAccount_id`),
  CONSTRAINT `FK_img3kaubb62u0h0jujkh1tejo` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditor`
--

LOCK TABLES `auditor` WRITE;
/*!40000 ALTER TABLE `auditor` DISABLE KEYS */;
/*!40000 ALTER TABLE `auditor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parentCategory_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3v34vcvwua46xp9jd0bj7rk78` (`parentCategory_id`),
  CONSTRAINT `FK_3v34vcvwua46xp9jd0bj7rk78` FOREIGN KEY (`parentCategory_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (25,0,'CATEGORY',NULL),(26,0,'Safari',25),(27,0,'Ground',26),(28,0,'Savana',27),(29,0,'Mountain',27),(30,0,'Water',25),(31,0,'Lake',30),(32,0,'River',30),(33,0,'Climbing',25),(34,0,'Mountain',33),(35,0,'River',33);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `VATTax` double NOT NULL,
  `bannerURL` varchar(255) DEFAULT NULL,
  `countryCode` varchar(255) DEFAULT NULL,
  `finderCached` double NOT NULL,
  `finderReturn` int(11) NOT NULL,
  `welcomeMessageEN` varchar(255) DEFAULT NULL,
  `welcomeMessageES` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration`
--

LOCK TABLES `configuration` WRITE;
/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
INSERT INTO `configuration` VALUES (36,0,21,'http://creek-tours.com/wp-content/uploads/Kenya-Tanzania-Family-Safari-banner.jpg','+34',1,10,'Tanzanika is an adventure company that makes your explorer\'s dreams true','Tanzanika es la empresa de aventuras que hará tus sueños de explorador realidad');
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration_spamwords`
--

DROP TABLE IF EXISTS `configuration_spamwords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration_spamwords` (
  `Configuration_id` int(11) NOT NULL,
  `spamWords` varchar(255) DEFAULT NULL,
  KEY `FK_71ono8c3475iofflg3gestqeq` (`Configuration_id`),
  CONSTRAINT `FK_71ono8c3475iofflg3gestqeq` FOREIGN KEY (`Configuration_id`) REFERENCES `configuration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration_spamwords`
--

LOCK TABLES `configuration_spamwords` WRITE;
/*!40000 ALTER TABLE `configuration_spamwords` DISABLE KEYS */;
INSERT INTO `configuration_spamwords` VALUES (36,'viagra'),(36,'cialis'),(36,'sex'),(36,'jes extender');
/*!40000 ALTER TABLE `configuration_spamwords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creditcard`
--

DROP TABLE IF EXISTS `creditcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creditcard` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `CVV` int(11) NOT NULL,
  `brandName` varchar(255) DEFAULT NULL,
  `expirationMonth` int(11) NOT NULL,
  `expirationYear` int(11) NOT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creditcard`
--

LOCK TABLES `creditcard` WRITE;
/*!40000 ALTER TABLE `creditcard` DISABLE KEYS */;
/*!40000 ALTER TABLE `creditcard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum`
--

DROP TABLE IF EXISTS `curriculum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `personalRecord_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mabrm092fo1ae2t2m5a3ww4en` (`personalRecord_id`),
  UNIQUE KEY `UK_cefwvqc4ixh7s51wfd1yi77yg` (`ticker`),
  CONSTRAINT `FK_mabrm092fo1ae2t2m5a3ww4en` FOREIGN KEY (`personalRecord_id`) REFERENCES `personalrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum`
--

LOCK TABLES `curriculum` WRITE;
/*!40000 ALTER TABLE `curriculum` DISABLE KEYS */;
/*!40000 ALTER TABLE `curriculum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_educationrecord`
--

DROP TABLE IF EXISTS `curriculum_educationrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_educationrecord` (
  `Curriculum_id` int(11) NOT NULL,
  `educationRecords_id` int(11) NOT NULL,
  UNIQUE KEY `UK_pl4rfqren0agff76y04l3y611` (`educationRecords_id`),
  KEY `FK_oldens4o8aaajbroxyv11fo6g` (`Curriculum_id`),
  CONSTRAINT `FK_oldens4o8aaajbroxyv11fo6g` FOREIGN KEY (`Curriculum_id`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_pl4rfqren0agff76y04l3y611` FOREIGN KEY (`educationRecords_id`) REFERENCES `educationrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_educationrecord`
--

LOCK TABLES `curriculum_educationrecord` WRITE;
/*!40000 ALTER TABLE `curriculum_educationrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `curriculum_educationrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_endorserrecord`
--

DROP TABLE IF EXISTS `curriculum_endorserrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_endorserrecord` (
  `Curriculum_id` int(11) NOT NULL,
  `endorserRecords_id` int(11) NOT NULL,
  UNIQUE KEY `UK_bbelpqhyh6qocyclhn133ndyh` (`endorserRecords_id`),
  KEY `FK_o79rcsgjmwauctqvj9b7i4ukb` (`Curriculum_id`),
  CONSTRAINT `FK_o79rcsgjmwauctqvj9b7i4ukb` FOREIGN KEY (`Curriculum_id`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_bbelpqhyh6qocyclhn133ndyh` FOREIGN KEY (`endorserRecords_id`) REFERENCES `endorserrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_endorserrecord`
--

LOCK TABLES `curriculum_endorserrecord` WRITE;
/*!40000 ALTER TABLE `curriculum_endorserrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `curriculum_endorserrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_miscellaneousrecord`
--

DROP TABLE IF EXISTS `curriculum_miscellaneousrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_miscellaneousrecord` (
  `Curriculum_id` int(11) NOT NULL,
  `miscellaneousRecords_id` int(11) NOT NULL,
  UNIQUE KEY `UK_i87gspje3t3kah740fj2aup8` (`miscellaneousRecords_id`),
  KEY `FK_qq8tboyasceyejxb5gtnyhlbx` (`Curriculum_id`),
  CONSTRAINT `FK_qq8tboyasceyejxb5gtnyhlbx` FOREIGN KEY (`Curriculum_id`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_i87gspje3t3kah740fj2aup8` FOREIGN KEY (`miscellaneousRecords_id`) REFERENCES `miscellaneousrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_miscellaneousrecord`
--

LOCK TABLES `curriculum_miscellaneousrecord` WRITE;
/*!40000 ALTER TABLE `curriculum_miscellaneousrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `curriculum_miscellaneousrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_professionalrecord`
--

DROP TABLE IF EXISTS `curriculum_professionalrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_professionalrecord` (
  `Curriculum_id` int(11) NOT NULL,
  `professionalRecords_id` int(11) NOT NULL,
  UNIQUE KEY `UK_fmlxwyi8ktscvd4hflcg2xerj` (`professionalRecords_id`),
  KEY `FK_cxve92cmijecu4e9ev01pp3wc` (`Curriculum_id`),
  CONSTRAINT `FK_cxve92cmijecu4e9ev01pp3wc` FOREIGN KEY (`Curriculum_id`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_fmlxwyi8ktscvd4hflcg2xerj` FOREIGN KEY (`professionalRecords_id`) REFERENCES `professionalrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_professionalrecord`
--

LOCK TABLES `curriculum_professionalrecord` WRITE;
/*!40000 ALTER TABLE `curriculum_professionalrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `curriculum_professionalrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `educationrecord`
--

DROP TABLE IF EXISTS `educationrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `educationrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachmentURL` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `diplomaTitle` varchar(255) DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `institutionName` varchar(255) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `educationrecord`
--

LOCK TABLES `educationrecord` WRITE;
/*!40000 ALTER TABLE `educationrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `educationrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endorserrecord`
--

DROP TABLE IF EXISTS `endorserrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `endorserrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullName` varchar(255) DEFAULT NULL,
  `linkedInProfileUrl` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endorserrecord`
--

LOCK TABLES `endorserrecord` WRITE;
/*!40000 ALTER TABLE `endorserrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `endorserrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `explorer`
--

DROP TABLE IF EXISTS `explorer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `explorer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fjs8q8hlbsegiuxw4r2ko6uv6` (`userAccount_id`),
  CONSTRAINT `FK_fjs8q8hlbsegiuxw4r2ko6uv6` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `explorer`
--

LOCK TABLES `explorer` WRITE;
/*!40000 ALTER TABLE `explorer` DISABLE KEYS */;
/*!40000 ALTER TABLE `explorer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `explorer_contact`
--

DROP TABLE IF EXISTS `explorer_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `explorer_contact` (
  `Explorer_id` int(11) NOT NULL,
  `contacts_id` int(11) NOT NULL,
  UNIQUE KEY `UK_3y1w0b2iyp1wh1d92r98ihphl` (`contacts_id`),
  KEY `FK_rqxiiawi37nkq73w14m2i8x6l` (`Explorer_id`),
  CONSTRAINT `FK_rqxiiawi37nkq73w14m2i8x6l` FOREIGN KEY (`Explorer_id`) REFERENCES `explorer` (`id`),
  CONSTRAINT `FK_3y1w0b2iyp1wh1d92r98ihphl` FOREIGN KEY (`contacts_id`) REFERENCES `contact` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `explorer_contact`
--

LOCK TABLES `explorer_contact` WRITE;
/*!40000 ALTER TABLE `explorer_contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `explorer_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder`
--

DROP TABLE IF EXISTS `finder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `endDate` date DEFAULT NULL,
  `keyWord` varchar(255) DEFAULT NULL,
  `lastSearchDate` datetime DEFAULT NULL,
  `maxPrice` double DEFAULT NULL,
  `minPrice` double DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `explorer_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_p2le5jsa454av0jdhoxxt7iwj` (`explorer_id`),
  CONSTRAINT `FK_p2le5jsa454av0jdhoxxt7iwj` FOREIGN KEY (`explorer_id`) REFERENCES `explorer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder`
--

LOCK TABLES `finder` WRITE;
/*!40000 ALTER TABLE `finder` DISABLE KEYS */;
/*!40000 ALTER TABLE `finder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder_trip`
--

DROP TABLE IF EXISTS `finder_trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder_trip` (
  `Finder_id` int(11) NOT NULL,
  `trips_id` int(11) NOT NULL,
  KEY `FK_banf3cvq0wb1mhnq645alwstq` (`trips_id`),
  KEY `FK_5c35eq4ikvgxcjfyq4g1l0g3e` (`Finder_id`),
  CONSTRAINT `FK_5c35eq4ikvgxcjfyq4g1l0g3e` FOREIGN KEY (`Finder_id`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_banf3cvq0wb1mhnq645alwstq` FOREIGN KEY (`trips_id`) REFERENCES `trip` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder_trip`
--

LOCK TABLES `finder_trip` WRITE;
/*!40000 ALTER TABLE `finder_trip` DISABLE KEYS */;
/*!40000 ALTER TABLE `finder_trip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `systemFolder` bit(1) DEFAULT NULL,
  `actor_id` int(11) NOT NULL,
  `parentFolder_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_qshunmxjrgbdahcn1eu357sxt` (`parentFolder_id`),
  CONSTRAINT `FK_qshunmxjrgbdahcn1eu357sxt` FOREIGN KEY (`parentFolder_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder`
--

LOCK TABLES `folder` WRITE;
/*!40000 ALTER TABLE `folder` DISABLE KEYS */;
INSERT INTO `folder` VALUES (37,0,'in box','',24,NULL),(38,0,'out box','',24,NULL),(39,0,'notification box','',24,NULL),(40,0,'trash box','',24,NULL),(41,0,'spam box','',24,NULL);
/*!40000 ALTER TABLE `folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder_message`
--

DROP TABLE IF EXISTS `folder_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder_message` (
  `Folder_id` int(11) NOT NULL,
  `messages_id` int(11) NOT NULL,
  KEY `FK_5nh3mwey9bw25ansh2thcbcdh` (`messages_id`),
  KEY `FK_dwna03p0i8so6ov91ouups81r` (`Folder_id`),
  CONSTRAINT `FK_dwna03p0i8so6ov91ouups81r` FOREIGN KEY (`Folder_id`) REFERENCES `folder` (`id`),
  CONSTRAINT `FK_5nh3mwey9bw25ansh2thcbcdh` FOREIGN KEY (`messages_id`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder_message`
--

LOCK TABLES `folder_message` WRITE;
/*!40000 ALTER TABLE `folder_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `folder_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder_notification`
--

DROP TABLE IF EXISTS `folder_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder_notification` (
  `Folder_id` int(11) NOT NULL,
  `notifications_id` int(11) NOT NULL,
  UNIQUE KEY `UK_mnj72jgwmv0ik4fxuncjafa08` (`notifications_id`),
  KEY `FK_kmi6422d1rbmbxdngb0win3lx` (`Folder_id`),
  CONSTRAINT `FK_kmi6422d1rbmbxdngb0win3lx` FOREIGN KEY (`Folder_id`) REFERENCES `folder` (`id`),
  CONSTRAINT `FK_mnj72jgwmv0ik4fxuncjafa08` FOREIGN KEY (`notifications_id`) REFERENCES `notification` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder_notification`
--

LOCK TABLES `folder_notification` WRITE;
/*!40000 ALTER TABLE `folder_notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `folder_notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `legaltext`
--

DROP TABLE IF EXISTS `legaltext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `legaltext` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `applicableLaws` varchar(255) DEFAULT NULL,
  `body` varchar(255) DEFAULT NULL,
  `moment` date DEFAULT NULL,
  `savedMode` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `legaltext`
--

LOCK TABLES `legaltext` WRITE;
/*!40000 ALTER TABLE `legaltext` DISABLE KEYS */;
/*!40000 ALTER TABLE `legaltext` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `legaltext_trip`
--

DROP TABLE IF EXISTS `legaltext_trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `legaltext_trip` (
  `LegalText_id` int(11) NOT NULL,
  `trips_id` int(11) NOT NULL,
  UNIQUE KEY `UK_nhe46krpx0p82b2bfg4rfivtl` (`trips_id`),
  KEY `FK_euperohyjlo853196aj98q7k0` (`LegalText_id`),
  CONSTRAINT `FK_euperohyjlo853196aj98q7k0` FOREIGN KEY (`LegalText_id`) REFERENCES `legaltext` (`id`),
  CONSTRAINT `FK_nhe46krpx0p82b2bfg4rfivtl` FOREIGN KEY (`trips_id`) REFERENCES `trip` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `legaltext_trip`
--

LOCK TABLES `legaltext_trip` WRITE;
/*!40000 ALTER TABLE `legaltext_trip` DISABLE KEYS */;
/*!40000 ALTER TABLE `legaltext_trip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_84bmmxlq61tiaoc7dy7kdcghh` (`userAccount_id`),
  CONSTRAINT `FK_84bmmxlq61tiaoc7dy7kdcghh` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager_note`
--

DROP TABLE IF EXISTS `manager_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager_note` (
  `Manager_id` int(11) NOT NULL,
  `notes_id` int(11) NOT NULL,
  UNIQUE KEY `UK_4qmynv06mkqshielxb0ew8xms` (`notes_id`),
  KEY `FK_4q6xbyc1s016wr6iiyjsnlj6b` (`Manager_id`),
  CONSTRAINT `FK_4q6xbyc1s016wr6iiyjsnlj6b` FOREIGN KEY (`Manager_id`) REFERENCES `manager` (`id`),
  CONSTRAINT `FK_4qmynv06mkqshielxb0ew8xms` FOREIGN KEY (`notes_id`) REFERENCES `note` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager_note`
--

LOCK TABLES `manager_note` WRITE;
/*!40000 ALTER TABLE `manager_note` DISABLE KEYS */;
/*!40000 ALTER TABLE `manager_note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager_survivalclass`
--

DROP TABLE IF EXISTS `manager_survivalclass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager_survivalclass` (
  `Manager_id` int(11) NOT NULL,
  `survivalClasses_id` int(11) NOT NULL,
  UNIQUE KEY `UK_ialxbswb11xwicwj88dj3mnin` (`survivalClasses_id`),
  KEY `FK_2k803htukyqf8omvqg9ndnpvy` (`Manager_id`),
  CONSTRAINT `FK_2k803htukyqf8omvqg9ndnpvy` FOREIGN KEY (`Manager_id`) REFERENCES `manager` (`id`),
  CONSTRAINT `FK_ialxbswb11xwicwj88dj3mnin` FOREIGN KEY (`survivalClasses_id`) REFERENCES `survivalclass` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager_survivalclass`
--

LOCK TABLES `manager_survivalclass` WRITE;
/*!40000 ALTER TABLE `manager_survivalclass` DISABLE KEYS */;
/*!40000 ALTER TABLE `manager_survivalclass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager_trip`
--

DROP TABLE IF EXISTS `manager_trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager_trip` (
  `Manager_id` int(11) NOT NULL,
  `trips_id` int(11) NOT NULL,
  UNIQUE KEY `UK_r8ejm8lgphhyss57tdl3omf25` (`trips_id`),
  KEY `FK_jkrem3umeepnuuqd9nkl63dgb` (`Manager_id`),
  CONSTRAINT `FK_jkrem3umeepnuuqd9nkl63dgb` FOREIGN KEY (`Manager_id`) REFERENCES `manager` (`id`),
  CONSTRAINT `FK_r8ejm8lgphhyss57tdl3omf25` FOREIGN KEY (`trips_id`) REFERENCES `trip` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager_trip`
--

LOCK TABLES `manager_trip` WRITE;
/*!40000 ALTER TABLE `manager_trip` DISABLE KEYS */;
/*!40000 ALTER TABLE `manager_trip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `moment` date DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `recipient_id` int(11) DEFAULT NULL,
  `sender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miscellaneousrecord`
--

DROP TABLE IF EXISTS `miscellaneousrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneousrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachmentURL` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneousrecord`
--

LOCK TABLES `miscellaneousrecord` WRITE;
/*!40000 ALTER TABLE `miscellaneousrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `miscellaneousrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `note`
--

DROP TABLE IF EXISTS `note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `note` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `creationMoment` date DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `auditor_id` int(11) DEFAULT NULL,
  `trip_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_c3f5vlsshwqtjo5f74rfs8gm4` (`auditor_id`),
  KEY `FK_phngbfwt9o5v49bpgsb9mkv66` (`trip_id`),
  CONSTRAINT `FK_phngbfwt9o5v49bpgsb9mkv66` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_c3f5vlsshwqtjo5f74rfs8gm4` FOREIGN KEY (`auditor_id`) REFERENCES `auditor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `note`
--

LOCK TABLES `note` WRITE;
/*!40000 ALTER TABLE `note` DISABLE KEYS */;
/*!40000 ALTER TABLE `note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `sender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_38ratii6djlvicgrn6j64yg87` (`sender_id`),
  CONSTRAINT `FK_38ratii6djlvicgrn6j64yg87` FOREIGN KEY (`sender_id`) REFERENCES `administrator` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personalrecord`
--

DROP TABLE IF EXISTS `personalrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personalrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullName` varchar(255) DEFAULT NULL,
  `linkedInProfileUrl` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `photoUrl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personalrecord`
--

LOCK TABLES `personalrecord` WRITE;
/*!40000 ALTER TABLE `personalrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `personalrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professionalrecord`
--

DROP TABLE IF EXISTS `professionalrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professionalrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachmentURL` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `companyName` varchar(255) DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `playedRole` varchar(255) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professionalrecord`
--

LOCK TABLES `professionalrecord` WRITE;
/*!40000 ALTER TABLE `professionalrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `professionalrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ranger`
--

DROP TABLE IF EXISTS `ranger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ranger` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  `curriculum_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_axw07q0mobub2mvr0xeh7al5f` (`userAccount_id`),
  KEY `FK_dbxchym8y9qceta3m1sn0cg6r` (`curriculum_id`),
  CONSTRAINT `FK_axw07q0mobub2mvr0xeh7al5f` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`),
  CONSTRAINT `FK_dbxchym8y9qceta3m1sn0cg6r` FOREIGN KEY (`curriculum_id`) REFERENCES `curriculum` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ranger`
--

LOCK TABLES `ranger` WRITE;
/*!40000 ALTER TABLE `ranger` DISABLE KEYS */;
/*!40000 ALTER TABLE `ranger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reply`
--

DROP TABLE IF EXISTS `reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reply` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `moment` date DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `manager_id` int(11) NOT NULL,
  `note_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dpuap5iwyj2memfx2ie3ujt7d` (`note_id`),
  KEY `FK_75ox4qihnsl60aagrhkbw5ifu` (`manager_id`),
  CONSTRAINT `FK_dpuap5iwyj2memfx2ie3ujt7d` FOREIGN KEY (`note_id`) REFERENCES `note` (`id`),
  CONSTRAINT `FK_75ox4qihnsl60aagrhkbw5ifu` FOREIGN KEY (`manager_id`) REFERENCES `manager` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reply`
--

LOCK TABLES `reply` WRITE;
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
/*!40000 ALTER TABLE `reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socialidentity`
--

DROP TABLE IF EXISTS `socialidentity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socialidentity` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `photoUrl` varchar(255) DEFAULT NULL,
  `profileLink` varchar(255) DEFAULT NULL,
  `socialNetworkName` varchar(255) DEFAULT NULL,
  `actor_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socialidentity`
--

LOCK TABLES `socialidentity` WRITE;
/*!40000 ALTER TABLE `socialidentity` DISABLE KEYS */;
/*!40000 ALTER TABLE `socialidentity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsor`
--

DROP TABLE IF EXISTS `sponsor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_okfx8h0cn4eidh8ng563vowjc` (`userAccount_id`),
  CONSTRAINT `FK_okfx8h0cn4eidh8ng563vowjc` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsor`
--

LOCK TABLES `sponsor` WRITE;
/*!40000 ALTER TABLE `sponsor` DISABLE KEYS */;
/*!40000 ALTER TABLE `sponsor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsorship`
--

DROP TABLE IF EXISTS `sponsorship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsorship` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `bannerURL` varchar(255) DEFAULT NULL,
  `infoPageLink` varchar(255) DEFAULT NULL,
  `creditCard_id` int(11) NOT NULL,
  `sponsor_id` int(11) NOT NULL,
  `trip_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_pp2g6qyk49qojvpry77wsm8b3` (`creditCard_id`),
  KEY `FK_e3idyfyffpufog3sjl3c2ulun` (`sponsor_id`),
  KEY `FK_p6ydvo1r10eo68cj18xvbx37q` (`trip_id`),
  CONSTRAINT `FK_p6ydvo1r10eo68cj18xvbx37q` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_e3idyfyffpufog3sjl3c2ulun` FOREIGN KEY (`sponsor_id`) REFERENCES `sponsor` (`id`),
  CONSTRAINT `FK_pp2g6qyk49qojvpry77wsm8b3` FOREIGN KEY (`creditCard_id`) REFERENCES `creditcard` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsorship`
--

LOCK TABLES `sponsorship` WRITE;
/*!40000 ALTER TABLE `sponsorship` DISABLE KEYS */;
/*!40000 ALTER TABLE `sponsorship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stage`
--

DROP TABLE IF EXISTS `stage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stage` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `trip_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_g8uuphy1i55oxqtlti7cftxk5` (`trip_id`),
  CONSTRAINT `FK_g8uuphy1i55oxqtlti7cftxk5` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stage`
--

LOCK TABLES `stage` WRITE;
/*!40000 ALTER TABLE `stage` DISABLE KEYS */;
/*!40000 ALTER TABLE `stage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `story`
--

DROP TABLE IF EXISTS `story`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `story` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `explorer_id` int(11) NOT NULL,
  `trip_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gfxb1n7yg00bo5w5rg2gphd82` (`explorer_id`),
  KEY `FK_h0duh0rohxwpscrd7e89cnsgv` (`trip_id`),
  CONSTRAINT `FK_h0duh0rohxwpscrd7e89cnsgv` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_gfxb1n7yg00bo5w5rg2gphd82` FOREIGN KEY (`explorer_id`) REFERENCES `explorer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `story`
--

LOCK TABLES `story` WRITE;
/*!40000 ALTER TABLE `story` DISABLE KEYS */;
/*!40000 ALTER TABLE `story` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `story_attachmenturls`
--

DROP TABLE IF EXISTS `story_attachmenturls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `story_attachmenturls` (
  `Story_id` int(11) NOT NULL,
  `attachmentURLs` varchar(255) DEFAULT NULL,
  KEY `FK_358hkev3f4ecjug5h1xbbd3c8` (`Story_id`),
  CONSTRAINT `FK_358hkev3f4ecjug5h1xbbd3c8` FOREIGN KEY (`Story_id`) REFERENCES `story` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `story_attachmenturls`
--

LOCK TABLES `story_attachmenturls` WRITE;
/*!40000 ALTER TABLE `story_attachmenturls` DISABLE KEYS */;
/*!40000 ALTER TABLE `story_attachmenturls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survivalclass`
--

DROP TABLE IF EXISTS `survivalclass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survivalclass` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `coordinates` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `organizationDate` date DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `trip_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_kgmsndege9b3pa1f2gce9hb40` (`trip_id`),
  CONSTRAINT `FK_kgmsndege9b3pa1f2gce9hb40` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survivalclass`
--

LOCK TABLES `survivalclass` WRITE;
/*!40000 ALTER TABLE `survivalclass` DISABLE KEYS */;
/*!40000 ALTER TABLE `survivalclass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survivalclass_explorer`
--

DROP TABLE IF EXISTS `survivalclass_explorer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survivalclass_explorer` (
  `SurvivalClass_id` int(11) NOT NULL,
  `explorers_id` int(11) NOT NULL,
  KEY `FK_7i2xgij2tq2yt7rj87jj5obgx` (`explorers_id`),
  KEY `FK_3sdn7xxvqfm886k6uvcmbnu7q` (`SurvivalClass_id`),
  CONSTRAINT `FK_3sdn7xxvqfm886k6uvcmbnu7q` FOREIGN KEY (`SurvivalClass_id`) REFERENCES `survivalclass` (`id`),
  CONSTRAINT `FK_7i2xgij2tq2yt7rj87jj5obgx` FOREIGN KEY (`explorers_id`) REFERENCES `explorer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survivalclass_explorer`
--

LOCK TABLES `survivalclass_explorer` WRITE;
/*!40000 ALTER TABLE `survivalclass_explorer` DISABLE KEYS */;
/*!40000 ALTER TABLE `survivalclass_explorer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (42,0,'country'),(43,0,'expertise'),(44,0,'dangerousness');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip`
--

DROP TABLE IF EXISTS `trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trip` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cancelReason` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `explorerRequirements` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `publicationDate` date DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `category_id` int(11) NOT NULL,
  `legalText_id` int(11) DEFAULT NULL,
  `ranger_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ugq799eb7dgmgmqjoxkjpx5k` (`ticker`),
  KEY `FK_mdljo2c0aixmrcx025mvfkc3x` (`category_id`),
  KEY `FK_ol2sss4qsbxlb5rhdynrl33wy` (`legalText_id`),
  KEY `FK_65bd0l9xa0l1x5qnjg2su9tig` (`ranger_id`),
  CONSTRAINT `FK_65bd0l9xa0l1x5qnjg2su9tig` FOREIGN KEY (`ranger_id`) REFERENCES `ranger` (`id`),
  CONSTRAINT `FK_mdljo2c0aixmrcx025mvfkc3x` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FK_ol2sss4qsbxlb5rhdynrl33wy` FOREIGN KEY (`legalText_id`) REFERENCES `legaltext` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip`
--

LOCK TABLES `trip` WRITE;
/*!40000 ALTER TABLE `trip` DISABLE KEYS */;
/*!40000 ALTER TABLE `trip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip_value`
--

DROP TABLE IF EXISTS `trip_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trip_value` (
  `Trip_id` int(11) NOT NULL,
  `values_id` int(11) NOT NULL,
  KEY `FK_2fflrgceelmkps17lth71qpjn` (`values_id`),
  KEY `FK_8fr2iin105jp2lk4uyx17rkmr` (`Trip_id`),
  CONSTRAINT `FK_8fr2iin105jp2lk4uyx17rkmr` FOREIGN KEY (`Trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_2fflrgceelmkps17lth71qpjn` FOREIGN KEY (`values_id`) REFERENCES `value` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip_value`
--

LOCK TABLES `trip_value` WRITE;
/*!40000 ALTER TABLE `trip_value` DISABLE KEYS */;
/*!40000 ALTER TABLE `trip_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (23,0,'','21232f297a57a5a743894a0e4a801fc3','admin');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount_authorities`
--

DROP TABLE IF EXISTS `useraccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount_authorities`
--

LOCK TABLES `useraccount_authorities` WRITE;
/*!40000 ALTER TABLE `useraccount_authorities` DISABLE KEYS */;
INSERT INTO `useraccount_authorities` VALUES (23,'ADMIN');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `value`
--

DROP TABLE IF EXISTS `value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `value` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gshaqbur2cof541isdpqw7fhs` (`tag_id`),
  CONSTRAINT `FK_gshaqbur2cof541isdpqw7fhs` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `value`
--

LOCK TABLES `value` WRITE;
/*!40000 ALTER TABLE `value` DISABLE KEYS */;
/*!40000 ALTER TABLE `value` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-17 18:43:31

commit;
