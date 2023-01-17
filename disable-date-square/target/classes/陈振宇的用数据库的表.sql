/*
SQLyog Professional v12.2.6 (64 bit)
MySQL - 8.0.29 : Database - disable-date
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`disable-date` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `disable-date`;

/*Table structure for table `tb_disable_date_admin_activity` */

DROP TABLE IF EXISTS `tb_disable_date_admin_activity`;

CREATE TABLE `tb_disable_date_admin_activity` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '活动id',
  `name` varchar(100) DEFAULT NULL COMMENT '活动名称',
  `cover_path` varchar(50) DEFAULT NULL COMMENT '活动封面路径',
  `place` varchar(20) DEFAULT NULL COMMENT '活动投放位置',
  `activity_status` int DEFAULT NULL COMMENT '活动状态 0-结束报名 1-报名中',
  `concern_amount` bigint DEFAULT NULL COMMENT '关注量',
  `sign_amount` int DEFAULT NULL COMMENT '报名人数',
  `number` int DEFAULT NULL COMMENT '序号',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `open_status` int DEFAULT NULL COMMENT '是否开放报名 1-是 0-否',
  `release_status` int DEFAULT NULL COMMENT '是否发布了 1-是 0-否',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tb_disable_date_admin_activity` */

insert  into `tb_disable_date_admin_activity`(`id`,`name`,`cover_path`,`place`,`activity_status`,`concern_amount`,`sign_amount`,`number`,`start_time`,`end_time`,`open_status`,`release_status`,`create_time`,`update_time`) values 
(1,'全国聋哑人线上交友会','cdvdbbbfdb','约吧',1,124,2141,1,'2022-07-30 16:54:07','2022-07-29 01:54:10',NULL,NULL,'2022-07-29 16:52:44','2022-07-29 16:54:33'),
(2,'全国聋哑人线上交友会','cdvdbbbfdb','约吧',1,214,2141,2,'2022-07-30 16:54:48','2022-07-31 16:54:52',NULL,NULL,'2022-07-29 16:53:51','2022-07-29 16:54:59');

/*Table structure for table `tb_disable_date_admin_activity_register` */

DROP TABLE IF EXISTS `tb_disable_date_admin_activity_register`;

CREATE TABLE `tb_disable_date_admin_activity_register` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(10) DEFAULT NULL COMMENT '用户id',
  `activity_id` bigint DEFAULT NULL COMMENT '活动id',
  `user_phone` varchar(11) DEFAULT NULL COMMENT '用户电话',
  `status` int DEFAULT '2' COMMENT '审核状态 0-不通过 1-通过 2-待审核',
  `ps` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '审核不通过的原因说明',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户申请报名的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tb_disable_date_admin_activity_register` */

insert  into `tb_disable_date_admin_activity_register`(`id`,`user_id`,`activity_id`,`user_phone`,`status`,`ps`,`create_time`) values 
(1,'1',1,'13330245687',2,NULL,'2022-08-06 17:35:47'),
(2,'1',1,'13330245687',2,NULL,'2022-08-06 17:35:54'),
(3,'1',1,'13330245687',2,NULL,'2022-08-06 17:35:55'),
(4,'1',1,'13330245687',2,NULL,'2022-08-06 17:35:56'),
(5,'1',1,'13330245687',2,NULL,'2022-08-06 17:35:56'),
(6,'1',1,'13330245687',2,NULL,'2022-08-06 17:40:32'),
(7,'1',1,'13330245687',2,NULL,'2022-08-06 17:40:33'),
(8,'1',1,'13330245687',2,NULL,'2022-08-06 17:40:34');

/*Table structure for table `tb_disable_date_admin_advertising` */

DROP TABLE IF EXISTS `tb_disable_date_admin_advertising`;

CREATE TABLE `tb_disable_date_admin_advertising` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ordinal` int DEFAULT NULL COMMENT '序号',
  `title` varchar(100) DEFAULT NULL COMMENT '广告标题',
  `place` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '所属广告位',
  `descrip` varchar(100) DEFAULT NULL COMMENT '广告描述',
  `appearence_path` varchar(50) DEFAULT NULL COMMENT '广告封面途径',
  `status` int DEFAULT NULL COMMENT '广告状态 0-禁用 1-正常',
  `advertising_links` varchar(50) DEFAULT NULL COMMENT '广告链接',
  `advertised_model` varchar(20) DEFAULT NULL COMMENT '广告型号',
  `view_amount` bigint DEFAULT NULL COMMENT '浏览量',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tb_disable_date_admin_advertising` */

insert  into `tb_disable_date_admin_advertising`(`id`,`ordinal`,`title`,`place`,`descrip`,`appearence_path`,`status`,`advertising_links`,`advertised_model`,`view_amount`,`start_time`,`end_time`,`create_time`,`update_time`) values 
(3,3,'single dog','111','1','cd/sf/asg/sg',1,'http://localhost:8080','50 * 50',124124,'2022-07-25 14:39:00','2022-07-31 16:27:16','2022-07-29 16:26:13','2022-07-29 21:07:53'),
(4,2,'single dog',NULL,NULL,NULL,NULL,'http://localhost:8080',NULL,NULL,NULL,NULL,'2022-07-30 20:45:20','2022-08-06 14:53:41');

/*Table structure for table `tb_disable_date_admin_advertising_place_manage` */

DROP TABLE IF EXISTS `tb_disable_date_admin_advertising_place_manage`;

CREATE TABLE `tb_disable_date_admin_advertising_place_manage` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '广告位id',
  `name` varchar(50) DEFAULT NULL COMMENT '广告位名称',
  `description` varchar(30) DEFAULT NULL COMMENT '广告位描述',
  `use_status` int DEFAULT NULL COMMENT '广告使用状态 0-未使用 1-使用',
  `type` int DEFAULT NULL COMMENT '栏目类型',
  `advertising_name` varchar(50) DEFAULT NULL COMMENT '已插入的广告名称',
  `advertising_id` int DEFAULT NULL COMMENT '已插入的广告id 0-没有 非0-有',
  `advertising_status` int DEFAULT NULL COMMENT '广告的状态 0—待使用 1-使用',
  `number` int DEFAULT NULL COMMENT '序号',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tb_disable_date_admin_advertising_place_manage` */

insert  into `tb_disable_date_admin_advertising_place_manage`(`id`,`name`,`description`,`use_status`,`type`,`advertising_name`,`advertising_id`,`advertising_status`,`number`,`create_time`,`update_time`) values 
(1,'首页全屏弹框','首页全屏弹框，用户第一时间可以看到的最优质广告位',0,0,'0',1,0,1,'2022-07-14 16:34:44','2022-08-02 11:26:04'),
(2,'广场精彩多多','小按钮广告，通过广场模块的悬浮按钮进入，该广告最持久',1,0,'1',2,1,2,'2022-07-13 16:35:12','2022-08-02 11:26:04'),
(3,'动态插入广告','在动态列表中插入广告用户，可以出现的频率最多',1,0,'1',3,1,2,'2022-08-02 11:24:01','2022-08-02 11:26:08');

/*Table structure for table `tb_disable_date_admin_audit_logs` */

DROP TABLE IF EXISTS `tb_disable_date_admin_audit_logs`;

CREATE TABLE `tb_disable_date_admin_audit_logs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` int DEFAULT NULL COMMENT '审核类型 1-图文动态审核 2-资料审核 3-视频审核 4-举报受理',
  `audit_status` int DEFAULT NULL COMMENT '审核状态 0-未通过 1-审核钟 2-通过',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `accept_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '受理时间',
  `admin_name` varchar(20) DEFAULT NULL COMMENT '审核员名字',
  `admin_id` int DEFAULT NULL COMMENT '审核员id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tb_disable_date_admin_audit_logs` */

