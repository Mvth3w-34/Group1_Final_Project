DROP DATABASE IF EXISTS transit;

CREATE DATABASE transit;

USE transit;

-- DROP TABLE IF EXISTS VEHICLES;
-- DROP TABLE IF EXISTS OPERATORS;
-- DROP TABLE IF EXISTS CREDENTIALS;
-- DROP TABLE IF EXISTS OPERATOR_TIMESTAMP;
-- DROP TABLE MAINTENANCE_REQUEST IF EXISTS;
-- DROP TABLE VEHICLE_COMPONENT IF EXISTS;
-- DROP TABLE COMPONENT IF EXISTS;
-- DROP TABLE FUEL_CONSUMPTION_ALERTS IF EXISTS;
-- DROP TABLE OPERATOR_BUS_STOP IF EXISTS;

CREATE TABLE VEHICLES
(
    VEHICLE_ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    VEHICLE_TYPE varchar(21) NOT NULL,
    VEHICLE_NUMBER varchar(50) NOT NULL,
    FUEL_TYPE varchar(50) NOT NULL,
    FUEL_CONSUMPTION_RATE float NOT NULL,
    MAXIMUM_PASSENGERS int NOT NULL,
    CURRENT_ROUTE varchar(50)
);

INSERT INTO VEHICLES (VEHICLE_TYPE, VEHICLE_NUMBER, FUEL_TYPE, FUEL_CONSUMPTION_RATE, MAXIMUM_PASSENGERS, CURRENT_ROUTE)
    VALUES ('DIESEL_BUS', 'D105', 'Diesel', '18.5', '55', null);
INSERT INTO VEHICLES (VEHICLE_TYPE, VEHICLE_NUMBER, FUEL_TYPE, FUEL_CONSUMPTION_RATE, MAXIMUM_PASSENGERS, CURRENT_ROUTE)
    VALUES ('ELECTRIC_LIGHT_RAIL', 'T1500', 'Electric', '18.5', '55', null);
INSERT INTO VEHICLES (VEHICLE_TYPE, VEHICLE_NUMBER, FUEL_TYPE, FUEL_CONSUMPTION_RATE, MAXIMUM_PASSENGERS, CURRENT_ROUTE)
    VALUES ('DIESEL_ELECTRIC_TRAIN', 'T1800', 'Diesel-Electric', '18.5', '55', null);

CREATE TABLE CREDENTIALS
(
    CREDENTIAL_ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    USERNAME varchar(50) NOT NULL,
    PASSWORD varchar(50) NOT NULL
);

INSERT INTO CREDENTIALS (USERNAME, PASSWORD) VALUES ('user1', 'pass1');
INSERT INTO CREDENTIALS (USERNAME, PASSWORD) VALUES ('user2', 'pass2');
INSERT INTO CREDENTIALS (USERNAME, PASSWORD) VALUES ('user3', 'pass3');
INSERT INTO CREDENTIALS (USERNAME, PASSWORD) VALUES ('user4', 'pass4');
INSERT INTO CREDENTIALS (USERNAME, PASSWORD) VALUES ('user5', 'pass5');
INSERT INTO CREDENTIALS (USERNAME, PASSWORD) VALUES ('user6', 'pass6');
INSERT INTO CREDENTIALS (USERNAME, PASSWORD) VALUES ('user7', 'pass7');
INSERT INTO CREDENTIALS (USERNAME, PASSWORD) VALUES ('user8', 'pass8');
INSERT INTO CREDENTIALS (USERNAME, PASSWORD) VALUES ('user9', 'pass9');
INSERT INTO CREDENTIALS (USERNAME, PASSWORD) VALUES ('user10', 'pass10');

CREATE TABLE OPERATORS
(
    OPERATOR_ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    OPERATOR_NAME varchar(50),
    CREDENTIAL_ID int NOT NULL,
    OPERATOR_USER varchar(9) NOT NULL,
    EMAIL varchar(50),
    FOREIGN KEY (CREDENTIAL_ID) REFERENCES CREDENTIALS(CREDENTIAL_ID)
);

