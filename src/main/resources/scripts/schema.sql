-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema logisticdb
-- -----------------------------------------------------
-- DROP DATABASE IF EXISTS logisticdb;
-- CREATE DATABASE IF NOT EXISTS logisticdb;
USE logisticdb;
-- -----------------------------------------------------
-- Table `CITY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CITY` ;

CREATE TABLE IF NOT EXISTS `CITY` (
                                      `S_ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                      `NAME` VARCHAR(45) NOT NULL,
                                      PRIMARY KEY (`S_ID`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TRUCK`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `TRUCK` ;

CREATE TABLE IF NOT EXISTS `TRUCK` (
                                       `REG_NUMBER` CHAR(7) NOT NULL,
                                       `CREW` INT NOT NULL DEFAULT 2,
                                       `CAPACITY` INT UNSIGNED NOT NULL DEFAULT 20000,
                                       `STATE` TINYINT NOT NULL DEFAULT 0,
                                       `CITY_ID` BIGINT UNSIGNED NULL,
                                       PRIMARY KEY (`REG_NUMBER`),
                                       INDEX `fk_TRUCK_CITY1_idx` (`CITY_ID` ASC) VISIBLE,
                                       CONSTRAINT `fk_TRUCK_CITY1`
                                           FOREIGN KEY (`CITY_ID`)
                                               REFERENCES `CITY` (`S_ID`)
                                               ON DELETE NO ACTION
                                               ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DRIVER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DRIVER` ;

CREATE TABLE IF NOT EXISTS `DRIVER` (
                                        `ID` INT NOT NULL,
                                        `FIRST_NAME` VARCHAR(45) NOT NULL,
                                        `LAST_NAME` VARCHAR(45) NOT NULL,
                                        `PATRONYMIC_NAME` VARCHAR(45) NULL,
                                        `STATUS` VARCHAR(20) NOT NULL,
                                        `WORK_HOURS` INT ZEROFILL NULL,
                                        `CITY_ID` BIGINT UNSIGNED NULL,
                                        `TRUCK_REG_NUMBER` CHAR(7) NULL,
                                        PRIMARY KEY (`ID`),
                                        INDEX `fk_DRIVER_TRUCK_idx` (`TRUCK_REG_NUMBER` ASC) VISIBLE,
                                        INDEX `fk_DRIVER_CITY1_idx` (`CITY_ID` ASC) VISIBLE,
                                        CONSTRAINT `fk_DRIVER_TRUCK`
                                            FOREIGN KEY (`TRUCK_REG_NUMBER`)
                                                REFERENCES `TRUCK` (`REG_NUMBER`)
                                                ON DELETE NO ACTION
                                                ON UPDATE NO ACTION,
                                        CONSTRAINT `fk_DRIVER_CITY1`
                                            FOREIGN KEY (`CITY_ID`)
                                                REFERENCES `CITY` (`S_ID`)
                                                ON DELETE NO ACTION
                                                ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CARGO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CARGO` ;

CREATE TABLE IF NOT EXISTS `CARGO` (
                                       `UID` BIGINT UNSIGNED NOT NULL,
                                       `TITLE` VARCHAR(45) NOT NULL,
                                       `WEIGHT` DECIMAL(6,3) NOT NULL,
                                       `STATUS` VARCHAR(20) NOT NULL,
                                       PRIMARY KEY (`UID`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MAP`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MAP` ;

CREATE TABLE IF NOT EXISTS `MAP` (
                                     `S_ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                     `DEPARTURE_POINT` BIGINT UNSIGNED NOT NULL,
                                     `DESTINATION_POINT` BIGINT UNSIGNED NOT NULL,
                                     PRIMARY KEY (`S_ID`),
                                     INDEX `fk_MAP_CITY1_idx` (`DEPARTURE_POINT` ASC) VISIBLE,
                                     INDEX `fk_MAP_CITY2_idx` (`DESTINATION_POINT` ASC) VISIBLE,
                                     CONSTRAINT `fk_MAP_CITY1`
                                         FOREIGN KEY (`DEPARTURE_POINT`)
                                             REFERENCES `CITY` (`S_ID`)
                                             ON DELETE NO ACTION
                                             ON UPDATE NO ACTION,
                                     CONSTRAINT `fk_MAP_CITY2`
                                         FOREIGN KEY (`DESTINATION_POINT`)
                                             REFERENCES `CITY` (`S_ID`)
                                             ON DELETE NO ACTION
                                             ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ORDER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ORDER` ;

CREATE TABLE IF NOT EXISTS `ORDER` (
                                       `UID` BIGINT UNSIGNED NOT NULL,
                                       `STATUS` TINYINT NOT NULL DEFAULT 0,
                                       `TRUCK_REG_NUBMER` CHAR(7) NULL,
                                       PRIMARY KEY (`UID`),
                                       INDEX `fk_ORDER_TRUCK1_idx` (`TRUCK_REG_NUBMER` ASC) VISIBLE,
                                       CONSTRAINT `fk_ORDER_TRUCK1`
                                           FOREIGN KEY (`TRUCK_REG_NUBMER`)
                                               REFERENCES `TRUCK` (`REG_NUMBER`)
                                               ON DELETE NO ACTION
                                               ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ORDER_WAY_POINTS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ORDER_WAY_POINTS` ;

CREATE TABLE IF NOT EXISTS `ORDER_WAY_POINTS` (
                                                  `S_UID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                  `ORDER_UID` BIGINT UNSIGNED NOT NULL,
                                                  `CITY_UID` BIGINT UNSIGNED NOT NULL,
                                                  `CARGO_UID` BIGINT UNSIGNED NOT NULL,
                                                  `TYPE` TINYINT NOT NULL,
                                                  PRIMARY KEY (`S_UID`),
                                                  INDEX `fk_ORDER_WAY_POINTS_ORDER1_idx` (`ORDER_UID` ASC) VISIBLE,
                                                  INDEX `fk_ORDER_WAY_POINTS_CITY1_idx` (`CITY_UID` ASC) VISIBLE,
                                                  INDEX `fk_ORDER_WAY_POINTS_CARGO1_idx` (`CARGO_UID` ASC) VISIBLE,
                                                  CONSTRAINT `fk_ORDER_WAY_POINTS_ORDER1`
                                                      FOREIGN KEY (`ORDER_UID`)
                                                          REFERENCES `ORDER` (`UID`)
                                                          ON DELETE NO ACTION
                                                          ON UPDATE NO ACTION,
                                                  CONSTRAINT `fk_ORDER_WAY_POINTS_CITY1`
                                                      FOREIGN KEY (`CITY_UID`)
                                                          REFERENCES `CITY` (`S_ID`)
                                                          ON DELETE NO ACTION
                                                          ON UPDATE NO ACTION,
                                                  CONSTRAINT `fk_ORDER_WAY_POINTS_CARGO1`
                                                      FOREIGN KEY (`CARGO_UID`)
                                                          REFERENCES `CARGO` (`UID`)
                                                          ON DELETE NO ACTION
                                                          ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ORDER_DRIVERS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ORDER_DRIVERS` ;

CREATE TABLE IF NOT EXISTS `ORDER_DRIVERS` (
                                               `S_UID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                               `ORDER_UID` BIGINT UNSIGNED NOT NULL,
                                               `DRIVER_ID` INT NOT NULL,
                                               PRIMARY KEY (`S_UID`),
                                               INDEX `fk_ORDER_DRIVERS_ORDER1_idx` (`ORDER_UID` ASC) VISIBLE,
                                               INDEX `fk_ORDER_DRIVERS_DRIVER1_idx` (`DRIVER_ID` ASC) VISIBLE,
                                               CONSTRAINT `fk_ORDER_DRIVERS_ORDER1`
                                                   FOREIGN KEY (`ORDER_UID`)
                                                       REFERENCES `ORDER` (`UID`)
                                                       ON DELETE NO ACTION
                                                       ON UPDATE NO ACTION,
                                               CONSTRAINT `fk_ORDER_DRIVERS_DRIVER1`
                                                   FOREIGN KEY (`DRIVER_ID`)
                                                       REFERENCES `DRIVER` (`ID`)
                                                       ON DELETE NO ACTION
                                                       ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
