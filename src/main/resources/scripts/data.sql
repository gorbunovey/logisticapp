
-- -----------------------------------------------------
-- Table CITY
-- -----------------------------------------------------
-- SPb-Tver-Moscow-Voronezh-Rostov branch:
INSERT INTO `logisticdb`.`city` (`CODE`,`NAME`) VALUES ('40000000000', 'Saint Petersburg');
INSERT INTO `logisticdb`.`city` (`CODE`,`NAME`) VALUES ('28401000000', 'Tver');
INSERT INTO `logisticdb`.`city` (`CODE`,`NAME`) VALUES ('45000000000', 'Moscow');
INSERT INTO `logisticdb`.`city` (`CODE`,`NAME`) VALUES ('20401000000', 'Voronezh');
INSERT INTO `logisticdb`.`city` (`CODE`,`NAME`) VALUES ('60401000000', 'Rostov-on-Don');
-- Moscow-Kazan-Yekaterinburg-Novosibirsk branch:
-- INSERT INTO `logisticdb`.`city` (`CODE`,`NAME`) VALUES ('92401000000', 'Kazan');
-- INSERT INTO `logisticdb`.`city` (`CODE`,`NAME`) VALUES ('65401000000', 'Yekaterinburg');
-- INSERT INTO `logisticdb`.`city` (`CODE`,`NAME`) VALUES ('50401000000', 'Novosibirsk');
-- For further tests:
-- INSERT INTO `logisticdb`.`city` (`CODE`,`NAME`) VALUES ('75401000000', 'Chelyabinsk');
-- INSERT INTO `logisticdb`.`city` (`CODE`,`NAME`) VALUES ('52401000000', 'Omsk');
-- INSERT INTO `logisticdb`.`city` (`CODE`,`NAME`) VALUES ('36401000000', 'Samara');
-- INSERT INTO `logisticdb`.`city` (`CODE`,`NAME`) VALUES ('22401000000', 'Nizhny Novgorod');
-- INSERT INTO `logisticdb`.`city` (`CODE`,`NAME`) VALUES ('49401000000', 'Velikiy Novgorod');
-- INSERT INTO `logisticdb`.`city` (`CODE`,`NAME`) VALUES ('41215501000', 'Vyborg');
-- INSERT INTO `logisticdb`.`city` (`CODE`,`NAME`) VALUES ('41245501000', 'Tikhvin');
-- INSERT INTO `logisticdb`.`city` (`CODE`,`NAME`) VALUES ('41224501000', 'Kirishi');

-- -----------------------------------------------------
-- Table MAP
-- -----------------------------------------------------
-- To simplify map service presume:
-- * Map with roads - connected graph, so there is always a way between cities(vertexes)
-- * Modern search of a way - use of geoservices
-- * Just to simulate their work -> make a full-connected graph with a small amount cities(vertexes) and simple service
--    to get the distance. I see no reason to map real roads in DB and make a recursive joins in relation DB

-- Make a full connected graph for chain SPb-Tver-Moscow-Voronezh-Rostov: (km)
-- SPb
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('1', '2', '500');
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('1', '3', '670');
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('1', '4', '1170');
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('1', '5', '1670');
-- Tver
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('2', '1', '500');
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('2', '3', '170');
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('2', '4', '670');
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('2', '5', '1220');
-- Msk
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('3', '1', '670');
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('3', '2', '170');
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('3', '4', '500');
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('3', '5', '1050');
-- Voronezh
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('4', '1', '1170');
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('4', '2', '670');
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('4', '3', '500');
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('4', '5', '550');
-- Rostov
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('5', '1', '1670');
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('5', '2', '1220');
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('5', '3', '1050');
INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('5', '4', '550');


