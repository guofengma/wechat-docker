DROP DATABASE IF EXISTS `wechat`;
CREATE DATABASE IF NOT EXISTS `wechat` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `wechat`;

DROP TABLE IF EXISTS `db_version`;
CREATE TABLE `db_version` (
	`version` VARCHAR(20) NULL DEFAULT NULL COMMENT '数据库版本'
)
ENGINE=InnoDB
;

insert into db_version values ("1.0.0.0");


DROP TABLE IF EXISTS `token`;
CREATE TABLE `token` (
	`id` BIGINT(128) UNSIGNED NOT NULL COMMENT '主键',
	`content` VARCHAR(512) NOT NULL DEFAULT '' COMMENT 'token内容',
	`expire_date` DATETIME NULL DEFAULT NULL COMMENT '过期时间',
	PRIMARY KEY(`id`)
)
ENGINE=InnoDB
;

UPDATE db_version SET version = '1.0.0.1';