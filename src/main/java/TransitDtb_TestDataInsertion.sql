/* filename: TransitDtb_TestDataInsertion.sql
 * authors: Stephanie Prystupa-Maule, John Tieu, Mathew Chebet
 * description: SQL script for inserting test data into the Transit Dtb
 * last modified: 04/04/2025
 */

USE TRANSIT;

-- ----------------------------------------------------------------------------------------------------------
/* SECTION 1: ROUTE RELATED */
/* author: Stephanie Prystupa-Maule */

-- Approx Line 1
INSERT INTO STOPS (STOP_NAME, IS_STATION, STOP_LOCATION) 
VALUES 
    ("Tunney's Pasture Station", TRUE, ST_PointFromText('POINT(-75.7352245883478 45.40401575567834)', 4326)),
    ("Bayview Station", TRUE, ST_PointFromText('POINT(-75.72525208834755 45.40862498182077)', 4326)),
    ("Pimisi Station", TRUE, ST_PointFromText('POINT(-75.7136209460185 45.41404097723)', 4326)),
    ("Lyon Station", TRUE, ST_PointFromText('POINT(-75.70425640368947 45.419397638845915)', 4326)),
    ("Parliament Station", TRUE, ST_PointFromText('POINT(-75.69970593437452 45.42135248509583)', 4326)),
    ("Rideau Station", TRUE, ST_PointFromText('POINT(-75.69196633067526 45.42624612658454)', 4326)),
    ("uOttawa Station", TRUE, ST_PointFromText('POINT(-75.6827392748538 45.420978355632855)', 4326)),
    ("Lees Station", TRUE, ST_PointFromText('POINT(-75.6702620813173 45.41621273232483)', 4326)),
    ("Hurdman Station", TRUE, ST_PointFromText('POINT(-75.66369819999107 45.411091960409976)', 4326)),
    ("Tremblay Station", TRUE, ST_PointFromText('POINT(-75.65328014231936 45.41736284143393)', 4326)),
    ("St. Laurent Station", TRUE, ST_PointFromText('POINT(-75.63785151903197 45.42072018171425)', 4326)),
    ("Cyrville Station", TRUE, ST_PointFromText('POINT(-75.62633585766163 45.42299454113686)', 4326)),
    ("Blair Station", TRUE, ST_PointFromText('POINT(-75.60862856596883 45.43093036319211)', 4326))
;

-- Approx 62
INSERT INTO STOPS (STOP_NAME, IS_STATION, STOP_LOCATION) 
VALUES 
    ("Terry Fox Station", TRUE, ST_PointFromText('POINT(-75.90690447670967 45.30940820250102)', 4326)),
    ("Kanata & Campeau", FALSE, ST_PointFromText('POINT(-75.91646958383394 45.31338685458409)', 4326)),
    ("Campeau & Gray", FALSE, ST_PointFromText('POINT(-75.89846685287512 45.3179024961574)', 4326)),
    ("Teron Station", TRUE, ST_PointFromText('POINT(-75.88929708835254 45.32273347368031)', 4326)),
    ("417 Ramp & Moodie", FALSE, ST_PointFromText('POINT(-75.84113439669215 45.33650827337948)', 4326)),
    ("Bayshore Station", TRUE, ST_PointFromText('POINT(-75.81078033789781 45.345959472658315)', 4326)),
    ("Holly Acres & Ramp", FALSE, ST_PointFromText('POINT(-75.81108927485816 45.343866602499595)', 4326)),
    ("Pinecrest", FALSE, ST_PointFromText('POINT(-75.78690871533715 45.348911811656)', 4326)),
    ("Lincoln Fields Station", TRUE, ST_PointFromText('POINT(-75.78248483437768 45.366022639797755)', 4326)),
    ("Dominion Station", TRUE, ST_PointFromText('POINT(-75.76054989204744 45.39240750044255)', 4326)),
    ("Westboro Station", TRUE, ST_PointFromText('POINT(-75.7519478018413 45.396614683616)', 4326))
    -- repeat ("Tunney's Pasture Station", TRUE, ST_PointFromText('POINT(-75.7352245883478 45.40401575567834)', 4326))
;

