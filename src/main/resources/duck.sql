/*
SQLyog v10.2 
MySQL - 5.5.60-log : Database - vahwallet
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE=''NO_AUTO_VALUE_ON_ZERO'' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`vahwallet` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `vahwallet`;

/*Table structure for table `vah_d_dictionary` */

DROP TABLE IF EXISTS `vah_d_dictionary`;

CREATE TABLE `vah_d_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT ''编号'',
  `parent_id` varchar(32) DEFAULT NULL COMMENT ''父节点ID'',
  `name` varchar(50) DEFAULT NULL COMMENT ''属性名'',
  `value` varchar(255) DEFAULT NULL COMMENT ''属性值'',
  `code` int(11) DEFAULT NULL COMMENT ''属性码'',
  `type` varchar(20) DEFAULT NULL COMMENT ''类型'',
  `sort_sign` int(11) DEFAULT NULL COMMENT ''排序'',
  `status` int(11) DEFAULT NULL COMMENT ''1-有效 2-无效'',
  `create_aid` int(11) DEFAULT NULL COMMENT ''添加人'',
  `create_time` timestamp NULL DEFAULT NULL COMMENT ''添加时间'',
  `update_aid` int(11) DEFAULT NULL COMMENT ''修改人'',
  `update_time` timestamp NULL DEFAULT NULL COMMENT ''修改时间'',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `vah_d_dictionary` */

insert  into `vah_d_dictionary`(`id`,`parent_id`,`name`,`value`,`code`,`type`,`sort_sign`,`status`,`create_aid`,`create_time`,`update_aid`,`update_time`) values (1,''0'',''扣除手续费'',''扣除手续费'',10,''DEDUCT_FORMALITIES'',10,1,1,''2018-07-30 14:48:16'',NULL,NULL),(2,''1'',''转入'',''10'',10,''DEDUCT_FORMALITIES'',20,1,1,''2018-07-30 14:48:49'',NULL,NULL),(3,''1'',''转出'',''10'',10,''DEDUCT_FORMALITIES'',30,1,1,''2018-07-30 14:49:03'',NULL,NULL),(4,''1'',''充值'',''5'',10,''DEDUCT_FORMALITIES'',40,1,1,''2018-07-30 14:49:23'',NULL,NULL),(5,''1'',''提现'',''10'',10,''DEDUCT_FORMALITIES'',50,1,1,''2018-07-30 14:49:41'',NULL,NULL),(6,''1'',''利息'',''5'',10,''DEDUCT_FORMALITIES'',60,1,1,''2018-07-30 14:50:02'',NULL,NULL),(7,''0'',''福利奖'',''福利奖'',10,''WALFARE_MODE'',10,1,1,''2018-07-30 14:53:27'',NULL,NULL),(8,''7'',''利息生成'',''0.5'',10,''WALFARE_MODE'',20,1,1,''2018-07-30 14:53:56'',NULL,NULL),(9,''7'',''直推奖'',''0.5'',10,''WALFARE_MODE'',30,1,1,''2018-07-30 14:54:17'',NULL,NULL),(10,''7'',''2倍奖'',''0.05'',10,''WALFARE_MODE'',40,1,1,''2018-07-30 14:54:40'',NULL,NULL),(11,''7'',''5倍奖'',''0.05'',10,''WALFARE_MODE'',50,1,1,''2018-07-30 14:54:54'',NULL,NULL);

/*Table structure for table `vah_d_directtprize` */

DROP TABLE IF EXISTS `vah_d_directtprize`;

CREATE TABLE `vah_d_directtprize` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT ''编号'',
  `referee_id` int(11) DEFAULT NULL COMMENT ''推荐人编号'',
  `coverReferee_id` int(11) DEFAULT NULL COMMENT ''被推荐人'',
  `coinId` int(11) DEFAULT NULL COMMENT ''币种编号'',
  `amountUsed` decimal(20,8) DEFAULT ''0.00000000'' COMMENT ''已用金额'',
  `amount` decimal(20,8) DEFAULT ''0.00000000'' COMMENT ''直推本金( 被推荐人本金*30%)'',
  `amountAvailable` decimal(20,8) DEFAULT ''0.00000000'' COMMENT ''可用金额'',
  `create_time` timestamp NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_time` timestamp NULL DEFAULT NULL COMMENT ''修改时间'',
  `reamrk` varchar(2000) DEFAULT NULL COMMENT ''备注'',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vah_d_directtprize` */

/*Table structure for table `vah_t_coin` */

DROP TABLE IF EXISTS `vah_t_coin`;

CREATE TABLE `vah_t_coin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coinName` varchar(255) NOT NULL COMMENT ''币种名称'',
  `coinImg` varchar(255) DEFAULT NULL COMMENT ''币种图标'',
  `createTime` datetime DEFAULT NULL COMMENT ''创建时间'',
  `updateTime` datetime DEFAULT NULL COMMENT ''更新时间'',
  `address` varchar(255) DEFAULT NULL COMMENT ''合约币地址'',
  `status` int(11) DEFAULT NULL COMMENT ''状态（1：正常，2：无效）'',
  PRIMARY KEY (`id`,`coinName`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT=''币种表'';

/*Data for the table `vah_t_coin` */

insert  into `vah_t_coin`(`id`,`coinName`,`coinImg`,`createTime`,`updateTime`,`address`,`status`) values (1,''BTS'',''http://img011.hc360.cn/k1/M07/6F/49/wKhQw1eDl9-EERlQAAAAANiIdPo336.jpg'',''2018-07-21 14:46:07'',NULL,''深圳市南山区深大迅美科技广场1'',1),(2,''EOS'',''http://img011.hc360.cn/k1/M07/6F/49/wKhQw1eDl9-EERlQAAAAANiIdPo336.jpg'',''2018-07-28 16:34:19'',NULL,''深圳市南山区深大迅美科技广场2'',1);

/*Table structure for table `vah_t_investment` */

DROP TABLE IF EXISTS `vah_t_investment`;

CREATE TABLE `vah_t_investment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT ''id'',
  `walletId` int(11) DEFAULT NULL COMMENT ''钱包币种编号'',
  `interests` decimal(20,8) DEFAULT ''0.00000000'' COMMENT ''利息（2%）'',
  `recommend` decimal(20,8) DEFAULT ''0.00000000'' COMMENT ''直推奖（30%） 拿下一个用户的30%币'',
  `dynamicAward` decimal(20,8) DEFAULT ''0.00000000'' COMMENT ''阶级，515 遇到当前用户下人数的5的倍数进行奖励'',
  `createTime` timestamp NULL DEFAULT NULL COMMENT ''创建时间'',
  `updateTime` timestamp NULL DEFAULT NULL COMMENT ''更新时间'',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT=''理财表'';

