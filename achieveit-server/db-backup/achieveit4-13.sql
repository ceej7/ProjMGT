/*
 Navicat Premium Data Transfer

 Source Server         : 47.100.57.110
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 47.100.57.110:3306
 Source Schema         : achieveit

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 13/04/2020 14:36:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `def1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `def2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`aid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (1, '工程活动 ', '需求开发');
INSERT INTO `activity` VALUES (2, '工程活动 ', '设计');
INSERT INTO `activity` VALUES (3, '工程活动 ', '编码');
INSERT INTO `activity` VALUES (4, '工程活动 ', '单体测试');
INSERT INTO `activity` VALUES (5, '工程活动 ', '集成测试');
INSERT INTO `activity` VALUES (6, '工程活动 ', '系统测试');
INSERT INTO `activity` VALUES (7, '工程活动 ', '交付');
INSERT INTO `activity` VALUES (8, '工程活动 ', '维护');
INSERT INTO `activity` VALUES (9, '管理活动', '范围管理');
INSERT INTO `activity` VALUES (10, '管理活动', '计划与调整');
INSERT INTO `activity` VALUES (11, '管理活动', '监控与分析');
INSERT INTO `activity` VALUES (12, '管理活动', '联络与沟通');
INSERT INTO `activity` VALUES (13, '外包活动 ', '外包管理');
INSERT INTO `activity` VALUES (14, '外包活动 ', '外包验收');
INSERT INTO `activity` VALUES (15, '外包活动 ', '外包支持 ');
INSERT INTO `activity` VALUES (16, '支持活动 ', '配置管理');
INSERT INTO `activity` VALUES (17, '支持活动 ', 'QA 审计');
INSERT INTO `activity` VALUES (18, '支持活动 ', '会议报告总结');
INSERT INTO `activity` VALUES (19, '支持活动 ', '培训');
INSERT INTO `activity` VALUES (20, '支持活动 ', '其他');

-- ----------------------------
-- Table structure for client
-- ----------------------------
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client`  (
  `cid` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `company` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `grade` enum('p0','p1','p2','p3','p4','p5','p6','p7','p8','p9') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '\'p0\',\'p1\',\'p2\',\'p3\',\'p4\',\'p5\',\'p6\',\'p7\',\'p8\',\'p9\'',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of client
-- ----------------------------
INSERT INTO `client` VALUES (1, '大客户', '大公司', 'p1', '1749597640@qq.com', '86-12345678901', '中山北路5000号');
INSERT INTO `client` VALUES (2, '中客户', '中公司', 'p2', '1749597640@qq.com', '86-12345678901', '中山北路5000号');
INSERT INTO `client` VALUES (3, '小客户', '小公司', 'p3', '1749597640@qq.com', '86-12345678901', '中山北路5000号');
INSERT INTO `client` VALUES (4, '幽灵客户', '皮包公司', 'p4', '1749597640@qq.com', '86-12345678901', '中山北路5000号');

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
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of defect
-- ----------------------------
INSERT INTO `defect` VALUES (2, b'10', '功能很混乱', 'https://github.com/ceej7/SiteOrdering', '889b172144c534c376bfb55dd851daeb9bc5371d', 'feature', '20200002D01', 2);
INSERT INTO `defect` VALUES (4, b'11', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '*', 'bug', '20200003O01', 4);
INSERT INTO `defect` VALUES (5, b'11', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '*', 'feature', '20200002D02', 5);
INSERT INTO `defect` VALUES (7, b'11', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '*', 'feature', '20200002D03', 7);
INSERT INTO `defect` VALUES (8, b'11', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '*', 'closed', '20200003O02', 8);
INSERT INTO `defect` VALUES (9, b'00', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '*', 'closed', '20200003O01', 9);
INSERT INTO `defect` VALUES (10, b'11', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '*', 'feature', '20200003O01', 10);
INSERT INTO `defect` VALUES (11, b'11', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '*', 'feature', '20200003O01', 11);
INSERT INTO `defect` VALUES (12, b'11', 'pm不肯和我出去喝一杯', 'https://github.com/ceej7/SiteOrdering', '*', 'feature', '20200002D02', 12);
INSERT INTO `defect` VALUES (14, b'11', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '*', 'feature', '20200002D02', 14);
INSERT INTO `defect` VALUES (16, b'00', '主要需求完成度250%', 'https://github.com/ceej7/SiteOrdering', '250', 'fixed', '20200001M02', 16);
INSERT INTO `defect` VALUES (17, b'00', '头秃，眼瞎', 'https://github.com/ceej7/SiteOrdering', '*', 'closed', '20200001M02', 17);
INSERT INTO `defect` VALUES (18, b'11', '你是没看需求的吗', 'https://github.com/ceej7/SiteOrdering', '*', 'closed', '20200002D03', 18);
INSERT INTO `defect` VALUES (19, b'11', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '*', 'feature', '20200002D03', 19);
INSERT INTO `defect` VALUES (20, b'11', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '*', 'feature', '20200002D03', NULL);
INSERT INTO `defect` VALUES (21, b'00', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '*', 'bug', '20200003O02', 21);
INSERT INTO `defect` VALUES (22, b'11', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '*', 'feature', '20200003O02', 22);
INSERT INTO `defect` VALUES (23, b'11', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '*', 'feature', '20200003O02', 23);
INSERT INTO `defect` VALUES (25, b'11', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '*', 'feature', '20200002D02', 25);
INSERT INTO `defect` VALUES (28, b'11', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '*', 'feature', '20200003O02', 28);
INSERT INTO `defect` VALUES (29, b'11', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '*', 'feature', '20200002D02', 29);
INSERT INTO `defect` VALUES (30, b'11', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '*', 'feature', '20200002D02', 30);
INSERT INTO `defect` VALUES (31, b'11', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', 'fix: some bugs', 'fixed', '20200001M02', 31);
INSERT INTO `defect` VALUES (32, b'11', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '*', 'feature', '20200001M02', 32);
INSERT INTO `defect` VALUES (33, b'11', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '*', 'feature', '20200002D03', 33);
INSERT INTO `defect` VALUES (35, b'11', '怎么跟我提的需求不一样', 'https://github.com/ceej7/SiteOrdering', '***', 'feature', '20200003O02', 35);
INSERT INTO `defect` VALUES (43, b'11', 'www', 'www', 'www', 'bug', '20200002D02', 13);
INSERT INTO `defect` VALUES (44, b'11', '是个大坑', 'www', 'wwwww', 'bug', '20200001M02', 26);
INSERT INTO `defect` VALUES (45, b'10', '我的需求向东，你的功能向西', 'http', 'first commit', 'bug', '20200001M02', 6);
INSERT INTO `defect` VALUES (46, b'01', '没救了', 'baidu', 'baidu', 'fixed', '20200001M02', 6);
INSERT INTO `defect` VALUES (49, b'00', '汪汪汪', '嘤嘤嘤', '......', 'closed', '20200002D03', 7);
INSERT INTO `defect` VALUES (50, b'01', '嘤嘤嘤', '嘤嘤嘤', '嘤嘤嘤', 'bug', '20200002D03', 7);
INSERT INTO `defect` VALUES (51, b'10', '客户跑路了', 'www.老板黄鹤.com', '原价三百万的系统，现在只要三百块', 'bug', '20200002D03', 34);
INSERT INTO `defect` VALUES (52, b'10', '代码写的太丑', 'www.github.com', 'ugly code', 'bug', '20200002D03', 7);
INSERT INTO `defect` VALUES (53, b'00', '违反我国刑法第二百三十四条', 'www.github.com', 'www.github.com', 'bug', '20200002D02', 5);
INSERT INTO `defect` VALUES (54, b'10', '点击付款会一直扣款', 'www', 'first commit', 'bug', '20200001O03', 98);

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
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `portrait` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` enum('pm_manager','configurer','pm','epg_leader','qa_manager','member') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '\'pm_manager\',\'configurer\',\'pm\',\'epg_leader\',\'qa_manager\',\'member\'',
  `sup_eid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`eid`) USING BTREE,
  INDEX `superior`(`sup_eid`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `eid`(`eid`) USING BTREE,
  CONSTRAINT `superior` FOREIGN KEY (`sup_eid`) REFERENCES `employee` (`eid`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, 'Alias', '1749597640@qq.com', '中山北路5123号理科楼888', '总部', '86-12345678901', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'pm_manager', NULL);
INSERT INTO `employee` VALUES (2, 'Ben', '1@qq.com', '中山北路5123号理科楼889', '总部', '86-12345678902', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'pm', 1);
INSERT INTO `employee` VALUES (3, 'Ceej', '2@qq.com', '中山北路5123号理科楼881', '总部', '86-12345678903', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'pm', 1);
INSERT INTO `employee` VALUES (4, 'Ella', '3@qq.com', '中山北路5123号理科楼882', '总部', '86-12345678904', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'qa_manager', NULL);
INSERT INTO `employee` VALUES (5, 'Father', '4@qq.com', '中山北路5123号理科楼1', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'epg_leader', NULL);
INSERT INTO `employee` VALUES (6, 'Gay', '5@qq.com', '中山北路5123号理科楼2', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'epg_leader', NULL);
INSERT INTO `employee` VALUES (7, 'Helen', '6@qq.com', '中山北路5123号理科楼3', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'configurer', NULL);
INSERT INTO `employee` VALUES (8, 'Irwin', '7@qq.com', '中山北路5123号理科楼4', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'configurer', NULL);
INSERT INTO `employee` VALUES (10, 'Kiki', '8@qq.com', '中山北路5123号理科楼5', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'member', 4);
INSERT INTO `employee` VALUES (11, 'Lily', '9@qq.com', '中山北路5123号理科楼6', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'member', 4);
INSERT INTO `employee` VALUES (12, 'Maya', '10@qq.com', '中山北路5123号理科楼7', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'member', 4);
INSERT INTO `employee` VALUES (13, 'N', '11@qq.com', '中山北路5123号理科楼8', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'member', 5);
INSERT INTO `employee` VALUES (14, 'O', '12@qq.com', '中山北路5123号理科楼9', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'member', 5);
INSERT INTO `employee` VALUES (15, 'P', '13@qq.com', '中山北路5123号理科10', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'member', 5);
INSERT INTO `employee` VALUES (16, 'Q', '140@qq.com', '中山北路5123号理科楼12', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'member', 6);
INSERT INTO `employee` VALUES (17, 'R', '15@qq.com', '中山北路5123号理科楼11', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'member', 6);
INSERT INTO `employee` VALUES (18, 'S', '16@qq.com', '中山北路5123号理科楼13', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'member', 6);
INSERT INTO `employee` VALUES (19, 'T', '19@qq.com', '中山北路5123号理科楼14', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'member', 7);
INSERT INTO `employee` VALUES (20, 'U', '20@qq.com', '中山北路5123号理科楼15', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'member', 7);
INSERT INTO `employee` VALUES (21, 'V', '21@qq.com', '中山北路5123号理科楼16', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'member', 7);
INSERT INTO `employee` VALUES (22, 'W', '22@qq.com', '中山北路5123号理科楼17', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'member', 7);
INSERT INTO `employee` VALUES (23, 'X', '23@qq.com', '中山北路5123号理科楼28', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'member', 8);
INSERT INTO `employee` VALUES (24, 'Y', '24@qq.com', '中山北路5123号理科楼19', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'member', 8);
INSERT INTO `employee` VALUES (25, 'Z', '25@qq.com', '中山北路5123号理科楼20', '总部', '86-12345678908', '123456', 'http://47.100.57.110:8079/images/default.jpg', 'member', 8);

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
  INDEX `superior_epid`(`superior_epid`) USING BTREE,
  CONSTRAINT `employee_project_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`pid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `employee_project_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`eid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `employee_project_ibfk_3` FOREIGN KEY (`superior_epid`) REFERENCES `employee_project` (`epid`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 136 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of employee_project
-- ----------------------------
INSERT INTO `employee_project` VALUES (1, NULL, b'11', '20200001D01', 2);
INSERT INTO `employee_project` VALUES (2, NULL, b'11', '20200002D01', 3);
INSERT INTO `employee_project` VALUES (3, NULL, b'11', '20200004S01', 2);
INSERT INTO `employee_project` VALUES (4, NULL, b'11', '20200003O01', 3);
INSERT INTO `employee_project` VALUES (5, NULL, b'11', '20200002D02', 3);
INSERT INTO `employee_project` VALUES (6, NULL, b'11', '20200001M02', 2);
INSERT INTO `employee_project` VALUES (7, NULL, b'11', '20200002D03', 2);
INSERT INTO `employee_project` VALUES (8, NULL, b'11', '20200003O02', 3);
INSERT INTO `employee_project` VALUES (9, 4, b'10', '20200003O01', 4);
INSERT INTO `employee_project` VALUES (10, 9, b'00', '20200003O01', 10);
INSERT INTO `employee_project` VALUES (11, 9, b'00', '20200003O01', 13);
INSERT INTO `employee_project` VALUES (12, 5, b'10', '20200002D02', 4);
INSERT INTO `employee_project` VALUES (13, 12, b'00', '20200002D02', 11);
INSERT INTO `employee_project` VALUES (14, 12, b'00', '20200002D02', 14);
INSERT INTO `employee_project` VALUES (15, 6, b'10', '20200001M02', 4);
INSERT INTO `employee_project` VALUES (16, 15, b'00', '20200001M02', 10);
INSERT INTO `employee_project` VALUES (17, 15, b'00', '20200001M02', 14);
INSERT INTO `employee_project` VALUES (18, 7, b'10', '20200002D03', 4);
INSERT INTO `employee_project` VALUES (19, 18, b'00', '20200002D03', 11);
INSERT INTO `employee_project` VALUES (21, 8, b'10', '20200003O02', 4);
INSERT INTO `employee_project` VALUES (22, 21, b'00', '20200003O02', 13);
INSERT INTO `employee_project` VALUES (23, 21, b'00', '20200003O02', 14);
INSERT INTO `employee_project` VALUES (24, 4, b'00', '20200003O01', 5);
INSERT INTO `employee_project` VALUES (25, 5, b'00', '20200002D02', 6);
INSERT INTO `employee_project` VALUES (26, 6, b'00', '20200001M02', 5);
INSERT INTO `employee_project` VALUES (27, 7, b'00', '20200002D03', 5);
INSERT INTO `employee_project` VALUES (28, 8, b'00', '20200003O02', 6);
INSERT INTO `employee_project` VALUES (29, 5, b'01', '20200002D02', 10);
INSERT INTO `employee_project` VALUES (30, 29, b'00', '20200002D02', 13);
INSERT INTO `employee_project` VALUES (31, 6, b'01', '20200001M02', 11);
INSERT INTO `employee_project` VALUES (32, 31, b'00', '20200001M02', 13);
INSERT INTO `employee_project` VALUES (33, 7, b'01', '20200002D03', 14);
INSERT INTO `employee_project` VALUES (34, 33, b'00', '20200002D03', 12);
INSERT INTO `employee_project` VALUES (35, 8, b'01', '20200003O02', 10);
INSERT INTO `employee_project` VALUES (36, 35, b'00', '20200003O02', 12);
INSERT INTO `employee_project` VALUES (62, NULL, b'11', '20200001D00', 2);
INSERT INTO `employee_project` VALUES (63, 62, b'00', '20200001D00', 10);
INSERT INTO `employee_project` VALUES (64, 62, b'00', '20200001D00', 11);
INSERT INTO `employee_project` VALUES (65, 62, b'00', '20200001D00', 12);
INSERT INTO `employee_project` VALUES (66, 62, b'10', '20200001D00', 4);
INSERT INTO `employee_project` VALUES (67, 66, b'00', '20200001D00', 13);
INSERT INTO `employee_project` VALUES (68, 66, b'00', '20200001D00', 14);
INSERT INTO `employee_project` VALUES (69, 66, b'01', '20200001D00', 15);
INSERT INTO `employee_project` VALUES (70, 69, b'00', '20200001D00', 16);
INSERT INTO `employee_project` VALUES (71, 69, b'00', '20200001D00', 17);
INSERT INTO `employee_project` VALUES (72, 69, b'00', '20200001D00', 18);
INSERT INTO `employee_project` VALUES (73, 69, b'00', '20200001D00', 19);
INSERT INTO `employee_project` VALUES (74, 69, b'00', '20200001D00', 20);
INSERT INTO `employee_project` VALUES (75, 66, b'00', '20200001D00', 21);
INSERT INTO `employee_project` VALUES (76, 66, b'00', '20200001D00', 22);
INSERT INTO `employee_project` VALUES (77, 66, b'00', '20200001D00', 23);
INSERT INTO `employee_project` VALUES (78, NULL, b'11', '20200001O00', 2);
INSERT INTO `employee_project` VALUES (80, 78, b'00', '20200001O00', 11);
INSERT INTO `employee_project` VALUES (81, 78, b'00', '20200001O00', 12);
INSERT INTO `employee_project` VALUES (82, 78, b'10', '20200001O00', 4);
INSERT INTO `employee_project` VALUES (83, 82, b'00', '20200001O00', 13);
INSERT INTO `employee_project` VALUES (85, 82, b'00', '20200001O00', 15);
INSERT INTO `employee_project` VALUES (86, 85, b'00', '20200001O00', 16);
INSERT INTO `employee_project` VALUES (87, 85, b'00', '20200001O00', 17);
INSERT INTO `employee_project` VALUES (88, 85, b'00', '20200001O00', 18);
INSERT INTO `employee_project` VALUES (89, 85, b'00', '20200001O00', 19);
INSERT INTO `employee_project` VALUES (91, 82, b'00', '20200001O00', 21);
INSERT INTO `employee_project` VALUES (92, 82, b'00', '20200001O00', 22);
INSERT INTO `employee_project` VALUES (94, 62, b'00', '20200001D00', 25);
INSERT INTO `employee_project` VALUES (95, NULL, b'11', '20200001M00', 2);
INSERT INTO `employee_project` VALUES (96, NULL, b'11', '20200001O01', 2);
INSERT INTO `employee_project` VALUES (97, NULL, b'11', '20200001O02', 2);
INSERT INTO `employee_project` VALUES (98, NULL, b'11', '20200001O03', 2);
INSERT INTO `employee_project` VALUES (99, 78, b'00', '20200001O00', 24);
INSERT INTO `employee_project` VALUES (100, 96, b'00', '20200001O01', 13);
INSERT INTO `employee_project` VALUES (101, 96, b'00', '20200001O01', 14);
INSERT INTO `employee_project` VALUES (102, 96, b'00', '20200001O01', 16);
INSERT INTO `employee_project` VALUES (103, 96, b'10', '20200001O01', 4);
INSERT INTO `employee_project` VALUES (104, 103, b'00', '20200001O01', 24);
INSERT INTO `employee_project` VALUES (105, 103, b'00', '20200001O01', 23);
INSERT INTO `employee_project` VALUES (106, 103, b'00', '20200001O01', 25);
INSERT INTO `employee_project` VALUES (107, 105, b'00', '20200001O01', 15);
INSERT INTO `employee_project` VALUES (108, NULL, b'11', '20200002O00', 2);
INSERT INTO `employee_project` VALUES (109, NULL, b'11', '20200001M01', 2);
INSERT INTO `employee_project` VALUES (110, 109, b'00', '20200001M01', 11);
INSERT INTO `employee_project` VALUES (111, 109, b'00', '20200001M01', 13);
INSERT INTO `employee_project` VALUES (112, 109, b'00', '20200001M01', 15);
INSERT INTO `employee_project` VALUES (113, 109, b'10', '20200001M01', 4);
INSERT INTO `employee_project` VALUES (114, 113, b'00', '20200001M01', 12);
INSERT INTO `employee_project` VALUES (115, 113, b'00', '20200001M01', 14);
INSERT INTO `employee_project` VALUES (116, 113, b'00', '20200001M01', 16);
INSERT INTO `employee_project` VALUES (117, 108, b'00', '20200002O00', 22);
INSERT INTO `employee_project` VALUES (118, 108, b'10', '20200002O00', 4);
INSERT INTO `employee_project` VALUES (119, 118, b'00', '20200002O00', 13);
INSERT INTO `employee_project` VALUES (120, 118, b'00', '20200002O00', 10);
INSERT INTO `employee_project` VALUES (121, 118, b'00', '20200002O00', 12);
INSERT INTO `employee_project` VALUES (122, 118, b'00', '20200002O00', 15);
INSERT INTO `employee_project` VALUES (123, 118, b'00', '20200002O00', 23);
INSERT INTO `employee_project` VALUES (124, 118, b'00', '20200002O00', 24);
INSERT INTO `employee_project` VALUES (125, 118, b'00', '20200002O00', 21);
INSERT INTO `employee_project` VALUES (126, 118, b'00', '20200002O00', 19);
INSERT INTO `employee_project` VALUES (127, 108, b'01', '20200002O00', 14);
INSERT INTO `employee_project` VALUES (128, 127, b'00', '20200002O00', 11);
INSERT INTO `employee_project` VALUES (129, 82, b'00', '20200001O00', 1);
INSERT INTO `employee_project` VALUES (131, 78, b'00', '20200001O00', 14);
INSERT INTO `employee_project` VALUES (132, 62, b'00', '20200001D00', 5);
INSERT INTO `employee_project` VALUES (133, NULL, b'11', '20200003S00', 3);
INSERT INTO `employee_project` VALUES (134, NULL, b'11', '20200001M03', 3);
INSERT INTO `employee_project` VALUES (135, NULL, b'11', '20200003D00', 3);

-- ----------------------------
-- Table structure for employee_role_project
-- ----------------------------
DROP TABLE IF EXISTS `employee_role_project`;
CREATE TABLE `employee_role_project`  (
  `employee_project_id` int(11) NOT NULL COMMENT 'epid删除时连级删除此字段',
  `role` enum('pm','rd_leader','qa_leader','rd','qa','epg') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '\'pm\',\'rd_leader\',\'qa_leader\',\'rd\',\'qa\',\'epg\'',
  PRIMARY KEY (`employee_project_id`, `role`) USING BTREE,
  INDEX `employee_role_project_ibfk_1`(`employee_project_id`) USING BTREE,
  CONSTRAINT `employee_role_project_ibfk_1` FOREIGN KEY (`employee_project_id`) REFERENCES `employee_project` (`epid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of employee_role_project
-- ----------------------------
INSERT INTO `employee_role_project` VALUES (1, 'pm');
INSERT INTO `employee_role_project` VALUES (2, 'pm');
INSERT INTO `employee_role_project` VALUES (3, 'pm');
INSERT INTO `employee_role_project` VALUES (4, 'pm');
INSERT INTO `employee_role_project` VALUES (5, 'pm');
INSERT INTO `employee_role_project` VALUES (6, 'pm');
INSERT INTO `employee_role_project` VALUES (7, 'pm');
INSERT INTO `employee_role_project` VALUES (8, 'pm');
INSERT INTO `employee_role_project` VALUES (9, 'qa_leader');
INSERT INTO `employee_role_project` VALUES (10, 'qa');
INSERT INTO `employee_role_project` VALUES (11, 'qa');
INSERT INTO `employee_role_project` VALUES (12, 'qa_leader');
INSERT INTO `employee_role_project` VALUES (13, 'rd');
INSERT INTO `employee_role_project` VALUES (13, 'qa');
INSERT INTO `employee_role_project` VALUES (14, 'qa');
INSERT INTO `employee_role_project` VALUES (15, 'qa_leader');
INSERT INTO `employee_role_project` VALUES (16, 'qa');
INSERT INTO `employee_role_project` VALUES (17, 'rd');
INSERT INTO `employee_role_project` VALUES (17, 'qa');
INSERT INTO `employee_role_project` VALUES (18, 'qa_leader');
INSERT INTO `employee_role_project` VALUES (19, 'qa');
INSERT INTO `employee_role_project` VALUES (21, 'qa_leader');
INSERT INTO `employee_role_project` VALUES (22, 'rd');
INSERT INTO `employee_role_project` VALUES (22, 'qa');
INSERT INTO `employee_role_project` VALUES (23, 'qa');
INSERT INTO `employee_role_project` VALUES (24, 'epg');
INSERT INTO `employee_role_project` VALUES (25, 'epg');
INSERT INTO `employee_role_project` VALUES (26, 'epg');
INSERT INTO `employee_role_project` VALUES (27, 'epg');
INSERT INTO `employee_role_project` VALUES (28, 'epg');
INSERT INTO `employee_role_project` VALUES (29, 'rd_leader');
INSERT INTO `employee_role_project` VALUES (30, 'rd');
INSERT INTO `employee_role_project` VALUES (31, 'rd_leader');
INSERT INTO `employee_role_project` VALUES (32, 'rd');
INSERT INTO `employee_role_project` VALUES (33, 'rd_leader');
INSERT INTO `employee_role_project` VALUES (34, 'rd');
INSERT INTO `employee_role_project` VALUES (35, 'rd_leader');
INSERT INTO `employee_role_project` VALUES (36, 'rd');
INSERT INTO `employee_role_project` VALUES (62, 'pm');
INSERT INTO `employee_role_project` VALUES (63, 'epg');
INSERT INTO `employee_role_project` VALUES (64, 'epg');
INSERT INTO `employee_role_project` VALUES (65, 'epg');
INSERT INTO `employee_role_project` VALUES (66, 'qa_leader');
INSERT INTO `employee_role_project` VALUES (67, 'qa');
INSERT INTO `employee_role_project` VALUES (68, 'qa');
INSERT INTO `employee_role_project` VALUES (69, 'rd_leader');
INSERT INTO `employee_role_project` VALUES (69, 'qa');
INSERT INTO `employee_role_project` VALUES (70, 'rd');
INSERT INTO `employee_role_project` VALUES (71, 'rd');
INSERT INTO `employee_role_project` VALUES (72, 'rd');
INSERT INTO `employee_role_project` VALUES (73, 'rd');
INSERT INTO `employee_role_project` VALUES (74, 'rd');
INSERT INTO `employee_role_project` VALUES (75, 'qa');
INSERT INTO `employee_role_project` VALUES (76, 'qa');
INSERT INTO `employee_role_project` VALUES (77, 'rd');
INSERT INTO `employee_role_project` VALUES (77, 'qa');
INSERT INTO `employee_role_project` VALUES (77, 'epg');
INSERT INTO `employee_role_project` VALUES (78, 'pm');
INSERT INTO `employee_role_project` VALUES (78, 'rd');
INSERT INTO `employee_role_project` VALUES (78, 'qa');
INSERT INTO `employee_role_project` VALUES (78, 'epg');
INSERT INTO `employee_role_project` VALUES (80, 'epg');
INSERT INTO `employee_role_project` VALUES (81, 'epg');
INSERT INTO `employee_role_project` VALUES (82, 'qa_leader');
INSERT INTO `employee_role_project` VALUES (83, 'qa');
INSERT INTO `employee_role_project` VALUES (85, 'rd_leader');
INSERT INTO `employee_role_project` VALUES (85, 'qa');
INSERT INTO `employee_role_project` VALUES (86, 'qa');
INSERT INTO `employee_role_project` VALUES (87, 'rd');
INSERT INTO `employee_role_project` VALUES (88, 'qa');
INSERT INTO `employee_role_project` VALUES (88, 'epg');
INSERT INTO `employee_role_project` VALUES (89, 'rd');
INSERT INTO `employee_role_project` VALUES (91, 'rd');
INSERT INTO `employee_role_project` VALUES (91, 'qa');
INSERT INTO `employee_role_project` VALUES (91, 'epg');
INSERT INTO `employee_role_project` VALUES (92, 'qa');
INSERT INTO `employee_role_project` VALUES (94, 'rd');
INSERT INTO `employee_role_project` VALUES (94, 'qa');
INSERT INTO `employee_role_project` VALUES (94, 'epg');
INSERT INTO `employee_role_project` VALUES (95, 'pm');
INSERT INTO `employee_role_project` VALUES (96, 'pm');
INSERT INTO `employee_role_project` VALUES (97, 'pm');
INSERT INTO `employee_role_project` VALUES (98, 'pm');
INSERT INTO `employee_role_project` VALUES (99, 'rd');
INSERT INTO `employee_role_project` VALUES (99, 'qa');
INSERT INTO `employee_role_project` VALUES (99, 'epg');
INSERT INTO `employee_role_project` VALUES (100, 'epg');
INSERT INTO `employee_role_project` VALUES (101, 'epg');
INSERT INTO `employee_role_project` VALUES (102, 'rd');
INSERT INTO `employee_role_project` VALUES (102, 'epg');
INSERT INTO `employee_role_project` VALUES (103, 'qa_leader');
INSERT INTO `employee_role_project` VALUES (104, 'qa');
INSERT INTO `employee_role_project` VALUES (105, 'rd_leader');
INSERT INTO `employee_role_project` VALUES (105, 'qa');
INSERT INTO `employee_role_project` VALUES (106, 'qa');
INSERT INTO `employee_role_project` VALUES (107, 'rd');
INSERT INTO `employee_role_project` VALUES (108, 'pm');
INSERT INTO `employee_role_project` VALUES (109, 'pm');
INSERT INTO `employee_role_project` VALUES (110, 'rd');
INSERT INTO `employee_role_project` VALUES (110, 'epg');
INSERT INTO `employee_role_project` VALUES (111, 'epg');
INSERT INTO `employee_role_project` VALUES (112, 'epg');
INSERT INTO `employee_role_project` VALUES (113, 'qa_leader');
INSERT INTO `employee_role_project` VALUES (114, 'rd');
INSERT INTO `employee_role_project` VALUES (114, 'qa');
INSERT INTO `employee_role_project` VALUES (115, 'rd_leader');
INSERT INTO `employee_role_project` VALUES (115, 'rd');
INSERT INTO `employee_role_project` VALUES (115, 'qa');
INSERT INTO `employee_role_project` VALUES (116, 'qa');
INSERT INTO `employee_role_project` VALUES (117, 'epg');
INSERT INTO `employee_role_project` VALUES (118, 'qa_leader');
INSERT INTO `employee_role_project` VALUES (119, 'rd');
INSERT INTO `employee_role_project` VALUES (119, 'qa');
INSERT INTO `employee_role_project` VALUES (120, 'qa');
INSERT INTO `employee_role_project` VALUES (121, 'rd');
INSERT INTO `employee_role_project` VALUES (121, 'qa');
INSERT INTO `employee_role_project` VALUES (122, 'rd');
INSERT INTO `employee_role_project` VALUES (122, 'qa');
INSERT INTO `employee_role_project` VALUES (123, 'qa');
INSERT INTO `employee_role_project` VALUES (124, 'qa');
INSERT INTO `employee_role_project` VALUES (125, 'qa');
INSERT INTO `employee_role_project` VALUES (126, 'qa');
INSERT INTO `employee_role_project` VALUES (127, 'rd_leader');
INSERT INTO `employee_role_project` VALUES (127, 'rd');
INSERT INTO `employee_role_project` VALUES (128, 'rd');
INSERT INTO `employee_role_project` VALUES (129, 'qa');
INSERT INTO `employee_role_project` VALUES (131, 'epg');
INSERT INTO `employee_role_project` VALUES (132, 'epg');
INSERT INTO `employee_role_project` VALUES (133, 'pm');
INSERT INTO `employee_role_project` VALUES (134, 'pm');
INSERT INTO `employee_role_project` VALUES (135, 'pm');

-- ----------------------------
-- Table structure for manhour
-- ----------------------------
DROP TABLE IF EXISTS `manhour`;
CREATE TABLE `manhour`  (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `fid` int(11) NULL DEFAULT NULL COMMENT '依靠后端从project的function的json中解析',
  `date` date NOT NULL,
  `starttime` datetime(0) NULL DEFAULT NULL,
  `endtime` datetime(0) NULL DEFAULT NULL,
  `status` enum('unfilled','unchecked','checked') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '\'unfilled\',\'unchecked\',\'checked\'',
  `employee_project_id` int(11) NOT NULL,
  `activity_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`mid`) USING BTREE,
  INDEX `employee_project_id`(`employee_project_id`) USING BTREE,
  INDEX `activity_id`(`activity_id`) USING BTREE,
  CONSTRAINT `manhour_ibfk_1` FOREIGN KEY (`employee_project_id`) REFERENCES `employee_project` (`epid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `manhour_ibfk_2` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`aid`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of manhour
-- ----------------------------
INSERT INTO `manhour` VALUES (2, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unfilled', 2, 1);
INSERT INTO `manhour` VALUES (3, 0, '2020-03-24', '2020-04-04 16:00:00', '2020-04-05 16:00:00', 'unfilled', 3, 5);
INSERT INTO `manhour` VALUES (4, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unfilled', 4, 2);
INSERT INTO `manhour` VALUES (5, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unfilled', 5, 7);
INSERT INTO `manhour` VALUES (6, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unfilled', 6, 8);
INSERT INTO `manhour` VALUES (7, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unfilled', 7, 1);
INSERT INTO `manhour` VALUES (8, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unfilled', 8, 3);
INSERT INTO `manhour` VALUES (9, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unfilled', 9, 2);
INSERT INTO `manhour` VALUES (11, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'checked', 11, 2);
INSERT INTO `manhour` VALUES (12, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unfilled', 12, 6);
INSERT INTO `manhour` VALUES (14, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unchecked', 14, 8);
INSERT INTO `manhour` VALUES (15, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'checked', 15, 2);
INSERT INTO `manhour` VALUES (17, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unchecked', 17, 1);
INSERT INTO `manhour` VALUES (18, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unchecked', 18, 11);
INSERT INTO `manhour` VALUES (21, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unfilled', 21, 1);
INSERT INTO `manhour` VALUES (22, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unfilled', 22, 3);
INSERT INTO `manhour` VALUES (23, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unfilled', 23, 2);
INSERT INTO `manhour` VALUES (25, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unfilled', 25, 2);
INSERT INTO `manhour` VALUES (26, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unchecked', 26, 4);
INSERT INTO `manhour` VALUES (28, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unfilled', 28, 7);
INSERT INTO `manhour` VALUES (29, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unfilled', 29, 8);
INSERT INTO `manhour` VALUES (30, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'checked', 30, 3);
INSERT INTO `manhour` VALUES (31, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'checked', 31, 2);
INSERT INTO `manhour` VALUES (32, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unfilled', 32, 1);
INSERT INTO `manhour` VALUES (33, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unchecked', 33, 2);
INSERT INTO `manhour` VALUES (34, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unfilled', 34, 4);
INSERT INTO `manhour` VALUES (35, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unfilled', 35, 6);
INSERT INTO `manhour` VALUES (36, 0, '2020-03-24', '2020-03-24 14:14:20', '2020-03-25 14:14:29', 'unfilled', 36, 8);
INSERT INTO `manhour` VALUES (38, 0, '2020-04-04', '2020-04-08 17:00:00', '2020-04-09 18:00:00', 'checked', 63, 1);
INSERT INTO `manhour` VALUES (41, 0, '2020-04-04', '2020-03-30 16:00:00', '2020-03-31 16:00:00', 'unchecked', 16, 3);
INSERT INTO `manhour` VALUES (44, 0, '2020-04-05', '2020-04-07 08:00:00', '2020-04-08 08:00:00', 'unfilled', 29, 17);
INSERT INTO `manhour` VALUES (47, 0, '2020-04-06', '2020-03-28 16:00:00', '2020-03-29 16:00:00', 'unchecked', 16, 7);
INSERT INTO `manhour` VALUES (49, 0, '2020-04-06', '2020-04-27 16:00:00', '2020-04-28 16:00:00', 'checked', 16, 4);
INSERT INTO `manhour` VALUES (50, 0, '2020-04-06', '2020-04-18 16:00:00', '2020-04-20 16:00:00', 'checked', 16, 14);
INSERT INTO `manhour` VALUES (54, 0, '2020-04-06', '2020-04-06 08:14:10', '2020-04-06 17:14:17', 'unfilled', 34, 8);
INSERT INTO `manhour` VALUES (55, 0, '2020-04-07', '2020-04-07 00:00:00', '2020-04-07 15:59:00', 'unchecked', 12, 20);
INSERT INTO `manhour` VALUES (56, 0, '2020-04-07', '2020-04-06 16:00:00', '2020-04-07 15:59:59', 'unchecked', 12, 1);
INSERT INTO `manhour` VALUES (57, 0, '2020-04-07', '2020-04-07 13:58:52', '2020-04-08 13:58:58', 'checked', 12, 10);
INSERT INTO `manhour` VALUES (58, 0, '2020-04-08', '2020-04-07 16:12:14', '2020-04-08 12:12:16', 'checked', 26, 1);
INSERT INTO `manhour` VALUES (60, 0, '2020-04-08', '2020-04-08 16:00:00', '2020-04-08 12:19:33', 'checked', 29, 1);
INSERT INTO `manhour` VALUES (67, 1, '2020-04-08', '2020-04-09 00:00:00', '2020-04-10 00:00:00', 'checked', 83, 1);
INSERT INTO `manhour` VALUES (68, 1, '2020-04-08', '2020-04-09 00:00:00', '2020-04-10 00:00:00', 'checked', 83, 1);
INSERT INTO `manhour` VALUES (69, 0, '2020-04-08', '2020-04-07 12:47:25', '2020-04-08 11:47:31', 'unfilled', 34, 14);
INSERT INTO `manhour` VALUES (70, 0, '2020-04-08', '2020-04-08 20:53:49', '2020-04-08 20:53:52', 'unfilled', 19, 17);

-- ----------------------------
-- Table structure for milestone
-- ----------------------------
DROP TABLE IF EXISTS `milestone`;
CREATE TABLE `milestone`  (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `time` datetime(0) NULL DEFAULT NULL,
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `project_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`mid`) USING BTREE,
  INDEX `project_id`(`project_id`) USING BTREE,
  CONSTRAINT `milestone_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of milestone
-- ----------------------------
INSERT INTO `milestone` VALUES (1, '2020-03-10 16:41:20', '完成输出1', '20200001D01');
INSERT INTO `milestone` VALUES (2, '2020-03-28 16:41:35', '完成输入2', '20200002D01');
INSERT INTO `milestone` VALUES (3, '2020-03-28 16:41:35', '完成输入3', '20200004S01');
INSERT INTO `milestone` VALUES (4, '2020-03-28 16:41:35', '完成输入4', '20200003O01');
INSERT INTO `milestone` VALUES (5, '2020-03-28 16:41:35', '完成输入5', '20200002D02');
INSERT INTO `milestone` VALUES (6, '2020-03-28 16:41:12', '完成输入6', '20200001M02');
INSERT INTO `milestone` VALUES (7, '2020-03-28 16:41:35', '完成输入7', '20200002D03');
INSERT INTO `milestone` VALUES (8, '2020-03-28 16:41:35', '完成输入8', '20200003O02');
INSERT INTO `milestone` VALUES (9, '2020-03-28 16:41:35', '完成输入15', '20200001D01');
INSERT INTO `milestone` VALUES (10, '2020-03-18 16:41:35', '完成输入9', '20200002D01');
INSERT INTO `milestone` VALUES (11, '2020-03-28 16:41:35', '完成输入10', '20200004S01');
INSERT INTO `milestone` VALUES (12, '2020-03-28 16:41:35', '完成输入11', '20200003O01');
INSERT INTO `milestone` VALUES (13, '2020-03-28 16:41:35', '完成输入12', '20200002D02');
INSERT INTO `milestone` VALUES (14, '2020-03-07 16:41:35', '完成输入13', '20200001M02');
INSERT INTO `milestone` VALUES (15, '2020-03-05 16:41:35', '完成输入14', '20200002D03');
INSERT INTO `milestone` VALUES (16, '2020-03-21 19:30:52', '输入15', '20200003O02');
INSERT INTO `milestone` VALUES (17, '2020-04-10 16:00:00', '哈哈哈', '20200001M02');

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `pid` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `starttime` datetime(0) NULL DEFAULT NULL,
  `endtime` datetime(0) NULL DEFAULT NULL,
  `technique` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `domain` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `function` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用json存储，使用两个int解析',
  `client_id` int(11) NOT NULL,
  `workflow_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`pid`) USING BTREE,
  INDEX `client_id`(`client_id`) USING BTREE,
  INDEX `workflow_id`(`workflow_id`) USING BTREE,
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`cid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `project_ibfk_2` FOREIGN KEY (`workflow_id`) REFERENCES `workflow` (`wid`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('20200001D00', '狗子', '2020-04-08 16:00:00', '2020-04-09 16:00:00', '没技术', '也没有', '{\"001000\":\"功能1\",\"001001\":\"功能1-1\",\"001002\":\"功能1-2\",\"001003\":\"功能1-3\",\"002000\":\"功能2\",\"002001\":\"功能2-1\",\"002002\":\"功能2-2\",\"003000\":\"功能3\",\"003001\":\"功能3-1\",\"003002\":\"功能3-2\",\"003003\":\"功能3-3\",\"003004\":\"功能3-4\"}', 1, 47);
INSERT INTO `project` VALUES ('20200001D01', '立项拒绝了的项目', '2020-03-04 15:19:34', '2020-03-28 15:19:37', '没有技术含量的', '01语言', '{\"001000\":\"能用就行\"}', 1, 1);
INSERT INTO `project` VALUES ('20200001M00', '刚创建的项目', '2020-04-08 16:00:00', '2020-05-21 16:00:00', '大猪头技术', '大猪蹄领域', NULL, 1, 49);
INSERT INTO `project` VALUES ('20200001M01', '我就是要再试一下workflow', '2020-04-01 00:00:00', '2020-05-29 00:00:00', '测试测试测试测试测试测试', '测试测试测试测试测试测试', '{\"001000\":\"测试一下\",\"001001\":\"二级 1-1\",\"002000\":\"一级 2\",\"002001\":\"二级 2-2\",\"002002\":\"二级 2-1\",\"003000\":\"一级 3\",\"003001\":\"test\",\"004000\":\"一级 4\"}', 1, 54);
INSERT INTO `project` VALUES ('20200001M02', 'PM开始交付的项目', '2020-03-05 00:41:57', '2020-03-22 00:42:01', '有极高技术含量', '01语言', '{\"001000\":\"不可能的\",\"001001\":\"绝对不可能的\",\"001002\":\"绝对绝对不可能的\"}', 1, 6);
INSERT INTO `project` VALUES ('20200001M03', '测试项目1', '2020-04-05 00:00:00', '2020-05-27 00:00:00', '小技术', '小领域', NULL, 1, 56);
INSERT INTO `project` VALUES ('20200001O00', '刚创建的项目2', '2020-04-08 16:00:00', '2020-05-21 16:00:00', '大猪头技术不ok！！？陈老师？？？', '大猪蹄领域', '{\"001000\":\"测试一下\",\"001001\":\"二级 1-1\",\"002000\":\"一级 2\",\"002001\":\"二级 2-2\",\"002002\":\"二级 2-1\",\"003000\":\"一级 3\",\"003001\":\"test\",\"004000\":\"一级 4\"}', 1, 48);
INSERT INTO `project` VALUES ('20200001O01', '12341234123', '2020-05-12 16:00:00', '2020-05-28 16:00:00', '123412341', '21341234', '{\"001000\":\"功能1\",\"001001\":\"功能1-1\",\"001002\":\"功能1-2\",\"001003\":\"功能1-3\",\"002000\":\"功能2\",\"002001\":\"功能3-1\",\"002002\":\"功能2-1\",\"002003\":\"功能2-2\",\"003000\":\"功能3\",\"003001\":\"功能3-2\",\"003002\":\"功能3-3\",\"003003\":\"功能3-4\"}', 1, 50);
INSERT INTO `project` VALUES ('20200001O02', 'new 菜狗子', '2020-04-10 13:20:27', '2021-04-22 00:00:00', 'tech', 'domain', '{}', 1, 51);
INSERT INTO `project` VALUES ('20200001O03', 'new project', '2020-04-09 00:00:00', '2020-05-22 00:00:00', 'tech', 'domain', '{\"001000\":\"功能1\",\"002000\":\"功能2\",\"003000\":\"功能3\"}', 1, 52);
INSERT INTO `project` VALUES ('20200002D01', '申请立项的项目', '2020-03-03 23:50:27', '2020-04-24 23:50:33', '没有技术含量的', '01语言', '{\"001000\":\"做梦去吧\"}', 2, 2);
INSERT INTO `project` VALUES ('20200002D02', 'PM也配置完成了，启动的项目', '2020-03-05 00:41:57', '2020-03-22 00:42:01', '有极高技术含量', '01语言', '{\"001000\":\"不可能的\"}', 2, 5);
INSERT INTO `project` VALUES ('20200002D03', '结束了准备归档的项目', '2020-03-05 00:41:57', '2020-03-22 00:42:01', '有极高技术含量', '01语言', '{\"001000\":\"不可能的\"}', 2, 7);
INSERT INTO `project` VALUES ('20200002O00', '我来试试workflow', '2020-04-10 00:00:00', '2020-05-12 00:00:00', '没得得', '没得得', '{\"001000\":\"父亲1\",\"001001\":\"二级 1-1\",\"002000\":\"父亲2\",\"002001\":\"二级 2-2\",\"002002\":\"二级 2-1\",\"003000\":\"父亲3\",\"003001\":\"test\",\"004000\":\"父亲4\"}', 2, 53);
INSERT INTO `project` VALUES ('20200003D00', '测试项目2', '2020-07-07 00:00:00', '2020-06-19 00:00:00', '大技术', '大领域', NULL, 3, 57);
INSERT INTO `project` VALUES ('20200003O01', '三位老板配置完成了的项目', '2020-03-05 00:41:57', '2020-03-22 00:42:01', '有极高技术含量', '01语言', '{\"001000\":\"不可能的\"}', 3, 4);
INSERT INTO `project` VALUES ('20200003O02', '完成归档的项目', '2020-03-05 00:41:57', '2020-03-22 00:42:01', '有极高技术含量', '01语言', '{\"001000\":\"不可能的\"}', 3, 8);
INSERT INTO `project` VALUES ('20200003S00', '测试项目1', '2020-04-13 00:00:00', '2020-05-13 00:00:00', '没什么技术', '小测试', NULL, 3, 55);
INSERT INTO `project` VALUES ('20200004S01', '被批准了准备配置的项目', '2020-03-05 00:41:57', '2020-03-22 00:42:01', '有极高技术含量', '01语言', '{\"001000\":\"不可能的\"}', 4, 3);

-- ----------------------------
-- Table structure for property
-- ----------------------------
DROP TABLE IF EXISTS `property`;
CREATE TABLE `property`  (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of property
-- ----------------------------
INSERT INTO `property` VALUES (1, '护颈仪');
INSERT INTO `property` VALUES (2, '蒸汽眼罩');
INSERT INTO `property` VALUES (3, 'k380键盘');
INSERT INTO `property` VALUES (4, '罗技键盘');
INSERT INTO `property` VALUES (5, '罗技鼠标');
INSERT INTO `property` VALUES (6, '阿米拉');
INSERT INTO `property` VALUES (7, 'mbp');
INSERT INTO `property` VALUES (8, 'mb air');
INSERT INTO `property` VALUES (9, 'asus screen');
INSERT INTO `property` VALUES (10, 'acer nb');
INSERT INTO `property` VALUES (11, 'acer mouse');
INSERT INTO `property` VALUES (12, 'logetect mouse');
INSERT INTO `property` VALUES (13, 'm590');
INSERT INTO `property` VALUES (14, 'k7380显示器');
INSERT INTO `property` VALUES (15, '宇宙飞行器');
INSERT INTO `property` VALUES (16, '航空小飞艇');
INSERT INTO `property` VALUES (17, '脚踏车');
INSERT INTO `property` VALUES (18, '三轮车');
INSERT INTO `property` VALUES (19, '1998年的电脑');
INSERT INTO `property` VALUES (20, 'indow xp');
INSERT INTO `property` VALUES (21, 'mac majive');
INSERT INTO `property` VALUES (22, '米忽悠');
INSERT INTO `property` VALUES (23, '米老鼠');
INSERT INTO `property` VALUES (24, '居酒屋');
INSERT INTO `property` VALUES (25, '阿卡斯');
INSERT INTO `property` VALUES (26, '翅膀');
INSERT INTO `property` VALUES (27, '鸡翅');
INSERT INTO `property` VALUES (28, '鸡腿');
INSERT INTO `property` VALUES (29, '主题');
INSERT INTO `property` VALUES (30, '猪蹄');
INSERT INTO `property` VALUES (31, '天堂');
INSERT INTO `property` VALUES (32, '地狱');
INSERT INTO `property` VALUES (33, '狱长');
INSERT INTO `property` VALUES (34, '长辈的护符');
INSERT INTO `property` VALUES (35, '抚州的板凳');
INSERT INTO `property` VALUES (36, '登山鞋');
INSERT INTO `property` VALUES (37, '鞋胶');
INSERT INTO `property` VALUES (38, '胶原蛋白美白粉');
INSERT INTO `property` VALUES (39, '粉色小猪粗铅管');
INSERT INTO `property` VALUES (40, '试管');
INSERT INTO `property` VALUES (41, '管大校');
INSERT INTO `property` VALUES (42, '校长的热狗');
INSERT INTO `property` VALUES (43, '狗儿子36e');
INSERT INTO `property` VALUES (44, '动物园的门票');

-- ----------------------------
-- Table structure for property_occupy
-- ----------------------------
DROP TABLE IF EXISTS `property_occupy`;
CREATE TABLE `property_occupy`  (
  `poid` int(11) NOT NULL AUTO_INCREMENT,
  `expire_time` datetime(0) NULL DEFAULT NULL,
  `is_intact` bit(1) NOT NULL,
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
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of property_occupy
-- ----------------------------
INSERT INTO `property_occupy` VALUES (1, '2020-03-13 01:37:03', b'1', 1, '20200001D01', 2);
INSERT INTO `property_occupy` VALUES (2, '2020-03-11 01:38:42', b'1', 2, '20200002D01', 3);
INSERT INTO `property_occupy` VALUES (3, '2020-03-03 01:39:41', b'0', 3, '20200004S01', 2);
INSERT INTO `property_occupy` VALUES (4, '2020-03-03 01:39:41', b'1', 4, '20200003O01', 3);
INSERT INTO `property_occupy` VALUES (5, '2020-03-03 01:39:41', b'1', 5, '20200002D02', 3);
INSERT INTO `property_occupy` VALUES (6, '2020-03-03 01:39:41', b'1', 6, '20200001M02', 2);
INSERT INTO `property_occupy` VALUES (7, '2020-03-03 01:39:41', b'1', 7, '20200002D03', 2);
INSERT INTO `property_occupy` VALUES (8, '2020-03-03 01:39:41', b'1', 8, '20200003O02', 3);
INSERT INTO `property_occupy` VALUES (9, '2020-03-03 01:39:41', b'1', 9, '20200003O01', 4);
INSERT INTO `property_occupy` VALUES (10, '2020-03-03 01:39:41', b'1', 10, '20200003O01', 10);
INSERT INTO `property_occupy` VALUES (11, '2020-03-03 01:39:41', b'1', 11, '20200003O01', 13);
INSERT INTO `property_occupy` VALUES (12, '2020-03-03 01:39:41', b'1', 12, '20200002D02', 4);
INSERT INTO `property_occupy` VALUES (13, '2020-03-03 01:39:41', b'1', 13, '20200002D02', 11);
INSERT INTO `property_occupy` VALUES (14, '2020-03-03 01:39:41', b'1', 14, '20200002D02', 14);
INSERT INTO `property_occupy` VALUES (18, '2020-03-03 01:39:41', b'1', 18, '20200002D03', 4);
INSERT INTO `property_occupy` VALUES (19, '2020-03-03 01:39:41', b'1', 19, '20200002D03', 11);
INSERT INTO `property_occupy` VALUES (20, '2020-03-03 01:39:41', b'1', 20, '20200002D03', 13);
INSERT INTO `property_occupy` VALUES (21, '2020-03-03 01:39:41', b'1', 21, '20200003O02', 4);
INSERT INTO `property_occupy` VALUES (22, '2020-03-03 01:39:41', b'1', 22, '20200003O02', 13);
INSERT INTO `property_occupy` VALUES (23, '2020-03-03 01:39:41', b'1', 23, '20200003O02', 14);
INSERT INTO `property_occupy` VALUES (24, '2020-03-03 01:39:41', b'1', 24, '20200003O01', 5);
INSERT INTO `property_occupy` VALUES (25, '2020-03-03 01:39:41', b'1', 25, '20200002D02', 6);
INSERT INTO `property_occupy` VALUES (26, '2020-03-03 01:39:41', b'1', 26, '20200001M02', 5);
INSERT INTO `property_occupy` VALUES (27, '2020-04-07 15:09:35', b'1', 27, '20200002D03', 5);
INSERT INTO `property_occupy` VALUES (28, '2020-03-03 01:39:41', b'1', 28, '20200003O02', 6);
INSERT INTO `property_occupy` VALUES (29, '2020-03-03 01:39:41', b'1', 29, '20200002D02', 10);
INSERT INTO `property_occupy` VALUES (30, '2020-03-03 01:39:41', b'1', 30, '20200002D02', 13);
INSERT INTO `property_occupy` VALUES (31, '2020-03-03 01:39:41', b'1', 31, '20200001M02', 11);
INSERT INTO `property_occupy` VALUES (32, '2020-03-03 01:39:41', b'1', 32, '20200001M02', 13);
INSERT INTO `property_occupy` VALUES (33, '2020-03-03 01:39:41', b'1', 33, '20200002D03', 14);
INSERT INTO `property_occupy` VALUES (34, '2020-03-03 01:39:41', b'1', 34, '20200002D03', 12);
INSERT INTO `property_occupy` VALUES (35, '2020-03-03 01:39:41', b'1', 35, '20200003O02', 10);
INSERT INTO `property_occupy` VALUES (36, '2020-03-03 01:39:41', b'1', 36, '20200003O02', 12);
INSERT INTO `property_occupy` VALUES (44, '2020-04-07 16:00:00', b'1', 15, '20200001M02', 2);
INSERT INTO `property_occupy` VALUES (46, '2020-04-07 12:11:28', b'1', 1, '20200001M02', 2);
INSERT INTO `property_occupy` VALUES (47, '2030-05-01 00:00:00', b'1', 19, '20200002D03', 2);
INSERT INTO `property_occupy` VALUES (48, '2020-04-08 16:00:00', b'1', 18, '20200001M02', 2);
INSERT INTO `property_occupy` VALUES (49, '2020-04-22 16:00:00', b'0', 8, '20200002D02', 4);
INSERT INTO `property_occupy` VALUES (50, '2020-04-08 16:00:00', b'1', 1, '20200002D02', 4);
INSERT INTO `property_occupy` VALUES (51, '2020-04-14 16:00:00', b'1', 2, '20200001M02', 4);
INSERT INTO `property_occupy` VALUES (52, '2020-05-13 08:00:00', b'1', 13, '20200001M02', 4);
INSERT INTO `property_occupy` VALUES (53, '2020-04-07 15:56:00', b'1', 14, '20200002D03', 5);
INSERT INTO `property_occupy` VALUES (54, '2020-04-07 16:00:00', b'1', 6, '20200001M02', 5);
INSERT INTO `property_occupy` VALUES (55, '2020-04-07 16:00:57', b'0', 9, '20200001M02', 5);
INSERT INTO `property_occupy` VALUES (56, '2020-04-08 16:00:00', b'1', 17, '20200002D03', 5);
INSERT INTO `property_occupy` VALUES (57, '2020-04-07 17:50:55', b'0', 35, '20200001M02', 10);
INSERT INTO `property_occupy` VALUES (58, '2020-04-07 17:58:39', b'0', 12, '20200001M02', 10);
INSERT INTO `property_occupy` VALUES (59, '2021-04-30 16:00:00', b'1', 35, '20200001M02', 10);
INSERT INTO `property_occupy` VALUES (60, '2020-04-07 18:10:19', b'1', 5, '20200002D02', 10);
INSERT INTO `property_occupy` VALUES (61, '2020-04-08 02:14:52', b'1', 23, '20200001M02', 10);
INSERT INTO `property_occupy` VALUES (62, '2020-04-08 16:00:00', b'1', 44, '20200001O02', 2);
INSERT INTO `property_occupy` VALUES (63, '2020-05-05 08:33:43', b'1', 22, '20200002D03', 2);
INSERT INTO `property_occupy` VALUES (64, '2022-02-02 20:00:00', b'0', 24, '20200002D03', 12);
INSERT INTO `property_occupy` VALUES (65, '2020-04-09 10:10:18', b'1', 25, '20200002D02', 3);
INSERT INTO `property_occupy` VALUES (66, '2020-04-09 10:11:24', b'1', 1, '20200002D02', 3);
INSERT INTO `property_occupy` VALUES (67, '2020-04-09 10:13:41', b'0', 1, '20200002D02', 3);
INSERT INTO `property_occupy` VALUES (68, '2020-04-09 10:15:18', b'1', 15, '20200002D02', 3);
INSERT INTO `property_occupy` VALUES (69, '2040-12-31 18:15:32', b'1', 15, '20200002D02', 3);
INSERT INTO `property_occupy` VALUES (70, '2020-04-30 00:00:00', b'1', 43, '20200001M02', 2);
INSERT INTO `property_occupy` VALUES (71, '2020-05-31 00:00:00', b'1', 1, '20200001O03', 2);

-- ----------------------------
-- Table structure for risk
-- ----------------------------
DROP TABLE IF EXISTS `risk`;
CREATE TABLE `risk`  (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `grade` enum('p0','p1','p2','p3','p4','p5','p6','p7','p8','p9') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '\'p0\',\'p1\',\'p2\',\'p3\',\'p4\',\'p5\',\'p6\',\'p7\',\'p8\',\'p9\'',
  `influence` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `strategy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `frequency` int(11) NOT NULL COMMENT '代表以n天的频率提醒一次',
  `template` bit(1) NOT NULL DEFAULT b'0' COMMENT 'boolean - 代表是否为模板',
  `employee_id` int(11) NOT NULL,
  `project_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`rid`) USING BTREE,
  INDEX `employee_id`(`employee_id`) USING BTREE,
  INDEX `project_id`(`project_id`) USING BTREE,
  CONSTRAINT `risk_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`eid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `risk_ibfk_2` FOREIGN KEY (`project_id`) REFERENCES `project` (`pid`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of risk
-- ----------------------------
INSERT INTO `risk` VALUES (1, '员工离职', '感觉挺不好的把', 'p1', 'i1', 's1', 1, b'1', 2, '20200001D01');
INSERT INTO `risk` VALUES (2, '员工违纪被辞退', '感觉挺不好的把', 'p2', 'i2', 's2', 5, b'1', 3, '20200002D01');
INSERT INTO `risk` VALUES (3, '员工删库跑路', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 2, '20200004S01');
INSERT INTO `risk` VALUES (4, '全过程管理', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 3, '20200003O01');
INSERT INTO `risk` VALUES (5, '全员管理', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 3, '20200002D02');
INSERT INTO `risk` VALUES (6, '要素集成管理', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 2, '20200001M02');
INSERT INTO `risk` VALUES (7, '险管理不能', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 2, '20200002D03');
INSERT INTO `risk` VALUES (8, '项目质量的优劣与', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 3, '20200003O02');
INSERT INTO `risk` VALUES (9, '项目风险管理是对工期', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 4, '20200003O01');
INSERT INTO `risk` VALUES (10, '成功的项目风险', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 10, '20200003O01');
INSERT INTO `risk` VALUES (11, '项目风险管理工作', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 13, '20200003O01');
INSERT INTO `risk` VALUES (12, '向或政府行为', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 4, '20200002D02');
INSERT INTO `risk` VALUES (13, '不利因素威胁', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 11, '20200002D02');
INSERT INTO `risk` VALUES (14, '息源包括过', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 14, '20200002D02');
INSERT INTO `risk` VALUES (15, '进行潜在问题', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 4, '20200001M02');
INSERT INTO `risk` VALUES (16, '中记录的经验和表', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 10, '20200001M02');
INSERT INTO `risk` VALUES (17, '识别项目中的潜在风险及其特征', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 14, '20200001M02');
INSERT INTO `risk` VALUES (18, '识别风险的主要来源', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 4, '20200002D03');
INSERT INTO `risk` VALUES (19, '预测风险可能会引起的后果', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 11, '20200002D03');
INSERT INTO `risk` VALUES (21, '从主观信息源出发的方法', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 4, '20200003O02');
INSERT INTO `risk` VALUES (22, '头脑风暴法', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 13, '20200003O02');
INSERT INTO `risk` VALUES (23, '德尔菲法（Delphi method）又称专家调查法', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 14, '20200003O02');
INSERT INTO `risk` VALUES (24, '情景分析法（Scenarios analysis）', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 5, '20200003O01');
INSERT INTO `risk` VALUES (25, '构造出多重情景', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 6, '20200002D02');
INSERT INTO `risk` VALUES (26, '构造出多重情景', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 5, '20200001M02');
INSERT INTO `risk` VALUES (27, '项目范围、成本、质量、进度', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 5, '20200002D03');
INSERT INTO `risk` VALUES (28, '各分流程图', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 6, '20200003O02');
INSERT INTO `risk` VALUES (29, '核对表法。', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 10, '20200002D02');
INSERT INTO `risk` VALUES (30, '流程图法', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 13, '20200002D02');
INSERT INTO `risk` VALUES (31, '财务报表法', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 11, '20200001M02');
INSERT INTO `risk` VALUES (32, '基线费用估计', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 13, '20200001M02');
INSERT INTO `risk` VALUES (33, '寿命期费用分析', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 14, '20200002D03');
INSERT INTO `risk` VALUES (34, '系统工程文件', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 12, '20200002D03');
INSERT INTO `risk` VALUES (35, '进度分析', '感觉挺不好的把', 'p1', 'i2', 's2', 5, b'0', 10, '20200003O02');
INSERT INTO `risk` VALUES (37, 'some risk yo', 'description for the concrete risk yo', 'p3', 'filling as ur will yo', 'filling it plz yo', 2, b'0', 2, '20200001D00');
INSERT INTO `risk` VALUES (38, 'some risk', 'description for the concrete risk', 'p1', 'filling as ur will', 'filling it plz', 1, b'0', 2, '20200001D00');

-- ----------------------------
-- Table structure for risk_relation
-- ----------------------------
DROP TABLE IF EXISTS `risk_relation`;
CREATE TABLE `risk_relation`  (
  `employee_project_id` int(11) NOT NULL,
  `risk_id` int(11) NOT NULL,
  PRIMARY KEY (`employee_project_id`, `risk_id`) USING BTREE,
  INDEX `employee_project_id`(`employee_project_id`) USING BTREE,
  INDEX `risk_relation_ibfk_2`(`risk_id`) USING BTREE,
  CONSTRAINT `risk_relation_ibfk_1` FOREIGN KEY (`employee_project_id`) REFERENCES `employee_project` (`epid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `risk_relation_ibfk_2` FOREIGN KEY (`risk_id`) REFERENCES `risk` (`rid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of risk_relation
-- ----------------------------
INSERT INTO `risk_relation` VALUES (1, 1);
INSERT INTO `risk_relation` VALUES (2, 2);
INSERT INTO `risk_relation` VALUES (3, 3);
INSERT INTO `risk_relation` VALUES (4, 4);
INSERT INTO `risk_relation` VALUES (5, 5);
INSERT INTO `risk_relation` VALUES (6, 6);
INSERT INTO `risk_relation` VALUES (7, 7);
INSERT INTO `risk_relation` VALUES (8, 8);
INSERT INTO `risk_relation` VALUES (9, 9);
INSERT INTO `risk_relation` VALUES (10, 10);
INSERT INTO `risk_relation` VALUES (11, 11);
INSERT INTO `risk_relation` VALUES (12, 12);
INSERT INTO `risk_relation` VALUES (13, 13);
INSERT INTO `risk_relation` VALUES (14, 14);
INSERT INTO `risk_relation` VALUES (15, 15);
INSERT INTO `risk_relation` VALUES (16, 16);
INSERT INTO `risk_relation` VALUES (17, 17);
INSERT INTO `risk_relation` VALUES (18, 18);
INSERT INTO `risk_relation` VALUES (19, 19);
INSERT INTO `risk_relation` VALUES (21, 21);
INSERT INTO `risk_relation` VALUES (22, 22);
INSERT INTO `risk_relation` VALUES (23, 23);
INSERT INTO `risk_relation` VALUES (24, 24);
INSERT INTO `risk_relation` VALUES (25, 25);
INSERT INTO `risk_relation` VALUES (26, 26);
INSERT INTO `risk_relation` VALUES (27, 27);
INSERT INTO `risk_relation` VALUES (28, 28);
INSERT INTO `risk_relation` VALUES (29, 29);
INSERT INTO `risk_relation` VALUES (30, 30);
INSERT INTO `risk_relation` VALUES (31, 31);
INSERT INTO `risk_relation` VALUES (32, 32);
INSERT INTO `risk_relation` VALUES (33, 33);
INSERT INTO `risk_relation` VALUES (34, 34);
INSERT INTO `risk_relation` VALUES (35, 35);
INSERT INTO `risk_relation` VALUES (66, 38);
INSERT INTO `risk_relation` VALUES (67, 38);
INSERT INTO `risk_relation` VALUES (68, 38);
INSERT INTO `risk_relation` VALUES (70, 38);
INSERT INTO `risk_relation` VALUES (71, 38);
INSERT INTO `risk_relation` VALUES (72, 38);
INSERT INTO `risk_relation` VALUES (73, 37);
INSERT INTO `risk_relation` VALUES (73, 38);

-- ----------------------------
-- Table structure for timeline
-- ----------------------------
DROP TABLE IF EXISTS `timeline`;
CREATE TABLE `timeline`  (
  `workflow_id` int(11) NOT NULL,
  `operation_type` enum('rejected','applying','approved','started','delivering','submitted','achieved') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `add_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '\'rejected\',\'applying\',\'approved\',\'started\',\'delivering\',\'submitted\',\'achieved\'',
  `employee_id` int(11) NOT NULL,
  PRIMARY KEY (`workflow_id`, `operation_type`, `employee_id`) USING BTREE,
  INDEX `timeline_ibfk_2`(`employee_id`) USING BTREE,
  CONSTRAINT `timeline_ibfk_1` FOREIGN KEY (`workflow_id`) REFERENCES `workflow` (`wid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `timeline_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`eid`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of timeline
-- ----------------------------
INSERT INTO `timeline` VALUES (48, 'applying', '2020-03-26 20:57:12', 2);
INSERT INTO `timeline` VALUES (48, 'approved', '2020-04-01 20:57:36', 1);
INSERT INTO `timeline` VALUES (48, 'started', '2020-04-02 21:00:14', 2);
INSERT INTO `timeline` VALUES (48, 'delivering', '2020-04-02 21:00:23', 2);
INSERT INTO `timeline` VALUES (48, 'submitted', '2020-04-02 21:00:30', 2);
INSERT INTO `timeline` VALUES (49, 'applying', '2020-04-04 19:55:08', 2);
INSERT INTO `timeline` VALUES (50, 'applying', '2020-04-04 20:52:46', 2);
INSERT INTO `timeline` VALUES (50, 'approved', '2020-04-04 21:29:36', 1);
INSERT INTO `timeline` VALUES (50, 'started', '2020-04-09 22:41:43', 2);
INSERT INTO `timeline` VALUES (50, 'delivering', '2020-04-09 22:42:51', 2);
INSERT INTO `timeline` VALUES (50, 'submitted', '2020-04-09 22:43:42', 2);
INSERT INTO `timeline` VALUES (50, 'achieved', '2020-04-09 22:47:19', 7);
INSERT INTO `timeline` VALUES (51, 'applying', '2020-04-08 02:22:39', 2);
INSERT INTO `timeline` VALUES (51, 'approved', '2020-04-09 23:19:05', 1);
INSERT INTO `timeline` VALUES (52, 'applying', '2020-04-08 20:13:49', 2);
INSERT INTO `timeline` VALUES (52, 'approved', '2020-04-09 23:29:00', 1);
INSERT INTO `timeline` VALUES (53, 'rejected', '2020-04-09 23:11:02', 1);
INSERT INTO `timeline` VALUES (53, 'applying', '2020-04-09 23:00:23', 2);
INSERT INTO `timeline` VALUES (53, 'approved', '2020-04-10 00:05:45', 1);
INSERT INTO `timeline` VALUES (53, 'started', '2020-04-10 00:09:47', 2);
INSERT INTO `timeline` VALUES (53, 'delivering', '2020-04-10 00:09:58', 2);
INSERT INTO `timeline` VALUES (53, 'submitted', '2020-04-10 00:10:00', 2);
INSERT INTO `timeline` VALUES (53, 'achieved', '2020-04-10 00:10:39', 8);
INSERT INTO `timeline` VALUES (54, 'applying', '2020-04-09 23:32:19', 2);
INSERT INTO `timeline` VALUES (54, 'approved', '2020-04-09 23:32:48', 1);
INSERT INTO `timeline` VALUES (54, 'started', '2020-04-09 23:56:06', 2);
INSERT INTO `timeline` VALUES (54, 'delivering', '2020-04-09 23:56:08', 2);
INSERT INTO `timeline` VALUES (54, 'submitted', '2020-04-09 23:56:12', 2);
INSERT INTO `timeline` VALUES (55, 'applying', '2020-04-13 14:14:44', 3);
INSERT INTO `timeline` VALUES (56, 'applying', '2020-04-13 14:23:10', 3);
INSERT INTO `timeline` VALUES (57, 'applying', '2020-04-13 14:28:33', 3);

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
  `archive03` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目估算表(功能点)',
  `archive04` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目计划书',
  `archive05` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目过程裁剪表',
  `archive06` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目成本管理表',
  `archive07` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目需求变更管理表 ',
  `archive08` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目风险管理表 ',
  `archive09` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户验收问题表 ',
  `archive10` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户验收报告 ',
  `archive11` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目总结',
  `archive12` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最佳经验和教训 ',
  `archive13` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开发工具',
  `archive14` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开发模板(设计模板/测试模板)',
  `archive15` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '各阶段检查单',
  `archive16` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'QA总结',
  PRIMARY KEY (`wid`) USING BTREE,
  INDEX `sup_eid`(`sup_eid`) USING BTREE,
  INDEX `configurer_eid`(`configurer_eid`) USING BTREE,
  INDEX `epgleader_eid`(`epgleader_eid`) USING BTREE,
  INDEX `qamanager_eid`(`qamanager_eid`) USING BTREE,
  INDEX `workflow_ibfk_0`(`pm_eid`) USING BTREE,
  CONSTRAINT `workflow_ibfk_0` FOREIGN KEY (`pm_eid`) REFERENCES `employee` (`eid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `workflow_ibfk_1` FOREIGN KEY (`sup_eid`) REFERENCES `employee` (`eid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `workflow_ibfk_2` FOREIGN KEY (`configurer_eid`) REFERENCES `employee` (`eid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `workflow_ibfk_3` FOREIGN KEY (`epgleader_eid`) REFERENCES `employee` (`eid`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `workflow_ibfk_4` FOREIGN KEY (`qamanager_eid`) REFERENCES `employee` (`eid`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of workflow
-- ----------------------------
INSERT INTO `workflow` VALUES (1, 0, 2, 1, 8, 5, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `workflow` VALUES (2, 1, 3, 1, 7, 6, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `workflow` VALUES (3, 3, 2, 1, 7, 6, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `workflow` VALUES (4, 31, 3, 1, 8, 5, 4, 'https://github.com/ceej7/ProjMGT', '47.100.57.110', '*', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `workflow` VALUES (5, 511, 3, 1, 8, 6, 4, 'https://github.com/ceej7/ProjMGT', '47.100.57.110', '*', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `workflow` VALUES (6, 1023, 2, 1, 7, 5, 4, 'https://github.com/ceej7/ProjMGT', '47.100.57.110', '*', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `workflow` VALUES (7, 2047, 2, 1, 7, 5, 4, 'https://github.com/ceej7/ProjMGT', '47.100.57.110', '*', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `workflow` VALUES (8, 536870911, 3, 1, 8, 6, 4, 'https://github.com/ceej7/ProjMGT', '47.100.57.110', '*', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看');
INSERT INTO `workflow` VALUES (47, 536870911, 2, 1, 7, 5, 4, 'git.com', './home', 'hh', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看');
INSERT INTO `workflow` VALUES (48, 16383, 2, 1, 7, 5, 4, 'git.com', './home', 'hh', '你就随便填吧反正没人看', '你就随便填吧反正没人看', '你就随便填吧反正没人看', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `workflow` VALUES (49, 1, 2, 1, 7, 5, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `workflow` VALUES (50, 536870911, 2, 1, 7, 5, 4, 'www.github.com', 'achieveit_G03', 'achieveit_G03@ecnu.edu.cn', '12341234123 项目基础数据表', '12341234123 项目提案书', '12341234123 项目报价书', '12341234123 项目估算表(功能点)', '12341234123 项目计划书', '12341234123 项目过程裁剪表', '12341234123 项目成本管理表', '12341234123 项目需求变更管理表', '12341234123 项目风险管理表', '12341234123 客户验收问题表', '12341234123 客户验收报告', '12341234123 项目总结', '12341234123 最佳经验和教训', '12341234123 开发工具', '12341234123 开发模板(设计模板，测试模板)', '12341234123 各阶段检查单', '12341234123 QA 总结');
INSERT INTO `workflow` VALUES (51, 3, 2, 1, 7, 5, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `workflow` VALUES (52, 7, 2, 1, 7, 5, 4, 'www.github.com', 'achieveit_G03', 'achieveit_G03@ecnu.edu.cn', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `workflow` VALUES (53, 536870911, 2, 1, 8, 5, 4, 'www.github.com', 'achieveit_G03', 'achieveit_G03@ecnu.edu.cn', '我来试试workflow 项目基础数据表', '我来试试workflow 项目提案书', '我来试试workflow 项目报价书', '我来试试workflow 项目估算表(功能点)', '我来试试workflow 项目计划书', '我来试试workflow 项目过程裁剪表', '我来试试workflow 项目成本管理表', '我来试试workflow 项目需求变更管理表', '我来试试workflow 项目风险管理表', '我来试试workflow 客户验收问题表', '我来试试workflow 客户验收报告', '我来试试workflow 项目总结', '我来试试workflow 最佳经验和教训', '我来试试workflow 开发工具', '我来试试workflow 开发模板(设计模板，测试模板)', '我来试试workflow 各阶段检查单', '我来试试workflow QA 总结');
INSERT INTO `workflow` VALUES (54, 268435455, 2, 1, 7, 5, 4, 'www.github.com', 'achieveit_G03', 'achieveit_G03@ecnu.edu.cn', '我就是要再试一下workflow 项目基础数据表', '我就是要再试一下workflow 项目提案书', '我就是要再试一下workflow 项目报价书', '我就是要再试一下workflow 项目估算表(功能点)', '我就是要再试一下workflow 项目计划书', '我就是要再试一下workflow 项目过程裁剪表', '我就是要再试一下workflow 项目成本管理表', '我就是要再试一下workflow 项目需求变更管理表', '我就是要再试一下workflow 项目风险管理表', '我就是要再试一下workflow 客户验收问题表', '我就是要再试一下workflow 客户验收报告', '我就是要再试一下workflow 项目总结', '我就是要再试一下workflow 最佳经验和教训', '我就是要再试一下workflow 开发工具', '我就是要再试一下workflow 开发模板(设计模板，测试模板)', '我就是要再试一下workflow 各阶段检查单', '我就是要再试一下workflow QA 总结');
INSERT INTO `workflow` VALUES (55, 1, 3, 1, 7, 6, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `workflow` VALUES (56, 1, 3, 1, 7, 5, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `workflow` VALUES (57, 1, 3, 1, 7, 6, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