-- Approx route 88
INSERT INTO STOPS (STOP_NAME, IS_STATION, STOP_LOCATION) 
VALUES 
    -- repeat ("Terry Fox Station", TRUE, ST_PointFromText('POINT(-75.90690447670967 45.30940820250102)', 4326)),
    ("Lord Byng & Kanata", FALSE, ST_PointFromText('POINT(-75.90533889020261 45.312153101268756)', 4326)),
    ("Katimavik & Castlefrank", FALSE, ST_PointFromText('POINT(-75.89927854602465 45.308853107198665)', 4326)),
    ("Terry Fox & Edgewater", FALSE, ST_PointFromText('POINT(-75.90085646136788 45.29690666437633)', 4326)),
    ("Hazeldean & Castlefrank", FALSE, ST_PointFromText('POINT(-75.88659844232623 45.29941843968636)', 4326)),
    ("Eagleson Station", TRUE, ST_PointFromText('POINT(-75.88476779547725 45.318108895220526)', 4326)),
    ("Robertson & Haanel", FALSE, ST_PointFromText('POINT(-75.85891000369574 45.31161841755316)', 4326)),
    ("Robertson & Northside", FALSE, ST_PointFromText('POINT(-75.8171329190372 45.32904362541365)', 4326)),
    ("Baseline & Sandcastle", FALSE, ST_PointFromText('POINT(-75.79987854602298 45.3358785171264)', 4326)),
    ("Baseline & Centrepointe", FALSE, ST_PointFromText('POINT(-75.76846886863822 45.349672935033084)', 4326)),
    ("Baseline Station", TRUE, ST_PointFromText('POINT(-75.76180823252926 45.34794825619941)', 4326)),
    ("Baseline & Cordova", FALSE, ST_PointFromText('POINT(-75.74977146136438 45.3573931510019)', 4326)),
    ("1305 Baseline", FALSE, ST_PointFromText('POINT(-75.73390708835011 45.364180580152734)', 4326)),
    ("Baseline & Prince of Wales", FALSE, ST_PointFromText('POINT(-75.70777417300695 45.37513316665185)', 4326)),
    ("Heron & Riverside", FALSE, ST_PointFromText('POINT(-75.68997000369185 45.37724577031831)', 4326)),
    ("Heron Station", TRUE, ST_PointFromText('POINT(-75.68080813067802 45.378903557104266)', 4326)),
    ("Billings Bridge Station", TRUE, ST_PointFromText('POINT(-75.67692000369148 45.384860536056365)', 4326)),
    ("Pleasant Park", FALSE, ST_PointFromText('POINT(-75.66940226783368 45.393070388979474)', 4326)),
    ("The Ottawa Hospital", FALSE, ST_PointFromText('POINT(-75.64947515976782 45.40188339329608)', 4326)),
    ("Lycee Claudel", FALSE, ST_PointFromText('POINT(-75.663581964702 45.40633475557828)', 4326))
    -- repeat ("Hurdman Station", TRUE, ST_PointFromText('POINT(-75.66369819999107 45.411091960409976)', 4326))
;

/* Create routes using create_bidirectional_route procedure */
-- Line 1, train 
CALL CREATE_BIDIRECTIONAL_ROUTE('Line 1', '1,2,3,4,5,6,7,8,9,10,11,12,13');
-- 62, bus
CALL CREATE_BIDIRECTIONAL_ROUTE('62', '14,15,16,17,18,19,20,21,22,23,24,1');
-- 88, bus
CALL CREATE_BIDIRECTIONAL_ROUTE('88', '14,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,9');
 
/* Add total distance manually */
UPDATE ROUTES SET TOTAL_DISTANCE = 12 WHERE ROUTE_NAME = 'Line 1';
UPDATE ROUTES SET TOTAL_DISTANCE = 22 WHERE ROUTE_NAME = '62';
UPDATE ROUTES SET TOTAL_DISTANCE = 28 WHERE ROUTE_NAME = '88';
 
/* Future improvement, leverage GIS integration to create function that calculates total distance
 * from the stops in a route, then create trigger on route inserts/updates that updates them to add it 
 */
 
 /* Create templates that will define scheduled trips */
 -- Create Line 1 trip templates for Weekday, Normal Service
