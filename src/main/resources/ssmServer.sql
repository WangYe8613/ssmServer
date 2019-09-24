-- ----------------------------
--  直接导入该.sql文件到mysql，即可生成数据表
--  指令：mysql -h localhost -u root -p ssmServer < ssmServer.sql --default-character-set=utf8
-- ----------------------------

-- ----------------------------
--  创建数据库
-- ----------------------------
DROP DATABASE IF EXISTS `ssmServer`;
CREATE DATABASE ssmServer default character set=utf8;
use ssmServer;

-- ----------------------------
--  实体表：用于"增、删、改"操作
-- ----------------------------

-- ----------------------------
--  Table structure for `UserEO`
-- ----------------------------
DROP TABLE IF EXISTS `UserEO`;
CREATE TABLE `UserEO` (
  `uuid` varchar(32) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `token` varchar(128) DEFAULT NULL,
  `lastOpDate` timestamp NOT NULL DEFAULT '2019-01-01 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT 'last operation date',
  `createDate` timestamp NOT NULL DEFAULT '2019-01-01 00:00:00',
  `deleted` varchar(255) DEFAULT NULL,
  `description` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `uuid` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  视图：用于"查"操作，优点是更快，缺点是每次启动服务都需要重新生成
-- ----------------------------

-- ----------------------------
--  View structure for `UserVO`
-- ----------------------------
DROP VIEW IF EXISTS `UserVO`;
CREATE OR REPLACE VIEW `UserVO` AS select * from `UserEO` where isnull(`UserEO`.`deleted`);