INSERT INTO OPERATORS (OPERATOR_NAME, CREDENTIAL_ID, OPERATOR_USER, EMAIL) VALUES ('Bob Smith', 1, 'MANAGER', '');
INSERT INTO OPERATORS (OPERATOR_NAME, CREDENTIAL_ID, OPERATOR_USER, EMAIL) VALUES ('Drew Anderson', 2, 'OPERATOR', '');
INSERT INTO OPERATORS (OPERATOR_NAME, CREDENTIAL_ID, OPERATOR_USER, EMAIL) VALUES ('Danny Dan', 3, 'OPERATOR', 'hisEMAIL@newEMAIL.com');
INSERT INTO OPERATORS (OPERATOR_NAME, CREDENTIAL_ID, OPERATOR_USER, EMAIL) VALUES ('Joe Clarke', 4, 'OPERATOR', 'joeclarke@clarke.com');
INSERT INTO OPERATORS (OPERATOR_NAME, CREDENTIAL_ID, OPERATOR_USER, EMAIL) VALUES ('Kate Amberly', 5, 'MANAGER', 'testEMAIL@EMAIL.com');

--Written by Mathew Chebet
CREATE TABLE COMPONENT (
    COMPONENT_ID INT PRIMARY KEY AUTO_INCREMENT,
    COMPONENT_NAME VARCHAR(25) NOT NULL 
);

--Written by Mathew Chebet
CREATE TABLE FUEL_CONSUMPTION_ALERT (
    ALERT_ID INT PRIMARY KEY AUTO_INCREMENT,
    DATE_OCCURED DATE NOT NULL,
    VEHICLE_ID INT NOT NULL,
    FOREIGN KEY(VEHICLE_ID) REFERENCES VEHICLES(VEHICLE_ID)
);

--Written by Mathew Chebet
CREATE TABLE OPERATOR_TIMESTAMP (
    TIMESTAMP_ID INT PRIMARY KEY AUTO_INCREMENT,
    PUNCH_TIME_START TIMESTAMP NOT NULL,
    PUNCH_TIME_STOP TIMESTAMP NOT NULL,
    PUNCH_TYPE VARCHAR(25) NOT NULL,
    OPERATOR_ID INT NOT NULL,
    FOREIGN KEY(OPERATOR_ID) REFERENCES OPERATORS(OPERATOR_ID)   
);

--Written by Mathew Chebet
-- CREATE TABLE MAINTENANCE_REQUEST (
--     REQUEST_ID INT PRIMARY KEY AUTO_INCREMENT,
--     REQUEST_DATE DATE NOT NULL,
--     QUOTED_COST DECIMAL(4,2) NOT NULL,
--     OPERATOR_ID INT NOT NULL,
--     VEHICLE_COMPONENT_ID INT NOT NULL,
--     SERVICE_DESCRIPTION VARCHAR(50) NOT NULL,
--     IS_COMPLETED VARCHAR(3) NOT NULL,
--     FOREIGN KEY(OPERATOR_ID) REFERENCES OPERATORS(OPERATOR_ID),
--     FOREIGN KEY(VEHICLE_COMPONENT_ID) REFERENCES VEHICLE_COMPONENT(VEHICLE_COMPONENT_ID)
-- );

--Written by Mathew Chebet
CREATE TABLE VEHICLE_COMPONENT (
    VEHICLE_COMPONENT_ID INT PRIMARY KEY AUTO_INCREMENT,
    HOURS_USED DECIMAL(4,2) DEFAULT 0,
    VEHICLE_ID INT NOT NULL, 
    COMPONENT_ID INT NOT NULL,
    FOREIGN KEY (VEHICLE_ID) REFERENCES VEHICLES(VEHICLE_ID)
);

--Written by Mathew Chebet
-- CREATE TABLE OPERATOR_BUS_STOP (
--     OPERATOR_BUS_STOP_ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
--     ARRIVAL_TIME TIMESTAMP NOT NULL,
--     EXPECTED_ARRIVAL_TIME TIMESTAMP NOT NULL,
--     OPERATOR_ID INT NOT NULL,
--     VEHICLE_ID INT NOT NULL,
--     LOCATION_ID INT NOT NULL,
--     FOREIGN KEY(OPERATOR_ID) REFERENCES OPERATORS(OPERATOR_ID),
--     FOREIGN KEY(VEHICLE_ID) REFERENCES VEHICLES(VEHICLE_ID),
--     FOREIGN KEY(LOCATION_ID) REFERENCES LOCATION(LOCATION_ID)
-- );

