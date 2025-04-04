/* filename: TransitDtb_TableCreation.sql
 * authors: Stephanie Prystupa-Maule, John Tieu, Mathew Chebet
 * description: SQL script for Transit Dtb creation
 * last modified: 04/02/2025
 */

DROP DATABASE IF EXISTS TRANSIT;
CREATE DATABASE TRANSIT;

USE TRANSIT;
 -- -------------------------------------------------------------------------------
/* SECTION 1: SETUP STOPS AND ROUTES */
/* author: Stephanie Prystupa-Maule */

/* Location uses the WGS84 coordinate system, the most common for geographic coordinates (latitude/longitude). 
 * Identified by Spatial Reference ID 4326.
 */
CREATE TABLE STOPS (
	ID INT AUTO_INCREMENT PRIMARY KEY,
    STOP_NAME VARCHAR(100) NOT NULL UNIQUE,
    IS_STATION BOOLEAN NOT NULL DEFAULT FALSE,
    STOP_LOCATION POINT NOT NULL SRID 4326, -- WGS84 system 
    SPATIAL INDEX(STOP_LOCATION) 
);

-- Create table of ROUTES 
CREATE TABLE ROUTES (
	ID INT AUTO_INCREMENT PRIMARY KEY,
    ROUTE_NAME VARCHAR(20) NOT NULL, 
    DIR VARCHAR(1) NOT NULL, -- 1 for original, 2 for return trip 
    TOTAL_DISTANCE INT, -- in km
	DETAILS TEXT, -- optional additional details 
    UNIQUE KEY (ROUTE_NAME, DIR)
);

-- Create table of STOP ORDER in a ROUTE
CREATE TABLE ROUTE_STOPS (
	ID INT AUTO_INCREMENT PRIMARY KEY,
    ROUTE_ID INT NOT NULL,
    STOP_ID INT NOT NULL,
    STOP_SEQUENCE INT NOT NULL, -- order of the stops in the route 
    FOREIGN KEY (ROUTE_ID) REFERENCES ROUTES(ID) ON DELETE CASCADE,
    FOREIGN KEY (STOP_ID) REFERENCES STOPS(ID) ON DELETE CASCADE,
    UNIQUE KEY (ROUTE_ID, STOP_SEQUENCE),
    INDEX (STOP_ID, ROUTE_ID)
);

-- Create a helper procedure for inserting routes
DELIMITER //

CREATE PROCEDURE CREATE_BIDIRECTIONAL_ROUTE(
    IN p_route_name VARCHAR(20),
    IN p_stop_ids TEXT -- Comma-separated list of stop IDs
)
BEGIN
    DECLARE route_id_1 INT;
    DECLARE route_id_2 INT;
    DECLARE current_position INT DEFAULT 1;
    DECLARE next_comma_position INT;
    DECLARE current_stop_id INT;
    DECLARE stop_sequence INT DEFAULT 1;
    DECLARE remaining_string TEXT;
    DECLARE stop_count INT;
    
    -- Create temporary table to hold stop IDs
    DROP TEMPORARY TABLE IF EXISTS temp_stops;
    CREATE TEMPORARY TABLE temp_stops (
        sequence_num INT,
        stop_id INT
    );
    
    -- Insert the original route
    INSERT INTO ROUTES (ROUTE_NAME, DIR) VALUES (p_route_name, '1');
    SET route_id_1 = LAST_INSERT_ID();
    
    -- Insert the return route
    INSERT INTO ROUTES (ROUTE_NAME, DIR) VALUES (p_route_name, '2');
    SET route_id_2 = LAST_INSERT_ID();
    
    -- Parse the comma-separated list and insert into temp table
    SET remaining_string = p_stop_ids;
    
    -- Parse each stop ID and add to temporary table
    parse_loop: LOOP
        -- Find position of next comma
        SET next_comma_position = LOCATE(',', remaining_string);
        
        IF next_comma_position > 0 THEN
            -- Extract the stop ID before the comma
            SET current_stop_id = CAST(TRIM(SUBSTRING(remaining_string, 1, next_comma_position - 1)) AS UNSIGNED);
            -- Update remaining string to remove the processed part
            SET remaining_string = SUBSTRING(remaining_string, next_comma_position + 1);
        ELSE
            -- Last item, no more commas
            SET current_stop_id = CAST(TRIM(remaining_string) AS UNSIGNED);
            SET remaining_string = '';
        END IF;
        
        -- Insert into temporary table
        INSERT INTO temp_stops (sequence_num, stop_id) VALUES (stop_sequence, current_stop_id);
        SET stop_sequence = stop_sequence + 1;
        
        -- Exit loop if no more stops
        IF remaining_string = '' THEN
            LEAVE parse_loop;
        END IF;
    END LOOP parse_loop;
    
    -- Get total number of stops
    SELECT COUNT(*) INTO stop_count FROM temp_stops;
    
    -- Insert stops for the original route (direction 1)
    INSERT INTO ROUTE_STOPS (ROUTE_ID, STOP_ID, STOP_SEQUENCE)
    SELECT route_id_1, STOP_ID, sequence_num
    FROM temp_stops
    ORDER BY sequence_num;
    
    -- Insert stops for the return route (direction 2)
    INSERT INTO ROUTE_STOPS (ROUTE_ID, STOP_ID, STOP_SEQUENCE)
    SELECT route_id_2, STOP_ID, (stop_count - sequence_num + 1) 
    FROM temp_stops
    ORDER BY (stop_count - sequence_num + 1);
    
    -- Clean up
    DROP TEMPORARY TABLE IF EXISTS temp_stops;
