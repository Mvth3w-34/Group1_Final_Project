/* filename: TransitDtb_TableCreation.sql
 * authors: Stephanie Prystupa-Maule, John Tieu, Mathew Chebet
 * description: SQL script for Transit Dtb creation
 * last modified: 04/06/2025
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

CREATE TABLE MAINTENANCE_ALERTS( 
     MAINTENANCE_ALERT_ID INT AUTO_INCREMENT PRIMARY KEY,
     DATE_OCCURED DATE NOT NULL,
     VEHICLE_COMPONENT_ID INT NOT NULL, 
     FOREIGN KEY (VEHICLE_COMPONENT_ID) REFERENCES VEHICLE_COMPONENTS(VEHICLE_COMPONENT_ID)
 );

CREATE TABLE MAINTENANCE_REQUESTS (
    REQUEST_ID INT AUTO_INCREMENT PRIMARY KEY,
    REQUEST_DATE DATE NOT NULL,
    QUOTED_COST DECIMAL(10,2) NOT NULL,
    OPERATOR_ID INT NOT NULL,
    VEHICLE_COMPONENT_ID INT NOT NULL,
    SERVICE_DESCRIPTION VARCHAR(50) NOT NULL,
    IS_COMPLETED BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY(OPERATOR_ID) REFERENCES OPERATORS(OPERATOR_ID),
    FOREIGN KEY(VEHICLE_COMPONENT_ID) REFERENCES VEHICLE_COMPONENTS(VEHICLE_COMPONENT_ID)
	);
    
CREATE TABLE FUEL_CONSUMPTION_ALERTS (
    ALERT_ID INT AUTO_INCREMENT PRIMARY KEY,
    DATE_OCCURED DATE NOT NULL,
    VEHICLE_ID INT NOT NULL, 
    FOREIGN KEY (VEHICLE_ID) REFERENCES VEHICLES(VEHICLE_ID)
	);    

-- -------------------------------------------------------------------------------
/* SECTION 6: SETUP ACTUAL TRIPS*/
/* author: Stephanie Prystupa-Maule */ 
-- Table to store actual trip execution data
CREATE TABLE ACTUAL_TRIPS (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    SCHEDULED_TRIP_ID INT NOT NULL,
    OPERATOR_ID INT NOT NULL,
    VEHICLE_ID INT NOT NULL,
    TRIP_DATE DATE NOT NULL,
    TRIP_STATUS ENUM('COMPLETED', 'CANCELLED', 'PARTIAL', 'IN_PROGRESS') NOT NULL,
    TOTAL_TIME INT, -- the total time of the trip
    TOTAL_DISTANCE INT, -- the total distanc of the trip
    FOREIGN KEY (SCHEDULED_TRIP_ID) REFERENCES TRIP_SCHEDULES(ID),
    FOREIGN KEY (OPERATOR_ID) REFERENCES OPERATORS(OPERATOR_ID),
    FOREIGN KEY (VEHICLE_ID) REFERENCES VEHICLES(VEHICLE_ID),
    INDEX (TRIP_DATE),
    INDEX (OPERATOR_ID),
    INDEX (VEHICLE_ID)
);

-- Table to store actual stop times during a trip
CREATE TABLE ACTUAL_STOP_TIMES (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ACTUAL_TRIP_ID INT NOT NULL,
    SEQ_STOP_ID INT NOT NULL,
    SCHEDULED_ARRIVAL TIME,
    SCHEDULED_DEPARTURE TIME,
    ACTUAL_ARRIVAL TIME,
    ACTUAL_DEPARTURE TIME,
    ARRIVAL_VARIANCE INT, -- in seconds, positive means late, negative means early
    DEPARTURE_VARIANCE INT, -- in seconds, positive means late, negative means early
    FOREIGN KEY (ACTUAL_TRIP_ID) REFERENCES ACTUAL_TRIPS(ID) ON DELETE CASCADE,
    FOREIGN KEY (SEQ_STOP_ID) REFERENCES ROUTE_STOPS(ID),
    INDEX (SEQ_STOP_ID)
);

/* Helper procedures for inserting Actual Trip Data */
DELIMITER //