--Written by Mathew Chebet
--Component Table data
-- INSERT INTO COMPONENT(COMPONENT_NAME) VALUES ('REAR BRAKES');
-- INSERT INTO COMPONENT(COMPONENT_NAME) VALUES ('FRONT BRAKES');
-- INSERT INTO COMPONENT(COMPONENT_NAME) VALUES ('LEFT REAR TIRE');
-- INSERT INTO COMPONENT(COMPONENT_NAME) VALUES ('LEFT FRONT TIRE');
-- INSERT INTO COMPONENT(COMPONENT_NAME) VALUES ('RIGHT FRONT TIRE');
-- INSERT INTO COMPONENT(COMPONENT_NAME) VALUES ('RIGHT REAR TIRE');
-- INSERT INTO COMPONENT(COMPONENT_NAME) VALUES ('REAR AXLE BEARINGS');
-- INSERT INTO COMPONENT(COMPONENT_NAME) VALUES ('FRONT AXLE BEARINGS');
-- INSERT INTO COMPONENT(COMPONENT_NAME) VALUES ('CATERNARY');
-- INSERT INTO COMPONENT(COMPONENT_NAME) VALUES ('CIRCUIT BREAKER');
-- INSERT INTO COMPONENT(COMPONENT_NAME) VALUES ('PANTOGRAPH');
-- INSERT INTO COMPONENT(COMPONENT_NAME) VALUES ('DIESEL ENGINE');
-- INSERT INTO COMPONENT(COMPONENT_NAME) VALUES ('ELECTRIC ENGINE');


--Written by Mathew Chebet
--Vehicle Component Table Data
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (600, 1, 1);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (600, 1, 2);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (2000, 1, 3);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (2000, 1, 4);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (2000, 1, 5);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (2000, 1, 6);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (300, 1, 7);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (300, 1, 8);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (250, 1, 11);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (700, 2, 1);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (700, 2, 2);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (250, 2, 9);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (300, 2, 10);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (250, 2, 11);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (500, 2, 13);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (700, 3, 1);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (700, 3, 2);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (250, 3, 9);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (300, 3, 10);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (250, 3, 11);
-- INSERT INTO VEHICLE_COMPONENT(HOURS_USED, VEHICLE_ID, COMPONENT_ID) VALUES (500, 3, 12);

