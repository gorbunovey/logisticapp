INSERT INTO `logisticdb`.`city` (`NAME`) VALUES ('Moscow');
INSERT INTO `logisticdb`.`city` (`NAME`) VALUES ('Saint Petersburg');
INSERT INTO `logisticdb`.`city` (`NAME`) VALUES ('Novosibirsk');
INSERT INTO `logisticdb`.`city` (`NAME`) VALUES ('Yekaterinburg');
INSERT INTO `logisticdb`.`city` (`NAME`) VALUES ('Nizhny Novgorod');
INSERT INTO `logisticdb`.`city` (`NAME`) VALUES ('Kazan');
INSERT INTO `logisticdb`.`city` (`NAME`) VALUES ('Chelyabinsk');
INSERT INTO `logisticdb`.`city` (`NAME`) VALUES ('Omsk');
INSERT INTO `logisticdb`.`city` (`NAME`) VALUES ('Samara');
INSERT INTO `logisticdb`.`city` (`NAME`) VALUES ('Rostov-on-Don');


INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('AA00001', '2', '20000', '1', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('AA00002', '2', '20000', '1', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('AA00003', '2', '20000', '1', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('AA00004', '2', '20000', '0', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('AA00005', '2', '20000', '0', '1');



INSERT INTO `logisticdb`.`driver` (`ID`, `FIRST_NAME`, `LAST_NAME`, `PATRONYMIC_NAME`, `STATUS`, `WORK_HOURS`, `CITY_ID`, `TRUCK_REG_NUMBER`) VALUES ('101', 'Alexandr', 'Kolobkov', 'Ivanovich', 'free', '10', '1', 'AA00001');
INSERT INTO `logisticdb`.`driver` (`ID`, `FIRST_NAME`, `LAST_NAME`, `PATRONYMIC_NAME`, `STATUS`, `WORK_HOURS`, `CITY_ID`, `TRUCK_REG_NUMBER`) VALUES ('102', 'Sergey', 'Andreykov', 'Ivanovich', 'free', '20', '1', 'AA00001');
INSERT INTO `logisticdb`.`driver` (`ID`, `FIRST_NAME`, `LAST_NAME`, `PATRONYMIC_NAME`, `STATUS`, `WORK_HOURS`, `CITY_ID`, `TRUCK_REG_NUMBER`) VALUES ('103', 'Evgeniy', 'Smirnov', 'Ivanovich', 'busy', '30', '1', 'AA00002');
INSERT INTO `logisticdb`.`driver` (`ID`, `FIRST_NAME`, `LAST_NAME`, `PATRONYMIC_NAME`, `STATUS`, `WORK_HOURS`, `CITY_ID`, `TRUCK_REG_NUMBER`) VALUES ('104', 'Andrey', 'Goncharov', 'Ivanovich', 'busy', '40', '1', 'AA00002');
INSERT INTO `logisticdb`.`driver` (`ID`, `FIRST_NAME`, `LAST_NAME`, `PATRONYMIC_NAME`, `STATUS`, `WORK_HOURS`, `CITY_ID`, `TRUCK_REG_NUMBER`) VALUES ('105', 'Anton', 'Kolodyan', 'Ivanovich', 'free', '50', '1', 'AA00003');