-- Procedure to calculate and update total time during a trip
CREATE PROCEDURE UPDATE_TRIP_TOTAL_TIME(
    IN p_actual_trip_id INT
)
BEGIN
    DECLARE v_first_arrival TIME;
    DECLARE v_last_arrival TIME;
    DECLARE v_total_seconds INT;
    
    -- Get the first arrival time (first stop in sequence)
    SELECT 
        ast.ACTUAL_ARRIVAL INTO v_first_arrival
    FROM 
        ACTUAL_STOP_TIMES ast
    JOIN 
        ROUTE_STOPS rs ON ast.SEQ_STOP_ID = rs.ID
    WHERE 
        ast.ACTUAL_TRIP_ID = p_actual_trip_id
    ORDER BY 
        rs.STOP_SEQUENCE ASC
    LIMIT 1;
    
    -- Get the last arrival time (last stop in sequence)
    SELECT 
        ast.ACTUAL_ARRIVAL INTO v_last_arrival
    FROM 
        ACTUAL_STOP_TIMES ast
    JOIN 
        ROUTE_STOPS rs ON ast.SEQ_STOP_ID = rs.ID
    WHERE 
        ast.ACTUAL_TRIP_ID = p_actual_trip_id
    ORDER BY 
        rs.STOP_SEQUENCE DESC
    LIMIT 1;
    
    -- Calculate the time difference in seconds
    IF v_first_arrival IS NOT NULL AND v_last_arrival IS NOT NULL THEN
        SET v_total_seconds = TIME_TO_SEC(TIMEDIFF(v_last_arrival, v_first_arrival));
        
        -- Update the ACTUAL_TRIPS table
        UPDATE ACTUAL_TRIPS
        SET total_time = v_total_seconds
        WHERE ID = p_actual_trip_id;
    END IF;
END //

-- Create a trigger to automatically update total_time when a trip is completed
CREATE TRIGGER after_actual_trip_status_update
AFTER UPDATE ON ACTUAL_TRIPS
FOR EACH ROW
BEGIN
    -- Only update if:
    -- 1. Status changed to COMPLETED
    -- 2. Not already being updated (check via total_time IS NULL)
    -- 3. Previous status wasn't already COMPLETED (to prevent recursion)
    IF NEW.TRIP_STATUS = 'COMPLETED' AND NEW.total_time IS NULL AND OLD.TRIP_STATUS != 'COMPLETED' THEN
        -- Direct update rather than calling procedure to avoid recursion
        UPDATE ACTUAL_TRIPS 
        SET total_time = (
            SELECT TIME_TO_SEC(TIMEDIFF(
                (SELECT ast.ACTUAL_ARRIVAL
                 FROM ACTUAL_STOP_TIMES ast
                 JOIN ROUTE_STOPS rs ON ast.SEQ_STOP_ID = rs.ID
                 WHERE ast.ACTUAL_TRIP_ID = NEW.ID
                 ORDER BY rs.STOP_SEQUENCE DESC
                 LIMIT 1),
                (SELECT ast.ACTUAL_ARRIVAL
                 FROM ACTUAL_STOP_TIMES ast
                 JOIN ROUTE_STOPS rs ON ast.SEQ_STOP_ID = rs.ID
                 WHERE ast.ACTUAL_TRIP_ID = NEW.ID
                 ORDER BY rs.STOP_SEQUENCE ASC
                 LIMIT 1)
            ))
        )
        WHERE ID = NEW.ID;
    END IF;
END //

-- Actual Trip helper procedure
-- does not include individual stops, returns new actual_trip_id for use by other procedures
CREATE PROCEDURE INSERT_ACTUAL_TRIP(
    IN p_scheduled_trip_id INT,
    IN p_operator_id INT,
    IN p_vehicle_id INT,
    IN p_trip_date DATE,
    IN p_trip_status ENUM('COMPLETED', 'CANCELLED', 'PARTIAL', 'IN_PROGRESS')
)
BEGIN
    DECLARE v_actual_trip_id INT;
    DECLARE v_route_distance INT;
    
    -- Get the total_distance from the associated ROUTES table
    SELECT r.TOTAL_DISTANCE INTO v_route_distance
    FROM TRIP_SCHEDULES ts
    JOIN ROUTES r ON ts.ROUTE_ID = r.ID
    WHERE ts.ID = p_scheduled_trip_id;
    
    -- Insert the actual trip with total_distance
    INSERT INTO ACTUAL_TRIPS (
        SCHEDULED_TRIP_ID,
        OPERATOR_ID,
        VEHICLE_ID,
        TRIP_DATE,
        TRIP_STATUS,
        total_distance
    ) VALUES (
        p_scheduled_trip_id,
        p_operator_id,
        p_vehicle_id,
        p_trip_date,
        p_trip_status,
        v_route_distance
    );
    
    -- Get the newly inserted trip ID
    SET v_actual_trip_id = LAST_INSERT_ID();
    
    -- Return the new actual trip ID
    SELECT v_actual_trip_id AS actual_trip_id;