END //

DELIMITER ;

 -- -------------------------------------------------------------------------------
/* SECTION 2: SETUP SCHEDULES */
/* author: Stephanie Prystupa-Maule */

CREATE TABLE TRIP_TEMPLATES (
	ID INT AUTO_INCREMENT PRIMARY KEY,
    ROUTE_ID INT NOT NULL,
    DETAILS TEXT, -- e.g. weekday rush hour
    FOREIGN KEY (ROUTE_ID) REFERENCES ROUTES(ID)
	);

CREATE TABLE TEMPLATE_STOP_TIMES (
	ID INT AUTO_INCREMENT PRIMARY KEY,
    TRIP_TEMPLATE_ID INT NOT NULL,
    SEQ_STOP_ID INT NOT NULL,
	ARRIVAL TIME, -- time from start of route, e.g. 10 minutes
    DEPARTURE TIME, -- time from start of route, e.g. 12 minutes
    FOREIGN KEY (TRIP_TEMPLATE_ID) REFERENCES TRIP_TEMPLATES(ID),
    FOREIGN KEY (SEQ_STOP_ID) REFERENCES ROUTE_STOPS(ID)
	);
    
CREATE TABLE TRIP_SCHEDULES (
	ID INT AUTO_INCREMENT PRIMARY KEY,
    ROUTE_ID INT NOT NULL,
    TRIP_TEMPLATE_ID INT NOT NULL,
    START_TIME TIME NOT NULL, 
    FOREIGN KEY (ROUTE_ID) REFERENCES ROUTES(ID)
	);

 CREATE TABLE SCHEDULED_STOP_TIMES (
	ID INT AUTO_INCREMENT PRIMARY KEY,
    TRIP_SCHEDULE_ID INT NOT NULL,
    SEQ_STOP_ID INT NOT NULL,
	ARRIVAL TIME, 
    DEPARTURE TIME,
    FOREIGN KEY (TRIP_SCHEDULE_ID) REFERENCES TRIP_SCHEDULES(ID),
    FOREIGN KEY (SEQ_STOP_ID) REFERENCES ROUTE_STOPS(ID)
	);

-- Create a helper procedure for defining a target schedule
DELIMITER //

CREATE PROCEDURE CREATE_SCHEDULE_FROM_TEMPLATE(
    IN p_trip_template_id INT,
    IN p_start_time TIME
)
BEGIN
    DECLARE v_route_id INT;
    DECLARE v_trip_schedule_id INT;
    
    -- Get the route_id from the trip template
    SELECT ROUTE_ID INTO v_route_id 
    FROM TRIP_TEMPLATES 
    WHERE ID = p_trip_template_id;
    
    -- Insert into trip_schedules
    INSERT INTO TRIP_SCHEDULES (
        ROUTE_ID,
        TRIP_TEMPLATE_ID,
        START_TIME
    ) VALUES (
        v_route_id,
        p_trip_template_id,
        p_start_time
    );
    
    -- Get the newly created trip_schedule_id
    SET v_trip_schedule_id = LAST_INSERT_ID();
    
    -- Insert into scheduled_stop_times by calculating actual times
    INSERT INTO SCHEDULED_STOP_TIMES (
        TRIP_SCHEDULE_ID,
        SEQ_STOP_ID,
        ARRIVAL,
        DEPARTURE
    )
    SELECT
        v_trip_schedule_id,
        tst.SEQ_STOP_ID,
        IF(tst.ARRIVAL IS NULL, NULL, ADDTIME(p_start_time, tst.ARRIVAL)),
        IF(tst.DEPARTURE IS NULL, NULL, ADDTIME(p_start_time, tst.DEPARTURE))
    FROM
        TEMPLATE_STOP_TIMES tst
    WHERE
        tst.TRIP_TEMPLATE_ID = p_trip_template_id
    ORDER BY
        tst.SEQ_STOP_ID;
END //

DELIMITER ;
    
-- -------------------------------------------------------------------------------
/* SECTION 3: SETUP VEHICLES */
/* author: John Tieu */    

CREATE TABLE VEHICLES (
    VEHICLE_ID INT AUTO_INCREMENT PRIMARY KEY,
    VEHICLE_TYPE VARCHAR(19) NOT NULL, 
    VEHICLE_NUMBER VARCHAR(50) NOT NULL, 
    FUEL_TYPE VARCHAR(50) NOT NULL, 
-- what units for each fuel type? e.g. litres/km, kilowatts/km, etc?
    FUEL_CONSUMPTION_RATE FLOAT NOT NULL,
    MAX_PASSENGERS INT NOT NULL,
    CURRENT_ASSIGNED_TRIP INT, -- changed from assigned route to better locate a vehicle at a particular time
    FOREIGN KEY (CURRENT_ASSIGNED_TRIP) REFERENCES TRIP_SCHEDULES(ID)
    );

