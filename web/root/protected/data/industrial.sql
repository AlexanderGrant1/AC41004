CREATE TABLE IF NOT EXISTS `Administrator` (
	`Id` smallint unsigned NOT NULL auto_increment,
	`Username` varchar(30) NOT NULL,
	`LoginKey` varchar(40) NOT NULL,
	PRIMARY KEY(`Id`),
	UNIQUE(`Username`)
) ENGINE=InnoDB CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS `potato_Photo` (
	`Id` smallint unsigned NOT NULL auto_increment,
	`Name` varchar(37) NOT NULL,
	UNIQUE(`Name`),
	PRIMARY KEY(`Id`)
) ENGINE=InnoDB CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS `potato_Pest` (
	`Id` smallint unsigned NOT NULL auto_increment,
	`Name` varchar(50) NOT NULL,
	`Description` text NOT NULL,
	UNIQUE(`Name`),
	PRIMARY KEY(`Id`)
)ENGINE=InnoDB CHARACTER SET utf8
COLLATE utf8_general_ci;


CREATE TABLE IF NOT EXISTS `potato_Pest_photo` (
	`Id` smallint unsigned NOT NULL auto_increment,
	`PestId` smallint unsigned NOT NULL,
	`PhotoId` smallint unsigned NOT NULL,
	PRIMARY KEY(`Id`),
	FOREIGN KEY(`PestId`) REFERENCES `potato_Pest`(`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(`PhotoId`) REFERENCES `potato_Photo`(`Id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS `potato_PlantLeaf` (
	`Id` smallint unsigned NOT NULL auto_increment,
	`Name` varchar(50) NOT NULL,
	`Description` text NOT NULL,
	UNIQUE(`Name`),
	PRIMARY KEY(`Id`)
)ENGINE=InnoDB CHARACTER SET utf8
COLLATE utf8_general_ci;


CREATE TABLE IF NOT EXISTS `potato_PlantLeaf_photo` (
	`Id` smallint unsigned NOT NULL auto_increment,
	`PlantLeafId` smallint unsigned NOT NULL,
	`PhotoId` smallint unsigned NOT NULL,
	PRIMARY KEY(`Id`),
	FOREIGN KEY(`PlantLeafId`) REFERENCES `potato_PlantLeaf`(`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(`PhotoId`) REFERENCES `potato_Photo`(`Id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB CHARACTER SET utf8
COLLATE utf8_general_ci;


CREATE TABLE IF NOT EXISTS `potato_Tuber` (
	`Id` smallint unsigned NOT NULL auto_increment,
	`Name` varchar(50) NOT NULL,
	`Description` text NOT NULL,
	UNIQUE(`Name`),
	PRIMARY KEY(`Id`)
)ENGINE=InnoDB CHARACTER SET utf8
COLLATE utf8_general_ci;


CREATE TABLE IF NOT EXISTS `potato_Tuber_photo` (
	`Id` smallint unsigned NOT NULL auto_increment,
	`TuberId` smallint unsigned NOT NULL,
	`PhotoId` smallint unsigned NOT NULL,
	PRIMARY KEY(`Id`),
	FOREIGN KEY(`TuberId`) REFERENCES `potato_Tuber`(`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(`PhotoId`) REFERENCES `potato_Photo`(`Id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB CHARACTER SET utf8
COLLATE utf8_general_ci;


CREATE TABLE IF NOT EXISTS `potato_Tutorial` (
	`Id` smallint unsigned NOT NULL auto_increment,
	`Name` varchar(50) NOT NULL,
	`Description` text NOT NULL,
	`VideoName` varchar(37),
	UNIQUE(`Name`),
	PRIMARY KEY(`Id`)
)ENGINE=InnoDB CHARACTER SET utf8
COLLATE utf8_general_ci;