END //

-- Actual Stop helper procedure
-- individual stops, could be used with real-time GPS to track an in-progress route
CREATE PROCEDURE INSERT_ACTUAL_STOP_TIME(
    IN p_actual_trip_id INT,
    IN p_seq_stop_id INT,
    IN p_actual_arrival TIME,
    IN p_actual_departure TIME
)
BEGIN
    DECLARE v_scheduled_arrival TIME;
    DECLARE v_scheduled_departure TIME;
    DECLARE v_arrival_variance INT;
    DECLARE v_departure_variance INT;
    DECLARE v_scheduled_trip_id INT;
    DECLARE v_stop_sequence INT;
    DECLARE v_total_stops INT;
    DECLARE v_is_last_stop BOOLEAN DEFAULT FALSE;
    
    -- Get the scheduled trip ID associated with this actual trip
    SELECT SCHEDULED_TRIP_ID INTO v_scheduled_trip_id 
    FROM ACTUAL_TRIPS 
    WHERE ID = p_actual_trip_id;
    
    -- Get scheduled arrival and departure times
    SELECT 
        sst.ARRIVAL, 
        sst.DEPARTURE
    INTO 
        v_scheduled_arrival, 
        v_scheduled_departure
    FROM 
        SCHEDULED_STOP_TIMES sst
    WHERE 
        sst.TRIP_SCHEDULE_ID = v_scheduled_trip_id
        AND sst.SEQ_STOP_ID = p_seq_stop_id;
    
    -- Calculate variances in seconds
    SET v_arrival_variance = TIME_TO_SEC(TIMEDIFF(p_actual_arrival, v_scheduled_arrival));
    SET v_departure_variance = TIME_TO_SEC(TIMEDIFF(p_actual_departure, v_scheduled_departure));
    
    -- Insert stop time
    INSERT INTO ACTUAL_STOP_TIMES (
        ACTUAL_TRIP_ID,
        SEQ_STOP_ID,
        SCHEDULED_ARRIVAL,
        SCHEDULED_DEPARTURE,
        ACTUAL_ARRIVAL,
        ACTUAL_DEPARTURE,
        ARRIVAL_VARIANCE,
        DEPARTURE_VARIANCE
    ) VALUES (
        p_actual_trip_id,
        p_seq_stop_id,
        v_scheduled_arrival,
        v_scheduled_departure,
        p_actual_arrival,
        p_actual_departure,
        v_arrival_variance,
        v_departure_variance
    );
    
    -- Get the current stop sequence and total number of stops for this route
    SELECT 
        rs.STOP_SEQUENCE, 
        (SELECT COUNT(*) FROM ROUTE_STOPS WHERE ROUTE_ID = rs2.ROUTE_ID) 
    INTO 
        v_stop_sequence, 
        v_total_stops
    FROM 
        ROUTE_STOPS rs
    JOIN 
        ACTUAL_TRIPS at ON at.ID = p_actual_trip_id
    JOIN 
        TRIP_SCHEDULES ts ON ts.ID = at.SCHEDULED_TRIP_ID
    JOIN 
        ROUTE_STOPS rs2 ON rs2.ID = p_seq_stop_id
    WHERE 
        rs.ID = p_seq_stop_id
        AND rs.ROUTE_ID = ts.ROUTE_ID;
    
    -- Check if this is the last stop
    SET v_is_last_stop = (v_stop_sequence = v_total_stops);
    
    -- If this is the last stop, update total_time
    IF v_is_last_stop THEN
        UPDATE ACTUAL_TRIPS 
        SET total_time = (
            SELECT TIME_TO_SEC(TIMEDIFF(
                (SELECT ast.ACTUAL_ARRIVAL
                 FROM ACTUAL_STOP_TIMES ast
                 JOIN ROUTE_STOPS rs ON ast.SEQ_STOP_ID = rs.ID
                 WHERE ast.ACTUAL_TRIP_ID = p_actual_trip_id
                 ORDER BY rs.STOP_SEQUENCE DESC
                 LIMIT 1),
                (SELECT ast.ACTUAL_ARRIVAL
                 FROM ACTUAL_STOP_TIMES ast
                 JOIN ROUTE_STOPS rs ON ast.SEQ_STOP_ID = rs.ID
                 WHERE ast.ACTUAL_TRIP_ID = p_actual_trip_id
                 ORDER BY rs.STOP_SEQUENCE ASC
                 LIMIT 1)
            ))
        )
        WHERE ID = p_actual_trip_id;
    END IF;