-- SPb-Tver-Moscow-Voronezh-Rostov: (m)
# INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('1', '2', '500000');
# INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('2', '1', '500000');
# INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('2', '3', '170000');
# INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('3', '2', '170000');
# INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('3', '4', '500000');
# INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('4', '3', '500000');
# INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('4', '5', '550000');
# INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('5', '4', '550000');
-- Moscow-Kazan-Yekaterinburg-Novosibirsk: (m)
-- INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('3', '6', '750000');
-- INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('6', '3', '750000');
-- INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('6', '7', '700000');
-- INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('7', '6', '700000');
-- INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('7', '8', '1500000');
-- INSERT INTO `logisticdb`.`map` (`DEPARTURE_POINT`,`DESTINATION_POINT`,`DISTANCE`) VALUES ('8', '7', '1500000');

-- -----------------------------------------------------
-- Table TRUCK
-- -----------------------------------------------------

INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('AA25001', '2', '25', '1', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('AA25002', '2', '25', '1', '3');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('AA25003', '2', '25', '1', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('AA25004', '2', '25', '1', '3');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('AA25005', '2', '25', '0', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('BB20001', '2', '20', '1', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('BB20002', '2', '20', '1', '2');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('BB20003', '2', '20', '1', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('BB20004', '2', '20', '1', '2');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('BB20005', '2', '20', '0', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('CC15001', '2', '15', '1', '3');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('CC15002', '2', '15', '1', '3');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('CC15003', '1', '15', '1', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('CC15004', '1', '15', '1', '3');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('CC15005', '2', '15', '0', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('DD10001', '2', '10', '1', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('DD10002', '1', '10', '1', '3');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('DD10003', '2', '10', '1', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('DD10004', '1', '10', '1', '3');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('DD10005', '2', '10', '0', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('EE05001', '1', '5', '1', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('EE05002', '1', '5', '1', '3');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('EE05003', '1', '5', '1', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('EE05004', '1', '5', '1', '3');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('EE05005', '1', '5', '0', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('FF03001', '1', '5', '0', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('GG01501', '1', '1.5', '1', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('GG01502', '1', '1.5', '1', '2');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('GG01503', '1', '1.5', '1', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('GG01504', '1', '1.5', '1', '2');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('GG01505', '1', '1.5', '0', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('HH00901', '1', '0.9', '1', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('HH00902', '1', '0.9', '1', '2');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('HH00903', '1', '0.9', '1', '1');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('HH00904', '1', '0.9', '1', '2');
INSERT INTO `logisticdb`.`truck` (`REG_NUMBER`, `CREW`, `CAPACITY`, `STATE`, `CITY_ID`) VALUES ('HH00905', '1', '0.9', '0', '1');

-- -----------------------------------------------------
-- Table CARGO
-- -----------------------------------------------------
-- STATUS {prepared, loaded, delivered}
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('1', 'Apple', '2000', 'prepared', '1','3');
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('2', 'Banana', '500', 'prepared', '1','3');
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('3', 'Watermelon', '10000', 'prepared', '1','3');
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('4', 'Pear', '500', 'prepared', '1','2');
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('5', 'Lemon', '1000', 'prepared', '1','2');
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('6', 'Potato', '20000', 'prepared', '1','3');
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('7', 'Carrot', '10000', 'prepared', '1','3');
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('8', 'Tomato', '5000', 'prepared', '1','3');
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('9', 'Cucumber', '1000', 'prepared', '1','2');
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('10', 'Pepper', '1000', 'prepared', '3','4');
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('11', 'Garlic', '500', 'prepared', '3','4');
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('12', 'Pumpkin', '2500', 'prepared', '3','4');
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('13', 'Onion', '2500', 'prepared', '3','1');
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('14', 'Potato', '20000', 'prepared', '3','1');
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('15', 'Carrot', '10000', 'prepared', '3','1');
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('16', 'Tomato', '5000', 'prepared', '3','1');
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('17', 'Mushroom', '1500', 'prepared', '3','1');
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('18', 'Corn', '3000', 'prepared', '3','1');
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('19', 'Lettuce', '500', 'prepared', '3','1');
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('20', 'Cabbage', '8000', 'prepared', '3','1');
-- loaded
INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('21', 'Onion', '5000', 'loaded', '3','1');
-- delivered
-- INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('22', 'Potato', '10000', 'delivered', '1','3');
-- INSERT INTO `logisticdb`.`cargo` (`NUMBER`, `TITLE`, `WEIGHT`, `STATUS`, `CITY_FROM`, `CITY_TO`) VALUES ('23', 'Cabbage', '5000', 'delivered', '1','2');

-- -----------------------------------------------------
-- Table ORDERS
-- -----------------------------------------------------
-- closed
-- INSERT INTO `logisticdb`.`orders` (`STATUS`, `TRUCK_ID`) VALUES ( '0', '11');
-- open
INSERT INTO `logisticdb`.`orders` (`STATUS`, `TRUCK_ID`) VALUES ( '1', '22');

-- -----------------------------------------------------
-- Table WAY_POINT
-- -----------------------------------------------------
-- closed
-- INSERT INTO `logisticdb`.`way_point` (`SEQ_NUMBER`, `TYPE`, `CARGO_ID`, `ORDER_ID`) VALUES ('1', '1','21','2');
-- INSERT INTO `logisticdb`.`way_point` (`SEQ_NUMBER`, `TYPE`, `CARGO_ID`, `ORDER_ID`) VALUES ('2', '1','22','2');
-- INSERT INTO `logisticdb`.`way_point` (`SEQ_NUMBER`, `TYPE`, `CARGO_ID`, `ORDER_ID`) VALUES ('3', '0','22','2');
-- INSERT INTO `logisticdb`.`way_point` (`SEQ_NUMBER`, `TYPE`, `CARGO_ID`, `ORDER_ID`) VALUES ('4', '0','21','2');
-- open
INSERT INTO `logisticdb`.`way_point` (`SEQ_NUMBER`, `TYPE`, `CARGO_ID`, `ORDER_ID`) VALUES ('1', '1','21','1');
INSERT INTO `logisticdb`.`way_point` (`SEQ_NUMBER`, `TYPE`, `CARGO_ID`, `ORDER_ID`) VALUES ('2', '0','21','1');


-- -----------------------------------------------------
-- Table ROLE
-- -----------------------------------------------------

INSERT INTO `logisticdb`.`role` (`NAME`) VALUES ('ROLE_DRIVER');
INSERT INTO `logisticdb`.`role` (`NAME`) VALUES ('ROLE_STUFF');
INSERT INTO `logisticdb`.`role` (`NAME`) VALUES ('ROLE_ADMIN');

-- -----------------------------------------------------
-- Table USER
-- -----------------------------------------------------

INSERT INTO `logisticdb`.`user` (`FIRST_NAME`, `LAST_NAME`,`BIRTHDAY`,`EMAIL`,`PASSWORD`,`ROLE_ID`) VALUES ('Alexandr', 'Kolobkov','1972-05-11','Kolobkov@gmail.com','$2a$10$4KMpqcw9zlSCAjHhvK/ekeIPJ9mJ5y6D.92/E/Yvu6g428NHYuh9u','1');
INSERT INTO `logisticdb`.`user` (`FIRST_NAME`, `LAST_NAME`,`BIRTHDAY`,`EMAIL`,`PASSWORD`,`ROLE_ID`) VALUES ('Sergey', 'Andreykov','1977-02-15','Andreykov@gmail.com','$2a$10$4KMpqcw9zlSCAjHhvK/ekeIPJ9mJ5y6D.92/E/Yvu6g428NHYuh9u','1');
INSERT INTO `logisticdb`.`user` (`FIRST_NAME`, `LAST_NAME`,`BIRTHDAY`,`EMAIL`,`PASSWORD`,`ROLE_ID`) VALUES ('Ivan', 'Smirnov','1985-08-17','Smirnov@gmail.com','$2a$10$4KMpqcw9zlSCAjHhvK/ekeIPJ9mJ5y6D.92/E/Yvu6g428NHYuh9u','1');
INSERT INTO `logisticdb`.`user` (`FIRST_NAME`, `LAST_NAME`,`BIRTHDAY`,`EMAIL`,`PASSWORD`,`ROLE_ID`) VALUES ('Andrey', 'Goncharov','1973-07-13','Goncharov@gmail.com','$2a$10$4KMpqcw9zlSCAjHhvK/ekeIPJ9mJ5y6D.92/E/Yvu6g428NHYuh9u','1');
INSERT INTO `logisticdb`.`user` (`FIRST_NAME`, `LAST_NAME`,`BIRTHDAY`,`EMAIL`,`PASSWORD`,`ROLE_ID`) VALUES ('Anton', 'Kolodyan','1965-01-21','Kolodyan@gmail.com','$2a$10$4KMpqcw9zlSCAjHhvK/ekeIPJ9mJ5y6D.92/E/Yvu6g428NHYuh9u','1');
INSERT INTO `logisticdb`.`user` (`FIRST_NAME`, `LAST_NAME`,`BIRTHDAY`,`EMAIL`,`PASSWORD`,`ROLE_ID`) VALUES ('Dmitry', 'Prigozhin','1987-09-26','Prigozhin@gmail.com','$2a$10$4KMpqcw9zlSCAjHhvK/ekeIPJ9mJ5y6D.92/E/Yvu6g428NHYuh9u','1');
INSERT INTO `logisticdb`.`user` (`FIRST_NAME`, `LAST_NAME`,`BIRTHDAY`,`EMAIL`,`PASSWORD`,`ROLE_ID`) VALUES ('Maksim', 'Skolkin','1990-10-24','Skolkin@gmail.com','$2a$10$4KMpqcw9zlSCAjHhvK/ekeIPJ9mJ5y6D.92/E/Yvu6g428NHYuh9u','1');
INSERT INTO `logisticdb`.`user` (`FIRST_NAME`, `LAST_NAME`,`BIRTHDAY`,`EMAIL`,`PASSWORD`,`ROLE_ID`) VALUES ('Vasiliy', 'Ampilogov','1982-02-04','Ampilogov@gmail.com','$2a$10$4KMpqcw9zlSCAjHhvK/ekeIPJ9mJ5y6D.92/E/Yvu6g428NHYuh9u','1');
INSERT INTO `logisticdb`.`user` (`FIRST_NAME`, `LAST_NAME`,`BIRTHDAY`,`EMAIL`,`PASSWORD`,`ROLE_ID`) VALUES ('Vyacheslav', 'Ivanov','1984-04-28','Ivanov@gmail.com','$2a$10$4KMpqcw9zlSCAjHhvK/ekeIPJ9mJ5y6D.92/E/Yvu6g428NHYuh9u','1');
INSERT INTO `logisticdb`.`user` (`FIRST_NAME`, `LAST_NAME`,`BIRTHDAY`,`EMAIL`,`PASSWORD`,`ROLE_ID`) VALUES ('Klim', 'Panov','1989-03-01','Panov@gmail.com','123456','1');
INSERT INTO `logisticdb`.`user` (`FIRST_NAME`, `LAST_NAME`,`BIRTHDAY`,`EMAIL`,`PASSWORD`,`ROLE_ID`) VALUES ('Andrey', 'Varvanovich','1975-01-26','Varvanovich@gmail.com','$2a$10$4KMpqcw9zlSCAjHhvK/ekeIPJ9mJ5y6D.92/E/Yvu6g428NHYuh9u','2');
INSERT INTO `logisticdb`.`user` (`FIRST_NAME`, `LAST_NAME`,`BIRTHDAY`,`EMAIL`,`PASSWORD`,`ROLE_ID`) VALUES ('Vladimir', 'Kaminskiy','1976-04-19','Kaminskiy@gmail.com','$2a$10$4KMpqcw9zlSCAjHhvK/ekeIPJ9mJ5y6D.92/E/Yvu6g428NHYuh9u','2');
INSERT INTO `logisticdb`.`user` (`FIRST_NAME`, `LAST_NAME`,`BIRTHDAY`,`EMAIL`,`PASSWORD`,`ROLE_ID`) VALUES ('Vladimir', 'Dagov','1972-11-08','Dagov@gmail.com','123456','2');
INSERT INTO `logisticdb`.`user` (`FIRST_NAME`, `LAST_NAME`,`BIRTHDAY`,`EMAIL`,`PASSWORD`,`ROLE_ID`) VALUES ('Aleksey', 'Stebkov','1969-12-04','Stebkov@gmail.com','$2a$10$4KMpqcw9zlSCAjHhvK/ekeIPJ9mJ5y6D.92/E/Yvu6g428NHYuh9u','2');
INSERT INTO `logisticdb`.`user` (`FIRST_NAME`, `LAST_NAME`,`BIRTHDAY`,`EMAIL`,`PASSWORD`,`ROLE_ID`) VALUES ('Dmitriy', 'Zhukov','1981-01-25','Zhukov@gmail.com','$2a$10$4KMpqcw9zlSCAjHhvK/ekeIPJ9mJ5y6D.92/E/Yvu6g428NHYuh9u','3');

-- -----------------------------------------------------
-- Table DRIVER
-- -----------------------------------------------------

INSERT INTO `logisticdb`.`driver` (`NUMBER`,`CITY_ID`,`USER_ID`,`STATUS`,`HOURS`,`SHIFT`,`SHIFT_TIME`) VALUES ('101', '1', '1', 'not-on-shift', '0', '0', NOW());
INSERT INTO `logisticdb`.`driver` (`NUMBER`,`CITY_ID`,`USER_ID`,`STATUS`,`HOURS`,`SHIFT`,`SHIFT_TIME`) VALUES ('102', '1', '2', 'not-on-shift', '0', '0', NOW());
INSERT INTO `logisticdb`.`driver` (`NUMBER`,`CITY_ID`,`USER_ID`,`STATUS`,`HOURS`,`SHIFT`,`SHIFT_TIME`) VALUES ('103', '1', '3', 'not-on-shift', '0', '0', NOW());
INSERT INTO `logisticdb`.`driver` (`NUMBER`,`CITY_ID`,`USER_ID`,`STATUS`,`HOURS`,`SHIFT`,`SHIFT_TIME`) VALUES ('104', '1', '4', 'not-on-shift', '0', '0', NOW());
INSERT INTO `logisticdb`.`driver` (`NUMBER`,`CITY_ID`,`USER_ID`,`STATUS`,`HOURS`,`SHIFT`,`SHIFT_TIME`) VALUES ('105', '1', '5', 'not-on-shift', '0', '0', NOW());
INSERT INTO `logisticdb`.`driver` (`NUMBER`,`CITY_ID`,`USER_ID`,`STATUS`,`HOURS`,`SHIFT`,`SHIFT_TIME`) VALUES ('106', '3', '6', 'not-on-shift', '0', '0', NOW());
INSERT INTO `logisticdb`.`driver` (`NUMBER`,`CITY_ID`,`USER_ID`,`STATUS`,`HOURS`,`SHIFT`,`SHIFT_TIME`) VALUES ('107', '3', '7', 'not-on-shift', '0', '0', NOW());
INSERT INTO `logisticdb`.`driver` (`NUMBER`,`CITY_ID`,`USER_ID`,`STATUS`,`HOURS`,`SHIFT`,`SHIFT_TIME`) VALUES ('108', '3', '8', 'not-on-shift', '0', '0', NOW());
INSERT INTO `logisticdb`.`driver` (`NUMBER`,`CITY_ID`,`USER_ID`,`STATUS`,`HOURS`,`SHIFT`,`SHIFT_TIME`) VALUES ('109', '3', '9', 'not-on-shift', '0', '0', NOW());
INSERT INTO `logisticdb`.`driver` (`NUMBER`,`CITY_ID`,`USER_ID`,`STATUS`,`HOURS`,`SHIFT`,`SHIFT_TIME`) VALUES ('110', '3', '10', 'first', '0', '1', NOW() - INTERVAL 8 HOUR);

-- -----------------------------------------------------
-- Table ORDER_DRIVERS
-- -----------------------------------------------------
-- closed
-- open
INSERT INTO `logisticdb`.`order_drivers` (`ORDER_ID`,`DRIVER_ID`) VALUES ('1','10');