INSERT INTO TRIP_TEMPLATES(ROUTE_ID, DETAILS) VALUE (1, "Weekday, Normal Service");
INSERT INTO TEMPLATE_STOP_TIMES(TRIP_TEMPLATE_ID, SEQ_STOP_ID, ARRIVAL, DEPARTURE) 
VALUES 
	(1, 1, '00:00:00', '00:05:00'),
    (1, 2, '00:08:00', '00:09:00'),
    (1, 3, '00:11:00', '00:12:00'),
    (1, 4, '00:14:00', '00:15:00'),
    (1, 5, '00:17:00', '00:18:00'),
    (1, 6, '00:20:00', '00:21:00'),
    (1, 7, '00:24:00', '00:25:00'),
    (1, 8, '00:27:00', '00:28:00'),
    (1, 9, '00:30:00', '00:31:00'),
    (1, 10, '00:34:00', '00:35:00'),
    (1, 11, '00:39:00', '00:40:00'),
    (1, 12, '00:42:00', '00:43:00'),
    (1, 13, '00:45:00', NULL)
    ;
INSERT INTO TRIP_TEMPLATES(ROUTE_ID, DETAILS) VALUE (2, "Weekday, Normal Service");
INSERT INTO TEMPLATE_STOP_TIMES(TRIP_TEMPLATE_ID, SEQ_STOP_ID, ARRIVAL, DEPARTURE) 
VALUES 
	(2, 16, '00:00:00', '00:05:00'),
    (2, 17, '00:08:00', '00:09:00'),
    (2, 18, '00:11:00', '00:12:00'),
    (2, 19, '00:15:00', '00:16:00'),
    (2, 20, '00:19:00', '00:20:00'),
    (2, 21, '00:23:00', '00:24:00'),
    (2, 22, '00:26:00', '00:27:00'),
    (2, 23, '00:30:00', '00:31:00'),
    (2, 24, '00:33:00', '00:34:00'),
    (2, 25, '00:36:00', '00:37:00'),
    (2, 26, '00:39:00', '00:40:00'),
    (2, 27, '00:42:00', '00:43:00'),
    (2, 28, '00:45:00', NULL)
    ;

-- Create 62 trip template for Weekday, Normal Service
INSERT INTO TRIP_TEMPLATES(ROUTE_ID, DETAILS) VALUE (3, "Weekday, Normal Service");
INSERT INTO TEMPLATE_STOP_TIMES(TRIP_TEMPLATE_ID, SEQ_STOP_ID, ARRIVAL, DEPARTURE) 
VALUES 
	(3, 31, '00:00:00', '00:01:00'), -- Terry Fox
    (3, 34, '00:11:00', '00:11:30'), -- Teron
    (3, 36, '00:23:00', '00:23:30'), -- Bayshore
    (3, 39, '00:32:30', '00:33:00'), -- Lincoln Fields
    (3, 40, '00:36:00', '00:36:30'), -- Dominion
    (3, 41, '00:37:30', '00:38:00'), -- Westboro
    (3, 42, '00:41:00', NULL) -- Tunney's Pasture
    ;    
INSERT INTO TRIP_TEMPLATES(ROUTE_ID, DETAILS) VALUE (4, "Weekday, Normal Service");
INSERT INTO TEMPLATE_STOP_TIMES(TRIP_TEMPLATE_ID, SEQ_STOP_ID, ARRIVAL, DEPARTURE) 
VALUES 
	(4, 46, '00:00:00', '00:01:00'), -- Tunney's Pasture
    (4, 47, '00:04:00', '00:04:30'), -- Westboro
    (4, 48, '00:05:30', '00:06:00'), -- Dominion
    (4, 49, '00:09:00', '00:09:30'), -- Lincoln Fields
    (4, 52, '00:18:30', '00:19:00'), -- Bayshore 
    (4, 54, '00:30:30', '00:31:00'), -- Teron 
    (4, 57, '00:41:00', NULL) -- Terry Fox 
    ;
    
-- Create 88 trip template for Weekday, Normal Service
INSERT INTO TRIP_TEMPLATES(ROUTE_ID, DETAILS) VALUE (5, "Weekday, Normal Service");
INSERT INTO TEMPLATE_STOP_TIMES(TRIP_TEMPLATE_ID, SEQ_STOP_ID, ARRIVAL, DEPARTURE) 
VALUES 
	(5, 61, '00:00:00', '00:01:00'), -- Terry Fox
    (5, 66, '00:10:00', '00:10:30'), -- Eagleson
    (5, 71, '00:25:30', '00:26:00'), -- Baseline
    (5, 76, '00:46:00', '00:46:30'), -- Heron
    (5, 77, '00:52:30', '00:53:00'), -- Billings Bridge
    (5, 81, '01:00:00', NULL) -- Hurdman
    ;    
