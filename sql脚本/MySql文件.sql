--- SpringMVC+Hibernate +MySql+ EasyUI   sql文件
/*
Navicat MySQL Data Transfer
 
Source Server         : local
Source Server Version : 50160
Source Host           : localhost:3306
Source Database       : test
 
Target Server Type    : MYSQL
Target Server Version : 50160
File Encoding         : 65001
 
Date: 2014-11-18 09:31:45
*/
 
--SET FOREIGN_KEY_CHECKS=0;
 
-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT '888888',
  `createTime` datetime DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
 
-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('2', '李四', '22', '2014-11-03 14:28:53', '北京', '888888','2014-11-03 14:28:53','2014-11-03 14:28:53');
INSERT INTO `userinfo` VALUES ('3', '张三三', '21', '2014-11-15 15:55:33', '北京', '888888','2014-11-15 15:55:33','2014-11-15 15:55:33');
INSERT INTO `userinfo` VALUES ('4', '李四四', '20', '2014-11-15 16:00:40', '北京', '888888','2014-11-15 15:55:33','2014-11-15 15:55:33');
INSERT INTO `userinfo` VALUES ('5', '王五', '20', '2014-11-15 16:02:41', '北京', '888888','2014-11-15 15:55:33','2014-11-15 15:55:33');
INSERT INTO `userinfo` VALUES ('7', '测试', '19', '2014-11-15 16:54:05', '北京', '888888','2014-11-15 15:55:33','2014-11-15 15:55:33');


