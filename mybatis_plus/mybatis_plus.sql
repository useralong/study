/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50738
 Source Host           : localhost:3306
 Source Schema         : mybatis_plus

 Target Server Type    : MySQL
 Target Server Version : 50738
 File Encoding         : 65001

 Date: 29/08/2022 16:31:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `version` int(10) NULL DEFAULT 1 COMMENT '乐观锁',
  `deleted` int(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'allen222', 18, 'allen222@email', NULL, '2022-08-25 13:55:28', 3, 0);
INSERT INTO `user` VALUES (2, 'Jack', 20, 'test2@baomidou.com', NULL, '2022-08-25 13:55:28', 1, 0);
INSERT INTO `user` VALUES (3, 'Tom', 28, 'test3@baomidou.com', NULL, '2022-08-25 13:55:28', 1, 0);
INSERT INTO `user` VALUES (4, 'Sandy', 21, 'test4@baomidou.com', NULL, '2022-08-25 13:55:28', 1, 0);
INSERT INTO `user` VALUES (5, 'Billie', 24, 'test5@baomidou.com', NULL, '2022-08-25 13:55:28', 1, 0);
INSERT INTO `user` VALUES (6, 'AllenW', 19, 'A@email.com', '2022-08-25 11:38:49', '2022-08-25 13:55:28', 1, 0);
INSERT INTO `user` VALUES (7, 'AllenW', 19, 'A@email.com', '2022-08-25 11:51:08', '2022-08-25 13:55:41', 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