INSERT INTO TRIP_TEMPLATES(ROUTE_ID, DETAILS) VALUE (6, "Weekday, Normal Service");
INSERT INTO TEMPLATE_STOP_TIMES(TRIP_TEMPLATE_ID, SEQ_STOP_ID, ARRIVAL, DEPARTURE) 
VALUES 
	(6, 92, '00:00:00', '00:01:00'), -- Hurdman
    (6, 96, '00:8:00', '00:08:30'), -- Billings Bridge
    (6, 97, '00:14:30', '00:15:00'), -- Heron
    (6, 102, '00:35:00', '00:35:30'), -- Baseline
    (6, 107, '00:50:30', '00:51:00'), -- Eagleson
    (6, 112, '01:00:00', NULL) -- Terry Fox
    ;

/* Use templates to create schedules of trips */
-- Line 1, dir 1, 8:00 AM run
CALL CREATE_SCHEDULE_FROM_TEMPLATE(1, '08:00:00');
-- Line 1, dir 2, 8:00 AM run
CALL CREATE_SCHEDULE_FROM_TEMPLATE(2, '08:00:00');

-- 62, dir 1, 7:30 AM run
CALL CREATE_SCHEDULE_FROM_TEMPLATE(3, '07:30:00');
-- 62, dir 2, 7:30 AM run
CALL CREATE_SCHEDULE_FROM_TEMPLATE(4, '07:30:00');

-- 88, dir 1, 11:00 AM run
CALL CREATE_SCHEDULE_FROM_TEMPLATE(5, '11:00:00');
-- 88, dir 2, 11:00 AM run
CALL CREATE_SCHEDULE_FROM_TEMPLATE(6, '11:30:00');

-- ----------------------------------------------------------------------------------------------------------
/* SECTION 2: VEHICLES */
/* author: Stephanie Prystupa-Maule */

-- NOTE: may want to revisit after Fuel Tracking is implemented 
INSERT INTO VEHICLES (VEHICLE_TYPE, VEHICLE_NUMBER, FUEL_TYPE, FUEL_CONSUMPTION_RATE, MAX_PASSENGERS, CURRENT_ASSIGNED_TRIP)
VALUES
    ("BUS", "001", "DIESEL", 10.0, 75, NULL), 
    ("ELECTRIC_TRAIN", "T100", "ELECTRICITY", 1000.0, 300, NULL), 
    ("DIESEL_TRAIN", "T200", "DIESEL", 30.0, 240, NULL),
    ("BUS", "002", "CNG", 9.0, 60, NULL)
    ;

-- ----------------------------------------------------------------------------------------------------------
/* SECTION 3: USER RELATED */
/* authors: John Tieu and Mathew Chebet */

-- author: John Tieu
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

INSERT INTO OPERATORS (OPERATOR_NAME, CREDENTIAL_ID, OPERATOR_USER, EMAIL) VALUES ('Bob Smith', 1, 'MANAGER', '');
INSERT INTO OPERATORS (OPERATOR_NAME, CREDENTIAL_ID, OPERATOR_USER, EMAIL) VALUES ('Drew Anderson', 2, 'OPERATOR', '');
INSERT INTO OPERATORS (OPERATOR_NAME, CREDENTIAL_ID, OPERATOR_USER, EMAIL) VALUES ('Danny Dan', 3, 'OPERATOR', 'hisEMAIL@newEMAIL.com');
INSERT INTO OPERATORS (OPERATOR_NAME, CREDENTIAL_ID, OPERATOR_USER, EMAIL) VALUES ('Joe Clarke', 4, 'OPERATOR', 'joeclarke@clarke.com');
INSERT INTO OPERATORS (OPERATOR_NAME, CREDENTIAL_ID, OPERATOR_USER, EMAIL) VALUES ('Kate Amberly', 5, 'MANAGER', 'testEMAIL@EMAIL.com');