insert  into `tb_disable_date_admin_audit_logs`(`id`,`type`,`audit_status`,`create_time`,`accept_time`,`admin_name`,`admin_id`) values 
(1,1,1,'2022-08-02 17:26:08','2022-08-02 17:26:08','钟国政',1),
(2,2,1,'2022-08-02 17:26:10','2022-08-02 17:26:10','钟国证',2),
(3,3,1,'2022-08-02 17:26:10','2022-08-02 17:26:10','钟国真',3),
(4,4,1,'2022-08-02 17:26:11','2022-08-02 17:26:11','钟国震',4),
(5,1,0,'2022-08-02 17:26:12','2022-08-02 17:26:12','钟国针',5),
(6,2,0,'2022-08-02 17:26:12','2022-08-02 17:26:12','钟国征',6),
(7,3,1,'2022-08-02 17:26:14','2022-08-02 17:26:14','钟国振',7),
(8,4,0,'2022-08-02 17:26:17','2022-08-02 17:26:17','钟国朕',8);

/*Table structure for table `tb_disable_date_admin_childrentypes` */

DROP TABLE IF EXISTS `tb_disable_date_admin_childrentypes`;

CREATE TABLE `tb_disable_date_admin_childrentypes` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '内容的id',
  `parent_name` varchar(30) DEFAULT NULL COMMENT '关联（父级）名称',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `name_id` int DEFAULT NULL COMMENT '孩子的id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tb_disable_date_admin_childrentypes` */

insert  into `tb_disable_date_admin_childrentypes`(`id`,`parent_name`,`name`,`name_id`) values 
(1,'广告类型','图片',0),
(2,'广告类型','文字',1),
(3,'广告类型','视频',2),
(4,'广告类型','推荐',3),
(5,'所属广告位','首页弹框',0),
(6,'所属广告位','就业模块',1),
(7,'所属广告位','社交按钮',2),
(8,'所属广告位','活动链接',3),
(9,'所属广告位','互动推荐',4),
(10,'主题颜色','浪漫粉',0),
(11,'主题颜色','炫酷黑',1),
(12,'主题颜色','优雅紫',2),
(13,'主题颜色','飘雪白',3),
(14,'主题颜色','玛瑙红',4),
(15,'广告位大小','全屏',0),
(16,'广告位大小','中屏',1),
(17,'广告位大小','右侧按钮',2),
(18,'广告位大小','图片',3);

/*Table structure for table `tb_disable_date_admin_festival` */

DROP TABLE IF EXISTS `tb_disable_date_admin_festival`;