-- -------------------------------------------------------------------------------
/* SECTION 4: SETUP USERS */
/* authors: John Tieu and Mathew Chebet */ 

-- author: John Tieu
CREATE TABLE CREDENTIALS (
    CREDENTIAL_ID INT AUTO_INCREMENT PRIMARY KEY,
    USERNAME VARCHAR(50) NOT NULL,
    PASSWORD VARCHAR(50) NOT NULL 
	);

-- author: John Tieu
CREATE TABLE OPERATORS (
    OPERATOR_ID INT AUTO_INCREMENT PRIMARY KEY,
    OPERATOR_NAME VARCHAR(50),
    CREDENTIAL_ID INT NOT NULL,
    OPERATOR_USER VARCHAR(9) NOT NULL,
    EMAIL VARCHAR(50),
    FOREIGN KEY (CREDENTIAL_ID) REFERENCES CREDENTIALS(CREDENTIAL_ID)
	);

-- author: Mathew Chebet
CREATE TABLE OPERATOR_TIMESTAMPS (
    TIMESTAMP_ID INT PRIMARY KEY AUTO_INCREMENT,
    PUNCH_TIME_START TIMESTAMP NOT NULL,
    PUNCH_TIME_STOP TIMESTAMP NOT NULL,
    PUNCH_TYPE VARCHAR(25) NOT NULL,
    OPERATOR_ID INT NOT NULL, 
    FOREIGN KEY(OPERATOR_ID) REFERENCES OPERATORS(OPERATOR_ID)   
	);

-- Make a temporary table with inner joins by John Tieu
CREATE OR REPLACE VIEW VEHICLE_TIMETABLES AS 
SELECT VEHICLES.VEHICLE_ID, STOPS.STOP_NAME, STOPS.IS_STATION, SCHEDULED_STOP_TIMES.ARRIVAL, SCHEDULED_STOP_TIMES.DEPARTURE 
FROM SCHEDULED_STOP_TIMES
INNER JOIN TRIP_SCHEDULES ON TRIP_SCHEDULES.ID = SCHEDULED_STOP_TIMES.TRIP_SCHEDULE_ID
INNER JOIN VEHICLES ON VEHICLES.CURRENT_ASSIGNED_TRIP = TRIP_SCHEDULES.ID
INNER JOIN ROUTES ON ROUTES.ID = TRIP_SCHEDULES.ROUTE_ID
INNER JOIN ROUTE_STOPS ON route_stops.ID = SCHEDULED_STOP_TIMES.SEQ_STOP_ID
    AND route_stops.ROUTE_ID = ROUTES.ID
INNER JOIN STOPS ON STOPS.ID = ROUTE_STOPS.STOP_ID;
    
-- -------------------------------------------------------------------------------
/* SECTION 5: SETUP MAINTENANCE */
/* author: Mathew Chebet */ 

CREATE TABLE COMPONENT_TYPES (
    COMPONENT_ID INT PRIMARY KEY AUTO_INCREMENT,
    COMPONENT_NAME VARCHAR(25) NOT NULL
	);   

CREATE TABLE VEHICLE_COMPONENTS( 
    VEHICLE_COMPONENT_ID INT AUTO_INCREMENT PRIMARY KEY,
    HOURS_USED DECIMAL(6,2) DEFAULT 0,
    VEHICLE_ID INT NOT NULL, 
    COMPONENT_ID INT NOT NULL,
    FOREIGN KEY (VEHICLE_ID) REFERENCES VEHICLES(VEHICLE_ID),
    FOREIGN KEY (COMPONENT_ID) REFERENCES COMPONENT_TYPES(COMPONENT_ID)
	);

CREATE TABLE MAINTENANCE_REQUESTS (
    REQUEST_ID INT AUTO_INCREMENT PRIMARY KEY,
    REQUEST_DATE DATE NOT NULL,
    QUOTED_COST DECIMAL(10,2) NOT NULL,
    OPERATOR_ID INT NOT NULL,
    VEHICLE_COMPONENT_ID INT NOT NULL,
    SERVICE_DESCRIPTION VARCHAR(50) NOT NULL,
    IS_COMPLETED BOOLEAN NOT NULL DEFAULT FALSE, -- changed varchar to boolean, see previous line for old version
    FOREIGN KEY(OPERATOR_ID) REFERENCES OPERATORS(OPERATOR_ID),
    FOREIGN KEY(VEHICLE_COMPONENT_ID) REFERENCES VEHICLE_COMPONENTS(VEHICLE_COMPONENT_ID)
	);
    
CREATE TABLE FUEL_CONSUMPTION_ALERTS (
    ALERT_ID INT AUTO_INCREMENT PRIMARY KEY,
    DATE_OCCURED DATE NOT NULL,
    VEHICLE_ID INT NOT NULL, 
    FOREIGN KEY (VEHICLE_ID) REFERENCES VEHICLES(VEHICLE_ID)
	);    
