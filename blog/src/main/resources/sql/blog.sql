/*
SQLyog Ultimate v11.27 (64 bit)
MySQL - 5.7.14-log : Database - blog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`blog` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `blog`;

/*Table structure for table `role_menu` */

DROP TABLE IF EXISTS `role_menu`;

CREATE TABLE `role_menu` (
  `ROLE_ID` int(11) DEFAULT NULL,
  `MENU_ID` int(11) DEFAULT NULL,
  KEY `ROLE_ID` (`ROLE_ID`),
  KEY `MENU_ID` (`MENU_ID`),
  CONSTRAINT `role_menu_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `t_role` (`ID`),
  CONSTRAINT `role_menu_ibfk_2` FOREIGN KEY (`MENU_ID`) REFERENCES `t_menu` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role_menu` */

insert  into `role_menu`(`ROLE_ID`,`MENU_ID`) values (1,1),(1,2),(1,3),(2,1),(2,2),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(2,3),(2,4),(2,5),(2,6),(2,7),(2,8),(2,9),(2,13),(2,14),(2,15),(3,1),(3,5),(3,8);

/*Table structure for table `t_menu` */

DROP TABLE IF EXISTS `t_menu`;

CREATE TABLE `t_menu` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(32) NOT NULL,
  `URL` varchar(32) NOT NULL,
  `MODULE_ID` int(11) NOT NULL,
  `SEQ` int(2) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `t_menu` */

insert  into `t_menu`(`ID`,`NAME`,`URL`,`MODULE_ID`,`SEQ`) values (1,'read','/photo/read',1,1),(2,'manage','/photo/manage',1,2),(3,'upload','/photo/upload',1,3),(4,'download','/photo/download',1,4),(5,'read','/artcle/read',2,1),(6,'manage','/artcle/manage',2,2),(7,'export','/artcle/export',2,3),(8,'read','/timestamp/read',3,1),(9,'manage','/timestamp/manage',3,2),(10,'export','/timestamp/export',3,3),(11,'read','/system/read',4,1),(12,'manage','/system/manage',4,2),(13,'private','/photo/private',1,5),(14,'private','/artcle/private',2,4),(15,'private','/timestamp/private',3,4);

/*Table structure for table `t_module` */

DROP TABLE IF EXISTS `t_module`;

CREATE TABLE `t_module` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ICON` varchar(32) NOT NULL,
  `SEQ` int(2) NOT NULL,
  `NAME` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `t_module` */

insert  into `t_module`(`ID`,`ICON`,`SEQ`,`NAME`) values (1,'glyphicon-picture',1,'photo'),(2,'glyphicon-list-alt',2,'artcle'),(3,'glyphicon-facetime-video',3,'timestamp'),(4,'glyphicon-wrench',4,'system');

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `t_role` */

insert  into `t_role`(`ID`,`NAME`) values (1,'admin'),(2,'user'),(3,'passerby');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NICK_NAME` varchar(32) NOT NULL,
  `ACCOUNT` varchar(32) NOT NULL,
  `PASSWORD` varchar(32) NOT NULL,
  `PHONE` varchar(11) DEFAULT NULL,
  `EMAIL` varchar(32) DEFAULT NULL,
  `ID_NO` decimal(18,0) DEFAULT NULL,
  `ROLE_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`ID`,`NICK_NAME`,`ACCOUNT`,`PASSWORD`,`PHONE`,`EMAIL`,`ID_NO`,`ROLE_ID`) values (1,'SUPERADMIN','admin','admin',NULL,NULL,NULL,1),(2,'JANE','jane','jane',NULL,NULL,NULL,2),(3,'PASSERBY','PASSERBY','PASSERBY',NULL,NULL,NULL,3);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
