/*
 Navicat Premium Data Transfer

 Source Server         : 119.29.87.183
 Source Server Type    : MySQL
 Source Server Version : 50560
 Source Host           : 119.29.87.183:3306
 Source Schema         : achieveit

 Target Server Type    : MySQL
 Target Server Version : 50560
 File Encoding         : 65001

 Date: 04/03/2020 18:48:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `aid` int(11) NOT NULL,
  `def1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `def2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`aid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for client
-- ----------------------------
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client`  (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `company` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `grade` enum('p0','p1','p2','p3','p4','p5','p6','p7','p8','p9') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of client
-- ----------------------------
INSERT INTO `client` VALUES (1, '大客户', '大公司', 'p1', '1749597640@qq.com', '86-12345678901', '中山北路5000号');

-- ----------------------------
-- Table structure for defect
-- ----------------------------
DROP TABLE IF EXISTS `defect`;
CREATE TABLE `defect`  (
  `did` int(11) NOT NULL AUTO_INCREMENT,
  `authority` bit(2) NOT NULL COMMENT '00-无权限\r\n01-开发Leader权限\r\n10-测试Leader权限\r\n11-项目经理权限',
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `git_repo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `commit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` enum('bug','reopen','fixed','wontfix','feature','closed') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '\'bug\',\'reopen\',\'fixed\',\'wontfix\',\'feature\',\'closed\'',
  `project_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `employee_project_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`did`) USING BTREE,
  INDEX `project_id`(`project_id`) USING BTREE,
  INDEX `employee_project_id`(`employee_project_id`) USING BTREE,
  CONSTRAINT `defect_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`pid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `defect_ibfk_2` FOREIGN KEY (`employee_project_id`) REFERENCES `employee_project` (`epid`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of defect
-- ----------------------------
INSERT INTO `defect` VALUES (1, b'01', '全tm是bug', 'https://github.com/ceej7/SiteOrdering', '*', 'bug', '1', 1);
INSERT INTO `defect` VALUES (2, b'10', '功能很混乱', 'https://github.com/ceej7/SiteOrdering', '889b172144c534c376bfb55dd851daeb9bc5371d', 'feature', '1', 2);

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `eid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `department` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sup_eid` int(11) NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `portrait` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`eid`) USING BTREE,
  INDEX `superior`(`sup_eid`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  CONSTRAINT `superior` FOREIGN KEY (`sup_eid`) REFERENCES `employee` (`eid`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, 'Alias', '1749597640@qq.com', '中山北路5123号理科楼888', '总部', '86-12345678901', NULL, '123456', '');
INSERT INTO `employee` VALUES (2, 'Ben', '1749597640@qq.com', '中山北路5123号理科楼889', '总部', '86-12345678902', 1, '123456', '');
INSERT INTO `employee` VALUES (3, 'Ceej', '1749597640@qq.com', '中山北路5123号理科楼881', '总部', '86-12345678903', 1, '123456', '');
INSERT INTO `employee` VALUES (4, 'Ella', '1749597640@qq.com', '中山北路5123号理科楼882', '总部', '86-12345678904', 1, '123456', '');
INSERT INTO `employee` VALUES (5, 'Father', '1749597640@qq.com', '中山北路5123号理科楼884', '总部', '86-12345678908', 2, '123456', '');

-- ----------------------------
-- Table structure for employee_project
-- ----------------------------
DROP TABLE IF EXISTS `employee_project`;
CREATE TABLE `employee_project`  (
  `epid` int(11) NOT NULL AUTO_INCREMENT,
  `superior_epid` int(11) NULL DEFAULT NULL,
  `defect_authority` bit(2) NOT NULL COMMENT '00-无权限\r\n01-开发Leader权限\r\n10-测试Leader权限\r\n11-项目经理权限',
  `project_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `employee_id` int(11) NOT NULL,
  PRIMARY KEY (`epid`) USING BTREE,
  INDEX `employee_project_ibfk_1`(`project_id`) USING BTREE,
  INDEX `employee_project_ibfk_2`(`employee_id`) USING BTREE,
  CONSTRAINT `employee_project_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`pid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `employee_project_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`eid`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of employee_project
-- ----------------------------
INSERT INTO `employee_project` VALUES (1, NULL, b'11', '1', 1);
INSERT INTO `employee_project` VALUES (2, 1, b'01', '1', 2);

-- ----------------------------
-- Table structure for employee_role_project
-- ----------------------------
DROP TABLE IF EXISTS `employee_role_project`;
CREATE TABLE `employee_role_project`  (
  `erpid` int(11) NOT NULL AUTO_INCREMENT,
  `employee_project_id` int(11) NOT NULL COMMENT 'epid删除时连级删除此字段',
  `role` enum('pm','rdLeader','testLeader','rd','test','configurer','qa','epg') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '\'pm\',\'rdLeader\',\'testLeader\',\'rd\',\'test\',\'configurer\',\'qa\',\'epg\'',
  PRIMARY KEY (`erpid`) USING BTREE,
  INDEX `employee_role_project_ibfk_1`(`employee_project_id`) USING BTREE,
  CONSTRAINT `employee_role_project_ibfk_1` FOREIGN KEY (`employee_project_id`) REFERENCES `employee_project` (`epid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of employee_role_project
-- ----------------------------
INSERT INTO `employee_role_project` VALUES (1, 1, 'pm');
INSERT INTO `employee_role_project` VALUES (2, 2, 'rdLeader');
INSERT INTO `employee_role_project` VALUES (3, 2, 'test');
INSERT INTO `employee_role_project` VALUES (7, 1, 'configurer');

-- ----------------------------
-- Table structure for manhour
-- ----------------------------
DROP TABLE IF EXISTS `manhour`;
CREATE TABLE `manhour`  (
  `mid` int(11) NOT NULL,
  `fid` int(11) NULL DEFAULT NULL COMMENT '依靠后端从project的function的json中解析',
  `date` date NOT NULL,
  `starttime` datetime NULL DEFAULT NULL,
  `endtime` datetime NULL DEFAULT NULL,
  `status` enum('unfilled','unchecked','checked','expired') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '\'unfilled\',\'unchecked\',\'checked\',\'expired\'',
  `employee_project_id` int(11) NOT NULL,
  `activity_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`mid`) USING BTREE,
  INDEX `employee_project_id`(`employee_project_id`) USING BTREE,
  INDEX `activity_id`(`activity_id`) USING BTREE,
  CONSTRAINT `manhour_ibfk_1` FOREIGN KEY (`employee_project_id`) REFERENCES `employee_project` (`epid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `manhour_ibfk_2` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`aid`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for milestone
-- ----------------------------
DROP TABLE IF EXISTS `milestone`;
CREATE TABLE `milestone`  (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `time` datetime NULL DEFAULT NULL,
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `project_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`mid`) USING BTREE,
  INDEX `project_id`(`project_id`) USING BTREE,
  CONSTRAINT `milestone_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of milestone
-- ----------------------------
INSERT INTO `milestone` VALUES (1, '2020-03-10 16:41:20', '完成输出', '1');
INSERT INTO `milestone` VALUES (2, '2020-03-28 16:41:35', '完成输入', '1');

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `pid` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `starttime` datetime NULL DEFAULT NULL,
  `endtime` datetime NULL DEFAULT NULL,
  `technique` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `domain` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `function` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用json存储，使用两个int解析',
  `client_id` int(11) NOT NULL,
  `workflow_id` int(11) NOT NULL,
  PRIMARY KEY (`pid`) USING BTREE,
  INDEX `client_id`(`client_id`) USING BTREE,
  INDEX `workflow_id`(`workflow_id`) USING BTREE,
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`cid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `project_ibfk_2` FOREIGN KEY (`workflow_id`) REFERENCES `workflow` (`wid`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('1', '千亿大项目', '2020-03-04 15:19:34', '2020-03-28 15:19:37', '没有技术含量的', '01语言', '{0:\"能用就行\"}', 1, 1);

-- ----------------------------
-- Table structure for property
-- ----------------------------
DROP TABLE IF EXISTS `property`;
CREATE TABLE `property`  (
  `pid` int(11) NOT NULL,
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for property_occupy
-- ----------------------------
DROP TABLE IF EXISTS `property_occupy`;
CREATE TABLE `property_occupy`  (
  `poid` int(11) NOT NULL,
  `expire_time` datetime NULL,
  `is_intact` tinyint(1) NOT NULL,
  `property_id` int(11) NOT NULL,
  `project_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `employee_id` int(11) NOT NULL,
  PRIMARY KEY (`poid`) USING BTREE,
  INDEX `property_id`(`property_id`) USING BTREE,
  INDEX `project_id`(`project_id`) USING BTREE,
  INDEX `employee_id`(`employee_id`) USING BTREE,
  CONSTRAINT `property_occupy_ibfk_1` FOREIGN KEY (`property_id`) REFERENCES `property` (`pid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `property_occupy_ibfk_2` FOREIGN KEY (`project_id`) REFERENCES `project` (`pid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `property_occupy_ibfk_3` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`eid`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for risk
-- ----------------------------
DROP TABLE IF EXISTS `risk`;
CREATE TABLE `risk`  (
  `rid` int(11) NOT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `grade` enum('p0','p1','p2','p3','p4','p5','p6','p7','p8','p9') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '\'p0\',\'p1\',\'p2\',\'p3\',\'p4\',\'p5\',\'p6\',\'p7\',\'p8\',\'p9\'',
  `influence` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `strategy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `frequency` int(11) NOT NULL COMMENT '代表以n天的频率提醒一次',
  `template` tinyint(1) NOT NULL COMMENT 'boolean - 代表是否为模板',
  `employee_id` int(11) NOT NULL,
  `project_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`rid`) USING BTREE,
  INDEX `employee_id`(`employee_id`) USING BTREE,
  INDEX `project_id`(`project_id`) USING BTREE,
  CONSTRAINT `risk_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`eid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `risk_ibfk_2` FOREIGN KEY (`project_id`) REFERENCES `project` (`pid`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for risk_relation
-- ----------------------------
DROP TABLE IF EXISTS `risk_relation`;
CREATE TABLE `risk_relation`  (
  `rrid` int(11) NOT NULL,
  `employee_project_id` int(11) NOT NULL,
  `risk_id` int(11) NOT NULL,
  PRIMARY KEY (`rrid`) USING BTREE,
  INDEX `employee_project_id`(`employee_project_id`) USING BTREE,
  INDEX `risk_relation_ibfk_2`(`risk_id`) USING BTREE,
  CONSTRAINT `risk_relation_ibfk_1` FOREIGN KEY (`employee_project_id`) REFERENCES `employee_project` (`epid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `risk_relation_ibfk_2` FOREIGN KEY (`risk_id`) REFERENCES `risk` (`rid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for workflow
-- ----------------------------
DROP TABLE IF EXISTS `workflow`;
CREATE TABLE `workflow`  (
  `wid` int(11) NOT NULL AUTO_INCREMENT,
  `flowbits` int(11) NOT NULL,
  `pm_eid` int(11) NOT NULL,
  `sup_eid` int(11) NULL DEFAULT NULL,
  `configurer_eid` int(11) NULL DEFAULT NULL,
  `epgleader_eid` int(11) NULL DEFAULT NULL,
  `qamanager_eid` int(11) NULL DEFAULT NULL,
  `git_repo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `server_root` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mail_list` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `archive00` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目基础数据表',
  `archive01` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目提案书',
  `archive02` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目报价书',
  `archive03` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目估算表(功 能 点 )',
  `archive04` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目计划书',
  `archive05` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项\r\n目过程裁剪表',
  `archive06` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目成本管理表',
  `archive07` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目需求变更管理表 ',
  `archive08` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目风险管理表 ',
  `archive09` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户验收问题表 ',
  `archive10` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户验收报告 ',
  `archive11` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目总结',
  `archive12` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最佳经验和教训 ',
  `archive13` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开发工具',
  `archive14` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开 发 模 板(设 计 模 板/测 试 模 板 )',
  `archive15` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '各阶段检查单',
  `archive16` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'QA总结',
  PRIMARY KEY (`wid`) USING BTREE,
  INDEX `sup_eid`(`sup_eid`) USING BTREE,
  INDEX `configurer_eid`(`configurer_eid`) USING BTREE,
  INDEX `epgleader_eid`(`epgleader_eid`) USING BTREE,
  INDEX `qamanager_eid`(`qamanager_eid`) USING BTREE,
  INDEX `workflow_ibfk_0`(`pm_eid`) USING BTREE,
  CONSTRAINT `workflow_ibfk_1` FOREIGN KEY (`sup_eid`) REFERENCES `employee` (`eid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `workflow_ibfk_2` FOREIGN KEY (`configurer_eid`) REFERENCES `employee` (`eid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `workflow_ibfk_3` FOREIGN KEY (`epgleader_eid`) REFERENCES `employee` (`eid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `workflow_ibfk_4` FOREIGN KEY (`qamanager_eid`) REFERENCES `employee` (`eid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `workflow_ibfk_0` FOREIGN KEY (`pm_eid`) REFERENCES `employee` (`eid`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of workflow
-- ----------------------------
INSERT INTO `workflow` VALUES (1, 0, 3, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