END //

-- Generate Test Data procedure
-- used to simulate actual trips with variations in stop times
CREATE PROCEDURE GENERATE_TEST_TRIP(
    IN p_scheduled_trip_id INT,
    IN p_operator_id INT,
    IN p_vehicle_id INT,
    IN p_trip_date DATE,
    IN p_min_variance INT, -- Minimum variance in seconds (can be negative for early)
    IN p_max_variance INT  -- Maximum variance in seconds (positive for late)
)
BEGIN
    DECLARE v_actual_trip_id INT;
    DECLARE v_seq_stop_id INT;
    DECLARE v_scheduled_arrival TIME;
    DECLARE v_scheduled_departure TIME;
    DECLARE v_actual_arrival TIME;
    DECLARE v_actual_departure TIME;
    DECLARE v_done BOOLEAN DEFAULT FALSE;
    
    DECLARE v_stop_cursor CURSOR FOR
        SELECT 
            sst.SEQ_STOP_ID,
            sst.ARRIVAL,
            sst.DEPARTURE
        FROM 
            SCHEDULED_STOP_TIMES sst
        JOIN 
            ROUTE_STOPS rs ON rs.ID = sst.SEQ_STOP_ID
        WHERE 
            sst.TRIP_SCHEDULE_ID = p_scheduled_trip_id
        ORDER BY 
            rs.STOP_SEQUENCE;
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_done = TRUE;
    
    -- Insert the actual trip
    CALL INSERT_ACTUAL_TRIP(
        p_scheduled_trip_id,
        p_operator_id,
        p_vehicle_id,
        p_trip_date,
        'COMPLETED'
    );
    
    -- Get the newly inserted trip ID
    SET v_actual_trip_id = LAST_INSERT_ID();
    
    -- Open cursor and loop through all stops
    OPEN v_stop_cursor;
    
    read_loop: LOOP
        FETCH v_stop_cursor INTO v_seq_stop_id, v_scheduled_arrival, v_scheduled_departure;
        
        IF v_done THEN
            LEAVE read_loop;
        END IF;
        
        -- Generate random variance within the specified range
        SET v_actual_arrival = ADDTIME(
            v_scheduled_arrival, 
            SEC_TO_TIME(
                FLOOR(
                    p_min_variance + RAND() * (p_max_variance - p_min_variance)
                )
            )
        );
        
        SET v_actual_departure = ADDTIME(
            v_scheduled_departure, 
            SEC_TO_TIME(
                FLOOR(
                    p_min_variance + RAND() * (p_max_variance - p_min_variance)
                )
            )
        );
        
        -- Insert stop time
        CALL INSERT_ACTUAL_STOP_TIME(
            v_actual_trip_id,
            v_seq_stop_id,
            v_actual_arrival,
            v_actual_departure
        );
    END LOOP;
    
    CLOSE v_stop_cursor;
    
END //

DELIMITER ;

/* Example Usage 
-- Insert a trip and its stops separately
CALL INSERT_ACTUAL_TRIP(123, 45, 67, '2025-04-03', 'COMPLETED');
-- (returns actual_trip_id, e.g. 789)

-- Then add individual stop times
CALL INSERT_ACTUAL_STOP_TIMES(789, 101, '08:05:00', '08:06:30');
CALL INSERT_ACTUAL_STOP_TIMES(789, 102, '08:15:45', '08:17:20');
-- etc.

-- Generate test data with variances between -5 and +15 minutes
CALL GENERATE_TEST_TRIP(123, 45, 67, '2025-04-03', -300, 900);
*/