CREATE TABLE `tb_disable_date_admin_festival` (
  `id` int NOT NULL AUTO_INCREMENT,
  `festival_name` varchar(20) DEFAULT NULL COMMENT '节日名称',
  `topic_name` varchar(20) DEFAULT NULL COMMENT '主题名称',
  `topic_color` int DEFAULT NULL COMMENT '主题色',
  `topic_status` int DEFAULT NULL COMMENT '主题状态 0-未开始 1-展示中',
  `cartoon` int DEFAULT NULL COMMENT '动画 0-无动画播放 1-展示动画',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_delete` int DEFAULT '0' COMMENT '删除 0-否 1-是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tb_disable_date_admin_festival` */

insert  into `tb_disable_date_admin_festival`(`id`,`festival_name`,`topic_name`,`topic_color`,`topic_status`,`cartoon`,`start_time`,`end_time`,`create_time`,`update_time`,`is_delete`) values 
(1,'七夕节','七夕之约',0,1,1,'2022-07-30 16:44:11','2022-08-06 16:44:28','2022-07-29 16:42:30','2022-07-29 21:09:42',0),
(2,'情人节','挚爱之约',2,0,0,'2022-07-31 16:44:18','2022-09-16 16:44:32','2022-07-29 16:42:35','2022-07-29 21:09:43',0),
(3,'残疾人节','残疾之美',1,1,1,'2022-07-31 16:44:22','2022-06-25 16:44:36','2022-07-29 16:42:36','2022-07-29 21:09:46',0),
(4,'单身节','单事之趣',3,0,1,'2022-09-08 16:44:24','2022-10-27 16:44:41','2022-07-29 16:42:46','2022-07-29 21:09:50',0);

/*Table structure for table `tb_disable_date_admin_menu_children` */

DROP TABLE IF EXISTS `tb_disable_date_admin_menu_children`;

CREATE TABLE `tb_disable_date_admin_menu_children` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `menu_name` varchar(20) DEFAULT NULL COMMENT '菜单名称',
  `type` int DEFAULT '2' COMMENT '类型 1-模块 2-菜单',
  `route_address` varchar(50) DEFAULT NULL COMMENT '路由地址',
  `component_address` varchar(50) DEFAULT NULL COMMENT '组件路径',
  `permission` int DEFAULT '1' COMMENT '权限标识',
  `parent_id` int DEFAULT NULL COMMENT '父级ID',
  `status` int DEFAULT '1' COMMENT '状态 0-异常 1-正常',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_delete` int DEFAULT '0' COMMENT '删除 0-否 1-是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tb_disable_date_admin_menu_children` */

insert  into `tb_disable_date_admin_menu_children`(`id`,`menu_name`,`type`,`route_address`,`component_address`,`permission`,`parent_id`,`status`,`create_time`,`update_time`,`is_delete`) values 
(1,'工作台',2,'/dashboard/workplace','/views/dashboard/workplace',1,1,1,'2022-07-28 14:19:11','2022-08-02 14:00:44',0),
(2,'分析页',2,'/dashboard/analysis','/views/dashboard/analysis',1,1,1,'2022-07-28 14:26:17','2022-08-02 14:00:47',0),
(3,'监控',2,'/dashboard/monitor','/views/dashboard/monitor',1,1,1,'2022-07-28 14:26:42','2022-07-28 14:26:42',0),
(4,'管理员管理',2,'/system/admin','/views/system/admin',1,2,1,'2022-07-28 14:27:14','2022-07-28 14:27:14',0),
(5,'部门管理',2,'/dashboard/department','/views/system/department',1,2,1,'2022-07-28 14:27:39','2022-07-28 14:27:39',0),
(6,'菜单管理',2,'/dashboard/menu','/views/system/menu',1,2,1,'2022-07-28 14:28:02','2022-07-28 14:28:02',0),
(7,'广告业务',2,'/business/adver','/views/business/adver',1,3,1,'2022-07-28 14:28:22','2022-07-28 14:28:22',0),
(8,'活动业务',2,'/business/activity','/views/business/activity',1,3,1,'2022-07-28 14:28:59','2022-07-28 14:28:59',0),
(9,'节日庆典',2,'/business/celebreation','/views/business/celebreation',1,3,1,'2022-07-28 14:29:12','2022-07-28 14:30:49',0),
(10,'vip充值',2,'/business/vip','/views/business/vip',1,3,1,'2022-07-28 14:29:13','2022-07-28 14:31:37',0),
(11,'用户管理',2,'/user/commentUser','/views/user/commentUser',1,4,1,'2022-07-28 14:32:01','2022-07-28 14:32:01',0),
(12,'特权管理',2,'/user/vipUser','/views/user/vipUser',1,4,1,'2022-07-28 14:32:22','2022-07-28 14:32:22',0),
(13,'登录日志',2,'/log/loginLog','/views/log/loginLog',1,5,1,'2022-07-28 14:32:48','2022-07-28 14:32:48',0),
(14,'操作日志',2,'/user/opeartionLog','/views/log/opeartionLog',1,5,1,'2022-07-28 14:33:12','2022-07-28 14:33:12',0);

/*Table structure for table `tb_disable_date_admin_menu_module` */

DROP TABLE IF EXISTS `tb_disable_date_admin_menu_module`;

CREATE TABLE `tb_disable_date_admin_menu_module` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `name` varchar(20) DEFAULT NULL COMMENT '菜单名称',
  `type` int DEFAULT '1' COMMENT '菜单类型 1-模块',
  `route_address` varchar(50) DEFAULT NULL COMMENT '路由地址',
  `permissions` int DEFAULT '1' COMMENT '权限标识 1-减 2-加',
  `status` int DEFAULT '1' COMMENT '状态 0-异常 1-正常',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_delete` int DEFAULT '0' COMMENT '删除 0-否 1-是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tb_disable_date_admin_menu_module` */

insert  into `tb_disable_date_admin_menu_module`(`id`,`name`,`type`,`route_address`,`permissions`,`status`,`create_time`,`update_time`,`is_delete`) values 
(1,'控制面板',1,'/dashboard',1,1,'2022-07-28 14:02:24','2022-07-28 14:02:24',0),
(2,'系统管理',1,'/system',1,1,'2022-07-28 14:02:25','2022-07-28 14:02:25',0),
(3,'业务管理',1,'/business',1,1,'2022-07-28 14:02:26','2022-07-28 14:02:26',0),
(4,'用户管理',1,'/user',1,1,'2022-07-28 14:02:26','2022-07-28 14:02:26',0),
(5,'日志管理',1,'/log',1,1,'2022-07-28 14:07:49','2022-07-28 14:07:49',0);

/*Table structure for table `tb_disable_date_admin_user` */

DROP TABLE IF EXISTS `tb_disable_date_admin_user`;

CREATE TABLE `tb_disable_date_admin_user` (
  `admin_user_id` int NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `login_user_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆名称',
  `login_password` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆密码',
  `nick_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '管理员显示昵称',
  `locked` tinyint DEFAULT '0' COMMENT '是否锁定 1-未锁定 2-已锁定无法登陆',
  `level` tinyint DEFAULT '0' COMMENT '管理员权限  1-超级管理员 2-普通管理员 3-审批员',
  PRIMARY KEY (`admin_user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_disable_date_admin_user` */

/*Table structure for table `tb_disable_date_admin_users` */

DROP TABLE IF EXISTS `tb_disable_date_admin_users`;

CREATE TABLE `tb_disable_date_admin_users` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '管理员的d',
  `account` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '账号',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '姓名',
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码',
  `sex` int DEFAULT NULL COMMENT '姓名 1-男 2-女',
  `head_pic_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像路径',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '电话',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱',
  `role` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '角色',
  `department` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '部门',
  `online_status` int DEFAULT NULL COMMENT '职级状态',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `audit_status` int DEFAULT '1' COMMENT '审核状态 0-不通过 1-通过',
  `introduction` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '个人简介',
  `tab` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '个人标签',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin;

/*Data for the table `tb_disable_date_admin_users` */

insert  into `tb_disable_date_admin_users`(`id`,`account`,`name`,`user_name`,`password`,`sex`,`head_pic_path`,`phone`,`email`,`role`,`department`,`online_status`,`create_time`,`update_time`,`audit_status`,`introduction`,`tab`) values 
(1,NULL,'陈振宇','其然','12345678',1,'2412421421421412','11111111111','11111111123123','管理员','运营部门',1,'2022-07-28 21:31:41','2022-07-28 21:31:41',1,'哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈','滑板 篮球 网球 唱歌'),
(3,NULL,'路飞','蔡序员','12345678',1,'d:/aegf/afg/a/fga/fg','12341234123','2355352512@qq.com','审核员','审核部门',1,'2022-07-29 00:39:32','2022-07-29 00:39:32',1,'你好，我是练习两年半的Java后端程序员','唱 跳 rap'),
(4,NULL,'柯南','海贼王','1235215',NULL,'d:/aegf/afg/a/fga/fg','11111111111','11111111111','审核员','审核部门',1,'2022-07-29 00:39:43','2022-07-29 00:39:43',1,'海贼王当定了','滑板 篮球 网球 唱歌'),
(5,NULL,'柯南','海贼王','1235215',NULL,'d:/aegf/afg/a/fga/fg','11111111111','11111111111','审核员','审核部门',1,'2022-07-29 00:39:44','2022-07-29 00:39:44',1,'海贼王当定了','滑板 篮球 网球 唱歌'),
(6,NULL,'柯南','海贼王','1235215',NULL,'d:/aegf/afg/a/fga/fg','11111111111','11111111111','管理员','运营部门',1,'2022-07-29 00:39:46','2022-07-29 00:39:46',1,'海贼王当定了','滑板 篮球 网球 唱歌'),
(7,NULL,'柯南','工藤新一','1235215',NULL,'d:/aegf/afg/a/fga/fg','11111111111','11111111111','管理员','运营部门',1,'2022-07-29 00:39:47','2022-07-29 00:39:47',1,'海贼王当定了','滑板 篮球 网球 唱歌'),
(8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2022-07-29 10:30:50','2022-07-29 10:30:50',NULL,NULL,'滑板 篮球 网球 唱歌'),
(9,'13330245687',NULL,'Letitbe','8ddcff3a80f4189ca1c9d4d902c3c909',NULL,NULL,NULL,'1','管理员','运营部门',NULL,'2022-07-29 16:20:13','2022-07-29 16:20:13',NULL,NULL,NULL),
(10,'admin',NULL,'admin','e10adc3949ba59abbe56e057f20f883e',NULL,NULL,NULL,NULL,'1','1',NULL,'2022-08-02 16:40:52','2022-08-02 16:40:52',1,NULL,NULL),
(11,'admin1',NULL,'admin1','e10adc3949ba59abbe56e057f20f883e',NULL,NULL,'13','231','2','5',NULL,'2022-08-02 17:05:36','2022-08-02 17:05:36',1,'123',''),
(12,'admi',NULL,'IT','e10adc3949ba59abbe56e057f20f883e',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2022-08-02 20:09:45','2022-08-02 20:09:45',1,NULL,NULL);

/*Table structure for table `tb_disable_date_admin_vip_manage` */

DROP TABLE IF EXISTS `tb_disable_date_admin_vip_manage`;

CREATE TABLE `tb_disable_date_admin_vip_manage` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '权力id',
  `function_name` varchar(50) DEFAULT NULL COMMENT '功能名称',
  `status` tinyint(1) DEFAULT '0' COMMENT 'vip功能的特权限制状态 1-开发 0-不开放',
  `longto` int DEFAULT NULL COMMENT '属于哪个模块 1-约吧中心 2-广场中心 3-消息中心 4-个人中心',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tb_disable_date_admin_vip_manage` */

insert  into `tb_disable_date_admin_vip_manage`(`id`,`function_name`,`status`,`longto`) values 
(1,'每天无限制点击喜欢',1,1),
(2,'滑错随时反悔',1,1),
(3,'活动优先报名权',1,2),
(4,'情感咨询',0,2),
(5,'私聊消息长久保存',1,3),
(6,'群聊消息长久保存',1,3),
(7,'私聊语音通话',1,3),
(8,'查看被关注者权限',1,4),
(9,'简历定制',1,4);

/*Table structure for table `tb_disable_date_admin_vip_package` */

DROP TABLE IF EXISTS `tb_disable_date_admin_vip_package`;

CREATE TABLE `tb_disable_date_admin_vip_package` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '套餐名',
  `time` int DEFAULT NULL COMMENT '时长',
  `price` double DEFAULT NULL COMMENT '单月价格',
  `total_price` double DEFAULT NULL COMMENT '总价格',
  `package_status` int DEFAULT NULL COMMENT '套餐状态 0-未开启 1-开启',
  `advice_first` int DEFAULT NULL COMMENT '是否首推 0-否 1-首推',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '套餐创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '套餐更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tb_disable_date_admin_vip_package` */

/*Table structure for table `tb_disable_date_admin_vip_recharge` */

DROP TABLE IF EXISTS `tb_disable_date_admin_vip_recharge`;

CREATE TABLE `tb_disable_date_admin_vip_recharge` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` varchar(60) DEFAULT NULL COMMENT '订单ID',
  `order_status` int DEFAULT NULL COMMENT '订单状态 0-支付失败 1-支付成功 2-待支付',
  `type` varchar(20) DEFAULT NULL COMMENT 'VIP类型',
  `price` double DEFAULT NULL COMMENT '价格',
  `start_time` datetime DEFAULT NULL COMMENT '特权开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '特权结束时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tb_disable_date_admin_vip_recharge` */

/*Table structure for table `tb_disable_date_app_version` */

DROP TABLE IF EXISTS `tb_disable_date_app_version`;

CREATE TABLE `tb_disable_date_app_version` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '版本id',
  `version` varchar(10) DEFAULT NULL COMMENT '版本号',
  `descrip` varchar(100) DEFAULT NULL COMMENT '版本描述',
  `release_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tb_disable_date_app_version` */

insert  into `tb_disable_date_app_version`(`id`,`version`,`descrip`,`release_time`) values 
(1,'v1.0','最初版','2022-07-28 20:09:41'),
(2,'v1.1','hhhh','2022-07-28 20:09:46'),
(3,'v1.2','马冬梅','2022-07-28 20:09:49'),
(4,'v1.3','马什么梅','2022-07-28 20:09:51'),
(5,'v1.4','什么冬梅','2022-07-28 20:09:56'),
(6,'v1.5','马冬什么','2022-07-28 20:09:59'),
(7,'v1.6','马冬梅啊','2022-07-28 20:10:04'),
(8,NULL,NULL,'2022-07-28 20:10:49');

/*Table structure for table `tb_disable_date_diary` */

DROP TABLE IF EXISTS `tb_disable_date_diary`;

CREATE TABLE `tb_disable_date_diary` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `diary_id` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '动态记录主键id',
  `diary_user_id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发表动态的用户id',
  `diary_user_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发动态的用户名',
  `diary_title` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '动态标题',
  `diary_content` mediumtext CHARACTER SET utf8mb3 COLLATE utf8_general_ci COMMENT '动态内容',
  `diary_kind` int DEFAULT '0' COMMENT '动态分类id 1-图文动态 2-视频类动态 3-纯2文字',
  `diary_category_id` int DEFAULT '0' COMMENT '主题分类id',
  `diary_category_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT 'null' COMMENT '主题分类(冗余字段)',
  `diary_tags` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT 'null' COMMENT '动态标签',
  `diary_status` tinyint DEFAULT '1' COMMENT '0-审核不通过 1-审核通过 2-待审核 3-草稿',
  `diary_comment` bigint DEFAULT '0' COMMENT '讨论量',
  `diary_views` bigint DEFAULT '0' COMMENT '阅读量',
  `diary_love` bigint DEFAULT '0' COMMENT '点赞量',
  `diary_collect` bigint DEFAULT '0' COMMENT '收藏量',
  `enable_look` tinyint DEFAULT '1' COMMENT '0-不可见 1-可见',
  `enable_comment` tinyint DEFAULT '1' COMMENT '0-不允许评论 1-允许评论',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除 0-否 1-是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '动态添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '动态修改时间',
  `is_report` int DEFAULT '0' COMMENT '举报 0-不举报 1-举报',
  `ps` varchar(200) DEFAULT NULL COMMENT '审核不通过给的提示',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_disable_date_diary` */

insert  into `tb_disable_date_diary`(`id`,`diary_id`,`diary_user_id`,`diary_user_name`,`diary_title`,`diary_content`,`diary_kind`,`diary_category_id`,`diary_category_name`,`diary_tags`,`diary_status`,`diary_comment`,`diary_views`,`diary_love`,`diary_collect`,`enable_look`,`enable_comment`,`is_deleted`,`create_time`,`update_time`,`is_report`,`ps`) values 
(1,'e5bed0a0-663f-403d-ba9d-71ed5fba0638','1','后端程序员','后端当今很卷','后端的性能处理要求很看重，所以要好好学，多实践',1,0,'null','null',1,120,0,99,0,1,1,0,'2022-07-26 16:52:45','2022-07-26 16:52:45',NULL,NULL),
(2,'61ecebcc-58f9-4cff-a1c4-327fa6a6aabb','2','后端程序员','后端当今很卷','后端的性能处理要求很看重，所以要好好学，多实践',2,0,'null','null',1,12,0,99,1,1,1,0,'2022-07-26 17:04:57','2022-07-26 17:04:57',NULL,NULL),
(3,'9ba061c5-f229-44ad-ae4f-05a293287dfa','3','前端哈哈哈哈','后端当今很卷','我也很喜欢看七龙珠和柯南',2,0,'null','null',1,20,0,123,0,1,1,0,'2022-07-26 17:05:02','2022-07-26 17:05:02',NULL,NULL),
(4,'cb0951b7-7b91-463b-852f-7dfb8895b212','4','后端程序员','后端当今很卷','后端的性能处理要求很看重，所以要好好学，多实践',2,0,'null','null',1,120,0,100,0,1,1,0,'2022-07-26 17:05:03','2022-07-26 17:05:03',NULL,NULL),
(5,'cb0951b7-7b91-463b-852f-7dfb8895b56f','5','后盾刀','后端当今很卷','海贼王和名侦探柯南我都喜欢看',1,0,'null','null',1,42,0,99,0,1,1,0,'2022-07-26 17:05:03','2022-07-26 17:05:03',NULL,NULL),
(6,'c1b99d17-d605-44a8-935c-fd5b6fa5bde4','7','hh','后端当今很卷','后端的性能处理要求很看重，所以要好好学，多实践',2,0,'null','null',1,120,0,100,0,1,1,0,'2022-07-26 17:05:04','2022-07-26 17:05:04',NULL,NULL),
(7,'485755ab-1b70-4629-b36a-c7edd3db14bd','7','hdaf','后端当今很卷','我喜欢看海贼王',1,0,'null','null',1,42,0,214,0,1,1,0,'2022-07-26 17:05:05','2022-07-26 17:05:05',NULL,NULL),
(8,'26c5ce50-4a40-4d63-acdc-392ef022c67c','8','后端程序员','后端当今很卷','后端的性能处理要求很看重，所以要好好学，多实践',2,0,'null','null',1,120,0,100,1,1,1,0,'2022-07-26 17:05:05','2022-07-26 17:05:05',NULL,NULL),
(9,'675e9431-67cd-4562-a35e-9c81167d65ae','9','后端程序员','后端当今很卷','后端的性能处理要求很看重，所以要好好学，多实践',1,0,'null','null',1,1,0,99,0,1,1,0,'2022-07-26 18:11:57','2022-07-26 18:11:57',NULL,NULL),
(10,'5c21d52c-2965-40dd-8433-306391961eb3','10','后端程序员','后端当今很卷','后端的性能处理要求很看重，所以要好好学，多实践',2,0,'null','null',1,2,0,12,0,1,1,0,'2022-07-27 01:12:59','2022-07-27 01:12:59',NULL,NULL),
(11,'d0657773-7d27-4e4c-851a-e9e9bcc47310','11','后端程序员','后端当今很卷','后端的性能处理要求很看重，所以要好好学，多实践',2,0,'null','null',1,120,0,100,2,1,1,0,'2022-07-27 01:13:01','2022-07-27 01:13:01',NULL,NULL),
(12,'aetqeaf2-at23q-tq3t-23-52q5-q235-q35','12','后端','后哈根功耗和公海贵宾卡更包括了结案报告','安徽覅高好高级哦啊是低功耗的噶多看两遍案例的那个卡就大概看是客观的你是',2,0,'null','null',1,0,0,1,0,1,1,0,'2022-08-03 10:14:24','2022-08-03 10:14:24',0,NULL),
(13,'9a493028-8641-4761-8938-95e33550b1ce','12','后端程序员','后端当今很卷','后端的性能处理要求很看重，所以要好好学，多实践',2,0,'null','null',1,120,0,99,0,1,1,0,'2022-07-27 12:22:45','2022-07-27 12:22:45',NULL,NULL),
(14,'aewt3q2t3453tq3t3vt235v2235v5v325vv2','1','嘎嘎嘎俄国','阿尔尕尔哥','噶尔五个挖给娃儿给阿维格a\'g',0,0,'null','null',1,25,0,99,0,1,1,0,'2022-08-03 10:16:08','2022-08-03 10:16:08',0,NULL),
(15,'b8038072-19f0-48c6-ab43-3947dba1978d','13','后端程序员','后端当今很卷','后端的性能处理要求很看重，所以要好好学，多实践',1,0,'null','null',0,120,0,100,0,1,1,0,'2022-07-27 12:33:37','2022-07-27 12:33:37',NULL,NULL),
(16,'d2431bb7-ce25-4667-b427-544606be344a','14','后端程序员','后端当今很卷','后端的性能处理要求很看重，所以要好好学，多实践',1,0,'null','null',1,99,0,101,0,1,1,0,'2022-07-27 15:48:31','2022-07-27 15:48:31',NULL,NULL);

/*Table structure for table `tb_disable_date_diary_category` */

DROP TABLE IF EXISTS `tb_disable_date_diary_category`;

CREATE TABLE `tb_disable_date_diary_category` (
  `category_id` int NOT NULL AUTO_INCREMENT COMMENT '分类表主键',
  `category_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '分类的名称',
  `category_icon` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '分类的图标',
  `category_rank` int NOT NULL DEFAULT '1' COMMENT '分类的排序值 被使用的次数越多数值越大',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除 1-否 2-是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_disable_date_diary_category` */

/*Table structure for table `tb_disable_date_diary_comment` */

DROP TABLE IF EXISTS `tb_disable_date_diary_comment`;

CREATE TABLE `tb_disable_date_diary_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `comment_id` varchar(100) NOT NULL COMMENT '评论主键id',
  `diary_id` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT '0' COMMENT '关联的diary主键',
  `commentator_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT '' COMMENT '评论者昵称',
  `comment_body` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT '' COMMENT '评论内容',
  `comment_create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论提交时间',
  `commentator_ip` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT '' COMMENT '评论时的ip',
  `reply_amount` bigint DEFAULT '0' COMMENT '本条评论的回复数量',
  `comment_status` tinyint DEFAULT '1' COMMENT '是否审核通过 0-审核不通过 1-审核通过 2-未审核',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除 1-未删除 2-已删除',
  `like_amount` int DEFAULT '0' COMMENT '点赞量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_disable_date_diary_comment` */

insert  into `tb_disable_date_diary_comment`(`id`,`comment_id`,`diary_id`,`commentator_name`,`comment_body`,`comment_create_time`,`commentator_ip`,`reply_amount`,`comment_status`,`is_deleted`,`like_amount`) values 
(1,'f26e6a81-e380-4335-85c8-f89381003360','aewt3q2t3453tq3t3vt235v2235v5v325vv2','哈利波特','是挺厉害的','2022-07-26 17:11:38','2',33,1,0,2),
(2,'897d1662-ecf2-4be8-b4be-117498667da8','aewt3q2t3453tq3t3vt235v2235v5v325vv2','哈利波特','是挺厉害的','2022-07-26 17:11:39','2',0,1,0,0),
(3,'f9bad922-b5c7-4aab-83aa-bd33fbe088f8','aewt3q2t3453tq3t3vt235v2235v5v325vv2','哈利波特','是挺厉害的','2022-07-26 17:11:44','2',0,1,0,0),
(4,'525a3841-79b5-4dbc-866f-a0bc6a30b78a','aewt3q2t3453tq3t3vt235v2235v5v325vv2','哈利波特','是挺厉害的','2022-07-26 18:11:59','2',0,1,0,0),
(5,'04f5bb5d-0181-4739-a02a-72c43b74500f','aewt3q2t3453tq3t3vt235v2235v5v325vv2','哈利波特','是挺厉害的','2022-07-26 22:05:25','2',0,1,0,0),
(6,'bf4f06a2-e6e3-4fcd-80b2-fabd3e712dff','aewt3q2t3453tq3t3vt235v2235v5v325vv2','哈利波特','是挺厉害的','2022-07-26 22:05:26','2',0,1,0,0),
(7,'6288ddbf-02b2-46c2-8c8e-90040ddc87b8','aewt3q2t3453tq3t3vt235v2235v5v325vv2','哈利波特','是挺厉害的','2022-07-26 22:05:27','2',0,1,0,0),
(8,'8c89d753-a7a4-4bde-b33a-a4b368dba112','aewt3q2t3453tq3t3vt235v2235v5v325vv2','哈利波特','是挺厉害的','2022-07-26 22:05:28','2',0,1,0,1),
(9,'0f17c84d-07cc-4af1-8500-aaeaa4ae7fd1','aewt3q2t3453tq3t3vt235v2235v5v325vv2','哈利波特','是挺厉害的','2022-07-26 22:05:29','2',0,1,0,0),
(10,'e19499cc-c06d-41c0-8341-9c542b55db96','aewt3q2t3453tq3t3vt235v2235v5v325vv2','哈利波特','是挺厉害的','2022-07-26 22:05:30','2',0,1,0,1),
(11,'aae30c97-aae2-4388-914b-55dbfcaccc8f','aewt3q2t3453tq3t3vt235v2235v5v325vv2','哈利波特','是挺厉害的','2022-07-26 22:05:30','2',0,1,0,0),
(12,'ad231628-17f3-4b31-a908-82cfa989d6d0','aewt3q2t3453tq3t3vt235v2235v5v325vv2','哈利波特','是挺厉害的','2022-07-26 22:05:31','2',0,1,0,1),
(13,'2ceb7065-6532-44b5-b7c2-201bd1b9b4d1','aewt3q2t3453tq3t3vt235v2235v5v325vv2','哈利波特','是挺厉害的','2022-07-26 22:05:31','2',0,1,0,0),
(14,'00f32210-d09b-47b8-a798-a8d5dd2297fb','aewt3q2t3453tq3t3vt235v2235v5v325vv2','哈利波特','是挺厉害的','2022-07-26 22:05:33','2',0,1,0,0),
(15,'08710744-ea4a-452e-95eb-d84890339782','aewt3q2t3453tq3t3vt235v2235v5v325vv2','哈利波特','是挺厉害的','2022-07-26 22:05:33','2',0,1,0,1),
(16,'dc9c463e-cbf1-4d07-8920-bdb5b9dabd12','aewt3q2t3453tq3t3vt235v2235v5v325vv2','哈利波特','是挺厉害的','2022-07-26 22:05:34','2',0,1,0,0),
(17,'9815ddd8-7eda-44e7-b07a-8795db0c23cf','aewt3q2t3453tq3t3vt235v2235v5v325vv2','哈利波特','是挺厉害的','2022-07-26 22:05:34','2',0,1,0,0),
(45,'1c8803a4-b018-4260-aae1-6418bfb6b4d4','d4d3d980-6af7-410d-b605-f1ce11f97c1f','哈利波特','是挺厉害的','2022-07-31 13:30:50','2',0,1,0,0),
(46,'17db141a-23b1-4abd-907d-5ed23fa08c28','d4d3d980-6af7-410d-b605-f1ce11f97c1f','哈利波特','是挺厉害的','2022-07-31 13:30:55','2',0,1,0,0),
(49,'70e19d90-7457-4da9-bee9-b4ef16d5e170','aewt3q2t3453tq3t3vt235v2235v5v325vv2','娜美','什么？','2022-08-06 18:16:24','3',0,1,1,0),
(50,'b8f9e040-1c53-4419-98a9-e8af6c31e23a',NULL,NULL,NULL,'2022-08-06 18:20:47',NULL,0,1,1,0);

/*Table structure for table `tb_disable_date_diary_reply` */

DROP TABLE IF EXISTS `tb_disable_date_diary_reply`;

CREATE TABLE `tb_disable_date_diary_reply` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reply_id` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '回复的id',
  `diary_id` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '动态的id',
  `comment_id` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '评论的id',
  `reply_content` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '回复的内容',
  `reply_user_name` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '回复者的名称',
  `reply_user_id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '回复者id',
  `comment_user_id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '被恢复的评论的用户id',
  `comment_user_name` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '被恢复的评论的用户名称',
  `reply_status` tinyint DEFAULT '1' COMMENT '是否审核通过 0-审核不通过 1-审核通过 2-未审核',
  `like_amount` int DEFAULT '0' COMMENT '点赞量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_disable_date_diary_reply` */

insert  into `tb_disable_date_diary_reply`(`id`,`reply_id`,`diary_id`,`comment_id`,`reply_content`,`reply_user_name`,`reply_user_id`,`comment_user_id`,`comment_user_name`,`reply_status`,`like_amount`,`create_time`) values 
(1,'2360ac0e-8070-41a9-8eb0-02110bc7e565','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,2,'2022-07-26 17:40:50'),
(2,'3f1b140b-8591-4c8a-b8fc-f6d7d7a21b2a','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,1,'2022-07-26 17:40:51'),
(3,'28ceab5d-b8fc-45fd-a154-793c406c8f5c','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-07-26 17:40:51'),
(4,'185e68c4-86e9-4285-a1e3-05c7e8164328','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,1,'2022-07-26 17:40:52'),
(5,'f47cb15e-27d6-4b6b-a4ed-771e838fe4b0','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-07-26 18:12:01'),
(6,'7a30ba7a-55df-4716-87e9-ee0dbdd5f6eb','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,1,'2022-07-26 20:31:09'),
(7,'25fc4ef9-d123-4e80-8d63-2f85fb6b976b','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-07-26 20:31:10'),
(8,'46d0cab6-9c85-4efb-9c3e-c7a72a30bba6','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-07-26 20:31:14'),
(9,'d7e4a5de-478d-4b8f-9ee9-4d33afb123f9','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-07-27 01:27:01'),
(10,'32469bcc-ed67-4421-899d-c2038d149c35','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-07-27 01:27:02'),
(11,'6317d2b7-922f-4ae4-a1a1-d5e13849279e','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-07-27 01:27:03'),
(12,'9e58f83f-857d-43ec-b001-6f3857e333cf','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-07-27 01:27:06'),
(13,'1787f992-4f9a-43ff-8d4f-2052d6df3cea','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-07-27 01:27:07'),
(14,'21ce5e31-4a81-4186-96b3-c4a1613a4d49','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-07-27 01:27:07'),
(15,'4ac35da2-dd60-4073-824b-61ba0c6ce477','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-07-27 01:27:08'),
(16,'3abff2eb-f19b-47a9-94a3-cb6d2c6bc678','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-07-27 01:27:11'),
(17,'c469b899-c0c3-4b38-b7de-8c3a31ae6543','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-07-27 01:27:12'),
(18,'63ddfdad-cbd0-404d-96f9-a940ed951d54','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-07-27 01:27:21'),
(19,'826cfc24-fc3a-4de7-be97-bfaaf9f81fad','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-07-27 01:27:21'),
(28,'cd558869-a9cc-4dca-a640-727c74d21f8f','206536c8-77c0-486b-9a44-93f6f09e50d1','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-07-27 14:46:04'),
(54,'63ddfdad-cbd0-404d-96f9-a940ed951d52','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-08-05 21:46:16'),
(55,'63ddfdad-cbd0-404d-16f9-a940ed951d54','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-08-05 21:46:17'),
(56,'63ddfdad-cbd0-404d-96f9-a940ed951d58','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-08-05 21:46:17'),
(57,'63ddfdad-cbd0-404d-96f9-a940ed951d21','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-08-05 21:46:18'),
(58,'63ddfdad-cbd0-404d-96f9-a940ed342342','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-08-05 21:46:18'),
(59,'63ddfdad-cbd0-404d-96f9-a940ed246346','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-08-05 21:46:20'),
(60,'63ddfdad-cbd0-404d-96f9-a94024678854','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-08-05 21:46:20'),
(61,'63ddfdad-cbd0-404d-96f9-a940ed246624','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-08-05 21:46:21'),
(62,'63ddfdad-cbd0-404d-96f9-a940ed951d00','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-08-05 21:46:21'),
(63,'63ddfdad-cbd0-404d-96f9-a940ed234324','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-08-05 21:46:24'),
(64,'12412412-cbd0-404d-96f9-a940ed951d54','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-08-05 21:46:37'),
(65,'12412412-cbd0-404d-96f9-a940ed144444','e5bed0a0-663f-403d-ba9d-71ed5fba0638','f26e6a81-e380-4335-85c8-f89381003360','是啊，确实挺厉害的呢','路飞','1','2','哈利波特',1,0,'2022-08-06 15:35:08'),
(69,'0be3bd2e-b97d-4bb7-9437-614b30827448','aewt3q2t3453tq3t3vt235v2235v5v325vv2','f26e6a81-e380-4335-85c8-f89381003360','没什么','罗宾','4','3','娜美',1,0,'2022-08-06 18:20:59');

/*Table structure for table `tb_disable_date_diary_tag` */

DROP TABLE IF EXISTS `tb_disable_date_diary_tag`;

CREATE TABLE `tb_disable_date_diary_tag` (
  `tag_id` int NOT NULL AUTO_INCREMENT COMMENT '标签表主键id',
  `tag_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '标签名称',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除 1-否 2-是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_disable_date_diary_tag` */

/*Table structure for table `tb_disable_date_diary_tag_relation` */

DROP TABLE IF EXISTS `tb_disable_date_diary_tag_relation`;

CREATE TABLE `tb_disable_date_diary_tag_relation` (
  `relation_id` bigint NOT NULL AUTO_INCREMENT COMMENT '关系表id',
  `diary_id` bigint NOT NULL COMMENT '朋友圈id',
  `tag_id` int NOT NULL COMMENT '标签id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`relation_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_disable_date_diary_tag_relation` */

/*Table structure for table `tb_disable_date_dislike` */

DROP TABLE IF EXISTS `tb_disable_date_dislike`;

CREATE TABLE `tb_disable_date_dislike` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '操作记录id',
  `user_id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '用户id',
  `dislike_user_id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '拉黑的人的id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '关系创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '关系更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_disable_date_dislike` */

/*Table structure for table `tb_disable_date_login_logs` */

DROP TABLE IF EXISTS `tb_disable_date_login_logs`;

CREATE TABLE `tb_disable_date_login_logs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(20) DEFAULT NULL COMMENT '登录用户名',
  `role` varchar(20) DEFAULT NULL COMMENT '角色',
  `last_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tb_disable_date_login_logs` */

insert  into `tb_disable_date_login_logs`(`id`,`nick_name`,`role`,`last_time`) values 
(1,'嘿嘿嘿','普通用户','2022-07-29 11:34:43'),
(2,'哈哈哈','普通用户','2022-07-29 11:34:52'),
(3,'喂喂喂','VIP用户','2022-07-29 11:34:59');

/*Table structure for table `tb_disable_date_payment_info` */

DROP TABLE IF EXISTS `tb_disable_date_payment_info`;

CREATE TABLE `tb_disable_date_payment_info` (
  `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_sn` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '订单号（对外业务号）',
  `user_id` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户id',
  `trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '支付宝交易流水号',
  `vip_type` int DEFAULT NULL COMMENT 'VIP类别 1-月包 2-季包 3-年包',
  `total_amount` decimal(18,4) DEFAULT NULL COMMENT '价格（支付总额）',
  `pay_way` int DEFAULT NULL COMMENT '支付方式 1-微信支付 2-支付宝支付',
  `subject` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '交易内容',
  `payment_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '订单状态 0-待支付 1-支付成功 2-支付失败',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单更新时间',
  `start_time` datetime DEFAULT NULL COMMENT '特权开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '特权结束时间',
  PRIMARY KEY (`order_id`) USING BTREE,
  UNIQUE KEY `order_sn` (`order_sn`) USING BTREE,
  UNIQUE KEY `alipay_trade_no` (`trade_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='支付信息表';

/*Data for the table `tb_disable_date_payment_info` */

insert  into `tb_disable_date_payment_info`(`order_id`,`order_sn`,`user_id`,`trade_no`,`vip_type`,`total_amount`,`pay_way`,`subject`,`payment_status`,`create_time`,`update_time`,`start_time`,`end_time`) values 
(1,'s956620200','1','213411',1,75.0000,1,NULL,'1','2022-08-02 10:59:16','2022-08-02 11:01:05','2022-08-02 10:59:20','2022-09-02 10:59:22'),
(2,'s956613201','2','242141',1,16.0000,2,NULL,'1','2022-08-18 11:14:54','2022-08-02 11:15:05','2022-08-19 11:15:00','2023-02-24 11:15:07'),
(3,'s956613200','3','124124',2,25.0000,1,NULL,'1','2022-08-03 11:01:38','2022-08-02 11:02:08','2022-08-03 14:01:52','2022-08-28 11:02:02');

/*Table structure for table `tb_disable_date_user_collect` */

DROP TABLE IF EXISTS `tb_disable_date_user_collect`;

CREATE TABLE `tb_disable_date_user_collect` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(10) DEFAULT NULL COMMENT '用户id',
  `liked_id` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '被喜欢的id（动态id或用户id）',
  `type` int DEFAULT NULL COMMENT '收场的类型 1-视频动态 2-图片修',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tb_disable_date_user_collect` */

insert  into `tb_disable_date_user_collect`(`id`,`user_id`,`liked_id`,`type`,`create_time`) values 
(1,'2','1',2,'2022-08-04 01:18:59'),
(2,'1','61ecebcc-58f9-4cff-a1c4-327fa6a6aabb',1,'2022-08-04 01:19:58'),
(3,'1','61ecebcc-58f9-4cff-a1c4-327fa6a6aabb',1,'2022-08-04 01:20:24'),
(4,'1','d0657773-7d27-4e4c-851a-e9e9bcc47313',1,'2022-08-04 01:20:39'),
(5,'1','61ecebcc-58f9-4cff-a1c4-327fa6a6aabb',1,'2022-08-04 01:20:55'),
(6,'1','3',2,'2022-08-04 01:21:07'),
(7,'1','4',2,'2022-08-04 01:21:22'),
(8,'1','485755ab-1b70-4629-b36a-c7edd3db14bd',1,'2022-08-04 01:21:40'),
(9,'1','aetqeaf2-at23q-tq3t-23-52q5-q235-q35',1,'2022-08-04 01:21:55'),
(10,'1','9ba061c5-f229-44ad-ae4f-05a293287dfa',1,'2022-08-04 01:22:16'),
(11,'1','5',2,'2022-08-04 01:22:23'),
(13,'1','2',2,'2022-08-04 11:39:22'),
(14,'1','2',2,'2022-08-04 11:41:03'),
(15,'1','61ecebcc-58f9-4cff-a1c4-327fa6a6aabb',1,'2022-08-04 11:42:14'),
(16,'1','61ecebcc-58f9-4cff-a1c4-327fa6a6aabb',1,'2022-08-04 11:48:52'),
(17,'1','d0657773-7d27-4e4c-851a-e9e9bcc47311',1,'2022-08-04 12:49:55'),
(18,'1','26c5ce50-4a40-4d63-acdc-392ef022c67c',1,'2022-08-04 13:18:38'),
(20,'1','d0657773-7d27-4e4c-851a-e9e9bdgaegega10',1,'2022-08-04 13:29:51');

/*Table structure for table `tb_disable_date_user_like_diary` */

DROP TABLE IF EXISTS `tb_disable_date_user_like_diary`;

CREATE TABLE `tb_disable_date_user_like_diary` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(10) DEFAULT NULL COMMENT '用户id',
  `diary_id` varchar(150) DEFAULT NULL COMMENT '动态id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '点赞的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=301 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tb_disable_date_user_like_diary` */

insert  into `tb_disable_date_user_like_diary`(`id`,`user_id`,`diary_id`,`create_time`) values 
(2,'1','e5bed0a0-663f-403d-ba9d-71ed5fba0638','2022-08-04 14:57:34'),
(31,'1','cb0951b7-7b91-463b-852f-7dfb8895b212','2022-08-04 17:17:15'),
(61,'2','f26e6a81-e380-4335-85c8-f89381003360','2022-08-04 21:53:53'),
(62,'2','63ddfdad-cbd0-404d-96f9-a940ed951d54','2022-08-04 22:04:11'),
(71,'3','2360ac0e-8070-41a9-8eb0-02110bc7e565','2022-08-04 22:27:04'),
(100,'4','2360ac0e-8070-41a9-8eb0-02110bc7e565','2022-08-06 17:12:02'),
(185,NULL,NULL,'2022-08-06 18:04:14'),
(212,'1','ad231628-17f3-4b31-a908-82cfa989d6d0','2022-08-06 18:06:58'),
(213,'1','e19499cc-c06d-41c0-8341-9c542b55db96','2022-08-06 18:06:58'),
(214,'1','8c89d753-a7a4-4bde-b33a-a4b368dba112','2022-08-06 18:06:59'),
(215,'1','08710744-ea4a-452e-95eb-d84890339782','2022-08-06 18:07:04'),
(235,'1','7a30ba7a-55df-4716-87e9-ee0dbdd5f6eb','2022-08-06 21:10:45'),
(255,'1','d2431bb7-ce25-4667-b427-544606be344a','2022-08-06 21:29:04'),
(295,'1','f26e6a81-e380-4335-85c8-f89381003360','2022-08-06 21:48:41'),
(297,'1','185e68c4-86e9-4285-a1e3-05c7e8164328','2022-08-06 21:48:47'),
(299,'1','3f1b140b-8591-4c8a-b8fc-f6d7d7a21b2a','2022-08-06 21:49:25');

/*Table structure for table `tb_disable_date_user_like_hobby` */

DROP TABLE IF EXISTS `tb_disable_date_user_like_hobby`;

CREATE TABLE `tb_disable_date_user_like_hobby` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(10) DEFAULT NULL COMMENT '用户id',
  `hobby` varchar(300) DEFAULT NULL COMMENT '兴趣爱好',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tb_disable_date_user_like_hobby` */

insert  into `tb_disable_date_user_like_hobby`(`id`,`user_id`,`hobby`) values 
(1,'1','跑步啊'),
(2,'1','听音乐 跑步'),
(3,'1','篮球'),
(4,'1','运动 羽毛球 乒乓球'),
(5,'1','画画 听歌 唱歌 网球'),
(6,'1','拳皇'),
(7,'2','街霸');

/*Table structure for table `tb_disable_diary_pictures` */

DROP TABLE IF EXISTS `tb_disable_diary_pictures`;

CREATE TABLE `tb_disable_diary_pictures` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '发布者id',
  `pic_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '照片文件的命名',
  `diary_id` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图片的来源主键id，来源可以是动态 or 图片秀',
  `pic_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '视频存放的路径',
  `pic_width` int DEFAULT NULL COMMENT '图片宽度',
  `pic_height` int DEFAULT NULL COMMENT '图片高度',
  `like_counts` bigint NOT NULL DEFAULT '0' COMMENT '喜欢/赞美的数量',
  `status` int NOT NULL COMMENT '视频状态：1-发布成功 2-未过审，管理员操作',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=145 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='图片信息表';

/*Data for the table `tb_disable_diary_pictures` */

insert  into `tb_disable_diary_pictures`(`id`,`user_id`,`pic_name`,`diary_id`,`pic_path`,`pic_width`,`pic_height`,`like_counts`,`status`,`create_time`) values 
(45,2,'31db106a-dfe5-4095-9044-d20505c32766.jpg','cb0951b7-7b91-463b-852f-7dfb8895b56f','/../..static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 12:21:17'),
(46,2,'6e3b1176-9f98-4300-a58c-2ca8e5aa4491.jpg','cb0951b7-7b91-463b-852f-7dfb8895b56f','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 12:21:17'),
(47,2,'faabe826-c8d3-4610-9877-d460e5eaf4a0.jpg','cb0951b7-7b91-463b-852f-7dfb8895b56f','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 12:21:17'),
(48,2,'f7772e0a-d8ae-4e59-98e9-73492b5bbc3d.jpg','cb0951b7-7b91-463b-852f-7dfb8895b56f','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 12:21:17'),
(49,2,'eb82ae2d-23fa-4fa3-9cc8-2695b76a290b.jpg','485755ab-1b70-4629-b36a-c7edd3db14bd','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 12:22:26'),
(50,2,'1c4649d4-765c-4544-a12f-1bc0dc4036f6.jpg','485755ab-1b70-4629-b36a-c7edd3db14bd','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 12:22:26'),
(51,2,'b8bbd4b3-f284-4ee1-b636-97650bafc246.jpg','485755ab-1b70-4629-b36a-c7edd3db14bd','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 12:22:26'),
(52,2,'6020d90a-dfa7-4192-a978-f57fad2f9b79.jpg','485755ab-1b70-4629-b36a-c7edd3db14bd','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 12:22:26'),
(53,2,'f2b97af9-1ab1-46fb-9ec7-30d58a7aaccd.jpg','9a493028-8641-4761-8938-95e33550b1ce','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 12:22:45'),
(54,2,'4a11c068-d276-4fbd-b514-006d76fe7de3.jpg','9a493028-8641-4761-8938-95e33550b1ce','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 12:22:45'),
(55,2,'9fc3b7e6-eb6c-45af-b8e6-793c53d10bb1.jpg','9a493028-8641-4761-8938-95e33550b1ce','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 12:22:45'),
(56,2,'e2a24f28-ca30-4468-b359-3cc2375b4fd7.jpg','9a493028-8641-4761-8938-95e33550b1ce','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 12:22:45'),
(61,2,'f4b9e452-8d23-4548-8c89-2148db93dcd8.jpg','b8038072-19f0-48c6-ab43-3947dba1978d','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 12:33:37'),
(62,2,'a6b87f80-f35e-4512-a581-c97352a935ad.jpg','b8038072-19f0-48c6-ab43-3947dba1978d','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 12:33:37'),
(63,2,'97446733-bed7-4c72-bef5-5948a970f2fe.jpg','b8038072-19f0-48c6-ab43-3947dba1978d','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 12:33:37'),
(64,2,'9566b123-35e8-494c-a14c-b374ff76307f.jpg','b8038072-19f0-48c6-ab43-3947dba1978d','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 12:33:37'),
(97,2,'ff57c2b3-ba76-4d8d-a514-003a5206db3f.jpg','4773c4de-3d6b-4022-920e-2256d307dc6c','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 13:23:19'),
(98,2,'2b5b148d-d38a-49e9-aca8-9e936eec1026.jpg','4773c4de-3d6b-4022-920e-2256d307dc6c','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 13:23:19'),
(99,2,'ede7e564-0102-4554-a61e-c96dd51a1808.jpg','4773c4de-3d6b-4022-920e-2256d307dc6c','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 13:23:19'),
(100,2,'2450b072-5a2e-4fe4-b88d-0aeac28fb6c1.jpg','4773c4de-3d6b-4022-920e-2256d307dc6c','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 13:23:19'),
(101,2,'5118c8c8-d7e9-44ca-9d04-27e5991fd757.jpg','2503ced4-2349-41e7-81fe-c89d96272ed0','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 13:24:53'),
(102,2,'fa480a04-fc1e-45a9-a7a5-6eed5c21cc36.jpg','2503ced4-2349-41e7-81fe-c89d96272ed0','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 13:24:53'),
(103,2,'55d935d6-58af-4534-9c15-edf919b3eae0.jpg','2503ced4-2349-41e7-81fe-c89d96272ed0','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 13:24:53'),
(104,2,'0c95b7a3-1ace-43ce-bad2-55da67519405.jpg','2503ced4-2349-41e7-81fe-c89d96272ed0','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 13:24:53'),
(137,2,'6a27006c-f8ce-4e34-9d3a-888f0e33f007.jpg','d2431bb7-ce25-4667-b427-544606be344a','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 15:48:31'),
(138,2,'356e4877-06b7-4fec-9715-dc5160908956.jpg','d2431bb7-ce25-4667-b427-544606be344a','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 15:48:31'),
(139,2,'e77c1614-84f8-4a75-bcfb-9539b9025a77.jpg','d2431bb7-ce25-4667-b427-544606be344a','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 15:48:31'),
(140,2,'198bddb7-f47a-4ecc-965e-9f5c056f1266.jpg','d2431bb7-ce25-4667-b427-544606be344a','../../static/images/content2.jpg',NULL,NULL,0,1,'2022-07-27 15:48:31');

/*Table structure for table `tb_disable_diary_videos` */

DROP TABLE IF EXISTS `tb_disable_diary_videos`;

CREATE TABLE `tb_disable_diary_videos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `diary_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '动态id',
  `user_id` bigint NOT NULL COMMENT '发布者id',
  `audio_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户使用音频的信息',
  `video_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '视频描述',
  `video_path` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '视频存放的路径',
  `video_seconds` float DEFAULT NULL COMMENT '视频秒数',
  `video_width` int DEFAULT NULL COMMENT '视频宽度',
  `video_height` int DEFAULT NULL COMMENT '视频高度',
  `cover_path` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '视频封面图',
  `like_counts` bigint DEFAULT '0' COMMENT '喜欢/赞美的数量',
  `status` int DEFAULT '1' COMMENT '视频状态：0-审核不通过 1-发布成功',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='视频信息表';

/*Data for the table `tb_disable_diary_videos` */

insert  into `tb_disable_diary_videos`(`id`,`diary_id`,`user_id`,`audio_id`,`video_desc`,`video_path`,`video_seconds`,`video_width`,`video_height`,`cover_path`,`like_counts`,`status`,`create_time`) values 
(1,'cb0951b7-7b91-463b-852f-7dfb8895b212',4,'1','214','D:\\Users\\Picture\\31db106a-dfe5-4095-9044-d20505c32766.mp4',35,12,24,'D:\\Users\\Picture\\31db106a-dfe5-4095-9044-d20505c32766.jpg',0,1,'2022-07-31 21:09:40'),
(2,'61ecebcc-58f9-4cff-a1c4-327fa6a6aabb',2,'1','123','D:\\Users\\Picture\\31db106a-dfe5-4095-9044-d20505c32766.mp4',23,12,23,'D:\\Users\\Picture\\31db106a-dfe5-4095-9044-d20505c32766.jpg',0,1,'2022-07-31 21:10:46'),
(3,'d0657773-7d27-4e4c-851a-e9e9bcc47310',1,'1','213','D:\\Users\\Picture\\31db106a-dfe5-4095-9044-d20505c32766.mp4',212,12,NULL,NULL,0,1,'2022-08-02 19:52:39'),
(4,'61ecebcc-58f9-4cff-a1c4-327fa6a6aabb',3,'1','213','D:\\Users\\Picture\\31db106a-dfe5-4095-9044-d20505c32766.mp4',123,1,NULL,NULL,0,1,'2022-08-03 02:39:20'),
(5,'cb0951b7-7b91-463b-852f-7dfb8895b56f',2,'1','212','D:\\Users\\aiee\\aewa\\aeg\\a\\gaw\\gwaeg.mp4',NULL,NULL,NULL,NULL,0,1,'2022-08-03 16:26:31'),
(6,'485755ab-1b70-4629-b36a-c7edd3db14bd',2,'1','123','D:\\Users\\Picture\\31db106a-dfe5-4095-9044-d20505c32766.mp4',NULL,NULL,NULL,NULL,0,1,'2022-08-03 16:27:38'),
(7,'aetqeaf2-at23q-tq3t-23-52q5-q235-q35',1,'2','1223','D:\\Users\\Picture\\31db106a-dfe5-4095-9044-d20505c32766.mp4',NULL,NULL,NULL,NULL,0,1,'2022-08-03 16:28:15'),
(8,'9ba061c5-f229-44ad-ae4f-05a293287dfa',2,'1','214','D:\\Users\\Picture\\31db106a-dfe5-4095-9044-d20505c32766.mp4',NULL,NULL,NULL,NULL,0,1,'2022-08-03 16:29:03');

/*Table structure for table `tb_disable_users_report` */

DROP TABLE IF EXISTS `tb_disable_users_report`;

CREATE TABLE `tb_disable_users_report` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(10) DEFAULT NULL COMMENT '举报人id',
  `reported_user_id` varchar(10) DEFAULT NULL COMMENT '被举报的人id',
  `reported_id` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '被举报内容的id',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '举报的原因',
  `type` int DEFAULT NULL COMMENT '类型 1-视频动态 2-非视频动态 3-图片秀 4-评论发言 5-举报用户这个人',
  `status` int DEFAULT NULL COMMENT '举报的审核状态 0-举报失败 1-举报成功 2-待审核',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '举报时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='举报信息表';

/*Data for the table `tb_disable_users_report` */

insert  into `tb_disable_users_report`(`id`,`user_id`,`reported_user_id`,`reported_id`,`reason`,`type`,`status`,`create_time`) values 
(1,'1','2','kjbribaoaiiebgabg-aegwai3gt3-gga12r-1','太low了',1,NULL,'2022-08-06 11:22:11'),
(2,'1','2','kjbribaoaiiebgabg-aegwai3gt3-gga12r-1','太low了',1,NULL,'2022-08-06 13:15:16'),
(3,'1','2','kjbribaoaiiebgabg-aegwai3gt3-gga12r-1','太low了',1,NULL,'2022-08-06 13:15:18'),
(4,'1','2','kjbribaoaiiebgabg-aegwai3gt3-gga12r-1','太low了',1,NULL,'2022-08-06 13:15:19'),
(5,'1','2','kjbribaoaiiebgabg-aegwai3gt3-gga12r-1','太low了',1,NULL,'2022-08-06 13:15:20'),
(6,'1','2','kjbribaoaiiebgabg-aegwai3gt3-gga12r-1','太low了',1,NULL,'2022-08-06 13:15:20');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
