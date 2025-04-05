/* filename: TransitDtb_ViewCreation.sql
 * authors: Stephanie Prystupa-Maule, John Tieu, Mathew Chebet
 * description: SQL script for Transit Dtb Views
 * last modified: 04/03/2025
 */

USE TRANSIT;

 -- -------------------------------------------------------------------------------
/* SECTION 1: DASHBOARD VIEWS */
/* author: Stephanie Prystupa-Maule */

-- View to Calculate Driver Performance Metrics
-- covers ontime/early/late, arrival/departure, and combined
CREATE VIEW VW_OPERATOR_PERFORMANCE AS
SELECT 
    o.OPERATOR_ID,
    o.OPERATOR_NAME,
    COUNT(DISTINCT at.ID) AS total_trips,
    
    -- Combined variance metric (average of arrival and departure variances where both exist)
    AVG(CASE 
        WHEN ast.SCHEDULED_DEPARTURE IS NOT NULL 
        THEN (ABS(ast.ARRIVAL_VARIANCE) + ABS(ast.DEPARTURE_VARIANCE)) / 2
        ELSE ABS(ast.ARRIVAL_VARIANCE)
    END) AS avg_combined_variance,
    
    -- On-time metrics (within 60 seconds of schedule)
    (SUM(CASE WHEN ast.ARRIVAL_VARIANCE BETWEEN -60 AND 60 THEN 1 ELSE 0 END) / COUNT(ast.ID) * 100) AS pct_ontime_arrivals,
    
    -- For departures, only count stops where scheduled departure exists
    (SUM(CASE WHEN ast.DEPARTURE_VARIANCE BETWEEN -60 AND 60 AND ast.SCHEDULED_DEPARTURE IS NOT NULL THEN 1 ELSE 0 END) / 
     SUM(CASE WHEN ast.SCHEDULED_DEPARTURE IS NOT NULL THEN 1 ELSE 0 END) * 100) AS pct_ontime_departures,
    
    -- Early metrics (more than 60 seconds early)
    (SUM(CASE WHEN ast.ARRIVAL_VARIANCE < -60 THEN 1 ELSE 0 END) / COUNT(ast.ID) * 100) AS pct_early_arrivals,
    AVG(CASE WHEN ast.ARRIVAL_VARIANCE < -60 THEN ast.ARRIVAL_VARIANCE ELSE NULL END) AS avg_early_arrival_time,
    
    -- Only include stops with scheduled departures
    (SUM(CASE WHEN ast.DEPARTURE_VARIANCE < -60 AND ast.SCHEDULED_DEPARTURE IS NOT NULL THEN 1 ELSE 0 END) / 
     SUM(CASE WHEN ast.SCHEDULED_DEPARTURE IS NOT NULL THEN 1 ELSE 0 END) * 100) AS pct_early_departures,
    AVG(CASE WHEN ast.DEPARTURE_VARIANCE < -60 THEN ast.DEPARTURE_VARIANCE ELSE NULL END) AS avg_early_departure_time,
    
    -- Late metrics (more than 60 seconds late)
    (SUM(CASE WHEN ast.ARRIVAL_VARIANCE > 60 THEN 1 ELSE 0 END) / COUNT(ast.ID) * 100) AS pct_late_arrivals,
    AVG(CASE WHEN ast.ARRIVAL_VARIANCE > 60 THEN ast.ARRIVAL_VARIANCE ELSE NULL END) AS avg_late_arrival_time,
    
    -- Only include stops with scheduled departures
    (SUM(CASE WHEN ast.DEPARTURE_VARIANCE > 60 AND ast.SCHEDULED_DEPARTURE IS NOT NULL THEN 1 ELSE 0 END) / 
     SUM(CASE WHEN ast.SCHEDULED_DEPARTURE IS NOT NULL THEN 1 ELSE 0 END) * 100) AS pct_late_departures,
    AVG(CASE WHEN ast.DEPARTURE_VARIANCE > 60 THEN ast.DEPARTURE_VARIANCE ELSE NULL END) AS avg_late_departure_time
    
FROM 
    OPERATORS o
JOIN 
    ACTUAL_TRIPS at ON o.OPERATOR_ID = at.OPERATOR_ID
JOIN 
    ACTUAL_STOP_TIMES ast ON at.ID = ast.ACTUAL_TRIP_ID
WHERE 
    at.TRIP_STATUS = 'COMPLETED'
GROUP BY 
    o.OPERATOR_ID, o.OPERATOR_NAME;


 -- -------------------------------------------------------------------------------
/* SECTION 2: BASIC FORMATTING */
/* author: John Tieu */