-- author: Mathew Chebet
INSERT INTO OPERATOR_TIMESTAMPS (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-19 10:15:00', '2025-01-19 10:30:00', 'BREAK', 2);
INSERT INTO OPERATOR_TIMESTAMPS (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-20 08:00:00', '2025-01-20 10:00:00', 'START', 2);
INSERT INTO OPERATOR_TIMESTAMPS (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-20 10:00:00', '2025-01-20 12:00:00', 'START', 3);
INSERT INTO OPERATOR_TIMESTAMPS (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-20 12:15:00', '2025-01-20 12:00:00', 'START', 3);
INSERT INTO OPERATOR_TIMESTAMPS (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-21 08:00:00', '2025-01-21 11:00:00', 'START', 4);
INSERT INTO OPERATOR_TIMESTAMPS (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-21 11:15:00', '2025-01-21 11:30:00', 'BREAK', 4);
INSERT INTO OPERATOR_TIMESTAMPS (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-22 08:00:00', '2025-01-22 08:00:00', 'START', 2);
INSERT INTO OPERATOR_TIMESTAMPS (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-23 15:00:00', '2025-01-23 18:00:00', 'START', 3);
INSERT INTO OPERATOR_TIMESTAMPS (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-23 18:15:00', '2025-01-23 18:30:00', 'BREAK', 3);
INSERT INTO OPERATOR_TIMESTAMPS (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-24 08:00:00', '2025-01-24 13:00:00', 'START', 2);
INSERT INTO OPERATOR_TIMESTAMPS (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-26 10:45:00', '2025-01-26 11:00:00', 'BREAK', 3);
INSERT INTO OPERATOR_TIMESTAMPS (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-27 11:00:00', '2025-01-27 11:15:00', 'BREAK', 4);
INSERT INTO OPERATOR_TIMESTAMPS (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-29 12:00:00', '2025-01-29 15:00:00', 'START', 4);
INSERT INTO OPERATOR_TIMESTAMPS (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) VALUES ('2025-01-30 08:00:00', '2025-01-30 14:00:00', 'START', 4);

-- ----------------------------------------------------------------------------------------------------------
/* SECTION 4: MAINTENANCE RELATED */
/* author: Mathew Chebet */
-- Component_Type Table data
INSERT INTO COMPONENT_TYPES(COMPONENT_NAME) 
VALUES 
	('REAR BRAKES'),
	('FRONT BRAKES'),
	('LEFT REAR TIRE'),
	('LEFT FRONT TIRE'),
	('RIGHT FRONT TIRE'),
	('RIGHT REAR TIRE'),
	('REAR AXLE BEARINGS'),
	('FRONT AXLE BEARINGS'),
	('CATERNARY'),
	('CIRCUIT BREAKER'),
	('PANTOGRAPH'),
	('DIESEL ENGINE'),
	('ELECTRIC ENGINE')
    ;

-- Vehicle Component Table Data
INSERT INTO VEHICLE_COMPONENTS (HOURS_USED, VEHICLE_ID, COMPONENT_ID) 
VALUES 
	(600, 1, 1),
	(600, 1, 2),
	(200, 1, 3),
	(200, 1, 4),
	(200, 1, 5),
	(200, 1, 6),
	(300, 1, 7),
	(300, 1, 8),
	(250, 1, 11),
	(700, 2, 1),
	(700, 2, 2),
	(250, 2, 9),
	(300, 2, 10),
	(250, 2, 11),
	(500, 2, 13),
	(700, 3, 1),
	(700, 3, 2),
	(250, 3, 9),
	(300, 3, 10),
	(250, 3, 11),
	(500, 3, 12)
    ;
 
-- Maintenance Request Table Data
INSERT INTO MAINTENANCE_REQUESTS (REQUEST_DATE, QUOTED_COST, OPERATOR_ID, VEHICLE_COMPONENT_ID, SERVICE_DESCRIPTION, IS_COMPLETED) 
-- updated inserts: boolean values for is_completed, and nullS (for now) for optional operator_id. original below
VALUES 
	('2025-12-03', 300.50, 1, 1, 'BRAKE REPLACEMENT', FALSE),
	('2025-12-05', 300.50, 1, 2, 'BRAKE REPLACEMENT', FALSE),
	('2023-10-03', 600, 5, 13, 'CIRCUIT BREAKER REPLACEMENT', TRUE),
	('2022-12-03', 300.50, 1, 10, 'BRAKE REPLACEMENT', TRUE),
	('2022-12-04', 300.50, 1, 11, 'BRAKE REPLACEMENT', TRUE),
	('2022-09-03', 1500.50, 5, 12, 'ENGINE REPLACEMENT', TRUE),
	('2022-07-03', 300.50, 5, 7, 'AXLE BEARING REPLACEMENT', TRUE),
	('2021-12-05', 300.50, 5, 8, 'AXLE BEARING REPLACEMENT', TRUE),
	('2021-11-06', 105.50, 5, 3, 'TIRE REPLACEMENT', TRUE),
	('2021-10-15', 105.50, 5, 4, 'TIRE REPLACEMENT', TRUE),
	('2021-12-30', 300.50, 5, 11, 'BRAKE REPLACEMENT', TRUE),
	('2021-03-03', 2000.20, 1, 15, 'ENGINE REPLACEMENT', TRUE),
	('2020-01-15', 700.50, 1, 12, 'CATERNARY REPLACEMENT', TRUE),
	('2020-12-16', 500.20, 1, 14, 'PANTOGRAPH REPLACEMENT', TRUE)
    ;

-- Fuel Consumption Alerts Table Data
INSERT INTO FUEL_CONSUMPTION_ALERTS (DATE_OCCURED, VEHICLE_ID) VALUES ('2025-12-03', 1);
INSERT INTO FUEL_CONSUMPTION_ALERTS (DATE_OCCURED, VEHICLE_ID) VALUES ('2015-05-03', 2);
INSERT INTO FUEL_CONSUMPTION_ALERTS (DATE_OCCURED, VEHICLE_ID) VALUES ('2025-06-23', 3);
INSERT INTO FUEL_CONSUMPTION_ALERTS (DATE_OCCURED, VEHICLE_ID) VALUES ('2024-07-03', 3);
INSERT INTO FUEL_CONSUMPTION_ALERTS (DATE_OCCURED, VEHICLE_ID) VALUES ('2023-01-13', 1);
INSERT INTO FUEL_CONSUMPTION_ALERTS (DATE_OCCURED, VEHICLE_ID) VALUES ('2020-02-03', 1);
INSERT INTO FUEL_CONSUMPTION_ALERTS (DATE_OCCURED, VEHICLE_ID) VALUES ('2022-05-04', 2);
INSERT INTO FUEL_CONSUMPTION_ALERTS (DATE_OCCURED, VEHICLE_ID) VALUES ('2019-06-13', 1);
INSERT INTO FUEL_CONSUMPTION_ALERTS (DATE_OCCURED, VEHICLE_ID) VALUES ('2018-02-03', 2);
INSERT INTO FUEL_CONSUMPTION_ALERTS (DATE_OCCURED, VEHICLE_ID) VALUES ('2017-01-15', 1);
INSERT INTO FUEL_CONSUMPTION_ALERTS (DATE_OCCURED, VEHICLE_ID) VALUES ('2016-08-20', 2);
INSERT INTO FUEL_CONSUMPTION_ALERTS (DATE_OCCURED, VEHICLE_ID) VALUES ('2014-09-12', 1);
INSERT INTO FUEL_CONSUMPTION_ALERTS (DATE_OCCURED, VEHICLE_ID) VALUES ('2012-10-13', 3);
INSERT INTO FUEL_CONSUMPTION_ALERTS (DATE_OCCURED, VEHICLE_ID) VALUES ('2012-05-01', 1);

-- ----------------------------------------------------------------------------------------------------------
/* SECTION 5: ACTUAL TRIP RELATED */
/* author: Stephanie Prystupa-Maule */
-- variable order (trip_schedule_id, operator_id, vehicle_id, trip_date, min_variance, max_variance)

-- User 2, with Vehicle 4, on Line 1 dir 1 (8:00 Weekday, normal service)
-- Perfect Times 
CALL GENERATE_TEST_TRIP(1, 2, 4, '2025-03-24', 0, 0);
CALL GENERATE_TEST_TRIP(1, 2, 4, '2025-03-25', 0, 0);
CALL GENERATE_TEST_TRIP(1, 2, 4, '2025-03-26', 0, 0);


-- User 3, with Vehicle 4, on Line 1 dir 2 (8:00 Weekday, normal service)
-- Between 2 and 5 minutes late
CALL GENERATE_TEST_TRIP(1, 3, 4, '2025-03-31', 120, 300);
CALL GENERATE_TEST_TRIP(1, 3, 4, '2025-04-01', 120, 300);
CALL GENERATE_TEST_TRIP(1, 3, 4, '2025-04-02', 120, 300);

-- User 4, with Vehicle 2, on Route 62 dir 1 (7:30 Weekday, normal service)
-- Between 2 minutes early and 1.5 minutes late
CALL GENERATE_TEST_TRIP(3, 4, 2, '2025-03-24', -120, 90);
CALL GENERATE_TEST_TRIP(3, 4, 2, '2025-03-25', -120, 90);
CALL GENERATE_TEST_TRIP(3, 4, 2, '2025-03-26', -120, 90);