--Written by Mathew Chebet
--Maintenance Request Table Data
--INSERT INTO MAINTENANCE_REQUEST (REQUEST_DATE, QUOTED_COST, OPERATOR_ID, VEHICLE_COMPONENT_ID, SERVICE_DESCRIPTION, IS_COMPLETED) VALUES ('2025-12-03', 300.50, 1, 1, 'BRAKE REPLACEMENT', 'NO');
--INSERT INTO MAINTENANCE_REQUEST (REQUEST_DATE, QUOTED_COST, OPERATOR_ID, VEHICLE_COMPONENT_ID, SERVICE_DESCRIPTION, IS_COMPLETED) VALUES ('2025-12-05', 300.50, 1, 2, 'BRAKE REPLACEMENT', 'NO');
--INSERT INTO MAINTENANCE_REQUEST (REQUEST_DATE, QUOTED_COST, OPERATOR_ID, VEHICLE_COMPONENT_ID, SERVICE_DESCRIPTION, IS_COMPLETED) VALUES ('2023-10-03', 600, 5, 13, 'CIRCUIT BREAKER REPLACEMENT', 'YES');
--INSERT INTO MAINTENANCE_REQUEST (REQUEST_DATE, QUOTED_COST, OPERATOR_ID, VEHICLE_COMPONENT_ID, SERVICE_DESCRIPTION, IS_COMPLETED) VALUES ('2022-12-03', 300.50, 1, 10, 'BRAKE REPLACEMENT', 'YES');
--INSERT INTO MAINTENANCE_REQUEST (REQUEST_DATE, QUOTED_COST, OPERATOR_ID, VEHICLE_COMPONENT_ID, SERVICE_DESCRIPTION, IS_COMPLETED) VALUES ('2022-12-04', 300.50, 1, 11, 'BRAKE REPLACEMENT', 'YES');
--INSERT INTO MAINTENANCE_REQUEST (REQUEST_DATE, QUOTED_COST, OPERATOR_ID, VEHICLE_COMPONENT_ID, SERVICE_DESCRIPTION, IS_COMPLETED) VALUES ('2022-09-03', 1500.50, 5, 21, 'ENGINE REPLACEMENT', 'YES');
--INSERT INTO MAINTENANCE_REQUEST (REQUEST_DATE, QUOTED_COST, OPERATOR_ID, VEHICLE_COMPONENT_ID, SERVICE_DESCRIPTION, IS_COMPLETED) VALUES ('2022-07-03', 300.50, 5, 7, 'AXLE BEARING REPLACEMENT', 'YES');
--INSERT INTO MAINTENANCE_REQUEST (REQUEST_DATE, QUOTED_COST, OPERATOR_ID, VEHICLE_COMPONENT_ID, SERVICE_DESCRIPTION, IS_COMPLETED) VALUES ('2021-12-05', 300.50, 5, 8, 'AXLE BEARING REPLACEMENT', 'YES');
--INSERT INTO MAINTENANCE_REQUEST (REQUEST_DATE, QUOTED_COST, OPERATOR_ID, VEHICLE_COMPONENT_ID, SERVICE_DESCRIPTION, IS_COMPLETED) VALUES ('2021-11-06', 105.50, 5, 3, 'TIRE REPLACEMENT', 'YES');
--INSERT INTO MAINTENANCE_REQUEST (REQUEST_DATE, QUOTED_COST, OPERATOR_ID, VEHICLE_COMPONENT_ID, SERVICE_DESCRIPTION, IS_COMPLETED) VALUES ('2021-10-15', 105.50, 5, 4, 'TIRE REPLACEMENT', 'YES');
--INSERT INTO MAINTENANCE_REQUEST (REQUEST_DATE, QUOTED_COST, OPERATOR_ID, VEHICLE_COMPONENT_ID, SERVICE_DESCRIPTION, IS_COMPLETED) VALUES ('2021-12-30', 300.50, 5, 11, 'BRAKE REPLACEMENT', 'YES');
--INSERT INTO MAINTENANCE_REQUEST (REQUEST_DATE, QUOTED_COST, OPERATOR_ID, VEHICLE_COMPONENT_ID, SERVICE_DESCRIPTION, IS_COMPLETED) VALUES ('2021-03-03', 2000.20, 1, 15, 'ENGINE REPLACEMENT', 'YES');
--INSERT INTO MAINTENANCE_REQUEST (REQUEST_DATE, QUOTED_COST, OPERATOR_ID, VEHICLE_COMPONENT_ID, SERVICE_DESCRIPTION, IS_COMPLETED) VALUES ('2020-01-15', 700.50, 1, 12, 'CATERNARY REPLACEMENT', 'YES');
--INSERT INTO MAINTENANCE_REQUEST (REQUEST_DATE, QUOTED_COST, OPERATOR_ID, VEHICLE_COMPONENT_ID, SERVICE_DESCRIPTION, IS_COMPLETED) VALUES ('2020-12-16', 500.20, 1, 14, 'PANTOGRAPH REPLACEMENT', 'YES');