CREATE OR REPLACE VIEW VEHICLE_FORMATTED AS
SELECT VEHICLE_ID AS "Vehicle ID",
    VEHICLE_TYPE AS "Vehicle Type",
    VEHICLE_NUMBER AS "Vehicle Number",
    FUEL_TYPE AS "Fuel Type",
    FUEL_CONSUMPTION_RATE AS "Fuel Consumption Rate (L/mile)",
    MAX_PASSENGERS AS "Max Passengers",
    CURRENT_ASSIGNED_TRIP AS "Current Trip"
FROM VEHICLES;

 -- -------------------------------------------------------------------------------
/* SECTION 3: TRIP REPORT VIEWS */
/* authors: John Tieu and Stephanie Prystupa-Maule */

-- author John Tieu
CREATE OR REPLACE VIEW VEHICLE_TIMETABLES AS 
SELECT VEHICLES.VEHICLE_ID, STOPS.STOP_NAME AS "STATION", STOPS.IS_STATION, SCHEDULED_STOP_TIMES.ARRIVAL AS "ARRIVAL TIME", SCHEDULED_STOP_TIMES.DEPARTURE AS "DEPARTURE TIME"
FROM SCHEDULED_STOP_TIMES
INNER JOIN TRIP_SCHEDULES ON TRIP_SCHEDULES.ID = SCHEDULED_STOP_TIMES.TRIP_SCHEDULE_ID
INNER JOIN VEHICLES ON VEHICLES.CURRENT_ASSIGNED_TRIP = TRIP_SCHEDULES.ID
INNER JOIN ROUTES ON ROUTES.ID = TRIP_SCHEDULES.ROUTE_ID
INNER JOIN ROUTE_STOPS ON route_stops.ID = SCHEDULED_STOP_TIMES.SEQ_STOP_ID
    AND route_stops.ROUTE_ID = ROUTES.ID
INNER JOIN STOPS ON STOPS.ID = ROUTE_STOPS.STOP_ID;

-- author Stephanie Prystupa-Maule
-- View of all vehicles assigned to trips, with route details and expected arrival/departure times
-- different version of VEHICLE_TIMETABLES view above
CREATE OR REPLACE VIEW VW_VEHICLE_ASSIGNMENTS AS
SELECT 
    v.VEHICLE_ID,
    v.VEHICLE_TYPE,
    v.VEHICLE_NUMBER,
    ts.ID AS TRIP_SCHEDULE_ID,
    r.ROUTE_NAME,
    r.DIR AS DIRECTION,
    s.STOP_NAME,
    rs.STOP_SEQUENCE,
    sst.ARRIVAL AS EXPECTED_ARRIVAL,
    sst.DEPARTURE AS EXPECTED_DEPARTURE,
    s.IS_STATION
FROM 
    VEHICLES v
JOIN 
    TRIP_SCHEDULES ts ON v.CURRENT_ASSIGNED_TRIP = ts.ID
JOIN 
    ROUTES r ON ts.ROUTE_ID = r.ID
JOIN 
    ROUTE_STOPS rs ON r.ID = rs.ROUTE_ID
JOIN 
    STOPS s ON rs.STOP_ID = s.ID
JOIN 
    SCHEDULED_STOP_TIMES sst ON (ts.ID = sst.TRIP_SCHEDULE_ID AND rs.ID = sst.SEQ_STOP_ID)
ORDER BY 
    v.VEHICLE_ID, rs.STOP_SEQUENCE;

-- example filter by vehicle
-- SELECT * FROM VW_VEHICLE_ASSIGNMENTS WHERE VEHICLE_ID = 123;

-- author Stephanie Prystupa-Maule
-- View of all trip schedules, with route detials and assigned vehicle (if any)
CREATE OR REPLACE VIEW VW_TRIP_SCHEDULES AS
SELECT 
    ts.ID AS TRIP_SCHEDULE_ID,
    ts.START_TIME,
    r.ID AS ROUTE_ID,
    r.ROUTE_NAME,
    r.DIR AS DIRECTION,
    v.VEHICLE_ID,
    v.VEHICLE_TYPE,
    v.VEHICLE_NUMBER
FROM 
    TRIP_SCHEDULES ts
JOIN 
    ROUTES r ON ts.ROUTE_ID = r.ID
LEFT JOIN 
    VEHICLES v ON ts.ID = v.CURRENT_ASSIGNED_TRIP
ORDER BY 
    ts.START_TIME, r.ROUTE_NAME, r.DIR;

-- example filter by route
-- SELECT * FROM VW_TRIP_SCHEDULES WHERE ROUTE_NAME = 'Route 5';