/*Data for the table `vah_t_investment` */

insert  into `vah_t_investment`(`id`,`walletId`,`interests`,`recommend`,`dynamicAward`,`createTime`,`updateTime`) values (3,NULL,''0.00000000'',''0.00000000'',''0.00000000'',''2018-08-23 13:45:47'',''2018-08-23 13:45:47''),(4,NULL,''0.00000000'',''0.00000000'',''0.00000000'',''2018-08-23 13:45:47'',''2018-08-23 13:45:47''),(5,NULL,''0.00000000'',''0.00000000'',''0.00000000'',''2018-08-23 14:26:31'',''2018-08-23 14:26:31''),(6,NULL,''0.00000000'',''0.00000000'',''0.00000000'',''2018-08-23 14:26:31'',''2018-08-23 14:26:31'');

/*Table structure for table `vah_t_sms` */

DROP TABLE IF EXISTS `vah_t_sms`;

CREATE TABLE `vah_t_sms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT ''id'',
  `tel` varchar(20) NOT NULL COMMENT ''手机号码'',
  `content` varchar(255) NOT NULL COMMENT ''短信内容'',
  `sendid` varchar(255) NOT NULL COMMENT ''短信请求号'',
  `create_time` timestamp NOT NULL DEFAULT ''0000-00-00 00:00:00'' ON UPDATE CURRENT_TIMESTAMP COMMENT ''请求时间'',
  `status` varchar(255) NOT NULL DEFAULT ''0'' COMMENT ''状态 0发送短信成功 1发送短信失败 2 已使用'',
  PRIMARY KEY (`id`,`tel`),
  KEY `rf_status` (`status`,`tel`,`create_time`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

/*Data for the table `vah_t_sms` */

insert  into `vah_t_sms`(`id`,`tel`,`content`,`sendid`,`create_time`,`status`) values (20,''18129851846'',''7488'',''2018082313453117963498543'',''2018-08-23 13:45:40'',''2''),(21,''13266793842'',''7043'',''2018082314261952819954894'',''2018-08-23 14:26:25'',''2''),(22,''18129851846'',''5527'',''2018082510132584782509603'',''2018-08-25 10:13:32'',''2''),(23,''13266793842'',''2742'',''2018082516393533668930610'',''2018-08-25 16:39:52'',''2''),(24,''13266793842'',''1352'',''2018082516405097087890473'',''2018-08-25 16:41:06'',''2''),(25,''18129851846'',''7513'',''2018082516451375145098556'',''2018-08-25 16:45:36'',''2''),(26,''13266793842'',''3671'',''2018082516455768578229572'',''2018-08-25 16:46:16'',''2''),(27,''13266793842'',''3622'',''2018082516475641487658835'',''2018-08-25 16:48:12'',''2''),(28,''18129851846'',''4017'',''2018090311111824185658300'',''2018-09-03 11:11:24'',''2'');

/*Table structure for table `vah_t_transcation` */

DROP TABLE IF EXISTS `vah_t_transcation`;

CREATE TABLE `vah_t_transcation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT ''用户ID'',
  `coinId` int(11) DEFAULT NULL COMMENT ''币种编号'',
  `coinType` varchar(255) DEFAULT NULL COMMENT ''币种'',
  `amount` decimal(20,8) DEFAULT NULL COMMENT ''交易金额'',
  `txType` int(11) DEFAULT NULL COMMENT ''交易类型（1：转入，2：转出，3：直推，4：利息，5:团队奖(动态奖)，6:扣除手续费，7、充值 8、提现）'',
  `from` varchar(255) DEFAULT NULL COMMENT ''转出地址'',
  `to` varchar(255) DEFAULT NULL COMMENT ''收款地址'',
  `hash` varchar(255) DEFAULT NULL COMMENT ''交易ID'',
  `remark` varchar(255) NOT NULL DEFAULT '''' COMMENT ''备注'',
  `txStatus` int(11) DEFAULT ''1'' COMMENT ''交易状态（1：已提交，2：已完成）'',
  `capital` decimal(20,8) DEFAULT NULL COMMENT ''本金'',
  `createTime` timestamp NULL DEFAULT NULL COMMENT ''订单创建时间'',
  `updateTime` timestamp NULL DEFAULT NULL COMMENT ''订单修改时间'',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT=''交易记录表'';

/*Data for the table `vah_t_transcation` */

/*Table structure for table `vah_t_user` */

DROP TABLE IF EXISTS `vah_t_user`;

CREATE TABLE `vah_t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phoneNum` varchar(11) NOT NULL COMMENT ''手机号码'',
  `passwd` varchar(255) NOT NULL COMMENT ''密码'',
  `seeds` varchar(255) NOT NULL DEFAULT '''' COMMENT ''种子'',
  `headImg` varchar(255) NOT NULL DEFAULT '''' COMMENT ''头像'',
  `status` int(1) NOT NULL DEFAULT ''1'' COMMENT ''1、正常 2、出局 3、冻结'',
  `nickName` varchar(255) NOT NULL DEFAULT '''' COMMENT ''昵称'',
  `sex` int(1) NOT NULL DEFAULT ''0'' COMMENT ''0 男 1女'',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT ''创建时间'',
  `updateTime` timestamp NOT NULL DEFAULT ''0000-00-00 00:00:00'' COMMENT ''修改时间'',
  `relationship` varchar(1000) NOT NULL COMMENT ''用户关系树 1,10 代表啊父id 为1 10'',
  `invitationCode` varchar(255) NOT NULL COMMENT ''邀请码'',
  `mnemonit` varchar(255) NOT NULL COMMENT ''助记词'',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT=''用户表'';

