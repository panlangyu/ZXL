SELECT * FROM PMBC.HUC_T_USER;
CREATE TABLE `HUC_T_USER` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) DEFAULT NULL,
  `sex` varchar(45) DEFAULT NULL,
  `userTrueName` varchar(45) DEFAULT NULL,
  `userPhone` varchar(45) DEFAULT NULL,
  `userIdCard` varchar(45) DEFAULT NULL,
  `userCountry` varchar(45) DEFAULT NULL,
  `userEmail` varchar(45) DEFAULT NULL,
  `createTime` varchar(45) DEFAULT NULL,
  `updateTime` varchar(45) DEFAULT NULL,
  `seeds` varchar(45) DEFAULT NULL,
  `transactionPW` varchar(45) DEFAULT NULL,
  `machinePW` varchar(45) DEFAULT NULL,
  `machineId` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `coinType` varchar(45) DEFAULT NULL,
  `buyNum` varchar(45) DEFAULT NULL,
  `birthDate` varchar(45) DEFAULT NULL,
  `postalCode` varchar(45) DEFAULT NULL,
  `ethAddress` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
