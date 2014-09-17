CREATE TABLE IF NOT EXISTS `potato_Photo` (
	`Id` smallint unsigned NOT NULL auto_increment,
	`Name` varchar(20) NOT NULL,
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

CREATE TABLE IF NOT EXISTS `potato_NutrientDeficency` (
	`Id` smallint unsigned NOT NULL auto_increment,
	`Name` varchar(50) NOT NULL,
	`Description` text NOT NULL,
	UNIQUE(`Name`),
	PRIMARY KEY(`Id`)
)ENGINE=InnoDB CHARACTER SET utf8
COLLATE utf8_general_ci;


CREATE TABLE IF NOT EXISTS `potato_NutrientDeficency_photo` (
	`Id` smallint unsigned NOT NULL auto_increment,
	`NutrientDefId` smallint unsigned NOT NULL,
	`PhotoId` smallint unsigned NOT NULL,
	PRIMARY KEY(`Id`),
	FOREIGN KEY(`NutrientDefId`) REFERENCES `potato_NutrientDeficency`(`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(`PhotoId`) REFERENCES `potato_NutrientDeficency`(`Id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB CHARACTER SET utf8
COLLATE utf8_general_ci;



CREATE TABLE IF NOT EXISTS `potato_Disease` (
	`Id` smallint unsigned NOT NULL auto_increment,
	`Name` varchar(50) NOT NULL,
	`Description` text NOT NULL,
	UNIQUE(`Name`),
	PRIMARY KEY(`Id`)
)ENGINE=InnoDB CHARACTER SET utf8
COLLATE utf8_general_ci;


CREATE TABLE IF NOT EXISTS `potato_Disease_photo` (
	`Id` smallint unsigned NOT NULL auto_increment,
	`DiseaseId` smallint unsigned NOT NULL,
	`PhotoId` smallint unsigned NOT NULL,
	PRIMARY KEY(`Id`),
	FOREIGN KEY(`DiseaseId`) REFERENCES `potato_Disease`(`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(`PhotoId`) REFERENCES `potato_Photo`(`Id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB CHARACTER SET utf8
COLLATE utf8_general_ci;



CREATE TABLE IF NOT EXISTS `potato_Virus` (
	`Id` smallint unsigned NOT NULL auto_increment,
	`Name` varchar(50) NOT NULL,
	`Description` text NOT NULL,
	UNIQUE(`Name`),
	PRIMARY KEY(`Id`)
)ENGINE=InnoDB CHARACTER SET utf8
COLLATE utf8_general_ci;


CREATE TABLE IF NOT EXISTS `potato_Virus_photo` (
	`Id` smallint unsigned NOT NULL auto_increment,
	`VirusId` smallint unsigned NOT NULL,
	`PhotoId` smallint unsigned NOT NULL,
	PRIMARY KEY(`Id`),
	FOREIGN KEY(`VirusId`) REFERENCES `potato_Virus`(`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(`PhotoId`) REFERENCES `potato_Photo`(`Id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB CHARACTER SET utf8
COLLATE utf8_general_ci;


CREATE TABLE IF NOT EXISTS `potato_Tutorial` (
	`Id` smallint unsigned NOT NULL auto_increment,
	`Name` varchar(50) NOT NULL,
	`Description` text NOT NULL,
	`VideoName` varchar(20),
	UNIQUE(`Name`),
	PRIMARY KEY(`Id`)
)ENGINE=InnoDB CHARACTER SET utf8
COLLATE utf8_general_ci;