/*Data for the table `vah_t_user` */

insert  into `vah_t_user`(`id`,`phoneNum`,`passwd`,`seeds`,`headImg`,`status`,`nickName`,`sex`,`createTime`,`updateTime`,`relationship`,`invitationCode`,`mnemonit`) values (1,''18129851846'',''e91e86995f04994de163e86b50b35c7a'','''',''http://tongyongbucket.oss-cn-hangzhou.aliyuncs.com/ab3508b4-72db-44e5-8ee8-31208f617de6'',1,''clarify'',1,''2018-08-24 16:16:54'',''2018-08-23 13:45:44'',''0'',''0002'',''move cushion rude merge forward endorse travel produce toss candy apple retire''),(2,''13266793842'',''8fb95dcf14b689e2adec009ea655446c'','''',''http://tongyongbucket.oss-cn-hangzhou.aliyuncs.com/ab3508b4-72db-44e5-8ee8-31208f617de6'',1,''section'',0,''2018-09-03 16:24:22'',''2018-08-25 16:48:08'',''0'',''0007'',''glass curve ten rug later drama feed pave wall basic bike chief''),(3,''15889762645'',''e10adc3949ba59abbe56e057f20f883e'','''',''http://tongyongbucket.oss-cn-hangzhou.aliyuncs.com/ab3508b4-72db-44e5-8ee8-31208f617de6'',1,''ply'',0,''2018-08-24 16:18:33'',''2018-08-24 16:18:35'',''0'',''2222'',''''),(4,''15874381234'',''e10adc3949ba59abbe56e057f20f883e'','''',''http://tongyongbucket.oss-cn-hangzhou.aliyuncs.com/ab3508b4-72db-44e5-8ee8-31208f617de6'',1,''PLY'',0,''2018-09-03 16:25:42'',''2018-08-24 16:20:11'',''0'',''1111'','''');

/*Table structure for table `vah_y_wallet` */

DROP TABLE IF EXISTS `vah_y_wallet`;

CREATE TABLE `vah_y_wallet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL COMMENT ''币种全称'',
  `coinName` varchar(200) DEFAULT NULL COMMENT ''币种名称'',
  `coinImg` varchar(2000) DEFAULT NULL COMMENT ''币种图像'',
  `userId` int(11) DEFAULT NULL COMMENT ''用户ID'',
  `address` varchar(255) DEFAULT NULL COMMENT ''钱包地址'',
  `contractAddr` varchar(255) DEFAULT NULL COMMENT ''合约币地址'',
  `privateKey` varchar(255) DEFAULT NULL COMMENT ''钱包私钥'',
  `passwd` varchar(255) DEFAULT NULL COMMENT ''密码'',
  `createTime` timestamp NULL DEFAULT NULL COMMENT ''钱包创建时间'',
  `updateTime` timestamp NULL DEFAULT NULL COMMENT ''钱包更新时间'',
  `keystore` varchar(255) DEFAULT NULL COMMENT ''keystore(密钥库)'',
  `freeAmount` decimal(20,8) DEFAULT ''0.00000000'' COMMENT ''冻结金额'',
  `amount` decimal(20,8) DEFAULT ''0.00000000'' COMMENT ''钱包总额'',
  `availableAmount` decimal(20,8) DEFAULT ''0.00000000'' COMMENT ''可用金额(钱包总额-冻结金额)'',
  `remark` varchar(2000) DEFAULT '''' COMMENT ''备注信息'',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8 COMMENT=''钱包表'';

/*Data for the table `vah_y_wallet` */

insert  into `vah_y_wallet`(`id`,`name`,`coinName`,`coinImg`,`userId`,`address`,`contractAddr`,`privateKey`,`passwd`,`createTime`,`updateTime`,`keystore`,`freeAmount`,`amount`,`availableAmount`,`remark`) values (19,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',2,''9cf93d99c427d4827c831c8fda6b184e64fa88e5'',NULL,''de796f2d32ce4c67b35747a2ce799731'',''123456'',''2018-07-24 10:02:53'',''2018-08-02 15:28:01'',''ac2cc2a7b5c544b4a14ce4a87a588748'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(21,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',4,''aba9dab59dc01271cc54cf585ab7941f342c1f4e'',NULL,''e25ca7b0c02c411a9ae789b8836f37d3'',''123456'',''2018-08-10 15:21:23'',''2018-08-10 15:21:23'',''f62d86d20b3d47c9b48effbc2e558319'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(23,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',8,''0x902486b3b6a986a13c8005777579bfa5b70a785b'',NULL,''f2df4d82e2424c948d1ca01baf74386c'',''111111'',''2018-08-11 14:20:12'',''2018-08-11 14:20:12'',''47a01e57c394474cae404b3a3c36e17e'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(24,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',6,''0x65dac5cb3844a65cd4dd10ec4f36e121519d83f6'',NULL,''8b2a36be30634109acb400331e522b45'',''123456'',''2018-08-11 15:32:19'',''2018-08-11 15:32:19'',''eaaa464deada47e583983b7ea47952f5'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(25,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',3,''0x578cf2058ef501a28b93ccfed53d51c517553279'',NULL,''a343b7bfdc6741d6963314373eeedff0'',''123456'',''2018-08-11 15:36:33'',''2018-08-11 15:36:33'',''62a926a8c13d4f38921d2d7652ac57d3'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(26,''HUC (HUC)'',''HUC'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',3,''0x578cf2058ef501a28b93ccfed53d51c517553279'',''0xa5feeb8d6fd6bfb46df35924a34aebf7a36ab8bd'',''b8a94411c51540a292a647d589e71852'',''123456'',''2018-08-11 15:54:47'',''2018-08-11 15:54:51'',''e864c95f02e247df9c367b806dbb8dde'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(27,''HUC (HUC)'',''HUC'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',4,''aba9dab59dc01271cc54cf585ab7941f342c1f4e'',''0xa5feeb8d6fd6bfb46df35924a34aebf7a36ab8bd'',''f5563dcf8fc143d0a4aa327e588b2621'',''123456'',''2018-08-11 17:18:34'',''2018-08-11 17:18:36'',''b6ccd71e1b2e49969cb2ebbc84107fcb'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(28,''HUC (HUC)'',''HUC'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',2,''9cf93d99c427d4827c831c8fda6b184e64fa88e5'',''0xa5feeb8d6fd6bfb46df35924a34aebf7a36ab8bd'',''f7343bbc326542d18d4a73c3ee41a628'',''123456'',''2018-08-13 10:38:13'',''2018-08-13 10:38:15'',''b0107b7ac5384cfeae93dae372193f8a'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(29,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',11,''0xde95c4f2af136651592ecfacde9a448d34834cab'',NULL,''4646301aa1e94e28a2dc2b920288f1b0'',''123456'',''2018-08-13 17:40:28'',''2018-08-13 17:40:28'',''8bc8dfc76b5a498da970616a6d044b97'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(30,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',10,''0xee9ecf45194dd47f6905dadc3b3141222372be24'',NULL,''41faed6b66c143aa89802645c8d760d4'',''123456'',''2018-08-13 17:45:07'',''2018-08-13 17:45:07'',''1c85806fffbb4c71a26c29046dff85aa'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(31,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',12,''0x12a5cadb493d8d39b7ebcd4e2b32bf1196870f89'',NULL,''33a8c9ce9da14abdab9be15bcde40787'',''123456'',''2018-08-13 18:46:38'',''2018-08-13 18:46:38'',''f25c630843cd4390a78eb0fdadc21618'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(32,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',13,''0x44a9650397c16e6ba14e0bdca4e0b1727afb54a5'',NULL,''bf81dc55413b44c0882d6e3a18682d4f'',''900917'',''2018-08-14 17:12:42'',''2018-08-14 17:12:42'',''96bee5797a5941f482da90f3a327e484'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(33,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',14,''0xc80bfe5dad0bf97817c7df392eb0c9a677c5ee1d'',NULL,''ad5476f80f37412db0eeae55921b6390'',''123456'',''2018-08-14 17:39:29'',''2018-08-14 17:39:29'',''81c7cb5fa5d947fe92949e6641cb41b0'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(34,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',15,''0x54292bcc33864bcdedb16241a05dcd013e5c495d'',NULL,''32b346b987544a6caf6f4dafe08f6a3a'',''123321'',''2018-08-14 17:41:45'',''2018-08-14 17:41:45'',''6753f9a5bd964317a8d91fa4668d5519'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(35,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',16,''0xcba8bb3c548715828be9fc6e87865159197f1237'',NULL,''f130f6b4d204480da00d06585e8178e4'',''123456'',''2018-08-14 18:47:31'',''2018-08-14 18:47:31'',''b101ad72f531485088cf7ab8023c8cde'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(36,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',5,''0xb1fd008a597cf33495cdf84dc9187918a9f1d3ab'',NULL,''670323dfd93d4ea09bccd7018c23f8cc'',''123456'',''2018-08-15 17:55:06'',''2018-08-15 17:55:06'',''1af6982650a24552b665e658030ea54b'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(37,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',19,''0x662f49597c5af4dbe38e06b408b65ee98d1fb843'',NULL,''21de896494db4e09967ea9067cebcb7b'',''123456'',''2018-08-22 19:11:21'',''2018-08-22 19:11:21'',''ba7987bcc5a343449340d03169a93b6b'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(38,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',20,''0x6325ab88f5b752dd7bc147d3cf204dbebd67936b'',NULL,''2e717f9204294aa1989af36c0518cd02'',''123456'',''2018-08-23 10:09:57'',''2018-08-23 10:09:57'',''8f0b3e86f03d4272a893ed61843f7975'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(39,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',21,''0x6b04a3a9b56ac1ff9c3c5f5a0db80ef932afbafb'',NULL,''67649552541649d28da35913e317982c'',''123456'',''2018-08-23 10:27:24'',''2018-08-23 10:27:24'',''922abfbffd7e44e8b7ac9d45f087757f'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(40,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',22,''0x2dfdaff98c72baab55379e523c79fad2eb7a7810'',NULL,''6e7c7cd8d4ff4143844b06833e0811f5'',''123456'',''2018-08-23 10:35:04'',''2018-08-23 10:35:04'',''4c3d81a4c7434771914463cc07090665'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(41,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',23,''0x89165e87be6581df82dec86e1e4e00d451506802'',NULL,''b406da06594c4aa9a436ea3709b37308'',''123456'',''2018-08-23 10:54:21'',''2018-08-23 10:54:21'',''00416e3ed7bd43e3b714a741ea1ab163'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(45,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',1,''0x730fca45a5a8079da3079517b90242c64164c179'',NULL,''0212603c42d440b4bb3cde3a60f99b20'',''123456'',''2018-08-25 13:33:49'',''2018-08-25 13:33:49'',''4e9e482c7d7c416989f4295d5e246fe2'',''0.00000000'',''0.00000000'',''0.00000000'',''''),(56,''ETH'',''ETH'',''https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg'',25,''0x84109e2a06eca25cbfbddc605bf0881b733ad4b9'',NULL,''66942e0ccdff4771844159e6ab3d1ccd'',''123456'',''2018-08-25 16:49:42'',''2018-08-25 16:49:42'',''252e377258234cd9920af29d83d75453'',''0.00000000'',''0.00000000'',''0.00000000'','''');

/*Table structure for table `vah_y_wallet_status` */

DROP TABLE IF EXISTS `vah_y_wallet_status`;

CREATE TABLE `vah_y_wallet_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT ''编号'',
  `contractAddr` varchar(255) DEFAULT NULL COMMENT ''合约币地址'',
  `status` int(11) DEFAULT NULL COMMENT ''币种状态'',
  `create_time` timestamp NULL DEFAULT NULL COMMENT ''添加日期'',
  `update_time` timestamp NULL DEFAULT NULL COMMENT ''修改日期'',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vah_y_wallet_status` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
