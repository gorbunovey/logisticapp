-- MySQL Workbench Forward Engineering
SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema logisticdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `logisticdb`;
-- -----------------------------------------------------
-- Schema logisticdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `logisticdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `logisticdb`;


-- -----------------------------------------------------
-- Table `logisticdb`.`CITY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `logisticdb`.`CITY`;

CREATE TABLE IF NOT EXISTS `logisticdb`.`CITY`
(
    `ID`   BIGINT      NOT NULL AUTO_INCREMENT,
    `CODE` BIGINT      NOT NULL UNIQUE,
    `NAME` VARCHAR(60) NOT NULL,
    PRIMARY KEY (`ID`),
    UNIQUE INDEX `CODE_UNIQUE` (`CODE` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logisticdb`.`ROLE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `logisticdb`.`ROLE`;

CREATE TABLE IF NOT EXISTS `logisticdb`.`ROLE`
(
    `ID`   BIGINT      NOT NULL AUTO_INCREMENT,
    `NAME` VARCHAR(60) NOT NULL UNIQUE,
    PRIMARY KEY (`ID`),
    UNIQUE INDEX `NAME_UNIQUE` (`NAME` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logisticdb`.`USER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `logisticdb`.`USER`;

CREATE TABLE IF NOT EXISTS `logisticdb`.`USER`
(
    `ID`         BIGINT      NOT NULL AUTO_INCREMENT,
    `NUMBER`     BIGINT      NOT NULL UNIQUE,
    `FIRST_NAME` VARCHAR(45) NOT NULL,
    `LAST_NAME`  VARCHAR(45) NOT NULL,
    `BIRTHDAY`   DATE        NULL,
    `EMAIL`      VARCHAR(60) NOT NULL UNIQUE,
    `PASSWORD`   VARCHAR(60) NOT NULL,
    `ROLE_ID`    BIGINT      NULL,
    PRIMARY KEY (`ID`),
    INDEX `fk_USER_ROLE1_idx` (`ROLE_ID` ASC) VISIBLE,
    UNIQUE INDEX `EMAIL_UNIQUE` (`EMAIL` ASC) VISIBLE,
    UNIQUE INDEX `NUMBER_UNIQUE` (`NUMBER` ASC) VISIBLE,
    CONSTRAINT `fk_USER_ROLE1`
        FOREIGN KEY (`ROLE_ID`)
            REFERENCES `logisticdb`.`ROLE` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logisticdb`.`DRIVER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `logisticdb`.`DRIVER`;

CREATE TABLE IF NOT EXISTS `logisticdb`.`DRIVER`
(
    `ID`      BIGINT NOT NULL AUTO_INCREMENT,
    `NUMBER`  BIGINT NOT NULL UNIQUE,
    `CITY_ID` BIGINT NULL,
    `USER_ID` BIGINT NOT NULL,
    PRIMARY KEY (`ID`),
    INDEX `fk_DRIVER_CITY1_idx` (`CITY_ID` ASC) VISIBLE,
    UNIQUE INDEX `num_DRIVER_idx` (`NUMBER` ASC) VISIBLE,
    INDEX `fk_DRIVER_USER1_idx` (`USER_ID` ASC) VISIBLE,
    CONSTRAINT `fk_DRIVER_CITY1`
        FOREIGN KEY (`CITY_ID`)
            REFERENCES `logisticdb`.`CITY` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_DRIVER_USER1`
        FOREIGN KEY (`USER_ID`)
            REFERENCES `logisticdb`.`USER` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logisticdb`.`TRUCK`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `logisticdb`.`TRUCK`;

CREATE TABLE IF NOT EXISTS `logisticdb`.`TRUCK`
(
    `ID`         BIGINT  NOT NULL AUTO_INCREMENT,
    `REG_NUMBER` CHAR(7) NOT NULL UNIQUE,
    `CREW`       INT     NOT NULL,
    `CAPACITY`   FLOAT   NOT NULL,
    `STATE`      TINYINT NOT NULL,
    `CITY_ID`    BIGINT  NULL,
    INDEX `fk_TRUCK_CITY1_idx` (`CITY_ID` ASC) VISIBLE,
    PRIMARY KEY (`ID`),
    CONSTRAINT `fk_TRUCK_CITY1`
        FOREIGN KEY (`CITY_ID`)
            REFERENCES `logisticdb`.`CITY` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logisticdb`.`CARGO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `logisticdb`.`CARGO`;

CREATE TABLE IF NOT EXISTS `logisticdb`.`CARGO`
(
    `ID`        BIGINT      NOT NULL AUTO_INCREMENT,
    `NUMBER`    BIGINT      NOT NULL UNIQUE,
    `TITLE`     VARCHAR(60) NOT NULL,
    `WEIGHT`    INT         NOT NULL,
    `STATUS`    VARCHAR(20) NOT NULL,
    `CITY_FROM` BIGINT      NOT NULL,
    `CITY_TO`   BIGINT      NOT NULL,
    PRIMARY KEY (`ID`),
    INDEX `fk_CARGO_CITY1_idx` (`CITY_FROM` ASC) VISIBLE,
    INDEX `fk_CARGO_CITY2_idx` (`CITY_TO` ASC) VISIBLE,
    UNIQUE INDEX `NUMBER_UNIQUE` (`NUMBER` ASC) VISIBLE,
    CONSTRAINT `fk_CARGO_CITY1`
        FOREIGN KEY (`CITY_FROM`)
            REFERENCES `logisticdb`.`CITY` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_CARGO_CITY2`
        FOREIGN KEY (`CITY_TO`)
            REFERENCES `logisticdb`.`CITY` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logisticdb`.`MAP`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `logisticdb`.`MAP`;

CREATE TABLE IF NOT EXISTS `logisticdb`.`MAP`
(
    `ID`                BIGINT NOT NULL AUTO_INCREMENT,
    `DEPARTURE_POINT`   BIGINT NOT NULL,
    `DESTINATION_POINT` BIGINT NOT NULL,
    `DISTANCE`          INT    NOT NULL,
    PRIMARY KEY (`ID`),
    INDEX `fk_MAP_CITY1_idx` (`DEPARTURE_POINT` ASC) VISIBLE,
    INDEX `fk_MAP_CITY2_idx` (`DESTINATION_POINT` ASC) VISIBLE,
    CONSTRAINT `fk_MAP_CITY1`
        FOREIGN KEY (`DEPARTURE_POINT`)
            REFERENCES `logisticdb`.`CITY` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_MAP_CITY2`
        FOREIGN KEY (`DESTINATION_POINT`)
            REFERENCES `logisticdb`.`CITY` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logisticdb`.`ORDER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `logisticdb`.`ORDER`;

CREATE TABLE IF NOT EXISTS `logisticdb`.`ORDER`
(
    `ID`       BIGINT  NOT NULL AUTO_INCREMENT,
    `NUMBER`   BIGINT  NOT NULL UNIQUE,
    `STATUS`   TINYINT NOT NULL,
    `TRUCK_ID` BIGINT  NOT NULL,
    PRIMARY KEY (`ID`),
    INDEX `fk_ORDER_TRUCK1_idx` (`TRUCK_ID` ASC) VISIBLE,
    UNIQUE INDEX `NUMBER_UNIQUE` (`NUMBER` ASC) VISIBLE,
    CONSTRAINT `fk_ORDER_TRUCK1`
        FOREIGN KEY (`TRUCK_ID`)
            REFERENCES `logisticdb`.`TRUCK` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logisticdb`.`WAY_POINT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `logisticdb`.`WAY_POINT`;

CREATE TABLE IF NOT EXISTS `logisticdb`.`WAY_POINT`
(
    `ID`         BIGINT  NOT NULL AUTO_INCREMENT,
    `ORDER_ID`   BIGINT  NOT NULL,
    `CARGO_ID`   BIGINT  NOT NULL,
    `TYPE`       TINYINT NOT NULL,
    `SEQ_NUMBER` INT     NOT NULL,
    PRIMARY KEY (`ID`),
    INDEX `fk_WAY_POINT_ORDER1_idx` (`ORDER_ID` ASC) VISIBLE,
    INDEX `fk_WAY_POINT_CARGO1_idx` (`CARGO_ID` ASC) VISIBLE,
    CONSTRAINT `fk_WAY_POINT_ORDER1`
        FOREIGN KEY (`ORDER_ID`)
            REFERENCES `logisticdb`.`ORDER` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_WAY_POINT_CARGO1`
        FOREIGN KEY (`CARGO_ID`)
            REFERENCES `logisticdb`.`CARGO` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logisticdb`.`ORDER_DRIVERS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `logisticdb`.`ORDER_DRIVERS`;

CREATE TABLE IF NOT EXISTS `logisticdb`.`ORDER_DRIVERS`
(
    `ORDER_ID`  BIGINT NOT NULL,
    `DRIVER_ID` BIGINT NOT NULL,
    PRIMARY KEY (`DRIVER_ID`, `ORDER_ID`),
    INDEX `fk_ORDER_DRIVERS_ORDER1_idx` (`ORDER_ID` ASC) VISIBLE,
    INDEX `fk_ORDER_DRIVERS_DRIVER1_idx` (`DRIVER_ID` ASC) VISIBLE,
    CONSTRAINT `fk_ORDER_DRIVERS_ORDER1`
        FOREIGN KEY (`ORDER_ID`)
            REFERENCES `logisticdb`.`ORDER` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_ORDER_DRIVERS_DRIVER1`
        FOREIGN KEY (`DRIVER_ID`)
            REFERENCES `logisticdb`.`DRIVER` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `logisticdb`.`DRIVER_HISTORY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `logisticdb`.`DRIVER_HISTORY`;

CREATE TABLE IF NOT EXISTS `logisticdb`.`DRIVER_HISTORY`
(
    `ID`          BIGINT      NOT NULL AUTO_INCREMENT,
    `STATUS`      VARCHAR(20) NOT NULL,
    `STATUS_TIME` DATETIME    NOT NULL,
    `DRIVER_ID`   BIGINT      NOT NULL,
    PRIMARY KEY (`ID`),
    INDEX `fk_DRIVER_HISTORY_DRIVER1_idx` (`DRIVER_ID` ASC) VISIBLE,
    CONSTRAINT `fk_DRIVER_HISTORY_DRIVER1`
        FOREIGN KEY (`DRIVER_ID`)
            REFERENCES `logisticdb`.`DRIVER` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
