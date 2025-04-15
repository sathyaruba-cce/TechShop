CREATE DATABASE CarConnectDB;
USE CarConnectDB;
CREATE TABLE Customer (
    CustomerID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    Email VARCHAR(100),
    PhoneNumber VARCHAR(20),
    Address VARCHAR(255),
    Username VARCHAR(50) UNIQUE,
    Password VARCHAR(100),
    RegistrationDate DATE
);
desc Customer;
CREATE TABLE Vehicle (
    VehicleID INT PRIMARY KEY AUTO_INCREMENT,
    Model VARCHAR(100),
    Make VARCHAR(100),
    Year INT,
    Color VARCHAR(30),
    RegistrationNumber VARCHAR(50) UNIQUE,
    Availability BOOLEAN,
    DailyRate DECIMAL(10,2)
);
desc Vehicle;
CREATE TABLE Reservation (
    ReservationID INT PRIMARY KEY AUTO_INCREMENT,
    CustomerID INT,
    VehicleID INT,
    StartDate DATETIME,
    EndDate DATETIME,
    TotalCost DECIMAL(10,2),
    Status VARCHAR(20),
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID),
    FOREIGN KEY (VehicleID) REFERENCES Vehicle(VehicleID)
);
desc Reservation;
CREATE TABLE Admin (
    AdminID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    Email VARCHAR(100),
    PhoneNumber VARCHAR(20),
    Username VARCHAR(50) UNIQUE,
    Password VARCHAR(100),
    Role VARCHAR(50),
    JoinDate DATE
);
desc Admin;
INSERT INTO Customer (FirstName, LastName, Email, PhoneNumber, Address, Username, Password, RegistrationDate)
VALUES 
('Arun', 'Kumar', 'arun.kumar@gmail.com', '9840012345', 'Chennai, Tamil Nadu', 'arun_k', 'arun123', '2024-12-01'),
('Priya', 'Ramesh', 'priya.ramesh@yahoo.com', '9876543210', 'Coimbatore, Tamil Nadu', 'priyar', 'priya@123', '2024-12-02'),
('Karthik', 'Murali', 'karthik.m@rediffmail.com', '9898989898', 'Madurai, Tamil Nadu', 'karthikm', 'kmPass!21', '2024-12-03'),
('Divya', 'Shree', 'divya.shree@gmail.com', '9797979797', 'Tiruchirappalli, Tamil Nadu', 'divyashree', 'dsSecure44', '2024-12-04'),
('Sundar', 'Raj', 'sundar.raj@hotmail.com', '9888877665', 'Salem, Tamil Nadu', 'sundarraj', 'sundar@321', '2024-12-05'),
('Meena', 'Kumari', 'meena.k@gmail.com', '9787865432', 'Erode, Tamil Nadu', 'meenak', 'meena#pass', '2024-12-06'),
('Vignesh', 'Ram', 'vignesh.r@yahoo.com', '9767676767', 'Vellore, Tamil Nadu', 'vignesh_r', 'vigRam007', '2024-12-07'),
('Lakshmi', 'Devi', 'lakshmi.d@gmail.com', '9876567890', 'Thoothukudi, Tamil Nadu', 'lakshmid', 'lakshmiD$', '2024-12-08'),
('Naveen', 'Sankar', 'naveen.s@gmail.com', '9654321098', 'Tirunelveli, Tamil Nadu', 'naveens', 'naveen@998', '2024-12-09'),
('Anitha', 'Prabha', 'anitha.p@yahoo.com', '9567890123', 'Kanchipuram, Tamil Nadu', 'anithap', 'anitha##1122', '2024-12-10');
select * from Customer;
INSERT INTO Vehicle (Model, Make, Year, Color, RegistrationNumber, Availability, DailyRate)
VALUES 
('Swift', 'Maruti', 2020, 'White', 'TN01AB1234', TRUE, 1200.00),
('i20', 'Hyundai', 2021, 'Red', 'TN02CD5678', FALSE, 1500.00),
('Tiago', 'Tata', 2019, 'Blue', 'TN03EF9012', TRUE, 1100.00),
('Baleno', 'Maruti', 2022, 'Grey', 'TN04GH3456', FALSE, 1400.00),
('Venue', 'Hyundai', 2021, 'Black', 'TN05IJ7890', TRUE, 1600.00),
('Altroz', 'Tata', 2020, 'Silver', 'TN06KL1234', FALSE, 1150.00),
('Seltos', 'Kia', 2022, 'White', 'TN07MN5678', TRUE, 1800.00),
('City', 'Honda', 2020, 'Red', 'TN08OP9012', TRUE, 1700.00),
('Creta', 'Hyundai', 2021, 'Blue', 'TN09QR3456', FALSE, 1750.00),
('Dzire', 'Maruti', 2019, 'Black', 'TN10ST7890', TRUE, 1250.00);
select * from Vehicle ;
INSERT INTO Reservation (CustomerID, VehicleID, StartDate, EndDate, TotalCost, Status)
VALUES 
(1, 3, '2025-03-01 09:00:00', '2025-03-03 09:00:00', 2200.00, 'completed'),
(2, 5, '2025-03-04 10:00:00', '2025-03-06 10:00:00', 3200.00, 'completed'),
(3, 1, '2025-03-10 08:30:00', '2025-03-12 08:30:00', 2400.00, 'confirmed'),
(4, 2, '2025-03-15 09:00:00', '2025-03-16 09:00:00', 1500.00, 'pending'),
(5, 7, '2025-03-18 11:00:00', '2025-03-20 11:00:00', 3600.00, 'completed'),
(6, 4, '2025-03-21 09:00:00', '2025-03-23 09:00:00', 2800.00, 'confirmed'),
(7, 6, '2025-03-25 10:00:00', '2025-03-26 10:00:00', 1150.00, 'completed'),
(8, 8, '2025-03-28 08:00:00', '2025-03-30 08:00:00', 3400.00, 'cancelled'),
(9, 9, '2025-04-01 07:30:00', '2025-04-03 07:30:00', 3500.00, 'confirmed'),
(10, 10, '2025-04-04 09:00:00', '2025-04-06 09:00:00', 2500.00, 'pending');
select * from Reservation  ;
INSERT INTO Admin (FirstName, LastName, Email, PhoneNumber, Username, Password, Role, JoinDate)
VALUES 
('Ravi', 'Subramanian', 'ravi.s@carconnect.in', '9840011122', 'ravi_admin', 'admin@123', 'super admin', '2022-01-10'),
('Meena', 'Iyer', 'meena.i@carconnect.in', '9840022233', 'meena_fleet', 'fleet@456', 'fleet manager', '2022-02-15'),
('Arun', 'Raj', 'arun.raj@carconnect.in', '9840033344', 'arun_mgr', 'manager@789', 'fleet manager', '2022-03-20'),
('Divya', 'Bharathi', 'divya.b@carconnect.in', '9840044455', 'divya_admin', 'pass@divya', 'super admin', '2022-04-05'),
('Sathish', 'Kumar', 'sathish.k@carconnect.in', '9840055566', 'sathish_ops', 'sathish@ops', 'operations', '2022-05-10'),
('Priya', 'Sundar', 'priya.s@carconnect.in', '9840066677', 'priya_fleet', 'priya@123', 'fleet manager', '2022-06-12'),
('Karthik', 'Venkat', 'karthik.v@carconnect.in', '9840077788', 'karthik_admin', 'karthik@admin', 'super admin', '2022-07-18'),
('Lakshmi', 'Devi', 'lakshmi.d@carconnect.in', '9840088899', 'lakshmi_mgr', 'lakshmi@456', 'fleet manager', '2022-08-20'),
('Vijay', 'Kumar', 'vijay.k@carconnect.in', '9840099900', 'vijay_ops', 'vijay@ops', 'operations', '2022-09-25'),
('Anitha', 'Ravi', 'anitha.r@carconnect.in', '9840101010', 'anitha_admin', 'anitha@321', 'super admin', '2022-10-30');
select * from Admin;
select * from Customer;
select * from Reservation;
select * from Vehicle;
USE CarConnectDB;



