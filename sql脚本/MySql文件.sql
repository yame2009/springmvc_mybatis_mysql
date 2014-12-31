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
-- Table structure for 'studentinfo'
-- ----------------------------
DROP TABLE IF EXISTS `studentinfo`;
CREATE TABLE `studentinfo` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`name` VARCHAR(255) NULL DEFAULT NULL COMMENT '名称',
	 `password` varchar(255) DEFAULT '888888',
	`age` INT(11) NULL DEFAULT NULL COMMENT '年龄',
	`birthday` DATETIME NULL DEFAULT NULL  COMMENT '生日',
	`address` VARCHAR(255) NULL DEFAULT NULL  COMMENT '家庭住址',
	`sex` varchar(255) DEFAULT '1' COMMENT '1表示男，0表示女',
  `email` varchar(255) DEFAULT null  COMMENT 'email',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `modifyTime` datetime DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=8;
 
-- ----------------------------
-- Records of studentinfo
-- ----------------------------
INSERT INTO `studentinfo` VALUES ('2', '李四','888888', '22', '2014-11-03 14:28:53', '北京', '1','','2014-11-03 14:28:53','2014-11-03 14:28:53');
INSERT INTO `studentinfo` VALUES ('3', '张三三','888888', '21', '2014-11-15 15:55:33', '北京', '1','','2014-11-15 15:55:33','2014-11-15 15:55:33');
INSERT INTO `studentinfo` VALUES ('4', '李四四','888888', '20', '2014-11-15 16:00:40', '北京', '1','','2014-11-15 15:55:33','2014-11-15 15:55:33');
INSERT INTO `studentinfo` VALUES ('5', '王五', '888888','20', '2014-11-15 16:02:41', '北京', '1','','2014-11-15 15:55:33','2014-11-15 15:55:33');
INSERT INTO `studentinfo` VALUES ('7', '测试','888888', '19', '2014-11-15 16:54:05', '北京', '1','','2014-11-15 15:55:33','2014-11-15 15:55:33');