--Written by Mathew Chebet
--Fuel Consumption Alert Table Data
-- INSERT INTO FUEL_CONSUMPTION_ALERT (DATE_OCCURED, VEHICLE_ID) VALUES ('2025-12-03', 1);
-- INSERT INTO FUEL_CONSUMPTION_ALERT (DATE_OCCURED, VEHICLE_ID) VALUES ('2015-05-03', 2);
-- INSERT INTO FUEL_CONSUMPTION_ALERT (DATE_OCCURED, VEHICLE_ID) VALUES ('2025-06-23', 3);
-- INSERT INTO FUEL_CONSUMPTION_ALERT (DATE_OCCURED, VEHICLE_ID) VALUES ('2024-07-03', 3);
-- INSERT INTO FUEL_CONSUMPTION_ALERT (DATE_OCCURED, VEHICLE_ID) VALUES ('2023-01-13', 1);
-- INSERT INTO FUEL_CONSUMPTION_ALERT (DATE_OCCURED, VEHICLE_ID) VALUES ('2020-02-03', 1);
-- INSERT INTO FUEL_CONSUMPTION_ALERT (DATE_OCCURED, VEHICLE_ID) VALUES ('2022-05-04', 2);
-- INSERT INTO FUEL_CONSUMPTION_ALERT (DATE_OCCURED, VEHICLE_ID) VALUES ('2019-06-13', 1);
-- INSERT INTO FUEL_CONSUMPTION_ALERT (DATE_OCCURED, VEHICLE_ID) VALUES ('2018-02-03', 2);
-- INSERT INTO FUEL_CONSUMPTION_ALERT (DATE_OCCURED, VEHICLE_ID) VALUES ('2017-01-15', 1);
-- INSERT INTO FUEL_CONSUMPTION_ALERT (DATE_OCCURED, VEHICLE_ID) VALUES ('2016-08-20', 2);
-- INSERT INTO FUEL_CONSUMPTION_ALERT (DATE_OCCURED, VEHICLE_ID) VALUES ('2014-09-12', 1);
-- INSERT INTO FUEL_CONSUMPTION_ALERT (DATE_OCCURED, VEHICLE_ID) VALUES ('2012-10-13', 3);
-- INSERT INTO FUEL_CONSUMPTION_ALERT (DATE_OCCURED, VEHICLE_ID) VALUES ('2012-05-01', 1);

--Written by Mathew Chebet
--Operator Timestamp Table Data
--INSERT INTO OPERATOR_TIMESTAMP (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-19 10:15:00', '2025-01-19 10:30:00', 'BREAK', 2);
--INSERT INTO OPERATOR_TIMESTAMP (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-20 08:00:00', '2025-01-20 10:00:00', 'START', 2);
--INSERT INTO OPERATOR_TIMESTAMP (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-20 10:00:00', '2025-01-20 12:00:00', 'START', 3);
--INSERT INTO OPERATOR_TIMESTAMP (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-20 12:15:00', '2025-01-20 12:00:00', 'START', 3);
--INSERT INTO OPERATOR_TIMESTAMP (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-21 08:00:00', '2025-01-21 11:00:00', 'START', 4);
--INSERT INTO OPERATOR_TIMESTAMP (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-21 11:15:00', '2025-01-21 11:30:00', 'BREAK', 4);
--INSERT INTO OPERATOR_TIMESTAMP (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-22 08:00:00', '2025-01-22 08:00:00', 'START', 2);
--INSERT INTO OPERATOR_TIMESTAMP (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-23 15:00:00', '2025-01-23 18:00:00', 'START', 3);
--INSERT INTO OPERATOR_TIMESTAMP (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-23 18:15:00', '2025-01-23 18:30:00', 'BREAK', 3);
--INSERT INTO OPERATOR_TIMESTAMP (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-24 08:00:00', '2025-01-24 13:00:00', 'START', 2);
--INSERT INTO OPERATOR_TIMESTAMP (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-26 10:45:00', '2025-01-26 11:00:00', 'BREAK', 3);
--INSERT INTO OPERATOR_TIMESTAMP (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-27 11:00:00', '2025-01-27 11:15:00', 'BREAK', 4);
--INSERT INTO OPERATOR_TIMESTAMP (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-29 12:00:00', '2025-01-29 15:00:00', 'START', 4);
--INSERT INTO OPERATOR_TIMESTAMP (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-30 08:00:00', '2025-01-30 14:00:00', 'START', 4);

--Written by Mario
-- Table to log daily energy and fuel consumption per vehicle
CREATE TABLE EnergyFuelLog (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    vehicle_id VARCHAR(20), -- References the VIN of the vehicle
    log_date DATE NOT NULL,
    fuel_consumed FLOAT DEFAULT 0,         -- In liters
    energy_consumed FLOAT DEFAULT 0,       -- In kWh
    fuel_threshold FLOAT DEFAULT 50,       -- Threshold to trigger alerts
    energy_threshold FLOAT DEFAULT 100,    -- Threshold to trigger alerts
    FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_num)
);
