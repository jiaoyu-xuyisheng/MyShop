/*
Navicat MySQL Data Transfer

Source Server         : xu
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : myshop

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-10-30 07:26:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `cid` varchar(50) NOT NULL,
  `cname` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', '手机数码');
INSERT INTO `category` VALUES ('2', '电脑办公');
INSERT INTO `category` VALUES ('3', '家具家居');
INSERT INTO `category` VALUES ('4', '鞋靴箱包');
INSERT INTO `category` VALUES ('5', '图书音像');
INSERT INTO `category` VALUES ('6', '母婴孕婴');
INSERT INTO `category` VALUES ('7', '汽车用品');
INSERT INTO `category` VALUES ('8', '性用品');

-- ----------------------------
-- Table structure for orderitem
-- ----------------------------
DROP TABLE IF EXISTS `orderitem`;
CREATE TABLE `orderitem` (
  `itemid` varchar(50) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  `pid` varchar(50) DEFAULT NULL,
  `oid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`itemid`),
  KEY `fk_0001` (`pid`),
  KEY `fk_0002` (`oid`),
  CONSTRAINT `fk_0002` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderitem
-- ----------------------------
INSERT INTO `orderitem` VALUES ('2acd48df-2d5f-4db0-9a2a-98bf53fdef66', '1', '6688', '32', '59613f30-281e-4a5f-83c4-e8d0ab54eb33');
INSERT INTO `orderitem` VALUES ('511de8e6-52c3-4206-b0b9-3cc28999418d', '1', '2298', '11', '59613f30-281e-4a5f-83c4-e8d0ab54eb33');
INSERT INTO `orderitem` VALUES ('53aba46f-2906-4af2-8099-b6605fa73ceb', '5', '33440', '32', '2ca1714d-d871-4221-8c3b-a52a321a15e3');
INSERT INTO `orderitem` VALUES ('772181ed-6a55-42f1-ad62-eaf6ad8b1494', '5', '11490', '11', 'f18b94f8-f1e4-4a71-ac3b-c44889960fe4');
INSERT INTO `orderitem` VALUES ('96a93703-43a1-44cb-97e0-00d698006fdc', '1', '2298', '11', 'c550f5d3-04b8-4aa0-8eb3-0a0fb6c11164');
INSERT INTO `orderitem` VALUES ('e96ba0bb-a7d6-4c48-b31f-63dfaccf31f8', '1', '1469', '19', 'c550f5d3-04b8-4aa0-8eb3-0a0fb6c11164');
INSERT INTO `orderitem` VALUES ('f4acf4ac-7586-4201-ab6f-b5584daf800c', '10', '18880', '48', '59613f30-281e-4a5f-83c4-e8d0ab54eb33');
INSERT INTO `orderitem` VALUES ('f8a46d2a-ea45-4cd6-8c55-43e05595c905', '5', '33440', '32', '0e083ea8-7fc1-4531-8c4b-d0e7dac6b764');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `oid` varchar(50) NOT NULL,
  `ordertime` datetime DEFAULT NULL,
  `total` double DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `address` varchar(80) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `uid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------




-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `pid` varchar(50) NOT NULL,
  `pname` varchar(50) DEFAULT NULL,
  `market_price` double DEFAULT NULL,
  `shop_price` double DEFAULT NULL,
  `pimage` varchar(200) DEFAULT NULL,
  `pdate` date DEFAULT NULL,
  `is_hot` int(11) DEFAULT NULL,
  `pdesc` varchar(255) DEFAULT NULL,
  `pflag` int(11) DEFAULT NULL,
  `cid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`pid`),
  KEY `sfk_0001` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', 'oppo手机', '2000', '2000', 'products/1/c_0001.jpg', '2018-04-03', '1', 'С', '0', '1');
INSERT INTO `product` VALUES ('10', 'Iphone', '6000', '6000', 'products/1/c_0010.jpg', '2018-04-03', '1', '', '0', '1');
INSERT INTO `product` VALUES ('11', 'vivo X5Pro', '2399', '2298', 'products/1/c_0014.jpg', '2015-11-02', '1', '', '0', '1');
INSERT INTO `product` VALUES ('12', '360手sm', '1899', '1799', 'products/1/c_0013.jpg', '2018-03-06', '0', 'Ŭ', '0', '1');
INSERT INTO `product` VALUES ('13', '', '2599', '2499', 'products/1/c_0012.jpg', '2018-04-03', '0', '', '0', '1');
INSERT INTO `product` VALUES ('14', 'vivo X5M', '1899', '1799', 'products/1/c_0011.jpg', '2015-11-02', '0', 'vivo X5M ', '0', '1');
INSERT INTO `product` VALUES ('15', 'Apple iPhone 6 (A1586)', '4399', '4288', 'products/1/c_0015.jpg', '2015-11-02', '1', 'Apple iPhone 6 (A1586) 16GB ', '0', '1');
INSERT INTO `product` VALUES ('16', '', '4200', '4087', 'products/1/c_0016.jpg', '2015-11-03', '0', '', '0', '1');
INSERT INTO `product` VALUES ('17', 'goodPhone', '4099', '3999', 'products/1/c_0017.jpg', '2018-04-03', '1', 'this is a good phone', '0', '1');
INSERT INTO `product` VALUES ('18', 'HTC One M9+', '3599', '3499', 'products/1/c_0018.jpg', '2015-11-02', '0', 'HTC One M9+', '0', '1');
INSERT INTO `product` VALUES ('19', 'HTC Desire 826d 32G ', '1599', '1469', 'products/1/c_0020.jpg', '2015-11-02', '1', '', '0', '1');
INSERT INTO `product` VALUES ('2', '', '2899', '2699', 'products/1/c_0002.jpg', '2015-11-05', '1', '', '0', '1');
INSERT INTO `product` VALUES ('20', 'С', '649', '549', 'products/1/c_0019.jpg', '2015-11-02', '0', '', '0', '1');
INSERT INTO `product` VALUES ('21', '', '1099', '999', 'products/1/c_0021.jpg', '2015-11-02', '0', '', '0', '1');
INSERT INTO `product` VALUES ('22', '', '2099', '1999', 'products/1/c_0022.jpg', '2015-11-02', '1', '5.1Ӣ', '0', '1');
INSERT INTO `product` VALUES ('23', 'sonim XP7700 4G', '1799', '1699', 'products/1/c_0023.jpg', '2015-11-09', '1', '', '0', '1');
INSERT INTO `product` VALUES ('24', 'Ŭ', '3988', '3888', 'products/1/c_0024.jpg', '2015-11-02', '1', '', '0', '1');
INSERT INTO `product` VALUES ('25', 'Apple iPhone 6 Plus (A1524) 16GB ', '5188', '4988', 'products/1/c_0025.jpg', '2015-11-02', '0', 'Apple iPhone 6 Plus (A1524) 16GB ', '0', '1');
INSERT INTO `product` VALUES ('26', 'Apple iPhone 6s (A1700) 64G õ', '6388', '6088', 'products/1/c_0026.jpg', '2015-11-02', '0', 'Apple iPhone 6 Plus (A1524) 16GB ', '0', '1');
INSERT INTO `product` VALUES ('27', '', '5588', '5388', 'products/1/c_0027.jpg', '2018-04-03', '1', '', '0', '1');
INSERT INTO `product` VALUES ('28', '', '5999', '5888', 'products/1/c_0028.jpg', '2015-11-02', '0', '', '0', '1');
INSERT INTO `product` VALUES ('29', 'LG G4', '3018', '2978', 'products/1/c_0029.jpg', '2015-11-02', '0', '', '0', '1');
INSERT INTO `product` VALUES ('3', '', '1599', '1499', 'products/1/c_0003.jpg', '2015-11-02', '0', '', '0', '1');
INSERT INTO `product` VALUES ('30', 'mobilePhone', '1099', '999', 'products/1/c_0030.jpg', '2018-04-03', '0', 'this is a mobilePhone', '0', '1');
INSERT INTO `product` VALUES ('31', '', '2399', '2299', 'products/1/c_0031.jpg', '2015-11-02', '0', '', '0', '2');
INSERT INTO `product` VALUES ('32', 'Apple MacBook Air MJVE2CH/A 13.3Ӣ', '6788', '6688', 'products/1/c_0032.jpg', '2015-11-02', '0', '', '0', '2');
INSERT INTO `product` VALUES ('33', '', '4399', '4199', 'products/1/c_0033.jpg', '2015-11-02', '0', '', '0', '2');
INSERT INTO `product` VALUES ('34', '', '4599', '4499', 'products/1/c_0034.jpg', '2015-11-02', '0', '14Ӣ', '0', '2');
INSERT INTO `product` VALUES ('35', '', '3799', '3699', 'products/1/c_0035.jpg', '2015-11-02', '0', '15.6Ӣ', '0', '2');
INSERT INTO `product` VALUES ('36', '', '4599', '4399', 'products/1/c_0036.jpg', '2015-11-02', '0', '14Ӣ', '0', '2');
INSERT INTO `product` VALUES ('37', '', '3399', '3299', 'products/1/c_0037.jpg', '2015-11-03', '0', ' Ins14C-4528B 14Ӣ', '0', '2');
INSERT INTO `product` VALUES ('38', '', '5699', '5499', 'products/1/c_0038.jpg', '2015-11-02', '0', '15.6Ӣ', '0', '2');
INSERT INTO `product` VALUES ('39', 'Apple ', '11299', '10288', 'products/1/c_0039.jpg', '2015-11-02', '0', 'Pro MF840CH/A 13.3Ӣ', '0', '2');
INSERT INTO `product` VALUES ('4', '', '2199', '1999', 'products/1/c_0004.jpg', '2015-11-02', '0', '', '0', '1');
INSERT INTO `product` VALUES ('40', '战神笔记本', '6799', '6599', 'products/1/c_0040.jpg', '2018-04-03', '1', '15.6Ӣ', '0', '1');
INSERT INTO `product` VALUES ('41', '', '5699', '5499', 'products/1/c_0041.jpg', '2015-11-02', '0', '15.6Ӣ', '0', '2');
INSERT INTO `product` VALUES ('42', '΢', '6199', '5999', 'products/1/c_0042.jpg', '2015-11-02', '0', '15.6Ӣ', '0', '2');
INSERT INTO `product` VALUES ('43', '', '5699', '5499', 'products/1/c_0043.jpg', '2015-11-02', '0', '15.6Ӣ', '0', '2');
INSERT INTO `product` VALUES ('44', '', '3199', '3099', 'products/1/c_0044.jpg', '2015-11-02', '0', '15-r239TX 15.6Ӣ', '0', '2');
INSERT INTO `product` VALUES ('45', '外星人笔记本', '10999', '9899', 'products/1/c_0045.jpg', '2018-04-03', '1', '15.6Ӣ', '0', '1');
INSERT INTO `product` VALUES ('46', '联想笔记本', '3299', '3199', 'products/1/c_0046.jpg', '2018-04-03', '1', '联想笔记本一般般', '0', '1');
INSERT INTO `product` VALUES ('47', '华硕台式机', '5099', '4899', 'products/1/c_0047.jpg', '2018-04-03', '1', '这个电脑性能很好;', '0', '1');
INSERT INTO `product` VALUES ('48', 'Apple iPad mini 2 ME279CH/A', '2088', '1888', 'products/1/c_0048.jpg', '2015-11-02', '0', '', '0', '2');
INSERT INTO `product` VALUES ('49', 'С', '1399', '1299', 'products/1/c_0049.jpg', '2015-11-02', '0', 'WIFI 64GB', '0', '2');
INSERT INTO `product` VALUES ('5', 'Ħ', '1799', '1699', 'products/1/c_0005.jpg', '2015-11-01', '0', 'Ħ', '0', '1');
INSERT INTO `product` VALUES ('50', 'Apple iPad Air 2 MGLW2CH/A', '2399', '2299', 'products/1/c_0050.jpg', '2015-11-12', '0', '', '0', '2');
INSERT INTO `product` VALUES ('6', 'hello', '1899', '1799', 'products/1/c_0006.jpg', '2018-04-03', '1', 'hello how are you', '0', '1');
INSERT INTO `product` VALUES ('7', '', '1499', '1398', 'products/1/c_0007.jpg', '2015-11-14', '0', '', '0', '1');
INSERT INTO `product` VALUES ('8', 'NUU NU5', '1288', '1190', 'products/1/c_0008.jpg', '2015-11-02', '0', 'NUU NU5 16GB ', '0', '1');
INSERT INTO `product` VALUES ('9', '', '2399', '2299', 'products/1/c_0009.jpg', '2015-11-02', '0', '', '0', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` varchar(50) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `code` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------

INSERT INTO `user` VALUES ('373eb242933b4f5ca3bd43503c34668b', 'ccc', 'ccc', 'aaa', 'bbb@store.com', '15723689921', '2015-11-04', '', '0', '9782f3e837ff422b9aee8b6381ccf927bdd9d2ced10d48f4ba4b9f187edf7738');
INSERT INTO `user` VALUES ('3ca76a75e4f64db2bacd0974acc7c897', 'bb', 'bb', '', 'bbb@store.com', '15723689921', '1990-02-01', '', '0', '1258e96181a9457987928954825189000bae305094a042d6bd9d2d35674684e6');
INSERT INTO `user` VALUES ('62145f6e66ea4f5cbe7b6f6b954917d3', 'cc', 'cc', '', 'bbb@store.com', '15723689921', '2015-11-03', '', '0', '19f100aa81184c03951c4b840a725b6a98097aa1106a4a38ba1c29f1a496c231');
INSERT INTO `user` VALUES ('c95b15a864334adab3d5bb6604c6e1fc', 'bbb', 'bbb', '', 'bbb@store.com', '15712344823', '2000-02-01', '', '1', '71a3a933353347a4bcacff699e6baa9c950a02f6b84e4f6fb8404ca06febfd6f');
INSERT INTO `user` VALUES ('f55b7d3a352a4f0782c910b2c70f1ea4', 'aaa', 'aaa', 'eee', 'aaa@store.com', '15712344823', '2000-02-01', '', '1', null);
