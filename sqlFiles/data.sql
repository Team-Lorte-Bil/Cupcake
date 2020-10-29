/*
 Navicat Premium Data Transfer

 Source Server         : Localhost
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : Cupcake

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 29/10/2020 11:29:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for CakeBottoms
-- ----------------------------
DROP TABLE IF EXISTS `CakeBottoms`;
CREATE TABLE `CakeBottoms` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_danish_ci DEFAULT NULL,
  `price` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;

-- ----------------------------
-- Records of CakeBottoms
-- ----------------------------
BEGIN;
INSERT INTO `CakeBottoms` VALUES (1, 'Chokolade', 5.00);
INSERT INTO `CakeBottoms` VALUES (2, 'Vanilje', 5.00);
INSERT INTO `CakeBottoms` VALUES (3, 'Nødder', 5.00);
INSERT INTO `CakeBottoms` VALUES (4, 'Pistacie', 6.00);
INSERT INTO `CakeBottoms` VALUES (5, 'Mandel', 7.00);
COMMIT;

-- ----------------------------
-- Table structure for CakesOnOrder
-- ----------------------------
DROP TABLE IF EXISTS `CakesOnOrder`;
CREATE TABLE `CakesOnOrder` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderId` int DEFAULT NULL,
  `bottomId` int DEFAULT NULL,
  `toppingId` int DEFAULT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `CakeIdOnOrderId` (`orderId`),
  KEY `BottomIdOnCakeId` (`bottomId`),
  KEY `ToppingIdOnCakeId` (`toppingId`),
  CONSTRAINT `BottomIdOnCakeId` FOREIGN KEY (`bottomId`) REFERENCES `CakeBottoms` (`id`),
  CONSTRAINT `CakeIdOnOrderId` FOREIGN KEY (`orderId`) REFERENCES `Orders` (`id`),
  CONSTRAINT `ToppingIdOnCakeId` FOREIGN KEY (`toppingId`) REFERENCES `CakeToppings` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;

-- ----------------------------
-- Records of CakesOnOrder
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for CakeToppings
-- ----------------------------
DROP TABLE IF EXISTS `CakeToppings`;
CREATE TABLE `CakeToppings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_danish_ci DEFAULT NULL,
  `price` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;

-- ----------------------------
-- Records of CakeToppings
-- ----------------------------
BEGIN;
INSERT INTO `CakeToppings` VALUES (1, 'Chokolade', 5.00);
INSERT INTO `CakeToppings` VALUES (2, 'Blåbær', 5.00);
INSERT INTO `CakeToppings` VALUES (3, 'Hindbær', 5.00);
INSERT INTO `CakeToppings` VALUES (4, 'Chrips', 6.00);
INSERT INTO `CakeToppings` VALUES (5, 'Jordbær', 6.00);
INSERT INTO `CakeToppings` VALUES (6, 'Rum og rossin', 7.00);
INSERT INTO `CakeToppings` VALUES (7, 'Appelsin', 8.00);
INSERT INTO `CakeToppings` VALUES (8, 'Citron', 8.00);
INSERT INTO `CakeToppings` VALUES (9, 'Blåskimmel', 9.00);
COMMIT;

-- ----------------------------
-- Table structure for Orders
-- ----------------------------
DROP TABLE IF EXISTS `Orders`;
CREATE TABLE `Orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int DEFAULT NULL,
  `comment` varchar(255) COLLATE utf8_danish_ci DEFAULT NULL,
  `createdAt` timestamp NULL DEFAULT NULL,
  `paid` tinyint(1) DEFAULT NULL,
  `completed` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `UserOnOrder` (`userId`),
  CONSTRAINT `UserOnOrder` FOREIGN KEY (`userId`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;

-- ----------------------------
-- Records of Orders
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for properties
-- ----------------------------
DROP TABLE IF EXISTS `properties`;
CREATE TABLE `properties` (
  `name` varchar(255) COLLATE utf8_danish_ci DEFAULT NULL,
  `value` varchar(255) COLLATE utf8_danish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;

-- ----------------------------
-- Records of properties
-- ----------------------------
BEGIN;
INSERT INTO `properties` VALUES ('version', '1');
COMMIT;

-- ----------------------------
-- Table structure for Users
-- ----------------------------
DROP TABLE IF EXISTS `Users`;
CREATE TABLE `Users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) COLLATE utf8_danish_ci NOT NULL,
  `name` varchar(255) COLLATE utf8_danish_ci NOT NULL,
  `phoneno` int NOT NULL,
  `salt` blob NOT NULL,
  `secret` blob NOT NULL,
  `role` enum('User','Admin') COLLATE utf8_danish_ci NOT NULL,
  `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `accountBalance` double(255,0) NOT NULL,
  PRIMARY KEY (`id`,`email`,`phoneno`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_danish_ci;

-- ----------------------------
-- Records of Users
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
