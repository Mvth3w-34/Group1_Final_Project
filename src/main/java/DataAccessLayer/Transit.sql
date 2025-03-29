DROP DATABASE IF EXISTS transit;

CREATE DATABASE transit;

USE transit;

CREATE TABLE Vehicles
(
    VehicleID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    VehicleType varchar(19) NOT NULL,
    VehicleNumber varchar(50) NOT NULL,
    FuelType varchar(11) NOT NULL,
    FuelConsumptionRate float NOT NULL,
    MaximumPassengers int NOT NULL,
    CurrentAssignedRoute varchar(50)
);

CREATE TABLE Credentials
(
    CredentialID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    UserName varchar(50) NOT NULL,
    Password varchar(50) NOT NULL
);

CREATE TABLE Operators
(
    OperatorID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Name varchar(50),
    CredentialID int NOT NULL,
    Type varchar(9) NOT NULL,
    Email varchar(50),
    FOREIGN KEY (CredentialID) REFERENCES Credentials(CredentialID)
);
INSERT INTO Credentials (UserName, Password) VALUES ('testUser', 'testPass');
INSERT INTO Credentials (UserName, Password) VALUES ('myUser', 'myPass');

INSERT INTO Operators (Name, CredentialID, Type, Email) VALUES ('Bob Smith', 1, 'MANAGER', '');
INSERT INTO Operators (Name, CredentialID, Type, Email) VALUES ('Drew Anderson', 2, 'OPERATOR', '');